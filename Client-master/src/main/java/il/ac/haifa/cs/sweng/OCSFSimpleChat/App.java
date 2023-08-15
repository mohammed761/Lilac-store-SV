package il.ac.haifa.cs.sweng.OCSFSimpleChat;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static Controller.SignInController.user;
import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleClient.getClient;

public class App extends Application {
    public static final String CURRENCY = "₪";
    private static Scene scene;
    private static SimpleClient client;
    private static Stage mainStage;

    @Override
    public void start(Stage stage) throws Exception {
        client = SimpleClient.getClient();
        client.openConnection();
        scene = new Scene(loadFXML("primary"));
        stage.setScene(scene);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Image/mainPageIcon.png"))));
        stage.setTitle("Lilac");
        stage.setResizable(false);
        mainStage = stage;
        mainStage.show();
    }

    @Override
    public void stop() throws IOException {
        if(user != null) {
            user.setSignedIn(false);
            getClient().sendToServer(new MsgObject("exit", user));
        }
        try {
            super.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void setRoot(String fxml, String image, String title) throws IOException {
        scene.setRoot(loadFXML(fxml));
        mainStage.getIcons().clear();
        mainStage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream(image))));
        mainStage.setTitle(title);
        mainStage.show();
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args){
        launch();
        try {
            client.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        client.connectionClosed();
    }
}

