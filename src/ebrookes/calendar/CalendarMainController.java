// Author: ebrookes

package ebrookes.calendar;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Month;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.*;

public class CalendarMainController implements Initializable {

    private AddEventController addEventController;
    private SaveCalendarController saveCalendarController;
    public static Stage stage;

    // Buttons
    @FXML
    Button prevMonthButton, nextMonthButton, prevYearButton, nextYearButton, saveButton, loadButton,
            addEventButton, deleteEventButton;

    // Labels
    @FXML
    Label viewingMonthLabel, viewingYearLabel, addEventLabel;


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

    @FXML
    CheckMenuItem darkModeToggle;

    Year viewingYear;
    Month viewingMonth;
    int today;
    Month todayMonth;
    Year todayYear;
    int eventIndex;
    List<Object[]> customEvents;

    FileChooser fileChooser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        darkModeToggle.setOnAction(e -> {
            Scene currentScene = nextMonthButton.getScene();

                if (darkModeToggle.isSelected()) {
                    currentScene.getStylesheets().removeAll("CalendarMainThemeLight.css");
                    currentScene.getStylesheets().add("CalendarMainThemeDark.css");
                }
                else {
                    currentScene.getStylesheets().removeAll("CalendarMainThemeDark.css");
                    currentScene.getStylesheets().add("CalendarMainThemeLight.css");

                }
        });

        customEvents = new ArrayList<>();

        fileChooser = new FileChooser();
        fileChooser.setTitle("Load Calendar");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Calendar Files (*.sav)", "*.sav"));

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
        assignPrevMonthButtonFunc(calendar, dayLabels, holidayLabels, daySlots);

        // Assign next month button functionality
        assignNextMonthButtonFunc(calendar, dayLabels, holidayLabels, daySlots);

        // Assign previous year button functionality
        assignPrevYearButtonFunc(calendar, dayLabels, holidayLabels, daySlots);

        // Assign next year button functionality
        assignNextYearButtonFunc(calendar, dayLabels, holidayLabels, daySlots);

        BooleanProperty isEventReadyToCreate = new SimpleBooleanProperty(false);

        addEventButton.setOnAction(e -> {
            stage = (Stage)((Node) e.getSource()).getScene().getWindow();
            showTooltip(stage, addEventButton, "Click on a calendar day to continue");

            for (int i = 0; i < daySlots.length; i++) {
                int finalI = i;

                daySlots[finalI].setCursor(Cursor.HAND);

                daySlots[i].setOnMouseClicked(e2 -> {
                    eventIndex = finalI;

                    System.out.println(daySlots[finalI] + "clicked");

                    addEventController = openAddEvent();

                    if (addEventController.getEventDetails() != null) {
                        if (addEventController.getCheck().getValue())
                            isEventReadyToCreate.setValue(true);
                    }
                    isEventReadyToCreate.setValue(false);

                    clearHolidays(holidayLabels);
                    addHolidays(dayLabels, holidayLabels);

                    for (int j = 0; j < daySlots.length; j++) {
                        daySlots[j].setCursor(Cursor.DEFAULT);
                        daySlots[j].setOnMouseClicked(e3 -> {
                        });
                    }
                });
            }
        });

        isEventReadyToCreate.addListener((observable, oldvalue, newvalue) -> {
            if (newvalue) {
                createNewEvent(addEventController.getEventDetails(), daySlots, dayLabels);
                addEventController.setCheck(false);
            }
        });

        addHolidays(dayLabels, holidayLabels);
        addCustomEvents(daySlots, dayLabels);

        saveButton.setOnAction(e -> {
            saveCalendarController = openSaveCalendar();
            Save.saveCustomEvents(customEvents, saveCalendarController.getFileName());
        });

        loadButton.setOnAction(e -> {

            stage = (Stage)((Node) e.getSource()).getScene().getWindow();
            fileChooser.setInitialDirectory(new File (System.getProperty("user.dir") + "\\Saved Calendars"));
            File file = fileChooser.showOpenDialog(stage);

            if (file != null) {
                Save.loadCustomEvents(file.getPath());
                List<Object[]> temp = new ArrayList<>(Save.getLoadedObjectsList());

                clearCustomEventsFromCalendar(daySlots);

                customEvents.clear();

                customEvents.addAll(temp);
                generateCustomEventsForMonth(daySlots, dayLabels);
            }
        });

        highlightToday(calendar, daySlots, dayLabels);
    }
    private void getCurrentDate(Calendar calendar) {
        today = calendar.getCurrentDay();
        todayMonth = calendar.getCurrentMonth();
        todayYear = calendar.getCurrentYear();
    }

    // TODO: ensure method doesn't add today twice
    private void highlightToday(Calendar calendar, VBox[] daySlots, Label[] dayLabels) {
        getCurrentDate(calendar);

        for (int i=0; i < daySlots.length; i++) {
            if (daySlots[i].getId().equals("today")) {
                daySlots[i].setId("");
                break;
            }

            if (viewingYear.toString().equals(calendar.getCurrentYear().toString())
                    && viewingMonth == calendar.getCurrentMonth()
                    && dayLabels[i].getText().equals(String.valueOf(today))) {
                daySlots[i].setId("today");
                break;
            }
        }
    }

    private void displayViewingYearAndMonth(Calendar calendar) {
        viewingYear = calendar.getCurrentYear();
        viewingMonth = calendar.getCurrentMonth();

        viewingMonthLabel.setText(viewingMonth.getDisplayName(TextStyle.FULL, Locale.CANADA));
        viewingYearLabel.setText(" " + viewingYear);
    }

    private void assignPrevMonthButtonFunc(Calendar calendar, Label[] dayLabels, Label[] holidayLabels, VBox[] daySlots) {
        prevMonthButton.setOnAction(e -> {
            viewingMonth = viewingMonth.minus(1);
            if (viewingMonth == Month.DECEMBER)
                viewingYear = viewingYear.minusYears(1);
            calendar.genDayLabels(dayLabels, viewingYear, viewingMonth);
            viewingMonthLabel.setText(viewingMonth.getDisplayName(TextStyle.FULL, Locale.CANADA));
            viewingYearLabel.setText(" " + viewingYear);
            System.out.println("Prev month button clicked");

            generateHolidaysForMonth(dayLabels, holidayLabels);

            generateCustomEventsForMonth(daySlots, dayLabels);

            highlightToday(calendar, daySlots, dayLabels);
        });
    }

    private void assignNextMonthButtonFunc(Calendar calendar, Label[] dayLabels, Label[] holidayLabels, VBox[] daySlots) {
        nextMonthButton.setOnAction(e -> {
            viewingMonth = viewingMonth.plus(1);
            if (viewingMonth == Month.JANUARY)
                viewingYear = viewingYear.plusYears(1);
            calendar.genDayLabels(dayLabels, viewingYear, viewingMonth);
            viewingMonthLabel.setText(viewingMonth.getDisplayName(TextStyle.FULL, Locale.CANADA));
            viewingYearLabel.setText(" " + viewingYear);
            System.out.println("Next month button clicked");

            generateHolidaysForMonth(dayLabels, holidayLabels);

            generateCustomEventsForMonth(daySlots, dayLabels);

            highlightToday(calendar, daySlots, dayLabels);
        });
    }

    private void assignPrevYearButtonFunc(Calendar calendar, Label[] dayLabels, Label[] holidayLabels, VBox[] daySlots) {
        prevYearButton.setOnAction(e -> {
            viewingYear = viewingYear.minusYears(1);
            calendar.genDayLabels(dayLabels, viewingYear, viewingMonth);
            //viewingMonthLabel.setText(viewingMonth.getDisplayName(TextStyle.FULL, Locale.CANADA));
            viewingYearLabel.setText(" " + viewingYear);
            System.out.println("Prev year button clicked");

            generateHolidaysForMonth(dayLabels, holidayLabels);

            generateCustomEventsForMonth(daySlots, dayLabels);

            highlightToday(calendar, daySlots, dayLabels);
        });
    }

    private void assignNextYearButtonFunc(Calendar calendar, Label[] dayLabels, Label[] holidayLabels, VBox[] daySlots) {
        nextYearButton.setOnAction(e -> {
            viewingYear = viewingYear.plusYears(1);
            calendar.genDayLabels(dayLabels, viewingYear, viewingMonth);
            //viewingMonthLabel.setText(viewingMonth.getDisplayName(TextStyle.FULL, Locale.CANADA));
            viewingYearLabel.setText(" " + viewingYear);
            System.out.println("Next year button clicked");

            generateHolidaysForMonth(dayLabels, holidayLabels);

            generateCustomEventsForMonth(daySlots, dayLabels);

            highlightToday(calendar, daySlots, dayLabels);
        });
    }

    private void generateHolidaysForMonth(Label[] dayLabels, Label[] holidayLabels) {

        clearHolidays(holidayLabels);
        addHolidays(dayLabels, holidayLabels);
    }

    // TODO: add rest of holidays
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
    private AddEventController openAddEvent() {
        Stage stage = new Stage();
        Parent root = null;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddEvent.fxml"));

        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root, 540, 300);
        stage.setScene(scene);

        scene.getStylesheets().add("AddEventTheme.css");

        stage.setTitle("Add Event");

        // block interaction with other windows
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        return loader.getController();
    }

    private SaveCalendarController openSaveCalendar() {
        Stage stage = new Stage();
        Parent root = null;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("SaveCalendar.fxml"));

        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root, 400, 200);
        stage.setScene(scene);

        //scene.getStylesheets().add("");

        stage.setTitle("Save Calendar");

        // block interaction with other windows
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        return loader.getController();
    }

    private void createNewEvent(Object[] eventDetails, VBox[] daySlots, Label[] dayLabels) {

        HBox hBox = new HBox();

        Label nameLabel = new Label(eventDetails[0].toString() + " ");
        Label timeLabel = new Label(" " + eventDetails[1] + eventDetails [2] + " - " +
                eventDetails[3] + eventDetails [4]);

        nameLabel.setId("name-label");
        timeLabel.setId("time-label");
        hBox.setId("event-label");

        nameLabel.setTextAlignment(TextAlignment.RIGHT);
        Region region = new Region();

        hBox.setHgrow(region, Priority.ALWAYS);

        hBox.setStyle("-fx-background-color:" + eventDetails[5]);

        hBox.getChildren().addAll(timeLabel, region, nameLabel);
        hBox.setPadding(new Insets(1));
        hBox.setCursor(Cursor.HAND);

        Object[] objects = new Object[7];

        objects[0] = viewingYear;
        objects[1] = viewingMonth;
        objects[2] = dayLabels[eventIndex].getText();
        objects[3] = eventIndex;
        objects[4] = hBox;
        objects[5] = dayLabels[eventIndex].isDisabled();
        objects[6] = nameLabel.getText() + "/" + timeLabel.getText() + "/" + hBox.getStyle();

        daySlots[eventIndex].getChildren().add(hBox);

        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItem = new MenuItem("Delete Event");
        contextMenu.getItems().add(menuItem);

        hBox.setOnMouseClicked(e -> {

            contextMenu.show(daySlots[eventIndex], Side.RIGHT, -100, 90);

            menuItem.setOnAction(ev -> {
                deleteEvent(hBox, daySlots, eventIndex);
            });
        });

        customEvents.add(objects);

    }
    //TODO: fix delete event method
    private void deleteEvent(HBox hBox, VBox[] daySlots, int eventIndex) {
        daySlots[eventIndex].getChildren().remove(hBox);
    }

    private void addCustomEvents(VBox[] daySlots, Label[] dayLabels) {

        for (int i = 0; i < customEvents.size(); i++) {

            // Add event normally
            if (viewingYear.toString().equals(customEvents.get(i)[0].toString()) && viewingMonth == customEvents.get(i)[1]) {
                daySlots[(int) customEvents.get(i)[3]].getChildren().add((HBox)customEvents.get(i)[4]);
            }

            //if (viewingYear.toString().equals(customEvents.get(i)[0].toString()) && Integer.parseInt((String)customEvents.get(i)[2]) < 15 && viewingMonth.minus(1) == customEvents.get(i)[1]) {

            if ((boolean) customEvents.get(i)[5]) {

                // If day label is day in next month
                if (viewingYear.toString().equals(customEvents.get(i)[0].toString()) && Integer.parseInt((String) customEvents.get(i)[2]) < 15 && viewingMonth.minus(1) == customEvents.get(i)[1]) {

                    for (int j = 0; j < 15; j++) {
                        if (dayLabels[j].getText().equals((String) customEvents.get(i)[2]))
                            daySlots[j].getChildren().add((HBox) customEvents.get(i)[4]);
                    }

                }
                // Else if day label is day in previous month
                else if (viewingYear.toString().equals(customEvents.get(i)[0].toString()) && Integer.parseInt((String) customEvents.get(i)[2]) > 15 && viewingMonth.plus(1) == customEvents.get(i)[1]) {

                    for (int j = dayLabels.length - 15; j < dayLabels.length; j++) {
                        if (dayLabels[j].getText().equals((String) customEvents.get(i)[2]))
                            daySlots[j].getChildren().add((HBox) customEvents.get(i)[4]);
                    }

                }
            }
            //}
            /* TODO: DEALING WITH EVENTS ADDED TO DAYS OUTSIDE OF CURRENT MONTH
            if (dayLabels[i].isDisabled()) {

                // If day label is day in next month
                if (Integer.parseInt(dayLabels[i].getText()) < 15 && viewingMonth.minus(1) == customEvents.get(i)[1]) {



                }
                // If day label is day in previous month
                else if (Integer.parseInt(dayLabels[eventIndex].getText()) > 15) {

                }
            }

             */
        }
    }

    private void clearCustomEventsFromCalendar(VBox[] daySlots) {

        for (int i = 0; i < daySlots.length; i++) {

            while (daySlots[i].getChildren().contains(daySlots[i].lookup("#event-label")))
                daySlots[i].getChildren().remove(daySlots[i].lookup("#event-label"));
        }
    }

    private void generateCustomEventsForMonth(VBox[] dayslots, Label[] dayLabels) {

        clearCustomEventsFromCalendar(dayslots);
        addCustomEvents(dayslots, dayLabels);
    }
    public static void showTooltip(Stage owner, Control control, String tooltipText)
    {
        Point2D p = control.localToScene(0.0, 0.0);

        final Tooltip customTooltip = new Tooltip();
        customTooltip.setId("tool-tip");
        customTooltip.setText(tooltipText);

        control.setTooltip(customTooltip);
        customTooltip.setAutoHide(true);

        customTooltip.show(owner,
                p.getX() + control.getScene().getX() + control.getScene().getWindow().getX() - 50,
                p.getY() + control.getScene().getY() + control.getScene().getWindow().getY());

    }

}
