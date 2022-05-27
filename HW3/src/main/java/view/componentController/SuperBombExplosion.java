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
        setX(GamePane.getGamePane().getCupHead().getX() - 10);
        setY(GamePane.getGamePane().getCupHead().getY() - 10);
        imageFrame = 1;
        explodeAnimation = new Timeline(new KeyFrame(Duration.millis(200), this::changeImage));
        boomImage = new ImageView(ImagesAddress.BOOM.getImage());
        boomImage.setX(GamePane.getGamePane().getCupHead().getX() - 10);
        boomImage.setY(GamePane.getGamePane().getCupHead().getY() - 10);
        explodeAnimation.setCycleCount(7);
        GamePane.getGamePane().requestAdding(this);
        GamePane.getGamePane().requestAdding(boomImage);
        explodeAnimation.play();
        GamePane.getGamePane().addAnimation(explodeAnimation);
    }

    private void changeImage(ActionEvent event) {
        if (imageFrame == 5) {
            GamePane.getGamePane().requestRemoving(boomImage);
        }
        this.setImage(ImagesAddress.SUPER_BOMB_EXPLOSION.getImage(imageFrame));
        if (imageFrame == 7) {
            explodeAnimation.stop();
            GamePane.getGamePane().removeAnimation(explodeAnimation);
            GamePane.getGamePane().requestRemoving(this);
            return;
        }
        imageFrame++;
    }
}
