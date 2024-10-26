package com.example.demo.model;

import com.example.demo.Main;

import java.net.URL;
import java.util.Comparator;

public class User {

    private String username;
    private String password;
    private URL avatarUrl;
    private int highScore;
    private int time;
    private int easyHighScore;
    private int easyTime;
    private int mediumHighScore;
    private int mediumTime;
    private int hardHighScore;
    private int hardTime;

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public static class Sort implements Comparator<User> {
        public int compare(User a, User b) {
            if (b.getHighScore() != a.getHighScore())
                return b.getHighScore() - a.getHighScore();
            return a.getTime() - b.getTime();
        }
    }

    public static class easySort implements Comparator<User> {
        public int compare(User a, User b) {
            if (b.getEasyHighScore() != a.getEasyHighScore())
                return b.getEasyHighScore() - a.getEasyHighScore();
            return a.getEasyTime() - b.getEasyTime();
        }
    }

    public static class mediumSort implements Comparator<User> {
        public int compare(User a, User b) {
            if (b.getMediumHighScore() != a.getMediumHighScore())
                return b.getMediumHighScore() - a.getMediumHighScore();
            return a.getMediumTime() - b.getMediumTime();
        }
    }

    public static class hardSort implements Comparator<User> {
        public int compare(User a, User b) {
            if (b.getHardHighScore() != a.getHardHighScore())
                return b.getHardHighScore() - a.getHardHighScore();
            return a.getHardTime() - b.getHardTime();
        }
    }

    public void setPassword(String password) {
        this.password = password;
        Data.saveUsersInJSON();
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getEasyTime() {
        return easyTime;
    }

    public void setEasyTime(int easyTime) {
        this.easyTime = easyTime;
    }

    public int getMediumTime() {
        return mediumTime;
    }

    public void setMediumTime(int mediumTime) {
        this.mediumTime = mediumTime;
    }

    public int getHardTime() {
        return hardTime;
    }

    public void setHardTime(int hardTime) {
        this.hardTime = hardTime;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public int getEasyHighScore() {
        return easyHighScore;
    }

    public void setEasyHighScore(int easyHighScore) {
        this.easyHighScore = easyHighScore;
    }

    public int getMediumHighScore() {
        return mediumHighScore;
    }

    public void setMediumHighScore(int mediumHighScore) {
        this.mediumHighScore = mediumHighScore;
    }

    public int getHardHighScore() {
        return hardHighScore;
    }

    public void setHardHighScore(int hardHighScore) {
        this.hardHighScore = hardHighScore;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setUsername(String username) {
        this.username = username;
        Data.saveUsersInJSON();
    }

    public User(String username, String password) {
        this.highScore = 0;
        this.easyHighScore = 0;
        this.mediumHighScore = 0;
        this.hardHighScore = 0;
        this.time=0;
        this.easyTime=0;
        this.hardTime=0;
        this.mediumTime=0;
        this.username = username;
        this.password = password;
        this.avatarUrl = Main.class.getResource("/Image/avatar1.png");
        Data.addUser(this);
        Data.saveUsersInJSON();
    }

    public User(){

    }

    public URL getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(URL avatarUrl) {
        this.avatarUrl = avatarUrl;
        Data.saveUsersInJSON();
    }
}
