// Author: ebrookes

package ebrookes.calendar;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
    static Stage stage;

    // Buttons
    @FXML
    Button prevMonthButton, nextMonthButton, prevYearButton, nextYearButton;

    // Labels
    @FXML
    Label viewingMonthLabel;
    @FXML
    Label viewingYearLabel;

    // Labels for days within day slots
    @FXML
    Label d00, d01, d02, d03, d04, d05, d06, d10, d11, d12, d13, d14, d15, d16, d20, d21, d22, d23, d24, d25, d26,
            d30, d31, d32, d33, d34, d35, d36, d40, d41, d42, d43, d44, d45, d46, d50, d51, d52, d53, d54, d55, d56;

    // Labels for holidays within day slots
    @FXML
    Label h00, h01, h02, h03, h04, h05, h06, h10, h11, h12, h13, h14, h15, h16, h20, h21, h22, h23, h24, h25, h26,
            h30, h31, h32, h33, h34, h35, h36, h40, h41, h42, h43, h44, h45, h46, h50, h51, h52, h53, h54, h55, h56;

    // VBoxes for day slots
    @FXML
    VBox v00, v01, v02, v03, v04, v05, v06, v10, v11, v12, v13, v14, v15, v16, v20, v21, v22, v23, v24, v25, v26,
            v30, v31, v32, v33, v34, v35, v36, v40, v41, v42, v43, v44, v45, v46, v50, v51, v52, v53, v54, v55, v56;

    Year viewingYear;
    Month viewingMonth;
    int eventIndex;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // stage = Main.getStage();

        // Populate dayLabels array
        Label[] dayLabels = {
                d00, d01, d02, d03, d04, d05, d06, d10, d11, d12, d13, d14, d15, d16, d20, d21, d22, d23, d24, d25, d26,
                d30, d31, d32, d33, d34, d35, d36, d40, d41, d42, d43, d44, d45, d46, d50, d51, d52, d53, d54, d55, d56
        };

        // Populate holidaysLabels array
        Label[] holidayLabels = {
                h00, h01, h02, h03, h04, h05, h06, h10, h11, h12, h13, h14, h15, h16, h20, h21, h22, h23, h24, h25, h26,
                h30, h31, h32, h33, h34, h35, h36, h40, h41, h42, h43, h44, h45, h46, h50, h51, h52, h53, h54, h55, h56
        };

        // Populate VBox daySlots array
        VBox[] daySlots = {
                v00, v01, v02, v03, v04, v05, v06, v10, v11, v12, v13, v14, v15, v16, v20, v21, v22, v23, v24, v25, v26,
                v30, v31, v32, v33, v34, v35, v36, v40, v41, v42, v43, v44, v45, v46, v50, v51, v52, v53, v54, v55, v56
        };

        // Create new calendar
        Calendar calendar = new Calendar();

        // Display viewing year and month
        displayViewingYearAndMonth(calendar);

        // Generate number labels for each day slot in viewing month
        calendar.genDayLabels(dayLabels, viewingYear, viewingMonth);

        // Assign previous month button functionality
        assignPrevMonthButtonFunc(calendar, dayLabels, holidayLabels);

        // Assign next month button functionality
        assignNextMonthButtonFunc(calendar, dayLabels, holidayLabels);

        // Assign previous year button functionality
        assignPrevYearButtonFunc(calendar, dayLabels, holidayLabels);

        // Assign next year button functionality
        assignNextYearButtonFunc(calendar, dayLabels, holidayLabels);


        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItem1 = new MenuItem("Add Event");
        contextMenu.getItems().addAll(menuItem1);


        BooleanProperty b = new SimpleBooleanProperty(false);

        b.addListener((observable, oldvalue, newvalue) -> {
            if (newvalue) {
                createNewEvent(AddEventController.getEventDetails(), daySlots);
                AddEventController.setCheck(false);
            }
        });

        for (int i = 0; i < daySlots.length; i++) {
            int finalI = i;
            daySlots[i].setOnMouseClicked(e -> {

                eventIndex = finalI;

                System.out.println(daySlots[finalI] + "clicked");

                contextMenu.show(daySlots[finalI], Side.RIGHT, -100, 60);

                menuItem1.setOnAction(ev -> openAddEvent());

                if (AddEventController.getEventDetails() != null) {
                    b.setValue(AddEventController.getCheck().getValue());
                }

                clearHolidays(holidayLabels);
                addHolidays(dayLabels, holidayLabels);
            });
        }

        //nextMonthButton.setOnMouseEntered(e -> nextMonthButton.setStyle("-fx-background-color: yellow;"));

        addHolidays(dayLabels, holidayLabels);
    }

    private void displayViewingYearAndMonth(Calendar calendar) {
        viewingYear = calendar.getCurrentYear();
        viewingMonth = calendar.getCurrentMonth();

        viewingMonthLabel.setText(viewingMonth.getDisplayName(TextStyle.FULL, Locale.CANADA));
        viewingYearLabel.setText(" " + viewingYear);
    }

    private void assignPrevMonthButtonFunc(Calendar calendar, Label[] dayLabels, Label[] holidayLabels) {
        prevMonthButton.setOnAction(e -> {
            viewingMonth = viewingMonth.minus(1);
            if (viewingMonth == Month.DECEMBER)
                viewingYear = viewingYear.minusYears(1);
            calendar.genDayLabels(dayLabels, viewingYear, viewingMonth);
            viewingMonthLabel.setText(viewingMonth.getDisplayName(TextStyle.FULL, Locale.CANADA));
            viewingYearLabel.setText(" " + viewingYear);
            System.out.println("Prev month button clicked");

            generateHolidaysForMonth(dayLabels, holidayLabels);
        });
    }

    private void assignNextMonthButtonFunc(Calendar calendar, Label[] dayLabels, Label[] holidayLabels) {
        nextMonthButton.setOnAction(e -> {
            viewingMonth = viewingMonth.plus(1);
            if (viewingMonth == Month.JANUARY)
                viewingYear = viewingYear.plusYears(1);
            calendar.genDayLabels(dayLabels, viewingYear, viewingMonth);
            viewingMonthLabel.setText(viewingMonth.getDisplayName(TextStyle.FULL, Locale.CANADA));
            viewingYearLabel.setText(" " + viewingYear);
            System.out.println("Next month button clicked");

            generateHolidaysForMonth(dayLabels, holidayLabels);
        });
    }

    private void assignPrevYearButtonFunc(Calendar calendar, Label[] dayLabels, Label[] holidayLabels) {
        prevYearButton.setOnAction(e -> {
            viewingYear = viewingYear.minusYears(1);
            calendar.genDayLabels(dayLabels, viewingYear, viewingMonth);
            //viewingMonthLabel.setText(viewingMonth.getDisplayName(TextStyle.FULL, Locale.CANADA));
            viewingYearLabel.setText(" " + viewingYear);
            System.out.println("Prev year button clicked");

            generateHolidaysForMonth(dayLabels, holidayLabels);
        });
    }

    private void assignNextYearButtonFunc(Calendar calendar, Label[] dayLabels, Label[] holidayLabels) {
        nextYearButton.setOnAction(e -> {
            viewingYear = viewingYear.plusYears(1);
            calendar.genDayLabels(dayLabels, viewingYear, viewingMonth);
            //viewingMonthLabel.setText(viewingMonth.getDisplayName(TextStyle.FULL, Locale.CANADA));
            viewingYearLabel.setText(" " + viewingYear);
            System.out.println("Next year button clicked");

            generateHolidaysForMonth(dayLabels, holidayLabels);
        });
    }

    private void generateHolidaysForMonth(Label[] dayLabels, Label[] holidayLabels) {
        clearHolidays(holidayLabels);
        addHolidays(dayLabels, holidayLabels);
    }

    private void addHolidays(Label[] dayLabels, Label[] holidayLabels) {

        for (int i = 0; i < dayLabels.length; i++) {

            if (viewingMonth == Month.JANUARY && dayLabels[i].getText().equals("1") && i < 15) {
                holidayLabels[i].setText("New Year's Day ");
                holidayLabels[i].getParent().setVisible(true);
            }
            else if (viewingMonth == Month.FEBRUARY && dayLabels[i].getText().equals("2") && i < 16) {
                holidayLabels[i].setText("Groundhog Day ");
                holidayLabels[i].getParent().setVisible(true);
            }
            else if (viewingMonth == Month.FEBRUARY && dayLabels[i].getText().equals("14") && i < 28) {
                holidayLabels[i].setText("Valentine's Day ");
                holidayLabels[i].getParent().setVisible(true);
            }
            else if (viewingMonth == Month.JULY && dayLabels[i].getText().equals("1") && i < 15) {
                holidayLabels[i].setText("Canada Day ");
                holidayLabels[i].getParent().setVisible(true);
            }
            else if (viewingMonth == Month.OCTOBER && dayLabels[i].getText().equals("31") && i > 17) {
                holidayLabels[i].setText("Halloween ");
                holidayLabels[i].getParent().setVisible(true);
            }
            else if (viewingMonth == Month.NOVEMBER && dayLabels[i].getText().equals("11") && i < 25) {
                holidayLabels[i].setText("Remembrance Day ");
                holidayLabels[i].getParent().setVisible(true);
            }
            else if (viewingMonth == Month.DECEMBER && dayLabels[i].getText().equals("24") && i > 10) {
                holidayLabels[i].setText("Christmas Eve ");
                holidayLabels[i].getParent().setVisible(true);
            }
            else if (viewingMonth == Month.DECEMBER && dayLabels[i].getText().equals("25") && i > 11) {
                holidayLabels[i].setText("Christmas Day ");
                holidayLabels[i].getParent().setVisible(true);
            }
            else if (viewingMonth == Month.DECEMBER && dayLabels[i].getText().equals("26") && i > 12) {
                holidayLabels[i].setText("Boxing Day ");
                holidayLabels[i].getParent().setVisible(true);
            }
            else if (viewingMonth == Month.DECEMBER && dayLabels[i].getText().equals("31") && i > 17) {
                holidayLabels[i].setText("New Year's Eve ");
                holidayLabels[i].getParent().setVisible(true);
            }
        }
    }

    private void clearHolidays(Label[] holidayLabels) {
        for (int i = 0; i < holidayLabels.length; i++) {
            if (!holidayLabels[i].getText().equals("")) {
                holidayLabels[i].setText("");
                holidayLabels[i].getParent().setVisible(false);

            }
        }

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

    private void createNewEvent(Object[] eventDetails, VBox[] daySlots) {
        Label label = new Label((String)eventDetails[0]);
        label.setId("event");
        daySlots[eventIndex].getChildren().add(label);
    }


}
