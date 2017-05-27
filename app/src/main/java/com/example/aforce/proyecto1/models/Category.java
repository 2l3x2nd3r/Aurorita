package com.example.aforce.proyecto1.models;

import java.util.List;

/**
 * Created by AForce on 24/03/2017.
 */

public class Category{

    int id;
    public String name;
    public int rubric_id;
    public int elementNumber;
    public int percent;

    List<Element> elements;

    public Category() {
    }

    public Category(String name, int rubric_id, int elementNumber, int percent) {
        this.name = name;
        this.rubric_id = rubric_id;
        this.elementNumber = elementNumber;
        this.percent = percent;
    }

    public List<Element> dbFlowOneTwoManyUtilMethod() {
        if (elements == null || elements.isEmpty()) {
        }
        return elements;
    }

}
