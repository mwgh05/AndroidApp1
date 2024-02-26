package com.example.myapplication.Producto;

import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.example.myapplication.archivos.Controlador;

public class ProductoManager {
    private int idActual = 0;
    private ArrayList<Producto> productos;
    private Controlador controlador;

    public ProductoManager(Context context){
        this.controlador = new Controlador(context);
        this.productos = controlador.cargarProductos();
        if(productos!=null && !productos.isEmpty()) {
            this.idActual = productos.get(productos.size()-1).getId();
        }
    }

    public ArrayList<Producto> getProductos(){
        return productos;
    }
    public void crearProducto(int cant, int vendidos, String nombre, int costo, int precio){
        productos=controlador.cargarProductos();
        idActual++;
        productos.add(new Producto(idActual, cant, vendidos, nombre, costo, precio));
        controlador.guardarProductos(productos);
    }
    public void incrementarCantidad(int id, int n){
        productos=controlador.cargarProductos();
        Producto prod;
        prod=buscarPorId(id);
        prod.aumentarCantidad(n);
        controlador.guardarProductos(productos);
    }
    public Producto buscarPorId(int id){
        productos=controlador.cargarProductos();
        if(productos!=null) {
            for (Producto producto : productos) {
                if(producto.getId() == id){
                    return producto;
                }
            }
            return null;
        }
        return null;
    }
    public ArrayList<Producto> buscarPorNombre(String nombre){
        productos=controlador.cargarProductos();
        ArrayList<Producto> buscados=new ArrayList<>();
        if(productos!=null) {
            for (Producto producto : productos) {
                if(producto.getNombre().equals(nombre.trim())){
                    buscados.add(producto);
                }
            }
            return buscados;
        }
        return null;
    }
    public ArrayList<Producto> buscarPorPrecio(int precio){
        productos=controlador.cargarProductos();
        ArrayList<Producto> buscados = new ArrayList<>();
        if(productos!=null){
            for(Producto producto : productos){
                if(producto.getPrecio()==precio){
                    buscados.add(producto);
                }
            }
            return buscados;
        }
        return null;
    }
    public ArrayList<Producto> buscarPorCantidad(int cantidad){
        productos=controlador.cargarProductos();
        ArrayList<Producto> buscados = new ArrayList<>();
        if(productos!=null){
            for(Producto producto : productos){
                if(producto.getCantidad()==cantidad){
                    buscados.add(producto);
                }
            }
            return buscados;
        }
        return null;
    }
    public ArrayList<Producto> buscarPorVendidos(int vendidos){
        productos=controlador.cargarProductos();
        ArrayList<Producto> buscados = new ArrayList<>();
        if(productos!=null){
            for(Producto producto : productos){
                if(producto.getVendidos()==vendidos){
                    buscados.add(producto);
                }
            }
            return buscados;
        }
        return null;
    }
    public void modificarProducto(int id, int costo, int precio, int cant){
        productos=controlador.cargarProductos();
        Producto producto;
        producto = buscarPorId(id);
        producto.modificar(costo, precio, cant);
        controlador.guardarProductos(productos);
    }
    public void eliminarPorId(int id){
        productos=controlador.cargarProductos();
        Producto prod;
        prod = buscarPorId(id);
        productos.remove(prod);
        controlador.guardarProductos(productos);
    }
    public void eliminarPorNombre(String nombre){
        productos=controlador.cargarProductos();
        ArrayList<Producto> buscados=buscarPorNombre(nombre.trim());
        for(Producto producto : buscados){
            productos.remove(producto);
        }
        controlador.guardarProductos(productos);
    }
}
