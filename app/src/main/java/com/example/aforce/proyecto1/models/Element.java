package com.example.aforce.proyecto1.models;

import com.orm.SugarRecord;

/**
 * Created by AForce on 24/03/2017.
 */

public class Element extends SugarRecord {

    String name;
    int category_id;

    public Element() {
    }

    public Element(String name, int category_id) {

        this.name = name;
        this.category_id = category_id;
    }
}
