package com.example.barbeariasuper.CLIENTE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.barbeariasuper.BARBEIRO.TelaBarbeiro;
import com.example.barbeariasuper.R;
import com.example.barbeariasuper.Telalogin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class TelaUser extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    DocumentReference docRef;
    ImageButton btn_deslogarUser;
    ImageView image_User;
    TextView nome_cliente_user; //nome do cliente na tela junto com a imagem dele!
    String image, nome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_user);

        iniciarComponentes();

        docRef.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            image = document.getString("Perfil");
                            nome = document.getString("nome");
                            Picasso.get().load(image).into(image_User);
                            nome_cliente_user.setText(nome);
                        }
                    }
                });

    }

    private void iniciarComponentes() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        docRef = db.collection("Clientes").document(mAuth.getUid());

        image_User = findViewById(R.id.image_User);
        btn_deslogarUser = findViewById(R.id.btn_deslogarUser);
        nome_cliente_user = findViewById(R.id.nome_cliente_user);
    }

    public void deslogar(View v){
        Intent deslogar = new Intent(TelaUser.this, Telalogin.class);
        startActivity(deslogar);
        FirebaseAuth.getInstance().signOut();
    }
    public void Agendarcorte(View v){
        Intent tela_agendar_corte = new Intent(TelaUser.this, TelaListaCortes.class);
        startActivity(tela_agendar_corte);
    }
    public void Listarcortes(View v){
        Intent tela_listar_datas = new Intent(TelaUser.this, TelaListaDatas.class);
        startActivity(tela_listar_datas);
    }



}