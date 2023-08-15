package Controller;

import com.jfoenix.controls.JFXButton;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.CustomerWorkerRespond;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.MsgObject;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import static Controller.ComplaintsItemCustomerServiceController.*;
import static Controller.SignInController.user;
import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.getClient;


// Type in handleSendRespondButton()

public class RespondToComplaintCustomerServiceController {

    private String style;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private Label charactersLeftLabel;

    @FXML
    private TextField emailTB;

    @FXML
    private Label messageErrorLabel;

    @FXML
    private TextArea notificationTB;

    @FXML
    private TextField refundTB;

    @FXML
    private TextArea respondTB;

    @FXML
    private JFXButton sendButton;

    private String refundStyle;

    private boolean flag;

    @FXML
    void handleCancelButton() throws IOException {

        getClient().sendToServer(new MsgObject("complaintsCustomerService", user));

    }

    @FXML
    void handleHome() throws IOException {
        handleCancelButton();
    }

    @FXML
    void handleRefundTBPressed() {

        String text = refundTB.getText();
        String regularExpressionPattern = "^[0-9]*$";
        Pattern pattern = Pattern.compile(regularExpressionPattern);
        Matcher matcher = pattern.matcher(text);
        if (matcher.matches() && !text.isBlank()) {
            flag = true;
            refundTB.setStyle(refundStyle);
        } else {
            flag = false;
            refundTB.setStyle("-fx-border-color: red; -fx-border-width: 2;");
        }
    }

    @FXML
    void handleRefundTBReleased() {
        handleRefundTBPressed();
    }

    @FXML
    void handleRespondTBKeyPressed() {

        respondTB.setStyle(style);
        messageErrorLabel.setVisible(false);
        if (850 - respondTB.getText().replaceAll("\\n", "").length() > 100) {
            charactersLeftLabel.setTextFill(Color.web("green"));
        } else {
            charactersLeftLabel.setTextFill(Color.web("red"));
        }
        charactersLeftLabel.setText("Characters Left: " + (850 - respondTB.getText().replaceAll("\\n", "").length()));
    }
    @FXML
    void handleRespondTBKeyReleased() {
        handleRespondTBKeyPressed();
    }

    @FXML
    void handleSendRespondButton() {

        handleRefundTBPressed();

        if (respondTB.getText().replaceAll("\\n", "").length() < 30) {
            messageErrorLabel.setVisible(true);
            respondTB.setStyle("-fx-border-color: red");
        } else if (!flag) {
            return;
        } else {
            respondTB.setEditable(false);
            respondTB.setStyle("-fx-text-fill: #9b9d9e");
            respondTB.setFont(Font.font("Cambria", FontWeight.BOLD, 18));
            messageErrorLabel.setTextFill(Color.web("#008000"));
            messageErrorLabel.setText("MESSAGE WAS SENT SUCCESSFULLY");
            messageErrorLabel.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(e -> messageErrorLabel.setVisible(false));
            pause.play();
            sendButton.setDisable(true);
            cancelButton.setDisable(true);

            CustomerWorkerRespond customerWorkerRespond = new CustomerWorkerRespond(user.getUsername(), name, email, phone, notification, respondTB.getText());
            customerWorkerRespond.setRefund(refundTB.getStyle());
            try {
                getClient().sendToServer(new MsgObject("messageRespond",customerWorkerRespond));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // Type here
            // Don't forget to delete the complaint when sent and also edit handleHome to have updated database
        }
    }

    @FXML
    void initialize() {

        style = respondTB.getStyle();
        refundStyle = refundTB.getStyle();
        messageErrorLabel.setVisible(false);

        emailTB.setText(email);

        emailTB.setText(email);

        notificationTB.setText(notification);

        emailTB.setStyle("-fx-text-fill: #9b9d9e");
        notificationTB.setStyle("-fx-text-fill: #9b9d9e");
        emailTB.setFont(Font.font("Cambria", FontWeight.BOLD, 18));
        notificationTB.setFont(Font.font("Cambria", FontWeight.BOLD, 18));

        ComplainUserController.setTextAreaLimit(respondTB, 850);
    }
}

