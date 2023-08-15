package Controller;

import il.ac.haifa.cs.sweng.OCSFSimpleChat.App;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.Catalog;
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

// See what's written in handleBuyAllButton and initialize

//for every button buy we need to send the client to have his confirmation and asks him if he wants shipping or no
public class CartUserController {

    @FXML
    private Label totalOrdersLabel;

    @FXML
    private VBox vbox;

    private final List<Catalog> catalogList = new ArrayList<>();

    public static List<Catalog> specifiedItemsList = new ArrayList<>();
    // See handleBuySpecifiedButton and buy the items in specifiedItemsList

    @FXML
    void handleBuyAllButton() throws IOException {
        //we need to make a list of new instances of shop to take care of all orders

        // User will be redirected to a page to confirm his details, when he pays move all items
        // will pop an alert to say if he confirmation the details of the account if pop another alert to say to change his details in the main page
        // From the Cart database to orders database of the current user
        //we need to check if there is the same item in the order, so we can just update the price/quantity

        if(!msgObject.getCatalogList().isEmpty()) {
            for (Catalog catalog : msgObject.getCatalogList()) {
                if (catalog.getPrivilege() == 1 && catalog.getUser() != null && catalog.getUser().getEmail().equals(user.getEmail())) {
                    catalog.setPrivilege(2);
                    catalogList.add(catalog);
                }
            }
        }
        msgObject.setMsg("cartToOrder");//"detailsConfirmation"/"userDetailsUser" from there we will send the catalogList
        msgObject.setCatalogList(catalogList);//if the client confirm everything
        App.setRoot("userDetailsUser", "/Image/userDetailsIcon.png", "Details Confirmation");
    }

    @FXML
    void handleBuySpecifiedButton() throws IOException {
        if (specifiedItemsList.size() == 0) return;
        for(Catalog catalog : specifiedItemsList){
            catalog.setPrivilege(2);
        }
        msgObject.setMsg("cartToOrder");//"detailsConfirmation"/"userDetailsUser" from there we will send the catalogList
        msgObject.setCatalogList(specifiedItemsList);//if the client confirm everything
        App.setRoot("userDetailsUser", "/Image/userDetailsIcon.png", "Details Confirmation");
    }

    @FXML
    void handleHome() throws IOException {
        App.setRoot("primaryUser", "/Image/mainPageIcon.png", "Lilac");
    }

    @FXML
    void initialize() throws IOException {
        specifiedItemsList.clear();
        // Add items in the Cart database you already created

        List<Catalog> catalogList1 = new ArrayList<>();

        for(Catalog catalog : msgObject.getCatalogList()){
            if(catalog.getPrivilege() == 1 && catalog.getUser().getEmail().equals(user.getEmail())){
                catalogList1.add(catalog);
            }
        }

        if (!catalogList1.isEmpty()) {
            for (Catalog catalog : catalogList1) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("cartItemUser.fxml"));
                Node node = fxmlLoader.load();
                CartItemUserController cartItemUserController = fxmlLoader.getController();
                cartItemUserController.setData(catalog);
                vbox.getChildren().add(node);

            }
        }
        totalOrdersLabel.setText("Total products: " + catalogList1.size());
    }
}