package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.myapplication.Producto.Producto;
import com.example.myapplication.Producto.ProductoManager;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class ConsultarProducto extends AppCompatActivity {
    private TableLayout tableLayout;
    TextInputEditText cantidadEditText;
    TextInputEditText precioEditText;
    TextInputEditText vendidosEditText;
    ProductoManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_producto);

        tableLayout = findViewById(R.id.tabla);
        cantidadEditText = findViewById(R.id.cantidad);
        precioEditText = findViewById(R.id.precio);
        vendidosEditText = findViewById(R.id.vendidos);
        manager = new ProductoManager(this);

        llenarTabla();

        Button botonRegresar = findViewById(R.id.button1);
        botonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsultarProducto.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Button botonPrecio = findViewById(R.id.button2);
        botonPrecio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Producto> buscados = new ArrayList<>();
                try {

                    int precio = Integer.parseInt(precioEditText.getText().toString().trim());
                    buscados = manager.buscarPorPrecio(precio);
                    llenarTableLayout(buscados);
                }catch(Exception e){
                    Intent intent = new Intent(ConsultarProducto.this, Error.class);
                    intent.putExtra("textoNuevo", "Error al consultar producto.");
                    startActivity(intent);
                }
            }
        });
        Button botonCantidad = findViewById(R.id.button3);
        botonCantidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Producto> buscados = new ArrayList<>();
                try {

                    int cantidad = Integer.parseInt(cantidadEditText.getText().toString().trim());
                    buscados = manager.buscarPorCantidad(cantidad);
                    llenarTableLayout(buscados);
                }catch(Exception e){
                    Intent intent = new Intent(ConsultarProducto.this, Error.class);
                    intent.putExtra("textoNuevo", "Error al consultar producto.");
                    startActivity(intent);
                }
            }
        });
        Button botonVendidos = findViewById(R.id.button4);
        botonVendidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Producto> buscados = new ArrayList<>();
                try {
                    int vendidos = Integer.parseInt(vendidosEditText.getText().toString().trim());
                    buscados = manager.buscarPorVendidos(vendidos);
                    llenarTableLayout(buscados);
                }catch(Exception e){
                    Intent intent = new Intent(ConsultarProducto.this, Error.class);
                    intent.putExtra("textoNuevo", "Error al consultar producto.");
                    startActivity(intent);
                }
            }
        });
    }

    private void llenarTableLayout(ArrayList<Producto> buscados) {
        tableLayout.removeAllViews();
        ArrayList<String> strings = convertirAStrings(buscados);

        for (String str : strings) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            TextView textView = new TextView(this);
            textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            if(str!=null){
                textView.setText(str);
            }

            row.addView(textView);
            tableLayout.addView(row);
        }

    }

    private ArrayList<String> convertirAStrings(ArrayList<Producto> buscados){
        ArrayList<String> strings = new ArrayList<>();
        strings.add("ID Nombre\tCantidad\tCosto\tPrecio\tVendidos");
        if(buscados!=null){
            for(Producto producto : buscados){
                String string = producto.getId() + " " +
                        producto.getNombre() + " " +
                        producto.getCantidad() + " " +
                        producto.getCosto() + " " +
                        producto.getPrecio() + " " +
                        producto.getVendidos();
                strings.add(string);
            }
        }
        return strings;
    }

    private void llenarTabla(){
        ArrayList<Producto> buscados = new ArrayList<>();
        if(manager.getProductos()!=null && !manager.getProductos().isEmpty()) {
            for (int i = 0; i < manager.getProductos().size(); i++) {
                if (manager.getProductos().get(i) != null) {
                    buscados.add(manager.getProductos().get(i));
                }else{
                    break;
                }
            }
            llenarTableLayout(buscados);
        }
    }
}