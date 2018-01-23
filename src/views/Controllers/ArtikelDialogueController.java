package views.Controllers;

import javafx.scene.control.Button;
import structs.Buch;
import structs.CD;
import structs.DVD;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import structs.Artikel;

/**
 * Artikel Dialogue Controller, which is extended in the New Artikel Controller and the Edit Artikel Controller
 */
public abstract class ArtikelDialogueController{

    @FXML
    private TextField idField; //ID FIELD

    @FXML
    private TextField nameField; //Name Field

    @FXML
    private TextField stockField; //Stock Field

    @FXML
    private TextField priceField; //Price Field

    @FXML
    private Label additionalLabel1; //The First additional Label

    @FXML
    private Label additionalLabel2; //The second additional Label

    @FXML
    private TextField additionalTextField1; //The first additional TextField

    @FXML
    private TextField additionalTextField2; //The second additional TextField

    @FXML
    protected Button okButton;




    /**
     * This is needed to ask the User if he wants to edit ID and Name of an Artikel, two attributes which are normally
     * not intended to be changed.
     */
    @FXML
    private boolean overrideEnabled = false; //TODO move this to Artikel Edit controller

    private Stage dialogStage; //Our dialogStage
    private Artikel art; //The current Artikel to be edited
    private boolean okClicked = false; //If the ok button is clicked

    private StringBuilder errorMessageBuilder = new StringBuilder(); //The String  builder for error messages

    /**
     * Standart Constructor, does little to nothing
     */
    public ArtikelDialogueController(){
    }

    /**
     * At this point, SceneBuilder automatically initializes the @FXML annotated Fields, so we do't have to.
     */
    @FXML
    private void initialize() {

    }

    /**
     * Set DialogStage of this object to the given one. used in main method of MainApp.MainApp.
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Set the art attribute of this class to the given art.
     * Also set our different TextField Texts to the Artikels attributes, so we can display them when the user wishes
     * to Edit an Artikel. This is useful, because the User will know the Artikels current Values upon editing.
     * When creating a new Artikel, basic predefined Values are shown.
     * TODO not be a lazy ass and type out all documentation
     * @param art
     */
    public void setArtikel(Artikel art){
        this.art = art; //Self explanatory

        //See above
        this.idField.setText(Integer.toString(art.getId()));
        this.nameField.setText(art.getName());
        this.priceField.setText(Float.toString(art.getPrice()));
        this.stockField.setText(Integer.toString(art.getStock()));


        if(art.getClass().equals(Artikel.class)){
            this.disableAdditionalLabelsAndTextFields(); //If our Artikel is of Class Artikel and not of a Subclass,
                                                         //Disable our additional Labels and TextFields

        }else if(art instanceof  CD){ //If our Artikel is of subclass CD
            CD cd = (CD) art; //Cast it into a CD variable

            this.enableAdditionalLabelsAndTextFields(); //enable our additional Labels and TextFields

            this.additionalLabel1.setText("Interpret"); //Set the first additional Label to display "Interpret"
            this.additionalLabel2.setText("Tracknumber");//Set the second additional Label below it to display "Tracknumber"

            //Set the first additional TextField to the CDs interpret attribute
            this.additionalTextField1.setText(cd.getInterpret());
            //Set the second additional TextField to the CDs Tracknumber attribute
            this.additionalTextField2.setText(Integer.toString(cd.getTracknumber()));

            //Set the Role description of the different TextFields to their corresponding Labels for later use
            this.additionalTextField1.setAccessibleRoleDescription(this.additionalLabel1.getText());
            this.additionalTextField2.setAccessibleRoleDescription(this.additionalLabel2.getText());

        }else if(art instanceof DVD){ //If our Artikel is of subclass DVD
            DVD dvd = (DVD) art; //cast into DVD

            //See above for documentation
            this.enableAdditionalLabelsAndTextFields();
            this.additionalLabel1.setText("Release Date");
            this.additionalLabel2.setText(("Playtime"));
            this.additionalTextField1.setText(Integer.toString(dvd.getReleaseDate()));
            this.additionalTextField2.setText(Integer.toString(dvd.getPlaytime()));
            this.additionalTextField1.setAccessibleRoleDescription(this.additionalLabel1.getText());
            this.additionalTextField2.setAccessibleRoleDescription(this.additionalLabel2.getText());

        }else if(art instanceof Buch){
            Buch buch = (Buch) art;

            this.enableAdditionalLabelsAndTextFields();
            this.additionalLabel1.setText("Author");
            this.additionalLabel2.setText("Verlag");
            this.additionalTextField1.setText(buch.getAuthor());
            this.additionalTextField2.setText(buch.getVerlag());
            this.additionalTextField1.setAccessibleRoleDescription(this.additionalLabel1.getText());
            this.additionalTextField2.setAccessibleRoleDescription(this.additionalLabel2.getText());
        }

    }

    /**
     * Handle the Users press of the OK button. This method is directly connected to the OK button via SceneBuilder
     * and called on action event.
     */
    @FXML
    protected void handleOk(){
        if(isInputValid()){ //If the Input is valid

            //Set the first four values, which Artikel and each of its subclasses share
            this.art.setId(Integer.parseInt(this.idField.getText()));
            this.art.setName(this.nameField.getText());
            this.art.setStack(Integer.parseInt(this.stockField.getText()));
            this.art.setPrice(Float.parseFloat(this.priceField.getText()));

            //Conduct differently if Artikel is of a subclass
            if(art instanceof CD){
                CD cd = (CD) art;
                cd.setInterpret(this.additionalTextField1.getText());
                cd.setTracknumber(Integer.parseInt(this.additionalTextField2.getText()));
            }else if(art instanceof DVD){
                DVD dvd = (DVD) art;
                dvd.setReleaseDate(Integer.parseInt(this.additionalTextField1.getText()));
                dvd.setPlaytime(Integer.parseInt(this.additionalTextField2.getText()));
            }else if(art instanceof Buch){
                Buch buch = (Buch) art;
                buch.setAuthor(this.additionalTextField1.getText());
                buch.setVerlag(this.additionalLabel2.getText());
            }

            this.okClicked = true; //set the boolean okClicked to true
            dialogStage.close(); //Close the Window
        }
    }

    /**
     * Handle the Cancel Buttons OnActionEvent
     */
    @FXML
    private void handleCancel(){
        this.dialogStage.close();
    }

    /**
     * Return okClicked boolean
     * @return
     */
    public boolean isOkClicked(){
        return this.okClicked;
    }

    /**
     * Beauty awakens the soul to act
     * The hottest places in hell are reserved to those who, in times of great moral crisis, maintain this function.
     * All hope abandon, ye who enter here!
     * @return
     */
    protected boolean isInputValid(){

        //Check the ID Field on emptyness, being a number and being below IDMIN or above IDMAX
        if(this.idField.getText() == null || !isInteger(this.idField.getText())
                || (Integer.parseInt(this.idField.getText()) < Artikel.ID_MIN)
                || (Integer.parseInt(this.idField.getText()) > Artikel.ID_MAX)){

            errorMessageBuilder.append("ID must be a four digit number above" + Artikel.ID_MIN + " and below "
                    + Artikel.ID_MAX + "!\n");

        //Checking the nameInput on emptyness and null
        }else if(this.nameField.getText() == null || this.nameField.getText().isEmpty()) {
            errorMessageBuilder.append("Name can't be empty!\n");

        //Checking if the nameField contains anything but Letters (lowercase and uppercase)
        }else if(!this.nameField.getText().trim().matches("[a-zA-Z\\u0020]+")){
            errorMessageBuilder.append("Name must only contain letters!");

        //Checking if stockInput is below zero or null
        }else if(this.stockField.getText() == null || !isInteger(this.stockField.getText())
                || (Integer.parseInt(this.stockField.getText()) < 0)){

            errorMessageBuilder.append("Stock must be a number and above 0!\n");

        //Check price on being below 0, being a Float and being not null
        }else if(this.priceField.getText() == null || !isFloat(this.priceField.getText())
                || Float.parseFloat(this.priceField.getText())  < 0F){
            errorMessageBuilder.append("Price must be a number and above 0!");
        }

        //SPECIFICS

        //If Artikel is of any subclass, check the additionalTextFields on their validity
        if(this.art.getClass() != Artikel.class) {
            checkAdditionalTextFields(this.additionalTextField1);
            checkAdditionalTextFields(this.additionalTextField2);
        }

        //Because DVD needs an extra Check to see if its release date is below MAX_DATE and above MIN_DATE, do it here.
        // Its ugly but works
        if(this.art instanceof DVD){
            DVD dvd = (DVD) art;
            if(isInteger(this.additionalTextField1.getText())
                    && (Integer.parseInt(this.additionalTextField1.getText()) < DVD.MIN_DATE
                    || Integer.parseInt(this.additionalTextField1.getText()) > DVD.MAX_DATE)){
                errorMessageBuilder.append("Release Date must be between " + DVD.MIN_DATE + " and " + DVD.MAX_DATE);
            }
        }


        //If the errorMessageBuilders length is 0, it means its empty, e.g no  Error message has been appended
        if(errorMessageBuilder.length() == 0){
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessageBuilder.toString()); //Display our error messages

            alert.showAndWait();

            return false;
        }


    }

    /**
     * Needed to check if a String is an Integer. Exception based Checking is awful style, but the method lives
     * of its simplicity
     * @param s String
     * @return true if its and Integer, false on else
     */
    public static boolean isInteger(String s) {
        try{
            Integer.parseInt(s);
        }catch(NumberFormatException e) {
            return false;
        }
        return true;

    }

    /**
     * See above
     * @param s
     * @return
     */
    public static boolean isFloat(String s){
        try{
            Float.parseFloat(s);
        }catch(NumberFormatException e){
            return false;
        }

        return true;

    }

    /**
     * used to check additional Text Fields
     **/

    private void checkAdditionalTextFields(TextField textField){
        if(textField != null) {
            if (!isInteger(textField.getText()) && textField.getText().isEmpty()) {
                errorMessageBuilder.append(textField.getAccessibleRoleDescription() + " Must not be empty!");
            } else if (isInteger(textField.getText()) && Integer.parseInt(textField.getText()) < 0) {
                errorMessageBuilder.append(textField.getAccessibleRoleDescription() + " Must not be below 0!");
            }
        }

    }

    /**
     * Switch the Override checkbox
     */
    @FXML
    public void switchOverride(){
        this.overrideEnabled = !overrideEnabled;
        this.idField.setDisable(overrideEnabled);
        this.nameField.setDisable(overrideEnabled);
    }

    /**
     *   manually enable override, only if you know what youre doing
     */
    public void enableOverride(){
        if(!this.overrideEnabled) {
            switchOverride();
        }
    }

    /**
     * See above
     */
    public void disableOverride(){
        if(this.overrideEnabled){
            switchOverride();
        }
    }

    /**
     * Return the overrideEnables boolean
     * @return
     */
    public boolean isOverrideEnabled(){
        return this.overrideEnabled;
    }

    /**
     * Disabling the additional TextFields and Labels, by turning them off and reducing their Opacity to 0D
     */
    @FXML
    public void disableAdditionalLabelsAndTextFields(){
        this.additionalLabel1.setOpacity(0D);
        this.additionalTextField1.setOpacity(0D);
        this.additionalTextField1.setDisable(true);

        this.additionalLabel2.setOpacity(0D);
        this.additionalTextField2.setOpacity(0D);
        this.additionalTextField2.setDisable(true);
    }

    /**
     * Enable the additional TextFields and Labels by turning them on and raising the Opacity to 1D(100%)
     */
    @FXML
    public void enableAdditionalLabelsAndTextFields(){
        this.additionalLabel1.setOpacity(1D);
        this.additionalTextField1.setOpacity(1D);
        this.additionalTextField1.setDisable(false);

        this.additionalLabel2.setOpacity(1D);
        this.additionalTextField2.setOpacity(1D);
        this.additionalTextField2.setDisable(false);
    }

}
