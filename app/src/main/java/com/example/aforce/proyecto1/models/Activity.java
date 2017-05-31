package com.example.aforce.proyecto1.models;

/**
 * Created by AForce on 24/03/2017.
 */

public class Activity {

    public String id;
    public String name;
    public String rubric_id;
    public String course_id;

    public Activity() {
    }

    public Activity(String name, String rubric_id, String course_id) {
        this.name = name;
        this.rubric_id = rubric_id;
        this.course_id = course_id;
    }

    @Override
    public String toString() {
        return id + " " + name;
    }

    public String getId() {
        return id;
    }
}

