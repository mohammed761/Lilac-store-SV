package Controller;

import com.jfoenix.controls.JFXButton;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.Complain;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.MsgObject;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.App;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.getClient;


// In handleSendMessageButton create a table in the data base which saves the sent message

public class ContactUsNotSignedController {

    String style;
    boolean flag1;
    boolean flag2;
    boolean flag3;

    @FXML
    private Label charactersLeftLabel;

    @FXML
    private ImageView emailImage;

    @FXML
    private TextField emailTB;

    @FXML
    private Label messageErrorLabel;

    @FXML
    private TextArea messageTB;

    @FXML
    private ImageView nameImage;

    @FXML
    private TextField nameTB;

    @FXML
    private ImageView phoneImage;

    @FXML
    private TextField phoneTB;

    @FXML
    private JFXButton sendMessageButton;

    @FXML
    void handleEmailTBKeyPressed() {

        String text = emailTB.getText();
        String regularExpressionPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regularExpressionPattern);
        Matcher matcher = pattern.matcher(text);
        if (!matcher.matches()) {
            flag2 = false;
            emailImage.setImage(new Image(getClass().getResourceAsStream("/Image/removeIcon.png")));
        } else {
            flag2 = true;
            emailImage.setImage(new Image(getClass().getResourceAsStream("/Image/acceptIcon.png")));
        }
    }

    @FXML
    void handleEmailTBKeyReleased() {
        handleEmailTBKeyPressed();
    }

    @FXML
    void handleHomeContactUs() throws IOException {
        App.setRoot("primary", "/Image/mainPageIcon.png", "Lilac");
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
    void handleNameTBKeyPressed() {

        String text = nameTB.getText();
        String regularExpressionPattern = "^[a-zA-Z'\\s-]{2,25}$";
        Pattern pattern = Pattern.compile(regularExpressionPattern);
        Matcher matcher = pattern.matcher(text);
        if (!matcher.matches()) {
            flag1 = false;
            nameImage.setImage(new Image(getClass().getResourceAsStream("/Image/removeIcon.png")));
        } else {
            flag1 = true;
            nameImage.setImage(new Image(getClass().getResourceAsStream("/Image/acceptIcon.png")));
        }
    }

    @FXML
    void handleNameTBKeyReleased() {
        handleNameTBKeyPressed();
    }

    @FXML
    void handlePhoneTBKeyPressed() {

        String text = phoneTB.getText();
        String regularExpressionPattern = "^[0-9]([.\\s\\-]?\\d){9}$";
        Pattern pattern = Pattern.compile(regularExpressionPattern);
        Matcher matcher = pattern.matcher(text);
        if (!matcher.matches()) {
            flag3 = false;
            phoneImage.setImage(new Image(getClass().getResourceAsStream("/Image/removeIcon.png")));
        } else {
            flag3 = true;
            phoneImage.setImage(new Image(getClass().getResourceAsStream("/Image/acceptIcon.png")));
        }
    }

    @FXML
    void handlePhoneTBKeyReleased() {
        handlePhoneTBKeyPressed();
    }

    @FXML
    void handleSendMessageButton() throws IOException {

        handleNameTBKeyPressed();
        handleEmailTBKeyPressed();
        handlePhoneTBKeyPressed();

        if (flag1 && flag2 && flag3 && !(messageTB.getText().replaceAll("\\n", "").length() < 30)) {
            nameTB.setEditable(false);
            emailTB.setEditable(false);
            phoneTB.setEditable(false);
            messageTB.setEditable(false);
            nameTB.setStyle("-fx-text-fill: #9b9d9e");
            emailTB.setStyle("-fx-text-fill: #9b9d9e");
            phoneTB.setStyle("-fx-text-fill: #9b9d9e");
            messageTB.setStyle("-fx-text-fill: #9b9d9e");
            nameTB.setFont(Font.font("Cambria", FontWeight.BOLD, 18));
            emailTB.setFont(Font.font("Cambria", FontWeight.BOLD, 18));
            phoneTB.setFont(Font.font("Cambria", FontWeight.BOLD, 18));
            messageTB.setFont(Font.font("Cambria", FontWeight.BOLD, 18));
            messageErrorLabel.setTextFill(Color.web("#008000"));
            messageErrorLabel.setText("MESSAGE WAS SENT SUCCESSFULLY");
            messageErrorLabel.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(e -> messageErrorLabel.setVisible(false));
            pause.play();
        } else if (messageTB.getText().replaceAll("\\n", "").length() < 30) {
            messageErrorLabel.setVisible(true);
            messageTB.setStyle("-fx-border-color: red");
        }
        Complain complain = new Complain(nameTB.getText(), emailTB.getText(), phoneTB.getText(), messageTB.getText(),"shop 11", String.valueOf(java.time.LocalDate.now()));
        getClient().sendToServer(new MsgObject("complainList",complain));
        sendMessageButton.setDisable(true);
    }

    public static void setTextLimit(TextField textField, int length) {
        textField.setOnKeyTyped(event -> {
            String string = textField.getText();
            if (string.length() > length) {
                textField.setText(string.substring(0, length));
                textField.positionCaret(string.length());
            }
        });
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

        setTextLimit(nameTB, 24);
        setTextLimit(emailTB, 60);
        setTextLimit(phoneTB, 10);
        setTextAreaLimit(messageTB, 850);

        nameImage.setImage(null);
        emailImage.setImage(null);
        phoneImage.setImage(null);
    }
}