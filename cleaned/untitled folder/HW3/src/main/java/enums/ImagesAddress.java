package enums;

import javafx.scene.image.Image;
import model.Game;
import view.Main;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public enum ImagesAddress {
    GAME_BACKGROUND("background/8", ".png"),
    CUP_HEAD_BULLET("bullet/bullet", ".png"),
    CUP_HEAD("cupHead/red", ".png"),
    BOSS_NORMAL_IMAGE("Boss/", ".png"),
    BOSS_SHOOTING("Boss/BossShoot/", ".png"),
    BOSS_EGG("Boss/BossShoot/egg", ".png"),
    CUP_HEAD_BOMB("cupHead/cupheadBomb", ".png"),
    BULLET_BUTTON("cupHead/bulletButton", ".png"),
    BOMB_BUTTON("cupHead/bombButton", ".png"),
    CUP_HEAD_GOT_SHOT_FOG("cupHead/cupheadGotShut/", ".png"),
    CUP_HEAD_GOT_SHOT_LIGHTNING("cupHead/cupheadGotShut/", "b.png"),
    MINI_BOSS_PURPLE("miniBoss/purple/", ".png"),
    MINI_BOSS_YELLOW("miniBoss/yellow/", ".png"),
    EGG_PHASE2("bossPhase2/egg", ".png"),
    BABY_BOSS("bossPhase2/boss/", ".png"),
    SPLASHED_EGG("bossPhase2/eggSplash/", ".png"),
    MINI_BOSS_EXPLODE_PINK("miniBoss/Death/pink/", ".png"),
    MINI_BOSS_EXPLODE_YELLOW("miniBoss/Death/yellow/", ".png"),
    BOSS_DEATH("Boss/death/", ".png"),
    BABY_BOSS_BULLET("bossPhase2/bullet", ".png"),
    MENU_VOLUME_OFF("inGameMenu/volumeOff", ".png"),
    MENU_VOLUME_On("inGameMenu/volumeOn", ".png"),
    IN_GAME_MENU_BUTTON("inGameMenu/menuButton", ".png"),
    OPENING_MOVIE("movies/startingMovie", ".mp4"),
    SUPER_BOMB("cupHead/superBomb/", ".png"),
    BOOM("cupHead/superBombExplosion/20", ".png"),
    SUPER_BOMB_EXPLOSION("cupHead/superBombExplosion/", ".png"),
    BULLET_EXPLOSION("bulletexplosion/",".png"),
    BULLET_FIRE("bulletFire/",".png"),
    PROFILE_PICTURE("defaultProfilePictures/",".png");

    private final String partA;
    private final String partB;

    ImagesAddress(String partA, String partB) {
        this.partA = partA;
        this.partB = partB;
    }

    public String getAddress(String i) {
        URL address = null;
        try {
            address = new URL(Objects.requireNonNull(Main.class.getResource("/pictures/" + Game.getPicturesDirectory() + "/" + partA + i + partB)).toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return Objects.requireNonNull(address).toString();
    }

    public String getAddress() {
        URL address = null;
        try {
            address = new URL(Objects.requireNonNull(Main.class.getResource("/pictures/" + Game.getPicturesDirectory() + "/" + partA + partB)).toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return Objects.requireNonNull(address).toString();
    }

    public Image getImage(int i, String... specifiers) {
        StringBuilder output = new StringBuilder(String.valueOf(i));
        for (String string : specifiers) output.append(string);
        return new Image(getAddress(output.toString()));
    }

    public Image getImage() {
        return new Image(getAddress());
    }
}
