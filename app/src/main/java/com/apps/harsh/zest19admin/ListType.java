package com.apps.harsh.zest19admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.apps.harsh.zest19admin.Model.Match;
import com.apps.harsh.zest19admin.adapter.MatchListAdapter;
import com.apps.harsh.zest19admin.adapter.RecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListType extends AppCompatActivity implements ItemClickListener{

    private static final String TAG = "ListTypes";
    int total = 0;
    int del_pos;
    String del_id = "";
    String list_title, game_title, type_title, team1, team2;
    Bundle bundle, bundle1;
    String[] collections;
    EditText team11, team12;

    private RecyclerView recyclerView;
    private MatchListAdapter mAdapter;
    private FirebaseFirestore firestoreDB;
    private ListenerRegistration firestoreListener;

    List<Match> matchList = new ArrayList<>();
    CollectionReference scheduleRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_type);

        recyclerView = findViewById(R.id.score_list);
        firestoreDB = FirebaseFirestore.getInstance();

        /*ItemClickListener listener = (view, position) -> {
            Toast.makeText(this, "Position " + position, Toast.LENGTH_SHORT).show();
            final Match match = matchList.get(position);
            String game = match.getGameType();
            String m = match.getMatchType();
            Toast.makeText(this, game + m, Toast.LENGTH_LONG).show();
            /*if (game.equalsIgnoreCase("Baseball (Boys)") || game.equalsIgnoreCase("Baseball (Girls)")) {
                Intent i = new Intent(ListType.this, BaseballUpdate.class);
                i.putExtra("id", match.getId());
                i.putExtra("team1", match.getTeam1());
                i.putExtra("team2", match.getTeam2());
                i.putExtra("game", game);
                i.putExtra("date",match.getDate());
                i.putExtra("time",match.getTime());
                i.putExtra("match_place",match.getPlace());
                i.putExtra("match_type", match.getMatchType());
                startActivity(i);
            } else if (game.equalsIgnoreCase("Cricket (Boys)") || game.equalsIgnoreCase("Cricket (Girls)") || game.equalsIgnoreCase("Box Cricket") || game.equalsIgnoreCase("Mix Cricket")) {

                Intent i = new Intent(ListType.this, CricketUpdate.class);
                i.putExtra("id", match.getId());
                i.putExtra("team1", match.getTeam1());
                i.putExtra("team2", match.getTeam2());
                i.putExtra("game", game);
                i.putExtra("date",match.getDate());
                i.putExtra("time",match.getTime());
                i.putExtra("match_place",match.getPlace());
                i.putExtra("match_type", match.getMatchType());
                startActivity(i);
            } else if (game.equalsIgnoreCase("Badminton (Boys)") || game.equalsIgnoreCase("Badminton (Girls)") || game.equalsIgnoreCase("Ball- Badminton (Boys)") || game.equalsIgnoreCase("Ball- Badminton (Girls)") || game.equalsIgnoreCase("Lawn Tennis (Boys)") || game.equalsIgnoreCase("Lawn Tennis (Girls)") || game.equalsIgnoreCase("Volleyball (Boys)") || game.equalsIgnoreCase("Volleyball (Girls)") || game.equalsIgnoreCase("Table Tennis (Boys)") || game.equalsIgnoreCase("Table Tennis (Girls)")) {

            Intent i = new Intent(ListType.this, SetUpdate.class);
            i.putExtra("id", match.getId());
            i.putExtra("team1", match.getTeam1());
            i.putExtra("team2", match.getTeam2());
            i.putExtra("game", game);
            i.putExtra("date",match.getDate());
            i.putExtra("time",match.getTime());
            i.putExtra("match_place",match.getPlace());
            i.putExtra("match_type", match.getMatchType());
            startActivity(i);
        } else {
                //String[] arr = temp.split(" ");
                //Toast.makeText(getApplicationContext(), arr[0] + " " + arr[2], Toast.LENGTH_LONG).show();
                Intent i = new Intent(ListType.this, UpdateScore.class);
                i.putExtra("id", match.getId());
                i.putExtra("team1", match.getTeam1());
                i.putExtra("team2", match.getTeam2());
                i.putExtra("game", game);
                i.putExtra("date",match.getDate());
                i.putExtra("time",match.getTime());
                i.putExtra("match_place",match.getPlace());
                i.putExtra("match_type", match.getMatchType());
                startActivity(i);
            }
        };*/

        mAdapter = new MatchListAdapter(matchList, getApplicationContext(), firestoreDB, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        //mAdapter.setClickListener(this);

        bundle = getIntent().getExtras();
        bundle1 = new Bundle();
        if (bundle != null) {
            switch (bundle.getString("Title")) {

                case "HOCKEY":
                    loadMatchList("Hockey (Boys)", "Hockey (Girls)");
                    break;

                case "KABADDI":
                    loadMatchList("Kabaddi (Boys)", "Kabaddi (Girls)");
                    break;
                case "KHO-KHO":
                    loadMatchList("Kho-Kho (Boys)", "Kho-Kho (Girls)");
                    break;

                case "FOOTBALL":
                    loadMatchList("Football (Boys)", "Football (Girls)");
                    break;

                case "HANDBALL":
                    loadMatchList("Handball (Boys)", "Handball (Girls)");
                    break;

                case "BASKET BALL":
                    loadMatchList("Basketball (Boys)", "Basketball (Girls)");
                    break;

                case "VOLLEY BALL":
                    loadMatchList("Volleyball (Boys)", "Volleyball (Girls)");
                    break;

                case "BADMINTON":
                    loadMatchList("Badminton (Boys)", "Badminton (Girls)");
                    break;

                case "LAWN TENNIS":
                    loadMatchList("Lawn Tennis (Boys)", "Lawn Tennis (Girls)");
                    break;
                case "TABLE TENNIS":
                    loadMatchList("Table Tennis (Boys)", "Table Tennis (Girls)");
                    break;

                case "BALL BADMINTON":
                    loadMatchList("Ball- Badminton (Boys)", "Ball- Badminton (Girls)");
                    break;

                case "BASE BALL":
                    loadMatchList("Baseball (Boys)", "Baseball (Girls)");
                    break;

                case "CRICKET":
                    loadMatchList("Cricket (Boys)", "Cricket (Girls)");
                    break;

                case "BOX CRICKET":
                    loadMatchList("Box Cricket");
                    break;

                case "MIX CRICKET":
                    loadMatchList("Mix Cricket");
                    break;

                default:
                    break;
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //firestoreListener.remove();
    }

    public void loadMatchList(final String name1, final String name2) {
        scheduleRef = firestoreDB.collection("Schedule");

        Query firstQuery = scheduleRef.whereEqualTo("GameType", name1);
        Query secondQuery = scheduleRef.whereEqualTo("GameType", name2);

        Task firstTask = firstQuery.get();
        Task secondTask = secondQuery.get();
        Task<List<QuerySnapshot>> allTasks = Tasks.whenAllSuccess(firstTask, secondTask);
        allTasks.addOnSuccessListener(new OnSuccessListener<List<QuerySnapshot>>() {
            @Override
            public void onSuccess(List<QuerySnapshot> querySnapshots) {
                for (QuerySnapshot queryDocumentSnapshots : querySnapshots) {
                    for (QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                        String id, gameType, matchType, team1, team2, date, time, place, score1, score2, status, desc,user;
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
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    public void loadMatchList(String name1) {
        scheduleRef = firestoreDB.collection("Schedule");
        scheduleRef.whereEqualTo("GameType", name1)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
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
                        /*mAdapter = new MatchListAdapter(matchList, getApplicationContext(), firestoreDB);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(mAdapter);
                        mAdapter.setClickListener(ListType.this);*/
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onClick(Bundle bundle) {
        // The onClick implementation of the RecyclerView item click
        String game = bundle.getString("gametype");
        Toast.makeText(this, game , Toast.LENGTH_LONG).show();
        if (game.equalsIgnoreCase("Baseball (Boys)") || game.equalsIgnoreCase("Baseball (Girls)")) {
            Intent i = new Intent(ListType.this, BaseballUpdate.class);
            i.putExtra("id", bundle.getString("id"));
            i.putExtra("team1", bundle.getString("team1"));
            i.putExtra("team2", bundle.getString("team2"));
            i.putExtra("game", bundle.getString("gametype"));
            i.putExtra("date", bundle.getString("date"));
            i.putExtra("time", bundle.getString("time"));
            i.putExtra("match_place", bundle.getString("place"));
            i.putExtra("match_type", bundle.getString("matchtype"));
            startActivity(i);
        } else if (game.equalsIgnoreCase("Cricket (Boys)") || game.equalsIgnoreCase("Cricket (Girls)") || game.equalsIgnoreCase("Box Cricket") || game.equalsIgnoreCase("Mix Cricket")) {

            Intent i = new Intent(ListType.this, CricketUpdate.class);
            i.putExtra("id", bundle.getString("id"));
            i.putExtra("team1", bundle.getString("team1"));
            i.putExtra("team2", bundle.getString("team2"));
            i.putExtra("game", bundle.getString("gametype"));
            i.putExtra("date", bundle.getString("date"));
            i.putExtra("time", bundle.getString("time"));
            i.putExtra("match_place", bundle.getString("place"));
            i.putExtra("match_type", bundle.getString("matchtype"));
            startActivity(i);
        } else if (game.equalsIgnoreCase("Badminton (Boys)") || game.equalsIgnoreCase("Badminton (Girls)") || game.equalsIgnoreCase("Ball- Badminton (Boys)") || game.equalsIgnoreCase("Ball- Badminton (Girls)") || game.equalsIgnoreCase("Lawn Tennis (Boys)") || game.equalsIgnoreCase("Lawn Tennis (Girls)") || game.equalsIgnoreCase("Volleyball (Boys)") || game.equalsIgnoreCase("Volleyball (Girls)") || game.equalsIgnoreCase("Table Tennis (Boys)") || game.equalsIgnoreCase("Table Tennis (Girls)")) {

            Intent i = new Intent(ListType.this, SetUpdate.class);
            i.putExtra("id", bundle.getString("id"));
            i.putExtra("team1", bundle.getString("team1"));
            i.putExtra("team2", bundle.getString("team2"));
            i.putExtra("game", bundle.getString("gametype"));
            i.putExtra("date", bundle.getString("date"));
            i.putExtra("time", bundle.getString("time"));
            i.putExtra("match_place", bundle.getString("place"));
            i.putExtra("match_type", bundle.getString("matchtype"));
            startActivity(i);
        } else {
            //String[] arr = temp.split(" ");
            //Toast.makeText(getApplicationContext(), arr[0] + " " + arr[2], Toast.LENGTH_LONG).show();
            Intent i = new Intent(ListType.this, UpdateScore.class);
            i.putExtra("id", bundle.getString("id"));
            i.putExtra("team1", bundle.getString("team1"));
            i.putExtra("team2", bundle.getString("team2"));
            i.putExtra("game", bundle.getString("gametype"));
            i.putExtra("date", bundle.getString("date"));
            i.putExtra("time", bundle.getString("time"));
            i.putExtra("match_place", bundle.getString("place"));
            i.putExtra("match_type", bundle.getString("matchtype"));
            startActivity(i);
        }
    }
}