package com.example.aforce.proyecto1.Controllers.Category;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.aforce.proyecto1.ListAdapter;
import com.example.aforce.proyecto1.R;
import com.example.aforce.proyecto1.models.Category;
import com.example.aforce.proyecto1.models.Element;
import com.example.aforce.proyecto1.models.MyDatabase;
import com.example.aforce.proyecto1.models.Rubric;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by AForce on 29/05/2017.
 */

public class ShowCategoryView extends Fragment {

    private FirebaseDatabase db;
    Category c;
    CardView cv;
    ListView lv;
    ListAdapter adapter;
    String categoryId;
    ArrayList<Object> elements;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = FirebaseDatabase.getInstance();
        final DatabaseReference dbCategories = db.getReference(MyDatabase.CATEGORIAS);
        final DatabaseReference dbElementos = db.getReference(MyDatabase.ELEMENTOS);
        categoryId = getArguments().getString("categoryId");
        cv = (CardView) view.findViewById(R.id.cvNoContent);
        lv = (ListView) view.findViewById(R.id.lvCategories);

        dbCategories.child(categoryId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                c = dataSnapshot.getValue(Category.class);
                c.id = categoryId;
                getActivity().setTitle(c.nombre);
                dbElementos.orderByChild("categoriaId").equalTo(c.id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        elements = new ArrayList<>();
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            Element e = postSnapshot.getValue(Element.class);
                            e.id = postSnapshot.getKey();
                            elements.add(e);
                        }
                        if(elements.isEmpty())
                            cv.setVisibility(View.VISIBLE);
                        adapter = new ListAdapter(getContext(), elements);
                        lv.setDivider(null);
                        lv.setAdapter(adapter);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    public String getCategoryId(){
        return categoryId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.show_category_view, container, false);
    }
}
