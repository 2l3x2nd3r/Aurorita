package com.example.aforce.proyecto1;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aforce.proyecto1.Controllers.Course.CoursesView;
import com.example.aforce.proyecto1.Controllers.Course.CreateCourseView;
import com.example.aforce.proyecto1.Controllers.Course.ShowCourseView;
import com.example.aforce.proyecto1.Controllers.Rubric.RubricsView;
import com.example.aforce.proyecto1.models.Course;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        FlowManager.init(new FlowConfig.Builder(this).openDatabasesOnInit(true).build());

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        displaySelectedScreen(R.id.nav_courses, 0);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            int count = getFragmentManager().getBackStackEntryCount();

            if (count == 0) {
                super.onBackPressed();
                //additional code
            } else {
                getFragmentManager().popBackStack();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displaySelectedScreen(int viewId, int itemId){
        Fragment fragment = null;
        switch (viewId){
            case R.id.nav_courses:
                fragment = new CoursesView();
                break;
            case R.id.nav_rubrics:
                fragment = new RubricsView();
                break;
            case 1: //CREAR CURSO
                fragment = new CreateCourseView();
                break;
            case 2: //VER CURSO
                fragment = new ShowCourseView();
                //buscar como pasar el itemId al fragment
                break;
            case 3:
                break;
            case 11:
                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                break;
        }

        if(fragment != null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main, fragment).addToBackStack("tag");
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        displaySelectedScreen(id, 0);

        return true;
    }

    public void displayItemFromList(View view) {
        String info[] = ((String) view.getTag()).split("-");

        switch (info[0]){
            case "Course":
                displaySelectedScreen(2, Integer.parseInt(info[1]));
                break;
            case "Rubric":

                break;
        }

    }

    //---------------------Display things---------------//

    public void onClickCreateCourseView(View view) {
        displaySelectedScreen(1, 0);
    }

    public void onClickCreateRubricView(View view) {
        displaySelectedScreen(11, 0);
    }
    //--------------------------------------------------//

    //---------------------Create things----------------//

    public void onClickCreateCourse(View view) {
        EditText et = (EditText) findViewById(R.id.etCourseName);
        Course c = new Course(et.getText().toString());
        c.save();
        displaySelectedScreen(R.id.nav_courses, 0);
    }


    //--------------------------------------------------//
}
