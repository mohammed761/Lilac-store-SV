package Controller;

import il.ac.haifa.cs.sweng.OCSFSimpleChat.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

import static Controller.SignInController.user;
import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.getClient;

public class SpecialOrderItemCustomerServiceController {

    @FXML
    private Label contentLabel;

    @FXML
    private Label emailLabel;
    private SpecialItem specialItem;
    public static String notification;

    public static String name;

    public static String phone;
    public static String email;

    @FXML
    void handleAcceptButton() {
        CustomerWorkerRespond customerWorkerRespond = new CustomerWorkerRespond(user.getUsername(), specialItem.getUser().getUsername(), specialItem.getUser().getEmail(), specialItem.getUser().getPhone(), specialItem.getData(), "Congratulations, we are happy to let you know that we did our best to make you order come true, we will do our best to make it better.");
        try {
            getClient().sendToServer(new MsgObject("itemAccept", customerWorkerRespond));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void handleRefuseButton() {
        CustomerWorkerRespond customerWorkerRespond = new CustomerWorkerRespond(user.getUsername(), specialItem.getUser().getUsername(), specialItem.getUser().getEmail(), specialItem.getUser().getPhone(), specialItem.getData(), "Sorry, that we did not fill your demands, we will do our best to make it better.\n We could not afford one or more of your specific description.");
        try {
            getClient().sendToServer(new MsgObject("itemRefused", customerWorkerRespond));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void handleRespondButton() throws IOException {
        email = emailLabel.getText();
        notification = contentLabel.getText();
        App.setRoot("respondToSpecialOrderCustomerService", "/Image/specialOrderIcon.png", "Special Order");
    }

    public void setData(SpecialItem specialItem) {
        this.specialItem =  specialItem;
        this.emailLabel.setText(specialItem.getUser().getEmail());
        this.contentLabel.setText(specialItem.getData());
    }
}