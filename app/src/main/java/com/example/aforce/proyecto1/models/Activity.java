package com.example.aforce.proyecto1.models;

import com.orm.SugarRecord;

/**
 * Created by AForce on 24/03/2017.
 */

public class Activity extends SugarRecord {

    String name;
    int rubric_id;
    int course_id;

    public Activity() {
    }

    public Activity(String name, int rubric_id, int course_id) {
        this.name = name;
        this.rubric_id = rubric_id;
        this.course_id = course_id;
    }
}

