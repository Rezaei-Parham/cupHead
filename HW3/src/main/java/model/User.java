package model;

import model.game.Score;

import java.net.MalformedURLException;
import java.util.HashMap;

public class User {
    private String username;
    private String password;
    private String avatarURL;
    private final HashMap<Score.ScoreTypes, Score> scores = new HashMap<>();


    public User(String username, String password) {
        this.username = username;
        this.password = password;
        scores.put(Score.ScoreTypes.DEVIL, new Score(0, 0, Score.ScoreTypes.DEVIL));
        scores.put(Score.ScoreTypes.LEVEL1, new Score(0, 0, Score.ScoreTypes.LEVEL1));
        scores.put(Score.ScoreTypes.LEVEL2, new Score(0, 0, Score.ScoreTypes.LEVEL2));
        scores.put(Score.ScoreTypes.LEVEL3, new Score(0, 0, Score.ScoreTypes.LEVEL3));
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        try {
            Database.saveToJson();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        try {
            Database.saveToJson();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
        try {
            Database.saveToJson();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void addNewScore(Score score) {
        switch (score.getType()) {
            case DEVIL:
                scores.get(Score.ScoreTypes.DEVIL).updateScore(score);
                break;
            case LEVEL1:
                scores.get(Score.ScoreTypes.LEVEL1).updateScore(score);
                break;
            case LEVEL2:
                scores.get(Score.ScoreTypes.LEVEL2).updateScore(score);
                break;
            case LEVEL3:
                scores.get(Score.ScoreTypes.LEVEL3).updateScore(score);
                break;
        }
        try {
            Database.saveToJson();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public HashMap<Score.ScoreTypes, Score> getScores() {
        return scores;
    }
}
