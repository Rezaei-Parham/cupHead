package view.componentController.background;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import view.Main;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class LandScape extends ImageView {
    Timeline animatedMove;

    public LandScape(int i) {
        setX(0);
        if (i == 1) setX(1280);
        setY(0);
        setFitWidth(1280);
        try {

            URL address = new URL(Objects.requireNonNull(Main.class.getResource("/pictures/colored/background/2.png")).toString());
            this.setImage(new Image(address.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        animatedMove = new Timeline(new KeyFrame(Duration.millis(90), this::movePictures));
        animatedMove.setCycleCount(Animation.INDEFINITE);
    }

    private void movePictures(ActionEvent event) {
        if (this.getX() <= -1280) {
            this.setX(1280);
        }
        this.setX(this.getX() - 40);
    }

    public void startMoving() {
        animatedMove.play();
    }
}
