package views.Controllers;

import structs.Artikel;
import structs.Buch;
import structs.CD;
import structs.DVD;
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


    /**
     * The Artikel Type chooser Window
     * TODO change this to work with enums, not type checking
     */
    public ArtikelNewChooserController(){

    }

    /**
     * Initialize the Menu Button
     */
    @FXML
    private void initialize() {
        //Remove the JAVAFX menuItems that are, for some insane reason, already in a newly created menuButton
        this.menuButton.getItems().removeAll(menuButton.getItems());
        //Add all my menu points
        this.menuButton.getItems().addAll(new MenuItem("Artikel"), new MenuItem("CD"),
                new MenuItem("DVD"), new MenuItem("Buch"));
        //And set the first choice too the first MenuItemm in the getItems list
        this.menuButton.setText(this.menuButton.getItems().get(0).getText());

        //Add an EventHandler for every MenuItem in getItems
        for(MenuItem mi : this.menuButton.getItems()){
            mi.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    //Set the menuButtons display text to the current choice
                    menuButton.setText(mi.getText());
                    //Set the currentMenuItems attribute to the current choice ON ACTION EVENT
                    currentMenuItem = mi;

                }
            });
        }
        //Set the currentMenuItem default to the first MenuItem in the getItems list
        this.currentMenuItem = this.menuButton.getItems().get(0);

    }

    /**
     * set the dialogStage
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Get the Artikel
     * @return
     */
    public Artikel getArt(){
        return this.art;
    }


    /**
     * Handle the Cancel Action Event
     */
    @FXML
    private void handleCancel(){
        this.dialogStage.close();
    }

    /**
     * Return this.okClicked
     * @return
     */
    public boolean isOkClicked(){
        return this.okClicked;
    }

    /**
     * Handle the OK button onActionEvent
     * TODO change this to enums
     */
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
