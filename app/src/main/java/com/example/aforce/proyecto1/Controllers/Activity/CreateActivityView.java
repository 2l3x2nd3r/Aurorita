package com.example.aforce.proyecto1.Controllers.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aforce.proyecto1.ListAdapter;
import com.example.aforce.proyecto1.R;
import com.example.aforce.proyecto1.models.MyDatabase;
import com.example.aforce.proyecto1.models.Rubric;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by mauricio on 19/04/17.
 */

public class CreateActivityView extends Fragment {
    private FirebaseDatabase db;
    ArrayList<Object> records;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Crear Actividad");

        db = FirebaseDatabase.getInstance();
        final DatabaseReference dbReference = db.getReference(MyDatabase.RUBRICAS);

        //NECESITAS ESTA LISTA, METES LOS ELEMENTOS DENTRO DE UNA SELECT LIST O ALGO ASI

        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Rubric r = postSnapshot.getValue(Rubric.class);
                    r.id = postSnapshot.getKey();
                    records.add(r);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_activity_view, container, false);
    }
}
