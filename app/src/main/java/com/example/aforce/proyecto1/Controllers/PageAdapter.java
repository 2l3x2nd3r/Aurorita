package com.example.aforce.proyecto1.Controllers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.aforce.proyecto1.Controllers.Activity.ActivitiesView;
import com.example.aforce.proyecto1.Controllers.Student.StudentsView;

/**
 * Created by mauricio on 17/04/17.
 */

public class PageAdapter extends FragmentStatePagerAdapter {
    private int ntabs;
    private int itemId;

    public PageAdapter (FragmentManager fm, int ntabs, int itemId){
        super(fm);
        this.ntabs = ntabs;
        this.itemId = itemId;
    }

    @Override
    public Fragment getItem(int position) {
        //Recibir el itemId de afuera
        Bundle bundle = new Bundle();
        bundle.putInt("itemId", itemId);

        switch (position){
            case 0:
                StudentsView sv = new StudentsView();
                sv.setArguments(bundle);
                return sv;

            case 1:
                ActivitiesView av = new ActivitiesView();
                av.setArguments(bundle);
                return av;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return ntabs;
    }
}
