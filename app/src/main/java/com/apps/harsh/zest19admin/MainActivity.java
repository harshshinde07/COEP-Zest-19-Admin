package com.apps.harsh.zest19admin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences mSharedPreferences;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String mUsername, name;
    CardView live, update, champ, schedule, notif, focused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        name = mSharedPreferences.getString("name", null);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null && name == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            if(name == null) {
                mUsername = mFirebaseUser.getDisplayName();
            } else if(mFirebaseUser == null) {
                mUsername = name;
            }
            editor.putString("user", mUsername).apply();
            Toast.makeText(this, mUsername, Toast.LENGTH_SHORT).show();
        }
        if(!isNetworkAvailable()){
            Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_LONG).show();

        }
        update = findViewById(R.id.card_viewUpdate);
        champ = findViewById(R.id.card_viewChampionship);
        schedule = findViewById(R.id.card_viewSchedule);
        focused = findViewById(R.id.card_viewFocused);

        update.setOnClickListener(this);
        champ.setOnClickListener(this);
        schedule.setOnClickListener(this);
        focused.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out:
                mFirebaseAuth.signOut();
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.card_viewUpdate:
                startActivity(new Intent(MainActivity.this, Update.class));
                break;
            case R.id.card_viewChampionship:
                startActivity(new Intent(MainActivity.this, Championship.class));
                break;
            case R.id.card_viewSchedule:
                startActivity(new Intent(MainActivity.this, Schedule.class));
                break;
            case R.id.card_viewFocused:
                startActivity(new Intent(MainActivity.this, Focused.class));
                break;
                default:
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }
}
