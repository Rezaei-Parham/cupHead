package model.game;

import controller.UtilMethods;
import controller.game.BulletExplosion;
import controller.game.GamePane;
import enums.ImagesAddress;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Iterator;

public class Bomb extends ImageView implements Shootable {
    Timeline movementAnimation;

    public Bomb() {
        this.setX(GamePane.getGamePane().getCupHead().getX() + GamePane.getGamePane().getCupHead().getImage().getWidth() / 2);
        this.setY(GamePane.getGamePane().getCupHead().getY() + GamePane.getGamePane().getCupHead().getImage().getHeight());
        movementAnimation = new Timeline(new KeyFrame(Duration.millis(50), this::actMoving));
        this.setImage(ImagesAddress.CUP_HEAD_BOMB.getImage());
        movementAnimation.setCycleCount(Animation.INDEFINITE);
        GamePane.getGamePane().requestAdding(this);
        movementAnimation.play();
        GamePane.getGamePane().addAnimation(movementAnimation);
    }

    private void actMoving(ActionEvent event) {
        if (this.getY() > 1280) {
            GamePane.getGamePane().requestRemoving(this);
            movementAnimation.stop();
            GamePane.getGamePane().removeAnimation(movementAnimation);
            return;
        }

        for (Iterator<Enemy> iterator = GamePane.getGamePane().getEnemies().iterator(); iterator.hasNext(); ) {
            Enemy enemy = iterator.next();
            if (UtilMethods.intersect(this, (ImageView) enemy)) {
                enemy.getHit(this);
                explode();
                return;
            }
        }
        this.setY(this.getY() + 20);
        this.setX(this.getX() + 1);
        this.setRotate(this.getRotate() + 2);
    }

    private void explode() {
        movementAnimation.stop();
        GamePane.getGamePane().removeAnimation(movementAnimation);
        GamePane.getGamePane().requestRemoving(this);
        new BulletExplosion(this);
    }
}
