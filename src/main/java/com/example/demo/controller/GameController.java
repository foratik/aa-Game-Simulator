package com.example.demo.controller;

import com.example.demo.Main;
import com.example.demo.model.Music;
import com.example.demo.view.Game;
import com.example.demo.view.LoginMenu;
import com.example.demo.view.ProfileMenu;
import com.example.demo.view.ScoreBoard;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static com.example.demo.view.Game.ballsCount;

public class GameController {
    public ProgressBar progressBar;
    public static ProgressBar staticProgressBar;
    public static double progress = 0.1;
    public ImageView muteButton;
    public Circle mainCircle;
    public static Circle staticMainCircle;
    public Text scoreText;
    public static Text staticScoreText;
    public Text leftBalls;
    public static Text staticLeftBalls;
    public Text degree;
    public Text time;
    public static Text staticDegree;
    public static Text staticTime;
    public ImageView pauseButton;
    public ImageView pauseMenu;
    public static ImageView staticPauseMenu;
    public Button restart;
    public Button endGame;
    public Button pauseMusic;
    public Button resume;
    public VBox pauseVBox;
    public static VBox staticPauseVBox;
    public Button playMusic;
    public Text shootingKey;
    public Text freezingKey;
    public ImageView endWindow;
    public static ImageView staticEndWindow;
    public VBox endVBox;
    public static VBox staticEndVBox;
    public Text finalScore;
    public Text elapsedSeconds;
    public Button backToProfile;
    public Button scoreBoard;
    public static Text staticFinalScore;
    public static Text staticElapsedSeconds;
    public static Button staticBackToProfile;
    public static Button staticScoreBoard;
    public Circle secondPlayer;
    public Button saveGame;
    public Text rightKey;
    public Text leftKey;


    public void initialize(){
        staticProgressBar = progressBar;
        staticMainCircle = mainCircle;
        staticScoreText = scoreText;
        staticLeftBalls = leftBalls;
        staticDegree = degree;
        staticTime = time;
        staticPauseMenu = pauseMenu;
        staticPauseVBox = pauseVBox;
        staticEndWindow = endWindow;
        staticEndVBox = endVBox;
        staticFinalScore = finalScore;
        staticElapsedSeconds = elapsedSeconds;
        staticBackToProfile = backToProfile;
        staticScoreBoard = scoreBoard;
        if (!Game.is2Player)
            secondPlayer.setVisible(false);
        else secondPlayer.setVisible(true);
        GameController.staticPauseMenu.setVisible(false);
        GameController.staticPauseVBox.setVisible(false);
        GameController.staticEndWindow.setVisible(false);
        GameController.staticEndVBox.setVisible(false);
        shootingKey.setText("Shooting key :"+Game.shootingKey);
        freezingKey.setText("Freezing key :"+Game.freezingKey);
        rightKey.setText("Right key : "+Game.rightKey);
        leftKey.setText("Left key : "+Game.leftKey);

        staticLeftBalls.setText("Balls left :"+ballsCount);
        staticScoreText.setFont(new Font("Arial",24));
        staticScoreText.setText(String.valueOf(Game.score));
        progressBar.setProgress(progress);
        setMusicButton();

        if (Main.isPersian)
            persian();
    }

    private void persian(){
        restart.setText("شروع مجدد");
        resume.setText("ادامه");
        pauseMusic.setText("توقف موسیقی");
        playMusic.setText("پخش موسیقی");
        endGame.setText("پایان بازی");
        scoreBoard.setText("جدول امتیازات");
        backToProfile.setText("بازگشت به پروفایل");
        degree.setText("درجه : 0");
        saveGame.setText("ذخیره بازی");
    }
    public void mute(MouseEvent mouseEvent) {
        ProfileMenuController.mute(muteButton);
    }

    private void setMusicButton() {
        setupMusicButton(muteButton);
    }

    static void setupMusicButton(ImageView muteButton) {
        if (!Music.isMute()) {
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

    public static void setProgress(){
        staticProgressBar.setProgress(progress);
    }

    public void pause(MouseEvent mouseEvent) {
        Game.pause();
    }

    public void restart(MouseEvent mouseEvent) throws Exception {
        new Game().start(Main.stage);
    }

    public void endGame(MouseEvent mouseEvent) throws Exception {
        if (ProfileMenuController.currentUser!=null)
        new ProfileMenu().start(Main.stage);
        else new LoginMenu().start(Main.stage);
    }

    public void pauseMusic(MouseEvent mouseEvent) {
        Music.pause();
    }

    public void resume(MouseEvent mouseEvent) {
        GameController.staticPauseVBox.setVisible(false);
        GameController.staticPauseMenu.setVisible(false);
        Game.playAnimations();
    }

    public void playMusic(MouseEvent mouseEvent) {
        Music.play();
    }

    public void backToProfile(MouseEvent mouseEvent) throws Exception {
        new ProfileMenu().start(Main.stage);
    }

    public void scoreBoard(MouseEvent mouseEvent) throws Exception {
        new ScoreBoard().start(Main.stage);
    }

    public void saveGame(MouseEvent mouseEvent) {
        Game.saveGame();
    }
}
