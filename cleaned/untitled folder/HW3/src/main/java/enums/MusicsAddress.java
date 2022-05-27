package enums;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.Game;
import view.Main;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public enum MusicsAddress {
    BOSS_BARF("Boss_Barf", ".wav"),
    GAME_MUSIC("gameMusic", ".mp3"),
    BABY_LASER("babyLaser", ".wav"),
    HOOHOO("hoohoo", ".wav"),
    EGG_SPLASH("eggSplash", ".wav"),
    BOSS_DEATH("death", ".wav"),
    GHUGHUL("Ghughul", ".wav"),
    MENUS("menus",".mp3");

    private final String path;
    private final String extension;
    private MediaPlayer player;

    MusicsAddress(String path, String extension) {
        this.path = path;
        this.extension = extension;
    }

    public MediaPlayer getPlayer() {
        return player;
    }

    public String getAddress() {
        URL address = null;
        try {
            address = new URL(Objects.requireNonNull(Main.class.getResource("/musics/" + path + extension)).toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return Objects.requireNonNull(address).toString();
    }

    public void playSong() {
        if (Game.isMute()) return;
        AudioClip song = new AudioClip(getAddress());
        song.play();
    }

    public void playMusic() {
        if (Game.isMute()) return;
        forceMusic();
    }

    public void forceMusic(){
        Media music = new Media(getAddress());
        MediaPlayer mediaPlayer = new MediaPlayer(music);
        player = mediaPlayer;
        mediaPlayer.play();
    }

    public void muteMusic() {
        if (player != null) player.setMute(true);
    }

    public void resumeMusic() {
        if (player == null) playMusic();
        else {
            if (!Game.isMute()) player.setMute(false);
        }
    }

    public void stopMusic() {
        if (player != null) player.stop();
    }

}
