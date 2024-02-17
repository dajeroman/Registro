package com.example.registro;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

import javafx.scene.control.TextField;


public class SignUp {


    @FXML
    private AnchorPane SignUpScene;

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
    private TextField surnameInput;

    //Tutti classi
    Random randomId = new Random();

    //I miei variabili
    private boolean ottieni_ID_pressed;
    int[] randArray = new int[9];


    //SQL Variabili
    private static final String URL = "jdbc:mysql://localhost:3306/registro";
    private static final String USER = "root";
    private static final String PASSWORD = "795123";

    //funzioni
    @FXML
    void initialize() {
    }
    @FXML
    void switchToHome(ActionEvent event) throws IOException {//cambia scene a home

        new SceneController(SignUpScene, "home.fxml");

    }

    //ID funzioni
    public void generateID() {//genera numero casuale con lungezza 9 numeri
        for (int i = 0; i < randArray.length; i++) {
            randArray[i] = 100_000_000 + randomId.nextInt(900_000_000);
        }
    }

    void getID(){//restituisce guesto numero
        for (int number : randArray) {
            idLabel.setText(String.valueOf(number));
        }
    }

    @FXML
    void generateID(ActionEvent event) {
        generateID();
        getID();
        String nome = nameInput.getText().trim();
        String cognome = surnameInput.getText().trim();

    }

    //SQL Funzioni
    private boolean idExists(int id){//prova se id esiste
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM registro.registro WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);

                try (var resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next(); // If the result set has a next row, the student exists return true or false
                }
            }
        } catch (SQLException e) {
            // Handle the exception appropriately (e.g., show an error message or log it)
            e.printStackTrace();
        }
        return false;
    }


    private boolean studentExists(int id, String nome, String cognome) {//prova se studente esiste
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "INSERT INTO registro (id, nome, cognome) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, nome);
                preparedStatement.setString(2, nome);
                preparedStatement.setString(3, cognome);

                try (var resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next(); // If the result set has a next row, the student exists return true or false
                }
            }
        } catch (SQLException e) {
            // Handle the exception appropriately (e.g., show an error message or log it)
            e.printStackTrace();
        }
        return false;
    }


    public void addStudent(ActionEvent actionEvent) {//agiunge nuovo studente

    }
}
