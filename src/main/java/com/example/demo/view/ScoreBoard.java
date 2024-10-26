package com.example.demo.view;

import com.example.demo.Main;
import com.example.demo.controller.ProfileMenuController;
import com.example.demo.model.Data;
import com.example.demo.model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;

public class ScoreBoard extends Application {
    public GridPane easy;
    public GridPane medium;
    public GridPane hard;
    public GridPane total;
    public Button back;


    @Override
    public void start(Stage stage) throws Exception {
        URL url = LoginMenu.class.getResource("/view/FXML/ScoreBoard.fxml");
        AnchorPane anchorPane = FXMLLoader.load(url);
        if (Main.isBlackAndWhite)
            Main.blackAndWhite(anchorPane);

        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.setTitle("Score Board");
        stage.show();
    }


    public void initialize(){
        initializeBoards(total,easy,medium,hard);
        addData();
        back.setViewOrder(-1);
    }

    private void initializeBoards(GridPane... gridPanes){
        for (GridPane gridPane : gridPanes){
            Text text1 = new Text("rank");
            Text text2 = new Text("username");
            Text text3 = new Text("score");
            Text text4 = new Text("time");
            gridPane.add(text1,0,0);
            gridPane.add(text2,1,0);
            gridPane.add(text3,2,0);
            gridPane.add(text4,3,0);
        }

    }

    private void addData(){
        int count;
        if (Data.getUsers().size()<10)
            count=Data.getUsers().size();
        else count = 10;
        Data.getUsers().sort(new User.Sort());
        addDataToPane(count, total);

        Data.getUsers().sort(new User.easySort());
        addDataToPane(count, easy);

        Data.getUsers().sort(new User.mediumSort());
        addDataToPane(count, medium);

        Data.getUsers().sort(new User.hardSort());
        addDataToPane(count, hard);
    }

    private void addDataToPane(int count, GridPane gridPane) {
        for (int i=1;i<=count;i++){
            Text rank = new Text(String.valueOf(i));
            Text username = new Text(Data.getUsers().get(i-1).getUsername());
            Text score;
            Text time;
            if (gridPane.equals(easy)){
                 score = new Text(String.valueOf(Data.getUsers().get(i-1).getEasyHighScore()));
                time = new Text(String.valueOf(Data.getUsers().get(i-1).getEasyTime()));
            }else if (gridPane.equals(medium)){
                score = new Text(String.valueOf(Data.getUsers().get(i-1).getMediumHighScore()));
                time = new Text(String.valueOf(Data.getUsers().get(i-1).getMediumTime()));
            }else if (gridPane.equals(hard)){
                score = new Text(String.valueOf(Data.getUsers().get(i-1).getHardHighScore()));
                time = new Text(String.valueOf(Data.getUsers().get(i-1).getHardTime()));
            }else {
                score = new Text(String.valueOf(Data.getUsers().get(i-1).getHighScore()));
                time = new Text(String.valueOf(Data.getUsers().get(i-1).getTime()));
            }

            if (i==1){
                rank.setFill(Color.GREEN);
                username.setFill(Color.GREEN);
                score.setFill(Color.GREEN);
                time.setFill(Color.GREEN);
            }else if (i==2){
                rank.setFill(Color.BLUE);
                username.setFill(Color.BLUE);
                score.setFill(Color.BLUE);
                time.setFill(Color.BLUE);
            }else if (i==3){
                rank.setFill(Color.RED);
                username.setFill(Color.RED);
                score.setFill(Color.RED);
                time.setFill(Color.RED);
            }
            if (i<4){
                rank.setFont(new Font("Arial",20));
                username.setFont(new Font("Arial",20));
                score.setFont(new Font("Arial",20));
                time.setFont(new Font("Arial",20));
            }

            gridPane.add(rank,0,i);
            gridPane.add(username,1,i);
            gridPane.add(score,2,i);
            gridPane.add(time,3,i);
        }
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        if (ProfileMenuController.currentUser!=null)
            new ProfileMenu().start(Main.stage);
        else new LoginMenu().start(Main.stage);
    }
}
