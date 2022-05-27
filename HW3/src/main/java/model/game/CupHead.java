package model.game;

import controller.game.GamePane;
import controller.game.GameScreenController;
import enums.ImagesAddress;
import model.Game;

import java.util.Vector;

public class CupHead extends Entity {
    private int superBombCounter = 0;
    private int pictureFrame = 1;
    private boolean isSuperBomb = false;
    private ShooterEnum shootingState;


    private Vector<Direction> direction;

    public CupHead() {
        setHealth(Game.getDifficultyLevel().getNumberOfLives());
        GamePane.getGamePane().requestAdding(this);
        this.shootingState = ShooterEnum.BULLET;
        direction = new Vector<>();
        this.setX(40);
        this.setY(40);
        this.setImage(ImagesAddress.CUP_HEAD.getImage());
        GamePane.getGamePane().setCupHead(this);
    }

    public void removeDirection(Entity.Direction direction) {
        this.direction.remove(direction);
    }

    public ShooterEnum getShootingState() {
        return shootingState;
    }

    public void changeShootingStyle() {
        if (this.shootingState.equals(CupHead.ShooterEnum.BULLET)) {
            this.shootingState = ShooterEnum.BOMB;
        } else this.shootingState = ShooterEnum.BULLET;
    }

    public void superBombRequest() {
        if (this.superBombCounter >= 15) {
            superBombCounter = 0;
            GamePane.getGamePane().getCupHeadController().becomeSuperBomb();
        }
    }

    public void becomeSuperBomb() {
        isSuperBomb = true;
        isImmune = true;
    }

    public boolean isSuperBomb() {
        return isSuperBomb;
    }

    public void setSuperBomb(boolean superBomb) {
        this.isSuperBomb = superBomb;
    }

    public int getSuperBombCounter() {
        return superBombCounter;
    }

    public void setSuperBombCounter(int superBombCounter) {
        this.superBombCounter = superBombCounter;
    }

    @Override
    public void setHealth(double health) {
        this.health = health;
        GameScreenController.updateCupHeadLives();
    }

    public int getPictureFrame() {
        return pictureFrame;
    }

    public void setPictureFrame(int pictureFrame) {
        this.pictureFrame = pictureFrame;
    }

    public Vector<Direction> getDirectionVector() {
        return direction;
    }

    public void setDirection(Entity.Direction direction) {
        if (!this.direction.contains(direction)) this.direction.add(direction);
    }

    public void setDirection(Vector<Direction> direction) {
        this.direction = direction;
    }

    public enum ShooterEnum {
        BOMB, BULLET
    }
}
