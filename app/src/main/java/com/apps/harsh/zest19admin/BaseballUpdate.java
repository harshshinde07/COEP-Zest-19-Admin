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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.harsh.zest19admin.Model.Baseball;
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

public class BaseballUpdate extends AppCompatActivity {

    TextView t1, t2;
    String team1, team2, game, docid, desc,match_st,winner,runner,date,time,match_place, match_type, username;
    Spinner t1_status, t2_status, inning;
    List<String> list,match_status;
    LinearLayout match_result;
    EditText run1, run2, away1,result;
    AutoCompleteTextView away2;
    Button update;
    Spinner match_stat;
    private SharedPreferences mSharedPreferences;
    private FirebaseFirestore firestoreDB;
    private ListenerRegistration firestoreListener;
    private static final String TAG = "Schedule";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baseball_update);

        firestoreDB = FirebaseFirestore.getInstance();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        username = mSharedPreferences.getString("user", null);
        t1 = (TextView) findViewById(R.id.textView13);
        t2 = (TextView) findViewById(R.id.textView14);
        t1_status = (Spinner) findViewById(R.id.spinner3);
        t2_status = (Spinner) findViewById(R.id.spinner4);

        run1 = (EditText) findViewById(R.id.editText3);
        run2 = (EditText) findViewById(R.id.editText4);
        update = (Button) findViewById(R.id.button4);
        away2 = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        away1 = (EditText) findViewById(R.id.editText5);
        inning = (Spinner) findViewById(R.id.spinner7);

        match_stat = (Spinner)findViewById(R.id.match_status);
        match_result = (LinearLayout)findViewById(R.id.match_result);
        match_status = new ArrayList<String>();
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item, match_status);
        match_status.add("Match Running");
        match_status.add("Match Completed");
        match_stat.setAdapter(dataAdapter2);

        result = (EditText) findViewById(R.id.winner_team);
        Intent i = getIntent();
        try {
            Bundle bundle = i.getExtras();
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
            match_type = bundle.getString("match_type");
            docid = bundle.getString("id");
        } catch (Exception e) {

        }

        list = new ArrayList<String>();
        list.add("Pitching");
        list.add("Slugging");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, list);
        t1_status.setAdapter(dataAdapter);

        t2_status.setAdapter(dataAdapter);

        list = new ArrayList<String>();
        list.add("First");
        list.add("Second");
        list.add("Third");
        list.add("Fourth");
        list.add("Fifth");
        list.add("Sixth");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, list);
        inning.setAdapter(arrayAdapter);

        match_stat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (match_status.get(match_stat.getSelectedItemPosition())){
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
        toggle();
    }

    public void toggle() {
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
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

    }

    public void update() {
        String status = match_status.get(match_stat.getSelectedItemPosition());
        String res = "";
        String i = inning.getSelectedItem().toString();
        String c1 = run1.getText().toString();
        String c2 = run2.getText().toString();
        String a1 = away2.getText().toString();
        String a2 = away1.getText().toString();
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
        Map<String, Object> baseball = new Baseball(game, match_type, team1, team2, date, time, match_place, c1,c2, status, a1, a2, s1, s2, i, res, username).toMap();
        //match.put("match", baseball);
        firestoreDB.collection("Schedule")
                .document(docid)
                .set(baseball, SetOptions.merge())
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
}
