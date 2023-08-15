package Controller;

import il.ac.haifa.cs.sweng.OCSFSimpleChat.App;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.Catalog;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.MsgObject;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.Order;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static Controller.OrderItemUserController.numberOfItems;
import static Controller.SignInController.user;
import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.getClient;
import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.msgObject;

public class DetailedOrderUserController {

    @FXML
    private Label totalOrdersLabel;

    @FXML
    private VBox vbox;

    @FXML
    void handleBackToOrders() throws IOException {
        MsgObject msgObject1 = new MsgObject("myOrdersUser");
        msgObject1.setUser(user);
        getClient().sendToServer(msgObject1);
    }

    @FXML
    void handleHome() throws IOException {
        App.setRoot("primaryUser", "/Image/mainPageIcon.png", "Lilac");
    }

    @FXML
    void initialize() throws IOException {

        if(msgObject.getUser() != null)
            user = msgObject.getUser();


        List<Catalog> catalogs = msgObject.getCatalogList();
        Order order = (Order) msgObject.getObject();
        List<Catalog> catalogList = new ArrayList<>();
        totalOrdersLabel.setText("Total items: " + order.getNumberOfItems());

        for(Catalog catalog : catalogs){
            if(catalog.getOrder() != null && catalog.getOrder().getId() == order.getId())
                catalogList.add(catalog);
        }

        if(!catalogList.isEmpty()) {
            for (Catalog catalog : catalogList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("detailedOrderItemUser.fxml"));
                Node node = fxmlLoader.load();
                DetailedOrderItemUserController detailedOrderItemUserController = fxmlLoader.getController();
                detailedOrderItemUserController.setData(catalog);
                vbox.getChildren().add(node);
            }
        }
    }
}
