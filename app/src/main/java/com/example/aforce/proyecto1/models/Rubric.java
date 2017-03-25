package com.example.aforce.proyecto1.models;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by AForce on 24/03/2017.
 */

public class Rubric extends SugarRecord {

    int activity_id;
    String name;

    List<Category> categories() {
        return Category.find(Category.class, "rubric = ?", String.valueOf(getId()));
    }

    public Rubric() {
    }

    public Rubric(int activity_id, String name) {
        this.activity_id = activity_id;
        this.name = name;
    }
}
