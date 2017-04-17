package com.example.aforce.proyecto1.models;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by UTA2 on 17/04/17.
 */

@Database(name = MyDatabase.NAME, version = MyDatabase.VERSION)
public class MyDatabase {
    public static final String NAME = "MyDataBase";

    public static final int VERSION = 1;
}
