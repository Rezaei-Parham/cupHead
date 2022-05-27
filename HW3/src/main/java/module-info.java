module com.example.hw3test {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires com.google.gson;
    requires javafx.media;


    exports view;
    exports controller.loginpage;
    exports controller.mainmenu;
    exports controller.game;
    exports controller.gameSetting;
    opens controller.mainmenu.profilemenu;
    opens controller.loginpage;
    opens controller.game;
    opens model.game;
    opens controller.mainmenu;
    opens controller.utils;
    requires com.fasterxml.jackson.databind;
    opens javafx.fxml to com.fasterxml.jackson.databind;
}