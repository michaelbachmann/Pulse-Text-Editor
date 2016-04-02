package server;

import resource.ChatMessage;

import java.io.*;
import java.net.Socket;


public class ServerClientCommunicator extends Thread {
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private ServerListener serverListener;
    public boolean closecommunication = true;

    public ServerClientCommunicator(Socket socket, ServerListener serverListener) throws IOException {
        this.socket = socket;
        this.serverListener = serverListener;
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        this.ois = new ObjectInputStream(socket.getInputStream());
    }

    public void sendMessage(ChatMessage chatMessage) {
        try {
            oos.writeObject(chatMessage);
            oos.flush();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public void run() {
        try {
            while (true) {
                ChatMessage message = (ChatMessage) ois.readObject();
            }
//            ChatMessage message = (ChatMessage) ois.readObject();
//            cs.sendMessageToAllClients(message);
//            ServerGUI.addMessage(socket.getInetAddress() + ":" + socket.getPort() + " - " + message.getMessage());
//            String line = br.readLine();
//            while (line != null) {
//                ServerGUI.addMessage(socket.getInetAddress() + ":" + socket.getPort() + " - " + line);
//                line = br.readLine();
//            }
//            while (closecommunication) {
//
//            }
        } catch (ClassNotFoundException cnfe) {
            System.out.println("cnfe in run: " + cnfe.getMessage());
        } catch (IOException ioe) {
            System.out.println("Disconnected");
            serverListener.removeServerClientCommunicator(this);
            ServerGUI.addMessage(socket.getInetAddress() + ":" + socket.getPort() + " - " + Constants.CLIENT_DISCONNETED_STRING);
            // socket closed
            try {
                socket.close();
            } catch (IOException ioe1) {
                System.out.println(ioe1.getMessage());
            }
        }
    }


}
