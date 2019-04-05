package com.apps.harsh.zest19admin.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.harsh.zest19admin.ItemClickListener;
import com.apps.harsh.zest19admin.Model.Match;
import com.apps.harsh.zest19admin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MatchListAdapter extends RecyclerView.Adapter<MatchListAdapter.ViewHolder> {

    private List<Match> matchList;
    private Context context;
    private FirebaseFirestore firestoreDB;
    private ItemClickListener clickListener;

    public MatchListAdapter(List<Match> matchList, Context context, FirebaseFirestore firestoreDB, ItemClickListener listener) {
        this.matchList = matchList;
        this.context = context;
        this.firestoreDB = firestoreDB;
        this.clickListener = listener;
    }

    @Override
    public MatchListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_match, parent, false);

        return new MatchListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MatchListAdapter.ViewHolder holder, final int position) {
        final int itemPosition = position;
        final Match match = matchList.get(itemPosition);
        Bundle bundle = new Bundle();
        String id = match.getId();
        String gt = match.getGameType();
        String mt = match.getMatchType();
        String dt = match.getDate();
        String tm = match.getTime();
        String pl = match.getPlace();
        String t1 = match.getTeam1();
        String t2 = match.getTeam2();
        String s1 = match.getScore1();
        String s2 = match.getScore2();
        String st = match.getStatus();
        String usr = match.getUser();
        bundle.putString("id", id);
        bundle.putString("gametype", gt);
        bundle.putString("matchtype", mt);
        bundle.putString("date", dt);
        bundle.putString("time", tm);
        bundle.putString("place", pl);
        bundle.putString("team1", t1);
        bundle.putString("team2", t2);
        bundle.putString("score1", s1);
        bundle.putString("score2", s2);
        bundle.putString("status", st);
        bundle.putString("User", usr);
        holder.gameType.setText(match.getGameType());
        holder.matchType.setText(match.getMatchType());
        holder.date.setText(match.getDate());
        holder.time.setText(match.getTime());
        holder.place.setText(match.getPlace());
        holder.team1.setText(match.getTeam1());
        holder.team2.setText(match.getTeam2());
        holder.score1.setText(match.getScore1());
        holder.score2.setText(match.getScore2());
        holder.status.setText(match.getStatus());
        holder.desc.setText(match.getDesc());
        holder.user.setText(match.getUser());


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMatch(match.getId(), itemPosition);
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onClick(bundle);
                //Toast.makeText(context, id + gt + pl + mt, Toast.LENGTH_LONG).show();
            }
        });
    }



    @Override
    public int getItemCount() {
        return matchList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView matchType, gameType, team1, team2, place, time, date, score1, score2, status, desc, user;
        ImageView edit;
        ImageView delete;
        View mView;

        ViewHolder(View view) {
            super(view);
            mView = view;
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
            status = view.findViewById(R.id.status);
            desc = view.findViewById(R.id.desc);
            user = view.findViewById(R.id.updatedBy);
            //edit.setVisibility(View.INVISIBLE);
        }
    }

    private void deleteMatch(String id, final int position) {
        firestoreDB.collection("Schedule")
                .document(id)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        matchList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, matchList.size());
                        Toast.makeText(context, "Match Removed!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}