package com.example.aforce.proyecto1.models;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by AForce on 24/03/2017.
 */

public class Element extends SugarRecord {

    String name;
    int category_id;

    Category category (){
        return Category.findById(Category.class, this.category_id);
    }

    List<Level> levels() {
        return Level.find(Level.class, "element = ?", String.valueOf(getId()));
    }

    public Element() {
    }

    public Element(String name, int category_id) {

        this.name = name;
        this.category_id = category_id;
    }
}
