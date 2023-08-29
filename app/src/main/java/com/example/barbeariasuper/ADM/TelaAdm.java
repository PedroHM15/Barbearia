package com.example.barbeariasuper.ADM;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.barbeariasuper.R;
import com.example.barbeariasuper.Telalogin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class TelaAdm extends AppCompatActivity {

    ImageView btn_deslogarAdm, cadastrarBarbeiro_adm, listaDeBarbeiros_adm;
    TextView nomeAdm;
    FirebaseFirestore db;
    ImageView btn_deslogar_adm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_adm);

        iniciarComponentes();

        pegarNome();

        listaDeBarbeiros_adm.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent listaBarbeiros = new Intent(TelaAdm.this, ListaAdm_barbeiros.class);
                startActivity(listaBarbeiros);
            }
        });
        cadastrarBarbeiro_adm.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent cadastrarBarbeiro = new Intent(TelaAdm.this, CadastroBarbeiroAdm.class);
                startActivity(cadastrarBarbeiro);
            }
        });
        btn_deslogar_adm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deslogar();
            }
        });

    }

    private void pegarNome() {
        db.collection("adm").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        String nome = document.getString("nome");
                        nomeAdm.setText(nome);
                    }
                }
            }
        });
    }
    public void deslogar(){
        Intent cadastrarBarbeiro = new Intent(TelaAdm.this, Telalogin.class);
        startActivity(cadastrarBarbeiro);
        FirebaseAuth.getInstance().signOut();
    }

    private void iniciarComponentes() {
        cadastrarBarbeiro_adm = findViewById(R.id.cadastrarBarbeiro_adm);
        listaDeBarbeiros_adm = findViewById(R.id.listaDeBarbeiros_adm);
        btn_deslogar_adm = findViewById(R.id.btn_deslogar_adm);

        nomeAdm = findViewById(R.id.nomeAdm);
        db = FirebaseFirestore.getInstance();
    }
}