package com.apps.harsh.zest19admin.Model;

import java.util.HashMap;
import java.util.Map;

public class Baseball {

    public String id, gameType, matchType, team1, team2, date, time, place, score1, score2, status, away1, away2, status1, status2, inning, result, user;

    public Baseball() {
    }

    public Baseball(String gameType, String matchType, String team1, String team2, String date, String time, String place, String score1, String score2, String status, String away1, String away2, String status1, String status2, String inning, String result, String user) {
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
        this.away1 = away1;
        this.away2 = away2;
        this.status1 = status1;
        this.status2 = status2;
        this.inning = inning;
        this.result = result;
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

    public String getAway1() {
        return away1;
    }

    public void setAway1(String away1) {
        this.away1 = away1;
    }

    public String getAway2() {
        return away2;
    }

    public void setAway2(String away2) {
        this.away2 = away2;
    }

    public String getStatus1() {
        return status1;
    }

    public void setStatus1(String status1) {
        this.status1 = status1;
    }

    public String getStatus2() {
        return status2;
    }

    public void setStatus2(String status2) {
        this.status2 = status2;
    }

    public String getInning() {
        return inning;
    }

    public void setInning(String inning) {
        this.inning = inning;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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
        result.put("Away1", this.away1);
        result.put("Away2", this.away2);
        result.put("Status1", this.status1);
        result.put("Status2", this.status2);
        result.put("Inning", this.inning);
        result.put("result", this.result);
        result.put("User", this.user);
        return result;
    }
}
