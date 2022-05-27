package view.componentController.mainmenu;

import controller.utils.RepresentableScore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Database;
import model.User;
import model.game.Score;
import view.Main;

public class ScoreBoardController {
    @FXML
    private TableView<RepresentableScore> scoreBoard;

    // getting every data in users
    public ObservableList<RepresentableScore> getScores() {
        ObservableList<RepresentableScore> scores = FXCollections.observableArrayList();
        for (User user : Database.getUsers()) {
            scores.add(new RepresentableScore(user.getUsername(), user.getScores().get(Score.ScoreTypes.LEVEL1).getScoreNumber(), user.getScores().get(Score.ScoreTypes.LEVEL1).getTime(), user.getScores().get(Score.ScoreTypes.LEVEL2).getScoreNumber(), user.getScores().get(Score.ScoreTypes.LEVEL2).getTime(), user.getScores().get(Score.ScoreTypes.LEVEL3).getScoreNumber(), user.getScores().get(Score.ScoreTypes.LEVEL3).getTime(), user.getScores().get(Score.ScoreTypes.DEVIL).getScoreNumber(), user.getScores().get(Score.ScoreTypes.DEVIL).getTime()));
        }
        return scores;
    }


    public void initialize() {
        TableColumn<RepresentableScore, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        TableColumn<RepresentableScore, Integer> level1Score = new TableColumn<>("Score1");
        level1Score.setMinWidth(80);
        level1Score.setCellValueFactory(new PropertyValueFactory<>("level1Score"));
        TableColumn<RepresentableScore, Integer> level1Time = new TableColumn<>("Time1");
        level1Time.setMinWidth(80);
        level1Time.setCellValueFactory(new PropertyValueFactory<>("level1Time"));
        TableColumn<RepresentableScore, Integer> level2Score = new TableColumn<>("Score2");
        level2Score.setMinWidth(80);
        level2Score.setCellValueFactory(new PropertyValueFactory<>("level2Score"));
        TableColumn<RepresentableScore, Integer> level2Time = new TableColumn<>("Time2");
        level2Time.setMinWidth(80);
        level2Time.setCellValueFactory(new PropertyValueFactory<>("level2Time"));
        TableColumn<RepresentableScore, Integer> level3Score = new TableColumn<>("SCore3");
        level3Score.setMinWidth(80);
        level3Score.setCellValueFactory(new PropertyValueFactory<>("level3Score"));
        TableColumn<RepresentableScore, Integer> level3Time = new TableColumn<>("Time3");
        level3Time.setMinWidth(80);
        level3Time.setCellValueFactory(new PropertyValueFactory<>("level3Time"));
        TableColumn<RepresentableScore, Integer> devilScore = new TableColumn<>("Score_d");
        devilScore.setMinWidth(80);
        devilScore.setCellValueFactory(new PropertyValueFactory<>("devilScore"));
        TableColumn<RepresentableScore, Integer> devilTime = new TableColumn<>("Time_d");
        devilTime.setMinWidth(80);
        devilTime.setCellValueFactory(new PropertyValueFactory<>("devilTime"));

        scoreBoard.setItems(getScores());
        scoreBoard.getColumns().addAll(nameColumn, level1Score, level1Time, level2Score, level2Time, level3Score, level3Time, devilScore, devilTime);

    }

    public void openMainMenu() {
        Main.changeMenu("MainMenu");
    }
}
