package controller.game;

import controller.ExplodeAnimation;
import enums.ImagesAddress;
import enums.MusicsAddress;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.Game;
import model.game.*;
import view.Main;
import view.componentController.background.LandScape;

public class GameScreenController {
    private static Rectangle bossHealthBarStatic;
    private static Label staticTimer;
    private static Label bossHealthStatic;
    private static Rectangle superBombProgressBarStatic;
    private static Label cupHeadLivesStatic;
    private static Label showingScoreStatic;
    @FXML
    private Rectangle bossHealthBar;
    @FXML
    private Label timer;
    @FXML
    private Label bossHealth;
    @FXML
    private Rectangle superBombProgressBar;
    @FXML
    private Label cupHeadLives;
    @FXML
    private Label showingScore;
    @FXML
    private Pane pane;
    @FXML
    private ImageView shootingStateButton;

    public static void updateHealthBar() {
        bossHealthBarStatic.setWidth(1.5 * GamePane.getGamePane().getBoss().getHealth());
        bossHealthStatic.setText(String.valueOf(GamePane.getGamePane().getBoss().getHealth()));
        if (GamePane.getGamePane().getBoss().getHealth() < 30)  // change dangerous color
            bossHealthBarStatic.setFill(Color.valueOf("E55A55"));
    }

    public static void updateSuperBombProgressBar() {
        if (GamePane.getGamePane().getCupHead() != null)
            superBombProgressBarStatic.setWidth // width according to the amount
                    (GamePane.getGamePane().getCupHead().getSuperBombCounter() * (float) (150) / 15);
    }

    public static void updateTimer() {
        staticTimer.setText(GamePane.getGamePane().getTime());
    }

    public static void updateShowingScore() {
        if (GamePane.getGamePane().getCupHead() != null) // score if cup head exists
            showingScoreStatic.setText(String.valueOf(GameEndingPage.getScore()));
    }

    public static void updateCupHeadLives() {
        if (GamePane.getGamePane().getCupHead() != null) {
            cupHeadLivesStatic.setText(String.valueOf(GamePane.getGamePane().getCupHead().getHealth()));
            if (GamePane.getGamePane().getCupHead().getHealth() < 3) // dangerous situation color
                cupHeadLivesStatic.setStyle("-fx-text-fill: #f14e4e");
        }
    }


    public void initialize() {
        if (Game.getPicturesDirectory().equals("bw")) pane.getStyleClass().add("BlackPane");
        if (!GamePane.getGamePane().isIsMiddleOfGame())
            partiallyInitializeStartOfTheGame();
        else {
            pane.getChildren().addAll(GamePane.getGamePane().getInstance().getChildren());
            GamePane.getGamePane().setPane(pane);
        }
        shootingStateButton.setViewOrder(-1);
        GamePane.getGamePane().setIsMiddleOfGame(true);
        createCupHeadMove(); // cup head movement animate
        staticTimer = timer;
        Game.startTimer(); // game timer start
        updateCupHeadLives(); // lives of cup head initialize
        updateHealthBar(); // boss health bar
    }

    private void partiallyInitializeStartOfTheGame(){
        GamePane.getGamePane().preSet(pane);
        LandScape landScape = new LandScape(0);
        LandScape landScape1 = new LandScape(1);
        // setting the static ones for static usage
        bossHealthBarStatic = bossHealthBar;
        bossHealthStatic = bossHealth;
        superBombProgressBarStatic = superBombProgressBar;
        cupHeadLivesStatic = cupHeadLives;
        showingScoreStatic = showingScore;
        pane.getChildren().add(landScape);
        pane.getChildren().add(landScape1);
        new CupHead(); // creating a cup head
        landScape.startMoving(); // start moving the landscapes
        landScape1.startMoving();
        GamePane.getGamePane().setBoss(new Boss());
        initializeMiniBosses(); // initialize mini boss creation
        MusicsAddress.GAME_MUSIC.playMusic();
    }

    private void createCupHeadMove() {
        CupHead cupHead = GamePane.getGamePane().getCupHead();
        cupHead.setFocusTraversable(true);
        cupHead.requestFocus(); // request focus one the cup head
        cupHead.setOnKeyPressed(event -> keyProcessor(event.getCode().getName(), true));
        cupHead.setOnKeyReleased(event -> keyProcessor(event.getCode().getName(), false));
    }

    private void keyProcessor(String key, boolean add) {
        switch (key) {
            case "Left": case "A":
                if (add) GamePane.getGamePane().getCupHead().setDirection(Entity.Direction.left);
                else GamePane.getGamePane().getCupHead().removeDirection(Entity.Direction.left);
                break;
            case "Right": case "D":
                if (add) GamePane.getGamePane().getCupHead().setDirection(Entity.Direction.right);
                else GamePane.getGamePane().getCupHead().removeDirection(Entity.Direction.right);break;
            case "Up": case "W":
                if (add) GamePane.getGamePane().getCupHead().setDirection(Entity.Direction.up);
                else GamePane.getGamePane().getCupHead().removeDirection(Entity.Direction.up);break;
            case "Down": case "S":
                if (add) GamePane.getGamePane().getCupHead().setDirection(Entity.Direction.down);
                else GamePane.getGamePane().getCupHead().removeDirection(Entity.Direction.down);
                break;
            case "Space":
                if (add) createBullet();break;
            case "Tab":
                if (add) {
                    GamePane.getGamePane().getCupHead().changeShootingStyle();
                    setShootingIcon();}
                break;
            case "B":
                if (add) GamePane.getGamePane().getCupHead().superBombRequest();
                break;

        }
    }

    private void createBullet() {
        if (GamePane.getGamePane().getCupHead().getShootingState().equals(CupHead.ShooterEnum.BULLET)) {
            ExplodeAnimation explodeAnimation = new ExplodeAnimation();
            GamePane.getGamePane().requestAdding(explodeAnimation.getExplosionImage());
            explodeAnimation.play(); // start animation of sending bullet
            Bullet bullet = new Bullet();
            bullet.startMoving(); // start moving the bullet
        }
        else new Bomb(); // make a bomb

    }

    public void initializeMiniBosses() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(15000), this::createMiniBuss));
        timeline.setCycleCount(-1);
        timeline.play(); // start timeline of creating mini bosses for 15 seconds each
        GamePane.getGamePane().addAnimation(timeline);
    }

    private void createMiniBuss(ActionEvent event) {
        new MiniBoss(0);
        new MiniBoss(1);
        new MiniBoss(2);
        new MiniBoss(3);
    }

    private void setShootingIcon() {
        if (GamePane.getGamePane().getCupHead().getShootingState().equals(CupHead.ShooterEnum.BULLET))
            shootingStateButton.setImage(ImagesAddress.BULLET_BUTTON.getImage());
        else shootingStateButton.setImage(ImagesAddress.BOMB_BUTTON.getImage());

    }

    public void openInGameMenu() {
        GamePane.getGamePane().stopAnimations();
        Game.stopTimer();
        Main.changeMenu("InGameMenu");
    }
}
