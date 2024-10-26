package com.example.demo.view;

import com.example.demo.Main;
import com.example.demo.controller.GameController;
import com.example.demo.controller.ProfileMenuController;
import com.example.demo.model.*;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import static com.example.demo.controller.GameController.*;

public class Game extends Application {

    public static Pane gamePane;
    private static ParallelTransition parTrans;
    private static ImageView background = new ImageView(new Image(Main.class.getResource("/Image/ice.png").toString(), 800, 500, false, false));
    ;
    private static ImageView background2 = new ImageView(new Image(Main.class.getResource("/Image/ice.png").toString(), 800, 500, false, false));
    ;
    public static int lineSize = 100;
    public static double rotationSpeed = 2;
    public static double defaultSpeed = 2;
    public static double windSpeed = 2;
    public static int freezeTime = 5;
    public static int ballsCount;
    public static int totalBalls = 10;
    public static int score;
    private static boolean phase2RotationFlag;
    private static boolean phase2RadiusFlag;
    private static boolean degreeTimelineFlag;
    private static boolean phase3invisible;
    private static Timeline visiblityTimeline;
    private static Timeline timer;
    private static Timeline changeRadiusTimeline;
    private static Timeline degreeTimeline;
    private static Timeline directionTimeline;
    private static int second;
    private static int minute;
    public static RotateAnimation rotateAnimation;
    public static String shootingKey;
    public static String freezingKey;
    public static String rightKey;
    public static String leftKey;
    private static Color circleColor;
    private static boolean loose;
    public static double angle;
    private static boolean phase4Active;
    private static double centerX;
    public static boolean is2Player = false;
    public static boolean continueGame;

    @Override
    public void start(Stage stage) throws Exception {
        angle = 0;
        score = 0;
        progress = 0;
        centerX = 450.0;
        phase2RotationFlag = true;
        phase2RadiusFlag = true;
        degreeTimelineFlag = true;
        phase3invisible = true;
        phase4Active = false;
        loose = false;
        ballsCount = totalBalls;
        circleColor = Color.BLACK;
        gamePane = FXMLLoader.load(Game.class.getResource("/view/FXML/Game.fxml").toURI().toURL());
        if (Main.isBlackAndWhite)
            Main.blackAndWhite(gamePane);

        if (!continueGame)
            setMap();
        timer();
        rotateAnimation = new RotateAnimation();
        rotateAnimation.play();
        newLine();
        background.setVisible(false);
        background2.setVisible(false);
        gamePane.getChildren().addAll(background, background2);

        setDifficulty();

        if (continueGame)
            loadGame();

        Scene scene = new Scene(gamePane);
        stage.setScene(scene);
        stage.setTitle("Game Menu");
        stage.show();
    }

    private void setDifficulty() {
        if (ProfileMenuController.gameDifficulty == null)
            ProfileMenuController.gameDifficulty = GameDifficulty.MEDIUM;
        freezeTime = ProfileMenuController.gameDifficulty.getFreezTime();
        windSpeed = ProfileMenuController.gameDifficulty.getWindSpeed();
        defaultSpeed = ProfileMenuController.gameDifficulty.getRotationSpeed();
        rotationSpeed = ProfileMenuController.gameDifficulty.getRotationSpeed();

        second = 0;
        minute = 2;

    }

    public void setMap() {

        Random random = new Random();
        int a = random.nextInt(3);

        switch (a) {
            case 0 -> {
                rotateNewLine(90);
                rotateNewLine(50);
                rotateNewLine(300);
                rotateNewLine(120);
                rotateNewLine(180);
            }
            case 1 -> {
                rotateNewLine(60);
                rotateNewLine(200);
                rotateNewLine(10);
                rotateNewLine(120);
                rotateNewLine(140);
            }
            case 2 -> {
                rotateNewLine(50);
                rotateNewLine(0);
                rotateNewLine(100);
                rotateNewLine(30);
                rotateNewLine(170);
            }
        }

    }

    private static void freezingAnimation() {
        background.setViewOrder(1);
        background2.setViewOrder(1);
        background.setVisible(true);
        background2.setVisible(true);
        TranslateTransition trans1 = new TranslateTransition(Duration.seconds(10), background);
        trans1.setFromX(0);
        trans1.setToX(800);
        trans1.setCycleCount(-1);
        TranslateTransition trans2 = new TranslateTransition(Duration.seconds(10), background2);
        trans2.setFromX(-800);
        trans2.setToX(0);
        trans2.setCycleCount(-1);
        parTrans = new ParallelTransition(trans1, trans2);
        parTrans.play();
    }

    public void rotateNewLine(int angle) {
        int startX, startY;
        startY = 230;
        startX = 450;
        Line line = new Line(startX, startY, startX, startY + 100);
        Circle circle = new Circle(450, 230 + lineSize, 10);

        Rotate rotate = new Rotate();

        rotate.setPivotX(450);
        rotate.setPivotY(180);
        rotate.setAngle(angle);

        line.getTransforms().add(rotate);
        circle.getTransforms().add(rotate);

        Game.gamePane.getChildren().addAll(line, circle);
    }

    public void newLine() {


        GameController.staticMainCircle.setFocusTraversable(true);
        Circle previewCircle = new Circle(450, 380 + lineSize, 10);
        Game.gamePane.getChildren().add(previewCircle);
        GameController.staticMainCircle.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String keyName = keyEvent.getCode().getName();

                if (!loose) {

                    if (keyName.equals(shootingKey)) {

                        Circle circle = new Circle(centerX, 380 + lineSize, 10);
                        Game.gamePane.getChildren().add(circle);

                        circle.setFill(circleColor);
                        previewCircle.setFill(circleColor);

                        Text text = new Text(String.valueOf(ballsCount));
                        text.setFill(Color.RED);
                        text.setX(centerX);
                        text.setY(380 + lineSize);

                        Game.gamePane.getChildren().addAll(text);

                        Line line = new Line(centerX, 380, centerX + lineSize * Math.sin(Math.toRadians(angle)), 380 + lineSize * Math.cos(Math.toRadians(angle)));


                        shootLine(line, circle, text, -5);

                        playShootingSound();

                        Game.gamePane.getChildren().add(line);
                        setBallsAndPhase();

                        setScore();

                        setProgress();

                    } else if (keyName.equals(freezingKey)) {
                        if (GameController.progress >= 0.7) {
                            freezingAnimation();
                            GameController.progress = 0;
                            GameController.setProgress();
                            rotationSpeed /= 4;
                            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(freezeTime * 1000),
                                    actionEvent -> {
                                        rotationSpeed = defaultSpeed;
                                        parTrans.stop();
                                        background.setVisible(false);
                                        background2.setVisible(false);
                                    }));
                            timeline.setCycleCount(1);
                            timeline.play();
                        }
                    }
                    if (is2Player) {
                        if (keyName.equals("Enter")) {
                            Circle circle = new Circle(450, 0, 10);
                            Game.gamePane.getChildren().add(circle);

                            circle.setFill(Color.BROWN);

                            Text text = new Text(String.valueOf(ballsCount));
                            text.setFill(Color.BLACK);
                            text.setX(450);
                            text.setY(0);

                            Game.gamePane.getChildren().addAll(text);

                            Line line = new Line(450, 0, 450 + lineSize * Math.sin(Math.toRadians(angle)), 0 + lineSize * Math.cos(Math.toRadians(angle)));


                            shootLine(line, circle, text, 2);

                            playShootingSound();

                            Game.gamePane.getChildren().add(line);
                            setBallsAndPhase();

                            setScore();

                            setProgress();
                        }
                    }
                    if (phase4Active) {
                        if (keyName.equals(rightKey)) {
                            if (previewCircle.getCenterX() < 790)
                                previewCircle.setCenterX(previewCircle.getCenterX() + 4);
                        } else if (keyName.equals(leftKey)) {
                            if (previewCircle.getCenterX() > 10)
                                previewCircle.setCenterX(previewCircle.getCenterX() - 4);
                        }
                        centerX = previewCircle.getCenterX();
                    }
                }

            }
        });

    }

    private static void setScore() {
        score += 2 * (rotationSpeed + windSpeed) + (7 - freezeTime);
        staticScoreText.setText(String.valueOf(score));
    }

    private static void setProgress() {
        if (GameController.progress + 0.15 < 1) {
            GameController.progress += 0.15;
        } else GameController.progress = 1;
        GameController.setProgress();
    }

    private void setBallsAndPhase() {
        ballsCount--;
        staticLeftBalls.setText("Balls left :" + ballsCount);
        if (Main.isPersian)
            staticLeftBalls.setText("توپ های باقی مانده :" + ballsCount);
        if (ballsCount == 0) {
            endWin();
        }
        if ((double) (totalBalls - ballsCount) / totalBalls >= 0.25) {
            phase2Rotation();
            phase2ChangeRadius();
            circleColor = Color.GREEN;
        }
        if ((double) (totalBalls - ballsCount) / totalBalls >= 0.5) {
            phase3invisible();
            circleColor = Color.BLUE;
        }
        if ((double) (totalBalls - ballsCount) / totalBalls >= 0.75) {
            phase4Active = true;
            circleColor = Color.PINK;
            setDegreeTimelineFlag();
        }
    }

    private static void playShootingSound() {
        String path = "src\\main\\resources\\Music\\shoot.m4a";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
    }


    private void phase2Rotation() {
        if (phase2RotationFlag) {

            Random random = new Random();
            int time = random.nextInt(3) + 4;
            directionTimeline = new Timeline(new KeyFrame(Duration.ZERO,
                    actionEvent -> {

                    }), new KeyFrame(Duration.millis(time * 1000),
                    actionEvent -> {
                        System.out.println("change direction");
                        if (Game.defaultSpeed == Game.rotationSpeed) {
                            Game.rotationSpeed = -1 * Game.defaultSpeed;
                        } else if (Game.defaultSpeed == -Game.rotationSpeed) {
                            Game.rotationSpeed = Game.defaultSpeed;
                        }
                    }), new KeyFrame(Duration.millis(time * 1000),
                    actionEvent -> {


                    }));
            directionTimeline.setCycleCount(-1);
            directionTimeline.play();
            phase2RotationFlag = false;
        }
    }


    private void phase2ChangeRadius() {
        if (phase2RadiusFlag) {

            changeRadiusTimeline = new Timeline(new KeyFrame(Duration.ZERO,
                    actionEvent -> {
                        for (Node node : gamePane.getChildren()) {
                            if (node instanceof Circle) {
                                if (node != GameController.staticMainCircle)
                                    ((Circle) node).setRadius(((Circle) node).getRadius() * 1.2);
                            }
                        }

                    }), new KeyFrame(Duration.millis(1000),
                    actionEvent -> {
                        for (Node node : gamePane.getChildren()) {
                            if (node instanceof Circle) {
                                if (node != GameController.staticMainCircle)
                                    ((Circle) node).setRadius(((Circle) node).getRadius() / 1.2);

                            }
                        }

                    }), new KeyFrame(Duration.millis(2000),
                    actionEvent -> {


                    }));
            changeRadiusTimeline.setCycleCount(-1);
            changeRadiusTimeline.play();


            phase2RadiusFlag = false;
        }
    }

    private void timer() {
        timer = new Timeline(new KeyFrame(Duration.ZERO,
                actionEvent -> {
                    if (second == 0) {
                        minute--;
                        second = 60;
                    }
                    if (minute < 0) {
                        endLoose();
                        minute = 0;
                        second = 1;
                    }
                    second--;

                    GameController.staticTime.setText("Time left : " + minute + ":" + second);
                    if (Main.isPersian)
                        GameController.staticTime.setText("زمان باقی مانده : " + minute + ":" + second);

                }), new KeyFrame(Duration.millis(1000),
                actionEvent -> {

                }));
        timer.setCycleCount(-1);
        timer.play();
    }

    private void phase3invisible() {
        if (phase3invisible) {

            visiblityTimeline = new Timeline(new KeyFrame(Duration.ZERO,
                    actionEvent -> {
                        for (Node node : gamePane.getChildren()) {
                            if (node instanceof Circle || node instanceof Line) {
                                if (node != GameController.staticMainCircle)
                                    node.setVisible(false);
                            }
                            if (node instanceof Text) {
                                if (((Text) node).getY() <= 330 && node != GameController.staticScoreText && node != GameController.staticLeftBalls)
                                    node.setVisible(false);
                            }
                        }

                    }), new KeyFrame(Duration.millis(1000),
                    actionEvent -> {
                        for (Node node : gamePane.getChildren()) {
                            if (node instanceof Circle || node instanceof Line) {
                                if (node != GameController.staticMainCircle)
                                    node.setVisible(true);
                            }
                            if (node instanceof Text) {
                                if (((Text) node).getY() <= 330 && node != GameController.staticScoreText)
                                    node.setVisible(true);
                            }
                        }

                    }), new KeyFrame(Duration.millis(2000),
                    actionEvent -> {


                    }));
            visiblityTimeline.setCycleCount(-1);
            visiblityTimeline.play();

            phase3invisible = false;
        }
    }

    public void shootLine(Line line, Circle circle, Text text, int speed) {
        ShootBallsAnimation shootBallsAnimation = new ShootBallsAnimation(line, circle, text, speed);
        shootBallsAnimation.play();
    }

    public static void pause() {
        stopAnimations();
        loose = true;
        GameController.staticPauseMenu.setVisible(true);
        GameController.staticPauseMenu.setViewOrder(-1);
        GameController.staticPauseVBox.setVisible(true);
        GameController.staticPauseVBox.setViewOrder(-1);
    }


    private static void stopAnimations() {
        rotateAnimation.stop();
        if (visiblityTimeline != null)
            visiblityTimeline.stop();
        if (timer != null)
            timer.stop();
        if (changeRadiusTimeline != null)
            changeRadiusTimeline.stop();
        if (directionTimeline != null)
            directionTimeline.stop();
        if (degreeTimeline != null)
            degreeTimeline.stop();
    }

    public static void playAnimations() {
        rotateAnimation.play();
        phase3invisible = true;
        phase2RadiusFlag = true;
        phase2RotationFlag = true;
        loose = false;
    }

    public static void endWin() {
        endWindow();
        stopAnimations();
        setScores();
        ImageView win = new ImageView(new Image(Main.class.getResource("/Image/green.png").toString(), 800, 600, false, false));
        win.setViewOrder(1);
        win.setVisible(true);
        gamePane.getChildren().add(win);
    }

    public static void endLoose() {
        endWindow();
        setScores();
        loose = true;
        stopAnimations();
        ImageView red = new ImageView(new Image(Main.class.getResource("/Image/red.png").toString(), 800, 600, false, false));
        red.setViewOrder(1);
        red.setVisible(true);
        gamePane.getChildren().add(red);
    }

    private static void endWindow() {
        GameController.staticEndWindow.setVisible(true);
        GameController.staticEndWindow.setViewOrder(-1);
        GameController.staticEndVBox.setVisible(true);
        GameController.staticEndVBox.setViewOrder(-1);
        if (ProfileMenuController.currentUser == null) {
            GameController.staticBackToProfile.setVisible(false);
        }
        GameController.staticFinalScore.setText("Your Final Score : " + score);
        GameController.staticElapsedSeconds.setText("Elapsed time : " + (120 - minute * 60 - second) + " second");
    }

    public static void setScores() {
        User current = ProfileMenuController.currentUser;
        if (current == null)
            return;
        if (ProfileMenuController.gameDifficulty == GameDifficulty.EASY) {
            if (current.getHighScore() < score) {
                current.setHighScore(score);
                current.setTime(120 - (60 * minute - second));
            }
            if (current.getEasyHighScore() < score) {
                current.setEasyHighScore(score);
                current.setEasyTime(120 - (60 * minute + second));
            }
        } else if (ProfileMenuController.gameDifficulty == GameDifficulty.MEDIUM) {
            if (current.getHighScore() < score) {
                current.setHighScore(score);
                current.setTime(120 - (60 * minute - second));
            }
            if (current.getMediumHighScore() < score) {
                current.setMediumHighScore(score);
                current.setMediumTime(120 - (60 * minute + second));
            }
        } else if (ProfileMenuController.gameDifficulty == GameDifficulty.HARD) {
            if (current.getHighScore() < score) {
                current.setHighScore(score);
                current.setTime(120 - (60 * minute - second));
            }
            if (current.getHardHighScore() < score) {
                current.setHardHighScore(score);
                current.setHardTime(120 - (60 * minute + second));
            }
        }

        Data.saveUsersInJSON();
    }

    private void setDegreeTimelineFlag() {
        if (degreeTimelineFlag) {
            degreeTimeline = new Timeline(new KeyFrame(Duration.millis(5000),
                    actionEvent -> {
                        Random random = new Random();
                        angle = random.nextInt(30) - 15;
                        GameController.staticDegree.setText("degree : " + String.valueOf(-1 * angle));
                        if (Main.isPersian)
                            GameController.staticDegree.setText("درجه : " + String.valueOf(-1 * angle));
                    }));
            degreeTimeline.setCycleCount(-1);
            degreeTimeline.play();
            degreeTimelineFlag = false;
        }
    }

    public static void saveGame() {
        Data.savedCircles = new ArrayList<>();
        Data.savedLines = new ArrayList<>();
        for (Node node : gamePane.getChildren()) {
            if (node instanceof Circle && !node.equals(GameController.staticMainCircle)) {
                double angle = 0;
                for (Transform transform : node.getTransforms()) {
                    if (transform instanceof Rotate) {
                        angle += ((Rotate) transform).getAngle();
                    }
                }
                if (((Circle) node).getCenterY() > 0 && ((Circle) node).getCenterY() < 450)
                    Data.savedCircles.add(new CircleGameData(angle, ((Circle) node).getCenterX(), ((Circle) node).getCenterY(), ((Circle) node).getRadius()));
            }
            if (node instanceof Line) {
                double angle = 0;
                for (Transform transform : node.getTransforms()) {
                    if (transform instanceof Rotate) {
                        angle += ((Rotate) transform).getAngle();
                    }
                }
                Data.savedLines.add(new LineData(((Line) node).getStartX(), ((Line) node).getEndX(), ((Line) node).getStartY(), ((Line) node).getEndY(), angle));
            }
        }

        Data.otherGameData = new OtherGameData(minute, second, score, progress, totalBalls, ballsCount);

        Data.saveGameInJSON();
    }

    public static void loadGame() {

        for (CircleGameData circleGameData : Data.savedCircles) {
            Rotate rotate = new Rotate();
            rotate.setPivotX(450);
            rotate.setPivotY(180);
            Circle circle = new Circle(circleGameData.centerX, circleGameData.centerY, circleGameData.radius);
            rotate.setAngle(circleGameData.angle);
            circle.getTransforms().add(rotate);
            circle.setVisible(true);
            gamePane.getChildren().add(circle);
        }
        for (LineData lineData : Data.savedLines) {
            Rotate rotate = new Rotate();
            rotate.setPivotX(450);
            rotate.setPivotY(180);
            Line line = new Line(lineData.startX, lineData.startY, lineData.endX, lineData.endY);
            rotate.setAngle(lineData.angle);
            line.getTransforms().add(rotate);
            line.setVisible(true);
            gamePane.getChildren().add(line);
        }
        minute = Data.otherGameData.minute;
        second = Data.otherGameData.second;
        score = Data.otherGameData.score;
        progress = Data.otherGameData.progress;
        totalBalls = Data.otherGameData.totalBalls;
        ballsCount = Data.otherGameData.ballsCount;

        setProgress();
        staticScoreText.setText(String.valueOf(score));
        staticLeftBalls.setText("Balls left :" + ballsCount);
        if (Main.isPersian)
            staticLeftBalls.setText("توپ های باقی مانده :" + ballsCount);

    }
}
