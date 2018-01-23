package structs;

import com.sun.xml.internal.txw2.annotation.XmlElement;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Buch") @XmlElement
public class Buch extends Artikel{
    private StringProperty author, verlag;

    public Buch(){
        super();
        this.author = new SimpleStringProperty("John Doe");
        this.verlag = new SimpleStringProperty("Sample Company");
    }

    public Buch(int id, String name, int stack, Float price, String author, String verlag){
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

}
