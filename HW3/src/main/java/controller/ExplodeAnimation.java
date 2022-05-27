package controller;

import controller.game.GamePane;
import enums.ImagesAddress;
import javafx.animation.Transition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class ExplodeAnimation extends Transition {

    private final ImageView explosionImage;
    private int frame = 1;

    public ExplodeAnimation() {
        this.explosionImage = new ImageView(ImagesAddress.BULLET_FIRE.getImage(frame));
        explosionImage.setX(GamePane.getGamePane().getCupHead().getX() + GamePane.getGamePane().getCupHead().getImage().getWidth() - 10);
        explosionImage.setY(GamePane.getGamePane().getCupHead().getY() + 25);
        setCycleCount(1);
        setCycleDuration(Duration.millis(100));
    }

    @Override
    protected void interpolate(double frac) {
        frame = (int) Math.floor(frac * 4) + 1;
        explosionImage.setX(GamePane.getGamePane().getCupHead().getX() + GamePane.getGamePane().getCupHead().getImage().getWidth() - 10);
        explosionImage.setY(GamePane.getGamePane().getCupHead().getY() + 25);
        if (frac != 1)
            this.explosionImage.setImage(ImagesAddress.BULLET_FIRE.getImage(frame));
        if (frac == 1) GamePane.getGamePane().requestRemoving(this.explosionImage);
    }

    public ImageView getExplosionImage() {
        return explosionImage;
    }
}
