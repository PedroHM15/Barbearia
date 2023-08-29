package com.example.barbeariasuper.ADM;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.barbeariasuper.Adapter;
import com.example.barbeariasuper.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListaAdm_barbeiros extends AppCompatActivity {
    ListView Lista_barbeirosAdm;
    FirebaseFirestore db;
    List<String> nome = new ArrayList<>();
    List<String> perfil = new ArrayList<>();
    List<String> email = new ArrayList<>();
    List<String> vazia = new ArrayList<>();
    List<String> uId = new ArrayList<>();
    public static String nome_update, perfil_update, email_update, vazio_update, uId_update;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_adm_barbeiros);

        iniciarComponentes();

        listarFuncionarios();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Adapter adapti = new Adapter(getApplicationContext(), nome, email,vazia,perfil);
                Lista_barbeirosAdm.setAdapter(adapti);
            }
        }, 1000);

        Lista_barbeirosAdm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < nome.size(); i++){
                    if (position == i){
                        nome_update = nome.get(i);
                        perfil_update = perfil.get(i);
                        email_update = email.get(i);
                        vazio_update = vazia.get(i);
                        uId_update = uId.get(i);
                        break;
                    }
                }
                Intent ir_InfoBarbeiro = new Intent(ListaAdm_barbeiros.this, InfosBarbeirosAdm.class);
                startActivity(ir_InfoBarbeiro);
            }
        });
    }

    private void iniciarComponentes() {
        Lista_barbeirosAdm = findViewById(R.id.Lista_barbeirosAdm);
        db = FirebaseFirestore.getInstance();
    }

    private void listarFuncionarios() {
        db.collection("funcionarios").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        nome.add(document.getString("nome"));
                        perfil.add(document.getString("Perfil"));
                        email.add(document.getString("email"));
                        uId.add(document.getId());
                        vazia.add(" ");

                    }
                }
            }
        });
    }
    public void VoltarTela(View v){
        Intent telaadm = new Intent(ListaAdm_barbeiros.this,TelaAdm.class);
        startActivity(telaadm);
    }
}