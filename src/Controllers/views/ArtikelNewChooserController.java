package Controllers.views;

import Controllers.structs.Artikel;
import Controllers.structs.Buch;
import Controllers.structs.CD;
import Controllers.structs.DVD;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class ArtikelNewChooserController {

    @FXML
    private MenuButton menuButton;

    private Stage dialogStage;

    private boolean okClicked = false;

    private Artikel art;

    private MenuItem currentMenuItem;



    public ArtikelNewChooserController(){

    }

    @FXML
    private void initialize() {
        this.menuButton.getItems().removeAll(menuButton.getItems());
        this.menuButton.getItems().addAll(new MenuItem("Artikel"), new MenuItem("CD"),
                new MenuItem("DVD"), new MenuItem("Buch"));
        this.menuButton.setText(this.menuButton.getItems().get(0).getText());

        for(MenuItem mi : this.menuButton.getItems()){
            mi.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    menuButton.setText(mi.getText());
                    currentMenuItem = mi;

                }
            });
        }
        this.currentMenuItem = this.menuButton.getItems().get(0);
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Artikel getArt(){
        return this.art;
    }


    @FXML
    private void handleCancel(){
        this.dialogStage.close();
    }

    public boolean isOkClicked(){
        return this.okClicked;
    }

    @FXML
    private void handleOK(){
        switch (currentMenuItem.getText()){
            case "Artikel": this.art = new Artikel();

                            break;
            case "CD" : this.art = new CD();
                            break;
            case "DVD" : this.art = new DVD();
                            break;
            case "Buch" : this.art = new Buch();

            default :
        }

        this.okClicked = true;
        this.dialogStage.close();
    }


}
