package com.example.aforce.proyecto1.models;

/**
 * Created by AForce on 24/03/2017.
 */

public class Student{
    public int id;
    public String name;
    public String course_id;
    public String state;

    public Student() {
    }

    public Student(String name, String course_id, String state) {
        this.name = name;
        this.course_id = course_id;
        this.state = state;
    }

}
