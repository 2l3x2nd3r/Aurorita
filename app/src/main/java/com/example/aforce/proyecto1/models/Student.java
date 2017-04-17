package com.example.aforce.proyecto1.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by AForce on 24/03/2017.
 */

@Table(database = MyDatabase.class)
public class Student extends BaseModel{

    @PrimaryKey(autoincrement = true)
    int id;

    @Column
    public String name;

    @Column
    public int course_id;

    @Column
    public String state;

    public Student() {
    }

    public Student(String name, int course_id, String state) {
        this.name = name;
        this.course_id = course_id;
        this.state = state;
    }


}
