package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.Producto.ProductoManager;
import com.google.android.material.textfield.TextInputEditText;

public class AgregarProducto extends AppCompatActivity {
    TextInputEditText nombreEditText;
    TextInputEditText cantidadEditText;
    TextInputEditText costoEditText;
    TextInputEditText ventaEditText;
    TextInputEditText vendidosEditText;
    ProductoManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);

        nombreEditText = findViewById(R.id.nombre);
        cantidadEditText = findViewById(R.id.cantidad);
        costoEditText = findViewById(R.id.costo);
        ventaEditText = findViewById(R.id.venta);
        vendidosEditText = findViewById(R.id.vendidos);
        manager = new ProductoManager(this);

        Button botonRegresar = findViewById(R.id.button1);
        botonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AgregarProducto.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button botonAumentar = findViewById(R.id.button3);
        botonAumentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AgregarProducto.this, AumentarCantidad.class);
                startActivity(intent);
            }
        });


        Button botonAgregar = findViewById(R.id.button2);
        botonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String nombre = nombreEditText.getText().toString();
                    int cantidad = Integer.parseInt(cantidadEditText.getText().toString());
                    int costo = Integer.parseInt(costoEditText.getText().toString());
                    int venta = Integer.parseInt(ventaEditText.getText().toString());
                    int vendidos = Integer.parseInt(vendidosEditText.getText().toString());
                    manager.crearProducto(cantidad, vendidos, nombre, costo, venta);
                    Intent intent = new Intent(AgregarProducto.this, Confirmacion.class);
                    intent.putExtra("textoNuevo", "Producto " + nombre + " agregado exitosamente!");
                    startActivity(intent);
                }catch(Exception e){
                    Intent intent = new Intent(AgregarProducto.this, Error.class);
                    intent.putExtra("textoNuevo", "Error al agregar producto.");
                    startActivity(intent);
                }
            }
        });
    }
}