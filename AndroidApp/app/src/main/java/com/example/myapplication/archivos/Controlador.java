package com.example.myapplication.archivos;

import android.content.Context;

import com.example.myapplication.Producto.Producto;
import com.example.myapplication.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Controlador {
    private String archivo = "Datos.dat";
    private Context context;

    // El constructor ahora requiere un objeto Context
    public Controlador(Context context) {
        this.context = context;
    }

    public void guardarProductos(ArrayList<Producto> productos) {
        try (FileOutputStream fos = context.openFileOutput(archivo, Context.MODE_PRIVATE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(productos);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Producto> cargarProductos() {
        ArrayList<Producto> productos = new ArrayList<>();
        try (FileInputStream fis = context.openFileInput(archivo);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            Object obj = ois.readObject();
            if (obj instanceof ArrayList) {
                productos = (ArrayList<Producto>) obj;
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return productos;
    }

    public ArrayList<String> leerProductos() {
        ArrayList<String> productos = new ArrayList<>();

        try (InputStream raw = context.getResources().openRawResource(R.raw.productos);
             BufferedReader br = new BufferedReader(new InputStreamReader(raw))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Elimina el número y el punto al principio de cada línea
                String producto = linea.substring(linea.indexOf(' ') + 1).trim();
                productos.add(producto);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return productos;
    }
}
