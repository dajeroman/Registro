package com.example.registro;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeStudenti {

    @FXML
    private Label data;

    @FXML
    public void initialize() {
        LocalDate oggi = LocalDate.now();
        int giorno = oggi.getDayOfMonth();
        String giornoSettimana = oggi.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        int mese = oggi.getMonthValue();
        String nomeMese = oggi.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
        int anno = oggi.getYear();

        String formattedDate = String.format("%d %s %s %d", giorno, giornoSettimana, nomeMese, anno);
        data.setText("Data: " + formattedDate);
    }
}
