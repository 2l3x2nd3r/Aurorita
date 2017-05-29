package com.example.aforce.proyecto1.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by AForce on 24/03/2017.
 */
@IgnoreExtraProperties
public class Course {

    public String id;
    public String name;

    public Course() {
    }

    public Course(String name) {
        this.name = name;
    }

}
