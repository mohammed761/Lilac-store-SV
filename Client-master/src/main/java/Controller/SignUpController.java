package Controller;

import com.jfoenix.controls.JFXButton;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.MsgObject;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.SignUp;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.App;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Controller.SignUpAccountTypeController.globalAccountType;
import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.getClient;
import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.msgObject;

// In handleUserNameTB check if the username exists in the database
// In handleEmailTB check if the email exists in the database
// In handlePhoneTB check if the phone number exists in the database
// If the credit card number entered already found in the database we'll check later what to do
// In handleSignUpButton you can get account type and shop chosen (if there is) by using the commands
// (SignUpAccountTypeController.accountType) and (SignUpAccountTypeController.shop)

public class SignUpController {

    private String firstLabel;
    private String secondLabel;
    private String thirdLabel;
    private boolean flag1;
    private boolean flag2;
    private boolean flag3;
    private boolean flag4;
    private boolean flag5;
    private boolean flag6;
    private boolean flag7;
    private boolean flag8;
    private boolean flag9;
    private boolean flag10;
    private boolean flag11;
    private boolean flag12;
    private boolean flag13;

    @FXML
    private ImageView CVVImage;

    @FXML
    private MFXTextField CVVTB;

    @FXML
    private ImageView CityImage;

    @FXML
    private MFXTextField CityTB;

    @FXML
    private ImageView ConfirmPasswordImage;

    @FXML
    private MFXPasswordField ConfirmPasswordTB;

    @FXML
    private ImageView CreditCardHolderImage;

    @FXML
    private MFXTextField CreditCardHolderTB;

    @FXML
    private ImageView CreditCardNoCardImage;

    @FXML
    private ImageView CreditCardNumberImage;

    @FXML
    private MFXTextField CreditCardNumberTB;

    @FXML
    private ImageView EmailImage;

    @FXML
    private MFXTextField EmailTB;

    @FXML
    private ImageView ExpDateImage;

    @FXML
    private MFXComboBox<String> ExpDateMonthCB;

    @FXML
    private MFXComboBox<String> ExpDateYearCB;

    @FXML
    private ImageView POBoxImage;

    @FXML
    private MFXTextField POBoxTB;

    @FXML
    private ImageView PasswordImage;

    @FXML
    private MFXPasswordField PasswordTB;

    @FXML
    private ImageView PhoneImage;

    @FXML
    private MFXTextField PhoneTB;

    @FXML
    private JFXButton SignUpButton;

    @FXML
    private ImageView StreetImage;

    @FXML
    private MFXTextField StreetTB;

    @FXML
    private ImageView UserNameImage;

    @FXML
    private MFXTextField UsernameTB;

    @FXML
    private MFXTextField ZIPCTB;

    @FXML
    private ImageView ZIPImage;

    @FXML
    private MFXComboBox<String> phoneCodeList;

    @FXML
    private Label showLabelCVV;

    @FXML
    private Label showLabelPassword;

    @FXML
    private Label showLabelUserName;

    @FXML
    private Label successLabel1;

    @FXML
    private Label successLabel2;

    @FXML
    private Label successLabel3;

    @FXML
    private Label userDetailsErrorLabel;

    private SignUp signup = new SignUp();

    @FXML
    void CVVQuestionMarkEnter() {
        showLabelCVV.setVisible(true);
    }

    @FXML
    void CVVQuestionMarkExit() {
        showLabelCVV.setVisible(false);
    }

    @FXML
    void handleCVVTB() {

        String text = CVVTB.getText();
        String regularExpressionPattern = "^[0-9]([.\\s\\-]?\\d){2}$";
        Pattern pattern = Pattern.compile(regularExpressionPattern);
        Matcher matcher = pattern.matcher(text);
        if (!matcher.matches()) {
            flag13 = false;
            CVVImage.setImage(new Image(getClass().getResourceAsStream("/Image/remove.png")));
        } else {
            flag13 = true;
            CVVImage.setImage(new Image(getClass().getResourceAsStream("/Image/accept.png")));
        }
    }

    @FXML
    void handleCityTB() {

        String text = CityTB.getText();
        String regularExpressionPattern = "^[a-zA-Z',.\\s-]{3,25}$";
        Pattern pattern = Pattern.compile(regularExpressionPattern);
        Matcher matcher = pattern.matcher(text);
        if (!matcher.matches()) {
            flag6 = false;
            CityImage.setImage(new Image(getClass().getResourceAsStream("/Image/remove.png")));
        } else {
            flag6 = true;
            CityImage.setImage(new Image(getClass().getResourceAsStream("/Image/accept.png")));
        }
    }

    @FXML
    void handleConfirmPasswordTB() {

        if (!ConfirmPasswordTB.getText().equals(PasswordTB.getText())) {
            flag5 = false;
            ConfirmPasswordImage.setImage(new Image(getClass().getResourceAsStream("/Image/remove.png")));
        } else {
            flag5 = true;
            ConfirmPasswordImage.setImage(new Image(getClass().getResourceAsStream("/Image/accept.png")));
        }
    }

    @FXML
    void handleCreditCardHolderTB() {

        String text = CreditCardHolderTB.getText();
        String regularExpressionPattern = "^[a-zA-Z'\\s-]{2,25}$";
        Pattern pattern = Pattern.compile(regularExpressionPattern);
        Matcher matcher = pattern.matcher(text);
        if (!matcher.matches()) {
            flag11 = false;
            CreditCardHolderImage.setImage(new Image(getClass().getResourceAsStream("/Image/remove.png")));
        } else {
            flag11 = true;
            CreditCardHolderImage.setImage(new Image(getClass().getResourceAsStream("/Image/accept.png")));
        }
    }

    @FXML
    void handleCreditCardNumberTB() {

        String text = CreditCardNumberTB.getText();
        if (text.startsWith("4")) {
            CreditCardNoCardImage.setImage(new Image(getClass().getResourceAsStream("/Image/visa.png")));
        } else if (text.startsWith("37") || text.startsWith("34")) {
            CreditCardNoCardImage.setImage(new Image(getClass().getResourceAsStream("/Image/american_express.png")));
        } else if (text.startsWith("3")) {
            CreditCardNoCardImage.setImage(new Image(getClass().getResourceAsStream("/Image/master_card.png")));
        } else if (text.startsWith("6")) {
            CreditCardNoCardImage.setImage(new Image(getClass().getResourceAsStream("/Image/discover.png")));
        } else {
            flag10 = false;
            CreditCardNoCardImage.setImage(null);
            CreditCardNumberImage.setImage(new Image(getClass().getResourceAsStream("/Image/remove.png")));
        }

        if (text.startsWith("37") || text.startsWith("34")) {
            String regularExpressionPattern = "^[0-9]([.\\s\\-]?\\d){14}$";
            Pattern pattern = Pattern.compile(regularExpressionPattern);
            Matcher matcher = pattern.matcher(text);
            if (matcher.matches() || text.isBlank()) {
                flag10 = true;
                CreditCardNumberImage.setImage(new Image(getClass().getResourceAsStream("/Image/accept.png")));
            } else {
                flag10 = false;
                CreditCardNumberImage.setImage(new Image(getClass().getResourceAsStream("/Image/remove.png")));
            }
        } else if (text.startsWith("4") || text.startsWith("3") || text.startsWith("6")) {
            String regularExpressionPattern = "^[0-9]([.\\s\\-]?\\d){15}$";
            Pattern pattern = Pattern.compile(regularExpressionPattern);
            Matcher matcher = pattern.matcher(text);
            if (matcher.matches() || text.isBlank()) {
                flag10 = true;
                CreditCardNumberImage.setImage(new Image(getClass().getResourceAsStream("/Image/accept.png")));
            } else {
                flag10 = false;
                CreditCardNumberImage.setImage(new Image(getClass().getResourceAsStream("/Image/remove.png")));
            }
        } else {
            flag10 = false;
            CreditCardNumberImage.setImage(new Image(getClass().getResourceAsStream("/Image/remove.png")));
        }
    }

    @FXML
    void handleEmailTB() {

        userDetailsErrorLabel.setVisible(false);
        String text = EmailTB.getText();
        String regularExpressionPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regularExpressionPattern);
        Matcher matcher = pattern.matcher(text);

        if (!matcher.matches()) {
            flag2 = false;
            EmailImage.setImage(new Image(getClass().getResourceAsStream("/Image/remove.png")));
        } else {
            flag2 = true;
            EmailImage.setImage(new Image(getClass().getResourceAsStream("/Image/accept.png")));
        }
    }

    @FXML
    void handleExpDateMonthCB() {
        ExpDateMonthCB.show();
    }

    @FXML
    void handleExpDateMonthCBSelected() {

        if (ExpDateYearCB.getSelectedItem() == null) {
            flag12 = false;
            ExpDateImage.setImage(new Image(getClass().getResourceAsStream("/Image/remove.png")));
        } else {
            flag12 = true;
            ExpDateImage.setImage(new Image(getClass().getResourceAsStream("/Image/accept.png")));
        }
    }

    @FXML
    void handleExpDateYearCB() {
        ExpDateYearCB.show();
    }

    @FXML
    void handleExpDateYearCBSelected() {

        if (ExpDateMonthCB.getSelectedItem() == null) {
            flag12 = false;
            ExpDateImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Image/remove.png"))));
        } else {
            flag12 = true;
            ExpDateImage.setImage(new Image(getClass().getResourceAsStream("/Image/accept.png")));
        }
    }

    @FXML
    void handleHomeSignUp() throws IOException {
        App.setRoot("primary", "/Image/mainPageIcon.png", "Lilac");
    }

    @FXML
    void handlePOBoxTB() {

        String text = POBoxTB.getText();
        String regularExpressionPattern = "^[0-9]([.\\s\\-]?\\d){3}$";
        Pattern pattern = Pattern.compile(regularExpressionPattern);
        Matcher matcher = pattern.matcher(text);
        if (matcher.matches() || text.isBlank()) {
            flag9 = true;
            POBoxImage.setImage(new Image(getClass().getResourceAsStream("/Image/accept.png")));
        } else {
            flag9 = false;
            POBoxImage.setImage(new Image(getClass().getResourceAsStream("/Image/remove.png")));
        }
    }

    @FXML
    void handlePasswordQuestionMarkEnter() {
        showLabelPassword.setVisible(true);
    }

    @FXML
    void handlePasswordQuestionMarkExit() {
        showLabelPassword.setVisible(false);
    }

    @FXML
    void handlePasswordTB() {

        String text = PasswordTB.getText();
        String regularExpressionPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~$^+=<>]).{8,24}$";
        Pattern pattern = Pattern.compile(regularExpressionPattern);
        Matcher matcher = pattern.matcher(text);
        if (!matcher.matches()) {
            flag4 = false;
            PasswordImage.setImage(new Image(getClass().getResourceAsStream("/Image/remove.png")));
        } else {
            flag4 = true;
            PasswordImage.setImage(new Image(getClass().getResourceAsStream("/Image/accept.png")));
        }
    }

    @FXML
    void handlePhoneCodeList() {
        phoneCodeList.show();
    }

    @FXML
    void handlePhoneCodeListSelected() {
        PhoneTB.setDisable(false);
    }

    @FXML
    void handlePhoneTB() {

        // To get the selected item of the drop down list use phoneCodeList.getSelectedItem()
        if (phoneCodeList.getSelectedItem() == null) {
            flag3 = false;
            PhoneImage.setImage(new Image(getClass().getResourceAsStream("/Image/remove.png")));
            PhoneTB.setDisable(true);
            return;
        }
        String text = PhoneTB.getText();
        String regularExpressionPattern = "^[0-9]([.\\s\\-]?\\d){6}$";
        Pattern pattern = Pattern.compile(regularExpressionPattern);
        Matcher matcher = pattern.matcher(text);
        if (!matcher.matches()) {
            flag3 = false;
            PhoneImage.setImage(new Image(getClass().getResourceAsStream("/Image/remove.png")));
        } else {
            flag3 = true;
            PhoneImage.setImage(new Image(getClass().getResourceAsStream("/Image/accept.png")));
        }
    }

    @FXML
    void handleSignUpButton() {
        handleUserNameTB();
        handleEmailTB();
        handlePhoneCodeListSelected();
        handlePhoneTB();
        handlePasswordTB();
        handleConfirmPasswordTB();
        handleCityTB();
        handleStreetTB();
        handleZIPCTB();
        handlePOBoxTB();
        handleCreditCardNumberTB();
        handleCreditCardHolderTB();
        handleExpDateMonthCBSelected();
        handleExpDateYearCBSelected();
        handleCVVTB();

        List<SignUp> signUpList = (List<SignUp>) msgObject.getObject();
        if (!signUpList.isEmpty()) {
            for (SignUp signUp : signUpList) {
                if (signUp.getUsername().equals(UsernameTB.getText())) {
                    userDetailsErrorLabel.setText("This username is already taken");
                    userDetailsErrorLabel.setVisible(true);
                    flag1 = false;
                    UserNameImage.setImage(new Image(getClass().getResourceAsStream("/Image/remove.png")));
                    return;
                }
                if (signUp.getEmail().equals(EmailTB.getText())) {
                    userDetailsErrorLabel.setText("This email is already taken");
                    userDetailsErrorLabel.setVisible(true);
                    flag2 = false;
                    EmailImage.setImage(new Image(getClass().getResourceAsStream("/Image/remove.png")));
                    return;
                }
            }
        }

        if (flag1 && flag2 && flag3 && flag4 && flag5 && flag6 && flag7 && flag8 && flag9 &&
                flag10 && flag11 && flag12 && flag13) {
            successLabel1.setText(firstLabel);
            successLabel2.setText(secondLabel);
            successLabel3.setText(thirdLabel);

            successLabel1.setVisible(true);
            successLabel2.setVisible(true);
            successLabel3.setVisible(true);

            UsernameTB.setDisable(true);
            EmailTB.setDisable(true);
            PhoneTB.setDisable(true);
            phoneCodeList.setDisable(true);
            PasswordTB.setDisable(true);
            ConfirmPasswordTB.setDisable(true);
            CityTB.setDisable(true);
            StreetTB.setDisable(true);
            ZIPCTB.setDisable(true);
            POBoxTB.setDisable(true);
            CreditCardNumberTB.setDisable(true);
            CreditCardHolderTB.setDisable(true);
            ExpDateMonthCB.setDisable(true);
            ExpDateYearCB.setDisable(true);
            CVVTB.setDisable(true);

            signup = new SignUp(globalAccountType, UsernameTB.getText(), EmailTB.getText(), phoneCodeList.getText() + PhoneTB.getText()
                    , PasswordTB.getText(), CityTB.getText() + " " + StreetTB.getText() + " " + ZIPCTB.getText() + " " + POBoxTB.getText(),
                    CreditCardNumberTB.getText(), CreditCardHolderTB.getText(), ExpDateYearCB.getText() + "/" + ExpDateMonthCB.getText(),
                    Integer.parseInt(CVVTB.getText()));
            signup.setCity(CityTB.getText());
            signup.setStreet(StreetTB.getText());
            signup.setZip(ZIPCTB.getText());
            if (POBoxTB.getText().equals("")) {
                signup.setPob("not added");
            } else {
                signup.setPob(POBoxTB.getText());
            }

            try {
                getClient().sendToServer(new MsgObject("addUser", signup));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            SignUpButton.setDisable(true);
        }
    }

    @FXML
    void handleStreetTB() {

        String text = StreetTB.getText();
        String regularExpressionPattern = "^[a-zA-Z0-9',.\\s-]{3,40}$";
        Pattern pattern = Pattern.compile(regularExpressionPattern);
        Matcher matcher = pattern.matcher(text);
        if (!matcher.matches()) {
            flag7 = false;
            StreetImage.setImage(new Image(getClass().getResourceAsStream("/Image/remove.png")));
        } else {
            flag7 = true;
            StreetImage.setImage(new Image(getClass().getResourceAsStream("/Image/accept.png")));
        }
    }

    @FXML
    void handleSuccessLabel2() throws IOException {
        MsgObject msgObject1 = new MsgObject("signIn");
        msgObject1.setUser(signup);
        getClient().sendToServer(msgObject1);
    }

    @FXML
    void handleUserNameQuestionMarkEnter() {
        showLabelUserName.setVisible(true);
    }

    @FXML
    void handleUserNameQuestionMarkExit() {
        showLabelUserName.setVisible(false);
    }

    @FXML
    void handleUserNameTB() {

        userDetailsErrorLabel.setVisible(false);
        String text = UsernameTB.getText();
        String regularExpressionPattern = "^(?=.{8,24}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";
        Pattern pattern = Pattern.compile(regularExpressionPattern);
        Matcher matcher = pattern.matcher(text);
        if (!matcher.matches()) { // Check here if the username exists in the database
            // I will add a label later to state that the username exists
            flag1 = false;
            UserNameImage.setImage(new Image(getClass().getResourceAsStream("/Image/remove.png")));
        } else {
            flag1 = true;
            UserNameImage.setImage(new Image(getClass().getResourceAsStream("/Image/accept.png")));
        }
    }

    @FXML
    void handleZIPCTB() {

        String text = ZIPCTB.getText();
        String regularExpressionPattern = "^[0-9]([.\\s\\-]?\\d){6}$";
        Pattern pattern = Pattern.compile(regularExpressionPattern);
        Matcher matcher = pattern.matcher(text);
        if (!matcher.matches()) {
            flag8 = false;
            ZIPImage.setImage(new Image(getClass().getResourceAsStream("/Image/remove.png")));
        } else {
            flag8 = true;
            ZIPImage.setImage(new Image(getClass().getResourceAsStream("/Image/accept.png")));
        }
    }

    @FXML
    void initialize() {

        LocalDate localDate = LocalDate.now();
        phoneCodeList.getItems().addAll("050", "052", "053", "054", "055", "058");
        int currentMonth = localDate.getMonth().getValue();
        for (int i = currentMonth; i <= 12; i++) {
            if (i <= 9) {
                ExpDateMonthCB.getItems().add("0" + i);
            } else {
                ExpDateMonthCB.getItems().add(String.valueOf(i));
            }
        }
        int currentYear = localDate.getYear();
        for (int i = 0; i < 7; i++) {
            ExpDateYearCB.getItems().add(String.valueOf(currentYear + i));
        }

        firstLabel = successLabel1.getText();
        secondLabel = successLabel2.getText();
        thirdLabel = successLabel3.getText();

        successLabel1.setText("");
        successLabel2.setText("");
        successLabel3.setText("");

        UserNameImage.setImage(null);
        EmailImage.setImage(null);
        PhoneImage.setImage(null);
        PasswordImage.setImage(null);
        ConfirmPasswordImage.setImage(null);
        CityImage.setImage(null);
        StreetImage.setImage(null);
        ZIPImage.setImage(null);
        POBoxImage.setImage(null);
        CreditCardNumberImage.setImage(null);
        CreditCardHolderImage.setImage(null);
        ExpDateImage.setImage(null);
        CVVImage.setImage(null);

        showLabelUserName.setVisible(false);
        showLabelPassword.setVisible(false);
        showLabelCVV.setVisible(false);

        PhoneTB.setDisable(true);
    }
}