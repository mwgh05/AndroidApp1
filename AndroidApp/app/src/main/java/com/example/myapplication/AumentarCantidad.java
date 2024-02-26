package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.Producto.ProductoManager;
import com.google.android.material.textfield.TextInputEditText;

public class AumentarCantidad extends AppCompatActivity {
    TextInputEditText idEditText;
    TextInputEditText cantidadEditText;
    ProductoManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aumentar_cantidad);

        idEditText = findViewById(R.id.id);
        cantidadEditText = findViewById(R.id.cantidad);
        manager = new ProductoManager(this);

        Button botonRegresar = findViewById(R.id.button2);
        botonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AumentarCantidad.this, AgregarProducto.class);
                startActivity(intent);
            }
        });
        Button botonAgregar = findViewById(R.id.button1);
        botonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    int id = Integer.parseInt(idEditText.getText().toString());
                    int cantidad = Integer.parseInt(cantidadEditText.getText().toString());
                    String nombre = manager.buscarPorId(id).getNombre();
                    manager.incrementarCantidad(id, cantidad);
                    Intent intent = new Intent(AumentarCantidad.this, Confirmacion.class);
                    intent.putExtra("textoNuevo", "Al producto " +String.valueOf(id)+ " " + nombre + " se le ha agregado" + String.valueOf(cantidad) + " unidades exitosamente!");
                    startActivity(intent);
                }catch(Exception e){
                    Intent intent = new Intent(AumentarCantidad.this, Error.class);
                    intent.putExtra("textoNuevo", "Error al agregar unidades.");
                    startActivity(intent);
                }
            }
        });
    }
}