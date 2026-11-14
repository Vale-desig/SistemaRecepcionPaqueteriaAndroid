package com.valerie.sistemarecepcionpaqueteria;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);

        Button btnCerrarSesion = findViewById(R.id.btn_cerrar_sesion);

        btnCerrarSesion.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        Button btnConsultar =findViewById(R.id.btn_consultar_paquetes);

        btnConsultar.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, ConsultarPaquetesActivity.class);
            startActivity(intent);
        });

        Button btnRegistrar = findViewById(R.id.btn_registrar_paquete);

        btnRegistrar.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, RegistrarPaqueteActivity.class);
            startActivity(intent);
        });

        Button btnEntregar = findViewById(R.id.btn_entregar_paquete);

        btnEntregar.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, EntregarPaqueteActivity.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}