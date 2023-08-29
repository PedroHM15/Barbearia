package com.example.barbeariasuper.BARBEIRO;

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

import com.example.barbeariasuper.CLIENTE.TelaInfosUser;
import com.example.barbeariasuper.CLIENTE.TelaListaCortes;
import com.example.barbeariasuper.CLIENTE.TelaUser;
import com.example.barbeariasuper.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class TelaInfosBarbeiro extends AppCompatActivity {
    TextView nomeCliente_infosBarbeiro, dataHora_InfosBarbeiro, Corte_infosBarbeiro;
    ImageView imageCliente_infosBarbeiro;
    Button btn_desmarcarBarbeiro;
    ImageButton btn_voltarListaCortesBarbeiro;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    String nome, perfil, data, servico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_infos_barbeiro);
        iniciar_componentes();

    }
    public void desmarcarCorte(View v) {
        db.collection("funcionarios").document(mAuth.getUid()).collection("Agenda").document(TelaAgendaBarbeiro.uId_enviar).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    db.collection("Clientes").document(TelaAgendaBarbeiro.uId_enviar).collection("Agenda").document(mAuth.getUid()).delete();
                    Toast.makeText(TelaInfosBarbeiro.this, "Horario desmarcado com sucesso", Toast.LENGTH_SHORT).show();
                    Intent telalista = new Intent(TelaInfosBarbeiro.this, TelaBarbeiro.class);
                    startActivity(telalista);
                }else {
                    Toast.makeText(TelaInfosBarbeiro.this, "Algo deu errado, tente novamente mais tarde ou entre em contato no nosso ZAP ZAP", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void VoltarTela(View v){
        Intent telalista = new Intent(TelaInfosBarbeiro.this, TelaAgendaBarbeiro.class);
        startActivity(telalista);
    }

    private void iniciar_componentes() {
        nomeCliente_infosBarbeiro = findViewById(R.id.nomeCliente_infosBarbeiro);
        dataHora_InfosBarbeiro = findViewById(R.id.dataHora_InfosBarbeiro);
        Corte_infosBarbeiro = findViewById(R.id.Corte_infosBarbeiro);
        imageCliente_infosBarbeiro = findViewById(R.id.imageCliente_infosBarbeiro);

        nome = TelaAgendaBarbeiro.nome_enviar;
        perfil = TelaAgendaBarbeiro.perfil_enviar;
        data = TelaAgendaBarbeiro.data_enviar;
        servico = TelaAgendaBarbeiro.servico_enviar;

        nomeCliente_infosBarbeiro.setText("Nome: "+nome);
        dataHora_InfosBarbeiro.setText(data);
        Corte_infosBarbeiro.setText("Serviço: "+servico);
        Picasso.get().load(perfil).into(imageCliente_infosBarbeiro);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }
}