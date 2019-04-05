package com.apps.harsh.zest19admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class Update extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        LinearLayout hockey = findViewById(R.id.hockey);
        LinearLayout kabbadi = findViewById(R.id.kabbadi);
        LinearLayout kho_kho = findViewById(R.id.kho_kho);
        LinearLayout football = findViewById(R.id.football);
        LinearLayout handball = findViewById(R.id.handball);
        LinearLayout basketball = findViewById(R.id.basketball);
        LinearLayout volleyball = findViewById(R.id.volleyball);
        LinearLayout badminton = findViewById(R.id.badminton);
        LinearLayout lawntennis = findViewById(R.id.lawntennis);
        LinearLayout tabletennis = findViewById(R.id.tabletennis);
        LinearLayout ballbadminton = findViewById(R.id.ballbadminton);
        LinearLayout baseball = findViewById(R.id.baseball);
        LinearLayout cricket = findViewById(R.id.cricket);
        LinearLayout boxcricket = findViewById(R.id.boxcricket);
        LinearLayout mixcricket = findViewById(R.id.mixcricket);
        hockey.setOnClickListener(this);
        kabbadi.setOnClickListener(this);
        hockey.setOnClickListener(this);
        kho_kho.setOnClickListener(this);
        football.setOnClickListener(this);
        handball.setOnClickListener(this);
        baseball.setOnClickListener(this);
        basketball.setOnClickListener(this);
        volleyball.setOnClickListener(this);
        badminton.setOnClickListener(this);
        lawntennis.setOnClickListener(this);
        tabletennis.setOnClickListener(this);
        ballbadminton.setOnClickListener(this);
        cricket.setOnClickListener(this);
        boxcricket.setOnClickListener(this);
        mixcricket.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, ListType.class);
        Bundle bundle = new Bundle();
        switch (v.getId()){
            case R.id.hockey:
                bundle.putString("Title",getString(R.string.hockey));
                break;
            case R.id.kabbadi:
                bundle.putString("Title",getString(R.string.kabaddi));
                break;
            case R.id.kho_kho:
                bundle.putString("Title",getString(R.string.kho_kho));
                break;
            case R.id.football:
                bundle.putString("Title",getString(R.string.football));
                break;
            case R.id.handball:
                bundle.putString("Title",getString(R.string.handball));
                break;
            case R.id.basketball:
                bundle.putString("Title",getString(R.string.basket_ball));
                break;
            case R.id.volleyball:
                bundle.putString("Title",getString(R.string.volley_ball));
                break;
            case R.id.badminton:
                bundle.putString("Title",getString(R.string.badminton));
                break;
            case R.id.lawntennis:
                bundle.putString("Title",getString(R.string.lawn_tennis));
                break;
            case R.id.tabletennis:
                bundle.putString("Title",getString(R.string.table_tennis));
                break;
            case R.id.ballbadminton:
                bundle.putString("Title",getString(R.string.ball_badminton));
                break;
            case R.id.baseball:
                bundle.putString("Title",getString(R.string.base_ball));
                break;
            case R.id.cricket:
                bundle.putString("Title",getString(R.string.cricket));
                break;
            case R.id.boxcricket:
                bundle.putString("Title",getString(R.string.box_cricket));
                break;
            case R.id.mixcricket:
                bundle.putString("Title",getString(R.string.mix_cricket));
                break;
            default:
                break;
        }

        intent.putExtras(bundle);
        startActivity(intent);

    }
}

