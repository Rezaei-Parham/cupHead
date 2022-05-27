package controller.mainmenu;

import enums.MusicsAddress;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import view.componentController.mainmenu.MenuBox;
import view.componentController.mainmenu.MenuItem;



public class MainMenuController {
    @FXML
    private Pane pane;

    public void initialize() {
        createMenu();
    }

    public void createMenu() {
        MenuBox menuList = new MenuBox(new MenuItem("START GAME"),
                new MenuItem("SCORE BOARD"),
                new MenuItem("PROFILE"), new MenuItem("LOGOUT"));
        menuList.setTranslateX(100);
        menuList.setTranslateY(100);
        MusicsAddress.MENUS.resumeMusic();
        pane.getChildren().addAll(menuList);
    }
}
