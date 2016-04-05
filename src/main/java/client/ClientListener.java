package client;

import resources.Document;
import resources.DocFile;
import resources.User;
import spellchecker.SpellCheckManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

// Client Listener class for network connection
public class ClientListener extends Thread {
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Socket mSocket;
    private User user;
    private Document doc;

    public ClientListener(Socket socket) {
        doc = new Document(new SpellCheckManager());
        mSocket = socket;
        this.user = user;
        boolean socketReady = initializeVariables();
//        if (socketReady) {
//            login(user);
//            sendDoc(doc);
//            start();
//        }
    }

    private boolean initializeVariables() {
        try {
            ois = new ObjectInputStream(mSocket.getInputStream());
            oos = new ObjectOutputStream(mSocket.getOutputStream());
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            return false;
        }
        return true;
    }

    public void sendUser(User user) {
        try {
            oos.writeObject(user);
            oos.flush();
        } catch (IOException ioe) {
            System.out.println("Failed To Login");
        }
    }

    public void sendDoc(DocFile doc) {
        try {
            oos.writeObject(doc);
            oos.flush();
        } catch (IOException ioe) {
            System.out.println("Failed To send doc");
        }
    }
    public void run() {
        try {
            while (true) {
                User user = (User)ois.readObject();
                System.out.println(user);
            }
        } catch (ClassNotFoundException cnfe) {
            System.out.println("cnfe: " + cnfe.getMessage());
        } catch (IOException ioe) {
            System.out.println("ioe: " + ioe.getMessage());
        }
    }
}


