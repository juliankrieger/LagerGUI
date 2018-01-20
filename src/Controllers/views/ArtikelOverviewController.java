package Controllers.views;

import Controllers.MainApp;
import Controllers.structs.Artikel;
import Controllers.structs.Lager;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.math.BigInteger;

public class ArtikelOverviewController {
    @FXML
    private TableView<Artikel> artikelTable;

    @FXML
    private TableColumn<Artikel, Integer> idColumn;

    @FXML
    private TableColumn<Artikel, String> nameColummn;

    @FXML
    private Label nameLabel;

    @FXML
    private Label idLabel;

    @FXML
    private Label stockLabel;

    @FXML
    private Label priceLabel;

    private MainApp mainApp;

    public ArtikelOverviewController(){

    }

    @FXML
    private void initialize(){
        //Populating Columns

        //Lambda cellData is an object of the Artikel Class

        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        nameColummn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        //clear person details
        showArtikelDetails(null);

        artikelTable.getSelectionModel().selectedItemProperty().addListener((
                observable, oldValue, newValue) -> showArtikelDetails(newValue));
    }


    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;

        artikelTable.setItems(mainApp.getLager().getArtikelData());
    }

    public void showArtikelDetails(Artikel art){
        if(art != null){
            this.idLabel.setText(Integer.toString(art.getId()));
            this.nameLabel.setText(art.getName());
            this.stockLabel.setText(Integer.toString(art.getStock()));
            this.priceLabel.setText(Float.toString(art.getPrice()));
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
            alert.setTitle("Nothing selected");
            alert.setHeaderText("No Person selected");
            alert.setContentText("Bitte einen Artikel auswaehlen!");

            alert.showAndWait();
        }
    }

    public void handleNewArtikel(){

    }

    public void handleEditArtikel(){

    }
}
