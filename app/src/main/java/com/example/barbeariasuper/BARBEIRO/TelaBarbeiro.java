package com.example.barbeariasuper.BARBEIRO;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.barbeariasuper.R;
import com.example.barbeariasuper.Telalogin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class TelaBarbeiro extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    DocumentReference docRef;
    TextView nomeBarbeiro_barbeiro;
    ImageView imageBarbeiro_barbeiro;
    String image, nome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_barbeiro);

        iniciarComponentes();
        docRef.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            image = document.getString("Perfil");
                            nome = document.getString("nome");
                            Picasso.get().load(image).into(imageBarbeiro_barbeiro);
                            nomeBarbeiro_barbeiro.setText(nome);
                        }
                    }
                });
    }

    private void iniciarComponentes() {
        mAuth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();
        docRef = db.collection("funcionarios").document(mAuth.getUid());

        imageBarbeiro_barbeiro = findViewById(R.id.imageBarbeiro_barbeiro);
        nomeBarbeiro_barbeiro = findViewById(R.id.nomeBarbeiro_barbeiro);
    }

    public void ListaCortesAgendados(View v){
        Intent telalista_cortesAgendados = new Intent(TelaBarbeiro.this, TelaAgendaBarbeiro.class);
        startActivity(telalista_cortesAgendados);
    }
    public void DataHoraDisponiveis(View v){
        Intent telaDataHoraDisponiveis= new Intent(TelaBarbeiro.this, TelaHoraDataDisponiveisBarbeiro.class);
        startActivity(telaDataHoraDisponiveis);
    }
    public void deslogar(View v){
        Intent cadastrarBarbeiro = new Intent(TelaBarbeiro.this, Telalogin.class);
        startActivity(cadastrarBarbeiro);
        FirebaseAuth.getInstance().signOut();
    }
}