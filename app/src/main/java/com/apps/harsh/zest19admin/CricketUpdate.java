package com.apps.harsh.zest19admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.apps.harsh.zest19admin.Model.Cricket;
import com.apps.harsh.zest19admin.Model.Match;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CricketUpdate extends AppCompatActivity {

    private static final String TAG = "Schedule";
    private FirebaseFirestore firestoreDB;
    private ListenerRegistration firestoreListener;

    TextView t1, t2;
    String team1, team2, game, desc, match_st, winner, runner,date,time,match_place, match_type, username;
    static String docid;
    Spinner t1_status, t2_status, over, ball, inning;
    List<String> list;
    EditText run, wicket, target, winner_team, runner_team, result;
    Button update;
    LinearLayout match_result;
    Spinner match_stat;
    List<String> match_status;
    Bundle bundle;
    private SharedPreferences mSharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cricket_update);

        firestoreDB = FirebaseFirestore.getInstance();

        t1 = (TextView) findViewById(R.id.textView13);
        t2 = (TextView) findViewById(R.id.textView14);
        t1_status = (Spinner) findViewById(R.id.spinner3);
        t2_status = (Spinner) findViewById(R.id.spinner4);
        over = (Spinner) findViewById(R.id.spinner5);
        ball = (Spinner) findViewById(R.id.spinner6);
        run = (EditText) findViewById(R.id.editText3);
        update = (Button) findViewById(R.id.button4);
        wicket = (EditText) findViewById(R.id.editText4);
        target = (EditText) findViewById(R.id.editText5);
        inning = (Spinner) findViewById(R.id.spinner7);
        result = (EditText) findViewById(R.id.winner_team);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        username = mSharedPreferences.getString("user", null);

        match_stat = (Spinner) findViewById(R.id.match_status);
        match_result = (LinearLayout) findViewById(R.id.match_result);
        match_status = new ArrayList<String>();
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, match_status);
        match_status.add("Match Running");
        match_status.add("Match Completed");
        match_stat.setAdapter(dataAdapter2);
        Intent i = getIntent();
        try {
            bundle = i.getExtras();
            String t = bundle.getString("team1");
            t1.setText(t);
            team1 = t;
            t = bundle.getString("team2");
            t2.setText(t);
            team2 = t;
            game = bundle.getString("game");
            date = bundle.getString("date");
            time = bundle.getString("time");
            match_place = bundle.getString("match_place");
            docid = bundle.getString("id");
            match_type = bundle.getString("match_type");
        } catch (Exception e) {

        }

        list = new ArrayList<String>();
        list.add("Ball");
        list.add("Bat");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, list);
        t1_status.setAdapter(dataAdapter);
        ;
        t2_status.setAdapter(dataAdapter);

        list = new ArrayList<String>();
        list.add(" Over");
        list.add("0");
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        list.add("10");
        list.add("11");
        list.add("12");
        list.add("13");
        list.add("14");
        list.add("15");
        list.add("16");
        list.add("17");
        list.add("18");
        list.add("19");
        dataAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, list);
        over.setAdapter(dataAdapter);
        list = new ArrayList<String>();
        list.add(" Ball");
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        dataAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, list);
        ball.setAdapter(dataAdapter);
        list = new ArrayList<String>();
        list.add("First");
        list.add("Second");
        dataAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, list);
        inning.setAdapter(dataAdapter);
        t1_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    t2_status.setSelection(1);
                } else if (position == 1) {
                    t2_status.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        t2_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    t1_status.setSelection(1);
                } else if (position == 1) {
                    t1_status.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        match_stat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (match_status.get(match_stat.getSelectedItemPosition())) {
                    case "Match Running":
                        match_result.setVisibility(View.GONE);
                        break;
                    case "Match Completed":
                        match_result.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = match_status.get(match_stat.getSelectedItemPosition());
                String o = over.getSelectedItem().toString();
                String b = ball.getSelectedItem().toString();
                String res = "";
                String i = inning.getSelectedItem().toString();
                String w = wicket.getText().toString();
                String t = target.getText().toString();
                String r = run.getText().toString();
                String s1 = t1_status.getSelectedItem().toString();
                String s2 = t2_status.getSelectedItem().toString();
                switch (status) {
                    case "Match Running":
                        res = "None";
                        break;
                    case "Match Completed":
                        res = result.getText().toString();
                        break;
                }
                Map<String, Object> match = new Match().toMap();
                Map<String, Object> cricket = new Cricket(game, match_type, team1, team2, date, time, match_place, s1,s2, status, b, i, o, t, w, res, r, username).toMap();
                //match.put(docid, cricket);
                firestoreDB.collection("Schedule")
                        .document(docid)
                        .set(cricket, SetOptions.merge())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.e(TAG, "update successful!");
                                Toast.makeText(getApplicationContext(), "Match has been updated!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e(TAG, "Error adding Note document", e);
                                Toast.makeText(getApplicationContext(), "Match could not be updated!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
