package structs;

import javafx.beans.property.*;
import javafx.fxml.FXML;

public class CD extends Artikel {
    @FXML
    private StringProperty interpret;

    @FXML
    private IntegerProperty tracknumber;

    public CD(){
        super();
        this.interpret = new SimpleStringProperty("sample Interpret");
        this.tracknumber = new SimpleIntegerProperty(0);
    }



    public CD(int id, String name, int stack, Float price, String interpret, int tracknumber){
        super(id, name, stack, price);
        if(interpret.isEmpty()){
            throw new IllegalArgumentException("Interpret must not be empty");
        }else if(tracknumber < 0){
            throw new IllegalArgumentException("Tracknumber must not be below zero!");
        }

        this.interpret = new SimpleStringProperty(interpret);
        this.tracknumber = new SimpleIntegerProperty(tracknumber);


    }

    public String getInterpret() {
        return interpret.get();
    }

    public StringProperty interpretProperty() {
        return interpret;
    }

    public void setInterpret(String interpret) {
        this.interpret.set(interpret);
    }

    public int getTracknumber() {
        return tracknumber.get();
    }

    public IntegerProperty tracknumberProperty() {
        return tracknumber;
    }

    public void setTracknumber(int tracknumber) {
        this.tracknumber.set(tracknumber);
    }
}
