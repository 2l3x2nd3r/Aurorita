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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.aforce.proyecto1.Controllers.Rubric.CreateRubricView;
import com.example.aforce.proyecto1.ListAdapter;
import com.example.aforce.proyecto1.R;
import com.example.aforce.proyecto1.models.Activity;
import com.example.aforce.proyecto1.models.Category;
import com.example.aforce.proyecto1.models.Element;
import com.example.aforce.proyecto1.models.Level;
import com.example.aforce.proyecto1.models.MyDatabase;
import com.example.aforce.proyecto1.models.Rubric;
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

public class ShowActivityView extends Fragment implements AdapterView.OnItemSelectedListener {

    private TextView tv;
    private ArrayList<Object> categorias;
    private ArrayList<Object> elementos;
    private ArrayList<Object> niveles;
    ViewGroup layout;
    View me;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        //For now I'm gonna filter from users

        String itemId = getArguments().getString("activityId");

        //TODO: change USUARIOS TO ESTUDIANTES
        DatabaseReference dbActivityRef = firebaseDatabase.getReference(MyDatabase.ACTIVIDADES);
        final DatabaseReference dbCategoryRef = firebaseDatabase.getReference(MyDatabase.CATEGORIAS);
        final DatabaseReference dbElementRef = firebaseDatabase.getReference(MyDatabase.ELEMENTOS);
        final DatabaseReference dbLevelRef = firebaseDatabase.getReference(MyDatabase.NIVELES);

        dbActivityRef.child(itemId);
        dbActivityRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Activity activity = dataSnapshot.getValue(Activity.class);
                getActivity().setTitle(activity.name);
                activity.id = dataSnapshot.getKey();
                categorias = new ArrayList<Object>();
                dbCategoryRef.orderByChild("rubricaId").equalTo(activity.rubric_id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final Category c = dataSnapshot.getValue(Category.class);
                        c.id = activity.rubric_id;
                        dbElementRef.orderByChild("categoriaId").equalTo(c.id).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                c.elementos = new ArrayList<Object>();
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    final Element e = postSnapshot.getValue(Element.class);
                                    e.id = postSnapshot.getKey();
                                    dbLevelRef.orderByChild(e.id).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            niveles = new ArrayList<Object>();
                                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                                final Level l = postSnapshot.getValue(Level.class);
                                                l.id = postSnapshot.getKey();
                                                niveles.add(l);
                                            }
                                            e.niveles = niveles;
                                            c.elementos.add(e);
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                        }
                                    });
                                }
                                categorias.add(c);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

                // CONTRAUIR EL LAYOUT CON LA PINCHE INFORMACION QUE ACABAMOS DE EXTRAER DE LA PÏNCHE BD EN FIREBAS

                for (Object c : categorias) {
                    Category cat = (Category) c;
                    addQualify(cat);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        me = inflater.inflate(R.layout.show_activity_view, container, false);
        layout = (ViewGroup) me.findViewById(R.id.elementsToQualify);
        return me;
    }

    ArrayList<LinearLayout> lls = new ArrayList<>();
    private void addQualify(Category cat) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.qualify_row, null);
        lls.add(ll);
        TextView catName = (TextView) ll.findViewById(R.id.catName);
        catName.setText(cat.nombre);
        LinearLayout llElementContainer = (LinearLayout) ll.findViewById(R.id.elementRow);
        for (Object e:cat.elementos){
            Element ele = (Element) e;
            LinearLayout ll2 = (LinearLayout) inflater.inflate(R.layout.element_row, null);
            TextView eleName = (TextView) ll2.findViewById(R.id.eleName);
            eleName.setText(ele.nombre);

            Spinner eleSpinner = (Spinner) ll2.findViewById(R.id.eleSpinner);
            eleSpinner.setTag(ele.id);
            eleSpinner.setOnItemSelectedListener(this);

            ArrayAdapter<Object> dataAdapater = new ArrayAdapter<Object>(getContext(), android.R.layout.simple_spinner_item, ele.niveles);
            dataAdapater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            eleSpinner.setAdapter(dataAdapater);
            llElementContainer.addView(ll2);
        }
        layout.addView(ll);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
