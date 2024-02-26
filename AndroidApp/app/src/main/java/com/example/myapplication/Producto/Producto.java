package com.example.myapplication.Producto;

import java.io.Serializable;

public class Producto implements Serializable {
    private static final long serialVersionUID = 1L; // Recommended to include

    private int id;
    private int cantidad=0;
    private int vendidos=0;
    private String nombre;
    private int costo=0;
    private int precio=0;


    public Producto(int id, int cant, int vendidos, String nombre, int costo, int precio){
        this.id=id;
        this.cantidad=cant;
        this.vendidos=vendidos;
        this.nombre=nombre;
        this.costo=costo;
        this.precio=precio;
    }
    public int getId(){
        return this.id;
    }
    public int getPrecio() {
        return this.precio;
    }
    public int getCantidad(){
        return this.cantidad;
    }
    public int getVendidos(){
        return this.vendidos;
    }

    public int getCosto() {
        return costo;
    }

    public String getNombre() {
        return nombre;
    }

    public void aumentarCantidad(int n){
        this.cantidad+=n;
    }

    public void modificar(int costo, int precio, int cant){
        this.costo=costo;
        this.precio=precio;
        this.cantidad=cant;
    }
}
