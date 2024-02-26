package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Error extends AppCompatActivity {
    TextView mensaje;
    String nuevo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        mensaje = findViewById(R.id.mensaje);

        nuevo = getIntent().getStringExtra("textoNuevo");

        if (nuevo != null) {
            mensaje.setText(nuevo);
        }

        Button botonRegresar = findViewById(R.id.button1);
        botonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Error.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}