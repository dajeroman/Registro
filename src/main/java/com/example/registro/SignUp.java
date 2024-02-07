package com.example.registro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

import java.io.IOException;

public class SignUp {
    @FXML
    private AnchorPane SignUpScene;

    @FXML
    void switchToHome(ActionEvent event) throws IOException {

        new SceneController(SignUpScene, "home.fxml");

    }
}
