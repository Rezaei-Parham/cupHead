package controller.game;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import view.Main;
import view.componentController.background.LandScape;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class MyTimer extends AnimationTimer {
    private final LandScape l;

    public MyTimer(LandScape l) {
        this.l = l;
    }

    @Override
    public void handle(long now) {
        l.setX(l.getX() - 20);
        if (l.getX() <= -1280) {
            l.setX(1280);
        }
        int frame = 2;
        URL address = null;
        try {
            address = new URL(Objects.requireNonNull(Main.class.getResource("/pictures/colored/background/" + frame + ".png")).toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        l.setImage(new Image(Objects.requireNonNull(address).toString()));
    }
}
