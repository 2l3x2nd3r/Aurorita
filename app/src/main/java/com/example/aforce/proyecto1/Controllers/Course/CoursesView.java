package com.example.aforce.proyecto1.Controllers.Course;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * Created by AForce on 25/03/2017.
 */

public class CoursesView extends Fragment {

    private CardView cv;
    private ListView lv;
    private ListAdapter adapter;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(MyDatabase.CURSOS);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Object courses = dataSnapshot.getValue();
                if(courses != null) {
                    //Show every course
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });

        getActivity().setTitle("Cursos");

        cv = (CardView) view.findViewById(R.id.cvNoContent);
        lv = (ListView) view.findViewById(R.id.lvCourses);

        ArrayList<Object> records = new ArrayList<>();

        if(records.isEmpty())
            cv.setVisibility(View.VISIBLE);

        adapter = new ListAdapter(getContext(), records);
        lv.setDivider(null);
        lv.setAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.courses_view, container, false);
    }
}
