package com.example.aforce.proyecto1.Controllers.Course;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aforce.proyecto1.R;

/**
 * Created by mauricio on 17/04/17.
 */

public class StudentsView extends Fragment{

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int itemId = getArguments().getInt("itemId");
        Log.d("Id:", String.valueOf(itemId));
        return inflater.inflate(R.layout.students_view, container, false);
    }
}
