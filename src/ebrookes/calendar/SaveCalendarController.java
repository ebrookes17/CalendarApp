package ebrookes.calendar;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class SaveCalendarController implements Initializable {

    Stage stage;

    String fileName;

    @FXML
    TextField fileNameInput;
    @FXML
    Label errorLabel;
    @FXML
    Button saveCalendarButton, cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cancelButton.setOnAction(e -> {
            stage = (Stage)((Node) e.getSource()).getScene().getWindow();
            stage.close();
        });

        saveCalendarButton.setOnAction(e -> {

            try {
                if (fileNameInput.getText().equals("") || fileNameInput.getText().contains(" ")) {
                    throw new FileNotFoundException("Invalid file name");

                } else {
                    fileName = fileNameInput.getText();
                    stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    stage.close();
                }

            }
            catch (FileNotFoundException exception) {
                errorLabel.setText(exception.getMessage());
            }
        });
    }

    public String getFileName() {
        return fileName;
    }

}
