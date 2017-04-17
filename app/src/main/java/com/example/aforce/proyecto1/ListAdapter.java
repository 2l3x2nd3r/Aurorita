package com.example.aforce.proyecto1;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by AForce on 26/03/2017.
 */

public class ListAdapter extends BaseAdapter{

    private Context context;
    String data[];

    public ListAdapter(Context context, String[] data) {
        this.context = context;
        this.data = data;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
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
        /*
        SugarRecord sr = (SugarRecord) getItem(position);
        switch (sr.getClass().getSimpleName()){
            case "Rubric":
                Rubric Robj = (Rubric) sr;
                tvRow.setText(""+Robj.getName());
                cardRow.setTag(Robj.getId());
                break;
            case "Course":
                Course Cobj = (Course) sr;
                tvRow.setText(""+Cobj.getName());
                cardRow.setTag("Course-" + Cobj.getId());
                break;
        }*/

        return view;
    }
}
