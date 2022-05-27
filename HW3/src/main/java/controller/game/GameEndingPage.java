package controller.game;

import enums.GamePreData;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.Game;
import model.game.Score;
import view.Main;

public class GameEndingPage {
    private static int scoreNumber;
    @FXML
    private Label score;
    @FXML
    private Label time;
    @FXML
    private ImageView progressLogo;

    public static int getScore() {
        if (Game.getGameMode().equals(Game.GameMode.NORMAL)) {
            if (Game.getDifficultyLevel().equals(GamePreData.LEVEL1))
                scoreNumber = (int) Math.ceil(100 - GamePane.getGamePane().getBoss().getHealth()) + (int) GamePane.getGamePane().getCupHead().getHealth();
            else if (Game.getDifficultyLevel().equals(GamePreData.LEVEL2))
                scoreNumber = (int) Math.ceil((100 - GamePane.getGamePane().getBoss().getHealth()) * 1.5) + (int) GamePane.getGamePane().getCupHead().getHealth();
            else if (Game.getDifficultyLevel().equals(GamePreData.LEVEL3))
                scoreNumber = (int) Math.ceil((100 - GamePane.getGamePane().getBoss().getHealth()) * 2) + (int) GamePane.getGamePane().getCupHead().getHealth();
        } else
            scoreNumber = (int) Math.ceil(100 - GamePane.getGamePane().getBoss().getHealth()) * 4 + (int) GamePane.getGamePane().getCupHead().getHealth();
        return scoreNumber;
    }

    public void initialize() {
        score.setText(String.valueOf(getScore()));
        time.setText(GamePane.getGamePane().getTime());
        Score.ScoreTypes scoreType;
        if (Game.getGameMode().equals(Game.GameMode.DEVIL)) scoreType = Score.ScoreTypes.DEVIL;
        else {
            if (Game.getDifficultyLevel().equals(GamePreData.LEVEL1)) scoreType = Score.ScoreTypes.LEVEL1;
            else if (Game.getDifficultyLevel().equals(GamePreData.LEVEL2)) scoreType = Score.ScoreTypes.LEVEL2;
            else scoreType = Score.ScoreTypes.LEVEL3;
        }
        if (Game.isNotGuest()) Game.getLoggedInUser().addNewScore(new Score(getScore(), Game.getTime(), scoreType));
        Timeline pageChanger = new Timeline(new KeyFrame(Duration.millis(200), this::endGame));
        pageChanger.play();
        pageChanger.setOnFinished(event -> setProgressLogo());
    }

    private void endGame(ActionEvent event) {
        GamePane.getGamePane().nullifyObjects();
    }

    public void enterMainMenu() {
        Main.changeMenu("MainMenu");
    }

    public void restart() {
        Main.changeMenu("GameScreen");

    }

    private void setProgressLogo() {
        double maxScore = 402;
        if (Game.getGameMode().equals(Game.GameMode.NORMAL)) {
            if (Game.getDifficultyLevel().equals(GamePreData.LEVEL1)) maxScore = 110;
            else if (Game.getDifficultyLevel().equals(GamePreData.LEVEL2)) maxScore = 155;
            else if (Game.getDifficultyLevel().equals(GamePreData.LEVEL3)) maxScore = 202;
        }
        progressLogo.setLayoutY(490 - (float) (scoreNumber) / maxScore * 420);
    }
}
