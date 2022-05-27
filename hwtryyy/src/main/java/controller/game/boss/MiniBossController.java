package controller.game.boss;

import controller.game.GamePane;
import enums.ImagesAddress;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import model.game.MiniBoss;

public class MiniBossController {
    private final MiniBoss miniBoss;
    private Timeline movementAnimation;
    private Timeline flappingAnimation;
    private Timeline explosionAnimation;

    public MiniBossController(MiniBoss miniBoss) {
        this.miniBoss = miniBoss;
        initializeAnimations();
    }

    private void initializeAnimations() {
        flappingAnimation = new Timeline(new KeyFrame(Duration.millis(150), this::animateFlapping));
        flappingAnimation.setCycleCount(Animation.INDEFINITE); // flap forever
        movementAnimation = new Timeline(new KeyFrame(Duration.millis(100), this::animateMove));
        movementAnimation.setCycleCount(Animation.INDEFINITE); // move forever
        explosionAnimation = new Timeline(new KeyFrame(Duration.millis(60), this::animateExplosion));
        explosionAnimation.setCycleCount(11); // explode 11 frames
        movementAnimation.play();
        GamePane.getGamePane().addAnimation(movementAnimation);
        flappingAnimation.play();
        GamePane.getGamePane().addAnimation(flappingAnimation);
    }

    private void animateExplosion(ActionEvent event) {
        if (miniBoss.getPictureFrame() >= 11) {
            explosionAnimation.stop(); // stopping the explosion animation
            GamePane.getGamePane().removeAnimation(explosionAnimation);
            GamePane.getGamePane().requestRemoving(miniBoss);
            return;
        }
        if (miniBoss.getColorNumber() == 1)
            miniBoss.setImage(ImagesAddress.MINI_BOSS_EXPLODE_YELLOW.getImage(miniBoss.getPictureFrame()));
        else miniBoss.setImage(ImagesAddress.MINI_BOSS_EXPLODE_PINK.getImage(miniBoss.getPictureFrame()));
        miniBoss.setPictureFrame(miniBoss.getPictureFrame() + 1); // resume the frames
    }

    private void animateFlapping(ActionEvent event) {
        if (miniBoss.getPictureFrame() > 4)
            miniBoss.setPictureFrame(1); // revert flapping images

        if (miniBoss.getColorNumber() == 1)
            miniBoss.setImage(ImagesAddress.MINI_BOSS_YELLOW.getImage(miniBoss.getPictureFrame()));
        else miniBoss.setImage(ImagesAddress.MINI_BOSS_PURPLE.getImage(miniBoss.getPictureFrame()));
        miniBoss.setPictureFrame(miniBoss.getPictureFrame() + 1);
    }

    private void animateMove(ActionEvent event) {
        if (miniBoss.getX() <= -miniBoss.getImage().getWidth()) {
            movementAnimation.stop(); // stop if is out of the scene
            GamePane.getGamePane().removeAnimation(movementAnimation);
            flappingAnimation.stop();
            GamePane.getGamePane().removeAnimation(flappingAnimation);
            GamePane.getGamePane().requestRemoving(miniBoss);
        }
        miniBoss.setX(miniBoss.getX() - 10); // move to left by 10
        miniBoss.checkCupHeadIntersection();
    }

    public void stopAnimations() { // stop all animations of the mini boss
        movementAnimation.stop();
        GamePane.getGamePane().removeAnimation(movementAnimation);
        flappingAnimation.stop();
        GamePane.getGamePane().removeAnimation(flappingAnimation);
        explosionAnimation.play();
        GamePane.getGamePane().addAnimation(explosionAnimation);
    }


}
