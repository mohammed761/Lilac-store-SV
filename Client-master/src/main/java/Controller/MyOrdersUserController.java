package Controller;

import il.ac.haifa.cs.sweng.OCSFSimpleChat.App;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.Catalog;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.Order;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static Controller.SignInController.user;
import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.msgObject;

// See what's written in initialize

public class MyOrdersUserController {

    @FXML
    private Label totalOrdersLabel;

    @FXML
    private VBox vbox;

    private List<Order> orderList = new ArrayList<>();

    @FXML
    void handleHome() throws IOException {
        App.setRoot("primaryUser", "/Image/mainPageIcon.png", "Lilac");
    }

    @FXML
    void initialize() throws IOException {


        // You must replace what appears down below with the real orders from the orders database
        // You've created

        if(msgObject.getUser() != null)
            user = msgObject.getUser();

        List<Order> orders = (List<Order>) msgObject.getObject();

        if(orders == null)
            return;
        for(Order order : orders){
            if(order.getUser().getId() == user.getId()){
                orderList.add(order);
            }
        }
        //we need to take out the catalog and place order
        if(!orderList.isEmpty()) {
            for (Order order : orderList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("orderItemUser.fxml"));
                Node node = fxmlLoader.load();
                OrderItemUserController orderItemUserController = fxmlLoader.getController();
                orderItemUserController.setData(order);
                vbox.getChildren().add(node);
            }
            totalOrdersLabel.setText("Total Orders: " + orderList.size());
        }
    }
}
