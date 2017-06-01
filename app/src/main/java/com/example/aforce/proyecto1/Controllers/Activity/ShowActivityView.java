package com.example.aforce.proyecto1.Controllers.Activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.aforce.proyecto1.ListAdapter;
import com.example.aforce.proyecto1.R;
import com.example.aforce.proyecto1.models.Activity;
import com.example.aforce.proyecto1.models.Category;
import com.example.aforce.proyecto1.models.Element;
import com.example.aforce.proyecto1.models.Level;
import com.example.aforce.proyecto1.models.MyDatabase;
import com.example.aforce.proyecto1.models.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import static com.facebook.login.widget.ProfilePictureView.TAG;

public class ShowActivityView extends Fragment {

    private TextView tv;
    private ArrayList<Object> categorias;
    private ArrayList<Object> elementos;
    private ArrayList<Object> niveles;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        //For now I'm gonna filter from users

        String itemId = getArguments().getString("activityId");

        tv = (TextView) view.findViewById(R.id.ElpropioTextView);

        getActivity().setTitle("Actividades");
        //TODO: change USUARIOS TO ESTUDIANTES
        DatabaseReference dbActivityRef = firebaseDatabase.getReference(MyDatabase.ACTIVIDADES);
        final DatabaseReference dbCategoryRef = firebaseDatabase.getReference(MyDatabase.CATEGORIAS);
        final DatabaseReference dbElementRef = firebaseDatabase.getReference(MyDatabase.ELEMENTOS);
        final DatabaseReference dbLevelRef = firebaseDatabase.getReference(MyDatabase.NIVELES);
        dbActivityRef.child(itemId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final Activity activity = dataSnapshot.getValue(Activity.class);
                activity.id = dataSnapshot.getKey();

                dbCategoryRef.child(activity.rubric_id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final Category c = dataSnapshot.getValue(Category.class);
                        c.id = activity.rubric_id;
                        dbElementRef.orderByChild("categoriaId").equalTo(c.id).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                elementos = new ArrayList<Object>();
                                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                    final Element e = postSnapshot.getValue(Element.class);
                                    e.id = postSnapshot.getKey();
                                    dbLevelRef.orderByChild(e.id).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            niveles = new ArrayList<Object>();
                                            for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                                final Level l = postSnapshot.getValue(Level.class);
                                                l.id = postSnapshot.getKey();
                                                niveles.add(l);
                                            }
                                            e.niveles = niveles;
                                            elementos.add(e);
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }
                                c.elementos = elementos;
                                categorias.add(c);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });

                // CONTRAUIR EL LAYOUT CON LA PINCHE INFORMACION QUE ACABAMOS DE EXTRAER DE LA PÏNCHE BD EN FIREBAS

                for (Object c: categorias) {
                    Category cat = (Category) c;
                }


            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.show_activity_view, container, false);
    }
}
