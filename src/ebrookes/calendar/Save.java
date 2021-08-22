package ebrookes.calendar;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.TextAlignment;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Save {

    public static List<Object[]> loadedObjectsList;
    public static boolean loadedObjectsListReturned;

    public static void saveCustomEvents(List<Object[]> objects, String fileName) {

        List<Object[]> objectsToSaveList = objects;

        for (int i=0; i < objectsToSaveList.size(); i ++)
            objectsToSaveList.get(i)[4] = null;

        try{
            FileOutputStream saveFile = new FileOutputStream(System.getProperty("user.dir") + "\\Saves\\" + fileName + ".sav");

            // Create an ObjectOutputStream to put objects into save file.
            ObjectOutputStream save = new ObjectOutputStream(saveFile);

            // Now we do the save.
            save.writeObject(objectsToSaveList);

            // Close the file.
            save.close(); // This also closes saveFile.
        }
        catch(Exception exc){
            exc.printStackTrace(); // If there was an error, print the info.
        }
        loadedObjectsListReturned = false;

    }
    public static void loadCustomEvents(String filePath) {

        // Create the data objects for us to restore.
        List<Object[]> objectsToLoadList = new ArrayList<>();

        // Wrap all in a try/catch block to trap I/O errors.
        try{
        // Open file to read from, named SavedObj.sav.
            FileInputStream saveFile = new FileInputStream(filePath);

        // Create an ObjectInputStream to get objects from save file.
            ObjectInputStream save = new ObjectInputStream(saveFile);

        // Now we do the restore.
        // readObject() returns a generic Object, we cast those back
        // into their original class type.
        // For primitive types, use the corresponding reference class.
            objectsToLoadList = (ArrayList<Object[]>)save.readObject();

        // Close the file.
            save.close(); // This also closes saveFile.
        }
        catch(Exception exc){
            exc.printStackTrace(); // If there was an error, print the info.
        }

        // Print the values, to see that they've been recovered.
        for (int i=0; i < objectsToLoadList.size(); i++) {
            for (int j=0; j < objectsToLoadList.get(i).length; j++)
                System.out.println(objectsToLoadList.get(i)[j]);
        }

        for (int i=0; i < objectsToLoadList.size(); i++) {
            HBox hBox = new HBox();

            String hboxInfo = objectsToLoadList.get(i)[6].toString();

            int firstDel = hboxInfo.indexOf("/");
            int secondDel = hboxInfo.lastIndexOf("/");

            String nameLabelText = hboxInfo.substring(0, firstDel);
            String timeLabelText = hboxInfo.substring(firstDel + 1, secondDel);
            String hboxStyle = hboxInfo.substring(secondDel + 1);

            Label nameLabel = new Label(nameLabelText);
            Label timeLabel = new Label(timeLabelText);

            nameLabel.setId("name-label");
            timeLabel.setId("time-label");
            hBox.setId("event-label");

            nameLabel.setTextAlignment(TextAlignment.RIGHT);
            Region region = new Region();

            hBox.setHgrow(region, Priority.ALWAYS);

            hBox.setStyle(hboxStyle);

            hBox.getChildren().addAll(timeLabel, region, nameLabel);
            hBox.setPadding(new Insets(1));

            objectsToLoadList.get(i)[4] = hBox;

        }
        loadedObjectsList = objectsToLoadList;

    }
    public static List<Object[]> getLoadedObjectsList() {

        loadedObjectsListReturned = true;
        return loadedObjectsList;
    }
    public static boolean isLoadedObjectsListReturned() {
        return loadedObjectsListReturned;
    }
}



