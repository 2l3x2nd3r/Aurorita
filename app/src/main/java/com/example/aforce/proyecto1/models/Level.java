package com.example.aforce.proyecto1.models;

/**
 * Created by AForce on 24/03/2017.
 */

public class Level{

    public String id;
    public String nombre;
    public String elementoId;

    public Level() {
    }

    public Level(String nombre, String elementoId) {
        this.nombre = nombre;
        this.elementoId = elementoId;
    }

    @Override
    public String toString(){
        return nombre;
    }
}
