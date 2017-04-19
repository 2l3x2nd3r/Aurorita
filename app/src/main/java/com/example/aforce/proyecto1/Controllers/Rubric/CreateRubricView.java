package com.example.aforce.proyecto1.Controllers.Rubric;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.aforce.proyecto1.R;

/**
 * Created by movil on 4/17/17.
 */

public class CreateRubricView extends Fragment {

    ViewGroup layout;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Crear Rubrica");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_rubric_view, container, false);
        layout = (ViewGroup) view.findViewById(R.id.categories);
        return view;
    }

    public void addLayout(LinearLayout ll){
        layout.addView(ll);
    }

    public void removeLayoutItems(){
        layout.removeAllViews();
    }
}
