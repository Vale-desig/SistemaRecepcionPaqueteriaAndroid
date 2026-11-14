package com.valerie.sistemarecepcionpaqueteria;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class EntregarPaqueteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_entregar_paquete);

        EditText campoResidente = findViewById(R.id.campo_residente);
        EditText campoApartamento = findViewById(R.id.campo_apartamento);
        EditText campoGuia = findViewById(R.id.campo_guia);
        EditText campoRecibe = findViewById(R.id.campo_recibe);

        Button btnConfirmar = findViewById(R.id.btn_confirmar_entrega);

        btnConfirmar.setOnClickListener(v -> {

            String residente = campoResidente.getText().toString().trim();
            String apartamento = campoApartamento.getText().toString().trim();
            String guia = campoGuia.getText().toString().trim();
            String recibe = campoRecibe.getText().toString().trim();

            if (residente.isEmpty()) {
                campoResidente.setError("Ingresa el nombre del residente");
                campoResidente.requestFocus();
            } else if (apartamento.isEmpty()) {
                campoApartamento.setError("Ingresa el número de apartamento");
                campoApartamento.requestFocus();
            } else if (guia.isEmpty()) {
                campoGuia.setError("Ingresa el número de guía");
                campoGuia.requestFocus();
            } else if (recibe.isEmpty()) {
                campoRecibe.setError("Ingresa el nombre de quien recibe");
                campoRecibe.requestFocus();
            } else {
                Toast.makeText(this, "Entrega confirmada correctamente", Toast.LENGTH_LONG).show();

                campoResidente.setText("");
                campoApartamento.setText("");
                campoGuia.setText("");
                campoRecibe.setText("");
                campoResidente.requestFocus();
            }
        });

        Button btnVolver = findViewById(R.id.btn_volver_menu);

        btnVolver.setOnClickListener(v -> {
            finish();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}