package controller.utils;

public class RepresentableScore {
    private final String username;
    private final int level1Score;
    private final int level1Time;
    private final int level2Score;
    private final int level2Time;
    private final int level3Score;
    private final int level3Time;
    private final int devilScore;
    private final int devilTime;

    public RepresentableScore(String username, int score1, int time1,
                              int score2, int time2, int score3, int time3, int scoreDevil, int timeDevil) {
        this.username = username;
        this.level1Score = score1;
        this.level1Time = time1;
        this.level2Score = score2;
        this.level2Time = time2;
        this.level3Score = score3;
        this.level3Time = time3;
        this.devilScore = scoreDevil;
        this.devilTime = timeDevil;
    }

    public String getUsername() {
        return username;
    }

    public int getLevel1Score() {
        return level1Score;
    }

    public int getLevel1Time() {
        return level1Time;
    }

    public int getLevel2Score() {
        return level2Score;
    }

    public int getLevel2Time() {
        return level2Time;
    }

    public int getLevel3Score() {
        return level3Score;
    }

    public int getLevel3Time() {
        return level3Time;
    }

    public int getDevilScore() {
        return devilScore;
    }

    public int getDevilTime() {
        return devilTime;
    }
}