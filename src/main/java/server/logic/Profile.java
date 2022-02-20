package server.logic;

import com.google.gson.annotations.Expose;

public class Profile {

    @Expose
    private String username;
    @Expose
    private String  password;
    @Expose
    private int wins=0;
    @Expose
    private int lose=0;
    @Expose
    private int score=0;

    public Profile(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
        score=wins-lose;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
        score=wins-lose;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
