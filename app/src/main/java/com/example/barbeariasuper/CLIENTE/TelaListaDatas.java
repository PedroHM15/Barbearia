package com.example.barbeariasuper.CLIENTE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.barbeariasuper.Adapter;
import com.example.barbeariasuper.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class TelaListaDatas extends AppCompatActivity {
    ListView ListaDatas_clientes;
    FirebaseFirestore db;
    List<String> datas = new ArrayList<>();
    List<String> nome_funcionario = new ArrayList<>();
    List<String> perfil_funcionario = new ArrayList<>();
    List<String> data_deletar = new ArrayList<>();
    List<String> vaiza = new ArrayList<>();
    List<String> uIdBarbeiros = new ArrayList<>();
    static String data_enviar, nome_enviar, perfil_enviar, uIdBarbeiros_enviar, data_deletar_enviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_lista_datas);

        iniciarComponentes();

        listarDatas();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Adapter adapti = new Adapter(TelaListaDatas.this, nome_funcionario, vaiza, datas, perfil_funcionario);
                ListaDatas_clientes.setAdapter(adapti);
            }
        }, 1000);
        ListaDatas_clientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for(int i = 0; i < datas.size(); i++){
                    if (position == i){
                        data_deletar_enviar = data_deletar.get(i);
                        data_enviar = datas.get(i);
                        nome_enviar = nome_funcionario.get(i);
                        perfil_enviar = perfil_funcionario.get(i);
                        uIdBarbeiros_enviar = uIdBarbeiros.get(i);
                        break;
                    }
                }
                Intent ir_agendarCorte = new Intent(TelaListaDatas.this, TelaConfirmarCorte.class);
                startActivity(ir_agendarCorte);
            }
        });
    }

    private void iniciarComponentes() {
        ListaDatas_clientes = findViewById(R.id.ListaDatas_clientes);
        db = FirebaseFirestore.getInstance();
    }

    private void listarDatas() {
        db.collection("funcionarios").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document_nome : task.getResult()){
                        String uId = document_nome.getId();
                            db.collection("funcionarios").document(uId).collection("HorariosLivres").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()){
                                        for (QueryDocumentSnapshot document : task.getResult()){
                                            if (document.getString("Data") != null) {
                                                datas.add("Dia: " + document.getString("Data")+"  Hora: "+document.getString("Horario"));
                                                data_deletar.add(document.getString("Data"));
                                                vaiza.add("");
                                                nome_funcionario.add(document_nome.getString("nome"));
                                                perfil_funcionario.add(document_nome.getString("Perfil"));
                                                uIdBarbeiros.add(document_nome.getId());
                                            }
                                        }
                                    }
                                }
                            });
                    }

                }
            }
        });
    }


    public void voltarTelaUser(View v){
        Intent telauser = new Intent(TelaListaDatas.this,TelaUser.class);
        startActivity(telauser);
    }
}