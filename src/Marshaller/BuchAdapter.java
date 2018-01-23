package Marshaller;

import structs.Artikel;
import structs.Buch;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class BuchAdapter extends XmlAdapter<Buch, Artikel> {
    @Override
    public Artikel unmarshal(Buch v) throws Exception {
        return v;
    }

    @Override
    public Buch marshal(Artikel v) throws Exception {
        return new Buch();

    }
}
