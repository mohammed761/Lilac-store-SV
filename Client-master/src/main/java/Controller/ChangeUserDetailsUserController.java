package Controller;

import com.jfoenix.controls.JFXButton;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.App;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.MsgObject;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.SignUp;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Controller.SignInController.user;
import static Controller.SignUpAccountTypeController.globalAccountType;
import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.getClient;
import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.msgObject;

public class ChangeUserDetailsUserController {

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
    private JFXButton cancelButton;

    @FXML
    private MFXComboBox<String> phoneCodeList;

    @FXML
    private JFXButton saveButton;

    @FXML
    private Label showLabelCVV;

    @FXML
    private Label showLabelPassword;

    @FXML
    private Label showLabelUserName;

    @FXML
    private Label userDetailsErrorLabel;

    @FXML
    private Label successLabel;

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
            CVVImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Image/remove.png"))));
        } else {
            flag13 = true;
            CVVImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Image/accept.png"))));
        }
    }

    @FXML
    void handleCancelButton() throws IOException {
        App.setRoot("primaryUser", "/Image/mainPageIcon.png", "Lilac");
    }

    @FXML
    void handleCityTB() {

        String text = CityTB.getText();
        String regularExpressionPattern = "^[a-zA-Z',.\\s-]{3,25}$";
        Pattern pattern = Pattern.compile(regularExpressionPattern);
        Matcher matcher = pattern.matcher(text);
        if (!matcher.matches()) {
            flag6 = false;
            CityImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Image/remove.png"))));
        } else {
            flag6 = true;
            CityImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Image/accept.png"))));
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

        flag12 = true;
        ExpDateImage.setImage(new Image(getClass().getResourceAsStream("/Image/accept.png")));
    }

    @FXML
    void handleExpDateYearCB() {
        ExpDateYearCB.show();
    }

    @FXML
    void handleExpDateYearCBSelected() {

        flag12 = true;
        ExpDateImage.setImage(new Image(getClass().getResourceAsStream("/Image/accept.png")));
    }

    @FXML
    void handleHomeSignUp() throws IOException {
        App.setRoot("primaryUser", "/Image/mainPageIcon.png", "Lilac");
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
    }

    @FXML
    void handlePhoneTB() {

        // To get the selected item of the drop down list use phoneCodeList.getSelectedItem()
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
    void handleSaveButton() {

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
                if (signUp.getUsername().equals(UsernameTB.getText()) && !signUp.getUsername().equals(user.getUsername())) {
                    userDetailsErrorLabel.setText("This username is already taken");
                    userDetailsErrorLabel.setVisible(true);
                    flag1 = false;
                    UserNameImage.setImage(new Image(getClass().getResourceAsStream("/Image/remove.png")));
                    return;
                }
                if (signUp.getEmail().equals(EmailTB.getText()) && !signUp.getEmail().equals(user.getEmail())) {
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

            SignUp signup = new SignUp(globalAccountType, UsernameTB.getText(), EmailTB.getText(), phoneCodeList.getText() + PhoneTB.getText()
                    , PasswordTB.getText(), CityTB.getText() + " " + StreetTB.getText() + " " + ZIPCTB.getText() + " " + POBoxTB.getText(),
                    CreditCardNumberTB.getText(), CreditCardHolderTB.getText(), ExpDateYearCB.getText() + "/" + ExpDateMonthCB.getText(),
                    Integer.parseInt(CVVTB.getText()));
            signup.setAccountType(user.getAccountType());
            signup.setId(user.getId());
            signup.setSignedIn(user.isSignedIn());
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
            cancelButton.setDisable(true);
            saveButton.setDisable(true);
            successLabel.setVisible(true);
            user = signup;
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
    //there is an error when the client try to change his ZIP CODE
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

        UserNameImage.setImage(new Image(getClass().getResourceAsStream("/Image/accept.png")));
        EmailImage.setImage(new Image(getClass().getResourceAsStream("/Image/accept.png")));
        PhoneImage.setImage(new Image(getClass().getResourceAsStream("/Image/accept.png")));
        PasswordImage.setImage(new Image(getClass().getResourceAsStream("/Image/accept.png")));
        ConfirmPasswordImage.setImage(new Image(getClass().getResourceAsStream("/Image/accept.png")));
        CityImage.setImage(new Image(getClass().getResourceAsStream("/Image/accept.png")));
        StreetImage.setImage(new Image(getClass().getResourceAsStream("/Image/accept.png")));
        ZIPImage.setImage(new Image(getClass().getResourceAsStream("/Image/accept.png")));
        POBoxImage.setImage(new Image(getClass().getResourceAsStream("/Image/accept.png")));
        CreditCardNumberImage.setImage(new Image(getClass().getResourceAsStream("/Image/accept.png")));
        CreditCardHolderImage.setImage(new Image(getClass().getResourceAsStream("/Image/accept.png")));
        ExpDateImage.setImage(new Image(getClass().getResourceAsStream("/Image/accept.png")));
        CVVImage.setImage(new Image(getClass().getResourceAsStream("/Image/accept.png")));
        flag1 = flag2 = flag3 = flag4 = flag5 = flag6 = flag7 = flag8 = flag9 = flag10 = flag11 = flag12 = flag13 = true;

        UsernameTB.setText(user.getUsername());
        EmailTB.setText(user.getEmail());
        phoneCodeList.setText(user.getPhone().substring(0, 3));
        PhoneTB.setText(user.getPhone().substring(3));
        PasswordTB.setText(user.getPassword());
        ConfirmPasswordTB.setText(user.getPassword());
        CityTB.setText(user.getCity());
        StreetTB.setText(user.getStreet());
        ZIPCTB.setText(user.getZip());
        POBoxTB.setText(user.getPob());
        CreditCardNumberTB.setText(user.getCreditCard());
        CreditCardHolderTB.setText(user.getHolderOfCard());
        ExpDateMonthCB.setText(user.getDate().substring(5));
        ExpDateYearCB.setText(user.getDate().substring(0, 4));
        CVVTB.setText(String.valueOf(user.getCvv()));


        showLabelUserName.setVisible(false);
        showLabelPassword.setVisible(false);
        showLabelCVV.setVisible(false);
    }
}
