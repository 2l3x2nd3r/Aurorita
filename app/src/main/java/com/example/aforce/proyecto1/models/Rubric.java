package com.example.aforce.proyecto1.models;

import com.orm.SugarRecord;

import java.util.ArrayList;

/**
 * Created by AForce on 24/03/2017.
 */

public class Rubric extends SugarRecord {

    int activity_id;

    public Rubric() {
    }

    public Rubric(int activity_id) {
        this.activity_id = activity_id;
    }
}
