package Controller;

import il.ac.haifa.cs.sweng.OCSFSimpleChat.App;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.Catalog;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.MyListener;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;

import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.msgObject;

public class CatalogController {

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
    private Text discountedItemPrice;

    private final List<Catalog> flowerList = msgObject.getCatalogList();

    private MyListener myListener;

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
                discountedItemPrice.setText((App.CURRENCY + catalog.getDiscount()));
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
        App.setRoot("primary", "/Image/mainPageIcon.png", "Lilac");
    }

    private void loadGridPane() {
        myListener = this::setChosenItem;
        int column = 0;
        int row = 1;
        for (Catalog value : flowerList) {
            if (value.getPrivilege() == 0) {
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