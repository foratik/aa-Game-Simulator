module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;
    requires com.google.gson;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    opens com.example.demo.controller;
    exports com.example.demo.controller;
    opens com.example.demo.model to com.google.gson;
    exports com.example.demo.view;
}