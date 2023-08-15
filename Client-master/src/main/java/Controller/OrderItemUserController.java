package Controller;

import il.ac.haifa.cs.sweng.OCSFSimpleChat.App;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.Catalog;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.MsgObject;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.Order;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.text.DecimalFormat;

import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.getClient;

//we need a new field phone
//we need to check if there is a discount to show it
public class OrderItemUserController extends Node {

    private Order order;

    @FXML
    private Label dateLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label sizeLabel;

    public static int numberOfItems; //////////////////////////// Remove the initialization

    @FXML
    void handleBoxClicked() throws IOException {

        getClient().sendToServer(new MsgObject("detailedOrderUser", order));

        // sizeLabel and descriptionLabel named as this to refrain from having errors
        // sizeLabel represents 'Number of items in this order'
        // descriptionLabel represents 'Location'

        // Leave this as it is, it has a use in the page where you show the order details
        // numberOfItems = Integer.parseInt(sizeLabel.getText());

        // App.setRoot("detailedOrderUser", "/Image/myOrdersIcon.png", "Orders");
    }

    @FXML
    void handleCancelOrderButton() {
        //we should send a notification to the client about the refund that he deserve

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText("Are you sure you want to delete this product?");
        alert.setContentText("Deleting a product means you'll never be able to retrieve it again unless you add it manually");
        ButtonType confirmButton = new ButtonType("Confirm", ButtonBar.ButtonData.YES);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(confirmButton, cancelButton);

        alert.showAndWait().ifPresent(type -> {
            if (type == confirmButton) {
                MsgObject msgObject1 =  new MsgObject("removeFromOrder", order);
                //we need to set an object that saves the date, so we can send it to customer worker to check what refund the client should get
                //and to save the object as complain or whatever
                try {
                    getClient().sendToServer(msgObject1);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });


    }

    public void setData(Order order) {
        this.order = order;
        this.descriptionLabel.setText(order.getUser().getAddress());//the location change the label //ok
        this.nameLabel.setText(order.getName());//ok
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(2);
        double orderPrice = Double.parseDouble(decimalFormat.format(Double.parseDouble(order.getPrice())));
        this.priceLabel.setText(App.CURRENCY + orderPrice);//ok
        this.sizeLabel.setText("" + order.getNumberOfItems());//the number of the items // ok
        this.dateLabel.setText(order.getDate());//ok
    }
}
