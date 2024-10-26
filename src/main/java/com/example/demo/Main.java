package com.example.demo;

import com.example.demo.controller.ProfileMenuController;
import com.example.demo.model.Data;
import com.example.demo.model.Music;
import com.example.demo.view.Game;
import com.example.demo.view.LoginMenu;
import com.example.demo.view.ProfileMenu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    public static Stage stage;
    public static boolean isPersian = false;
    public static boolean isBlackAndWhite = false;

    public static void main(String[] args) throws Exception {
        ProfileMenuController.gameDifficulty = null;
        Game.shootingKey="Space";
        Game.freezingKey="Tab";
        Game.leftKey="Left";
        Game.rightKey="Right";
        Music.playMusic();
        Data.initializeUsers();
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        Main.stage=stage;
        new LoginMenu().start(stage);
    }

    public static void blackAndWhite(Pane Pane) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setSaturation(-1);
        Pane.setEffect(colorAdjust);
    }


}
