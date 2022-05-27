package model.game;

import controller.utils.UtilMethods;
import controller.game.GamePane;
import enums.ImagesAddress;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class BossEggBullet extends ImageView implements Shootable {
    private final Timeline movementAnimation;

    public BossEggBullet() {
        this.setX(GamePane.getGamePane().getBoss().getX()); // set position
        this.setY(GamePane.getGamePane().getBoss().getY()
                + GamePane.getGamePane().getBoss().getImage().getHeight() / 2);
        movementAnimation = new Timeline(new KeyFrame(Duration.millis(100), this::moveAndRotate));
        movementAnimation.setCycleCount(Animation.INDEFINITE); // movement animation
        this.setImage(ImagesAddress.BOSS_EGG.getImage());
        GamePane.getGamePane().requestAdding(this);
        movementAnimation.play();
        GamePane.getGamePane().addAnimation(movementAnimation);
    }

    private void moveAndRotate(ActionEvent event) {
        this.setX(this.getX() - 20); // turn moving
        this.setRotate(this.getRotate() + 30); // turn rotating
        if (this.getX() + this.getImage().getWidth() < 0) { // out of the scene
            GamePane.getGamePane().requestRemoving(this);
            movementAnimation.stop();
            GamePane.getGamePane().removeAnimation(movementAnimation);
        }
        if (UtilMethods.intersect(this, GamePane.getGamePane().getCupHead())) { // hit cupHead
            GamePane.getGamePane().getCupHeadController().hit(true);
            movementAnimation.stop();
            GamePane.getGamePane().removeAnimation(movementAnimation);
            GamePane.getGamePane().requestRemoving(this);
        }
    }
}
