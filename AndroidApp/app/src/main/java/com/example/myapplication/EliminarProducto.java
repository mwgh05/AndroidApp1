package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.Producto.ProductoManager;
import com.google.android.material.textfield.TextInputEditText;

public class EliminarProducto extends AppCompatActivity {
    TextInputEditText idEditText;
    TextInputEditText nombreEditText;
    ProductoManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_producto);

        nombreEditText = findViewById(R.id.nombre);
        idEditText = findViewById(R.id.id);
        manager = new ProductoManager(this);

        Button botonRegresar = findViewById(R.id.button1);
        botonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EliminarProducto.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button botonPorId = findViewById(R.id.button2);
        botonPorId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int id = Integer.parseInt(idEditText.getText().toString());
                    String nombre = manager.buscarPorId(id).getNombre();
                    manager.eliminarPorId(id);
                    Intent intent = new Intent(EliminarProducto.this, Confirmacion.class);
                    intent.putExtra("textoNuevo", "Producto " +String.valueOf(id)+ " " + nombre + " eliminado exitosamente!");
                    startActivity(intent);
                }catch(Exception e){
                    Intent intent = new Intent(EliminarProducto.this, Error.class);
                    intent.putExtra("textoNuevo", "Error al eliminar producto.");
                    startActivity(intent);
                }
            }
        });

        Button botonPorNombre = findViewById(R.id.button3);
        botonPorNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    String nombre = nombreEditText.getText().toString();
                    manager.eliminarPorNombre(nombre);
                    Intent intent = new Intent(EliminarProducto.this, Confirmacion.class);
                    intent.putExtra("textoNuevo", "Producto " + nombre + " eliminado exitosamente!");
                    startActivity(intent);
                }catch(Exception e){
                    Intent intent = new Intent(EliminarProducto.this, Error.class);
                    intent.putExtra("textoNuevo", "Error al eliminar producto.");
                    startActivity(intent);
                }
            }
        });
    }
}