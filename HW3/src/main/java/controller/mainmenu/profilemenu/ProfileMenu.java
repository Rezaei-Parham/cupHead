package controller.mainmenu.profilemenu;

import com.jfoenix.controls.JFXComboBox;
import controller.loginpage.AlertBox;
import controller.loginpage.FirstPageController;
import enums.loginPageTexts;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Database;
import model.Game;
import view.Main;

import java.io.File;

public class ProfileMenu {
    private final FileChooser fileChooser = new FileChooser();
    @FXML
    private JFXComboBox<String> avatarList;
    @FXML
    private TextField newUsername;
    @FXML
    private TextField newPassword;
    @FXML
    private Circle avatarCircle;

    public void initialize() {
        avatarList.setItems(FXCollections.observableArrayList("Miles Prower", "CupHead", "Mugman"));
        ImagePattern pattern = new ImagePattern(new Image(Game.getLoggedInUser().getAvatarURL()));
        avatarCircle.setFill(pattern);
        avatarCircle.setStyle("-fx-background-repeat: stretch; -fx-background-size: 59");
    }

    public void saveNewPassword() {
        String password = newPassword.getText();
        // check new password safety
        if (FirstPageController.isPasswordNotSafe(password)) AlertBox.display(loginPageTexts.PASSWORD_SAFETY);
        else {
            AlertBox.display(loginPageTexts.PASSWORD_CHANGED);
            Game.getLoggedInUser().setPassword(password);
        }
    }

    public void saveNewUsername() {
        String username = newUsername.getText();
        // check if user with same username exists
        if (FirstPageController.isUsernameNotUnique(username)) AlertBox.display(loginPageTexts.USER_EXISTS);
        else {
            AlertBox.display(loginPageTexts.USERNAME_CHANGED);
            Game.getLoggedInUser().setUsername(username);
        }
    }

    public void removeAccount() {
        Database.removeUser(Game.getLoggedInUser());
        Main.changeMenu("FirstPage");
    }

    public void logOut() {
        Game.setLoggedInUser(null);
        Main.changeMenu("FirstPage");
    }

    public void returnToMainMenu() {
        Main.changeMenu("MainMenu");
    }

    public void chooseAvatarFile() {
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            String chosenAvatarPath = file.toURI().toString();
            ImagePattern pattern = new ImagePattern(new Image(chosenAvatarPath),
                    280, 180, 100, 100, false);
            avatarCircle.setFill(pattern);
            Game.getLoggedInUser().setAvatarURL(chosenAvatarPath);
        }
    }

    public void defineAvatar() {
        FirstPageController.choosePictureOfList(avatarList, avatarCircle);
    }
}
