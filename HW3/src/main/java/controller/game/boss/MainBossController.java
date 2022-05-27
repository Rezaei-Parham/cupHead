package controller.game.boss;

import controller.utils.UtilMethods;
import controller.game.GamePane;
import enums.ImagesAddress;
import enums.MusicsAddress;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import model.Game;
import model.game.BabyBoss;
import model.game.Boss;
import model.game.BossEggBullet;
import model.game.Entity;

public class MainBossController {
    private final Boss boss;
    private Timeline moveTimeline;
    private Timeline imageTimeline;
    private Timeline shootingTime;
    private Timeline deathAnimation;

    public MainBossController(Boss boss) {
        this.boss = boss;
        initializeAnimations();
    }

    private void initializeAnimations() {
        moveTimeline = new Timeline(new KeyFrame(Duration.millis(100), this::moveOscillating));
        moveTimeline.setCycleCount(-1);
        imageTimeline = new Timeline(new KeyFrame(Duration.millis(200), this::changeImage));
        shootingTime = new Timeline(new KeyFrame(Duration.seconds(7), this::startShooting));
        shootingTime.setCycleCount(-1);
        imageTimeline.setCycleCount(-1);
        playOscillatingAnimations();

    }

    private void startShooting(ActionEvent event) {
        if(boss == null) return;
        boss.setPictureNumber(1);
        boss.setShooting(true);
    }

    public void playOscillatingAnimations() {
        if(boss == null) return;
        this.moveTimeline.play();
        GamePane.getGamePane().addAnimation(moveTimeline);
        this.imageTimeline.play();
        GamePane.getGamePane().addAnimation(imageTimeline);
        this.shootingTime.play();
        GamePane.getGamePane().addAnimation(shootingTime);
    }


    private void moveOscillating(ActionEvent actionEvent) {
        if(boss == null) return;
        if (UtilMethods.intersect(GamePane.getGamePane().getCupHead(), boss)) {
            if (GamePane.getGamePane().getCupHead().isSuperBomb())
                boss.setHealth(boss.getHealth() - 4 * Game.getDifficultyLevel().getHitDamageRate());
            GamePane.getGamePane().getCupHeadController().hit(false);
        }
        // setting the position to fixed by game type
        if (boss.getHealth() < 55 && Game.getGameMode().equals(Game.GameMode.DEVIL)) boss.setY(100);
        if (boss.getHealth() <= 0) GamePane.getGamePane().endGame(); // end the game by health value
        if (boss.hitUpWall()) boss.setDirection(Entity.Direction.down);
        if (boss.hitFloor()) boss.setDirection(Entity.Direction.up);
        if (boss.getDirection().equals(Entity.Direction.down)) boss.setY(boss.getY() + 10);
        if (boss.getDirection().equals(Entity.Direction.up)) boss.setY(boss.getY() - 10);
    }


    private void changeImage(ActionEvent actionEvent) {
        if(boss == null) return;
        if (!boss.isShooting()) {
            boss.setPictureNumber((boss.getPictureNumber() + 1) % 6 + 1); // frame of shooting image
            boss.setImage(ImagesAddress.BOSS_NORMAL_IMAGE.getImage(boss.getPictureNumber()));
        } else {
            boss.setPictureNumber(boss.getPictureNumber() + 1);
            if (boss.getPictureNumber() == 13) {
                boss.setShooting(false); // stop the shooting
                boss.setPictureNumber(1);
                MusicsAddress.HOOHOO.playSong(); // clock sound
                boss.setX(600);
                return;
            }
            if (boss.getPictureNumber() == 8) MusicsAddress.GHUGHUL.playSong(); // rooster sound
            if (boss.getPictureNumber() > 6) boss.setX(550); // return to previous position
            if (boss.getPictureNumber() == 3) MusicsAddress.BOSS_BARF.playSong(); // barf egg sound
            if (boss.getPictureNumber() == 4) new BossEggBullet();
            boss.setImage(ImagesAddress.BOSS_SHOOTING.getImage(boss.getPictureNumber()));
        }
    }

    public void doDeathProcess() {
        moveTimeline.stop();
        GamePane.getGamePane().removeAnimation(moveTimeline);
        imageTimeline.stop();
        GamePane.getGamePane().removeAnimation(imageTimeline);
        shootingTime.stop();
        GamePane.getGamePane().removeAnimation(shootingTime);
        deathAnimation = new Timeline(new KeyFrame(Duration.millis(100), this::setDieFrames));
        // start the die animation
        boss.setPictureNumber(1);
        deathAnimation.play();
        GamePane.getGamePane().addAnimation(deathAnimation);
        MusicsAddress.BOSS_DEATH.playSong();
    }

    private void setDieFrames(ActionEvent event) {
        if(boss == null) return;
        if (boss.getPictureNumber() >= 27) {
            boss.setPictureNumber(1); // reverting the animation frames
            GamePane.getGamePane().requestRemoving(boss);
            GamePane.getGamePane().removeEnemy(boss); // removing the boss
            GamePane.getGamePane().setBoss(new BabyBoss());
            deathAnimation.stop();
            GamePane.getGamePane().removeAnimation(deathAnimation); // removing the death animation
            return;
        }
        boss.setImage(ImagesAddress.BOSS_DEATH.getImage(boss.getPictureNumber()));
        boss.setPictureNumber(boss.getPictureNumber() + 1); // going to the next frame of death
    }
}
