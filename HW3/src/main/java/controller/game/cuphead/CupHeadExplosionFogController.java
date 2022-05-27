package controller.game.cuphead;

import controller.game.GamePane;
import enums.ImagesAddress;
import javafx.event.ActionEvent;
import view.componentController.CupHeadExplosionFog;

public class CupHeadExplosionFogController {
    private final CupHeadExplosionFog explosionFog;

    public CupHeadExplosionFogController(CupHeadExplosionFog explosionFog) {
        this.explosionFog = explosionFog;
    }

    public void animatePhoto(ActionEvent ignoredEvent) {
        if (explosionFog.isFired()) {
            if (explosionFog.getFrameCount() <= 3) {
                explosionFog.setImage(ImagesAddress.CUP_HEAD_GOT_SHOT_FOG.getImage(explosionFog.getFrameCount()));
                explosionFog.getLightning().setImage(ImagesAddress.CUP_HEAD_GOT_SHOT_LIGHTNING.getImage(explosionFog.getFrameCount()));
                explosionFog.setOpacity(0.5);
                explosionFog.getLightning().setOpacity(0.5);
            }
            if (explosionFog.getFrameCount() == 4) {
                GamePane.getGamePane().requestRemoving(explosionFog);
                GamePane.getGamePane().requestRemoving(explosionFog.getLightning());
            }
        }
        explosionFog.setFrameCount(explosionFog.getFrameCount() + 1);
        if (explosionFog.getFrameCount() >= 10) {
            explosionFog.setFrameCount(1);
            explosionFog.getExplosionAnimation().stop();
            GamePane.getGamePane().removeAnimation(explosionFog.getExplosionAnimation());
            GamePane.getGamePane().getCupHead().setImmune(false);
            GamePane.getGamePane().getCupHead().setOpacity(1);
        }
    }
}
