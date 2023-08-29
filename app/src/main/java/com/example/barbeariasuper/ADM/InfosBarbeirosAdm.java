package com.example.barbeariasuper.ADM;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barbeariasuper.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class InfosBarbeirosAdm extends AppCompatActivity {
    TextView nomeBarbeiro_adm, emailBarbeiro_adm, senhaBarbeiro_adm;
    Button btn_demitirBarbeiro;
    ImageButton btn_voltarListaBarbeiros_Adm;
    ImageView imageBarbeiro_adm;
    String nome_update, perfil_update, email_update, vazio_update, uId_update;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infos_barbeiros_adm);

        iniciarComponentes();
        Picasso.get().load(perfil_update).into(imageBarbeiro_adm);
        nomeBarbeiro_adm.setText(nome_update);
        emailBarbeiro_adm.setText(email_update);
        senhaBarbeiro_adm.setText(vazio_update);

        btn_demitirBarbeiro.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                demitirBarbeiro();
            }
        });
        btn_voltarListaBarbeiros_Adm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voltar();
            }
        });
    }
    public void demitirBarbeiro(){
        db.collection("funcionarios").document(uId_update).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Barbeiro demitido com sucesso", Toast.LENGTH_SHORT).show();
                    Intent voltarAdm = new Intent(InfosBarbeirosAdm.this, TelaAdm.class);
                    startActivity(voltarAdm);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Algo Deu Errado ao Demitir o Barbeiro" + nome_update, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void voltar(){
        Intent voltarAdm = new Intent(InfosBarbeirosAdm.this, TelaAdm.class);
        startActivity(voltarAdm);
    }

    private void iniciarComponentes() {
        imageBarbeiro_adm = findViewById(R.id.imageBarbeiro_adm);
        nomeBarbeiro_adm = findViewById(R.id.nomeBarbeiro_adm);
        emailBarbeiro_adm = findViewById(R.id.emailBarbeiro_adm);
        senhaBarbeiro_adm = findViewById(R.id.senhaBarbeiro_adm);
        btn_demitirBarbeiro = findViewById(R.id.btn_demitirBarbeiro);
        btn_voltarListaBarbeiros_Adm = findViewById(R.id.btn_voltarListaBarbeiros_Adm);

        nome_update = ListaAdm_barbeiros.nome_update;
        perfil_update = ListaAdm_barbeiros.perfil_update;
        email_update = ListaAdm_barbeiros.email_update;
        vazio_update = ListaAdm_barbeiros.vazio_update;
        uId_update = ListaAdm_barbeiros.uId_update;

        db = FirebaseFirestore.getInstance();
    }
}