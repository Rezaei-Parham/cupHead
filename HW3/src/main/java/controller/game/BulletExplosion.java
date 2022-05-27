package controller.game;

import enums.ImagesAddress;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.game.Shootable;

public class BulletExplosion extends ImageView {
    private final Timeline photoAnimation;
    private int frame = 0;

    public BulletExplosion(Shootable bullet) {
        if (bullet instanceof ImageView) {
            this.setX(((ImageView) bullet).getX() + ((ImageView) bullet).getImage().getWidth() / 2);
            this.setY(((ImageView) bullet).getY() + ((ImageView) bullet).getImage().getHeight() / 2);
        }
        photoAnimation = new Timeline(new KeyFrame(Duration.millis(50), this::animatePhoto));
        photoAnimation.setCycleCount(6);
        GamePane.getGamePane().requestAdding(this);
        photoAnimation.play();
    }

    private void animatePhoto(ActionEvent event) {
        frame++;
        if (frame >= 6) {
            photoAnimation.stop();
            GamePane.getGamePane().requestRemoving(this);
            return;
        }
        this.setImage(ImagesAddress.BULLET_EXPLOSION.getImage(frame));
    }
}
