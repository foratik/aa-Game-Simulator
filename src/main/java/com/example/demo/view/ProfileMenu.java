package com.example.demo.view;

import com.example.demo.Main;
import com.example.demo.controller.ProfileMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ProfileMenu extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage = Main.stage;
        URL url = LoginMenu.class.getResource("/view/FXML/ProfileMenu.fxml");
        AnchorPane anchorPane = FXMLLoader.load(url);
        if (Main.isBlackAndWhite)
            Main.blackAndWhite(anchorPane);
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.setTitle("Profile Menu");
        stage.show();
    }




}
