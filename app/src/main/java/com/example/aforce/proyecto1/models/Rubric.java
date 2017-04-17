package com.example.aforce.proyecto1.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by AForce on 24/03/2017.
 */

@Table(database = MyDatabase.class)
public class Rubric extends BaseModel{
    @PrimaryKey
    int id;

    @Column
    public String name;

    @Column
    public int categoryNumber;

    @Column
    public int levelNumber;

    public Rubric() {
    }

    public Rubric(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }
}
