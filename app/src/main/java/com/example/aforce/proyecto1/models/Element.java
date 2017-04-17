package com.example.aforce.proyecto1.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by AForce on 24/03/2017.
 */

@Table(database = MyDatabase.class)
public class Element extends BaseModel{

    @PrimaryKey(autoincrement = true)
    int id;

    @Column
    public String name;

    @Column
    public int category_id;


    public Element() {
    }

    public Element(String name, int category_id) {
        this.name = name;
        this.category_id = category_id;
    }
}
