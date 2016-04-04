package client;

import resources.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

// Client Listener class for network connection
public class ClientListener extends Thread {
    private int port;
    private String hostname;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Scanner scan;
    private Socket mSocket;
    private ClientGUI mClientGUI;


    public ClientListener(Socket socket) {
        mSocket = socket;
//        mClientGUI = clientGUI;
        boolean socketReady = initializeVariables();
        if (socketReady) {
            login(new User("Miguel", "pimp1"));
            start();
        }

//            try {
//                oos = new ObjectOutputStream(socket.getOutputStream());
//                ois = new ObjectInputStream(socket.getInputStream());
//                this.start();
//
//                scan = new Scanner(System.in);
//                while(true) {
////                    if (messageReady) {
////                        try {
////                            oos.writeObject(user);
////                            oos.flush();
////                        } catch (IOException ioe) {
////                            System.out.println("Failed To Login");
////                        }
////                        messageReady = false;
////                    }
//
//
//
//                    String line = scan.nextLine();
//                    if (line.equals("exit"))
//                        break;
//                    oos.writeObject(new User("Michaelllll", "adiaido"));
//                    oos.flush();
//                }
//            } catch (IOException ioe) {
//                System.out.println("ioe: " + ioe.getMessage());
//            } finally {
//                try {
//                    if (socket != null) {
//                        socket.close();
//                        System.out.println("Socket closed");
//                    }
//                    if (scan != null) {
//                        scan.close();
//                    }
//                } catch (IOException ioe) {
//                    System.out.println("ioe: " + ioe.getMessage());
//                }
//            }

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
//    public void sendMessage(String msg) {
//        pw.println(msg);
//        pw.flush();
//    }
//
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


