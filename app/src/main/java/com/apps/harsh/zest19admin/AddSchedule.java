package com.apps.harsh.zest19admin;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.apps.harsh.zest19admin.Model.Match;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AddSchedule extends AppCompatActivity {

    EditText team11,team22,time;
    Button submit;
    Spinner type,game,match_place1;
    EditText datepicker;
    String team1,team2,game_ty,match_ty,place, ti, date, username;
    ProgressDialog progressDialog;
    private static final String TAG = "AddMatchActivity";
    private SharedPreferences mSharedPreferences;
    private FirebaseFirestore firestoreDB;
    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        team11 = (EditText) findViewById(R.id.team1);
        team22 = (EditText) findViewById(R.id.team2);
        submit = (Button) findViewById(R.id.button1);
        type = (Spinner)findViewById(R.id.spinner);
        game = (Spinner)findViewById(R.id.spinner2);
        match_place1 = (Spinner)findViewById(R.id.spinner8);
        team11.setHintTextColor(Color.LTGRAY);
        team22.setHintTextColor(Color.LTGRAY);
        team11.setHint("Team1");
        team22.setHint("Team2");
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        username = mSharedPreferences.getString("user", null);
        firestoreDB = FirebaseFirestore.getInstance();

        datepicker = (EditText)findViewById(R.id.date);
        Calendar mcurrentDate = Calendar.getInstance();
        final int mYear = mcurrentDate.get(Calendar.YEAR);
        final int mMonth = mcurrentDate.get(Calendar.MONTH);
        final int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        Calendar mcurrentTime = Calendar.getInstance();
        final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        final int minute = mcurrentTime.get(Calendar.MINUTE);
        final DatePickerDialog datePickerDialog;
        final TimePickerDialog timePickerDialog;
        time = (EditText)findViewById(R.id.time);
        String am_pm = (hour < 12) ? " AM" : " PM";
        final String[] MONTH = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        datePickerDialog = new DatePickerDialog(AddSchedule.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                datepicker.setText(MONTH[month]+" "+dayOfMonth);

            }
        },mYear,mMonth,mDay);//Yes 24 hour time

        timePickerDialog = new TimePickerDialog(AddSchedule.this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                time.setText(String.format("%02d:%02d", hourOfDay, minute));
            }

        },hour,minute, false);
        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                mTimePicker.setTitle("Select Time");
                datePickerDialog.show();
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show();
            }
        });

        //preferences = getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);
        //user_name = preferences.getString("Username","");

        progressDialog = new ProgressDialog(this, R.style.MyTheme);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.setCancelable(false);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString("UpdateMatchId");
            //type.setText(bundle.getString("UpdateMatchType"));
            //game.setText(bundle.getString("UpdateMatchGameType"));
            //match_place1.setText(bundle.getString("UpdateMatchPlace"));
            time.setText(bundle.getString("UpdateMatchTime"));
            datepicker.setText(bundle.getString("UpdateMatchDate"));
            team11.setText(bundle.getString("UpdateMatchTeam1"));
            team22.setText(bundle.getString("UpdateMatchTeam2"));
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(team11.getText().toString().equals("") || (datepicker.getText().toString()).equals("") || (time.getText().toString()).equals("")){
                    Toast.makeText(getApplicationContext(),"Insert some meaningful data",Toast.LENGTH_LONG).show();
                }else if(team22.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Insert some meaningful data",Toast.LENGTH_LONG).show();
                }
                else if(team11.getText().toString().equalsIgnoreCase(team22.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Both Team can't be same",Toast.LENGTH_LONG).show();
                } else{
                    team1 = team11.getText().toString();
                    team2 = team22.getText().toString();
                    ti = time.getText().toString();
                    date = datepicker.getText().toString();
                    match_ty = type.getSelectedItem().toString();
                    game_ty = game.getSelectedItem().toString();
                    place = match_place1.getSelectedItem().toString();
                    //Toast.makeText(AddSchedule.this, team1 + team2 + ti + date + match_ty + game_ty + place, Toast.LENGTH_LONG).show();

                    if (team2.length() > 0 && team1.length() > 0) {
                        if (id.length() > 0) {
                            //Toast.makeText(AddSchedule.this, "Update: " +team1 + team2 + ti + date + match_ty + game_ty + place, Toast.LENGTH_LONG).show();
                            updateMatch(id, team1, team2, date, ti, place, game_ty, match_ty, "0","0");
                        } else {
                            //Toast.makeText(AddSchedule.this, "Insert: " + team1 + team2 + ti + date + match_ty + game_ty + place, Toast.LENGTH_LONG).show();
                            addMatch(team1, team2, date, ti, place, game_ty, match_ty, "0","0");
                        }
                    }

                    finish();
                }
            }
        });
    }

    private void updateMatch(String id, String team1, String team2, String date, String ti, String place, String game_ty, String match_ty, String score1, String score2) {
        Map<String, Object> match = (new Match(id, game_ty, match_ty, team1, team2, date, ti, place, "0","0","Upcoming", "No description available", username)).toMap();

        firestoreDB.collection("Schedule")
                .document(id)
                .set(match)
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

    private void addMatch(String team1, String team2, String date, String ti, String place, String game_ty, String match_ty, String score1, String score2) {
        Map<String, Object> match = new Match(game_ty, match_ty, team1, team2, date, ti, place, "0","0", "Upcoming", "No description available", username).toMap();

        firestoreDB.collection("Schedule")
                .add(match)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.e(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        Toast.makeText(getApplicationContext(), "Match has been added!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error adding Match document", e);
                        Toast.makeText(getApplicationContext(), "Match could not be added!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
