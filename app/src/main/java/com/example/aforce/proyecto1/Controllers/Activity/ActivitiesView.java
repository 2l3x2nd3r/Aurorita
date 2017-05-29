package com.example.aforce.proyecto1.Controllers.Activity;

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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mauricio on 17/04/17.
 */

public class ActivitiesView extends Fragment {

    CardView cv;
    ListView lv;
    ListAdapter adapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int itemId = getArguments().getInt("itemId");

        getActivity().setTitle("Curso");

        cv = (CardView) view.findViewById(R.id.cvNoContent);
        lv = (ListView) view.findViewById(R.id.lvActivities);

        ArrayList<Object> records = new ArrayList<>();


        if(records.isEmpty())
            cv.setVisibility(View.VISIBLE);


        adapter = new ListAdapter(getContext(), records);
        lv.setDivider(null);
        lv.setAdapter(adapter);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activities_view, container, false);
    }
}
