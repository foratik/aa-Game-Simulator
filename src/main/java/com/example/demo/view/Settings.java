package com.example.demo.view;

import com.example.demo.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;

public class Settings extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage = Main.stage;
        URL url = LoginMenu.class.getResource("/view/FXML/Settings.fxml");
        AnchorPane anchorPane = FXMLLoader.load(url);
        if (Main.isBlackAndWhite)
            Main.blackAndWhite(anchorPane);
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.setTitle("Settings");
        stage.show();
    }
}
