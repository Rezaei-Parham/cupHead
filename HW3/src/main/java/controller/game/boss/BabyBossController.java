package controller.game.boss;

import controller.utils.UtilMethods;
import controller.game.GamePane;
import enums.ImagesAddress;
import enums.MusicsAddress;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.Game;
import model.game.BabyBoss;
import model.game.LaserBullet;
import view.componentController.EggSplash;

import java.util.Iterator;

public class BabyBossController {
    private final BabyBoss boss;

    public BabyBossController(BabyBoss boss) {
        this.boss = boss;
        initializeAnimations();
    }

    private void initializeAnimations() {
        Timeline movingAnimation = new Timeline(new KeyFrame(Duration.millis(50), this::randomMove));
        Timeline bossImageAnimation = new Timeline(new KeyFrame(Duration.millis(50), this::changeImage));
        movingAnimation.setCycleCount(Animation.INDEFINITE);
        bossImageAnimation.setCycleCount(Animation.INDEFINITE);
        movingAnimation.play();
        GamePane.getGamePane().addAnimation(movingAnimation);
        bossImageAnimation.play();
        GamePane.getGamePane().addAnimation(bossImageAnimation);
    }

    private void changeImage(ActionEvent event) {
        if(boss == null) return;
        // finishing the image by laser bullet
        if (boss.getPresentImageIndex() >= 36) boss.setPresentImageIndex(1);
        if (boss.getPresentImageIndex() == 27) MusicsAddress.BABY_LASER.playSong();
        if (boss.getPresentImageIndex() == 28) new LaserBullet();
        boss.setImage(ImagesAddress.BABY_BOSS.getImage(boss.getPresentImageIndex()));
        boss.setPresentImageIndex(boss.getPresentImageIndex() + 1);
    }

    private void randomMove(ActionEvent event) {
        if(boss == null) return;
        if (boss.getAngle() == 360) boss.setAngle(0); // resetting the angle
        boss.setX(800 + 300 * Math.cos(Math.toRadians(boss.getAngle())) - boss.getImage().getWidth() / 2);
        boss.setY(360 - 100 * Math.sin(Math.toRadians(boss.getAngle())) - boss.getImage().getHeight() / 2);
        for (Integer i : boss.getRotatingEggs().keySet()) {
            boss.getRotatingEggs().get(i).setX(boss.getX() +
                    boss.getImage().getWidth() / 2 + 300 * Math.cos(Math.toRadians(boss.getAngle() + i * 90)));
            boss.getRotatingEggs().get(i).setY(boss.getY() +
                    boss.getImage().getHeight() / 2 - 300 * Math.sin(Math.toRadians(boss.getAngle() + i * 90)));
        }
        doHittingAction();
        boss.setAngle(boss.getAngle() + 10);
    }

    private void doHittingAction() {
        if(boss == null) return;
        for (Iterator<Integer> iterator = boss.getRotatingEggs().keySet().iterator(); iterator.hasNext(); ) {
            int index = iterator.next(); // check if next one exists
            ImageView egg = boss.getRotatingEggs().get(index); // egg which will be splashing
            if (UtilMethods.intersect(egg, GamePane.getGamePane().getCupHead())) {
                GamePane.getGamePane().getCupHeadController().hit(false);
                boss.getRotatingEggs().remove(index);
                GamePane.getGamePane().requestRemoving(egg);
                new EggSplash(egg); // start new egg splash animation
                return;
            }
        }
        if (UtilMethods.intersect(boss, GamePane.getGamePane().getCupHead())) {
            GamePane.getGamePane().getCupHeadController().hit(false);
            if (GamePane.getGamePane().getCupHead().isSuperBomb()) // hit by the ratio of damage
                boss.setHealth(boss.getHealth() - 4 * Game.getDifficultyLevel().getHitDamageRate());
        }
    }

}
