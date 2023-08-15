package il.ac.haifa.cs.sweng.OCSFSimpleChat;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(
        name = "specialItem"
)
public class SpecialItem implements Serializable{
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int id;
    @Column(name = "container")
    private String container;

    @Column(name = "Flowers_Number")
    private int numOfFlowers;

    @Column(name = "chocolates")
    private Boolean chocolates;

    @Column(name = "bear")
    private Boolean teddyBeer;

    @Column(name = "message")
    private Boolean thereIsMessage;

    @Column(name = "flowerTypes")
    private String flowerTypes;

    @Column(name = "flowersIsNotIn")
    private String flowersIsNotIn;

    @Column(name = "color")
    private String color;

    @Column(name = "price")
    private String price;

    @Column(length = 2000)
    private String data = "";
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private SignUp user;

    public SpecialItem(){}

    public SpecialItem(String container, int numOfFlowers, Boolean chocolates, Boolean teddyBeer, Boolean thereIsMessage, String flowerType, String flowersIsNotIn, String price, String color, SignUp user){
        this.container = container;
        this.numOfFlowers = numOfFlowers;
        this.chocolates = chocolates;
        this.teddyBeer = teddyBeer;
        this.thereIsMessage = thereIsMessage;
        this.flowerTypes = flowerType;
        this.flowersIsNotIn = flowersIsNotIn;
        this.price = price;
        this.color = color;
        this.user = user;
        this.data ="Special order:\n " +
                "container: " + container + "\n" +
                "number of flowers: " + numOfFlowers + "\n" +
                "contains a box of chocolates: " + chocolates + "\n" +
                "contains a Teddy Beer: " + teddyBeer + "\n" +
                "blessing message: " + thereIsMessage + "\n" +
                "flower type: " + flowerType+ "\n" +
                "flowers that we should know about: " + flowersIsNotIn + "\n" +
                "color: " + color + "\n" +
                "price: " + price + "\n";
    }

    public SignUp getUser() {
        return user;
    }

    public void setUser(SignUp user) {
        this.user = user;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setChocolates(Boolean chocolates) {
        this.chocolates = chocolates;
    }

    public void setColor(String color) {
        this.color = color;
    }


    public void setContainer(String container) {
        this.container = container;
    }

    public void setFlowersIsNotIn(String flowersIsNotIn) {
        this.flowersIsNotIn = flowersIsNotIn;
    }

    public void setFlowerTypes(String flowerTypes) {
        this.flowerTypes = flowerTypes;
    }

    public void setNumOfFlowers(int numOfFlowers) {
        this.numOfFlowers = numOfFlowers;
    }

    public void setTeddyBeer(Boolean teddyBeer) {
        this.teddyBeer = teddyBeer;
    }

    public Boolean getChocolates() {
        return chocolates;
    }

    public Boolean getTeddyBeer() {
        return teddyBeer;
    }

    public Boolean getThereIsMessage() {
        return thereIsMessage;
    }

    public int getNumOfFlowers() {
        return numOfFlowers;
    }

    public String getColor() {
        return color;
    }

    public String getContainer() {
        return container;
    }

    public String getFlowersIsNotIn() {
        return flowersIsNotIn;
    }

    public String getFlowerTypes() {
        return flowerTypes;
    }

    public String getPrice() {
        return price;
    }

    public void setThereIsMessage(Boolean thereIsMessage) {
        this.thereIsMessage = thereIsMessage;
    }
}
