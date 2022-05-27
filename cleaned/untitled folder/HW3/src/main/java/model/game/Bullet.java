package model.game;

import controller.utils.UtilMethods;
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

public class Bullet extends ImageView implements Shootable {

    private final Timeline movementAnimation;

    public Bullet() {
        GamePane.getGamePane().requestAdding(this);
        setX(GamePane.getGamePane().getCupHead().getX() // set the position
                + GamePane.getGamePane().getCupHead().getImage().getWidth());
        setY(GamePane.getGamePane().getCupHead().getY()
                + GamePane.getGamePane().getCupHead().getImage().getHeight() / 2);
        this.setImage(ImagesAddress.CUP_HEAD_BULLET.getImage());
        movementAnimation = new Timeline(new KeyFrame(Duration.millis(40), this::moveAnimator));
        movementAnimation.setCycleCount(Animation.INDEFINITE);
        GamePane.getGamePane().addAnimation(movementAnimation);
    }

    private void moveAnimator(ActionEvent event) {
        for (Iterator<Enemy> iterator = GamePane.getGamePane().getEnemies().iterator(); iterator.hasNext(); ) {
            Enemy enemy = iterator.next(); // for solving concurrent modification
            if (UtilMethods.intersect(this, (ImageView) enemy)) { // intersection with enemies
                enemy.getHit(this); // hit enemy
                new BulletExplosion(this);
                this.movementAnimation.stop();
                GamePane.getGamePane().removeAnimation(movementAnimation);
                GamePane.getGamePane().requestRemoving(this);
                return;
            }
        }

        if (this.getX() >= 1290) { // out of the scene
            this.movementAnimation.stop();
            GamePane.getGamePane().removeAnimation(movementAnimation);
        }
        this.setX(this.getX() + 25);
    }


    public void startMoving() {
        this.movementAnimation.play();
        GamePane.getGamePane().addAnimation(movementAnimation);
    }


}
