package com.example.aforce.proyecto1;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.aforce.proyecto1.models.Activity;
import com.example.aforce.proyecto1.models.Category;
import com.example.aforce.proyecto1.models.Course;
import com.example.aforce.proyecto1.models.Rubric;
import com.example.aforce.proyecto1.models.Student;

import java.util.ArrayList;

/**
 * Created by AForce on 26/03/2017.
 */

public class ListAdapter extends BaseAdapter{

    private Context context;
    ArrayList<Object> data;

    public ListAdapter(Context context, ArrayList<Object> data) {
        this.context = context;
        this.data = data;
    }

    public void setData(ArrayList<Object> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row, null);
        }

        TextView tvRow = (TextView) view.findViewById(R.id.tvRow);
        CardView cardRow = (CardView) view.findViewById(R.id.cardRow);

        Object sr = (Object) getItem(position);
        switch (sr.getClass().getSimpleName()){
            case "Rubric":
                Rubric Robj = (Rubric) sr;
                tvRow.setText(Robj.nombre);
                cardRow.setTag("Rubric|" + Robj.id);
                Log.d("myTag", "" + cardRow.getTag());
                break;
            case "Category":
                Category Caobj = (Category) sr;
                tvRow.setText(Caobj.nombre);
                cardRow.setTag("Category|" + Caobj.id);
                break;
            case "Course":
                Course Cobj = (Course) sr;
                tvRow.setText(""+Cobj.name);
                cardRow.setTag("Course|" + Cobj.id);
                break;
            case "Student":
                Student Sobj = (Student) sr;
                tvRow.setText(""+Sobj.name);
                cardRow.setTag("Student|" + Sobj.id);
                break;
            case "Activity":
                Activity Aobj = (Activity) sr;
                tvRow.setText(""+Aobj.name);
                cardRow.setTag("Activity|" +Aobj.getId());
        }

        return view;
    }
}
