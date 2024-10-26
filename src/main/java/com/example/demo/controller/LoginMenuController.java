package com.example.demo.controller;

import com.example.demo.Main;
import com.example.demo.model.Data;
import com.example.demo.model.User;
import com.example.demo.view.Game;
import com.example.demo.view.ProfileMenu;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class LoginMenuController {
    public TextField username;
    public PasswordField password;
    public ImageView muteButton;
    public TextField loginUsername;
    public PasswordField loginPassword;
    public Label registerLabel;
    public Label loginLabel;
    public Button resetButton;
    public Button registerButton;
    public Button loginButton;
    public Button guestButton;


    public void initialize() {
        if (Main.isPersian) {
            registerLabel.setText("ثبت نام");
            loginLabel.setText("ورود");
            username.setPromptText("نام کاربری");
            password.setPromptText("رمز عبور");
            loginUsername.setPromptText("نام کاربری");
            loginPassword.setPromptText("رمز عبور");
            registerButton.setText("ثبت نام");
            loginButton.setText("ورود");
            resetButton.setText("تنظیم مجدد");
            guestButton.setText("شروع بازی به عنوان مهمان");
        }
    }

    public void register(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("registration error");
        if (Data.findUser(username.getText()) != null) {
            alert.setContentText("your entered username already exists!");
            alert.showAndWait();
        } else if (username.getText().length() == 0) {
            alert.setContentText("your username field is empty!");
            alert.showAndWait();
        } else if (password.getText().length() == 0) {
            alert.setContentText("your password field is empty!");
            alert.showAndWait();
        } else {
            User user = new User(username.getText(), password.getText());
            username.setText("");
            password.setText("");
        }

    }

    public void login(MouseEvent mouseEvent) throws Exception {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("login error");
        User user = Data.findUser(loginUsername.getText());
        if (loginUsername.getText().length() == 0) {
            alert.setContentText("your username field is empty!");
            alert.showAndWait();
        } else if (loginPassword.getText().length() == 0) {
            alert.setContentText("your password field is empty!");
            alert.showAndWait();
        } else if (user == null) {
            alert.setContentText("your entered username doesn't exist!");
            alert.showAndWait();
        } else if (!user.getPassword().equals(loginPassword.getText())) {
            alert.setContentText("your entered password is wrong!");
            alert.showAndWait();
        } else {
            ProfileMenuController.currentUser = user;
            new ProfileMenu().start(Main.stage);
        }
    }

    public void mute(MouseEvent mouseEvent) {
        ProfileMenuController.mute(muteButton);

    }

    public void guest(MouseEvent mouseEvent) throws Exception {
        Game.continueGame = false;
        Game.is2Player = false;
        new Game().start(Main.stage);
    }
}
