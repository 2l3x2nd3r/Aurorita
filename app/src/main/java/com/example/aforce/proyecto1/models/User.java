package com.example.aforce.proyecto1.models;

import java.io.Serializable;

/**
 * Created by AForce on 27/05/2017.
 */

public class User implements Serializable {
    public String email;
    public String uid;
    public String role;

    public User(String email, String uid, String role) {
        this.email = email;
        this.uid = uid;
        this.role = role;
    }

    public User() {
    }
}
