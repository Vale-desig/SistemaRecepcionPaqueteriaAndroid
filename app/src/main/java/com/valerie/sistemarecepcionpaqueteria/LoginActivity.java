package com.valerie.sistemarecepcionpaqueteria;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        TextView btnVolver = findViewById(R.id.btn_volver);
        EditText campoCorreo = findViewById(R.id.campo_correo);
        EditText campoContrasena = findViewById(R.id.campo_contrasena);
        Button btnIngresar = findViewById(R.id.btn_ingresar);

        btnVolver.setOnClickListener(v -> finish());

        btnIngresar.setOnClickListener(v -> {

            String correo = campoCorreo.getText().toString().trim();
            String contrasena = campoContrasena.getText().toString().trim();

            if (correo.isEmpty()) {
                campoCorreo.setError("Ingresa tu correo electrónico");
                campoCorreo.requestFocus();
            } else if (contrasena.isEmpty()) {
                campoContrasena.setError("Ingresa tu contraseña");
                campoContrasena.requestFocus();
            } else {
                btnIngresar.setEnabled(false);
                btnIngresar.setText("Ingresando...");

                executor.execute(() -> iniciarSesion(correo, contrasena, btnIngresar));
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void iniciarSesion(String correo, String contrasena, Button btnIngresar) {

        HttpURLConnection conexion = null;

        try {
            URL url = new URL("http://192.168.0.107:8080/api/auth/login");
            conexion = (HttpURLConnection) url.openConnection();

            conexion.setRequestMethod("POST");
            conexion.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conexion.setRequestProperty("Accept", "application/json");
            conexion.setConnectTimeout(10000);
            conexion.setReadTimeout(10000);

            int codigo = conexion.getResponseCode();

            InputStream entrada = (codigo >= 200 && codigo < 300)
                    ? conexion.getInputStream()
                    : conexion.getErrorStream();

            StringBuilder respuesta = new StringBuilder();

            if (entrada != null) {
                try (BufferedReader lector = new BufferedReader(
                        new InputStreamReader(entrada, StandardCharsets.UTF_8))) {
                    String linea;
                    while ((linea = lector.readLine()) != null) {
                        respuesta.append(linea);
                    }
                }
            }

            if (codigo == HttpURLConnection.HTTP_OK) {
                JSONObject resultado = new JSONObject(respuesta.toString());

                runOnUiThread(() -> {
                    Toast.makeText(this, "Inicio de sesión correcto", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                    startActivity(intent);
                    finish();
                });

            } else {
                JSONObject resultado = new JSONObject(respuesta.toString());
                String mensaje = resultado.optString(
                        "mensaje",
                        "Correo o contraseña incorrectos."
                );

                runOnUiThread(() -> {
                    Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
                    btnIngresar.setEnabled(true);
                    btnIngresar.setText("Ingresar");
                });
            }

        } catch (Exception e) {

            runOnUiThread(() -> {
                Toast.makeText(
                        LoginActivity.this,
                        "Modo demostración: servidor no disponible",
                        Toast.LENGTH_LONG
                ).show();

                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            });

        } finally {
            if (conexion != null) {
                conexion.disconnect();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}