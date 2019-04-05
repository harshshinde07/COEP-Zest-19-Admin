package com.apps.harsh.zest19admin.Model;

import java.util.HashMap;
import java.util.Map;

public class Cricket {
    public String  id, gameType, matchType, team1, team2, date, time, place, score1, score2, status, ball, inning, over, target, wicket, result, run, user;

    public Cricket() {
    }

    public Cricket(String gameType, String matchType, String team1, String team2, String date, String time, String place, String score1, String score2, String status, String ball, String inning, String over, String target, String wicket, String result, String run, String user) {
        this.gameType = gameType;
        this.matchType = matchType;
        this.team1 = team1;
        this.team2 = team2;
        this.date = date;
        this.time = time;
        this.place = place;
        this.score1 = score1;
        this.score2 = score2;
        this.status = status;
        this.ball = ball;
        this.inning = inning;
        this.over = over;
        this.target = target;
        this.wicket = wicket;
        this.result = result;
        this.run = run;
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getScore1() {
        return score1;
    }

    public void setScore1(String score1) {
        this.score1 = score1;
    }

    public String getScore2() {
        return score2;
    }

    public void setScore2(String score2) {
        this.score2 = score2;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBall() {
        return ball;
    }

    public void setBall(String ball) {
        this.ball = ball;
    }

    public String getInning() {
        return inning;
    }

    public void setInning(String inning) {
        this.inning = inning;
    }

    public String getOver() {
        return over;
    }

    public void setOver(String over) {
        this.over = over;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getWicket() {
        return wicket;
    }

    public void setWicket(String wicket) {
        this.wicket = wicket;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRun() {
        return run;
    }

    public void setRun(String run) {
        this.run = run;
    }

    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("GameType", this.gameType);
        result.put("MatchType", this.matchType);
        result.put("Team1", this.team1);
        result.put("Team2", this.team2);
        result.put("Date", this.date);
        result.put("Time", this.time);
        result.put("Place", this.place);
        result.put("Score1", this.score1);
        result.put("Score2", this.score2);
        result.put("Status", this.status);
        result.put("Ball", this.ball);
        result.put("Inning", this.inning);
        result.put("Over", this.over);
        result.put("Target", this.target);
        result.put("Wicket", this.wicket);
        result.put("Result", this.result);
        result.put("Run", this.run);
        result.put("User", this.user);
        return result;
    }
}
