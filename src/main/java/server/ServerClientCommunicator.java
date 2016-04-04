package server;

import resources.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

// Thread that that manages connection with user
public class ServerClientCommunicator extends Thread {
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private ServerListener serverListener;

    public ServerClientCommunicator(Socket socket, ServerListener serverListener) throws IOException {
        this.socket = socket;
        this.serverListener = serverListener;
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        this.ois = new ObjectInputStream(socket.getInputStream());
    }

    public void sendUser(User user) {
        try {
            oos.writeObject(user);
            oos.flush();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public void requestStop(){
        try {
            ServerGUI.addMessage("Socket closed....");
            socket.close();
        } catch (IOException ioe1) {
            ServerGUI.addMessage("io1: " + ioe1.getMessage());
        }
    }

    public void run() {
        try {
            while (true) {
                User user = (User) ois.readObject();
                ServerGUI.addMessage("Login from: " + user.getUsername() + " Password: " + user.getPassword());
            }
        } catch (ClassNotFoundException cnfe) {
            System.out.println("cnfe in run: " + cnfe.getMessage());
        } catch (IOException ioe) {
            System.out.println("Disconnected");
            serverListener.removeServerClientCommunicator(this);
            ServerGUI.addMessage(socket.getInetAddress() + ":" + socket.getPort() + " - " + Constants.CLIENT_DISCONNETED_STRING);
            // socket closed
//            try {
//                ServerGUI.addMessage("Socket closed....");
//                socket.close();
//            } catch (IOException ioe1) {
//                ServerGUI.addMessage("io1: " + ioe1.getMessage());
//            }
        }
    }


}
