package com.example.aforce.proyecto1;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.aforce.proyecto1.models.Rubric;
import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by AForce on 24/03/2017.
 */

public class RubricView extends Fragment {

    CardView cv;
    ListView lv;
    ListAdapter adapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Rubricas");

        cv = (CardView) view.findViewById(R.id.cvNoContent);
        lv = (ListView) view.findViewById(R.id.lvRubrics);


            List<Rubric> list = Rubric.listAll(Rubric.class);
            SugarRecord records[] = new SugarRecord[list.size()];
            for (int i = 0; i < list.size(); i++) {
                records[i] = list.get(i);
            }
            if(list.isEmpty()){
                cv.setVisibility(View.VISIBLE);
                Rubric r = new Rubric("HOLA");
                r.save();
            }else{
                adapter = new ListAdapter(getContext(), records);
                lv.setDivider(null);
                lv.setAdapter(adapter);
            }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.rubric_view, container, false);
    }
}
