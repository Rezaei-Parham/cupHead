package model.game;

import controller.game.GamePane;
import controller.game.GameScreenController;
import enums.ImagesAddress;
import javafx.scene.image.ImageView;
import model.Game;

import java.util.HashMap;

public class BabyBoss extends Entity implements Enemy {
    private HashMap<Integer, ImageView> rotatingEggs = new HashMap<>();
    private int presentImageIndex = 1;
    private double angle = 0;

    public BabyBoss() {
        this.setHealth(50);
        setImage(ImagesAddress.BABY_BOSS.getImage(1));
        setX(800 - this.getImage().getWidth() / 2); // set position
        setY(360 - this.getImage().getHeight() / 2);
        rotatingEggs.put(0, new ImageView(ImagesAddress.EGG_PHASE2.getImage())); // set images
        rotatingEggs.put(1, new ImageView(ImagesAddress.EGG_PHASE2.getImage()));
        rotatingEggs.put(2, new ImageView(ImagesAddress.EGG_PHASE2.getImage()));
        rotatingEggs.put(3, new ImageView(ImagesAddress.EGG_PHASE2.getImage()));
        GamePane.getGamePane().requestAdding(this); // add to page
        GamePane.getGamePane().requestAdding
                (rotatingEggs.get(0), rotatingEggs.get(1), rotatingEggs.get(2), rotatingEggs.get(3));
        GamePane.getGamePane().addEnemy(this);
    }


    @Override
    public void setHealth(double health) {
        super.setHealth(health);
        GameScreenController.updateHealthBar(); // updating the health bar
        if (health <= 0) GamePane.getGamePane().endGame(); // ending the game
    }


    public void getHit(Shootable shootable) { // getting damaged by hitting
        if (!isImmune()) this.setHealth(this.getHealth() - Game.getDifficultyLevel().getHitDamageRate());
    }

    public HashMap<Integer, ImageView> getRotatingEggs() {
        return rotatingEggs;
    }

    public void setRotatingEggs(HashMap<Integer, ImageView> rotatingEggs) {
        this.rotatingEggs = rotatingEggs;
    }

    public int getPresentImageIndex() {
        return presentImageIndex;
    }

    public void setPresentImageIndex(int presentImageIndex) {
        this.presentImageIndex = presentImageIndex;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
}
