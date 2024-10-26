package com.example.demo.model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Music {
    static MediaPlayer mediaPlayer;
    public static void playMusic() {

        String path = "src\\main\\resources\\Music\\3.mp3";

        Media media = new Media(new File(path).toURI().toString());

        mediaPlayer = new MediaPlayer(media);
        infinitePlayMusic();
        mediaPlayer.setAutoPlay(true);
    }

    public static void changeMusic(int number){
        mediaPlayer.stop();
        String path = "src\\main\\resources\\Music\\"+number+".mp3";
        Media media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        infinitePlayMusic();
    }

    public static void pause(){
        mediaPlayer.pause();
    }
    public static void play(){
        mediaPlayer.play();
    }

    private static void infinitePlayMusic() {
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
                mediaPlayer.play();
            }
        });
    }

    public static boolean isMute() {
        if (mediaPlayer.isMute()){
            return false;
        }
        return true;
    }

    public static void setPlayer(boolean play){
        if (play){
            mediaPlayer.setMute(false);
            mediaPlayer.setAutoPlay(true);
        }else mediaPlayer.setMute(true);
    }
}
