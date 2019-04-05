package com.apps.harsh.zest19admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.harsh.zest19admin.Model.Match;
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


public class Schedule extends AppCompatActivity {

    private static final String TAG = "Schedule";

    private RecyclerView recyclerView;
    private RecyclerAdapter mAdapter;

    private FirebaseFirestore firestoreDB;
    private ListenerRegistration firestoreListener;
    List<Match> matchList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.match_list);
        firestoreDB = FirebaseFirestore.getInstance();

        loadMatchList();

        firestoreListener = firestoreDB.collection("Schedule")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.e(TAG, "Listen failed!", e);
                            return;
                        }

                        matchList = new ArrayList<>();

                        for (DocumentSnapshot doc : documentSnapshots) {
                            //Match match = doc.toObject(Match.class);
                            //Log.e("PlaceName", doc.getString("Place"));
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

                        mAdapter = new RecyclerAdapter(matchList, getApplicationContext(), firestoreDB);
                        recyclerView.setAdapter(mAdapter);
                    }
                });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Schedule.this,AddSchedule.class));
            }
        });
    }

    public class MatchesHolder extends RecyclerView.ViewHolder {
        TextView matchType, gameType, team1, team2, place, time, date, score1, score2;
        ImageView edit;
        ImageView delete;


        public MatchesHolder(View view) {
            super(view);
            matchType = view.findViewById(R.id.match_type);
            gameType = view.findViewById(R.id.game_type);
            team1 = view.findViewById(R.id.team1);
            team2 = view.findViewById(R.id.team2);
            place = view.findViewById(R.id.place);
            time = view.findViewById(R.id.time);
            date = view.findViewById(R.id.date);
            edit = view.findViewById(R.id.edit);
            delete = view.findViewById(R.id.delete);
            score1 = view.findViewById(R.id.score1);
            score2 = view.findViewById(R.id.score2);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
       //adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        //adapter.stopListening();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        firestoreListener.remove();
    }

    private void loadMatchList() {
        firestoreDB.collection("Schedule")
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

                            mAdapter = new RecyclerAdapter(matchList, getApplicationContext(), firestoreDB);
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
