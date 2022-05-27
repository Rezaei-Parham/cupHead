package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Database;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class Main extends Application {
    public static Scene getScene() {
        return scene;
    }

    private static Scene scene;
    public static Parent root;


    public static Stage getStage() {
        return stage;
    }

    private static Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.<AnchorPane>load(Objects.requireNonNull(getClass().getResource("/fxml/OpeningPage.fxml")));
        stage = primaryStage;
        Main.root = root;
        Scene scene = new Scene(root);
        Main.scene = scene;
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args){
        Database.loadFromJson();
        launch(args);
    }


    public static void changeMenu(String name){
        Parent root = loadFXML(name);
        if(!Objects.equals(name, "FirstPage")) {
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setResizable(false); // fixing the size
            stage.close();
            stage = newStage;
            stage.show();
        }else{
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setResizable(false);
            newStage.initStyle(StageStyle.TRANSPARENT); // special background of first page
            scene.setFill(Color.TRANSPARENT); // filling color
            stage.close();
            stage = newStage;
            stage.show();
        }
        Main.scene.setRoot(root);
    }
    private static Parent loadFXML(String name){
        try {
            URL address = new URL(Objects.requireNonNull(
                    Main.class.getResource("/fxml/" + name + ".fxml")).toString());
            return FXMLLoader.load(address);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void closeApp(){
        stage.close();
    }
}
