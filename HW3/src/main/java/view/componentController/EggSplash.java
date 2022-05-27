package view.componentController;

import controller.game.GamePane;
import enums.ImagesAddress;
import enums.MusicsAddress;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class EggSplash extends ImageView {
    private final Timeline animation;
    private int pictureFrame = 1;

    public EggSplash(ImageView egg) {
        Timeline splashAnimation = new Timeline(new KeyFrame(Duration.millis(50), this::animateSplash));
        splashAnimation.setCycleCount(9); // animation rounds
        animation = splashAnimation;
        this.setX(egg.getX()); // setting the position
        this.setY(egg.getY());
        this.setImage(ImagesAddress.SPLASHED_EGG.getImage(1));
        GamePane.getGamePane().requestAdding(this);
        splashAnimation.play();
        GamePane.getGamePane().addAnimation(splashAnimation);
        MusicsAddress.EGG_SPLASH.playSong(); // egg splashing song
    }

    private void animateSplash(ActionEvent event) {
        if (pictureFrame >= 9) { // ending the animation
            GamePane.getGamePane().requestRemoving(this);
            animation.stop();
            GamePane.getGamePane().removeAnimation(animation);
            return;
        }
        this.setImage(ImagesAddress.SPLASHED_EGG.getImage(pictureFrame));
        pictureFrame++;
    }

}
