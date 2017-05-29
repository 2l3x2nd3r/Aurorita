package com.example.aforce.proyecto1.Controllers.Rubric;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.aforce.proyecto1.ListAdapter;
import com.example.aforce.proyecto1.R;
import com.example.aforce.proyecto1.models.MyDatabase;
import com.example.aforce.proyecto1.models.Rubric;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by AForce on 24/03/2017.
 */

public class RubricsView extends Fragment {
    private FirebaseDatabase db;
    CardView cv;
    ListView lv;
    ListAdapter adapter;
    ArrayList<Object> records;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Rubricas");
        db = FirebaseDatabase.getInstance();
        cv = (CardView) view.findViewById(R.id.cvNoContent);
        lv = (ListView) view.findViewById(R.id.lvRubrics);
        records = new ArrayList<>();
        final DatabaseReference dbReference = db.getReference(MyDatabase.RUBRICAS);
        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Rubric r = postSnapshot.getValue(Rubric.class);
                    r.id = postSnapshot.getKey();
                    records.add(r);
                }
                if(records.isEmpty())
                    cv.setVisibility(View.VISIBLE);
                adapter = new ListAdapter(getContext(), records);
                lv.setDivider(null);
                lv.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.rubrics_view, container, false);
    }

}
