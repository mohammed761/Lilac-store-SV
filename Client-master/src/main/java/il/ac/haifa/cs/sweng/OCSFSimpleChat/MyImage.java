package il.ac.haifa.cs.sweng.OCSFSimpleChat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "image")
public class MyImage implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    String url;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Catalog> catalog;

    public MyImage(String url) {
        this.url = url;
    }

    public MyImage() {
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return this.id;
    }

}