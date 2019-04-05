package com.apps.harsh.zest19admin.Model;

import java.util.HashMap;
import java.util.Map;

public class Set {
    public String  id, gameType, matchType, team1, team2, date, time, place, score1, score2, status, set, team1Win, team2Win, result, user;

    public Set() {
    }

    public Set(String gameType, String matchType, String team1, String team2, String date, String time, String place, String score1, String score2, String status, String set, String team1Win, String team2Win, String result, String user) {
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
        this.set = set;
        this.team1Win = team1Win;
        this.team2Win = team2Win;
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

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getTeam1Win() {
        return team1Win;
    }

    public void setTeam1Win(String team1Win) {
        this.team1Win = team1Win;
    }

    public String getTeam2Win() {
        return team2Win;
    }

    public void setTeam2Win(String team2Win) {
        this.team2Win = team2Win;
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
        result.put("Set", this.set);
        result.put("Team1Win", this.team1Win);
        result.put("Team2Win", this.team2Win);
        result.put("Result", this.result);
        result.put("User", this.user);
        return result;
    }
}
