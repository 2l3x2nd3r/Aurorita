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
import com.example.aforce.proyecto1.models.MyDatabase;
import com.example.aforce.proyecto1.models.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.facebook.login.widget.ProfilePictureView.TAG;

public class ShowActivityView extends Fragment {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private TextView tv;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //For now I'm gonna filter from users

        String itemId = getArguments().getString("activityId");

        tv = (TextView) view.findViewById(R.id.ElpropioTextView);

        getActivity().setTitle("Actividades");

        firebaseDatabase = FirebaseDatabase.getInstance();
        //TODO: change USUARIOS TO ESTUDIANTES
        databaseReference = firebaseDatabase.getReference(MyDatabase.ACTIVIDADES);
        databaseReference.orderByKey().equalTo(itemId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Activity a = dataSnapshot.getValue(Activity.class);
                a.id = dataSnapshot.getKey();
                tv.setText("Id: " + a.id + ", Course Id:" + a.course_id + ", Rubric Id: " + a.rubric_id + ", Name: " + a.name);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.show_activity_view, container, false);
    }
}
