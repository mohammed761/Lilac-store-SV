package Controller;

import il.ac.haifa.cs.sweng.OCSFSimpleChat.App;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.MsgObject;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.getClient;

// Nothing to edit here. you can access account type and the chosen shop (if there is) in any class
// by using (SignUpAccountTypeController.accountType) and (SignUpAccountTypeController.shop)

public class SignUpAccountTypeController {

    public static String accountType;
    public static int shop;
    private String style;

    @FXML
    private Label basicPrice;

    @FXML
    private VBox basicVB;

    @FXML
    private ImageView basicXImage;

    @FXML
    private MFXComboBox<Integer> chooseShopCB;

    @FXML
    private Label eliteDiscount;

    @FXML
    private Label elitePrice;

    @FXML
    private VBox eliteVB;

    @FXML
    private Label errorLabel1;

    @FXML
    private Label errorLabel2;

    @FXML
    private Label goldPrice;

    @FXML
    private VBox goldVB;

    public static String globalAccountType;

    @FXML
    void handleBasicMemberShip() {

        errorLabel1.setVisible(false);
        errorLabel2.setVisible(false);
        chooseShopCB.setVisible(true);
        basicXImage.setVisible(false);
        accountType = "shop ";
        basicVB.setStyle(style);
        goldVB.setStyle("");
        eliteVB.setStyle("");
        basicPrice.setTextFill(Color.web("#ffd700"));
        goldPrice.setTextFill(Color.web("#847979"));
        elitePrice.setTextFill(Color.web("#847979"));
        eliteDiscount.setTextFill(Color.web("#847979"));
    }

    @FXML
    void handleChooseShopCB() {
        chooseShopCB.show();
    }

    @FXML
    void handleEliteMemberShip() {

        errorLabel1.setVisible(false);
        errorLabel2.setVisible(false);
        chooseShopCB.setVisible(false);
        basicXImage.setVisible(true);
        accountType = "elite";
        basicVB.setStyle("");
        goldVB.setStyle("");
        eliteVB.setStyle(style);
        basicPrice.setTextFill(Color.web("#847979"));
        goldPrice.setTextFill(Color.web("#847979"));
        elitePrice.setTextFill(Color.web("#ffd700"));
        eliteDiscount.setTextFill(Color.web("#ffd700"));
    }

    @FXML
    void handleGoldMemberShip() {

        errorLabel1.setVisible(false);
        errorLabel2.setVisible(false);
        chooseShopCB.setVisible(false);
        basicXImage.setVisible(true);
        accountType = "gold";
        basicVB.setStyle("");
        goldVB.setStyle(style);
        eliteVB.setStyle("");
        basicPrice.setTextFill(Color.web("#847979"));
        goldPrice.setTextFill(Color.web("#ffd700"));
        elitePrice.setTextFill(Color.web("#847979"));
        eliteDiscount.setTextFill(Color.web("#847979"));
    }

    @FXML
    void handleHomeButton() throws IOException {
        App.setRoot("primary", "/Image/mainPageIcon.png", "Lilac");
    }

    @FXML
    void handleNextPage() throws IOException {

        if (basicVB.getStyle().equals("") && goldVB.getStyle().equals("") && eliteVB.getStyle().equals("")) {
            errorLabel1.setText("Please choose account type");
            errorLabel2.setText("Please choose account type");
            errorLabel1.setVisible(true);
            errorLabel2.setVisible(true);
        } else if (basicVB.getStyle().equals(style) && chooseShopCB.getSelectedItem() == null) {
            errorLabel1.setText("Please choose the shop");
            errorLabel2.setText("Please choose the shop");
            errorLabel1.setVisible(true);
            errorLabel2.setVisible(true);
        } else {
            if (basicVB.getStyle().equals(style)) {
                shop = chooseShopCB.getSelectedItem();
                globalAccountType = accountType + shop;
                getClient().sendToServer(new MsgObject("signUp", accountType + shop)); //we need to change an item in the msg object that means I need an object not a list of a specific type
            } else {
                globalAccountType = accountType;
                getClient().sendToServer(new MsgObject("signUp", accountType));
            }
        }
    }

    @FXML
    void initialize() {

        List<Integer> shopList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) shopList.add(i);
        chooseShopCB.getItems().addAll(shopList);
        chooseShopCB.setVisible(false);
        style = basicVB.getStyle();
        basicVB.setStyle("");
        goldVB.setStyle("");
        eliteVB.setStyle("");
        errorLabel1.setVisible(false);
        errorLabel2.setVisible(false);
    }
}