package com.example.aforce.proyecto1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.aforce.proyecto1.Controllers.Activity.ShowActivityView;
import com.example.aforce.proyecto1.Controllers.Category.ShowCategoryView;
import com.example.aforce.proyecto1.Controllers.Course.CoursesView;
import com.example.aforce.proyecto1.Controllers.Activity.CreateActivityView;
import com.example.aforce.proyecto1.Controllers.Course.CreateCourseView;
import com.example.aforce.proyecto1.Controllers.Element.ShowElementView;
import com.example.aforce.proyecto1.Controllers.Student.CreateStudentView;
import com.example.aforce.proyecto1.Controllers.Student.StudentsView;
import com.example.aforce.proyecto1.Controllers.StudentsActivitiesContainer;
import com.example.aforce.proyecto1.Controllers.Rubric.CreateRubricView;
import com.example.aforce.proyecto1.Controllers.Rubric.RubricsView;
import com.example.aforce.proyecto1.Controllers.Rubric.ShowRubricView;
import com.example.aforce.proyecto1.models.Category;
import com.example.aforce.proyecto1.models.Course;
import com.example.aforce.proyecto1.models.Element;
import com.example.aforce.proyecto1.models.Level;
import com.example.aforce.proyecto1.models.MyDatabase;
import com.example.aforce.proyecto1.models.Rubric;
import com.example.aforce.proyecto1.models.Activity;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    MenuItem backMenu;
    boolean canCreateRubric = false;
    private String globalId;
    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = FirebaseDatabase.getInstance();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        displaySelectedScreen(R.id.nav_courses, "");
        //displaySelectedScreen(12, "-KlHv18J3ew3cKo5lmYV");
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
            else { Toast.makeText(getBaseContext(), "Presione nuevamente para salir", Toast.LENGTH_SHORT).show(); }

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
            int count = getSupportFragmentManager().getBackStackEntryCount();
            Log.d("Back", ""+count);
            if (count == 2) {
                getSupportFragmentManager().popBackStack();
                backMenu.setVisible(false);
            } else {
                getSupportFragmentManager().popBackStack();
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
    private void displaySelectedScreen(int viewId, String itemId){
        FragmentManager fm = getSupportFragmentManager();
        fragment = null;
        String tag = "noBack";
        Bundle bundle = null;
        int count = fm.getBackStackEntryCount();
        switch (viewId){
            case R.id.nav_courses:
                for(int i = 0; i < count; ++i) {
                    fm.popBackStack();
                }
                fragment = new CoursesView();
                try{backMenu.setVisible(false);}catch (Exception e){}
                break;
            case R.id.nav_rubrics:
                for(int i = 0; i < count; ++i) {
                    fm.popBackStack();
                }
                fragment = new RubricsView();
                try{backMenu.setVisible(false);}catch (Exception e){}
                break;
            case 1: //CREAR CURSO
                fragment = new CreateCourseView();
                tag = "Back";
                backMenu.setVisible(true);
                break;
            case 2: //VER CONTENEDOR ESTUDIANTES/ACTIVIDADES
                tag = "Back";
                backMenu.setVisible(true);
                globalId = itemId;
                bundle = new Bundle();
                bundle.putString("itemId", itemId);
                fragment = new StudentsActivitiesContainer();
                fragment.setArguments(bundle);
                break;
            case 3: //CREAR ESTUDIANTE
                tag = "Back";
                backMenu.setVisible(true);
                fragment = new CreateStudentView();
                break;
            case 4: //CREAR ACTIVIDAD
                tag = "Back";
                backMenu.setVisible(true);
                fragment = new CreateActivityView();
                break;
            case 11:
                fragment = new CreateRubricView();
                tag = "Back";
                backMenu.setVisible(true);
                break;
            case 12://VER RUBRICA
                backMenu.setVisible(true);
                tag = "Back";
                bundle = new Bundle();
                bundle.putString("rubricId", itemId);
                fragment = new ShowRubricView();
                fragment.setArguments(bundle);
                break;
            case 22://VER CATEGORIA
                backMenu.setVisible(true);
                tag = "Back";
                bundle = new Bundle();
                bundle.putString("categoryId", itemId);
                fragment = new ShowCategoryView();
                fragment.setArguments(bundle);
                break;
            case 32://VER ELEMENTO
                backMenu.setVisible(true);
                tag = "Back";
                bundle = new Bundle();
                bundle.putString("elementId", itemId);
                fragment = new ShowElementView();
                fragment.setArguments(bundle);
                break;
            case 42: //ACTIVIDAD
                backMenu.setVisible(true);
                tag = "Back";
                bundle = new Bundle();
                bundle.putString("activityId", itemId);
                fragment = new ShowActivityView();
                fragment.setArguments(bundle);
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

        displaySelectedScreen(id, "");

        return true;
    }

    public void displayItemFromList(View view) {
        String info[] = view.getTag().toString().split("\\|");
        Log.d("myInfo", info[1]);
        switch (info[0]){
            case "Course":
                displaySelectedScreen(2, info[1]);
                break;
            case "Rubric":
                displaySelectedScreen(12, info[1]);
                break;
            case "Category":
                displaySelectedScreen(22, info[1]);
                break;
            case "Element":
                displaySelectedScreen(32, info[1]);
                break;
            case "Activity":
                displaySelectedScreen(42, info[1]);
                break;
        }

    }

    //---------------------Display things---------------//

    public void onClickCreateCourseView(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 0, 50, 0);

        final EditText nombre = new EditText(this);
        nombre.setHint("Nombre");


        final TextInputLayout etWrapper = new TextInputLayout(this);
        etWrapper.addView(nombre);

        final Switch sw = new Switch(this);
        sw.setText("Visible");

        layout.addView(etWrapper);
        layout.addView(sw);

        alert.setMessage("Ingrese el nombre del curso");
        alert.setTitle("Crear curso");
        alert.setView(layout);
        alert.setPositiveButton("Crear", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String name = nombre.getText().toString();
                Course c = new Course(name, sw.isChecked());
                final DatabaseReference dbCourses = db.getReference(MyDatabase.CURSOS);
                dbCourses.push().setValue(c);
            }
        });
        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {}
        });
        alert.show();
    }

    public void onClickCreateRubricView(View view) {
        displaySelectedScreen(11, "");
    }

    public void onClickCreateStudentView(View view) {
        displaySelectedScreen(3, "");
    }

    public void onClickCreateActividadView(View view) {
        displaySelectedScreen(4, "");
    }
    //--------------------------------------------------//

    //---------------------Create things----------------//

    public void onClickCreateCourse(View view) {
        EditText et = (EditText) findViewById(R.id.etCourseName);
        Switch sw = (Switch) findViewById(R.id.swVisible);
        Course c = new Course(et.getText().toString(), sw.isChecked());

        final DatabaseReference dbCourses = db.getReference(MyDatabase.CURSOS);
        String key = dbCourses.push().getKey();
        dbCourses.child(key).setValue(c);
        c.id = key;
        displaySelectedScreen(R.id.nav_courses, "");
        Toast.makeText(this, "Curso creado con éxito", Toast.LENGTH_SHORT).show();
    }

    public void onClickCreateStudent(View view) {
        EditText etStudentName = (EditText) findViewById(R.id.etStudentName);
        EditText etStudentState = (EditText) findViewById(R.id.etStudentState);
        displaySelectedScreen(2, globalId);
        Toast.makeText(this, "Estudiante creado con éxito", Toast.LENGTH_SHORT).show();
    }

    public void onClickCreateActividad(View view) {
        EditText etActivityName = (EditText) findViewById(R.id.etActivityName);
        Spinner spRubrics = (Spinner) findViewById(R.id.spRubrics);
        final DatabaseReference dbActivities = db.getReference(MyDatabase.ACTIVIDADES);
        String key = dbActivities.push().getKey();

        Rubric r = (Rubric) spRubrics.getSelectedItem();
        Activity a = new Activity(etActivityName.getText().toString(), r.id, globalId);
        a.id = key;
        dbActivities.child(key).setValue(a);

        displaySelectedScreen(2, globalId);
        Toast.makeText(this, "Actividad creada con éxito", Toast.LENGTH_SHORT).show();
    }

    public void onClickCreateRubric(View view) {
        if(canCreateRubric) {
            final DatabaseReference dbRubrics = db.getReference(MyDatabase.RUBRICAS);
            final DatabaseReference dbCategories = db.getReference(MyDatabase.CATEGORIAS);
            String nam = ((EditText) findViewById(R.id.crvEt1)).getText().toString();
            int cats = Integer.parseInt(((EditText) findViewById(R.id.crvEt2)).getText().toString());
            int lvls = Integer.parseInt(((EditText) findViewById(R.id.crvEt3)).getText().toString());

            Rubric r = new Rubric(nam, cats, lvls);
            String key = dbRubrics.push().getKey();
            dbRubrics.child(key).setValue(r);
            r.id = key;

            for (int i = 0; i < lls.size(); i++) {
                String name = ((EditText) lls.get(i).findViewById(R.id.catrowet1)).getText().toString();
                EditText percent = (EditText) lls.get(i).findViewById(R.id.catrowet2);
                int porcentaje;
                try {
                    porcentaje = Integer.parseInt(percent.getText().toString());
                }catch (Exception e){
                    porcentaje = 25;
                }

                Category cat = new Category(name, r.id, porcentaje);
                dbCategories.push().setValue(cat);
            }
            displaySelectedScreen(R.id.nav_rubrics, "");
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
        for(int i = 0; i < quantity; i++){
            addCategory(i + 1);
        }
    }

    public void onClickCreateElementView(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText nombre = new EditText(this);
        nombre.setHint("Nombre");
        layout.addView(nombre);

        final EditText porcentaje = new EditText(this);
        porcentaje.setHint("Porcentaje");
        layout.addView(porcentaje);

        alert.setMessage("Ingrese el nombre del elemento");
        alert.setTitle("Crear Elemento");
        alert.setView(layout);
        alert.setPositiveButton("Crear", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String name = nombre.getText().toString();
                String percent = porcentaje.getText().toString();
                int porcentaje;
                try {
                    porcentaje = Integer.parseInt(percent);
                }catch (Exception e){
                    porcentaje = 25;
                }
                String id = ((ShowCategoryView) fragment).getCategoryId();
                Element e = new Element(name, id, porcentaje);
                final DatabaseReference dbElements = db.getReference(MyDatabase.ELEMENTOS);
                dbElements.push().setValue(e);
            }
        });
        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {}
        });
        alert.show();
    }

    public void onClickCreateLevelView(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText nombre = new EditText(this);
        nombre.setHint("Nombre");
        layout.addView(nombre);

        alert.setMessage("Ingrese el nombre del nivel");
        alert.setTitle("Crear nivel");
        alert.setView(layout);
        alert.setPositiveButton("Crear", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String name = nombre.getText().toString();
                String id = ((ShowElementView) fragment).getElementId();
                Level l = new Level(name, id);
                final DatabaseReference dbLevels = db.getReference(MyDatabase.NIVELES);
                dbLevels.push().setValue(l);
            }
        });
        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {}
        });

        if( ((ShowElementView) fragment).getLevelCount() == ShowRubricView.numeroDeNiveles )
            Toast.makeText(this, "Numero maximo de niveles creado", Toast.LENGTH_SHORT).show();
        else
            alert.show();
    }

    //--------------------------------------------------//
    ArrayList<LinearLayout> lls = new ArrayList<>();
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
