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


public class RegistrarPaqueteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar_paquete);

        EditText campoResidente = findViewById(R.id.campo_residente);
        EditText campoApartamento = findViewById(R.id.campo_apartamento);
        EditText campoTransportadora = findViewById(R.id.campo_transportadora);
        EditText campoGuia = findViewById(R.id.campo_guia);
        EditText campoDescripcion = findViewById(R.id.campo_descripcion);

        Button btnGuardar =findViewById(R.id.btn_guardar_paquete);

        btnGuardar.setOnClickListener(v -> {

            String residente = campoResidente.getText().toString().trim();
            String apartamento = campoApartamento.getText().toString().trim();
            String transportadora = campoTransportadora.getText().toString().trim();
            String guia = campoGuia.getText().toString().trim();
            String descripcion = campoDescripcion.getText().toString().trim();

            if (residente.isEmpty()) {
                campoResidente.setError("Ingresa el nombre del residente");
                campoResidente.requestFocus();
            } else if (apartamento.isEmpty()) {
                campoApartamento.setError("Ingresa el número de apartamento");
                campoApartamento.requestFocus();
            } else if (transportadora.isEmpty()) {
                campoTransportadora.setError("Ingresa la transportadora");
                campoTransportadora.requestFocus();
            } else if (guia.isEmpty()) {
                campoGuia.setError("Ingresa el número de guía");
                campoGuia.requestFocus();
            } else if (descripcion.isEmpty()) {
                campoDescripcion.setError("Ingresa la descripción del paquete");
                campoDescripcion.requestFocus();
            } else {
                Toast.makeText(this, "Paquete registrado correctamente", Toast.LENGTH_LONG).show();

                campoResidente.setText("");
                campoApartamento.setText("");
                campoTransportadora.setText("");
                campoGuia.setText("");
                campoDescripcion.setText("");
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