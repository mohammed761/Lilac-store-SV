package Controller;

import il.ac.haifa.cs.sweng.OCSFSimpleChat.App;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.Complain;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.MsgObject;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Date;

import static Controller.SignInController.user;
import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.getClient;
//when a client send a complaint we need to make a new instance of shop that count that complaint for the specific shop
//if the client is not normal we will make a new instance of the shop 100 that mean global shop

// See what's written at the very beginning of void initialize()
// See what's written at the end of handleSendMessageButton

public class ComplainUserController {

    private String style;

    @FXML
    private Label charactersLeftLabel;

    @FXML
    private TextField emailTB;

    @FXML
    private Label messageErrorLabel;

    @FXML
    private TextArea messageTB;

    @FXML
    private TextField nameTB;

    @FXML
    private TextField phoneTB;

    @FXML
    void handleHome() throws IOException {
        App.setRoot("primaryUser", "/Image/mainPageIcon.png", "Lilac");
    }

    @FXML
    void handleMessageTBKeyPressed() {

        messageTB.setStyle(style);
        messageErrorLabel.setVisible(false);
        if (850 - messageTB.getText().replaceAll("\\n", "").length() > 100) {
            charactersLeftLabel.setTextFill(Color.web("green"));
        } else {
            charactersLeftLabel.setTextFill(Color.web("red"));
        }
        charactersLeftLabel.setText("Characters Left: " + (850 - messageTB.getText().replaceAll("\\n", "").length()));
    }

    @FXML
    void handleMessageTBKeyReleased() {
        handleMessageTBKeyPressed();
    }

    @FXML
    void handleSendMessageButton() {

        if (messageTB.getText().replaceAll("\\n", "").length() < 30) {
            messageErrorLabel.setVisible(true);
            messageTB.setStyle("-fx-border-color: red");
        } else {
            messageTB.setEditable(false);
            messageTB.setStyle("-fx-text-fill: #9b9d9e");
            messageTB.setFont(Font.font("Cambria", FontWeight.BOLD, 18));
            messageErrorLabel.setTextFill(Color.web("#008000"));
            messageErrorLabel.setText("MESSAGE WAS SENT SUCCESSFULLY");
            messageErrorLabel.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(e -> messageErrorLabel.setVisible(false));
            pause.play();

            Date date = new Date();
            String newDate = String.valueOf(java.time.LocalDate.now());

            if(date.getHours() < 10)
                newDate = newDate + " 0" + date.getHours();
            else
                newDate = newDate + " " + date.getHours();

            if(date.getMinutes() < 10)
                newDate = newDate + ":0" + date.getMinutes();
            else
                newDate = newDate + ":" + date.getMinutes();

            Complain complain = new Complain(user.getUsername(), user.getEmail(), user.getPhone(), messageTB.getText(), user.getAccountType(), newDate);
            try {
                getClient().sendToServer(new MsgObject("complainList", complain));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Save this data in the complaint database
        /* nameTB.getText();
        emailTB.getText();
        phoneTB.getText();
        messageTB.getText(); */
    }

    public static void setTextAreaLimit(TextArea textArea, int length) {
        textArea.setOnKeyTyped(event -> {
            String string = textArea.getText().replaceAll("\\n", "");
            if (string.length() > length) {
                textArea.setText(string.substring(0, length));
                textArea.positionCaret(string.length());
            }
        });
    }

    @FXML
    void initialize() {

        style = messageTB.getStyle();
        messageErrorLabel.setVisible(false);

        nameTB.setText(user.getUsername());
        emailTB.setText(user.getEmail()); // Initialize to user's email
        phoneTB.setText(user.getPhone()); // Initialize to user's phone

        nameTB.setStyle("-fx-text-fill: #9b9d9e");
        emailTB.setStyle("-fx-text-fill: #9b9d9e");
        phoneTB.setStyle("-fx-text-fill: #9b9d9e");
        nameTB.setFont(Font.font("Cambria", FontWeight.BOLD, 18));
        emailTB.setFont(Font.font("Cambria", FontWeight.BOLD, 18));
        phoneTB.setFont(Font.font("Cambria", FontWeight.BOLD, 18));

        setTextAreaLimit(messageTB, 850);
    }
}