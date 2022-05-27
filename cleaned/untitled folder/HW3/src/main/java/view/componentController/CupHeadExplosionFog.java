package view.componentController;

import controller.game.GamePane;
import controller.game.cuphead.CupHeadExplosionFogController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class CupHeadExplosionFog extends ImageView {
    private Timeline explosionAnimation;
    private int frameCount = 1;
    private ImageView lightning;
    private boolean isFired;

    public CupHeadExplosionFog(boolean isFire) {
        CupHeadExplosionFogController controller = new CupHeadExplosionFogController(this);
        isFired = isFire; // if contains fire
        explosionAnimation = new Timeline(new KeyFrame(Duration.millis(100), controller::animatePhoto));
        lightning = new ImageView();
        this.setX(GamePane.getGamePane().getCupHead().getX()); // setting position
        this.setY(GamePane.getGamePane().getCupHead().getY());
        lightning.setX(GamePane.getGamePane().getCupHead().getX()); // lightning position
        lightning.setY(GamePane.getGamePane().getCupHead().getY());
        explosionAnimation.setCycleCount(10);
        GamePane.getGamePane().requestAdding(this);
        GamePane.getGamePane().requestAdding(lightning);
        explosionAnimation.play();
        GamePane.getGamePane().addAnimation(explosionAnimation); // adding animation to saved ones
        GamePane.getGamePane().getCupHead().setOpacity(0);
        GamePane.getGamePane().getCupHead().setImmune(true); // cupHead is immune for a second
    }


    public Timeline getExplosionAnimation() {
        return explosionAnimation;
    }

    public void setExplosionAnimation(Timeline explosionAnimation) {
        this.explosionAnimation = explosionAnimation;
    }

    public int getFrameCount() {
        return frameCount;
    }

    public void setFrameCount(int frameCount) {
        this.frameCount = frameCount;
    }

    public ImageView getLightning() {
        return lightning;
    }

    public void setLightning(ImageView lightning) {
        this.lightning = lightning;
    }

    public boolean isFired() {
        return isFired;
    }

    public void setFired(boolean fired) {
        isFired = fired;
    }
}
