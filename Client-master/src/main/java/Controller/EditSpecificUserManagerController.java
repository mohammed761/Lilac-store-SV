package Controller;

import com.jfoenix.controls.JFXButton;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.MsgObject;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.SignUp;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static Controller.EditUsersItemManagerController.signUp;
import static Controller.EditUsersManagerController.addFlag;
import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.getClient;
import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.msgObject;

public class EditSpecificUserManagerController {

    @FXML
    private ImageView creditCardNumberImage;

    @FXML
    private MFXComboBox<String> accountType;

    @FXML
    private JFXButton cancel;

    @FXML
    private MFXTextField city;

    @FXML
    private MFXTextField creditCardHolder;

    @FXML
    private MFXTextField creditCardNumber;

    @FXML
    private MFXTextField cvv;

    @FXML
    private MFXTextField email;

    @FXML
    private MFXComboBox<String> expDateMonth;

    @FXML
    private MFXComboBox<String> expDateYear;

    @FXML
    private MFXPasswordField password;

    @FXML
    private MFXComboBox<String> phoneCode;

    @FXML
    private MFXTextField phoneNumber;

    @FXML
    private MFXTextField postOfficeBox;

    @FXML
    private JFXButton save;

    @FXML
    private MFXTextField street;

    @FXML
    private Label successLabel;

    @FXML
    private Label userDetailsErrorLabel;

    @FXML
    private MFXTextField username;

    @FXML
    private MFXTextField zipCode;

    @FXML
    void handleAccountType() {
        accountType.show();
    }

    @FXML
    void handleCancel() throws IOException {
        getClient().sendToServer(new MsgObject("editUsersManager"));
    }

    @FXML
    void handleCreditCardNumber() {
        String text = creditCardNumber.getText();
        if (text.startsWith("4")) {
            creditCardNumberImage.setImage(new Image(getClass().getResourceAsStream("/Image/visa.png")));
        } else if (text.startsWith("37") || text.startsWith("34")) {
            creditCardNumberImage.setImage(new Image(getClass().getResourceAsStream("/Image/american_express.png")));
        } else if (text.startsWith("3")) {
            creditCardNumberImage.setImage(new Image(getClass().getResourceAsStream("/Image/master_card.png")));
        } else if (text.startsWith("6")) {
            creditCardNumberImage.setImage(new Image(getClass().getResourceAsStream("/Image/discover.png")));
        } else {
            creditCardNumberImage.setImage(null);
        }
    }

    @FXML
    void handleExpDateMonth() {
        expDateMonth.show();
    }

    @FXML
    void handleExpDateYear() {
        expDateYear.show();
    }

    @FXML
    void handleHome() throws IOException {
        getClient().sendToServer(new MsgObject("editUsersManager"));
    }

    @FXML
    void handlePhoneCode() {
        phoneCode.show();
    }

    @FXML
    void handleSave() throws IOException {

        List<SignUp> signUpList = (List<SignUp>) msgObject.getObject();
        if (!signUpList.isEmpty()) {
            for (SignUp signUpUser : signUpList) {
                if (signUpUser.getUsername().equals(username.getText()) && !signUpUser.getUsername().equals(signUp.getUsername())) {
                    userDetailsErrorLabel.setText("This username is already taken");
                    userDetailsErrorLabel.setVisible(true);
                    return;
                }
                if (signUpUser.getEmail().equals(email.getText()) && !signUpUser.getEmail().equals(signUp.getEmail())) {
                    userDetailsErrorLabel.setText("This email is already taken");
                    userDetailsErrorLabel.setVisible(true);
                    return;
                }
            }
        }

        if (signUp != null) {
            signUp.setUsername(username.getText());
            signUp.setEmail(email.getText());
            signUp.setPhone(phoneCode.getText() + phoneNumber.getText());
            signUp.setPassword(password.getText());
            signUp.setAccountType(accountType.getText());
            signUp.setCity(city.getText());
            signUp.setStreet(street.getText());
            signUp.setZip(zipCode.getText());
            if (postOfficeBox.getText().equals("")) {
                signUp.setPob("not added");
            } else {
                signUp.setPob(postOfficeBox.getText());
            }
            signUp.setCreditCard(creditCardNumber.getText());
            signUp.setHolderOfCard(creditCardHolder.getText());
            signUp.setDate(expDateYear.getText() + "/" + expDateMonth.getText());
            try {
                int theCvv = Integer.parseInt(cvv.getText());
                signUp.setCvv(theCvv);
            } catch (Exception exception) {
                signUp.setCvv(0);
            }
        }

        SignUp sign = new SignUp(accountType.getText(), username.getText(), email.getText(), phoneCode.getText() + phoneNumber.getText()
                , password.getText(), city.getText() + " " + street.getText() + " " + zipCode.getText() + " " + postOfficeBox.getText(),
                creditCardNumber.getText(), creditCardHolder.getText(), expDateYear.getText() + "/" + expDateMonth.getText(),
                0);
        try {
            int theCvv = Integer.parseInt(cvv.getText());
            sign.setCvv(theCvv);
        } catch (Exception exception) {
            sign.setCvv(0);
        }
        sign.setCity(city.getText());
        sign.setStreet(street.getText());
        sign.setZip(zipCode.getText());
        if (postOfficeBox.getText().equals("")) {
            sign.setPob("not added");
        } else {
            sign.setPob(postOfficeBox.getText());
        }

        if (!addFlag) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Edit Confirmation");
            alert.setHeaderText("Are you sure you want to edit this user?");
            ButtonType confirmButton = new ButtonType("Confirm", ButtonBar.ButtonData.YES);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(confirmButton, cancelButton);

            alert.showAndWait().ifPresent(type -> {
                if (type == confirmButton) {
                    try {
                        getClient().sendToServer(new MsgObject("addUser", signUp));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            successLabel.setText("The user was edited successfully");
        } else {
            getClient().sendToServer(new MsgObject("addUser", sign));
            successLabel.setText("The user was added successfully");
        }

        username.setDisable(true);
        email.setDisable(true);
        phoneCode.setDisable(true);
        phoneNumber.setDisable(true);
        password.setDisable(true);
        accountType.setDisable(true);
        city.setDisable(true);
        street.setDisable(true);
        zipCode.setDisable(true);
        postOfficeBox.setDisable(true);
        creditCardNumber.setDisable(true);
        creditCardHolder.setDisable(true);
        expDateMonth.setDisable(true);
        expDateYear.setDisable(true);
        cvv.setDisable(true);
        cancel.setDisable(true);
        save.setDisable(true);
    }

    @FXML
    void initialize() {

        LocalDate localDate = LocalDate.now();
        phoneCode.getItems().addAll("050", "052", "053", "054", "055", "058");
        int currentMonth = localDate.getMonth().getValue();
        for (int i = currentMonth; i <= 12; i++) {
            if (i <= 9) {
                expDateMonth.getItems().add("0" + i);
            } else {
                expDateMonth.getItems().add(String.valueOf(i));
            }
        }
        int currentYear = localDate.getYear();
        for (int i = 0; i < 7; i++) {
            expDateYear.getItems().add(String.valueOf(currentYear + i));
        }
        accountType.getItems().addAll("system manager", "customer service", "system worker", "elite", "gold");
        for (int i = 1; i <= 11; i++) {
            accountType.getItems().add("shop " + i);
            accountType.getItems().add("shop manager " + i);
        }

        if (addFlag) {
            successLabel.setText("Adding User");
        } else {
            successLabel.setText("Editing User");
            username.setText(signUp.getUsername());
            email.setText(signUp.getEmail());
            try {
                phoneCode.setText(signUp.getPhone().substring(0, 3));
                phoneCode.setValue(signUp.getPhone().substring(0, 3));
                phoneNumber.setText(signUp.getPhone().substring(3));
            } catch (Exception ignored) {
            }
            password.setText(signUp.getPassword());
            accountType.setText(signUp.getAccountType());
            accountType.setValue(signUp.getAccountType());
            city.setText(signUp.getCity());
            street.setText(signUp.getStreet());
            zipCode.setText(signUp.getZip());
            postOfficeBox.setText(signUp.getPob());
            creditCardNumber.setText(signUp.getCreditCard());
            creditCardHolder.setText(signUp.getHolderOfCard());
            try {
                expDateMonth.setText(signUp.getDate().substring(5));
                expDateMonth.setValue(signUp.getDate().substring(5));
                expDateYear.setText(signUp.getDate().substring(0, 4));
                expDateYear.setValue(signUp.getDate().substring(0, 4));
            } catch (Exception ignored) {
            }
            cvv.setText(String.valueOf(signUp.getCvv()));
        }
    }
}
