package Controllers.views;

import Controllers.MainApp;
import Controllers.structs.*;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * The Overview Controller
 */
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
    private Label additionalLabel1Left;

    @FXML
    private Label additionalLabel1Right;

    @FXML
    private Label additionalLabel2Left;

    @FXML
    private Label additionalLabel2Right;

    @FXML
    private boolean areAdditionalLabelsEnabled = false;

    private MainApp mainApp;

    public ArtikelOverviewController(){

    }

    /**
     * Initialise our idColumns, nameColumns and kindColumns
     */
    @FXML
    private void initialize(){
        //Populating Columns

        //Lambda cellData is an object of the Artikel Class

        this.idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        this.nameColummn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        //Get the cellDatas class name, and substring the Directoy its in
        //Bad because its hardcoded, but whatever.
        this.kindColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClass().getName()
                .substring("Controllers.structs.".length())));



        //Disable additioal buttons, which are unneeded for the start
        this.disableAdditionalLabels();

        //clear person details
        showArtikelDetails(null);

        //Add a changeListener to display the new Artikel details on click
        artikelTable.getSelectionModel().selectedItemProperty().addListener((
                observable, oldValue, newValue) -> showArtikelDetails(newValue));


    }


    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;

        artikelTable.setItems(mainApp.getLager().getArtikelData());
        //Set the artikelTable
    }

    /**
     * show the Artikel Details on the Right side of the Split Grid
     * @param art
     */
    public void showArtikelDetails(Artikel art){
        if(art != null){

            //Set the first four labels, which each subclass of Artikel shares
            this.idLabel.setText(Integer.toString(art.getId()));
            this.nameLabel.setText(art.getName());
            this.stockLabel.setText(Integer.toString(art.getStock()));
            this.priceLabel.setText(Float.toString(art.getPrice()));


            //Denote the Rest
            if(art.getClass().equals(Artikel.class)) {
                this.disableAdditionalLabels();

            //If the given Artikel is of instance CD
            }else if(art instanceof CD){

                CD cd = (CD) art;

                this.enableAdditionalLabels();
                this.additionalLabel1Left.setText("Interpret");
                this.additionalLabel1Right.setText(cd.getInterpret());
                this.additionalLabel2Left.setText("Tracknumber");
                this.additionalLabel2Right.setText(Integer.toString(cd.getTracknumber()));
            }else if(art instanceof DVD){

                DVD dvd = (DVD) art;
                this.enableAdditionalLabels();
                this.additionalLabel1Left.setText("Release Date");
                this.additionalLabel1Right.setText(Integer.toString(dvd.getReleaseDate()));
                this.additionalLabel2Left.setText("Playtime");
                this.additionalLabel2Right.setText(Integer.toString(dvd.getPlaytime()));
            }else if (art instanceof Buch){

                Buch buch = (Buch) art;
                this.enableAdditionalLabels();
                this.additionalLabel1Left.setText("Author");
                this.additionalLabel1Right.setText(buch.getAuthor());
                this.additionalLabel2Left.setText("Verlag");
                this.additionalLabel2Right.setText(buch.getVerlag());
            }
        }else{
            this.idLabel.setText("");
            this.nameLabel.setText("");
            this.stockLabel.setText("");
            this.priceLabel.setText("");
        }
    }

    /**
     * Handle the User clicking on the Delete button
     */
    @FXML
    private void handleDeleteArtikel(){
        //Get the currently highlighted item and its index
        int selectedIndex = artikelTable.getSelectionModel().getSelectedIndex();
        //if the index is above or 0 , remove it (its -1 on nothing selected)
        if(selectedIndex >= 0) {
            artikelTable.getItems().remove(selectedIndex);

        }else{ //Warn the USer on else
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

        //tempArtikel is set the either an empty Artikel or an empty value of one of its Subclasses
        Artikel tempArtikel = mainApp.showArtikelNewChooserDialog();

        //If its not null (meaning if the user selected something and press OK)
        if(tempArtikel != null){
            //Show the new Dialog
            boolean okClicked = mainApp.showArtikelNewDialog(tempArtikel);

            if (okClicked) { //If the user clicked OK

                //If an Artikel with the same ID is already in the Lager, warn the user
                if(mainApp.getLager().getArtikelData().contains(tempArtikel)){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.initOwner(mainApp.getPrimaryStage());
                    alert.setTitle("ERROR");
                    alert.setHeaderText("Artikel with same ID already in Lager!");


                    alert.showAndWait();
                //Else add it to the lager
                }else {
                    mainApp.getLager().getArtikelData().add(tempArtikel);
                }
            }
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

    /**
     * Disable the additional Labels by setting their Opacity to 0
     */
    @FXML
    public void disableAdditionalLabels(){
        this.additionalLabel1Left.setOpacity(0D);
        this.additionalLabel1Right.setOpacity(0D);
        this.additionalLabel2Left.setOpacity(0D);
        this.additionalLabel2Right.setOpacity(0D);
    }

    /**
     * Enable the additional Labels by setting their Opacity to 1D
     */
    @FXML
    public void enableAdditionalLabels(){
        this.additionalLabel1Left.setOpacity(1D);
        this.additionalLabel1Right.setOpacity(1D);
        this.additionalLabel2Left.setOpacity(1D);
        this.additionalLabel2Right.setOpacity(1D);
    }


}
