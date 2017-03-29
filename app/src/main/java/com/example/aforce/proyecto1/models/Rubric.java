package com.example.aforce.proyecto1.models;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by AForce on 24/03/2017.
 */

public class Rubric extends SugarRecord {

    String name;

    List<Category> categories() {
        return Category.find(Category.class, "rubric = ?", String.valueOf(getId()));
    }

    public Rubric() {
    }

    public Rubric(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
