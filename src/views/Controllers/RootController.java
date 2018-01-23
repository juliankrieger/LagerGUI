package views.Controllers;

import MainApp.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckMenuItem;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;
import java.util.prefs.Preferences;

public class RootController {
    @FXML
    private MenuItem about;

    private MainApp mainApp;

    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }

    @FXML
    public CheckMenuItem debugItem;

    /**
     * Creates an empty address book.
     */
    @FXML
    private void handleNew() {
        mainApp.getLager().getArtikelData().clear();
        mainApp.setArtikelFilePath(null);
    }

    /**
     * Opens a FileChooser to let the user select an address book to load.
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show open file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadArtikelDataFromFile(file);
        }
    }

    /**
     * Saves the file to the person file that is currently open. If there is no
     * open file, the "save as" dialog is shown.
     */
    @FXML
    private void handleSave() {
        File personFile = mainApp.getArtikelFilePath();
        if (personFile != null) {
            mainApp.saveArtikelDataToFile(personFile);
        } else {
            handleSaveAs();
        }
    }

    /**
     * Opens a FileChooser to let the user select a file to save to.
     */
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.saveArtikelDataToFile(file);
        }
    }

    @FXML
    private void handleAbout(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("About");
        alert.setContentText("Created by Julian Krieger under the MIT license.");
        alert.setHeaderText("About this program:");
        alert.showAndWait();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }

    @FXML
    private void switchEnableLoadFileFromLastOpen(){
        mainApp.switchEnableLoadFileFromLastUse();
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);

        prefs.remove("EnableLoadFileFromLastUse");
        prefs.putBoolean("EnableLoadFileFromLastUse", mainApp.getEnableLoadFileFromLastUse());
    }
}
