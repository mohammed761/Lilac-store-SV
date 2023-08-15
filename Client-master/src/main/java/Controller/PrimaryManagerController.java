package Controller;

import com.jfoenix.controls.JFXComboBox;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.MsgObject;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static Controller.SignInController.user;
import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.getClient;

public class PrimaryManagerController {

    @FXML
    private JFXComboBox<String> allShopsHistogramTypeCB;

    @FXML
    private JFXComboBox<Integer> comparisonFirstShopComplaintCB;

    @FXML
    private JFXComboBox<Integer> comparisonFirstShopOrdersCB;

    @FXML
    private JFXComboBox<Integer> comparisonFirstShopProfitCB;

    @FXML
    private JFXComboBox<Integer> comparisonSecondShopComplaintCB;

    @FXML
    private JFXComboBox<Integer> comparisonSecondShopOrdersCB;

    @FXML
    private JFXComboBox<Integer> comparisonSecondShopProfitCB;

    @FXML
    private JFXComboBox<Integer> singleShopComplaintCB;

    @FXML
    private JFXComboBox<Integer> singleShopOrdersCB;

    @FXML
    private JFXComboBox<Integer> singleShopProfitCB;

    @FXML
    void handleAllShopHistogram() throws IOException {
//        we need to choose what histogram the manager what wants, so we need a new field that the manager must choose from it
        if(allShopsHistogramTypeCB.getValue() != null && allShopsHistogramTypeCB.getValue().equals("Profit"))
            getClient().sendToServer(new MsgObject("Histogram Profit ALL"));
        else if(allShopsHistogramTypeCB.getValue() != null && allShopsHistogramTypeCB.getValue().equals("Orders"))
            getClient().sendToServer(new MsgObject("Histogram Orders ALL"));
        else if(allShopsHistogramTypeCB.getValue() != null && allShopsHistogramTypeCB.getValue().equals("Complaints"))
            getClient().sendToServer(new MsgObject("Histogram Complaints ALL"));
//        to the normal histogram and when we go to the server we need to get all the shops

    }

    @FXML
    void handleComplaintComparison() throws IOException {
        if ((comparisonFirstShopComplaintCB.getValue() != null && comparisonSecondShopComplaintCB.getValue() != null)
        && (comparisonFirstShopComplaintCB.getValue() != comparisonSecondShopComplaintCB.getValue())) {
            String numberOfShops = comparisonFirstShopComplaintCB.getValue() + " ";
            numberOfShops = numberOfShops + comparisonSecondShopComplaintCB.getValue();
            getClient().sendToServer(new MsgObject("compareHist Complaints", numberOfShops));
        }
    }

    @FXML
    void handleComplaintHistogram() throws IOException {
        if (singleShopComplaintCB.getValue() != null) {
            getClient().sendToServer(new MsgObject("Histogram Complaints", singleShopComplaintCB.getValue()));
        }
    }

    @FXML
    void handleEditCatalogue() throws IOException {
        getClient().sendToServer(new MsgObject("catalogueSystemWorker"));
    }

    @FXML
    void handleLogoutButton() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText("Confirm logout");
        ButtonType confirmButton = new ButtonType("Confirm", ButtonBar.ButtonData.YES);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(confirmButton, cancelButton);

        alert.showAndWait().ifPresent(type -> {
            if (type == confirmButton) {
                user.setSignedIn(false);
                try {
                    getClient().sendToServer(new MsgObject("Home", user));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //App.setRoot("primary", "/Image/mainPageIcon.png", "Lilac");
            }
        });
    }

    @FXML
    void handleOrdersComparison() throws IOException {
        if ((comparisonFirstShopOrdersCB.getValue() != null && comparisonSecondShopOrdersCB.getValue() != null)
        && (comparisonFirstShopOrdersCB.getValue() != comparisonSecondShopOrdersCB.getValue())) {
            String numberOfShops = comparisonFirstShopOrdersCB.getValue() + " ";
            numberOfShops = numberOfShops + comparisonSecondShopOrdersCB.getValue();
            getClient().sendToServer(new MsgObject("compareHist Orders", numberOfShops));
        }
    }

    @FXML
    void handleOrdersHistogram() throws IOException {
        if (singleShopOrdersCB.getValue() != null) {
            getClient().sendToServer(new MsgObject("Histogram Orders", singleShopOrdersCB.getValue()));
        }

    }

    @FXML
    void handleProfitComparison() throws IOException {
        if ((comparisonFirstShopProfitCB.getValue() != null && comparisonSecondShopProfitCB.getValue() != null)
        && (comparisonFirstShopProfitCB.getValue() != comparisonSecondShopProfitCB.getValue())) {
            String numberOfShops = comparisonFirstShopProfitCB.getValue() + " ";
            numberOfShops = numberOfShops + comparisonSecondShopProfitCB.getValue();
            getClient().sendToServer(new MsgObject("compareHist Profit", numberOfShops));
        }
    }

    @FXML
    void handleProfitHistogram() throws IOException {
        if (singleShopProfitCB.getValue() != null) {
            getClient().sendToServer(new MsgObject("Histogram Profit", singleShopProfitCB.getValue()));
        }
    }

    @FXML
    void handleShowComplaints() throws IOException {
        //when we click on the home button it will redirect us to the main page of customer service worker // fixed
        getClient().sendToServer(new MsgObject("complaintsCustomerService"));
    }

    @FXML
    void handleShowSpecialOrders() throws IOException {
        //when we click on the home button it will redirect us to the main page of customer service worker //fixed
        getClient().sendToServer(new MsgObject("specialOrdersCustomerService"));

    }

    @FXML
    void handleShowUsers() throws IOException {
        getClient().sendToServer(new MsgObject("editUsersManager"));
    }

    @FXML
    void initialize() {

        allShopsHistogramTypeCB.getItems().add("Profit");
        allShopsHistogramTypeCB.getItems().add("Complaints");
        allShopsHistogramTypeCB.getItems().add("Orders");



        List<Integer> shopList = new ArrayList<>();
        for (int i = 1; i <= 11; i++) shopList.add(i);
        singleShopComplaintCB.getItems().addAll(shopList);
        singleShopProfitCB.getItems().addAll(shopList);
        singleShopOrdersCB.getItems().addAll(shopList);
        comparisonFirstShopComplaintCB.getItems().addAll(shopList);
        comparisonSecondShopComplaintCB.getItems().addAll(shopList);
        comparisonFirstShopProfitCB.getItems().addAll(shopList);
        comparisonSecondShopProfitCB.getItems().addAll(shopList);
        comparisonFirstShopOrdersCB.getItems().addAll(shopList);
        comparisonSecondShopOrdersCB.getItems().addAll(shopList);
    }
}