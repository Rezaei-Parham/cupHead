package model.mainmenu;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class MenuBox extends VBox {
    public MenuBox(MenuItem... items){
        getChildren().add(createSeparator());

        for(MenuItem menuItem : items){
            getChildren().addAll(menuItem,createSeparator());
        }
    }

    private Line createSeparator(){
        Line separatorLine = new Line();
        separatorLine.setEndX(200);
        separatorLine.setStroke(Color.DARKGREY);
        return separatorLine;
    }
}
