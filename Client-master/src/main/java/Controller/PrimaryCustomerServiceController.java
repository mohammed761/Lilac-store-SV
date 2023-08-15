package Controller;


import com.jfoenix.controls.JFXButton;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.App;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.MsgObject;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.io.IOException;

import static Controller.SignInController.user;
import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.getClient;


public class PrimaryCustomerServiceController {

    @FXML
    private JFXButton backToManager;

    @FXML
    void handleBackToManager() throws IOException {

        App.setRoot("primaryManager", "/Image/mainPageIcon.png", "Lilac");
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
    void handleShowComplaints() throws IOException {
        getClient().sendToServer(new MsgObject("complaintsCustomerService"));
    }

    @FXML
    void handleSpecialOrders() throws IOException {
        getClient().sendToServer(new MsgObject("specialOrdersCustomerService"));
    }

    @FXML
    void initialize() {

        backToManager.setVisible(user.getAccountType().equals("system manager"));
    }
}
