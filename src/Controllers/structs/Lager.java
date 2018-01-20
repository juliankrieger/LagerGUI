package Controllers.structs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Lager {
    private ObservableList<Artikel> artikelData= FXCollections.observableArrayList();

    public void addArtikel(Artikel art){
        if(art == null){
            throw new IllegalArgumentException();
        }

        this.artikelData.add(art);
    }

    public ObservableList<Artikel> getArtikelData(){
        return this.artikelData;
    }
}
