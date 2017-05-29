package com.example.aforce.proyecto1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.aforce.proyecto1.models.MyDatabase;
import com.example.aforce.proyecto1.models.User;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private static final int RC_SIGN_IN = 0;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    Button btn;
    LinearLayout linearLoading;
    List<AuthUI.IdpConfig> providers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        btn = (Button) findViewById(R.id.btnLogin);
        linearLoading = (LinearLayout) findViewById(R.id.linearLoadind);
        providers = new ArrayList<>();
        if(mAuth.getCurrentUser() != null){
            afterLogin();
        }
    }

    @Override
    public void onBackPressed()
    {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.onBackPressed();
            return;
        }
        else { Toast.makeText(getBaseContext(), "Toca otra vez para salir", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            if(resultCode == RESULT_OK){
                Log.d("AUTH", mAuth.getCurrentUser().getEmail());
                afterLogin();
            }
        }
    }
    public int cont = 0;
    public void afterLogin(){
        linearLoading.setVisibility(View.VISIBLE);
        btn.setEnabled(false);
        final DatabaseReference dbReference = db.getReference(MyDatabase.USUARIOS);
        dbReference.orderByChild("email").equalTo(mAuth.getCurrentUser().getEmail()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User user = dataSnapshot.getValue(User.class);
                Intent i;
                if(user.role == "professor"){
                    // Iniciar actividad de profesor, obviamente no es MainActivity
                    i = new Intent(getApplicationContext(), MainActivity.class);
                }else{
                    i = new Intent(getApplicationContext(), MainActivity.class);
                }
                startActivity(i);
                finish();
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    public void login(View view) {
        providers.add(new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());
        startActivityForResult(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setTheme(R.style.FirebaseLoginTheme)
                .setLogo(R.drawable.iconoaurorita)
                .setIsSmartLockEnabled(false)
                .setProviders(providers)
                .build(), RC_SIGN_IN);
    }
}
