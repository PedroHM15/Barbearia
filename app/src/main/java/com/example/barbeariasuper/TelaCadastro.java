package com.example.barbeariasuper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.barbeariasuper.BARBEIRO.TelaBarbeiro;
import com.example.barbeariasuper.CLIENTE.TelaUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class TelaCadastro extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    EditText email_cadastro, senha_cadastro, nome_cadastro;
    Button btn_cadastrar;
    ImageView image_cadastro;
    ImageButton btn_voltar_login;
    Map<String, Object> usuarios;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro);

        iniciar_componentes();

        image_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent upImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                upImage.setType("image/*");
                startActivityForResult(upImage, 0);
            }
        });
    }

    private void iniciar_componentes() {
        email_cadastro = findViewById(R.id.email_cadastro);
        senha_cadastro = findViewById(R.id.senha_cadastro);
        nome_cadastro = findViewById(R.id.nome_cadastro);
        btn_cadastrar = findViewById(R.id.btn_cadastrar);
        image_cadastro = findViewById(R.id.image_cadastro);
        btn_voltar_login = findViewById(R.id.btn_voltar_login);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        usuarios = new HashMap<>();
    }

    public void CadastrarUsuario(View v){
        String emailNovoUser = email_cadastro.getText().toString();
        String senhaNovoUser = senha_cadastro.getText().toString();

        usuarios.put("nome", nome_cadastro.getText().toString());
        usuarios.put("email", emailNovoUser);

        if (senhaNovoUser.length() >= 6){
            int codigo = 0;
            for (char c : emailNovoUser.toCharArray()) {
                if(c == '@'){
                    codigo = 1;
                    mAuth.createUserWithEmailAndPassword(emailNovoUser, senhaNovoUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            StorageReference referencia = FirebaseStorage.getInstance().getReference("/imagens/" + uri);

                            referencia.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    if(task.isSuccessful()){
                                        referencia.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String urlPerfil = uri.toString();

                                                Picasso.get().load(urlPerfil).into(image_cadastro);

                                                usuarios.put("Perfil", urlPerfil);

                                                db.collection("Clientes").document(mAuth.getUid()).set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Toast.makeText(TelaCadastro.this, "Cadastro Efetuado com Sucesso!!", Toast.LENGTH_SHORT).show();

                                                        Intent back = new Intent(TelaCadastro.this, Telalogin.class);
                                                        startActivity(back);
                                                    }
                                                });
                                            }
                                        });
                                    }
                                }
                            });

                        }
                    });
                    break;
                }
            }
            if(codigo == 0){
                Toast.makeText(this, "E-mail Inválido", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Senha muito curta, minimo de 6 caracteres", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0){
            uri = data.getData();

            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                image_cadastro.setImageDrawable(new BitmapDrawable(bitmap));
            } catch (Exception e){
                Log.e("Erro no Upload", e.toString());
            }
        }
    }

    public void Retornar(View v){
        Intent retornar = new Intent(this, Telalogin.class);
        startActivity(retornar);
    }
}