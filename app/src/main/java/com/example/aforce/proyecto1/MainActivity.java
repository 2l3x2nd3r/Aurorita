package com.example.aforce.proyecto1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.aforce.proyecto1.Controllers.Course.CoursesView;
import com.example.aforce.proyecto1.Controllers.Course.CreateActivityView;
import com.example.aforce.proyecto1.Controllers.Course.CreateCourseView;
import com.example.aforce.proyecto1.Controllers.Course.CreateStudentView;
import com.example.aforce.proyecto1.Controllers.Course.StudentsActivitiesContainer;
import com.example.aforce.proyecto1.Controllers.Rubric.CreateRubricView;
import com.example.aforce.proyecto1.Controllers.Rubric.RubricsView;
import com.example.aforce.proyecto1.models.Category;
import com.example.aforce.proyecto1.models.Course;
import com.example.aforce.proyecto1.models.Rubric;
import com.example.aforce.proyecto1.models.Activity;
import com.example.aforce.proyecto1.models.Student;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    MenuItem backMenu;
    boolean canCreateRubric = false;
    private int globalId = 0;

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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        displaySelectedScreen(R.id.nav_rubrics, 0);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
            {
                finish();
                return;
            }
            else { Toast.makeText(getBaseContext(), "Unde de nuevo para salir", Toast.LENGTH_SHORT).show(); }

            mBackPressed = System.currentTimeMillis();
        }

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        backMenu = menu.findItem(R.id.action_settings);
        backMenu.setVisible(false);
        return true;
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
            int count = getFragmentManager().getBackStackEntryCount();
            String tag = fragment.getTag();
            Log.d("back", tag);
            if (count == 0) {
                if(tag == "Back") {
                    super.onBackPressed();
                    backMenu.setVisible(false);
                }
                //additional code
            } else {
                getFragmentManager().popBackStack();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void logout(View view) {
        AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("AUTH", "LOGOUT");
            }
        });
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
        finish();
    }

    Fragment fragment;
    boolean sw = false;
    private void displaySelectedScreen(int viewId, int itemId){
        fragment = null;
        String tag = "noBack";

        if(sw)
            backMenu.setVisible(false);
        sw = true;
        Bundle bundle = null;
        switch (viewId){
            case R.id.nav_courses:
                fragment = new CoursesView();
                break;
            case R.id.nav_rubrics:
                fragment = new RubricsView();
                break;
            case 1: //CREAR CURSO
                fragment = new CreateCourseView();
                tag = "Back";
                backMenu.setVisible(true);
                break;
            case 2: //VER CONTENEDOR ESTUDIANTES/ACTIVIDADES
                globalId = itemId;
                bundle = new Bundle();
                bundle.putInt("itemId", itemId);
                fragment = new StudentsActivitiesContainer();
                fragment.setArguments(bundle);
                break;
            case 3: //CREAR ESTUDIANTE
                fragment = new CreateStudentView();
                break;
            case 4: //CREAR ACTIVIDAD
                fragment = new CreateActivityView();
                break;
            case 11:
                fragment = new CreateRubricView();
                tag = "Back";
                backMenu.setVisible(true);
                break;
        }

        if(fragment != null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main, fragment, tag).addToBackStack("tag");
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

    public void onClickCreateStudentView(View view) {
        displaySelectedScreen(3, 0);
    }

    public void onClickCreateActividadView(View view) {
        displaySelectedScreen(4, 0);
    }
    //--------------------------------------------------//

    //---------------------Create things----------------//

    public void onClickCreateCourse(View view) {
        EditText et = (EditText) findViewById(R.id.etCourseName);
        Course c = new Course(et.getText().toString());

        displaySelectedScreen(R.id.nav_courses, 0);
    }

    public void onClickCreateStudent(View view) {
        EditText etStudentName = (EditText) findViewById(R.id.etStudentName);
        EditText etStudentState = (EditText) findViewById(R.id.etStudentState);

        displaySelectedScreen(2, globalId);
    }

    public void onClickCreateActividad(View view) {
        EditText etActivityName = (EditText) findViewById(R.id.etActivityName);
        Activity a = new Activity(etActivityName.getText().toString(), 1, globalId);

        displaySelectedScreen(2, globalId);
    }

    public void onClickCreateRubric(View view) {
        if(canCreateRubric) {
            String nam = ((EditText) findViewById(R.id.crvEt1)).getText().toString();
            int cats = Integer.parseInt(((EditText) findViewById(R.id.crvEt2)).getText().toString());
            int lvls = Integer.parseInt(((EditText) findViewById(R.id.crvEt3)).getText().toString());

            Rubric r = new Rubric(nam, cats, lvls);

            for (int i = 0; i < lls.size(); i++) {
                String name = ((EditText) lls.get(i).findViewById(R.id.catrowet1)).getText().toString();
                int percent = Integer.parseInt(((EditText) lls.get(i).findViewById(R.id.catrowet2)).getText().toString());
                int elements = Integer.parseInt(((EditText) lls.get(i).findViewById(R.id.catrowet3)).getText().toString());

                //Category cat = new Category(name, r.getId(), percent, elements);

            }
            displaySelectedScreen(R.id.nav_rubrics, 0);
            Toast.makeText(this, "Rubrica creada con exito", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Debes agregar almenos una categoria", Toast.LENGTH_SHORT).show();
        }
    }

    public void addCategoryRow(View view){
        ((CreateRubricView) fragment).removeLayoutItems();
        EditText categoryNumber = (EditText) findViewById(R.id.crvEt2);
        String num = categoryNumber.getText().toString();
        int quantity;
        if(num.equals("")){
            quantity=0;
        }else{
            canCreateRubric = true;
            quantity=Integer.parseInt(num);
        }
        for(int i = 0; i < quantity; i++) {
            addCategory(i + 1);
        }
    }

    //--------------------------------------------------//
    ArrayList<LinearLayout> lls = new ArrayList<LinearLayout>();
    private void addCategory(int i) {
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.category_row, null);
        lls.add(ll);
        EditText catrowet1 = (EditText) ll.findViewById(R.id.catrowet1);
        catrowet1.setText("Categoria " + (i));
        //EditText catrowet2 = (EditText) ll.findViewById(R.id.catrowet2);
        //EditText catrowet3 = (EditText) ll.findViewById(R.id.catrowet3);
        ((CreateRubricView) fragment).addLayout(ll);
    }
}
