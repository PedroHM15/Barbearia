package com.example.barbeariasuper.BARBEIRO;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.barbeariasuper.Adapter;
import com.example.barbeariasuper.CLIENTE.TelaInfosUser;
import com.example.barbeariasuper.CLIENTE.TelaListaCortes;
import com.example.barbeariasuper.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TelaAgendaBarbeiro extends AppCompatActivity {
    ListView Lista_cortesAgendadosBarbeiro;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    List<String> nome, data, perfil, servico, uId, lista_servicos;
    List<Long> preco;
    static String nome_enviar, data_enviar, perfil_enviar, servico_enviar, uId_enviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_agenda_barbeiro);

        iniciarComponentes();

        pegarInfos();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Adapter adapti = new Adapter(TelaAgendaBarbeiro.this, nome, data, lista_servicos, perfil);
                Lista_cortesAgendadosBarbeiro.setAdapter(adapti);
            }
        }, 1000);

        Lista_cortesAgendadosBarbeiro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for(int i = 0; i < data.size(); i++){
                    if (position == i){
                        data_enviar = data.get(i);
                        nome_enviar = nome.get(i);
                        perfil_enviar = perfil.get(i);
                        uId_enviar = uId.get(i);
                        servico_enviar = lista_servicos.get(i);
                        break;
                    }
                }
                Intent ir_agendarCorte = new Intent(TelaAgendaBarbeiro.this, TelaInfosBarbeiro.class);
                startActivity(ir_agendarCorte);
            }
        });

    }

    private void pegarInfos() {
        db.collection("funcionarios").document(mAuth.getUid()).collection("Agenda").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    int x=0;
                    for (QueryDocumentSnapshot document : task.getResult()){
                        uId.add(document.getId());
                        nome.add(document.getString("Nome"));
                        data.add(document.getString("Data"));
                        perfil.add(document.getString("Perfil"));
                        preco.add(document.getLong("Preco"));
                        servico.addAll((List<String>) document.get("Servico"));

                        if (servico.size()==3){
                            lista_servicos.add(String.valueOf(servico.get(0))+" / "+String.valueOf(servico.get(1))+" / "+String.valueOf(servico.get(2))+"  Preço: R$"+String.valueOf(preco.get(x)));
                            servico.remove(2);
                            servico.remove(1);
                            servico.remove(0);
                            x++;
                        }
                        else if (servico.size()==2){
                            lista_servicos.add(String.valueOf(servico.get(0))+" / "+String.valueOf(servico.get(1))+"  Preço: R$"+String.valueOf(preco.get(x)));
                            servico.remove(1);
                            servico.remove(0);
                            x++;
                        }
                        else if (servico.size()==1){
                            lista_servicos.add(String.valueOf(servico.get(0))+"  Preço: R$"+String.valueOf(preco.get(x)));
                            servico.remove(0);
                            x++;
                        }

                    }
                }
            }
        });
    }

    private void iniciarComponentes() {
        Lista_cortesAgendadosBarbeiro = findViewById(R.id.Lista_cortesAgendadosBarbeiro);

        nome = new ArrayList<>();
        data = new ArrayList<>();
        perfil = new ArrayList<>();
        servico = new ArrayList<>();
        uId = new ArrayList<>();
        lista_servicos = new ArrayList<>();
        preco = new ArrayList<>();

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    public void VoltarTela(View v){
        Intent telabarbeiro = new Intent(TelaAgendaBarbeiro.this, TelaBarbeiro.class);
        startActivity(telabarbeiro);
    }
}