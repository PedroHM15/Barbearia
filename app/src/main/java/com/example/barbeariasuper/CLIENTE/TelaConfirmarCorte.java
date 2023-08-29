package com.example.barbeariasuper.CLIENTE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barbeariasuper.R;
import com.example.barbeariasuper.TelaCadastro;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TelaConfirmarCorte extends AppCompatActivity {
    TextView nomeBarbeiro_infosUser, dataHora_cofirma, preco_confirma;
    CheckBox cabelo, barba, sobrancelha;
    ImageView imageBarbeiro_confirma;
    Button btn_agendar;
    ImageButton btn_voltarAgendar;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    int preco = 0, x=0,y=0,z=0;
    Map<String, Object> agendar;
    Map<String, Object> agendar_funcionario;
    List<String> servico = new ArrayList<>();
    String data = TelaListaDatas.data_deletar_enviar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_confirmar_corte);

        iniciar_componentes();


    }
    public void Voltartela(View v){
        Intent telaagendar = new Intent(TelaConfirmarCorte.this, TelaListaDatas.class);
        startActivity(telaagendar);
    }
    public void cabelo(View v){
        if(cabelo.isChecked()){
            preco += 40;
            x=1;
            preco_confirma.setText("Preço: R$"+preco);
            servico.add("Corte");
        } else if (x==1) {
            preco -= 40;
            x=0;
            preco_confirma.setText("Preço: R$"+preco);
            servico.remove("Corte");
        }
    }
    public void barba(View v){
        if(barba.isChecked()){
            preco += 25;
            y=1;
            preco_confirma.setText("Preço: R$"+preco);
            servico.add("Barba");
        } else if (y==1) {
            preco -= 25;
            y=0;
            preco_confirma.setText("Preço: R$"+preco);
            servico.remove("Barba");
        }
    }
    public void sobrancelha(View v){
        if(sobrancelha.isChecked()){
            preco += 5;
            z=1;
            preco_confirma.setText("Preço: R$"+preco);
            servico.add("sobrancelha");
        } else if (z==1) {
            preco -= 5;
            z=0;
            preco_confirma.setText("Preço: R$"+preco);
            servico.remove("sobrancelha");
        }
    }

    private void iniciar_componentes() {
        nomeBarbeiro_infosUser = findViewById(R.id.nomeBarbeiro_confirma);
        dataHora_cofirma = findViewById(R.id.dataHora_cofirma);
        preco_confirma = findViewById(R.id.preco_confirma);
        imageBarbeiro_confirma = findViewById(R.id.imageBarbeiro_confirma);

        btn_agendar = findViewById(R.id.btn_agendar);
        btn_voltarAgendar = findViewById(R.id.btn_voltarAgendar);

        cabelo = findViewById(R.id.checkCorte);
        barba = findViewById(R.id.checkBarba);
        sobrancelha = findViewById(R.id.checkSobrancelha);

        nomeBarbeiro_infosUser.setText(TelaListaDatas.nome_enviar);
        dataHora_cofirma.setText(TelaListaDatas.data_enviar);
        Picasso.get().load(TelaListaDatas.perfil_enviar).into(imageBarbeiro_confirma);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        agendar = new HashMap<>();
        agendar_funcionario = new HashMap<>();
    }

    public void Agendar(View v) {
        if (preco != 0) {
            agendar.put("Nome", TelaListaDatas.nome_enviar);
            agendar.put("Data", TelaListaDatas.data_enviar);
            agendar.put("Perfil", TelaListaDatas.perfil_enviar);
            agendar.put("Preco", preco);
            agendar.put("Servico", servico);

            db.collection("Clientes").document(mAuth.getUid()).collection("Agenda").document(TelaListaDatas.uIdBarbeiros_enviar).set(agendar).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        agendar_funcionario.put("Preco", preco);
                        agendar_funcionario.put("Servico", servico);
                        agendar_funcionario.put("Data", TelaListaDatas.data_enviar);
                        db.collection("Clientes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()){
                                    for (QueryDocumentSnapshot document : task.getResult()){
                                        String mAuthUid = mAuth.getUid();
                                        String docId = document.getId();
                                        Log.e("uIddd", mAuthUid +"   "+docId);
                                        if (mAuthUid.equals(docId)){
                                            String a = document.getString("nome");
                                            agendar_funcionario.put("Nome", a);
                                            a = document.getString("Perfil");
                                            agendar_funcionario.put("Perfil", a);
                                            agendarFuncionario();
                                            break;

                                        }
                                    }
                                }
                            }
                        });

                    }
                    else {
                        Toast.makeText(TelaConfirmarCorte.this, "Algo deu errado, Tente novamente mais Tarde ou entre em contato em nosso Zap", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            Toast.makeText(TelaConfirmarCorte.this, "Informe o que deseja fazer antes de confirmar", Toast.LENGTH_SHORT).show();
        }

    }
    public void agendarFuncionario(){
        db.collection("funcionarios").document(TelaListaDatas.uIdBarbeiros_enviar).collection("Agenda").document(mAuth.getUid()).set(agendar_funcionario).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    db.collection("funcionarios").document(TelaListaDatas.uIdBarbeiros_enviar).collection("HorariosLivres").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                for (QueryDocumentSnapshot document_data : task.getResult()){


                                    Log.e("Printtt", data+"     "+document_data.getString("Data"));
                                    if (data.equals(document_data.getString("Data"))){
                                        data= document_data.getId();
                                        db.collection("funcionarios").document(TelaListaDatas.uIdBarbeiros_enviar).collection("HorariosLivres").document(data).delete();
                                        break;
                                    }
                                }
                            }
                        }
                    });
                }
                else {
                    Log.e("uIddd", "Deu Errado");
                }
            }
        });
        Toast.makeText(TelaConfirmarCorte.this, "Corte Agendado com Sucesso", Toast.LENGTH_SHORT).show();
        Intent telaagendar = new Intent(TelaConfirmarCorte.this, TelaUser.class);
        startActivity(telaagendar);
    }

}