package com.apps.harsh.zest19admin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Championship extends AppCompatActivity {

    private static final String COLLEGE1_KEY = "College 1";
    private static final String POINT1_KEY = "Point 1";
    private static final String COLLEGE2_KEY = "College 2";
    private static final String POINT2_KEY = "Point 2";
    private static final String COLLEGE3_KEY = "College 3";
    private static final String POINT3_KEY = "Point 3";
    private static final String COLLEGE4_KEY = "College 4";
    private static final String POINT4_KEY = "Point 4";
    private static final String COLLEGE5_KEY = "College 5";
    private static final String POINT5_KEY = "Point 5";
    FirebaseFirestore db;
    private SharedPreferences mSharedPreferences;
    String clg1, clg2, clg3, clg4, clg5, point1, point2, point3, point4, point5, username;
    EditText p1, p2, p3, p4, p5;
    EditText c1, c2, c3, c4, c5;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_championship);

        db = FirebaseFirestore.getInstance();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        c1 = findViewById(R.id.clg1);
        c2 = findViewById(R.id.clg2);
        c3 = findViewById(R.id.clg3);
        c4 = findViewById(R.id.clg4);
        c5 = findViewById(R.id.clg5);
        p1 = findViewById(R.id.point1);
        p2 = findViewById(R.id.point2);
        p3 = findViewById(R.id.point3);
        p4 = findViewById(R.id.point4);
        p5 = findViewById(R.id.point5);
        username = mSharedPreferences.getString("user", null);
        update = findViewById(R.id.submit);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c1.getText().toString().equalsIgnoreCase("") || c2.getText().toString().equalsIgnoreCase("")||c3.getText().toString().equalsIgnoreCase("")||c4.getText().toString().equalsIgnoreCase("")||c5.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(),"Insert proper data ",Toast.LENGTH_LONG).show();
                }
                else  if(p1.getText().toString().equalsIgnoreCase("") || p2.getText().toString().equalsIgnoreCase("")||p3.getText().toString().equalsIgnoreCase("")||p4.getText().toString().equalsIgnoreCase("")||p5.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(),"Insert proper data ",Toast.LENGTH_LONG).show();
                }
                else {
                    addData();
                }
            }
        });
    }

    private void addData() {

        Map<String, Object> newData = new HashMap<>();

        newData.put(COLLEGE1_KEY, c1.getText().toString());
        newData.put(POINT1_KEY, p1.getText().toString());
        newData.put(COLLEGE2_KEY, c2.getText().toString());
        newData.put(POINT2_KEY, p2.getText().toString());
        newData.put(COLLEGE3_KEY, c3.getText().toString());
        newData.put(POINT3_KEY, p3.getText().toString());
        newData.put(COLLEGE4_KEY, c4.getText().toString());
        newData.put(POINT4_KEY, p4.getText().toString());
        newData.put(COLLEGE5_KEY, c5.getText().toString());
        newData.put(POINT5_KEY, p5.getText().toString());
        newData.put("User", username);
        db.collection("Championship").document("Details").set(newData)

                .addOnSuccessListener(new OnSuccessListener<Void>() {

                    @Override

                    public void onSuccess(Void aVoid) {

                        Toast.makeText(Championship.this, "Updated!",

                                Toast.LENGTH_SHORT).show();

                    }

                })

                .addOnFailureListener(new OnFailureListener() {

                    @Override

                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(Championship.this, "ERROR: " + e.toString(),

                                Toast.LENGTH_SHORT).show();

                        Log.d("TAG", e.toString());

                    }

                });
    }
}
