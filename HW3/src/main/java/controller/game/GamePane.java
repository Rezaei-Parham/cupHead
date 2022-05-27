package controller.game;

import controller.game.boss.BabyBossController;
import controller.game.boss.MainBossController;
import controller.game.cuphead.CupHeadController;
import enums.MusicsAddress;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.Game;
import model.game.*;
import view.Main;

import java.util.Vector;

public class GamePane {
    private static GamePane staticGamePane;
    private boolean isMiddleOfGame;
    private MainBossController bossController;
    private BabyBossController babyBossController;
    private Entity boss;
    private CupHead cupHead;
    private CupHeadController cupHeadController;
    private Pane instance;
    private Vector<Enemy> enemies = new Vector<>();
    private Vector<Timeline> activeAnimations = new Vector<>();

    public static GamePane getGamePane() {
        if (staticGamePane == null) {
            staticGamePane = new GamePane();
            initializer();
        }

        return staticGamePane;
    }

    public static void initializer() {

    }

    public static GamePane getStaticGamePane() {
        return staticGamePane;
    }

    public static void setStaticGamePane(GamePane staticGamePane) {
        GamePane.staticGamePane = staticGamePane;
    }

    public Pane getInstance() {
        return instance;
    }

    public void setInstance(Pane instance) {
        this.instance = instance;
    }

    public void preSet(Pane parent) {
        if (instance == null) instance = parent;
    }

    public void setPane(Pane pane) {
        instance = pane;
    }

    public void requestAdding(Node... nodes) {
        instance.getChildren().addAll(nodes);
    }

    public void requestRemoving(Node... nodes) {
        if (instance != null) instance.getChildren().removeAll(nodes);
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }

    public Vector<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(Vector<Enemy> enemies) {
        this.enemies = enemies;
    }

    public void addAnimation(Timeline timeline) {
        activeAnimations.add(timeline);
    }

    public void removeAnimation(Timeline timeline) {
        activeAnimations.remove(timeline);
    }

    public String getTime() {
        int totalTime = Game.getTime();
        int hours = totalTime / 3600;
        int minutes = (totalTime % 3600) / 60;
        int seconds = totalTime % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public void stopAnimations() {
        Vector<Timeline> removingOnes = new Vector<>();
        for (Timeline timeline : activeAnimations) {
            if (timeline.getStatus().equals(Animation.Status.RUNNING)) timeline.stop();
            else removingOnes.add(timeline);
        }
        activeAnimations.removeAll(removingOnes);
    }

    public void restartAnimations() {
        for (Timeline timeline : activeAnimations) {
            timeline.play();
        }
    }

    public void preInitializeGame() {
        instance = null;
        enemies = new Vector<>();
        activeAnimations = new Vector<>();
    }

    public boolean isIsMiddleOfGame() {
        return isMiddleOfGame;
    }

    public void setIsMiddleOfGame(boolean isMiddleOfGame) {
        GamePane.getGamePane().isMiddleOfGame = isMiddleOfGame;
    }

    public void endGame() {
        stopAnimations();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), new KeyValue(MusicsAddress.GAME_MUSIC.getPlayer().volumeProperty(), 0)));
        timeline.setOnFinished(event -> MusicsAddress.GAME_MUSIC.stopMusic());
        timeline.play();
        Main.changeMenu("GameEndPage");
    }

    public void nullifyObjects() {
        boss = null;
        instance = null;
        enemies = new Vector<>();
        activeAnimations = new Vector<>();
        setCupHead(null);
        isMiddleOfGame = false;
    }

    public Entity getBoss() {
        return boss;
    }

    public void setBoss(Entity newBoss) {
        boss = newBoss;
        if (newBoss instanceof Boss) bossController = new MainBossController((Boss) boss);
        else babyBossController = new BabyBossController((BabyBoss) boss);
    }

    public model.game.CupHead getCupHead() {
        return cupHead;
    }

    public void setCupHead(model.game.CupHead cupHead) {
        this.cupHead = cupHead;
        cupHeadController = new CupHeadController(cupHead);
    }

    public Vector<Timeline> getActiveAnimations() {
        return activeAnimations;
    }

    public void setActiveAnimations(Vector<Timeline> activeAnimations) {
        this.activeAnimations = activeAnimations;
    }

    public boolean isMiddleOfGame() {
        return isMiddleOfGame;
    }

    public void setMiddleOfGame(boolean middleOfGame) {
        isMiddleOfGame = middleOfGame;
    }

    public MainBossController getBossController() {
        return bossController;
    }

    public void setBossController(MainBossController bossController) {
        this.bossController = bossController;
    }

    public BabyBossController getBabyBossController() {
        return babyBossController;
    }

    public void setBabyBossController(BabyBossController babyBossController) {
        this.babyBossController = babyBossController;
    }

    public CupHeadController getCupHeadController() {
        return cupHeadController;
    }

    public void setCupHeadController(CupHeadController cupHeadController) {
        this.cupHeadController = cupHeadController;
    }
}
