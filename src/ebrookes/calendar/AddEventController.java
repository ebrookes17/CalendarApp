// Author: ebrookes

package ebrookes.calendar;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AddEventController implements Initializable {

    static Stage window;

    static Object[] toReturn;

    @FXML
    TextField eventNameInput, eventStartInput, eventEndInput;
    @FXML
    ChoiceBox<String> colorSelection;
    @FXML
    Rectangle colorDisplay;
    @FXML
    ChoiceBox<String> startAmPm, endAmPm;
    @FXML
    Button cancelButton, addEventButton;

    static SimpleBooleanProperty check;

    @Override
    public void initialize(URL location, ResourceBundle resources)  {

        check = new SimpleBooleanProperty(false);

        eventNameInput.setPromptText("Doctor's Appointment");
        eventStartInput.setPromptText("8:00");
        eventEndInput.setPromptText("10:00");

        startAmPm.getItems().addAll("AM", "PM");
        startAmPm.setValue("AM");

        endAmPm.getItems().addAll("AM", "PM");
        endAmPm.setValue("AM");

        // TODO: regex for time format: /^(0?[1-9]|1[0-2]):[0-5][0-9]$/

        initColorChoices();

        colorSelection.getItems().addAll(COLOR_CHOICES.keySet());
        colorSelection.setValue("Blue");
        colorDisplay.setFill(Paint.valueOf(COLOR_CHOICES.get("Blue")));

        colorSelection.setOnAction(e -> {
            String rgbaVal = COLOR_CHOICES.get(colorSelection.getValue());
            colorDisplay.setFill(Paint.valueOf(rgbaVal));
        });

        cancelButton.setOnAction(e -> {
            window = (Stage)((Node) e.getSource()).getScene().getWindow();
            window.close();
        });
        addEventButton.setOnAction(e -> {
            toReturn = new Object[7];
            toReturn[0] = eventNameInput.getText();
            toReturn[1] = eventStartInput.getText();
            toReturn[2] = startAmPm.getValue();
            toReturn[3] = eventEndInput.getText();
            toReturn[4] = endAmPm.getValue();
            toReturn[5] = COLOR_CHOICES.get(colorSelection.getValue());

            check.setValue(true);

            window = (Stage)((Node) e.getSource()).getScene().getWindow();
            window.close();
        });

    }
    public static Object[] getEventDetails(){

        return toReturn;
    }
    public static SimpleBooleanProperty getCheck(){

        return check;
    }
    public static void setCheck(boolean b){

        check.setValue(b);
    }
    private static HashMap<String, String> COLOR_CHOICES;

    private void initColorChoices (){
        COLOR_CHOICES = new HashMap<>();
        COLOR_CHOICES.put("Blue", "rgba(0, 66, 255, 0.5)");
        COLOR_CHOICES.put("Red", "rgba(255, 0, 0, 0.5)");
        COLOR_CHOICES.put("Green", "rgba(3, 255, 0, 0.5)");
        COLOR_CHOICES.put("Yellow", "rgba(255, 226, 0, 0.5)");
        COLOR_CHOICES.put("Pink", "rgba(247, 0, 255, 0.5)");
        COLOR_CHOICES.put("Teal", "rgba(0, 255, 231, 0.5)");
        COLOR_CHOICES.put("Orange", "rgba(255, 82, 0, 0.5)");
        COLOR_CHOICES.put("Purple", "rgba(109, 0, 255, 0.5)");

    }

}
