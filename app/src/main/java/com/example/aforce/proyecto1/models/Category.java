package com.example.aforce.proyecto1.models;

import java.util.List;

/**
 * Created by AForce on 24/03/2017.
 */

public class Category{

    public String id;
    public String nombre;
    public String rubricaId;
    public int porcentaje;

    public Category() {
    }

    public Category(String nombre, String rubricaId, int porcentaje) {
        this.nombre = nombre;
        this.rubricaId = rubricaId;
        this.porcentaje = porcentaje;
    }

}
