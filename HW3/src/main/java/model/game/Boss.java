package model.game;

import controller.game.GamePane;
import controller.game.GameScreenController;
import enums.ImagesAddress;
import model.Game;

public class Boss extends Entity implements Enemy {
    private int pictureNumber;
    private boolean isShooting = false;

    public Boss() {
        GamePane.getGamePane().requestAdding(this);
        GamePane.getGamePane().addEnemy(this);
        this.setHealth(100);
        pictureNumber = 1;
        this.setImage(ImagesAddress.BOSS_NORMAL_IMAGE.getImage(pictureNumber));
        this.setX(600);
        this.setY(100);
        this.setDirection(Direction.up);
    }


    @Override
    public void setHealth(double health) {
        this.setHealth(health);
        GameScreenController.updateHealthBar();
        if (health <= 50 && Game.getGameMode().equals(Game.GameMode.DEVIL)) {
            GamePane.getGamePane().getBossController().doDeathProcess(); // die process
            this.setImmune(true);
        }
    }


    public void getHit(Shootable shootable) {
        if (!isImmune()) {
            if (shootable instanceof Bullet) { // change health by kind of impact
                setHealth(getHealth() - Game.getDifficultyLevel().getHitDamageRate());
            } else if (shootable instanceof Bomb) {
                setHealth(getHealth() - 2 * Game.getDifficultyLevel().getHitDamageRate());
            }
        }
    }


    public int getPictureNumber() {
        return pictureNumber;
    }

    public void setPictureNumber(int pictureNumber) {
        this.pictureNumber = pictureNumber;
    }


    public boolean isShooting() {
        return isShooting;
    }

    public void setShooting(boolean shooting) {
        isShooting = shooting;
    }
}
