package com.example.barbeariasuper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.barbeariasuper.ADM.TelaAdm;
import com.example.barbeariasuper.BARBEIRO.TelaBarbeiro;
import com.example.barbeariasuper.CLIENTE.TelaUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class  Telalogin extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    EditText email_login, senha_login;
    Button btn_logar;
    static public String uId;
    String email, senha;
    FirebaseUser currentUser;
    String[] tipos = {"Clientes", "adm", "funcionarios"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.telalogin);

        iniciar_componentes();

        if (currentUser != null) {
            loginAuto();
        }
    }

    private void iniciar_componentes() {
        email_login = findViewById(R.id.email_login);
        senha_login = findViewById(R.id.senha_login);
        btn_logar = findViewById(R.id.btn_logar);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();
    }

    public void Logar(View v){
         email = email_login.getText().toString();
         senha = senha_login.getText().toString();

        mAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    uId = mAuth.getUid();

                    for (String tipo : tipos) {
                        if(tipo.equals("Clientes")){
                            db.collection(tipo).document(mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        if (task.getResult().exists()) {
                                            Toast.makeText(Telalogin.this, "Bem Vindo!!", Toast.LENGTH_SHORT).show();

                                            Intent telaUser = new Intent(Telalogin.this, TelaUser.class);
                                            startActivity(telaUser);
                                        }
                                        else {
                                            Log.d("colecao", "UID não encontrado na coleção " + tipo);
                                        }
                                    }
                                    else {
                                        Log.e("colecao", "Erro ao consultar o Firestore: ", task.getException());
                                    }
                                }
                            });
                        }
                        else if(tipo.equals("adm")){
                            db.collection(tipo).document(mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        if (task.getResult().exists()) {
                                            Intent telaAdm = new Intent(Telalogin.this, TelaAdm.class);
                                            startActivity(telaAdm);

                                            Toast.makeText(Telalogin.this, "Bem Vindo!!", Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            Log.d("colecao", "UID não encontrado na coleção " + tipo);
                                        }
                                    }
                                    else {
                                        Log.e("colecao", "Erro ao consultar o Firestore: ", task.getException());
                                    }
                                }
                            });
                        }
                        else if(tipo.equals("funcionarios")){
                            db.collection(tipo).document(mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        if (task.getResult().exists()) {
                                            Intent telaBarbeiro = new Intent(Telalogin.this, TelaBarbeiro.class);
                                            startActivity(telaBarbeiro);

                                            Toast.makeText(Telalogin.this, "Bem Vindo!!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Log.d("colecao", "UID não encontrado na coleção " + tipo);
                                        }
                                    } else {
                                        Log.e("colecao", "Erro ao consultar o Firestore: ", task.getException());
                                    }
                                }
                            });
                        }
                    }
                }
                else {
                    Toast.makeText(Telalogin.this, "E-mail e/ou senha incorreto(s)", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void loginAuto(){
        if (currentUser != null) {
            uId = mAuth.getUid();

            for (String tipo : tipos) {
                if (tipo.equals("Clientes")) {
                    db.collection(tipo).document(mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().exists()) {
                                    Toast.makeText(Telalogin.this, "Bem Vindo!!", Toast.LENGTH_SHORT).show();

                                    Intent telaUser = new Intent(Telalogin.this, TelaUser.class);
                                    startActivity(telaUser);
                                } else {
                                    Log.d("colecao", "UID não encontrado na coleção " + tipo);
                                }
                            } else {
                                Log.e("colecao", "Erro ao consultar o Firestore: ", task.getException());
                            }
                        }
                    });
                } else if (tipo.equals("adm")) {
                    db.collection(tipo).document(mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().exists()) {
                                    Intent telaAdm = new Intent(Telalogin.this, TelaAdm.class);
                                    startActivity(telaAdm);

                                    Toast.makeText(Telalogin.this, "Bem Vindo!!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.d("colecao", "UID não encontrado na coleção " + tipo);
                                }
                            } else {
                                Log.e("colecao", "Erro ao consultar o Firestore: ", task.getException());
                            }
                        }
                    });
                } else if (tipo.equals("funcionarios")) {
                    db.collection(tipo).document(mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().exists()) {
                                    Intent telaBarbeiro = new Intent(Telalogin.this, TelaBarbeiro.class);
                                    startActivity(telaBarbeiro);

                                    Toast.makeText(Telalogin.this, "Bem Vindo!!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.d("colecao", "UID não encontrado na coleção " + tipo);
                                }
                            } else {
                                Log.e("colecao", "Erro ao consultar o Firestore: ", task.getException());
                            }
                        }
                    });
                }
            }
        }
    }


    public void Cadastrar(View v){
        Intent telaCadastrar = new Intent(Telalogin.this, TelaCadastro.class);
        startActivity(telaCadastrar);
    }
}