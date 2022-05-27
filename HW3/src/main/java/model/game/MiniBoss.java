package model.game;

import controller.utils.UtilMethods;
import controller.game.GamePane;
import controller.game.boss.MiniBossController;
import enums.ImagesAddress;

public class MiniBoss extends Entity implements Enemy {
    private final MiniBossController controller;
    private int pictureFrame = 1;
    private int colorNumber; //yellow 1 pink 0

    public MiniBoss(int i) {
        controller = new MiniBossController(this);
        GamePane.getGamePane().addEnemy(this);
        colorNumber = Math.abs(i % 2);
        setHealth(2); // set pre health
        if (i % 2 == 0) this.setImage(ImagesAddress.MINI_BOSS_PURPLE.getImage(pictureFrame));
        else this.setImage(ImagesAddress.MINI_BOSS_YELLOW.getImage(pictureFrame));
        this.setX(1280 + this.getImage().getWidth() * i);
        this.setY(500);
        GamePane.getGamePane().requestAdding(this);
    }


    public void checkCupHeadIntersection() {
        if (UtilMethods.intersect(this, GamePane.getGamePane().getCupHead())) { // check if intersects cupHead
            GamePane.getGamePane().getCupHeadController().hit(false);
            this.setHealth(0);
            explode();
        }
    }

    public void getHit(Shootable shootable) {
        if (shootable instanceof Bullet) setHealth(getHealth() - 1);
        else if (shootable instanceof Bomb) setHealth(0);
        if (getHealth() == 0) explode();
    }


    private void explode() {
        controller.stopAnimations();
        pictureFrame = 1;
        GamePane.getGamePane().removeEnemy(this);
    }


    public int getPictureFrame() {
        return pictureFrame;
    }

    public void setPictureFrame(int pictureFrame) {
        this.pictureFrame = pictureFrame;
    }

    public int getColorNumber() {
        return colorNumber;
    }

    public void setColorNumber(int colorNumber) {
        this.colorNumber = colorNumber;
    }
}
