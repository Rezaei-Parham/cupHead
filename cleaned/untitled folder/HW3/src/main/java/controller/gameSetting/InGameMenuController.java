package controller.gameSetting;

import controller.game.GamePane;
import enums.ImagesAddress;
import enums.MusicsAddress;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.Game;
import view.Main;


public class InGameMenuController {
    @FXML
    private ImageView volume;
    @FXML
    private VBox helpList;

    public void initialize() {
        if (Game.isMute()) volume.setImage(ImagesAddress.MENU_VOLUME_OFF.getImage());
        else volume.setImage(ImagesAddress.MENU_VOLUME_On.getImage());
    }

    public void helpAction() {
        if(helpList.getOpacity() == 1)
            helpList.setOpacity(0); // change opacity of the help text
        else helpList.setOpacity(1);
    }

    public void changeMusicState() {
        if (Game.isMute()) {
            Game.unMute(); // unmute the game
            volume.setImage(ImagesAddress.MENU_VOLUME_On.getImage()); // change the icon of volume
            return;
        }
        Game.mute();
        volume.setImage(ImagesAddress.MENU_VOLUME_OFF.getImage()); // change the icon of the volume
    }

    public void resumeGame() {
        GamePane.getGamePane().restartAnimations();
        Main.changeMenu("GameScreen");
    }

    public void exitGame(MouseEvent event) {
        Main.closeApp();
    }

    public void restart() {
        GamePane.getGamePane().stopAnimations();
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(2),
                        new KeyValue(MusicsAddress.GAME_MUSIC.getPlayer().volumeProperty(), 0)));
        timeline.setOnFinished(event -> MusicsAddress.GAME_MUSIC.stopMusic());
        timeline.play();
        GamePane.getGamePane().nullifyObjects();
        Main.changeMenu("GameSetting");
    }

}
