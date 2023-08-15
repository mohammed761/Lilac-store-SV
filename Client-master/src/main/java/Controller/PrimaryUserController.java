package Controller;

import il.ac.haifa.cs.sweng.OCSFSimpleChat.App;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.MsgObject;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

import static Controller.SignInController.user;
import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.getClient;

public class PrimaryUserController {

    @FXML
    private Label nameLabel;

    @FXML
    private Label totalRefundLabel;

    @FXML
    private ImageView userImage;

    @FXML
    void handleCartButton() throws IOException {
        getClient().sendToServer(new MsgObject("cartUser"));
    }

    @FXML
    void handleCatalogueButton() throws IOException {
        getClient().sendToServer(new MsgObject("catalogueUser"));
    }

    @FXML
    void handleComplainButton() throws IOException {
        MsgObject msgObject1 = new MsgObject("complainUser");
        getClient().sendToServer(msgObject1);
    }

    @FXML
    void handleEditAccount() throws IOException {
        getClient().sendToServer(new MsgObject("editAccountInformation"));
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
    void handleMyOrdersButton() throws IOException {
        MsgObject msgObject1 = new MsgObject("myOrdersUser");
        msgObject1.setUser(user);
        getClient().sendToServer(msgObject1);
    }

    @FXML
    void handleNotificationsButton() throws IOException {
        MsgObject msgObject1 = new MsgObject("notificationsUser");
        msgObject1.setUser(user);
        getClient().sendToServer(msgObject1);

    }

    @FXML
    void handleSpecialOrderButton() throws IOException {
        getClient().sendToServer(new MsgObject("specialOrderUser"));
    }

    @FXML
    void initialize() {
        nameLabel.setText(user.getUsername());
        switch (SignInController.rank) {

            case "elite":
                userImage.setImage(new Image(getClass().getResourceAsStream("/Image/elitePlan.png")));
                break;

            case "gold":
                userImage.setImage(new Image(getClass().getResourceAsStream("/Image/goldPlan.png")));
                break;

            default:
                userImage.setImage(new Image(getClass().getResourceAsStream("/Image/basicPlan.png")));
                break;
        }
        totalRefundLabel.setText(user.getMoneyInTheBank());
    }
}
