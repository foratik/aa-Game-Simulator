package com.example.demo.controller;

import com.example.demo.Main;
import com.example.demo.model.GameDifficulty;
import com.example.demo.view.Game;
import com.example.demo.view.ProfileMenu;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class SettingsController {

    public ChoiceBox hardness;
    public ChoiceBox ballsCount;
    public Button back;
    public Button shootKey;
    public Button freezeKey;
    public Button rightKey;
    public Button leftKey;

    public void initialize(){
        hardness.getItems().add(1);
        hardness.getItems().add(2);
        hardness.getItems().add(3);
        hardness.setValue("Hardness");


        hardness.setOnAction((event) -> {
            int selectedIndex = hardness.getSelectionModel().getSelectedIndex();

            if (selectedIndex == 0) ProfileMenuController.gameDifficulty= GameDifficulty.EASY;
            else if (selectedIndex == 1) ProfileMenuController.gameDifficulty = GameDifficulty.MEDIUM;
            else if (selectedIndex == 2) ProfileMenuController.gameDifficulty = GameDifficulty.HARD;


        });


        for (int i=4 ; i<25 ; i++){
            ballsCount.getItems().add(i);
        }
        ballsCount.setValue("Balls Count");

        ballsCount.setOnAction((event) -> {

            Game.totalBalls = ballsCount.getSelectionModel().getSelectedIndex()+4;

        });


        if (Main.isPersian)
            persian();
    }

    private void persian(){
        hardness.setValue("سختی");
        ballsCount.setValue("تعداد توپ ها");
        back.setText("بازگشت");
        shootKey.setText("تغییر دکمه پرتاب");
        freezeKey.setText("تفییر دکمه فریز");
        rightKey.setText("تغییر دکمه سمت راست");
        leftKey.setText("تغییر دکمه سمت چپ");
    }
    public void back(MouseEvent mouseEvent) throws Exception {
        new ProfileMenu().start(Main.stage);
    }

    public void changeShootKey(MouseEvent mouseEvent) {
        shootKey.setFocusTraversable(true);
        shootKey.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String keyName = keyEvent.getCode().getName();
                Game.shootingKey = keyName;
                shootKey.setFocusTraversable(false);
                System.out.println(keyName);
            }
        });
    }

    public void changeFreezeKey(MouseEvent mouseEvent) {
        freezeKey.setFocusTraversable(true);
        freezeKey.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String keyName = keyEvent.getCode().getName();
                Game.freezingKey = keyName;
                freezeKey.setFocusTraversable(false);
                System.out.println(keyName);
            }
        });
    }

    public void changeRightKey(MouseEvent mouseEvent) {
        rightKey.setFocusTraversable(true);
        rightKey.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String keyName = keyEvent.getCode().getName();
                Game.rightKey = keyName;
                rightKey.setFocusTraversable(false);
                System.out.println(keyName);
            }
        });
    }

    public void changeLeftKey(MouseEvent mouseEvent) {
        leftKey.setFocusTraversable(true);
        leftKey.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String keyName = keyEvent.getCode().getName();
                Game.leftKey = keyName;
                leftKey.setFocusTraversable(false);
                System.out.println(keyName);
            }
        });
    }
}
