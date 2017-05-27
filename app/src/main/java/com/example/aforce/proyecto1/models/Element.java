package com.example.aforce.proyecto1.models;


/**
 * Created by AForce on 24/03/2017.
 */

public class Element{

    int id;
    public String name;
    public int category_id;


    public Element() {
    }

    public Element(String name, int category_id) {
        this.name = name;
        this.category_id = category_id;
    }
}
