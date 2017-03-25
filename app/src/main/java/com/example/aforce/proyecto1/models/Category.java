package com.example.aforce.proyecto1.models;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by AForce on 24/03/2017.
 */

public class Category extends SugarRecord {

    String name;
    int rubric_id;

    Rubric rubric (){
        return Rubric.findById(Rubric.class, this.rubric_id);
    }

    List<Element> elements() {
        return Element.find(Element.class, "category = ?", String.valueOf(getId()));
    }

    public Category() {
    }

    public Category(String name, int rubric_id) {

        this.name = name;
        this.rubric_id = rubric_id;
    }
}
