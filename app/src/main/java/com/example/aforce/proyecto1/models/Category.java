package com.example.aforce.proyecto1.models;

import com.orm.SugarRecord;

/**
 * Created by AForce on 24/03/2017.
 */

public class Category extends SugarRecord {

    String name;
    int rubric_id;

    public Category() {
    }

    public Category(String name, int rubric_id) {

        this.name = name;
        this.rubric_id = rubric_id;
    }
}
