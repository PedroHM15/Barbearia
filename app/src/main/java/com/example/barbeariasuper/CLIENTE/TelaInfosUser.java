package com.example.barbeariasuper.CLIENTE;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class TelaInfosUser extends AppCompatActivity {
    TextView nomeBarbeiro_infosUser, dataHora_infosUser, preco_infosUser;
    ImageView imageBarbeiro_infosUser;
    Button btn_desmarcarUser;
    ImageButton btn_voltarListaCortes;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    String nome, perfil, data, servico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_infos_user);

        iniciar_componentes();
    }

    public void desmarcarCorte(View v) {
        db.collection("Clientes").document(mAuth.getUid()).collection("Agenda").document(TelaListaCortes.uId_enviar).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    db.collection("funcionarios").document(TelaListaCortes.uId_enviar).collection("Agenda").document(mAuth.getUid()).delete();
                    Toast.makeText(TelaInfosUser.this, "Horario desmarcado com sucesso", Toast.LENGTH_SHORT).show();
                    Intent telalista = new Intent(TelaInfosUser.this, TelaUser.class);
                    startActivity(telalista);
                }else {
                    Toast.makeText(TelaInfosUser.this, "Algo deu errado, tente novamente mais tarde ou entre em contato no nosso ZAP ZAP", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void VoltarTela(View v){
        Intent telalista = new Intent(TelaInfosUser.this, TelaListaCortes.class);
        startActivity(telalista);
    }

    private void iniciar_componentes() {
        nomeBarbeiro_infosUser = findViewById(R.id.nomeBarbeiro_infosUser);
        dataHora_infosUser = findViewById(R.id.dataHora_infosUser);
        preco_infosUser = findViewById(R.id.preco_infosUser);
        imageBarbeiro_infosUser = findViewById(R.id.imageBarbeiro_infosUser);
        btn_voltarListaCortes = findViewById(R.id.btn_voltarListaCortes);
        btn_desmarcarUser = findViewById(R.id.btn_desmarcarUser);

        nome = TelaListaCortes.nome_enviar;
        perfil = TelaListaCortes.perfil_enviar;
        data = TelaListaCortes.data_enviar;
        servico = TelaListaCortes.servico_enviar;

        nomeBarbeiro_infosUser.setText("Nome: "+nome);
        dataHora_infosUser.setText(data);
        preco_infosUser.setText("Serviço: "+servico);
        Picasso.get().load(perfil).into(imageBarbeiro_infosUser);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }
}