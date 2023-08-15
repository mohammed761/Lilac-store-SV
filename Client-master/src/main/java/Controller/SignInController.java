package Controller;

import il.ac.haifa.cs.sweng.OCSFSimpleChat.MsgObject;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.SignUp;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.App;

import java.io.IOException;
import java.util.List;

import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.getClient;
import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.msgObject;

// In handleSignInButton check what's written
// we need to check if the user trying to connect to the server from two computers or from two clients
public class SignInController {

    public static String userName;

    public static SignUp user;

    public static String rank;

    @FXML
    private Label errorMessageLabel;

    @FXML
    private MFXPasswordField passwordTB;

    @FXML
    private MFXTextField userNameTB;

    @FXML
    void handleContactUs() throws IOException {
        getClient().sendToServer(new MsgObject("contactUs"));
    }

    @FXML
    void handleHomeSignIn() throws IOException {
        App.setRoot("primary", "/Image/mainPageIcon.png", "Lilac");
    }

    @FXML
    void handleSignInButton() throws IOException {
        // I do believe we need to send the details to the server and the server should check if the user is on the line or not
        //because here we can just in both client click at the same the then we have all the users are not in, so we can sign
        //in two clients with the same email
        //if we send to the server, we will send it when we click on sign in and in the server we will check if this user are in
        //and will check if the user is in the database then the server will redirect him to the correct page
        userName = userNameTB.getText();
        List<SignUp> signup = (List<SignUp>) msgObject.getObject();

        if (!signup.isEmpty()) {
            for (SignUp usr : signup) {
                if (((usr.getEmail().equals(userName) && usr.getPassword().equals(passwordTB.getText())) ||
                usr.getUsername().equals(userName) && usr.getPassword().equals(passwordTB.getText())) && !usr.isSignedIn()) {
                    user = usr;
                    if (usr.isBanned()) {
                        getClient().sendToServer(new MsgObject("signIn"));
                        return;
                    }
                    user.setSignedIn(true);
                    userName = usr.getUsername();
                    rank = usr.getAccountType();
                    break;
                }
            }
        }
        getClient().sendToServer(new MsgObject("signInButton", userName + passwordTB.getText()));

    }

    @FXML
    void handleSignUpButton() throws IOException {
        getClient().sendToServer(new MsgObject("signUpAccountType"));
    }

    @FXML
    void handleUserNameEnter() throws IOException {
        handleSignInButton();
    }

    @FXML
    void handlePasswordEnter() throws IOException {
        handleSignInButton();
    }

    @FXML
    void initialize() {
        errorMessageLabel.setVisible(false);
        if (user != null) {
            if (user.isBanned()) {
                errorMessageLabel.setText("You are banned. Contact us for more information.");
                errorMessageLabel.setVisible(true);
                user = null;
                return;
            }
        }
        if(msgObject.isIn() == 2) {
            errorMessageLabel.setText("Your email is signed in, please make sure to logout from other clients.");
            errorMessageLabel.setVisible(true);
        }
        else if(msgObject.isIn() == 1){
            errorMessageLabel.setText("The username / password is wrong, please try again.");
            errorMessageLabel.setVisible(true);
        }
    }
}