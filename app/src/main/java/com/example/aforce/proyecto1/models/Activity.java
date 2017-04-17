package com.example.aforce.proyecto1.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

/**
 * Created by AForce on 24/03/2017.
 */


@Table(database = MyDatabase.class)
public class Activity {

    @PrimaryKey (autoincrement = true)
    int id;

    @Column
    String name;

    @Column
    int rubric_id;

    @Column
    int course_id;

    public Activity() {
    }

    public Activity(String name, int rubric_id, int course_id) {
        this.name = name;
        this.rubric_id = rubric_id;
        this.course_id = course_id;
    }
}

