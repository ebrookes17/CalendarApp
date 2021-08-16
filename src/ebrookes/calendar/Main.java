package ebrookes.calendar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.Month;
import java.time.Year;
import java.util.Scanner;

public class Main extends Application {

    public Stage window;
    public static Calendar calendar;

    @Override
    public void start(Stage primaryStage) throws Exception{

        window = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("CalendarMain.fxml"));
        window.setTitle("Calendar");
        window.setScene(new Scene(root, 1440, 1080));
        window.show();
    }


    public static void main(String[] args) {

        calendar = new Calendar();
        launch(args);
    }
}
