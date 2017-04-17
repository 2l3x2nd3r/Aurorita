package com.example.aforce.proyecto1.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by AForce on 24/03/2017.
 */

@Table(database = MyDatabase.class)
public class Category extends BaseModel{

    @PrimaryKey(autoincrement = true)
    long id;

    @Column
    public String name;

    @Column
    public int rubric_id;

    public Category() {
    }

    public Category(String name, int rubric_id) {

        this.name = name;
        this.rubric_id = rubric_id;
    }
}
