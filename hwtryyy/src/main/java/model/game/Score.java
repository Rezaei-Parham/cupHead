package model.game;

public class Score implements Comparable<Score> {
    private int scoreNumber;
    private int time;
    private ScoreTypes type;

    public Score(int score, int time, ScoreTypes type) {
        this.scoreNumber = score;
        this.time = time;
        this.type = type;
    }

    public int getScoreNumber() {
        return scoreNumber;
    }

    public void setScoreNumber(int scoreNumber) {
        this.scoreNumber = scoreNumber;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public ScoreTypes getType() {
        return type;
    }

    public void setType(ScoreTypes type) {
        this.type = type;
    }

    public void updateScore(Score score1) {
        if (this.compareTo(score1) < 0) {
            this.scoreNumber = score1.getScoreNumber();
            this.time = score1.getTime();
        }
    }

    @Override
    public int compareTo(Score score) {
        if (this.scoreNumber > score.getScoreNumber()) return 1;
        else if (this.scoreNumber == score.getScoreNumber()) if (this.time < score.getTime()) return 1;
        return -1;
    }

    public enum ScoreTypes {
        LEVEL1, LEVEL2, LEVEL3, DEVIL
    }

}
