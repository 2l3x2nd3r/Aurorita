package com.example.aforce.proyecto1.Controllers.Rubric;

import android.content.Context;
import android.net.Uri;
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
import com.example.aforce.proyecto1.models.MyDatabase;
import com.example.aforce.proyecto1.models.Rubric;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ShowRubricView extends Fragment {

    private FirebaseDatabase db;
    Rubric r;
    CardView cv;
    ListView lv;
    ListAdapter adapter;
    String rubricId;
    ArrayList<Object> categories;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = FirebaseDatabase.getInstance();
        final DatabaseReference dbRubrics = db.getReference(MyDatabase.RUBRICAS);
        final DatabaseReference dbCategories = db.getReference(MyDatabase.CATEGORIAS);
        rubricId = getArguments().getString("rubricId");
        cv = (CardView) view.findViewById(R.id.cvNoContent);
        lv = (ListView) view.findViewById(R.id.lvCategories);

        dbRubrics.child(rubricId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                r = dataSnapshot.getValue(Rubric.class);
                r.id = rubricId;
                getActivity().setTitle(r.nombre);
                dbCategories.orderByChild("rubricaId").equalTo(r.id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        categories = new ArrayList<>();
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            Category c = postSnapshot.getValue(Category.class);
                            c.id = postSnapshot.getKey();
                            categories.add(c);
                        }
                        if(categories.isEmpty())
                            cv.setVisibility(View.VISIBLE);
                        adapter = new ListAdapter(getContext(), categories);
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.show_rubric_view, container, false);
    }


}
