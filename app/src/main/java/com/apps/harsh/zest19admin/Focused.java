package com.apps.harsh.zest19admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.apps.harsh.zest19admin.Model.Match;
import com.apps.harsh.zest19admin.adapter.FocusedAdapter;
import com.apps.harsh.zest19admin.adapter.RecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Focused extends AppCompatActivity {

    private static final String TAG = "Focused";

    private RecyclerView recyclerView;
    private FocusedAdapter mAdapter;
    private SharedPreferences mSharedPreferences;
    String username;
    private FirebaseFirestore firestoreDB;
    private ListenerRegistration firestoreListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focused);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.focused_list);
        firestoreDB = FirebaseFirestore.getInstance();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        username = mSharedPreferences.getString("user", null);
        loadFocusedList();

        firestoreListener = firestoreDB.collection("Focused")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.e(TAG, "Listen failed!", e);
                            return;
                        }

                        List<Match> matchList = new ArrayList<>();

                        for (DocumentSnapshot doc : documentSnapshots) {
                            String id, gameType, matchType, team1, team2, date, time, place, score1, score2, status, desc, user;
                            gameType = doc.getString("GameType");
                            matchType = doc.getString("MatchType");
                            team1 = doc.getString("Team1");
                            team2 = doc.getString("Team2");
                            date = doc.getString("Date");
                            time = doc.getString("Time");
                            place = doc.getString("Place");
                            score1 = doc.getString("Score1");
                            score2 = doc.getString("Score2");
                            status = doc.getString("Status");
                            desc = doc.getString("Description");
                            user = doc.getString("User");
                            Match match = new Match(gameType, matchType, team1, team2, date, time, place, score1, score2, status, desc, user);
                            match.setId(doc.getId());
                            matchList.add(match);
                        }

                        mAdapter = new FocusedAdapter(matchList, getApplicationContext(), firestoreDB);
                        recyclerView.setAdapter(mAdapter);
                    }
                });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Focused.this,AddFocused.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        firestoreListener.remove();
    }

    private void loadFocusedList() {
        firestoreDB.collection("Focused")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Match> matchList = new ArrayList<>();

                            for (DocumentSnapshot doc : task.getResult()) {
                                //Match match = doc.toObject(Match.class);
                                String id, gameType, matchType, team1, team2, date, time, place, score1, score2, status, desc, user;
                                gameType = doc.getString("GameType");
                                matchType = doc.getString("MatchType");
                                team1 = doc.getString("Team1");
                                team2 = doc.getString("Team2");
                                date = doc.getString("Date");
                                time = doc.getString("Time");
                                place = doc.getString("Place");
                                score1 = doc.getString("Score1");
                                score2 = doc.getString("Score2");
                                status = doc.getString("Status");
                                desc = doc.getString("Description");
                                user = doc.getString("User");
                                Match match = new Match(gameType, matchType, team1, team2, date, time, place, score1, score2, status, desc, user);
                                match.setId(doc.getId());
                                matchList.add(match);
                            }

                            mAdapter = new FocusedAdapter(matchList, getApplicationContext(), firestoreDB);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(mAdapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

}
