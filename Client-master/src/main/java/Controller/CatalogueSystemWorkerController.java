package Controller;

import il.ac.haifa.cs.sweng.OCSFSimpleChat.App;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.Catalog;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.MsgObject;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.MyListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;

import static Controller.SignInController.user;
import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.getClient;
import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.msgObject;

// Check if you need to add anything in handleAddItemButton and handleEditButton
//we need to check if there is a discount
public class CatalogueSystemWorkerController {

    public static String itemImage;
    public static String itemName;
    public static double itemPrice;
    public static String itemSize;
    public static String itemDescription;
    public static String itemColor;
    public static boolean addFlag;
    public static boolean editFlag;

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
        if (catalog.getPrivilege() == 0) {
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
            itemColor = catalog.getColor();
            itemImage = catalog.getImgUrl();
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
                if (value.getName().toUpperCase().contains(text.toUpperCase())) {
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
        if(user.getAccountType().equals("system manager"))
            App.setRoot("primaryManager", "/Image/managerIcon.png", "System Manager");
        else
            App.setRoot("primarySystemWorker", "/Image/systemWorkerIcon.png", "System Worker");
    }

    private void loadGridPane() {
        String text = searchItemTB.getText();
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
    void handleAddItemButton() throws IOException {
        addFlag = true;
        editFlag = false;
        App.setRoot("editAddItemSystemWorker", "/Image/mainPageIcon.png", "Lilac");
    }

    @FXML
    void handleDeleteButton() {

        if (flowerList.size() == 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Progress failed");
            alert.setContentText("Catalog must contain at least 1 product");
            alert.show();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText("Are you sure you want to delete this product?");
        alert.setContentText("Deleting a product means you'll never be able to retrieve it again unless you add it manually");
        ButtonType confirmButton = new ButtonType("Confirm", ButtonBar.ButtonData.YES);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(confirmButton, cancelButton);

        alert.showAndWait().ifPresent(type -> {
            if (type == confirmButton) {
                for (Catalog catalog : flowerList) {
                    if (catalog.getName().equals(chosenItemName.getText())) {
                        try {
                            getClient().sendToServer(new MsgObject("deleteItem", catalog));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                // Type here what you need. Delete the item from the database.
            }
        });
    }

    @FXML
    void handleEditButton() throws IOException {

        addFlag = false;
        editFlag = true;
        itemName = chosenItemName.getText();
        itemPrice = Double.parseDouble(chosenItemPrice.getText().substring(1));
        itemSize = chosenItemSize.getText();
        itemDescription = chosenItemDetails.getText();
        App.setRoot("editAddItemSystemWorker", "/Image/mainPageIcon.png", "Lilac");
    }

    @FXML
    void initialize() {

        if (flowerList.size() > 0) setChosenItem(flowerList.get(0));
        loadGridPane();
    }
}
