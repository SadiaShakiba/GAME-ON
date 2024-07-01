package com.example.onlinegamingplatformaoop;

public class modelTable {
    String game_name;
    int played, won, lost,score,highScore;
    double percentage;

    public modelTable(String game_name, int played, int won, int lost, int score, int highScore, double percentage) {
        this.game_name = game_name;
        this.played = played;
        this.won = won;
        this.lost = lost;
        this.score = score;
        this.highScore = highScore;
        this.percentage = percentage;
    }

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    public int getPlayed() {
        return played;
    }

    public void setPlayed(int played) {
        this.played = played;
    }

    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        percentage = percentage;
    }
}