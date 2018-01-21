package Controllers.views;

import Controllers.structs.Buch;
import Controllers.structs.CD;
import Controllers.structs.DVD;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Controllers.structs.Artikel;


public abstract class ArtikelDialogueController{
    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField stockField;

    @FXML
    private TextField priceField;

    @FXML
    private Label additionalLabel1;

    @FXML
    private Label additionalLabel2;

    @FXML
    private TextField additionalLabelFilling1;

    @FXML
    private TextField additionalLabelFilling2;


    @FXML
    private boolean overrideEnabled = false;

    private Stage dialogStage;
    private Artikel art;
    private boolean okClicked = false;

    private    StringBuilder errorMessageBuilder = new StringBuilder();

    public ArtikelDialogueController(){

    }


    @FXML
    private void initialize() {

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setArtikel(Artikel art){
        this.art = art;

        this.idField.setText(Integer.toString(art.getId()));
        this.nameField.setText(art.getName());
        this.priceField.setText(Float.toString(art.getPrice()));
        this.stockField.setText(Integer.toString(art.getStock()));

        if(art.getClass().equals(Artikel.class)){
            System.out.println("Making an Artikel");
            this.disableAdditionalButtons();
        }else if(art instanceof  CD){
            CD cd = (CD) art;

            this.enableAdditionalButtons();
            this.additionalLabel1.setText("Interpret");
            this.additionalLabel2.setText("Tracknumber");
            this.additionalLabelFilling1.setText(cd.getInterpret());
            this.additionalLabelFilling2.setText(Integer.toString(cd.getTracknumber()));
            this.additionalLabelFilling1.setAccessibleRoleDescription(this.additionalLabel1.getText());
            this.additionalLabelFilling2.setAccessibleRoleDescription(this.additionalLabel2.getText());
        }else if(art instanceof DVD){
            DVD dvd = (DVD) art;

            this.enableAdditionalButtons();
            this.additionalLabel1.setText("Release Date");
            this.additionalLabel2.setText(("Playtime"));
            this.additionalLabelFilling1.setText(Integer.toString(dvd.getReleaseDate()));
            this.additionalLabelFilling2.setText(Integer.toString(dvd.getPlaytime()));
            this.additionalLabelFilling1.setAccessibleRoleDescription(this.additionalLabel1.getText());
            this.additionalLabelFilling2.setAccessibleRoleDescription(this.additionalLabel2.getText());

        }else if(art instanceof Buch){
            Buch buch = (Buch) art;

            this.enableAdditionalButtons();
            this.additionalLabel1.setText("Author");
            this.additionalLabel2.setText("Verlag");
            this.additionalLabelFilling1.setText(buch.getAuthor());
            this.additionalLabelFilling2.setText(buch.getVerlag());
            this.additionalLabelFilling1.setAccessibleRoleDescription(this.additionalLabel1.getText());
            this.additionalLabelFilling2.setAccessibleRoleDescription(this.additionalLabel2.getText());
        }


    }

    @FXML
    private void handleOk(){
        if(isInputValid()){
            this.art.setId(Integer.parseInt(this.idField.getText()));
            this.art.setName(this.nameField.getText());
            this.art.setStack(Integer.parseInt(this.stockField.getText()));
            this.art.setPrice(Float.parseFloat(this.priceField.getText()));

            if(art instanceof CD){
                CD cd = (CD) art;
                cd.setInterpret(this.additionalLabelFilling1.getText());
                cd.setTracknumber(Integer.parseInt(this.additionalLabelFilling2.getText()));
            }else if(art instanceof DVD){
                DVD dvd = (DVD) art;
                dvd.setReleaseDate(Integer.parseInt(this.additionalLabelFilling1.getText()));
                dvd.setPlaytime(Integer.parseInt(this.additionalLabelFilling2.getText()));
            }else if(art instanceof Buch){
                Buch buch = (Buch) art;
                buch.setAuthor(this.additionalLabelFilling1.getText());
                buch.setVerlag(this.additionalLabel2.getText());
            }

            this.okClicked = true;
            dialogStage.close();
        }
    }
    @FXML
    private void handleCancel(){
        this.dialogStage.close();
    }

    public boolean isOkClicked(){
        return this.okClicked;
    }

    //TODO
    private boolean isInputValid(){

        if(this.idField.getText() == null || !isInteger(this.idField.getText())
                || (Integer.parseInt(this.idField.getText()) < Artikel.ID_MIN)
                || (Integer.parseInt(this.idField.getText()) > Artikel.ID_MAX)){

            errorMessageBuilder.append("ID must be a four digit number above 0!\n");

        }else if(this.nameField.getText() == null || this.nameField.getText().isEmpty()) {
            errorMessageBuilder.append("Name can't be empty!\n");

        }else if(!this.nameField.getText().matches("[a-zA-Z]+")){
            errorMessageBuilder.append("Name must only contain letters!");

        }else if(this.stockField.getText() == null || !isInteger(this.stockField.getText())
                || (Integer.parseInt(this.stockField.getText()) < 0)){

            errorMessageBuilder.append("Stock must be a number and above 0!\n");

        }else if(this.priceField.getText() == null || !isFloat(this.priceField.getText())
                || Float.parseFloat(this.priceField.getText())  < 0F){
            errorMessageBuilder.append("Price must be a number and above 0!");
        }

        checkAdditionalTextFields(this.additionalLabelFilling1);
        checkAdditionalTextFields(this.additionalLabelFilling2);

        if(this.art instanceof DVD){
            DVD dvd = (DVD) art;
            if(isInteger(this.additionalLabelFilling1.getText())
                    && (Integer.parseInt(this.additionalLabelFilling1.getText()) < DVD.MIN_DATE
                    || Integer.parseInt(this.additionalLabelFilling1.getText()) > DVD.MAX_DATE)){
                errorMessageBuilder.append("Release Date must be between " + DVD.MIN_DATE + " and " + DVD.MAX_DATE);
            }
        }



        if(errorMessageBuilder.length() == 0){
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessageBuilder.toString());

            alert.showAndWait();

            return false;
        }


    }


    public static boolean isInteger(String s) {
        try{
            Integer.parseInt(s);
        }catch(NumberFormatException e) {
            return false;
        }
        return true;

    }

    public static boolean isFloat(String s){
        try{
            Float f = Float.parseFloat(s);
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

    @FXML
    public void switchOverride(){
        this.overrideEnabled = !overrideEnabled;
        this.idField.setDisable(overrideEnabled);
        this.nameField.setDisable(overrideEnabled);
    }

    //manually enable override, only if you know what youre doing
    public void enableOverride(){
        if(!this.overrideEnabled) {
            switchOverride();
        }
    }
    //see above
    public void disableOverride(){
        if(this.overrideEnabled){
            switchOverride();
        }
    }

    public boolean isOverrideEnabled(){
        return this.overrideEnabled;
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
