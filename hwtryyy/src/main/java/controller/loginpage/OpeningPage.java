package controller.loginpage;

import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import view.Main;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class OpeningPage {
    @FXML
    private MediaView movieView;
    private MediaPlayer player;

    public void initialize() {
        URL address = null;
        try {
            address = new URL(Objects.requireNonNull(
                    Main.class.getResource("/pictures/colored/movies/startingMovie.mp4")).toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        assert address != null;
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(address.toString()));
        player = mediaPlayer;
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setVolume(1); // set the volume to max
        movieView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setOnEndOfMedia(() -> Main.changeMenu("FirstPage"));
    }

    public void skipMovie() {
        player.stop();
        Main.changeMenu("FirstPage");
    }
}
