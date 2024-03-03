package com.example.registro;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeStudenti {

    @FXML
    private Label data;

    @FXML
    public void initialize() {
        LocalDate oggi = LocalDate.now();
        int giorno = oggi.getDayOfMonth();
        int mese = oggi.getMonthValue();
        int anno = oggi.getYear();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        String formattedDate = oggi.format(formatter);
        data.setText("Data: " + formattedDate);
    }
}
