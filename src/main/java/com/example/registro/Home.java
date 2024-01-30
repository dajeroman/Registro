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


    // JDBC database URL, username, and password of MySQL server
    private static final String URL = "jdbc:mysql://localhost:3306/registro";
    private static final String USER = "root";
    private static final String PASSWORD = "795123";

    @FXML
    public void accedi(ActionEvent actionEvent) {
        // Get data from the form
        int id = parseInt(codiceIstituto.getText());
        String nome = nomeStudente.getText();
        String cognome = cognomeStudente.getText();

        // Check if the student already exists in the database
        if (studentExists(id, nome, cognome)) {
            // Allow access to the application (you can add your logic here)
            errorMessage.setText("Access granted!");
        } else {
            // Deny access to the application (you can add your logic here)
            errorMessage.setText("Access denied! Student not found.");
        }
    }

    private boolean studentExists(int id, String nome, String cognome) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM registro WHERE id = ? AND nome = ? AND cognome = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, nome);
                preparedStatement.setString(3, cognome);

                try (var resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next(); // If the result set has a next row, the student exists
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately (e.g., show an error message)
        }
        return false;
    }

    @FXML
    void initialize() {
        // Initialization code (if any)
    }
}