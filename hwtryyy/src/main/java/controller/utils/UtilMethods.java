package controller.utils;

import javafx.scene.image.ImageView;

public class UtilMethods {
    public static boolean intersect(ImageView x, ImageView y) { // checking intersection of two image vies
        double a = y.getX() + y.getImage().getWidth() / 2 - x.getX() - x.getImage().getWidth() / 2;
        double b = y.getY() + y.getImage().getHeight() / 2 - x.getY() - x.getImage().getHeight() / 2;
        double c = a * a + b * b;
        return c <= Math.min(y.getImage().getWidth() / 2, y.getImage().getHeight() / 2)
                * Math.min(y.getImage().getWidth() / 2, y.getImage().getHeight() / 2);
    }

}
