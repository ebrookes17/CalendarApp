// Author: ebrookes

package ebrookes.calendar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{

        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("CalendarMain.fxml"));
        stage.setTitle("Calendar");
        Scene scene = new Scene(root, 1920, 1080);
        scene.getStylesheets().add("CalendarMainTheme.css");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
    public static Stage getStage() {
        return stage;
    }
}
