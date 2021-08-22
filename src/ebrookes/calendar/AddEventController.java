// Author: ebrookes

package ebrookes.calendar;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AddEventController implements Initializable {

    Stage stage;

    Object[] eventDetails;

    @FXML
    TextField eventNameInput;
    @FXML
    ChoiceBox<String> colorSelection;
    @FXML
    Rectangle colorDisplay;
    @FXML
    ChoiceBox<String> startHour, startMinute, endHour, endMinute, startAmPm, endAmPm;
    @FXML
    Button cancelButton, addEventButton;

    SimpleBooleanProperty check = new SimpleBooleanProperty(false);

    @Override
    public void initialize(URL location, ResourceBundle resources)  {

        check = new SimpleBooleanProperty(false);

        eventNameInput.setPromptText("Doctor's Appointment");

        startHour.getItems().addAll(Calendar.getEventHours());
        startHour.setValue("8");

        startMinute.getItems().addAll(Calendar.getEventMinutes());
        startMinute.setValue("00");

        // Workaround to force proper location of startMinute's context menu. Otherwise, the first time it is clicked
        // it appears in wrong location, but works the second time.
        startMinute.setOnMouseClicked(e -> {
            startMinute.hide();
            startMinute.show();
        });

        startMinute.setId("time-choice");
        startHour.setId("time-choice");

        endHour.getItems().addAll(Calendar.getEventHours());
        endHour.setValue("10");

        endMinute.getItems().addAll(Calendar.getEventMinutes());
        endMinute.setValue("00");

        // Workaround used again
        endMinute.setOnMouseClicked(e -> {
            endMinute.hide();
            endMinute.show();
        });

        endMinute.setId("time-choice");
        endHour.setId("time-choice");

        startAmPm.getItems().addAll("AM", "PM");
        startAmPm.setValue("AM");

        endAmPm.getItems().addAll("AM", "PM");
        endAmPm.setValue("AM");

        initColorChoices();

        colorSelection.getItems().addAll(COLOR_CHOICES.keySet());
        colorSelection.setValue("Aqua");
        colorDisplay.setFill(Paint.valueOf(COLOR_CHOICES.get("Aqua")));

        colorSelection.setOnAction(e -> {
            String rgbaVal = COLOR_CHOICES.get(colorSelection.getValue());
            colorDisplay.setFill(Paint.valueOf(rgbaVal));
        });

        cancelButton.setOnAction(e -> {
            stage = (Stage)((Node) e.getSource()).getScene().getWindow();
            stage.close();
        });

        addEventButton.setOnAction(e -> {
            eventDetails = new Object[7];
            eventDetails[0] = eventNameInput.getText();
            eventDetails[1] = startHour.getValue();

            if (!startMinute.getValue().equals("00"))
                eventDetails[1] += ":" + startMinute.getValue();

            eventDetails[2] = startAmPm.getValue();
            eventDetails[3] = endHour.getValue();

            if (!endMinute.getValue().equals("00"))
                eventDetails[3] += ":" + endMinute.getValue();

            eventDetails[4] = endAmPm.getValue();
            eventDetails[5] = COLOR_CHOICES.get(colorSelection.getValue());

            check.setValue(true);

            stage = (Stage)((Node) e.getSource()).getScene().getWindow();
            stage.close();
        });

        addEventButton.setDefaultButton(true);

    }
    public Object[] getEventDetails(){

        return eventDetails;
    }
    public SimpleBooleanProperty getCheck(){

        return check;
    }
    public void setCheck(boolean b){

        check.setValue(b);
    }
    private HashMap<String, String> COLOR_CHOICES;

    private void initColorChoices (){

        COLOR_CHOICES = new HashMap<>();

        COLOR_CHOICES.put("Aqua", "#3BAFDA");
        COLOR_CHOICES.put("Blue", "#1d65c4");
        COLOR_CHOICES.put("Green", "#8CC152");
        COLOR_CHOICES.put("Mint", "#37BC9B");
        COLOR_CHOICES.put("Orange", "#FE8714");
        COLOR_CHOICES.put("Pink", "#D770AD");
        COLOR_CHOICES.put("Red", "#DA4453");
        COLOR_CHOICES.put("Yellow", "#F6BB42");

    }

}
