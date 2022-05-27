package controller.game;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import view.Main;
import view.componentController.background.LandScape;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class MyTimer extends AnimationTimer {
    private final LandScape landScape;

    public MyTimer(LandScape scape) {
        this.landScape = scape;
    }

    @Override
    public void handle(long now) {
        landScape.setX(landScape.getX() - 20);
        if (landScape.getX() <= -1280) // landscape out of the page
            landScape.setX(1280);
        int frame = 2;
        URL address = null;
        try {
            address = new URL(Objects.requireNonNull(
                    Main.class.getResource("/pictures/colored/background/" + frame + ".png")).toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        landScape.setImage(new Image(Objects.requireNonNull(address).toString()));
    }
}
