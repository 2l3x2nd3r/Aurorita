package com.example.aforce.proyecto1.Controllers.Course;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by mauricio on 17/04/17.
 */

public class PageAdapter extends FragmentStatePagerAdapter {
    private int ntabs;

    public PageAdapter (FragmentManager fm, int ntabs){
        super(fm);
        this.ntabs = ntabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                StudentsView sv = new StudentsView();
                return sv;

            case 1:
                ActivitiesView av = new ActivitiesView();
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
