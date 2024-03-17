package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.myapplication.Producto.ProductoManager;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button botonAgregar = findViewById(R.id.button);
        botonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AgregarProducto.class);
                startActivity(intent);
            }
        });

        Button botonConsultar = findViewById(R.id.button2);
        botonConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ConsultarProducto.class);
                startActivity(intent);
            }
        });

        Button botonModificar = findViewById(R.id.button3);
        botonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ModificarProducto.class);
                startActivity(intent);
            }
        });

        Button botonEliminar = findViewById(R.id.button4);
        botonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EliminarProducto.class);
                startActivity(intent);
            }
        });

        ImageButton botonMenu = findViewById(R.id.menu);
        botonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "Botón de menú clickeado");
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

    }
}