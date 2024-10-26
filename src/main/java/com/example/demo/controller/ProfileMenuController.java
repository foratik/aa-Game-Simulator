package com.example.demo.controller;

import com.example.demo.Main;
import com.example.demo.model.Data;
import com.example.demo.model.GameDifficulty;
import com.example.demo.model.Music;
import com.example.demo.model.User;
import com.example.demo.view.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ProfileMenuController {
    public static User currentUser;
    public ImageView userAvatar;
    public TextField newUsername;
    public PasswordField newPassword;
    public ImageView muteButton;
    public HBox avatarsHBox;
    public Button chooseFromFile;
    public Button chooseFromGallery;
    public Button changePasswordButton;
    public Button changeUsernameButton;
    public Label changeText;
    public Button deleteAccountButton;
    public Button logoutButton;
    public ChoiceBox choiceBox;
    public ChoiceBox changeMusic;
    public Button startGame;
    public Button scoreBoard;
    public ChoiceBox colorBox;
    public static GameDifficulty gameDifficulty;
    public Button settings;
    public Button exitGame;
    public Button twoPlayerGame;
    public Button continueGame;


    public void initialize() throws IOException {

        choiceBox.getItems().add("فارسی");
        choiceBox.getItems().add("English");
        choiceBox.setValue("Language|زبان");

        choiceBox.setOnAction((event) -> {
            int selectedIndex = choiceBox.getSelectionModel().getSelectedIndex();

            if (selectedIndex == 0) Main.isPersian = true;
            else Main.isPersian = false;
            if (Main.isPersian) persian();
            else {
                try {
                    new ProfileMenu().start(Main.stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        colorBox.getItems().add("color");
        colorBox.getItems().add("black and white");
        colorBox.setValue("change game color");

        colorBox.setOnAction((event) -> {
            int selectedIndex = colorBox.getSelectionModel().getSelectedIndex();

            if (selectedIndex == 1) Main.isBlackAndWhite = true;
            else Main.isBlackAndWhite = false;
        });

        changeMusic.getItems().add("1");
        changeMusic.getItems().add("2");
        changeMusic.getItems().add("3");
        changeMusic.setValue("choose music");

        changeMusic.setOnAction((event) -> {
            int selectedIndex = changeMusic.getSelectionModel().getSelectedIndex() + 1;
            if (selectedIndex > 0 && selectedIndex < 4) Music.changeMusic(selectedIndex);
            setMusicButton();
        });


        reserAvatar();
        avatarsHBox.setVisible(false);
        if (Main.isPersian) persian();
        setMusicButton();
    }

    private void setMusicButton() {
        GameController.setupMusicButton(muteButton);
    }

    public void persian() {
        chooseFromFile.setText("انتخاب از فایل");
        chooseFromGallery.setText("انتخاب از گالری");
        newUsername.setPromptText("نام کاربری جدید");
        newPassword.setPromptText("رمز عبور جدید");
        changePasswordButton.setText("تغییر رمز عبور");
        changeUsernameButton.setText("تغییر نام کاربری");
        changeText.setText("تغییر نام کاربری یا رمز عبور");
        deleteAccountButton.setText("حذف حساب کاربری");
        logoutButton.setText("خروج از حساب کاربری");
        changeMusic.setValue("انتخاب آهنگ");
        colorBox.setValue("انتخاب رنگ بازی");
        startGame.setText("شروع بازی");
        scoreBoard.setText("جدول امتیازات");
        settings.setText("تنظیمات");
        exitGame.setText("خروج از بازی");
        twoPlayerGame.setText("بازی دو نفره");
        continueGame.setText("ادامه بازی");
    }

    public void chooseFromFile(MouseEvent mouseEvent) throws IOException {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG, PNG, JEPG Files", "*.jpg", "*.png", "*.jepg"));
        File selectedFile = fc.showOpenDialog(Main.stage);
        if (selectedFile != null) {
            currentUser.setAvatarUrl(selectedFile.toURI().toURL());

            reserAvatar();

        } else {
            System.out.println("file is not valid");
        }
    }

    public void deleteAccount() {
        Data.removeUser(currentUser);
        Data.saveUsersInJSON();
        currentUser = null;
        try {
            new LoginMenu().start(Main.stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void reserAvatar() throws IOException {
        userAvatar.setImage(new Image(currentUser.getAvatarUrl().openStream()));
        Circle clip = new Circle(50, 50, 50);
        userAvatar.setClip(clip);
    }

    public void changeUsername(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("change username error");
        if (Data.findUser(newUsername.getText()) != null) {
            alert.setContentText("your entered username is already existing!");
            alert.showAndWait();
        } else {
            currentUser.setUsername(newUsername.getText());
        }
    }

    public void changePassword(MouseEvent mouseEvent) {
        if (newPassword.getText().length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("change password error");
            alert.setContentText("your new password is empty!");
            alert.showAndWait();
        } else {
            currentUser.setPassword(newPassword.getText());
        }
    }

    public void mute(MouseEvent mouseEvent) {
        mute(muteButton);
    }

    static void mute(ImageView muteButton) {
        if (Music.isMute()) {
            try {
                muteButton.setImage(new Image(new FileInputStream("src/main/resources/Image/mute.png")));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            Music.setPlayer(false);
        } else {
            try {
                muteButton.setImage(new Image(new FileInputStream("src/main/resources/Image/unmute.png")));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            Music.setPlayer(true);
        }
    }

    public void chooseFromGallery(MouseEvent mouseEvent) {
        avatarsHBox.setVisible(true);
    }

    public void choosePicture1(MouseEvent mouseEvent) throws IOException {
        currentUser.setAvatarUrl(Main.class.getResource("/Image/Avatars/1.png"));
        reserAvatar();
        avatarsHBox.setVisible(false);
    }

    public void choosePicture2(MouseEvent mouseEvent) throws IOException {
        currentUser.setAvatarUrl(Main.class.getResource("/Image/Avatars/2.png"));
        reserAvatar();
        avatarsHBox.setVisible(false);
    }

    public void choosePicture3(MouseEvent mouseEvent) throws IOException {
        currentUser.setAvatarUrl(Main.class.getResource("/Image/Avatars/3.png"));
        reserAvatar();
        avatarsHBox.setVisible(false);
    }

    public void choosePicture4(MouseEvent mouseEvent) throws IOException {
        currentUser.setAvatarUrl(Main.class.getResource("/Image/Avatars/4.png"));
        reserAvatar();
        avatarsHBox.setVisible(false);
    }

    public void logout(MouseEvent mouseEvent) throws Exception {
        currentUser = null;
        new LoginMenu().start(Main.stage);
    }

    public void startGame(MouseEvent mouseEvent) throws Exception {
        Game.continueGame = false;
        Game.is2Player = false;
        new Game().start(Main.stage);
    }

    public void scoreBoard(MouseEvent mouseEvent) throws Exception {
        new ScoreBoard().start(Main.stage);
    }

    public void settings(MouseEvent mouseEvent) throws Exception {
        new Settings().start(Main.stage);
    }

    public void exitGame(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void twoPlayerGame(MouseEvent mouseEvent) throws Exception {
        Game.is2Player = true;
        new Game().start(Main.stage);
    }

    public void continueGame(MouseEvent mouseEvent) throws Exception {
        Game.continueGame = true;
        Data.initializeGameData();
        new Game().start(Main.stage);
    }
}
