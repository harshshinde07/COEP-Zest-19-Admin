package com.apps.harsh.zest19admin;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UpdateScore extends AppCompatActivity {

    TextView t1, t2;
    String team1, team2, g;
    String description, game;
    TextView team1_name, team2_name;
    EditText score1, score2, set, team1_win, team2_win, winner_team, runner_team, result;
    String s1, s2, match_st, set_n, team1_set, team2_set, winner, runner,date,time,match_place, username;
    TextView p1, p2, m1, m2, g1;
    Button update;
    int score1_1, score2_2;
    String docid;
    Spinner match_stat;
    private SharedPreferences mSharedPreferences;
    LinearLayout match_result, set_no, set_status;
    List<String> match_status;
    private FirebaseFirestore firestoreDB;
    private static final String TAG = "Schedule";
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_score);

        firestoreDB = FirebaseFirestore.getInstance();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        username = mSharedPreferences.getString("user", null);
        t1 = (TextView) findViewById(R.id.textView4);
        t2 = (TextView) findViewById(R.id.textView5);
        score1 = (EditText) findViewById(R.id.editText);
        score2 = (EditText) findViewById(R.id.editText2);
        set = (EditText) findViewById(R.id.set_no);
        team1_win = (EditText) findViewById(R.id.team1_set);
        team2_win = (EditText) findViewById(R.id.team2_set);
        winner_team = (EditText) findViewById(R.id.winner_team);
        p1 = (TextView) findViewById(R.id.textView6);
        m1 = (TextView) findViewById(R.id.textView7);
        p2 = (TextView) findViewById(R.id.textView8);
        m2 = (TextView) findViewById(R.id.textView9);
        g1 = (TextView) findViewById(R.id.game_text);
        match_stat = (Spinner) findViewById(R.id.match_status);
        match_result = (LinearLayout) findViewById(R.id.match_result);
        set_no = (LinearLayout) findViewById(R.id.set);
        set_status = (LinearLayout) findViewById(R.id.set_status);
        match_status = new ArrayList<>();
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, match_status);
        match_status.add("Match Running");
        match_status.add("Match Completed");
        match_stat.setAdapter(dataAdapter2);
        score1.setEnabled(false);
        score2.setEnabled(false);
        score1.setText("0");
        score2.setText("0");
        result = (EditText) findViewById(R.id.winner_team);

        //display();

        match_stat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (match_stat.getSelectedItemPosition()) {
                    case 0:
                        match_result.setVisibility(View.GONE);
                        break;
                    case 1:
                        match_result.setVisibility(View.VISIBLE);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //sharedPreferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        update = (Button) findViewById(R.id.button);
        bundle = getIntent().getExtras();
        if(bundle != null) {
            team1 = bundle.getString("team1");
            team2 = bundle.getString("team2");
            g = bundle.getString("game");
            date = bundle.getString("date");
            time = bundle.getString("time");
            match_place = bundle.getString("match_place");
            docid = bundle.getString("id");
            t1.setText(team1);
            t2.setText(team2);
            //Toast.makeText(this, " " + bundle.getString("game") + bundle.getString("team1") + bundle.getString("team2"), Toast.LENGTH_SHORT).show();
        }else {
            System.out.println("Bundle is null");
        }
        if (!isNetworkAvailable()) {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }

        p1.setOnClickListener(new View.OnClickListener() {
            int temp;

            @Override
            public void onClick(View v) {
                temp = Integer.parseInt(score1.getText().toString());
                temp++;
                score1.setText(temp + "");
                score1_1 = temp;
            }
        });
        m1.setOnClickListener(new View.OnClickListener() {
            int temp;

            @Override
            public void onClick(View v) {
                temp = Integer.parseInt(score1.getText().toString());
                temp--;
                score1.setText(temp + "");
                score1_1 = temp;
            }
        });
        p2.setOnClickListener(new View.OnClickListener() {
            int temp;

            @Override
            public void onClick(View v) {
                temp = Integer.parseInt(score2.getText().toString());
                temp++;
                score2.setText(temp + "");
                score2_2 = temp;
            }
        });
        m2.setOnClickListener(new View.OnClickListener() {
            int temp;

            @Override
            public void onClick(View v) {
                temp = Integer.parseInt(score2.getText().toString());
                temp--;
                score2.setText(temp + "");
                score2_2 = temp;
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference documentReference = firestoreDB.collection("Schedule").document(docid);
                String s1 = score1.getText().toString();
                String s2 = score2.getText().toString();
                String st = match_stat.getSelectedItem().toString();
                String res = "";
                switch (st) {
                    case "Match Running":
                        res = "None";
                        break;
                    case "Match Completed":
                        res = result.getText().toString();
                        break;
                }
                documentReference.update("Status", st);
                documentReference.update("Description", res);
                documentReference.update("Score1", s1);
                documentReference.update("Score2", s2);
                documentReference.update("User", username).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
