package com.example.barbeariasuper.ADM;

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

import com.example.barbeariasuper.R;
import com.example.barbeariasuper.TelaCadastro;
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

public class CadastroBarbeiroAdm extends AppCompatActivity {
    EditText nomeCadastraBarbeiro_adm, emailCadastraBarbeiro_adm, senhaCadastraBarbeiro_adm;
    ImageView imageCadastraBarbeiro_adm;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    Map<String, Object> barbeiros;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_barbeiro_adm);

        iniciarComponentes();

        imageCadastraBarbeiro_adm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent upImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                upImage.setType("image/*");
                startActivityForResult(upImage, 0);
            }
        });
    }

    private void iniciarComponentes() {
        nomeCadastraBarbeiro_adm = findViewById(R.id.nomeCadastraBarbeiro_adm);
        emailCadastraBarbeiro_adm = findViewById(R.id.emailCadastraBarbeiro_adm);
        senhaCadastraBarbeiro_adm = findViewById(R.id.senhaCadastraBarbeiro_adm);

        imageCadastraBarbeiro_adm = findViewById(R.id.imageCadastraBarbeiro_adm);

        barbeiros = new HashMap<>();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public void CadastrarBarbeiro(View v){
        String emailNovoUser = emailCadastraBarbeiro_adm.getText().toString();
        String senhaNovoUser = senhaCadastraBarbeiro_adm.getText().toString();

        barbeiros.put("nome", nomeCadastraBarbeiro_adm.getText().toString());
        barbeiros.put("email", emailNovoUser);

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

                                                Picasso.get().load(urlPerfil).into(imageCadastraBarbeiro_adm);

                                                barbeiros.put("Perfil", urlPerfil);

                                                db.collection("funcionarios").document(mAuth.getUid()).set(barbeiros).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Toast.makeText(CadastroBarbeiroAdm.this, "Cadastro efetuado com sucesso!!", Toast.LENGTH_SHORT).show();
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

        Intent retornar = new Intent(this, TelaAdm.class);
        startActivity(retornar);
    }

    public void RetornarTelaADM(View v){
        Intent voltar = new Intent(this, TelaAdm.class);
        startActivity(voltar);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0){
            uri = data.getData();

            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imageCadastraBarbeiro_adm.setImageDrawable(new BitmapDrawable(bitmap));
            } catch (Exception e){
                Log.e("Erro no Upload", e.toString());
            }
        }
    }
}