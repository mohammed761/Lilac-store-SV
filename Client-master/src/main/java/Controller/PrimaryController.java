package Controller;

import il.ac.haifa.cs.sweng.OCSFSimpleChat.MsgObject;
import javafx.fxml.FXML;

import java.io.IOException;

import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.getClient;

public class PrimaryController {

    @FXML
    void SignUpHandle() throws IOException {
        getClient().sendToServer(new MsgObject("signUpAccountType"));
    }

    @FXML
    void catalogHandle() throws IOException {
        getClient().sendToServer(new MsgObject("Catalog"));
    }

    @FXML
    void contactUsHandle() throws IOException {
        getClient().sendToServer(new MsgObject("contactUs"));
    }

    @FXML
    void signInHandle() throws IOException {
        getClient().sendToServer(new MsgObject("signIn"));
    }

    @FXML
    void initialize() {

    }
}
