package view.componentController;

import controller.game.GamePane;
import enums.ImagesAddress;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SuperBombExplosion extends ImageView {
    private final Timeline explodeAnimation;
    private final ImageView boomImage;
    private int imageFrame;

    public SuperBombExplosion() {
        setX(GamePane.getGamePane().getCupHead().getX() - 10); // setting the position
        setY(GamePane.getGamePane().getCupHead().getY() - 10);
        imageFrame = 1;
        explodeAnimation = new Timeline(new KeyFrame(Duration.millis(200), this::changeImage));
        boomImage = new ImageView(ImagesAddress.BOOM.getImage()); // Boom image
        boomImage.setX(GamePane.getGamePane().getCupHead().getX() - 30);
        boomImage.setY(GamePane.getGamePane().getCupHead().getY() - 30);
        explodeAnimation.setCycleCount(7); // 7 rounds of animation
        GamePane.getGamePane().requestAdding(this);
        GamePane.getGamePane().requestAdding(boomImage);
        explodeAnimation.play();
        GamePane.getGamePane().addAnimation(explodeAnimation);
    }

    private void changeImage(ActionEvent event) {
        if (imageFrame == 5) // ending the boom
            GamePane.getGamePane().requestRemoving(boomImage);

        this.setImage(ImagesAddress.SUPER_BOMB_EXPLOSION.getImage(imageFrame));
        if (imageFrame == 7) { // ending the explosion animation
            explodeAnimation.stop();
            GamePane.getGamePane().removeAnimation(explodeAnimation);
            GamePane.getGamePane().requestRemoving(this);
            return;
        }
        imageFrame++; // frame goes to next one
    }
}
