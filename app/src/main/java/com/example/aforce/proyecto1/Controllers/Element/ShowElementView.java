package com.example.aforce.proyecto1.Controllers.Element;

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
import com.example.aforce.proyecto1.models.Element;
import com.example.aforce.proyecto1.models.Level;
import com.example.aforce.proyecto1.models.MyDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by AForce on 30/05/2017.
 */

public class ShowElementView extends Fragment {

    private FirebaseDatabase db;
    Element e;
    CardView cv;
    ListView lv;
    ListAdapter adapter;
    String elementId;
    ArrayList<Object> levels;
    int numeroDeNiveles;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = FirebaseDatabase.getInstance();
        final DatabaseReference dbElementos = db.getReference(MyDatabase.ELEMENTOS);
        final DatabaseReference dbNiveles = db.getReference(MyDatabase.NIVELES);
        elementId = getArguments().getString("elementId");
        cv = (CardView) view.findViewById(R.id.cvNoContent);
        lv = (ListView) view.findViewById(R.id.lvLevels);

        dbElementos.child(elementId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                e = dataSnapshot.getValue(Element.class);
                e.id = elementId;
                getActivity().setTitle(e.nombre);
                dbNiveles.orderByChild("elementoId").equalTo(e.id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        levels = new ArrayList<>();
                        numeroDeNiveles = 0;
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            Level l = postSnapshot.getValue(Level.class);
                            l.id = postSnapshot.getKey();
                            levels.add(l);
                            numeroDeNiveles++;
                        }
                        if(levels.isEmpty())
                            cv.setVisibility(View.VISIBLE);
                        adapter = new ListAdapter(getContext(), levels);
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

    public String getElementId(){
        return elementId;
    }
    public int getLevelCount(){ return numeroDeNiveles;}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.show_element_view, container, false);
    }
}
