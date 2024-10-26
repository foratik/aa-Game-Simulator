package com.example.demo.model;

public class OtherGameData {
    public int minute;
    public int second;
    public int score;
    public double progress;
    public int totalBalls;
    public int ballsCount;

    public OtherGameData(int minute, int second, int score, double progress, int totalBalls, int ballsCount) {
        this.minute = minute;
        this.second = second;
        this.score = score;
        this.progress = progress;
        this.totalBalls = totalBalls;
        this.ballsCount = ballsCount;
    }
}
