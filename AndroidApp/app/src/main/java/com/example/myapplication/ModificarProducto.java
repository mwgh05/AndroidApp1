package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.Producto.ProductoManager;
import com.google.android.material.textfield.TextInputEditText;

public class ModificarProducto extends AppCompatActivity {
    TextInputEditText idEditText;
    TextInputEditText cantidadEditText;
    TextInputEditText costoEditText;
    TextInputEditText ventaEditText;
    ProductoManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_producto);

        idEditText = findViewById(R.id.id);
        cantidadEditText = findViewById(R.id.cantidad);
        costoEditText = findViewById(R.id.costo);
        ventaEditText = findViewById(R.id.venta);
        manager = new ProductoManager(this);

        Button botonRegresar = findViewById(R.id.button2);
        botonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModificarProducto.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Button botonModificar = findViewById(R.id.button1);
        botonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int id = Integer.parseInt(idEditText.getText().toString());
                    int cantidad = Integer.parseInt(cantidadEditText.getText().toString());
                    int costo = Integer.parseInt(costoEditText.getText().toString());
                    int venta = Integer.parseInt(ventaEditText.getText().toString());
                    String nombre = manager.buscarPorId(id).getNombre();
                    manager.modificarProducto(id,costo,venta,cantidad);
                    Intent intent = new Intent(ModificarProducto.this, Confirmacion.class);
                    intent.putExtra("textoNuevo", "Producto " +String.valueOf(id)+ " " + nombre + " modificado exitosamente!");
                    startActivity(intent);
                }catch(Exception e){
                    Intent intent = new Intent(ModificarProducto.this, Error.class);
                    intent.putExtra("textoNuevo", "Error al modificar producto.");
                    startActivity(intent);
                }
            }
        });
    }
}