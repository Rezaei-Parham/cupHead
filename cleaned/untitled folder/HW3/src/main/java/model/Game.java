package model;

import controller.game.GamePane;
import controller.game.GameScreenController;
import enums.GamePreData;
import enums.MusicsAddress;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class Game {
    private static User loggedInUser;
    private static GameMode gameMode;
    private static GamePreData difficultyLevel = GamePreData.LEVEL2;
    private static boolean isMute;
    private static int time = 0;
    private static Timeline timer; // in game timer
    private static String picturesDirectory = "colored"; // color of the game

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser) {
        Game.loggedInUser = loggedInUser;
    }

    public static GamePreData getDifficultyLevel() {
        return difficultyLevel;
    }

    public static void setDifficultyLevel(GamePreData difficultyLevel) {
        Game.difficultyLevel = difficultyLevel;
    }

    public static GameMode getGameMode() {
        return gameMode;
    }

    public static void setGameMode(GameMode gameMode) {
        Game.gameMode = gameMode;
    }

    public static void setBlackAndWhite() {
        Game.picturesDirectory = "bw";
    }

    public static void setColored() {
        Game.picturesDirectory = "colored";
    }

    public static String getPicturesDirectory() {
        return picturesDirectory;
    }

    public static boolean isMute() {
        return isMute;
    }

    public static void mute() {
        Game.isMute = true;
        MusicsAddress.GAME_MUSIC.muteMusic();
    }

    public static void unMute() {
        Game.isMute = false;
        MusicsAddress.GAME_MUSIC.resumeMusic();
    }

    public static void startMusic(){
        if(!Game.isMute()) MusicsAddress.GAME_MUSIC.playMusic(); // starting the music
    }
    public static void preSetTimer() {
        time = 0;
        timer = new Timeline(new KeyFrame(Duration.seconds(1), Game::setTime));
        timer.setCycleCount(Animation.INDEFINITE);
        timer.play(); // starting the timer
    }

    public static int getTime() {
        return time;
    }

    private static void setTime(ActionEvent event) {
        time++;
        GameScreenController.updateTimer();
        GameScreenController.updateSuperBombProgressBar();
        GameScreenController.updateShowingScore();
        if (GamePane.getGamePane().getCupHead() != null)
            GamePane.getGamePane().getCupHeadController().increaseSuperEnergy(1);
    }

    public static void startTimer() {
        timer.play();
    }

    public static void stopTimer() {
        timer.stop();
    }

    public static boolean isNotGuest() {
        return loggedInUser != null;
    }

    public enum GameMode {
        NORMAL, DEVIL
    }
}
