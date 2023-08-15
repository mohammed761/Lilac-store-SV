package Controller;

import il.ac.haifa.cs.sweng.OCSFSimpleChat.App;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.MsgObject;
import il.ac.haifa.cs.sweng.OCSFSimpleChat.SignUp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.getClient;
import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.msgObject;

public class EditUsersManagerController {

    @FXML
    private Label totalOrdersLabel;

    @FXML
    private VBox vbox;

    List<SignUp> signUpList = (List<SignUp>) msgObject.getObject();

    public static boolean addFlag;

    @FXML
    void handleAddUser() throws IOException {
        addFlag = true;
        getClient().sendToServer(new MsgObject("editUserManager"));
    }

    @FXML
    void handleHome() throws IOException {
        App.setRoot("primaryManager", "/Image/managerIcon.png", "System Manager");
    }

    @FXML
    void initialize() throws IOException {

        if (!signUpList.isEmpty()) {
            int counter = 0;
            for (SignUp signUp : signUpList) {
                if (signUp.getAccountType().equals("system manager")) {
                    counter++;
                }
            }
            if (counter == 1) {
                for (SignUp signUp : signUpList) {
                    if (signUp.getAccountType().equals("system manager")) {
                        signUpList.remove(signUp);
                        break;
                    }
                }
            }
        }

        if (!signUpList.isEmpty()) {
            for (SignUp signUp : signUpList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("editUsersItemManager.fxml"));
                Node node = fxmlLoader.load();
                EditUsersItemManagerController editUsersItemManagerController = fxmlLoader.getController();
                editUsersItemManagerController.setData(signUp);
                vbox.getChildren().add(node);
            }
            totalOrdersLabel.setText("Total Users: " + signUpList.size());
        }
    }
}
