package model.game;

import controller.UtilMethods;
import controller.game.GamePane;
import enums.ImagesAddress;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class LaserBullet extends ImageView implements Shootable {
    private final Timeline moveAnimation;

    public LaserBullet() {
        this.setX(GamePane.getGamePane().getBoss().getX());
        this.setY(GamePane.getGamePane().getBoss().getY() + GamePane.getGamePane().getBoss().getImage().getHeight() / 2);
        this.setImage(ImagesAddress.BABY_BOSS_BULLET.getImage());
        moveAnimation = new Timeline(new KeyFrame(Duration.millis(30), this::moveLaser));
        moveAnimation.setCycleCount(Animation.INDEFINITE);
        GamePane.getGamePane().requestAdding(this);
        moveAnimation.play();
        GamePane.getGamePane().addAnimation(moveAnimation);
    }

    private void moveLaser(ActionEvent event) {
        if (getX() < 0) {
            GamePane.getGamePane().requestRemoving(this);
            moveAnimation.stop();
            GamePane.getGamePane().removeAnimation(moveAnimation);
            return;
        }
        if (UtilMethods.intersect(this, GamePane.getGamePane().getCupHead())) {
            GamePane.getGamePane().getCupHeadController().hit(true);
            GamePane.getGamePane().requestRemoving(this);
            moveAnimation.stop();
            GamePane.getGamePane().removeAnimation(moveAnimation);
            return;
        }
        setX(getX() - 20);
    }
}
