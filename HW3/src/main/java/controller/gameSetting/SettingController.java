package controller.gameSetting;

import com.jfoenix.controls.JFXButton;
import controller.game.GamePane;
import enums.GamePreData;
import enums.ImagesAddress;
import enums.MusicsAddress;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import model.Game;
import view.Main;

public class SettingController {
    @FXML
    private ComboBox<String> gameType;
    @FXML
    private ComboBox<String> difficulty;
    @FXML
    private ToggleButton blackAndWhite;
    @FXML
    private JFXButton startGame;
    @FXML
    private ImageView volume;

    public void initialize() {
        MusicsAddress.MENUS.stopMusic();
        if (Game.isMute()) volume.setImage(ImagesAddress.MENU_VOLUME_OFF.getImage());
        else volume.setImage(ImagesAddress.MENU_VOLUME_On.getImage());
        gameType.setItems(FXCollections.observableArrayList("Normal", "Devil Mode"));
        difficulty.setItems(FXCollections.observableArrayList("Amateur", "Medium", "Legendary"));
        blackAndWhite = new ToggleButton();
        blackAndWhite.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) Game.setBlackAndWhite();
            else Game.setColored();
        });
    }

    public void gameTypeAction() {
        if (gameType.getValue() == null) {
            startGame.setDisable(true);
            return;
        }

        difficulty.setDisable(!gameType.getValue().equals("Normal"));
        if (gameType.getValue().equals("Normal")) {
            Game.setGameMode(Game.GameMode.NORMAL);
            Game.setDifficultyLevel(GamePreData.LEVEL2);
        }
        if (gameType.getValue().equals("Devil Mode")) {
            Game.setGameMode(Game.GameMode.DEVIL);
            difficulty.setDisable(true);
            Game.setDifficultyLevel(GamePreData.LEVEL3);
        }
        if (difficulty.getValue() != null) {
            if (difficulty.getValue().equals("Amateur")) Game.setDifficultyLevel(GamePreData.LEVEL1);
            else if (difficulty.getValue().equals("Medium")) Game.setDifficultyLevel(GamePreData.LEVEL2);
            else if (difficulty.getValue().equals("Legendary")) Game.setDifficultyLevel(GamePreData.LEVEL3);
        }
        startGame.setDisable(gameType.getSelectionModel().isEmpty());
    }

    public void initializeGame() {
        gameTypeAction();
        GamePane.getGamePane().preInitializeGame();
        Game.preSetTimer();
        GamePane.getGamePane().setIsMiddleOfGame(false);
    }

    public void startGame() {
        //MusicsAddress.MENUS.stopMusic();
        initializeGame();
        Main.changeMenu("GameScreen");
    }

    public void changeColor() {
        if (Game.getPicturesDirectory().equals("colored")) Game.setBlackAndWhite();
        else Game.setColored();
    }

    public void changeMusicState() {
        if (Game.isMute()) {
            Game.unMute();
            volume.setImage(ImagesAddress.MENU_VOLUME_On.getImage());
            return;
        }
        Game.mute();
        volume.setImage(ImagesAddress.MENU_VOLUME_OFF.getImage());
    }
}
