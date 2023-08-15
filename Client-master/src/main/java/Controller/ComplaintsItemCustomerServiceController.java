package Controller;

import il.ac.haifa.cs.sweng.OCSFSimpleChat.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

import static Controller.SignInController.user;
import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.getClient;
public class ComplaintsItemCustomerServiceController {

    @FXML
    private Label contentLabel;

    @FXML
    private Label emailLabel;

    private Complain complain;


    public static String email;

    public static String notification;

    public static String name;

    public static String phone;

    @FXML
    void handleRefuseButton() {

            CustomerWorkerRespond customerWorkerRespond = new CustomerWorkerRespond(user.getUsername(), complain.getName(), complain.getEmail(), complain.getPhone(), complain.getMessage(), "Sorry, that we did not fill your demands, we will do our best to make it better.\nWe did not see that you deserve refund.");
            try {
                getClient().sendToServer(new MsgObject("messageRefused", customerWorkerRespond));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

    }

    @FXML
    void handleRespondButton() throws IOException{
        email = emailLabel.getText();

            notification = contentLabel.getText();

        App.setRoot("respondToComplaintCustomerService", "/Image/complaintIcon.png", "Complaints");
    }

    public void setData(Complain complain) {


            this.complain =  complain;
            this.emailLabel.setText(complain.getEmail());
            this.contentLabel.setText(complain.getMessage());
            name = complain.getName();

    }
}
