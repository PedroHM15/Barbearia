package com.example.barbeariasuper.BARBEIRO;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.barbeariasuper.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TelaHoraDataDisponiveisBarbeiro extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    CalendarView DataDisponivel_barbeiro;
    EditText horaDisponivel_barbeiro;
    ImageButton btn_voltarTelaBarbeiro;
    Map<String, Object> data;
    String dataSelecionada;
    String dataBanco;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_hora_data_disponiveis_barbeiro);

        iniciarComponentes();

        DataDisponivel_barbeiro.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                dataSelecionada = String.valueOf(dayOfMonth) + "/" + String.valueOf(month + 1);
                dataBanco = String.valueOf(dayOfMonth) + "_" + String.valueOf(month + 1);

                Log.e("dattaaa", dataSelecionada);

                data.put("Data", dataSelecionada);
            }
        });

    }

    private void iniciarComponentes() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        DataDisponivel_barbeiro = findViewById(R.id.DataDisponivel_barbeiro);
        horaDisponivel_barbeiro = findViewById(R.id.horaDisponivel_barbeiro);
        btn_voltarTelaBarbeiro = findViewById(R.id.btn_voltarTelaBarbeiro);

        data = new HashMap<>();
    }

    public void PegarData(View v){
        data.put("Horario", horaDisponivel_barbeiro.getText().toString());

        db.collection("funcionarios").document(mAuth.getUid()).collection("HorariosLivres").document(dataBanco).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(TelaHoraDataDisponiveisBarbeiro.this, "Horário Disponível", Toast.LENGTH_SHORT).show();

                Intent retornar = new Intent(TelaHoraDataDisponiveisBarbeiro.this, TelaBarbeiro.class);
                startActivity(retornar);
            }
        });
    }
    public void voltar(View v){
        Intent retornar = new Intent(TelaHoraDataDisponiveisBarbeiro.this, TelaBarbeiro.class);
        startActivity(retornar);
    }
}