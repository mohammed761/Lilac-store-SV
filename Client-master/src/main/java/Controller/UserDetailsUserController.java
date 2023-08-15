package Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.App;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.Catalog;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.MsgObject;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.Order;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyComboBox;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Calendar;

import static Controller.SignInController.user;
import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.getClient;
import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.msgObject;

public class UserDetailsUserController {

    @FXML
    private JFXButton backToCartButton;

    @FXML
    private JFXCheckBox blessing;

    @FXML
    private Pane blessingPane;

    @FXML
    private TextArea blessingText;
    @FXML
    private MFXTextField city;

    @FXML
    private JFXButton confirmButton;

    @FXML
    private JFXCheckBox courier;

    @FXML
    private MFXTextField creditCardExpDate;

    @FXML
    private MFXTextField creditCardHolder;

    @FXML
    private MFXTextField creditCardNumber;

    @FXML
    private MFXTextField cvv;

    @FXML
    private MFXDatePicker datePicker;

    @FXML
    private Label deliveryErrorLabel;

    @FXML
    private MFXTextField deliveryReceiverName;

    @FXML
    private MFXTextField deliveryReceiverPhone;

    @FXML
    private JFXButton editMyAccountInformationButton;

    @FXML
    private MFXLegacyComboBox<String> hourPicker;

    @FXML
    private JFXCheckBox pickupFromBranch;

    @FXML
    private MFXTextField postOfficeBox;

    @FXML
    private MFXTextField street;

    @FXML
    private MFXTextField zip;

    @FXML
    private Text finalPriceLabel; // This is a numeric label so just enter numbers here, original price

    @FXML
    private Text finalPriceOriginalLabel; // This is just the ₪ symbol so strikethrough when you need

    @FXML
    private Text finalPriceDiscountedLabel; // This is the ₪ symbol for the discounted price so setVisible(true) when needed

    @FXML
    private Text discountedPriceText; // This is the discounted price text, it's numeric so just enter numbers, setVisible(true) when needed

    private String blessingMessage;
    private double originalPrice;
    private double originalDiscount;

    @FXML
    void handleBackToCart() throws IOException {
        getClient().sendToServer(new MsgObject("cartUser"));
    }

    @FXML
    void handleConfirm() {

        // Check the notes below maybe you need to use some of them

        // String date = datePicker.getText();
        LocalDate date = datePicker.getValue();
        LocalDate localDate = LocalDate.now();
        Calendar calendar = Calendar.getInstance();

        // city.setText(hourPicker.getValue());
        /*String.valueOf(date.getDayOfMonth());
        String.valueOf(date.getMonthValue());
        String.valueOf(date.getYear());*/
        String hour;
        if(hourPicker.getValue() != null) {
            hour = hourPicker.getValue().substring(0, 2);
        }
        else{
            deliveryErrorLabel.setText("Please fill the requested fields");
            deliveryErrorLabel.setVisible(true);
            return;
        }
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY) + 1;

        if (datePicker.getText().equals("") || hourPicker.getValue().equals("") ||
                (!courier.isSelected() && !pickupFromBranch.isSelected()) ||
                deliveryReceiverName.getText().equals("") || deliveryReceiverPhone.getText().equals("")) {
            deliveryErrorLabel.setText("Please fill the requested fields");
            deliveryErrorLabel.setVisible(true);
            return;
        } else if (date.getYear() < localDate.getYear() ||
                date.getMonthValue() < localDate.getMonthValue() ||
                (date.getMonthValue() == localDate.getMonthValue() && date.getDayOfMonth() < localDate.getDayOfMonth())) {
            deliveryErrorLabel.setText("Please select valid values for each field");
            deliveryErrorLabel.setVisible(true);
            return;
        } else if (Integer.parseInt(hour) < currentHour
                && date.getYear() == localDate.getYear() && date.getMonthValue() == localDate.getMonthValue()
                && date.getDayOfMonth() == localDate.getDayOfMonth()) {
            deliveryErrorLabel.setText("Please select valid hour");
            deliveryErrorLabel.setVisible(true);
            return;
        }  else if (Integer.parseInt(hour) == currentHour
                && date.getYear() == localDate.getYear() && date.getMonthValue() == localDate.getMonthValue()
                && date.getDayOfMonth() == localDate.getDayOfMonth() && calendar.get(Calendar.MINUTE) != 0 ) {
            deliveryErrorLabel.setText("Please select valid hour");
            deliveryErrorLabel.setVisible(true);
            return;
        }else {
            Order order = new Order();
            if (date.getMonthValue() < 10)
                order.setDate(date.getYear() + "-0" + date.getMonthValue() + "-");
            else
                order.setDate(date.getYear() + "-" + date.getMonthValue() + "-");

            if(date.getDayOfMonth() < 10)
                order.setDate(order.getDate() + "0" + date.getDayOfMonth() + " " + hourPicker.getValue());
            else
                order.setDate(order.getDate() + date.getDayOfMonth() + " " + hourPicker.getValue());

            order.setBlessing(blessingText.getText());
            order.setShipping(courier.isSelected());
            order.setName(deliveryReceiverName.getText());
            order.setPhone(deliveryReceiverPhone.getText());
            if(finalPriceLabel.isStrikethrough())
                order.setPrice(discountedPriceText.getText());
            else
                order.setPrice(finalPriceLabel.getText());
            msgObject.setObject(order);
            try {
                getClient().sendToServer(msgObject);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        confirmButton.setDisable(true);
        backToCartButton.setDisable(true);
        editMyAccountInformationButton.setDisable(true);
    }

    @FXML
    void handleCourier() {
        //we need to update the final price too, and to make it strikethrough we need to take care that we need to take the order from the shop so there we need to update.
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(2);
        originalPrice = Double.parseDouble(decimalFormat.format(originalPrice));
        if (!courier.isSelected()) {
            pickupFromBranch.setDisable(false);
            finalPriceLabel.setText(String.valueOf(originalPrice));
            if(finalPriceLabel.isStrikethrough()) {
                discountedPriceText.setText(String.valueOf(originalDiscount));
                finalPriceLabel.setStrikethrough(true);
            }
            if(discountedPriceText.isVisible())
                finalPriceLabel.setStrikethrough(true);
        } else {
            pickupFromBranch.setDisable(true);
            finalPriceLabel.setText(String.valueOf(originalPrice + 10));
            if(finalPriceLabel.isStrikethrough()) {
                finalPriceLabel.setStrikethrough(true);
                discountedPriceText.setText(String.valueOf(originalDiscount + 10));
            }
            if(discountedPriceText.isVisible())
                finalPriceLabel.setStrikethrough(true);
        }
    }

    @FXML
    void handleEditMyAccountInformation() throws IOException {
        // Leave it for now, I'll make a page later
        getClient().sendToServer(new MsgObject("editAccountInformation"));
    }

    @FXML
    void handleHome() throws IOException {
        App.setRoot("primaryUser", "/Image/mainPageIcon.png", "Lilac");
    }

    @FXML
    void handlePickUpFromBranch() {

        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(2);
        originalPrice = Double.parseDouble(decimalFormat.format(originalPrice));
        courier.setDisable(pickupFromBranch.isSelected());
        finalPriceLabel.setText(String.valueOf(originalPrice));
        if(finalPriceLabel.isStrikethrough()) {
            discountedPriceText.setText(String.valueOf(originalDiscount));
        }
        if(discountedPriceText.isVisible())
            finalPriceLabel.setStrikethrough(true);

    }

    @FXML
    void handleBlessing() {
        blessingPane.setVisible(blessing.isSelected());
    }

    //we need to take care of these both functions.
    @FXML
    void handleDone() {
        blessingMessage = blessingText.getText();
        // Don't insert this message to database unless the user clicks confirm and the Blessing checkbox is checked
        blessingPane.setVisible(false);
    }

    @FXML
    void initialize() {

        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(2);
        String number = "** **** **** ";
        number = user.getCreditCard().substring(0, 2) + number + user.getCreditCard().substring(user.getCreditCard().length() - 4);
        creditCardNumber.setText(number);
        creditCardHolder.setText(user.getHolderOfCard());
        creditCardExpDate.setText(user.getDate());
        cvv.setText(String.valueOf(user.getCvv()));
        city.setText(user.getCity());
        street.setText(user.getStreet());
        zip.setText(user.getZip());
        postOfficeBox.setText(user.getPob());
        for (int i = 10; i <= 19; i++) {
            hourPicker.getItems().add(i + ":00");
        }
        originalPrice = Double.parseDouble(finalPriceLabel.getText());
        double a = 0;
        for (Catalog catalog : msgObject.getCatalogList()) {
            a += Double.parseDouble(catalog.getPrice());
        }
        originalPrice = a;
        a = Double.parseDouble(decimalFormat.format(a));
        finalPriceLabel.setText("" + a);

        if(user.getAccountType().equals("elite") && a > 50){
            finalPriceLabel.setStrikethrough(true);
            a *= 0.9;
            a = Double.parseDouble(decimalFormat.format(a));
            discountedPriceText.setText("" + a);
            discountedPriceText.setVisible(true);
            finalPriceDiscountedLabel.setVisible(true);
        }

        originalDiscount = a;
    }
}

