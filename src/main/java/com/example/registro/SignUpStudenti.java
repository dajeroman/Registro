package com.example.registro;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

import static java.lang.System.out;

public class SignUpStudenti{

    @FXML
    private AnchorPane SignUpStudentiScene;

    @FXML
    private Label cognomeError;

    @FXML
    private Label idError;

    @FXML
    private Label idLabel;

    @FXML
    private TextField nameInput;

    @FXML
    private Label nomeError;

    @FXML
    private Label studenteAggiunto;

    @FXML
    private TextField surnameInput;

    Random randomId = new Random();
    private boolean ottieni_ID_pressed;
    private boolean boolIDExist;
    int[] randArray = new int[9];

    private static final String URL = "jdbc:mysql://localhost:3306/registro";
    private static final String USER = "root";
    private static final String PASSWORD = "795123";

    @FXML
    void initialize() {
        studenteAggiunto.setOpacity(0.0);
    }

    @FXML
    void switchToLogIn(ActionEvent event) throws IOException {
        new SceneController(SignUpStudentiScene, "login.fxml");
    }

    public void generateID() {
        for (int i = 0; i < randArray.length; i++) {
            randArray[i] = 100_000_000 + randomId.nextInt(900_000_000);
        }
    }

    void getID() {
        generateID();
        for (int generatedID : randArray) {
            if (!idExists(generatedID)) {
                idLabel.setText(String.valueOf(generatedID));
                return;
            }
        }
        getID();
    }

    @FXML
    void buttGenerateID(ActionEvent event) {
        generateID();
        getID();
        boolIDExist = true;
    }

    private boolean idExists(int id) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM registro.registro WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
                try (var resultSet = preparedStatement.executeQuery()) {
                    boolIDExist = resultSet.next();
                    return boolIDExist;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean studentExists(int id, String nome, String cognome) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "INSERT INTO registro (id, nome, cognome) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, nome);
                preparedStatement.setString(3, cognome);

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addStudent(ActionEvent actionEvent) {
        if (!boolIDExist) {
            idError.setText("Prima di aggiungere uno studente, ottieni un ID.");
            return;
        }

        String nome = nameInput.getText();
        String cognome = surnameInput.getText();
        int id = Integer.parseInt(idLabel.getText());

        if (studentExists(id, nome, cognome)) {
            studenteAggiunto.setText("Studente aggiunto!");
            studenteAggiunto.setOpacity(1.0);
            return;
        }

        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "INSERT INTO registro (id, nome, cognome) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, nome);
                preparedStatement.setString(3, cognome);

                preparedStatement.executeUpdate();
            }

            idError.setText("");
            out.println("Studente aggiunto con successo al database.");
            studenteAggiunto.setText("Studente aggiunto con successo al database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
