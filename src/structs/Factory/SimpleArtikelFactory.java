package structs.Factory;

import structs.Artikel;
import structs.Buch;
import structs.CD;
import structs.DVD;

public class SimpleArtikelFactory {

    public SimpleArtikelFactory(){

    }

    public Artikel generate(int id, String name, int stock, float price){
        Artikel art;
        try{
            art = new Artikel(id, name, stock, price);
            return art;
        }catch(IllegalArgumentException e){
            return null;
        }
    }

    public Artikel generate(int id, String name, int stock, float price, String author, String verlag){
        Buch buch;
        try{
           buch = new Buch(id, name, stock, price, author, verlag);
           return buch;
        }catch(IllegalArgumentException e){
            return null;
        }
    }

    public Artikel generate(int id, String name, int stock, float price, String interpret, int tracknum){
        CD cd;
        try{
            cd = new CD(id, name, stock ,price, interpret, tracknum);
            return cd;
        }catch(IllegalArgumentException e){
            return null;
        }
    }

    public Artikel generate(int id, String name, int stock, float price, int releaseDate, int playtime){
        DVD dvd;
        try{
            dvd = new DVD(id, name, stock, price, releaseDate, playtime);
            return dvd;
        }catch(IllegalArgumentException e){
            return null;
        }
    }


}
