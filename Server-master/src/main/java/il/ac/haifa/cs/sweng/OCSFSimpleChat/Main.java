package il.ac.haifa.cs.sweng.OCSFSimpleChat;

import java.io.IOException;

import static il.ac.haifa.cs.sweng.OCSFSimpleChat.SimpleServer.addToDataBase;

public class Main {
    private static SimpleServer server;

    public static void main(String[] args){
        server = new SimpleServer(3000);

        addToDataBase();

        try {
            server.listen();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("I'm listening");

    }
}
