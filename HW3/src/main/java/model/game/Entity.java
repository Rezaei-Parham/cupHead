package model.game;

import javafx.scene.image.ImageView;

public class Entity extends ImageView {
    double health;
    boolean isImmune;
    int speed;
    Direction direction;

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public boolean isImmune() {
        return isImmune;
    }

    public void setImmune(boolean immune) {
        isImmune = immune;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean hitUpWall() {
        return this.getY() <= 0;
    }

    public boolean hitLeftWall() {
        return this.getX() <= 0;
    }

    public boolean hitRightWall() {
        return this.getX() + this.getImage().getWidth() >= 1280;
    }

    public boolean hitFloor() {
        return this.getY() + this.getImage().getHeight() >= 720;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public enum Direction {
        left, right, up, down, fixed
    }
}
