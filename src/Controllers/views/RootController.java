package Controllers.views;

import Controllers.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.awt.*;

public class RootController {
    @FXML
    private MenuItem about;

    private MainApp mainApp;

    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
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
}
