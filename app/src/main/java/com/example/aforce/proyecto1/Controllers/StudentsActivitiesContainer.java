package com.example.aforce.proyecto1.Controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aforce.proyecto1.R;

/**
 * Created by movil on 4/3/17.
 */

public class StudentsActivitiesContainer extends Fragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("nombre del curso");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View inflatedView = inflater.inflate(R.layout.students_activities_container_view, container, false);

        String itemId = getArguments().getString("itemId");

        TabLayout tabLayout = (TabLayout) inflatedView.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Estudiantes"));
        tabLayout.addTab(tabLayout.newTab().setText("Actividades"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) inflatedView.findViewById(R.id.pager);
        final PageAdapter adapter = new PageAdapter(getFragmentManager(), tabLayout.getTabCount(), itemId);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return inflatedView;
    }
}
