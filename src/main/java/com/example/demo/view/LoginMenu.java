package com.example.demo.view;

import com.example.demo.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.net.URL;

public class LoginMenu extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage = Main.stage;
        URL url = LoginMenu.class.getResource("/view/FXML/LoginMenu.fxml");
        BorderPane borderPane = FXMLLoader.load(url);
        if (Main.isBlackAndWhite)
            Main.blackAndWhite(borderPane);

        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.getIcons().add(new Image(new FileInputStream("src/main/resources/Image/logo.png")));
        stage.setTitle("Login and Register Menu");
        stage.show();
    }


}
