package Controllers.views;

import Controllers.AppLogger;
import Controllers.MainApp;
import Controllers.structs.Artikel;
import Controllers.structs.Lager;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.math.BigInteger;
import java.util.logging.Handler;

public class ArtikelOverviewController {
    @FXML
    private TableView<Artikel> artikelTable;

    @FXML
    private TableColumn<Artikel, Integer> idColumn;

    @FXML
    private TableColumn<Artikel, String> nameColummn;

    @FXML
    private TableColumn<Artikel, String> kindColumn;

    @FXML
    private Label nameLabel;

    @FXML
    private Label idLabel;

    @FXML
    private Label stockLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label additionalLabel1;

    @FXML
    private Label additionalLabelFilling1;

    @FXML
    private Label additionalLabel2;

    @FXML
    private Label additionalLabelFilling2;

    @FXML
    private boolean areAdditionalButtonsDisabled = true;

    private MainApp mainApp;

    public ArtikelOverviewController(){

    }

    @FXML
    private void initialize(){
        //Populating Columns

        //Lambda cellData is an object of the Artikel Class

        this.idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        this.nameColummn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        this.kindColumn.setCellValueFactory(cellData -> cellData.getValue().kindProperty());



        //clear person details
        showArtikelDetails(null);

        artikelTable.getSelectionModel().selectedItemProperty().addListener((
                observable, oldValue, newValue) -> showArtikelDetails(newValue));


    }


    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;

        artikelTable.setItems(mainApp.getLager().getArtikelData());
    }

    //TODO configure for CD, DVD and Book classes
    public void showArtikelDetails(Artikel art){
        if(art != null){
            if(art.getClass().equals(Artikel.class)) {
                this.idLabel.setText(Integer.toString(art.getId()));
                this.nameLabel.setText(art.getName());
                this.stockLabel.setText(Integer.toString(art.getStock()));
                this.priceLabel.setText(Float.toString(art.getPrice()));
                //Disable additional buttons
                this.disableAdditionalButtons();
            }
        }else{
            this.idLabel.setText("");
            this.nameLabel.setText("");
            this.stockLabel.setText("");
            this.priceLabel.setText("");
        }
    }

    public void handleDeleteArtikel(){
        int selectedIndex = artikelTable.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0) {
            artikelTable.getItems().remove(selectedIndex);
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("ERROR");
            alert.setHeaderText("No Artikel chosen");

            alert.showAndWait();
        }
    }

    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new artikel.
     */
    @FXML
    private void handleNewArtikel() {
        Artikel tempArtikel = new Artikel();
        boolean okClicked = mainApp.showArtikelEditDialog(tempArtikel);
        if (okClicked) {
            mainApp.getLager().getArtikelData().add(tempArtikel);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected artikel.
     */
    @FXML
    private void handleEditArtikel() {
        Artikel selectedArtikel = artikelTable.getSelectionModel().getSelectedItem();
        if (selectedArtikel != null) {
            boolean okClicked = mainApp.showArtikelEditDialog(selectedArtikel);
            if (okClicked) {
                showArtikelDetails(selectedArtikel);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("ERROR");
            alert.setHeaderText("No Artikel chosen");


            alert.showAndWait();
        }
    }

    @FXML
    public void disableAdditionalButtons(){
        this.additionalLabel1.setOpacity(0D);
        this.additionalLabelFilling1.setOpacity(0D);
        this.additionalLabel2.setOpacity(0D);
        this.additionalLabelFilling2.setOpacity(0D);
    }

    @FXML
    public void enableAdditionalButtons(){
        this.additionalLabel1.setOpacity(1D);
        this.additionalLabelFilling1.setOpacity(1D);
        this.additionalLabel2.setOpacity(1D);
        this.additionalLabelFilling2.setOpacity(1D);
    }

}
