package Controller;

import il.ac.haifa.cs.sweng.OCSFSimpleChat.CustomerWorkerRespond;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.MsgObject;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.io.IOException;

import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.getClient;

public class NotificationsItemUserController {

    private CustomerWorkerRespond complainRespond;
    @FXML
    private Label dateLabel;

    @FXML
    private Label notificationLabel;

    @FXML
    private Label responseLabel;

    @FXML
    void handleRemoveButton() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText("Confirm that you are willing to remove the notification");
        ButtonType confirmButton = new ButtonType("Confirm", ButtonBar.ButtonData.YES);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(confirmButton, cancelButton);

        alert.showAndWait().ifPresent(type -> {
            if (type == confirmButton) {
                try {
                    getClient().sendToServer(new MsgObject("removeNotification", complainRespond));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });


    }

    public void setData(CustomerWorkerRespond complainRespond, String notification, String response, String date) {
        this.complainRespond = complainRespond;
        this.notificationLabel.setText(notification);
        this.responseLabel.setText(response);
        this.dateLabel.setText(date);
    }
}
