package com.example.aforce.proyecto1.models;

import com.orm.SugarRecord;

/**
 * Created by AForce on 24/03/2017.
 */

public class Course extends SugarRecord {

    String Name;

    public Course() {
    }

    public Course(String name) {
        Name = name;
    }
}
