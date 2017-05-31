package com.example.aforce.proyecto1.Controllers.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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

public class CreateActivityView extends Fragment implements AdapterView.OnItemSelectedListener {
    private FirebaseDatabase db;
    ArrayList<Rubric> rubrics;
    Spinner spinner;

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
                rubrics = new ArrayList<Rubric>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Rubric r = postSnapshot.getValue(Rubric.class);
                    r.id = postSnapshot.getKey();
                    rubrics.add(r);
                }

                if(!rubrics.isEmpty()) {
                    //Create adapter for spinner
                    ArrayAdapter<Rubric> dataAdapater = new ArrayAdapter<Rubric>(getContext(), android.R.layout.simple_spinner_item, rubrics);

                    //Drop down layout style
                    dataAdapater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    //Set adapter to spinner
                    spinner.setAdapter(dataAdapater);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.create_activity_view, container, false);

        spinner = (Spinner) v.findViewById(R.id.spRubrics);
        spinner.setOnItemSelectedListener(this);
        return v;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
