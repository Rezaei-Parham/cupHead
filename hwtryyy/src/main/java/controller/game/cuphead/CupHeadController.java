package controller.game.cuphead;

import controller.game.GamePane;
import enums.ImagesAddress;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import model.Game;
import model.game.CupHead;
import model.game.Entity;
import view.componentController.CupHeadExplosionFog;
import view.componentController.SuperBombExplosion;

public class CupHeadController {
    private final CupHead cupHead;
    private Timeline transformToSuperBomb;

    public CupHeadController(CupHead cupHead) {
        this.cupHead = cupHead;
        Timeline cupHeadMove = new Timeline(new KeyFrame(Duration.millis(30), this::moveCupHead));
        cupHeadMove.setCycleCount(Animation.INDEFINITE);
        cupHeadMove.play();
        GamePane.getGamePane().addAnimation(cupHeadMove);
    }

    private void moveCupHead(ActionEvent event) {
        if(cupHead == null) return;
        if (cupHead.getDirectionVector().contains(Entity.Direction.left) && !cupHead.hitLeftWall())
            cupHead.setX(cupHead.getX() - 15);
        if (cupHead.getDirectionVector().contains(Entity.Direction.right) && !cupHead.hitRightWall())
            cupHead.setX(cupHead.getX() + 15);
        if (cupHead.getDirectionVector().contains(Entity.Direction.down) && !cupHead.hitFloor() && !cupHead.isSuperBomb())
            cupHead.setY(cupHead.getY() + 15);
        if (cupHead.getDirectionVector().contains(Entity.Direction.up) && !cupHead.hitUpWall() && !cupHead.isSuperBomb())
            cupHead.setY(cupHead.getY() - 15);
    }

    public void hit(boolean isOnFire) {
        if(cupHead == null) return;
        if (cupHead.isSuperBomb()) { // if is super bomb
            new SuperBombExplosion();
            new CupHeadExplosionFog(false);
            cupHead.setImage(ImagesAddress.CUP_HEAD.getImage());
            cupHead.setSuperBomb(false); // de super bomb
            cupHead.setX(40); // set the position
            cupHead.setY(500);
            return;
        }
        if (cupHead.isImmune()) return; // check if it's immune
        // decrease the health level
        cupHead.setHealth(cupHead.getHealth() - Game.getDifficultyLevel().getGettingDamagedRate());
        new CupHeadExplosionFog(isOnFire); // start new explosion
        if (cupHead.getHealth() <= 0) GamePane.getGamePane().endGame();
    }

    public void becomeSuperBomb() {
        if(cupHead == null) return;
        cupHead.becomeSuperBomb(); // set super bomb field and immunity as true
        transformToSuperBomb = new Timeline(new KeyFrame(Duration.millis(50), this::changePhotoToSuperBomb));
        transformToSuperBomb.setCycleCount(12);
        transformToSuperBomb.play(); // becoming the super bomb image animation
        GamePane.getGamePane().addAnimation(transformToSuperBomb);
    }

    private void changePhotoToSuperBomb(ActionEvent event) {
        if(cupHead == null) return;
        cupHead.setImage(ImagesAddress.SUPER_BOMB.getImage(cupHead.getPictureFrame()));
        if (cupHead.getPictureFrame() == 12) {
            transformToSuperBomb.stop(); // stop the transformation
            GamePane.getGamePane().removeAnimation(transformToSuperBomb);
            return;
        }
        cupHead.setPictureFrame(cupHead.getPictureFrame() + 1);
    }

    public void increaseSuperEnergy(int i) { // increasing the energy needed to become super bomb
        if(cupHead == null) return;
        if (cupHead.getSuperBombCounter() == 15) return;
        cupHead.setSuperBombCounter(cupHead.getSuperBombCounter() + i);
        if (cupHead.getSuperBombCounter() >= 15) {
            cupHead.setSuperBombCounter(15);
        }
    }


}
