package Controller;

import il.ac.haifa.cs.sweng.OCSFSimpleChat.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

import static Controller.SignInController.user;
import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.getClient;
import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.msgObject;

public class ComplaintsCustomerServiceController {

    @FXML
    private Label totalOrdersLabel;

    @FXML
    private VBox vbox;

    private final List<Complain> complainList = (List<Complain>) msgObject.getObject();


    @FXML
    void handleHome() throws IOException {
        if(user.getAccountType().equals("system manager"))
            getClient().sendToServer(new MsgObject("primaryManager", user));
        else
            getClient().sendToServer(new MsgObject("primaryCustomerService", user));
        //App.setRoot("primaryCustomerService", "/Image/mainPageIcon.png", "Lilac");
    }

    @FXML
    void initialize() throws IOException {

        int size = 0;
// we should check if the customer worker answered before 24 hours if no we need to send an automatic message about a refund
        if(complainList != null) {
            for (Complain complain : complainList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("complaintsItemCustomerService.fxml"));
                Node node = fxmlLoader.load();
                ComplaintsItemCustomerServiceController complaintsItemCustomerServiceController = fxmlLoader.getController();
                complaintsItemCustomerServiceController.setData(complain);
                vbox.getChildren().add(node);
            }
            size = complainList.size();
        }

        totalOrdersLabel.setText("Total Complaints: " + size);
    }
}
