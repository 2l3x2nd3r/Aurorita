package com.example.aforce.proyecto1.models;

/**
 * Created by mauri on 1/06/2017.
 */

public class Curso_Usuario {
    public String cursoId;
    public String state;
    public String usuarioId;

    public Curso_Usuario() {

    }

    public Curso_Usuario(String cursoId, String state, String usuarioId) {
        this.cursoId = cursoId;
        this.state = state;
        this.usuarioId = usuarioId;
    }
}
