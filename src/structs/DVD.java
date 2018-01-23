package structs;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;

public class DVD extends Artikel {
    @FXML
    private IntegerProperty releaseDate, playtime;

    public static final int MIN_DATE = 1950; //angegeben
    public static final int MAX_DATE = 2014; //angegeben

    public DVD(){
        super();
        this.releaseDate = new SimpleIntegerProperty(1950);
        this.playtime = new SimpleIntegerProperty(0);
    }

    public DVD(int id, String name, int stack, Float price, int releaseDate, int playtime){
        super(id, name, stack, price);
        if(releaseDate < MIN_DATE || releaseDate > MAX_DATE){
            throw new IllegalArgumentException("Release Date not in Range between " + MIN_DATE +" and " + MAX_DATE);
        }
        if(playtime < 0){
            throw new IllegalArgumentException("Playtime must not be below 0");
        }
    }

    public int getReleaseDate() {
        return releaseDate.get();
    }

    public IntegerProperty releaseDateProperty() {
        return releaseDate;
    }

    public void setReleaseDate(int releaseDate) {
        this.releaseDate.set(releaseDate);
    }

    public int getPlaytime() {
        return playtime.get();
    }

    public IntegerProperty playtimeProperty() {
        return playtime;
    }

    public void setPlaytime(int playtime) {
        this.playtime.set(playtime);
    }
}
