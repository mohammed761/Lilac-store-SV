package Controller;

import il.ac.haifa.cs.sweng.OCSFSimpleChat.App;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.CustomerWorkerRespond;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.MsgObject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static Controller.SignInController.user;
import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.getClient;
import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.msgObject;

public class NotificationsUserController {

    @FXML
    private Label totalOrdersLabel;

    @FXML
    private VBox vbox;

    @FXML
    void handleDeleteAllButton() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText("Confirm that you are willing to remove all the notifications");
        ButtonType confirmButton = new ButtonType("Confirm", ButtonBar.ButtonData.YES);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(confirmButton, cancelButton);

        alert.showAndWait().ifPresent(type -> {
            if (type == confirmButton) {
                try {
                    getClient().sendToServer(new MsgObject("removeAllNotification",user));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });


    }

    @FXML
    void handleHome() throws IOException {
        App.setRoot("primaryUser", "/Image/mainPageIcon.png", "Lilac");
    }

    @FXML
    void initialize() throws IOException {

        List<CustomerWorkerRespond> complainResponds = (List<CustomerWorkerRespond>) msgObject.getObject();

        int counter = 0;
        if (complainResponds != null) {
            for (CustomerWorkerRespond complainRespond : complainResponds) {
                if (complainRespond.getEmail().equals(user.getEmail())) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(App.class.getResource("notificationsItemUser.fxml"));
                    Node node = fxmlLoader.load();
                    NotificationsItemUserController notificationsItemUserController = fxmlLoader.getController();
                    notificationsItemUserController.setData(complainRespond, "New Notification " + counter++, complainRespond.getRespondMessage(), complainRespond.getDate());
                    vbox.getChildren().add(node);
                }
            }
            totalOrdersLabel.setText("Total notifications: " + counter);
        }
    }
}
