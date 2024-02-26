package com.example.myapplication.archivos;

import android.content.Context;

import com.example.myapplication.Producto.Producto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
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
}
