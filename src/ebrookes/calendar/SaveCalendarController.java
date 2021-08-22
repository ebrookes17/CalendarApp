package ebrookes.calendar;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SaveCalendarController implements Initializable {

    Stage stage;

    String fileName;

    @FXML
    TextField fileNameInput;

    @FXML
    Button saveCalendarButton, cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cancelButton.setOnAction(e -> {
            stage = (Stage)((Node) e.getSource()).getScene().getWindow();
            stage.close();
        });

        saveCalendarButton.setOnAction(e -> {
            if (!fileNameInput.getText().equals("")) {
                fileName = fileNameInput.getText();
                stage = (Stage)((Node) e.getSource()).getScene().getWindow();
                stage.close();
            }
            else
                System.out.println("Could not save: invalid text");
        });
    }

    public String getFileName() {
        return fileName;
    }

}
