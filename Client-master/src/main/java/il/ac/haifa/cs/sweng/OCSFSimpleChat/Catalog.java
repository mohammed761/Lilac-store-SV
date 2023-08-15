package il.ac.haifa.cs.sweng.OCSFSimpleChat;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.*;

@Entity
@Table(
        name = "catalog"
)
public class Catalog implements Serializable {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int id;
    @Column(name = "item")
    private String name;
    @Column(name = "color")
    private String color;
    @Column(name = "item_details", length = 2000)
    private String itemDetails;
    @Column(name = "price")
    private String price;
    @Column(name = "size")
    private String size;

    @Column(name = "Quantity")
    private int left;
    @Column(name = "imgUrl")
    private String imgUrl;

    @Column(name = "dicount")
    private double discount = -1;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private SignUp user;

    @ManyToOne(cascade = CascadeType.ALL)
    private Order order;

    private int privilege = 0;

    public Catalog(String imgUrl, String name, String price, String details, String size, String color){
        this.imgUrl = imgUrl;
        this.name = name;
        this.price = price;
        this.itemDetails = details;
        this.color = color;
        this.size = size;
    }

    public Catalog(){
        this.imgUrl = "";
        this.name = "";
        this.price = "";
        this.itemDetails = "";
        this.color = "";
        this.size = "";
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getDiscount() {
        return discount;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public SignUp getUser() {
        return user;
    }

    public void setUser(SignUp user) {
        this.user = user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getItemValue() {
        return price;
    }


    public String getItemDetails() {
        return itemDetails;
    }


    public String getPrice() {
        return price;
    }

    public String getColor() {
        return color;
    }

    public String getSize() {
        return size;
    }

    public void setItemDetails(String itemDetails) {
        this.itemDetails = itemDetails;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setSize(String size) {
        this.size = size;
    }
    public int getLeft() {
        return left;
    }
    public void setLeft(int left) {
        this.left = left;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getPrivilege() {
        return privilege;
    }

    public void setPrivilege(int privilege) {
        this.privilege = privilege;
    }
}
