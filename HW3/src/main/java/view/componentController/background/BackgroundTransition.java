package view.componentController.background;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.util.Duration;
import view.Main;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class BackgroundTransition extends Transition {
    private final LandScape landScape;
    int frame = 3;

    public BackgroundTransition(LandScape landScape) {
        setCycleDuration(Duration.millis(200));
        this.landScape = landScape;
        setCycleCount(-1);

    }

    @Override
    protected void interpolate(double frac) {
        if (landScape.getX() <= -1280) {
            landScape.setX(1280);
        }
// 
        landScape.setX(landScape.getX() - 20);
        try {

            URL address = new URL(Objects.requireNonNull(Main.class.getResource("/pictures/colored/background/" + frame + ".png")).toString());
            landScape.setImage(new Image(address.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
