package com.apps.harsh.zest19admin.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.harsh.zest19admin.AddSchedule;
import com.apps.harsh.zest19admin.Model.Match;
import com.apps.harsh.zest19admin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<Match> matchList;
    private Context context;
    private FirebaseFirestore firestoreDB;

    public RecyclerAdapter(List<Match> matchList, Context context, FirebaseFirestore firestoreDB) {
        this.matchList = matchList;
        this.context = context;
        this.firestoreDB = firestoreDB;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_match, parent, false);

        return new RecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        final int itemPosition = position;
        final Match match = matchList.get(itemPosition);

        holder.gameType.setText(match.getGameType());
        holder.matchType.setText(match.getMatchType());
        holder.date.setText(match.getDate());
        holder.time.setText(match.getTime());
        holder.place.setText(match.getPlace());
        holder.team1.setText(match.getTeam1());
        holder.team2.setText(match.getTeam2());
        holder.user.setText(match.getUser());
        holder.desc.setText(match.getDesc());
        holder.status.setText(match.getStatus());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMatch(match);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMatch(match.getId(), itemPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView matchType, gameType, team1, team2, place, time, date, score1, score2, user, status, desc;
        ImageView edit;
        ImageView delete;

        ViewHolder(View view) {
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
            user = view.findViewById(R.id.updatedBy);
            status = view.findViewById(R.id.status);
            desc = view.findViewById(R.id.desc);
        }
    }

    private void updateMatch(Match match) {
        Intent intent = new Intent(context, AddSchedule.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("UpdateMatchId", match.getId());
        intent.putExtra("UpdateMatchType", match.getMatchType());
        intent.putExtra("UpdateMatchGameType", match.getGameType());
        intent.putExtra("UpdateMatchTeam1", match.getTeam1());
        intent.putExtra("UpdateMatchTeam2", match.getTeam2());
        intent.putExtra("UpdateMatchTime", match.getTime());
        intent.putExtra("UpdateMatchPlace", match.getPlace());
        intent.putExtra("UpdateMatchDate", match.getDate());
        context.startActivity(intent);
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