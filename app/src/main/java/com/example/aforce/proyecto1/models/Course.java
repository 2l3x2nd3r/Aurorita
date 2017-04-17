package com.example.aforce.proyecto1.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by AForce on 24/03/2017.
 */

@Table(database = MyDatabase.class)
public class Course extends BaseModel {

    @PrimaryKey (autoincrement = true)
    int id;

    @Column
    public String name;

    public Course() {
    }

    public Course(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

}
