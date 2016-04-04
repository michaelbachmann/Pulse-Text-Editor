package server;


import resources.ChatMessage;
import utilities.ConfigureSettings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;

public class PulseServer {

    private ServerSocket ss = null;
    private static ServerListener serverListener;
    private int port;
    private boolean listening;
    private ServerGUI serverGUI;

    public void sendToServer(ChatMessage message) {
            System.out.println("We got "+message);
    }

    public PulseServer() {
        listening = false;
        initializeApplication();
    }

    private void initializeApplication() {
        // Setup Port
        port = ConfigureSettings.readPort(ConfigureSettings.getSetings(server.Constants.CONFIG_FILE));  // try setting port
        if (!(port > utilities.Constants.LOW_PORT && port < utilities.Constants.HIGH_PORT)) {
            System.exit(5); // check if in use????
        }
        // Setup GUI
        serverGUI = new ServerGUI();
        serverGUI.getStartStopButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listening) {
                    serverGUI.getStartStopButton().setText("Start");
                    stopListening();
                } else {
                    serverGUI.getStartStopButton().setText("Stop");
                    startListening();
                }
            }
        });

    }

    private void startListening() {
        try {
            ss = new ServerSocket(port);
            ss.setReuseAddress(true);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        listening = true;
        serverListener = new ServerListener(ss);
        ServerGUI.addMessage("Server started on Port: " + port);
        serverListener.start();
    }
    private void stopListening() {
        try {
            ss.close();
            listening = false;
            serverListener = null;
        } catch (IOException ioe) {
            ServerGUI.addMessage(Constants.SERVER_STOPPED_STRING);
        }

    }

    // Main
    public static void main(String[] args) {
        new PulseServer();
    }

    // Send Message
    public static void sendMessage(ChatMessage message) {
        if (serverListener != null) {
            serverListener.sendMessage(message);
        }
    }


}
