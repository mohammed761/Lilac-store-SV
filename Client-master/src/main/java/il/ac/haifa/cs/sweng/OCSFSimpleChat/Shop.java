package il.ac.haifa.cs.sweng.OCSFSimpleChat;
//I do think we should save in start (in generate in the server the 10 shops) where every shop have the number of complaints that reserved
//or there is another idea we make a new shop for every day
//example if we make an order in shop 1 in day 25 so we make a new instance of shop where shopId is 1 and we count every thing in this day for this shop
//if we get an order in shop 1 in day 26 we do what we did in 25 (every thing new)
//and when a client makes an order we should save the value of the order here in the specific shop (that client belongs)
//when the user cancel the order we should remove the value of the order in the shop he belongs to
//we count every order the user makes and saves it in the shop that he belongs to
//we need to save the date of the orders, complaints, profit to sort it by days

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(
        name = "shop"
)
public class Shop implements Serializable {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int id;

    @Column(name = "shop_id")
    private String shopId;

    @Column(name = "number_of_complaints")
    private int numberOfComplaints;

    @Column(name = "profit")
    private double profit;

    @Column(name = "number_of_orders")
    private int numberOfOrders;

    @Column(name = "date")
    private String date;

    public Shop(){}
    public Shop(String shopId, int numberOfComplaints, double profit, int numberOfOrders, String date){
        this.shopId = shopId;
        this.numberOfComplaints = numberOfComplaints;
        this.profit = profit;
        this.numberOfOrders = numberOfOrders;
        this.date = date;
    }


    public int getId() {
        return id;
    }

    public double getProfit() {
        return profit;
    }

    public String getDate() {
        return date;
    }

    public int getNumberOfComplaints() {
        return numberOfComplaints;
    }

    public int getNumberOfOrders() {
        return numberOfOrders;
    }

    public String getShopId() {
        return shopId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setNumberOfComplaints(int numberOfComplaints) {
        this.numberOfComplaints = numberOfComplaints;
    }

    public void setNumberOfOrders(int numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
