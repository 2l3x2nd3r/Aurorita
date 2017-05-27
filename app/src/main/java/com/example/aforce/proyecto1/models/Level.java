package com.example.aforce.proyecto1.models;

/**
 * Created by AForce on 24/03/2017.
 */

public class Level{

    int id;
    public String name;
    public int element_id;

    public Level() {
    }

    public Level(String name, int element_id) {
        this.name = name;
        this.element_id = element_id;
    }
}
