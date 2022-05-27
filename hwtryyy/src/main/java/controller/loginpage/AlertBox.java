package controller.loginpage;

import enums.loginPageTexts;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
    public static void display(loginPageTexts loginPageTexts) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);

        window.setMinWidth(350);
        window.setMinHeight(100);

        Label label = new Label();
        label.setText(loginPageTexts.getText());

        label.setStyle("-fx-font-family: Chalkboard");
        label.setStyle("-fx-font-size: 14");

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label);
        layout.setAlignment(Pos.CENTER);
        if (loginPageTexts.getState().equals(enums.loginPageTexts.type.SUCCESS)) {
            window.setTitle("Success");
            layout.setStyle("-fx-background-color: #6ab46d");
        } else {
            window.setTitle("Error");
            layout.setStyle("-fx-background-color: #c56c6c");
        }
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
