package structs;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "Artikels")
public class ArtikelListWrapper {

    private List<Artikel> artikelList;

    @XmlElements({
            @XmlElement(name = "Artikel", type = Artikel.class),
            @XmlElement(name = "CD", type = CD.class),
            @XmlElement(name = "DVD", type = DVD.class),
            @XmlElement(name = "Buch", type = Buch.class)
    })
    public List<Artikel> getArtikelList(){
        return this.artikelList;
    }





    public void setArtikelList(List<Artikel> artikelList) {
        this.artikelList = artikelList;
    }

}
