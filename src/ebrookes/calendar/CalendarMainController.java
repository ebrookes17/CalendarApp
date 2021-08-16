package ebrookes.calendar;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.Month;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;

public class CalendarMainController implements Initializable {

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

    // Labels for days within day slots
    @FXML
    Label d00, d01, d02, d03, d04, d05, d06, d10, d11, d12, d13, d14, d15, d16, d20, d21, d22, d23, d24, d25, d26,
            d30, d31, d32, d33, d34, d35, d36, d40, d41, d42, d43, d44, d45, d46, d50, d51, d52, d53, d54, d55, d56;

    // VBoxes for day slots
    @FXML
    VBox v00, v01, v02, v03, v04, v05, v06, v10, v11, v12, v13, v14, v15, v16, v20, v21, v22, v23, v24, v25, v26,
            v30, v31, v32, v33, v34, v35, v36, v40, v41, v42, v43, v44, v45, v46, v50, v51, v52, v53, v54, v55, v56;

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

        VBox[] daySlots = {
                v00, v01, v02, v03, v04, v05, v06, v10, v11, v12, v13, v14, v15, v16, v20, v21, v22, v23, v24, v25, v26,
                v30, v31, v32, v33, v34, v35, v36, v40, v41, v42, v43, v44, v45, v46, v50, v51, v52, v53, v54, v55, v56
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

        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItem1 = new MenuItem("Add Event");
        contextMenu.getItems().addAll(menuItem1);

        for (int i = 0; i < daySlots.length; i++) {
            int finalI = i;
            daySlots[i].setOnMouseClicked(e -> {
                System.out.println(daySlots[finalI] + "clicked");

                contextMenu.show(daySlots[finalI], Side.RIGHT, -100, 60);

                menuItem1.setOnAction(ev -> openAddEvent());
        });
        }
        //nextMonthButton.setOnMouseEntered(e -> nextMonthButton.setStyle("-fx-background-color: yellow;"));
    }
    private void openAddEvent() {
        Stage window = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("AddEvent.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        window.setScene(new Scene(root, 540, 300));
        window.setTitle("Add Event");

        // block interaction with other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.showAndWait();

    }

}
