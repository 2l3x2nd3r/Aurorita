package com.example.aforce.proyecto1.models;


import java.util.ArrayList;

/**
 * Created by AForce on 24/03/2017.
 */

public class Element{

    public String id;
    public String nombre;
    public String categoriaId;
    public int porcentaje;
    public ArrayList<Object> niveles;

    public Element() {
    }

    public Element(String nombre, String categoriaId, int porcentaje) {
        this.nombre = nombre;
        this.categoriaId = categoriaId;
        this.porcentaje = porcentaje;
    }
}
