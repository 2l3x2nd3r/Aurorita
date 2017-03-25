package com.example.aforce.proyecto1.models;

import com.orm.SugarRecord;

/**
 * Created by AForce on 24/03/2017.
 */

public class Level extends SugarRecord {

    String name;
    int element_id;

    Element element (){
        return Element.findById(Element.class, this.element_id);
    }

    public Level() {
    }

    public Level(String name, int element_id) {
        this.name = name;
        this.element_id = element_id;
    }
}
