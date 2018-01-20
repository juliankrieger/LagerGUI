package Controllers.views;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import Controllers.structs.Artikel;

import java.awt.*;
import java.math.BigDecimal;
import java.util.Scanner;

public class ArtikelEditController {
    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField stockField;

    @FXML
    private TextField priceField;

    private Stage dialogStage;
    private Artikel art;
    private boolean okClicked = false;

    public ArtikelEditController(){

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


    }

    @FXML
    private void handleOk(){
        if(isInputValid()){
            this.art.setId(Integer.parseInt(this.idField.getText()));
            this.art.setName(this.nameField.getText());
            this.art.setStack(Integer.parseInt(this.stockField.getText()));
            this.art.setPrice(Float.parseFloat(this.priceField.getText()));

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

        StringBuilder sb = new StringBuilder();

        if(this.idField.getText() == null || !isInteger(this.idField.getText())
                || (Integer.parseInt(this.idField.getText()) < Artikel.ID_MIN)
                || (Integer.parseInt(this.idField.getText()) > Artikel.ID_MAX)){

            sb.append("ID is not valid!\n");

        }else if(this.nameField.getText() == null || this.nameField.getText().isEmpty()){
            sb.append("Name must not be empty!\n");

        }else if(this.stockField.getText() == null || !isInteger(this.stockField.getText())
                || (Integer.parseInt(this.nameField.getText()) < 0)){

            sb.append("Stock must be a number and not be below zero!\n");

        }else if(this.priceField.getText() == null || !isFloat(this.priceField.getText())
                || Float.parseFloat(this.priceField.getText())  < 0F){
            sb.append("Price must be a number and not below 0!");
        }

        if(sb.length() == 0){
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(sb.toString());

            alert.showAndWait();

            return false;
        }

    }

    public static boolean isInteger(String s) {
        try{
            Integer.parseInt(s);
        }catch(NumberFormatException e){
            return false;
        }finally{
            return true;
        }

    }

    public static boolean isFloat(String s){
        try{
            Float f = Float.parseFloat(s);
        }catch(NumberFormatException e){
            return false;
        }finally{
            return true;
        }
    }

}
