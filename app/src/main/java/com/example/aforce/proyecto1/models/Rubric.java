package com.example.aforce.proyecto1.models;

/**
 * Created by AForce on 24/03/2017.
 */

public class Rubric{
    public String id;
    public String nombre;
    public int numeroDeCategorias;
    public int numeroDeNiveles;

    public Rubric() {
    }

    public Rubric(String nombre, int numeroDeCategorias, int numeroDeNiveles) {
        this.nombre = nombre;
        this.numeroDeCategorias = numeroDeCategorias;
        this.numeroDeNiveles = numeroDeNiveles;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
