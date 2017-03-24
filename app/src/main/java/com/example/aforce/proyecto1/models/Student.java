package com.example.aforce.proyecto1.models;

import com.orm.SugarRecord;

/**
 * Created by AForce on 24/03/2017.
 */

public class Student extends SugarRecord {

    String name;
    int course_id;
    String state;

    public Student() {
    }

    public Student(String name, int course_id, String state) {
        this.name = name;
        this.course_id = course_id;
        this.state = state;
    }


}
