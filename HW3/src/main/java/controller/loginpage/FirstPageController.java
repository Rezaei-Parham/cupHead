package controller.loginpage;

import com.jfoenix.controls.JFXComboBox;
import enums.MusicsAddress;
import enums.loginPageTexts;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Database;
import model.Game;
import model.User;
import view.Main;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.Random;

public class FirstPageController {
    private final FileChooser fileChooser = new FileChooser();
    @FXML
    private JFXComboBox<String> avatarList = new JFXComboBox<>();
    @FXML
    private VBox vBox;
    @FXML
    private Pane parent;
    @FXML
    private TextField password;
    @FXML
    private TextField username;
    @FXML
    private Circle avatarCircle;
    private String chosenAvatarPath;

    public static boolean isUsernameNotUnique(String username) {
        for (User user : Database.getUsers()) {
            if (user.getUsername().equals(username)) return true;
        }
        return false;
    }

    public static boolean isUsernameValid(String username) {
        return isUsernameNotUnique(username);
    }

    public static boolean isPasswordCorrect(User user, String password) {
        return user.getPassword().equals(password);
    }

    public static boolean isPasswordNotSafe(String password) {
        if (!password.matches("[a-zA-Z0-9!@#$%^]*[a-z]+[a-zA-Z0-9!@#$%^]*")) return true;
        if (!password.matches("[a-zA-Z0-9!@#$%^]*[A-Z]+[a-zA-Z0-9!@#$%^]*")) return true;
        if (!password.matches("[a-zA-Z0-9!@#$%^]*[0-9]+[a-zA-Z0-9!@#$%^]*")) return true;
        if (!password.matches("[a-zA-Z0-9!@#$%^]*[!@#$%^]+[a-zA-Z0-9!@#$%^]*")) return true;
        return !password.matches("[a-zA-Z0-9!@#$%^]{8,32}");
    }

    public static void choosePictureOfList(JFXComboBox avatarList, Circle avatarCircle) {
        String newAvatarPath;
        if (avatarList == null || avatarList.getValue() == null) return;
        if (avatarList.getValue().equals("Miles Prower"))
            newAvatarPath = getProfilePictureAddress(1);
        else if (avatarList.getValue().equals("CupHead"))
            newAvatarPath = getProfilePictureAddress(2);
        else if (avatarList.getValue().equals("Mugman"))
            newAvatarPath = getProfilePictureAddress(3);
            else return;
        if (!newAvatarPath.equals("")) {
            ImagePattern pattern = new ImagePattern(new Image(newAvatarPath),
                    280, 180, 100, 100, false);
            avatarCircle.setFill(pattern);
            if (Game.getLoggedInUser() != null) Game.getLoggedInUser().setAvatarURL(newAvatarPath);
        }
    }

    private static String getProfilePictureAddress(int i){
        String newAvatarPath = "";
        try {
            newAvatarPath = String.valueOf(
                    new URL(Objects.requireNonNull(
                            Main.class.getResource(
                                    "/pictures/defaultProfilePictures/" + i + ".jpeg")).toString()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return  newAvatarPath;
    }

    public void initialize() {
        MusicsAddress.MENUS.stopMusic();
        avatarList.setItems(FXCollections.observableArrayList("Miles Prower", "CupHead", "Mugman"));
        openSignUp();
        fileChooser.setInitialDirectory(new File(
                "/Users/cyberrose/personal/Sharif%/Term2/" +
                        "AP/HW3/src/main/resources/pictures/defaultProfilePictures"));
        if (avatarCircle != null) randomInitializeAvatar();
    }

    public void openSignUp() { // change the vbox to sign up page
        TranslateTransition transition = new TranslateTransition(Duration.millis(1000), vBox);
        transition.setToX(0);
        transition.play();
        transition.setOnFinished((e -> {
            try {
                parent = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/fxml/SignUp.fxml")));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            vBox.getChildren().removeAll();
            vBox.getChildren().setAll(parent);
        }));
    }

    public void openSignIn() { // change th vbox to sign in
        TranslateTransition transition = new TranslateTransition(Duration.millis(1000), vBox);
        transition.setToX(vBox.getLayoutX() * 20);
        transition.play();
        transition.setOnFinished((e -> {
            try {
                parent = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/fxml/loginpage.fxml")));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            vBox.getChildren().removeAll();
            vBox.getChildren().setAll(parent);
        }));
    }

    public void register() throws MalformedURLException {
        if (isUsernameNotUnique(username.getText())) // check if username is unique
            AlertBox.display(loginPageTexts.USER_EXISTS);
        if (isPasswordNotSafe(password.getText())) // chec k if password is safe
            AlertBox.display(loginPageTexts.PASSWORD_SAFETY);
        else {
            AlertBox.display(loginPageTexts.WELCOME); // show the welcome popo up
            User user = new User(username.getText(), password.getText());
            user.setAvatarURL(chosenAvatarPath);
            Database.addUser(user);
        }

    }

    public void signInAttempt() {
        String usernameText = username.getText();
        if (!isUsernameValid(usernameText))
            AlertBox.display(loginPageTexts.USER_NOT_EXISTS);
        else {
            User user = Database.getUserByUsername(usernameText);
            assert user != null;
            if (!isPasswordCorrect(user, password.getText())) {
                AlertBox.display(loginPageTexts.USER_NOT_EXISTS);
            } else {
                Game.setLoggedInUser(user);
                Main.changeMenu("MainMenu");
            }
        }
    }

    public void closeApp() {
        Main.closeApp();
    }

    public void chooseImage() {
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            chosenAvatarPath = file.toURI().toString();
            ImagePattern pattern = new ImagePattern(
                    new Image(chosenAvatarPath), 280, 180, 100, 100, false);
            avatarCircle.setFill(pattern);
        }
    }

    private void randomInitializeAvatar() {
        Random random = new Random(); // random number
        int index = Math.abs(random.nextInt()) % 3 + 1; // based in 1 - 3
        URL address = null;
        try {
            address = new URL(Objects.requireNonNull(
                    Main.class.getResource("/pictures/defaultProfilePictures/" + index + ".jpeg")).toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        assert address != null;
        chosenAvatarPath = address.toString();
        ImagePattern pattern = new ImagePattern(new Image(chosenAvatarPath));
        avatarCircle.setFill(pattern);
    }

    public void signAsGuest() {
        Main.changeMenu("MainMenu"); // go to game without sign in
    }

    public void actAvatarList() {
        choosePictureOfList(avatarList, avatarCircle);
    }
}
