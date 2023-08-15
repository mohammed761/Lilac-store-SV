package Controller;

import il.ac.haifa.cs.sweng.OCSFSimpleChat.App;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.Catalog;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.MsgObject;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.text.DecimalFormat;

import static Controller.CartUserController.specifiedItemsList;
import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.getClient;
import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.msgObject;

//we need to check if there is a discount and to show it

public class CartItemUserController {

    private Catalog catalog;

    @FXML
    private Label descriptionLabel;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label quantityLabel;

    @FXML
    private Label sizeLabel;

    private String style;

    private int counter = 0;

    @FXML
    void handleBuyButton() throws IOException {
        //we need to make a new instance of shop that will count the order and the value of it0

        // User will be redirected to pay for this specific item, when he pays move the item from
        // will pop an alert to say to confirmable the details of the account if not then will pop another alert to say to change his details in the main page
        // cart database to orders database for the current user
        msgObject.getCatalogList().clear();

        catalog.setPrivilege(2);
        msgObject.setMsg("cartToOrder");//"detailsConfirmation"/"userDetailsUser" from there we will send the catalogList
        msgObject.getCatalogList().add(catalog);//if the client confirm everything else we'll not do an update
        App.setRoot("userDetailsUser", "/Image/userDetailsIcon.png", "Details Confirmation");
    }

    @FXML
    void handleBoxClicked() {

        // If (counter % 2 == 0) then the item is chosen so insert it to a list of items you need to buy
        // See the list 'specifiedItemsList' I created at CartUserController and add to it

        if (counter % 2 == 0) {
            anchorPane.setStyle("-fx-background-color: #80b380");
            specifiedItemsList.add(catalog);
            // Insert to a list of items that you wanna buy
        } else {
            anchorPane.setStyle(style);
            specifiedItemsList.remove(catalog);
            // Remove from the list of items that you wanna buy
        }
        counter++;
    }

    @FXML
    void handleRemoveButton() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText("Confirm that you are willing to remove this product from cart");
        ButtonType confirmButton = new ButtonType("Confirm", ButtonBar.ButtonData.YES);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(confirmButton, cancelButton);

        alert.showAndWait().ifPresent(type -> {
            if (type == confirmButton) {
                MsgObject msgObject =  new MsgObject("removeFromCart");
                msgObject.getCatalogList().add(catalog);
                try {
                    getClient().sendToServer(msgObject);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });


    }

    public void setData(Catalog catalog) {
        this.catalog = catalog;
        this.descriptionLabel.setText(catalog.getItemDetails());
        this.nameLabel.setText(catalog.getName());
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(2);
        double catalogPrice = Double.parseDouble(decimalFormat.format(Double.parseDouble(catalog.getPrice())));
        double catalogDiscount = Double.parseDouble(decimalFormat.format(catalog.getDiscount()));
        if(catalog.getDiscount() == -1)
            this.priceLabel.setText(App.CURRENCY + catalogPrice);
        else
            this.priceLabel.setText(App.CURRENCY + catalogDiscount);
        this.sizeLabel.setText(catalog.getSize());
        this.quantityLabel.setText("" + catalog.getLeft());
    }

    @FXML
    void initialize() {
        style = anchorPane.getStyle();
    }
}
