package com.example.registro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

import static java.lang.Integer.parseInt;

public class Home {
    //variables
    @FXML
    private Label errorMessage;

    @FXML
    private TextField codiceIstituto;

    @FXML
    private TextField nomeStudente;

    @FXML
    private TextField cognomeStudente;

    @FXML
    private AnchorPane homeScene;


    // JDBC database URL, username, and password of MySQL server
    private static final String URL = "jdbc:mysql://localhost:3306/registro";
    private static final String USER = "root";
    private static final String PASSWORD = "795123";

    @FXML
    public void accedi(ActionEvent actionEvent) {
        // Clear previous error messages
        errorMessage.setText("");

        try {
            // Get data from the form
            int id;
            try {
                id = Integer.parseInt(codiceIstituto.getText());
            } catch (NumberFormatException e) {
                errorMessage.setText("Invalid ID. Please enter a valid numeric value.");
                return; // Stop further processing
            }

            // Check for non-empty and non-numeric values for nome and cognome
            String nome = nomeStudente.getText().trim();
            String cognome = cognomeStudente.getText().trim();

            if (nome.isEmpty() || cognome.isEmpty() || isNumeric(nome) || isNumeric(cognome)) {
                errorMessage.setText("Please enter valid values for both name and surname.");
                return; // Stop further processing
            }

            // Check if the student already exists in the database
            if (studentExists(id, nome, cognome)) {
                // Allow access to the application (you can add your logic here)
                errorMessage.setText("Studente Trovato");
            } else {
                // Deny access to the application (you can add your logic here)
                errorMessage.setText("Studente non trovato, riguardi id, nome o cognome");
            }
        } catch (Exception e) {
            errorMessage.setText("An error occurred. Please try again later.");
            e.printStackTrace(); // Log the exception
        }
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    private boolean studentExists(int id, String nome, String cognome) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM registro WHERE id = ? AND nome = ? AND cognome = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
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



    @FXML
    void switchToSignUp(ActionEvent event) throws IOException {

        new SceneController(homeScene, "SignUp.fxml");

    }

    @FXML
    void initialize() {
        // Initialization code (if any)
    }

}