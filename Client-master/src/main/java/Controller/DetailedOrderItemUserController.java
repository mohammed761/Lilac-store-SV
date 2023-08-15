package Controller;

import il.ac.haifa.cs.sweng.OCSFSimpleChat.App;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.Catalog;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.MsgObject;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.Order;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.io.IOException;
import java.text.DecimalFormat;

import static Controller.SignInController.user;
import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.getClient;


//we need to check if there is a discount and to show it
public class DetailedOrderItemUserController {

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label quantityLabel;

    @FXML
    private Label sizeLabel;

    private Catalog catalog;

    @FXML
    void handleRemoveButton() throws IOException {

        // Don't forget that if the last item was removed then you have to remove the whole order
        // I didn't add a remove all button in DetailedOrderUserController in purpose since if the user tend
        // To remove everything he must remove the order from the main page

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remove Confirmation");
            alert.setHeaderText("Remove product?");
            ButtonType confirmButton = new ButtonType("Confirm", ButtonBar.ButtonData.YES);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(confirmButton, cancelButton);

            alert.showAndWait().ifPresent(type -> {
                if (type == confirmButton) {
                    user.setSignedIn(false);
                    try {
                        getClient().sendToServer(new MsgObject("removeItem", catalog));
                    } catch (IOException e) {
                        e.printStackTrace();
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
}
