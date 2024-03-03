package com.example.registro;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeStudenti {

    @FXML
    private Label numeroGiorno;
    @FXML
    private Label giorno;
    @FXML
    private Label meseAnno;

    private LocalDate currentDate;

    @FXML
    public void initialize() {
        currentDate = LocalDate.now();
        updateLabels();
    }

    private void updateLabels() {
        int giornoNumero = currentDate.getDayOfMonth();
        String giornoSettimana = currentDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        int mese = currentDate.getMonthValue();
        String nomeMese = currentDate.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
        int anno = currentDate.getYear();

        String numeroGiornostr = String.format("%d ", giornoNumero);
        numeroGiorno.setText(numeroGiornostr);
        String giornostr = String.format("%s ", giornoSettimana);
        giorno.setText(giornostr);
        String meseAnnostr = String.format("%s %d ", nomeMese, anno);
        meseAnno.setText(meseAnnostr);
    }

    @FXML
    void nextDay(ActionEvent event) {
        currentDate = currentDate.plusDays(1);
        updateLabels();
    }

    @FXML
    void prevDay(ActionEvent event) {
        currentDate = currentDate.minusDays(1);
        updateLabels();
    }
}
