package structs;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;

public class Buch extends Artikel{
    private StringProperty author, verlag;

    public Buch(){
        super();
        this.author = new SimpleStringProperty("John Doe");
        this.verlag = new SimpleStringProperty("Sample Company");
    }

    public String getAuthor() {
        return author.get();
    }

    public StringProperty authorProperty() {
        return author;
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    public String getVerlag() {
        return verlag.get();
    }

    public StringProperty verlagProperty() {
        return verlag;
    }

    public void setVerlag(String verlag) {
        this.verlag.set(verlag);
    }

    public Buch(int id, String name, int stack, BigDecimal price, String author, String verlag){
        super(id, name, stack, price);
        if(author.isEmpty()){
            throw new IllegalArgumentException("Author must not be empty");
        }
        if(verlag.isEmpty()){
            throw new IllegalArgumentException("Verlag must not be empty");
        }

        this.author = new SimpleStringProperty(author);
        this.verlag = new SimpleStringProperty(verlag);

    }
}
