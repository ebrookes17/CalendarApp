package ebrookes.calendar;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.Month;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private Scene scene;

    // Buttons
    @FXML
    Button prevMonthButton;
    @FXML
    Button nextMonthButton;

    // Labels
    @FXML
    Label viewingMonthLabel;
    @FXML
    Label viewingYearLabel;
    @FXML
    Label d00, d01, d02, d03, d04, d05, d06, d10, d11, d12, d13, d14, d15, d16, d20, d21, d22, d23, d24, d25, d26,
            d30, d31, d32, d33, d34, d35, d36, d40, d41, d42, d43, d44, d45, d46, d50, d51, d52, d53, d54, d55, d56;

    Year viewingYear;
    Month viewingMonth;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        viewingYear = Main.calendar.getCurrentYear();
        viewingMonth = Main.calendar.getCurrentMonth();

        viewingMonthLabel.setText(viewingMonth.getDisplayName(TextStyle.FULL, Locale.CANADA));
        viewingYearLabel.setText(" " + viewingYear);

        Label[] dayLabels = {
                d00, d01, d02, d03, d04, d05, d06, d10, d11, d12, d13, d14, d15, d16, d20, d21, d22, d23, d24, d25, d26,
                d30, d31, d32, d33, d34, d35, d36, d40, d41, d42, d43, d44, d45, d46, d50, d51, d52, d53, d54, d55, d56
        };
        Main.calendar.genDayLabels(dayLabels, viewingYear, viewingMonth);

        prevMonthButton.setOnAction(e -> {
            viewingMonth = viewingMonth.minus(1);
            if (viewingMonth == Month.DECEMBER)
                viewingYear = viewingYear.minusYears(1);
            Main.calendar.genDayLabels(dayLabels, viewingYear, viewingMonth);
            viewingMonthLabel.setText(viewingMonth.getDisplayName(TextStyle.FULL, Locale.CANADA));
            viewingYearLabel.setText(" " + viewingYear);
            System.out.println("Prev month button clicked");
        });

        //prevMonthButton.setOnMouseEntered(e -> prevMonthButton.setStyle("-fx-background-color: yellow;"));

        nextMonthButton.setOnAction(e -> {
            viewingMonth = viewingMonth.plus(1);
            if (viewingMonth == Month.JANUARY)
                viewingYear = viewingYear.plusYears(1);
            Main.calendar.genDayLabels(dayLabels, viewingYear, viewingMonth);
            viewingMonthLabel.setText(viewingMonth.getDisplayName(TextStyle.FULL, Locale.CANADA));
            viewingYearLabel.setText(" " + viewingYear);
            System.out.println("Next month button clicked");
        });

        //nextMonthButton.setOnMouseEntered(e -> nextMonthButton.setStyle("-fx-background-color: yellow;"));

    }

}
