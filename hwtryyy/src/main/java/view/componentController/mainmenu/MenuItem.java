package view.componentController.mainmenu;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.Game;
import view.Main;

import java.util.List;

public class MenuItem extends StackPane {
    public MenuItem(String name){
        LinearGradient gradient = new LinearGradient(0,
                0,1,0,true, CycleMethod.NO_CYCLE, List.of(
                new Stop(0, Color.DARKVIOLET),
                new Stop(0.1, Color.BLACK),
                new Stop(0.9, Color.BLACK),
                new Stop(1, Color.DARKVIOLET)

        ));
        Rectangle backGround = new Rectangle( 200,30);
        backGround.setOpacity(0.4);
        Text text = new Text(name);
        text.setFill(Color.DARKGREY);
        text.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD,22));
        setAlignment(Pos.CENTER);
        getChildren().addAll(backGround,text);

        setOnMouseEntered(event -> {
            backGround.setFill(gradient);
            text.setFill(Color.WHITE);
        });

        setOnMouseExited(event -> {
            backGround.setFill(Color.BLACK);
            text.setFill(Color.DARKGREY);
        });

        setOnMousePressed(event -> {
            backGround.setFill(Color.DARKVIOLET);
            if(name.equals("LOGOUT")) Main.changeMenu("FirstPage");
            else if(name.equals("START GAME")) Main.changeMenu("GameSetting");
            else if(name.equals("PROFILE") && Game.isNotGuest()) Main.changeMenu("ProfilePage");
            else if(name.equals("SCORE BOARD")) Main.changeMenu("ScoreBoard");
        });

        setOnMouseReleased(event -> backGround.setFill(gradient));
    }

}
