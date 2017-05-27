package com.example.aforce.proyecto1.models;

/**
 * Created by AForce on 24/03/2017.
 */

public class Rubric{
    int id;
    public String name;
    public int categoryNumber;
    public int levelNumber;

    public Rubric() {
    }

    public Rubric(String name, int categoryNumber, int levelNumber) {
        this.name = name;
        this.categoryNumber = categoryNumber;
        this.levelNumber = levelNumber;
    }

    public int getId() {
        return id;
    }
}
