package com.example.demo.view;

import com.example.demo.controller.GameController;
import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class RotateAnimation extends Transition {


    public RotateAnimation() {
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double v) {

        Rotate rotate = new Rotate();

        rotate.setPivotX(450);
        rotate.setPivotY(180);
        rotate.setAngle(Game.rotationSpeed);

        for (Node node : Game.gamePane.getChildren()) {
            if (node instanceof Line) {
                if (node.getBoundsInParent().intersects(GameController.staticMainCircle.getLayoutBounds()))
                    node.getTransforms().add(rotate);
            }
            if (node instanceof Circle) {
                double destination = Math.pow((((Circle) node).getCenterX()-GameController.staticMainCircle.getCenterX()),2)+
                        Math.pow((((Circle) node).getCenterY()-GameController.staticMainCircle.getCenterY()),2);
                if (destination<=23615)
                    node.getTransforms().add(rotate);
            }
            if (node instanceof Text) {
                double destination = Math.pow((((Text) node).getX()-GameController.staticMainCircle.getCenterX()),2)+
                        Math.pow((((Text) node).getY()-GameController.staticMainCircle.getCenterY()),2);
                if (destination<=23615)
                    node.getTransforms().add(rotate);
            }
        }

    }
}
