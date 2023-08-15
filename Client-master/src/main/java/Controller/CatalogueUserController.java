package Controller;

import com.mysql.cj.LicenseConfiguration;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.*;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Controller.SignInController.user;
import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.getClient;
import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.msgObject;

// Check what's written in handleAddToCart
//we need to check if there is a discount
public class CatalogueUserController {

    private boolean flag = false;

    @FXML
    private VBox chosenItem;

    @FXML
    private Label chosenItemDetails;

    @FXML
    private ImageView chosenItemImage;

    @FXML
    private Label chosenItemName;

    @FXML
    private Text chosenItemPrice;

    @FXML
    private Label chosenItemSize;

    @FXML
    private GridPane gridPane;

    @FXML
    private TextField searchItemTB;

    @FXML
    private ImageView quantityImage;

    @FXML
    private Label quantityLabel;

    @FXML
    private MFXTextField quantityTB;

    @FXML
    private Text discountedItemPrice;

    private final List<Catalog> flowerList = msgObject.getCatalogList();

    private MyListener myListener;

    @FXML
    void handleAddToCart() {

        handleQuantityTBKeyPressed();
        if (!flag) return;


        for(Catalog catalog : msgObject.getCatalogList()){
            if(catalog.getPrivilege() == 1 && catalog.getUser() != null && catalog.getName().equals(chosenItemName.getText()) && catalog.getUser().getEmail().equals(user.getEmail())){
                double a;
                if (!chosenItemPrice.isStrikethrough()) {
                    a = Double.parseDouble(chosenItemPrice.getText().substring(1)) * Double.parseDouble(quantityTB.getText());
                } else {
                    a = Double.parseDouble(discountedItemPrice.getText().substring(1)) * Double.parseDouble(quantityTB.getText());
                }
                catalog.setPrice("" + (a + Double.parseDouble(catalog.getPrice())));
                catalog.setLeft(Integer.parseInt(quantityTB.getText()) + catalog.getLeft());
                try {
                    getClient().sendToServer(new MsgObject("updateCart", catalog));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return;
            }
        }

        Catalog catalog = new Catalog();
        catalog.setLeft(Integer.parseInt(quantityTB.getText()));
        catalog.setName(chosenItemName.getText());
        catalog.setSize(chosenItemSize.getText());
        double a;
        if (!chosenItemPrice.isStrikethrough()) {
            a = Double.parseDouble(chosenItemPrice.getText().substring(1)) * Double.parseDouble(quantityTB.getText());
        } else {
            a = Double.parseDouble(discountedItemPrice.getText().substring(1)) * Double.parseDouble(quantityTB.getText());
        }
        catalog.setPrice("" + a);
        catalog.setItemDetails(chosenItemDetails.getText());
        catalog.setPrivilege(1);
        catalog.setUser(user);

        try {
            getClient().sendToServer(new MsgObject("addToCart", catalog));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Add these items to cart database along with the quantity
    }

    @FXML
    void handleQuantityQuestionMarkEnter() {
        quantityLabel.setVisible(true);
    }

    @FXML
    void handleQuantityQuestionMarkExit() {
        quantityLabel.setVisible(false);
    }

    @FXML
    void handleQuantityTBKeyPressed() {

        String text = quantityTB.getText();
        String regularExpressionPattern = "^(0|[1-9][0-9]*)$";
        Pattern pattern = Pattern.compile(regularExpressionPattern);
        Matcher matcher = pattern.matcher(text);
        if (!matcher.matches() || text.equals("0")) {
            flag = false;
            quantityImage.setImage(new Image(getClass().getResourceAsStream("/Image/remove.png")));
        } else {
            flag = true;
            quantityImage.setImage(new Image(getClass().getResourceAsStream("/Image/accept.png")));
        }
        quantityImage.setVisible(true);
    }

    @FXML
    void handleQuantityTBKeyReleased() {
        handleQuantityTBKeyPressed();
    }

    @FXML
    void handleVisitCart() throws IOException {
        getClient().sendToServer(new MsgObject("cartUser"));
    }

    private void setChosenItem(Catalog catalog) {
        if(catalog.getPrivilege() == 0) {
            chosenItemName.setText(catalog.getName());
            if(catalog.getDiscount() == -1) {
                chosenItemPrice.setText(App.CURRENCY + catalog.getPrice());
                chosenItemPrice.setStrikethrough(false);
                discountedItemPrice.setVisible(false);
            }
            else{
                chosenItemPrice.setText(App.CURRENCY + catalog.getPrice());
                discountedItemPrice.setText(App.CURRENCY + catalog.getDiscount());
                chosenItemPrice.setStrikethrough(true);
                discountedItemPrice.setVisible(true);
            }
            chosenItemDetails.setText(catalog.getItemDetails());
            chosenItemSize.setText(catalog.getSize());
            Image image = new Image(catalog.getImgUrl());
            chosenItemImage.setImage(image);
            chosenItem.setStyle("-fx-background-color: #" + catalog.getColor() + ";\n" +
                    "    -fx-background-radius: 30;");
        }
    }

    @FXML
    void searchItemTBKeyPressed() {

        gridPane.getChildren().clear();
        String text = searchItemTB.getText();
        if (text.equals("")) {
            loadGridPane();
            return;
        }
        myListener = this::setChosenItem;
        int column = 0;
        int row = 1;

        for (Catalog value : flowerList) {
            try {
                if (value.getName().toUpperCase().contains(text.toUpperCase()) && value.getPrivilege() == 0) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/il/ac/haifa/cs/sweng/OCSFSimpleChat/Item.fxml"));

                    AnchorPane anchorPane = fxmlLoader.load();

                    ItemController itemController = fxmlLoader.getController();
                    itemController.setData(value, myListener);

                    if (column == 3) {
                        column = 0;
                        row++;
                    }

                    gridPane.add(anchorPane, column++, row);
                    gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                    gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    gridPane.setMaxWidth(Region.USE_PREF_SIZE);

                    gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                    gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    gridPane.setMaxHeight(Region.USE_PREF_SIZE);

                    GridPane.setMargin(anchorPane, new Insets(10));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void searchItemTBKeyReleased() {

        searchItemTBKeyPressed();
    }

    @FXML
    void handleHomeCatalog() throws IOException {
        App.setRoot("primaryUser", "/Image/mainPageIcon.png", "Lilac");
    }

    private void loadGridPane() {
        myListener = this::setChosenItem;
        int column = 0;
        int row = 1;

        for (Catalog value : flowerList) {
            if(value.getPrivilege() == 0) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/il/ac/haifa/cs/sweng/OCSFSimpleChat/Item.fxml"));

                    AnchorPane anchorPane = fxmlLoader.load();

                    ItemController itemController = fxmlLoader.getController();
                    itemController.setData(value, myListener);

                    if (column == 3) {
                        column = 0;
                        row++;
                    }

                    gridPane.add(anchorPane, column++, row);
                    gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                    gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    gridPane.setMaxWidth(Region.USE_PREF_SIZE);

                    gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                    gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    gridPane.setMaxHeight(Region.USE_PREF_SIZE);

                    GridPane.setMargin(anchorPane, new Insets(10));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    void initialize() {

        if (flowerList.size() > 0) setChosenItem(flowerList.get(0));
        loadGridPane();
    }
}
