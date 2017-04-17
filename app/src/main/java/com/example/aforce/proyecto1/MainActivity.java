package com.example.aforce.proyecto1;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
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

import com.example.aforce.proyecto1.models.Course;
import com.orm.SugarContext;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FloatingActionButton fab;

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

        fab = (FloatingActionButton) findViewById(R.id.fab);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        SugarContext.init(this);
        displaySelectedScreen(R.id.nav_courses);
    }

    @Override
    public void onBackPressed() {
        fab.setVisibility(View.VISIBLE);
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

    public void bindAction(View view) {
        Toast.makeText(getApplicationContext(), "" + view.getTag(), Toast.LENGTH_SHORT).show();
        switch ("" + view.getTag()){
            case "newCourse":
                displaySelectedScreen(1);
                break;
            case "newRubric":
                
                break;
        }
    }

    private void displaySelectedScreen(int id){
        Fragment fragment = null;
        fab.setVisibility(View.VISIBLE);
        switch (id){
            case R.id.nav_courses:
                fab.setTag("newCourse");
                fragment = new CoursesView();
                break;
            case R.id.nav_rubrics:
                fab.setTag("newRubric");
                fragment = new RubricView();
                break;
            case 1: //CREAR CURSO
                fab.setVisibility(View.INVISIBLE);
                fragment = new CreateCourseView();
                break;
            case 2: //VER CURSO
                fab.setTag("showCourse");
                fragment = new ShowCourseView();
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

        displaySelectedScreen(id);

        return true;
    }

    public void showCard(View view) {
        String info[] = ((String) view.getTag()).split("-");

        switch (info[0]){
            case "Course":
                displaySelectedScreen(2);
                break;

            case "Rubric":

                break;
        }

    }

    public void onClickCreateCourse(View view) {
        EditText et = (EditText) findViewById(R.id.etCourseName);
        Course c = new Course(et.getText().toString());
        c.save();
    }
}
