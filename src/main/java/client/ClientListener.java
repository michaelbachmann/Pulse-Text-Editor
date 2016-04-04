package client;

import resources.User;

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

    public ClientListener(User user, Socket socket) {
        mSocket = socket;
        this.user = user;
        boolean socketReady = initializeVariables();
        if (socketReady) {
            login(user);
            start();
        }
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

    public void login(User user) {
        try {
            oos.writeObject(user);
            oos.flush();
        } catch (IOException ioe) {
            System.out.println("Failed To Login");
        }
    }

    public void run() {
        try {
            while (true) {
                User user = (User)ois.readObject();
                System.out.println(user.getUsername() + " Pass: " + user.getPassword() );
            }
        } catch (ClassNotFoundException cnfe) {
            System.out.println("cnfe: " + cnfe.getMessage());
        } catch (IOException ioe) {
            System.out.println("ioe: " + ioe.getMessage());
        }
    }
}


