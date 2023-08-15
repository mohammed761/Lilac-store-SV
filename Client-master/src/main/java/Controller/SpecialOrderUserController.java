package Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.App;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.Catalog;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.MsgObject;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.SpecialItem;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.util.Duration;

import java.io.IOException;

import static Controller.SignInController.user;
import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.getClient;

// Go to the end of handlePlaceOrderButton and see what's written there

public class SpecialOrderUserController {

    @FXML
    private JFXCheckBox alstroemeriaCB;

    @FXML
    private TextArea anotherFlowerTA;

    @FXML
    private JFXComboBox<String> blessingMessage;

    @FXML
    private JFXComboBox<String> boxOfChocolate;

    @FXML
    private JFXCheckBox carnationsCB;

    @FXML
    private JFXComboBox<String> chooseContainer;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private JFXCheckBox daisiesCB;

    @FXML
    private JFXComboBox<String> desiredPrice;

    @FXML
    private JFXCheckBox gerberaCB;

    @FXML
    private JFXCheckBox greenFoliageCB;

    @FXML
    private JFXCheckBox lavenderCB;

    @FXML
    private JFXCheckBox liliesCB;

    @FXML
    private JFXCheckBox lisianthusCB;

    @FXML
    private JFXCheckBox longStemmedRosesCB;

    @FXML
    private Label messageErrorLabel;

    @FXML
    private Label messageErrorLabel1;

    @FXML
    private Label messageErrorLabel2;

    @FXML
    private JFXCheckBox monteCasinoCB;

    @FXML
    private JFXComboBox<Integer> numberOfFlowers;

    @FXML
    private JFXCheckBox rosesCB;

    @FXML
    private JFXCheckBox seasonalFlowersCB;

    @FXML
    private JFXComboBox<String> teddyBear;

    @FXML
    private JFXButton placeOrderRequestButton;

    @FXML
    void handleHome() throws IOException {
        App.setRoot("primaryUser", "/Image/mainPageIcon.png", "Lilac");
    }

    @FXML
    void handlePlaceOrderButton() {

        int counter = 0;
        counter = getSelectedItems(counter, rosesCB, longStemmedRosesCB, lavenderCB, carnationsCB, monteCasinoCB, lisianthusCB);
        counter = getSelectedItems(counter, gerberaCB, seasonalFlowersCB, greenFoliageCB, daisiesCB, alstroemeriaCB, liliesCB);

        String s;

        if (chooseContainer.getValue() == null || numberOfFlowers.getValue() == null ||
                boxOfChocolate.getValue() == null || teddyBear.getValue() == null ||
                blessingMessage.getValue() == null || desiredPrice.getValue() == null) {
            messageErrorLabel1.setVisible(true);
            messageErrorLabel2.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(4));
            pause.setOnFinished(e -> {
                messageErrorLabel1.setVisible(false);
                messageErrorLabel2.setVisible(false);
            });
            pause.play();

            return;
        }

        if (counter > 4) {
            messageErrorLabel.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(4));
            pause.setOnFinished(e -> messageErrorLabel.setVisible(false));
            pause.play();
            return;
        }

        String chosenTypes = "";
        chosenTypes = getChosenTypes(chosenTypes, rosesCB, longStemmedRosesCB, lavenderCB, carnationsCB, monteCasinoCB, lisianthusCB);
        chosenTypes = getChosenTypes(chosenTypes, gerberaCB, seasonalFlowersCB, greenFoliageCB, daisiesCB, alstroemeriaCB, liliesCB);

        SpecialItem specialItem = new SpecialItem(chooseContainer.getValue(), numberOfFlowers.getValue(), boxOfChocolate.getValue().equals("Yes")
                , teddyBear.getValue().equals("Yes"), blessingMessage.getValue().equals("Yes"), chosenTypes, anotherFlowerTA.getText(), desiredPrice.getValue(), String.valueOf(colorPicker.getValue()), user);
        try {
            getClient().sendToServer(new MsgObject("specialItem",specialItem));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String order = "Arrangement of " + chosenTypes;
        order = order.substring(0, order.length() - 2);
        for (int i = order.length() - 1; i >= 0; i--) {
            if (order.charAt(i) == ' ' && order.charAt(i - 1) == ',') {
                order = order.substring(0, i - 1) + " and " + order.substring(i + 1);
                break;
            }
        }
        order += " in a " + chooseContainer.getValue() + ".";
        anotherFlowerTA.setText(order);
        if (boxOfChocolate.getValue().equals("Yes") && teddyBear.getValue().equals("No") && blessingMessage.getValue().equals("No"))
            order += " Comes with a box of chocolates.";
        if (boxOfChocolate.getValue().equals("Yes") && teddyBear.getValue().equals("Yes") && blessingMessage.getValue().equals("No"))
            order += " Comes with a box of chocolates and a teddy bear.";
        if (boxOfChocolate.getValue().equals("Yes") && teddyBear.getValue().equals("No") && blessingMessage.getValue().equals("Yes"))
            order += " Comes with a box of chocolates and a blessing message.";
        if (boxOfChocolate.getValue().equals("No") && teddyBear.getValue().equals("Yes") && blessingMessage.getValue().equals("No"))
            order += " Comes with a teddy bear.";
        if (boxOfChocolate.getValue().equals("No") && teddyBear.getValue().equals("Yes") && blessingMessage.getValue().equals("Yes"))
            order += " Comes with a teddy bear and a blessing message.";
        if (boxOfChocolate.getValue().equals("No") && teddyBear.getValue().equals("No") && blessingMessage.getValue().equals("Yes"))
            order += " Comes with a blessing message.";
        if (boxOfChocolate.getValue().equals("Yes") && teddyBear.getValue().equals("Yes") && blessingMessage.getValue().equals("Yes"))
            order += " Comes with a box of chocolates, teddy bear and a blessing message.";

        int flowersNumber = numberOfFlowers.getValue();
        String anotherSuggestedFlowers = anotherFlowerTA.getText();
        String suggestedPrice = desiredPrice.getValue();
        String dominantColor = String.valueOf(colorPicker.getValue());
        dominantColor = "#" + dominantColor.substring(2, dominantColor.length() - 2);
        placeOrderRequestButton.setDisable(true);

        // Save order, flowersNumber, anotherSuggestedFlowers, suggestedPrice and dominantColor to
        // the special order database
    }

    private String getChosenTypes(String chosenTypes, JFXCheckBox rosesCB, JFXCheckBox longStemmedRosesCB, JFXCheckBox lavenderCB, JFXCheckBox carnationsCB, JFXCheckBox monteCasinoCB, JFXCheckBox lisianthusCB) {

        if (rosesCB.isSelected()) chosenTypes += rosesCB.getText() + ", ";
        if (longStemmedRosesCB.isSelected()) chosenTypes += longStemmedRosesCB.getText() + ", ";
        if (lavenderCB.isSelected()) chosenTypes += lavenderCB.getText() + ", ";
        if (carnationsCB.isSelected()) chosenTypes += carnationsCB.getText() + ", ";
        if (monteCasinoCB.isSelected()) chosenTypes += monteCasinoCB.getText() + ", ";
        if (lisianthusCB.isSelected()) chosenTypes += lisianthusCB.getText() + ", ";
        return chosenTypes;
    }

    private int getSelectedItems(int counter, JFXCheckBox rosesCB, JFXCheckBox longStemmedRosesCB, JFXCheckBox lavenderCB, JFXCheckBox carnationsCB, JFXCheckBox monteCasinoCB, JFXCheckBox lisianthusCB) {

        if (rosesCB.isSelected()) counter++;
        if (longStemmedRosesCB.isSelected()) counter++;
        if (lavenderCB.isSelected()) counter++;
        if (carnationsCB.isSelected()) counter++;
        if (monteCasinoCB.isSelected()) counter++;
        if (lisianthusCB.isSelected()) counter++;
        return counter;
    }

    @FXML
    void handleColorPicker() {
    }

    @FXML
    void initialize() {

        chooseContainer.getItems().addAll("Glass Bowl", "Basket", "Wicker Basket", "Glass Vase", "Vase");
        for (int i = 1; i <= 30; i++) {
            numberOfFlowers.getItems().add(i);
        }
        boxOfChocolate.getItems().addAll("Yes", "No");
        teddyBear.getItems().addAll("Yes", "No");
        blessingMessage.getItems().addAll("Yes", "No");
        desiredPrice.getItems().addAll("₪30 - ₪40", "₪40 - ₪50", "₪50 - ₪60", "₪60 - ₪70", "₪70 - ₪80", "₪80 - ₪90", "₪90 - ₪100", "₪100 - ₪110", "₪110 - ₪120");
    }
}