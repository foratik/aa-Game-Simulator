package com.example.demo.model;

public enum GameDifficulty {

    EASY(7,1.2,1),
    MEDIUM(5,1.5,2),
    HARD(3,1.8,4);
    private int freezTime;
    private double windSpeed;
    private double rotationSpeed;

    GameDifficulty(int freezTime, double windSpeed, double rotationSpeed) {
        this.freezTime = freezTime;
        this.windSpeed = windSpeed;
        this.rotationSpeed = rotationSpeed;
    }

    public int getFreezTime() {
        return freezTime;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getRotationSpeed() {
        return rotationSpeed;
    }
}
