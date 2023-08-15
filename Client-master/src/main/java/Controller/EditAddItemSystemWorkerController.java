package Controller;

import com.jfoenix.controls.JFXButton;

import il.ac.haifa.cs.sweng.OCSFSimpleChat.Catalog;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.CustomerWorkerRespond;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.MsgObject;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Controller.SignInController.user;
import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.getClient;
import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.msgObject;

// Check handleDoneButton

public class EditAddItemSystemWorkerController {

    private boolean nameFlag;
    private boolean priceFlag;
    private boolean discountFlag;
    private boolean sizeFlag;
    private boolean descriptionFlag;
    private boolean imageFlag = false;

    private String mainProductName;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private ImageView descriptionImage;

    @FXML
    private ImageView discountImage;

    @FXML
    private JFXButton doneButton;

    @FXML
    private ImageView imageImage;

    @FXML
    private ImageView nameImage;

    @FXML
    private ImageView priceImage;

    @FXML
    private ColorPicker productColor;

    @FXML
    private MFXTextField productDescriptionTB;

    @FXML
    private MFXTextField productDiscountTB;

    @FXML
    private ImageView productImage;

    @FXML
    private MFXTextField productNameTB;

    @FXML
    private MFXTextField productPriceTB;

    @FXML
    private MFXTextField productSizeTB;

    @FXML
    private ImageView sizeImage;

    @FXML
    private Label titleLabel;

    @FXML
    void handleCancelButton() throws IOException {
        getClient().sendToServer(new MsgObject("catalogueSystemWorker", user));
    }

    @FXML
    void handleDoneButton() throws IOException {

        handleProductNameKeyPressed();
        handleProductPriceKeyPressed();
        handleProductDiscountKeyPressed();
        handleProductSizeKeyPressed();
        handleProductDescriptionKeyPressed();
        if (CatalogueSystemWorkerController.editFlag) imageFlag = true;
        if (!imageFlag) {
            imageImage.setImage(new Image(getClass().getResourceAsStream("/Image/remove.png")));
        } else {
            imageImage.setImage(new Image(getClass().getResourceAsStream("/Image/accept.png")));
        }
        imageImage.setVisible(true);
        if (!nameFlag || !priceFlag || !discountFlag || !sizeFlag || !descriptionFlag || !imageFlag) {
            return;
        }
        double a;
        if (productDiscountTB.getText().equals("0")) {
            a = -1;
        } else {
            a = ((100 - Double.parseDouble(productDiscountTB.getText())) / 100) * Double.parseDouble(productPriceTB.getText());
        }
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(2);
        a = Double.parseDouble(decimalFormat.format(a));
        CustomerWorkerRespond customerWorkerRespond = new CustomerWorkerRespond();
        if (CatalogueSystemWorkerController.editFlag) {
            // Edit existing item
            // new attributes:
            for (Catalog catalog : msgObject.getCatalogList()) {
                if (catalog.getName().equals(mainProductName)) {

                    catalog.setName(productNameTB.getText());
                    catalog.setItemDetails(productDescriptionTB.getText());
                    catalog.setSize(productSizeTB.getText());
                    catalog.setImgUrl(productImage.getImage().getUrl());
                    catalog.setPrice(productPriceTB.getText());
                    catalog.setDiscount(a);
                    catalog.setColor(String.valueOf(productColor.getValue()).substring(2, 8));
                }
            }
            MsgObject msgObject1 = new MsgObject("edit");
            msgObject1.setCatalogList(msgObject.getCatalogList());
            try {
                getClient().sendToServer(msgObject1);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            productDiscountTB.getText();
            // productImage.getImage(); Can't be use for now

        } else {
            // Add new item
            // attributes:
            Catalog catalog = new Catalog(productImage.getImage().getUrl(), productNameTB.getText(), "" + a, productDescriptionTB.getText(), productSizeTB.getText(), String.valueOf(productColor.getValue()).substring(2, 8));
            catalog.setPrice(productPriceTB.getText());
            try {
                getClient().sendToServer(new MsgObject("addItem", catalog));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        cancelButton.setDisable(true);
        doneButton.setDisable(true);
        getClient().sendToServer(new MsgObject("catalogueSystemWorker"));
    }

    @FXML
    void handleProductDescriptionKeyPressed() {

        String text = productDescriptionTB.getText();
        String regularExpressionPattern = "^[a-zA-Z0-9_.,\"' -]*$";
        Pattern pattern = Pattern.compile(regularExpressionPattern);
        Matcher matcher = pattern.matcher(text);
        if (!matcher.matches() || text.equals("")) {
            descriptionFlag = false;
            descriptionImage.setImage(new Image(getClass().getResourceAsStream("/Image/remove.png")));
        } else {
            descriptionFlag = true;
            descriptionImage.setImage(new Image(getClass().getResourceAsStream("/Image/accept.png")));
        }
        descriptionImage.setVisible(true);
    }

    @FXML
    void handleProductDescriptionKeyReleased() {
        handleProductDescriptionKeyPressed();
    }

    @FXML
    void handleProductColor() {
        productColor.setStyle("-fx-background-color: #" + String.valueOf(productColor.getValue()).substring(2, 8));
    }

    @FXML
    void handleProductDiscountKeyPressed() {

        String text = productDiscountTB.getText();
        String regularExpressionPattern = "^[0-9]*$";
        Pattern pattern = Pattern.compile(regularExpressionPattern);
        Matcher matcher = pattern.matcher(text);
        if (!matcher.matches() || text.equals("")) {
            discountFlag = false;
            discountImage.setImage(new Image(getClass().getResourceAsStream("/Image/remove.png")));
        } else if (Integer.parseInt(text) > 100) {
            discountFlag = false;
            discountImage.setImage(new Image(getClass().getResourceAsStream("/Image/remove.png")));
        } else {
            discountFlag = true;
            discountImage.setImage(new Image(getClass().getResourceAsStream("/Image/accept.png")));
        }
        discountImage.setVisible(true);
    }

    @FXML
    void handleProductDiscountKeyReleased() {
        handleProductDiscountKeyPressed();
    }

    @FXML
    void handleProductNameKeyPressed() {

        String text = productNameTB.getText();
        String regularExpressionPattern = "^[a-zA-Z0-9_. -]*$";
        Pattern pattern = Pattern.compile(regularExpressionPattern);
        Matcher matcher = pattern.matcher(text);
        if (!matcher.matches() || text.equals("")) {
            nameFlag = false;
            nameImage.setImage(new Image(getClass().getResourceAsStream("/Image/remove.png")));
        } else {
            nameFlag = true;
            nameImage.setImage(new Image(getClass().getResourceAsStream("/Image/accept.png")));
        }
        nameImage.setVisible(true);
    }

    @FXML
    void handleProductNameKeyReleased() {
        handleProductNameKeyPressed();
    }

    @FXML
    void handleProductPriceKeyPressed() {

        String text = productPriceTB.getText();
        String regularExpressionPattern = "^(?!\\.)(?!.*\\.$)(?!.*\\.\\.)[0-9.]*$";
        Pattern pattern = Pattern.compile(regularExpressionPattern);
        Matcher matcher = pattern.matcher(text);
        if (!matcher.matches() || text.equals("")) {
            priceFlag = false;
            priceImage.setImage(new Image(getClass().getResourceAsStream("/Image/remove.png")));
        } else {
            priceFlag = true;
            priceImage.setImage(new Image(getClass().getResourceAsStream("/Image/accept.png")));
        }
        priceImage.setVisible(true);
    }

    @FXML
    void handleProductPriceKeyReleased() {
        handleProductPriceKeyPressed();
    }

    @FXML
    void handleProductSizeKeyPressed() {

        String text = productSizeTB.getText();
        String regularExpressionPattern = "^[a-zA-Z0-9.\" ]*$";
        Pattern pattern = Pattern.compile(regularExpressionPattern);
        Matcher matcher = pattern.matcher(text);
        if (!matcher.matches() || text.equals("")) {
            sizeFlag = false;
            sizeImage.setImage(new Image(getClass().getResourceAsStream("/Image/remove.png")));
        } else {
            sizeFlag = true;
            sizeImage.setImage(new Image(getClass().getResourceAsStream("/Image/accept.png")));
        }
        sizeImage.setVisible(true);
    }

    @FXML
    void handleProductSizeKeyReleased() {
        handleProductSizeKeyPressed();
    }

    @FXML
    void handleUploadImage() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png", "*.svg"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            imageFlag = true;
            imageImage.setImage(new Image(getClass().getResourceAsStream("/Image/accept.png")));
            productImage.setImage(new Image(String.valueOf(file)));
        } else {
            imageFlag = false;
            imageImage.setImage(new Image(getClass().getResourceAsStream("/Image/remove.png")));
        }
        imageImage.setVisible(true);
    }

    @FXML
    void initialize() {

        if (CatalogueSystemWorkerController.editFlag) {
            titleLabel.setText("EDIT PRODUCT");
            productNameTB.setText(CatalogueSystemWorkerController.itemName);
            productPriceTB.setText(String.valueOf(CatalogueSystemWorkerController.itemPrice));
            productSizeTB.setText(CatalogueSystemWorkerController.itemSize);
            productDescriptionTB.setText(CatalogueSystemWorkerController.itemDescription);
            productImage.setImage(new Image(CatalogueSystemWorkerController.itemImage));
            productColor.setValue(Color.web(CatalogueSystemWorkerController.itemColor));
            productColor.setStyle("-fx-background-color: #" + CatalogueSystemWorkerController.itemColor);
            mainProductName = productNameTB.getText();

        } else {
            titleLabel.setText("ADD NEW PRODUCT");
        }
    }
}
