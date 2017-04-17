package com.example.aforce.proyecto1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.aforce.proyecto1.models.Rubric;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AForce on 24/03/2017.
 */

public class RubricsView extends Fragment {

    CardView cv;
    ListView lv;
    ListAdapter adapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Rubricas");

        cv = (CardView) view.findViewById(R.id.cvNoContent);
        lv = (ListView) view.findViewById(R.id.lvRubrics);

        ArrayList<BaseModel> records = new ArrayList<BaseModel>();

        List<Rubric> list = new Select().from(Rubric.class).queryList();

        for (int i = 0; i < list.size(); i++){
            records.add(list.get(i));
        }

        if(records.isEmpty()){
            cv.setVisibility(View.VISIBLE);
        }
        adapter = new ListAdapter(getContext(), records);
        lv.setDivider(null);
        lv.setAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.rubrics_view, container, false);
    }

}
