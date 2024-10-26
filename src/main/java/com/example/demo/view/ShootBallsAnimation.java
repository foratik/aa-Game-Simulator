package com.example.demo.view;

import com.example.demo.controller.GameController;
import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class ShootBallsAnimation extends Transition {

    private Line line;
    private Circle circle;
    private Text text;
    private int speed;
    private boolean finish;

    public ShootBallsAnimation(Line line, Circle circle, Text text, int speed) {
        this.line = line;
        this.circle = circle;
        this.text = text;
        this.speed = speed;
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double v) {
        finish = false;

        if (circle.getCenterY()<0){
            finish=true;
            this.stop();
            Game.gamePane.getChildren().remove(circle);
        }
        circle.setCenterY(circle.getCenterY() + speed);
        circle.setCenterX(circle.getCenterX() + speed * Math.sin(Math.toRadians(Game.angle)));
        line.setStartY(line.getStartY() + speed);
        line.setStartX(line.getStartX() + speed * Math.sin(Math.toRadians(Game.angle)));
        line.setEndY(line.getEndY() + speed);
        line.setEndX(line.getEndX() + speed * Math.sin(Math.toRadians(Game.angle)));
        text.setY(text.getY() + speed);
        text.setX(text.getX() + speed * Math.sin(Math.toRadians(Game.angle)));

        if (Game.angle != 0) {
            Game.gamePane.getChildren().remove(line);

            Circle node = circle;
            double destination = Math.pow((((Circle) node).getCenterX() - GameController.staticMainCircle.getCenterX()), 2) +
                    Math.pow((((Circle) node).getCenterY() - GameController.staticMainCircle.getCenterY()), 2);
            if (destination <= 23615) {

                this.stop();
                Line newLine = new Line(circle.getCenterX(), circle.getCenterY(), GameController.staticMainCircle.getCenterX(), GameController.staticMainCircle.getCenterY());
                Game.gamePane.getChildren().add(newLine);

                checkLoose();
            }
        }
        if (Game.angle == 0)
            if (line.getBoundsInParent().intersects(GameController.staticMainCircle.getBoundsInParent())) {
                this.stop();
                checkLoose();
            }


        if (finish)
            Game.endLoose();
    }

    private void checkLoose() {
        for (Node node1 : Game.gamePane.getChildren()) {
            if (node1 instanceof Circle) {
                if (((Circle) node1).getCenterY()<0){
                    finish = true;
                    return;
                }
                for (Node node2 : Game.gamePane.getChildren()) {
                    if (node2 instanceof Circle)
                        if (node2 != (node1) && node1.getBoundsInParent().intersects(node2.getBoundsInParent())) {
                            System.out.println("game over");
                            finish = true;
                            return;
                        }
                }
            }

        }
    }

}
