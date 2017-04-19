package com.example.aforce.proyecto1.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

/**
 * Created by AForce on 24/03/2017.
 */

@Table(database = MyDatabase.class)
public class Category extends BaseModel{

    @PrimaryKey(autoincrement = true)
    int id;

    @Column
    public String name;

    @Column
    public int rubric_id;

    @Column
    public int elementNumber;

    @Column
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

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "elements")
    public List<Element> dbFlowOneTwoManyUtilMethod() {
        if (elements == null || elements.isEmpty()) {
            elements = SQLite.select()
                    .from(Element.class)
                    .where(Element_Table.category_id.eq(id))
                    .queryList();
        }
        return elements;
    }

}
