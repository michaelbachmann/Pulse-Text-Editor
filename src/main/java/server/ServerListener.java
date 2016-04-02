package server;


import resource.ChatMessage;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ServerListener extends Thread {
    private ServerSocket ss;
    private Vector<ServerClientCommunicator> sccVector;
    private ChatMessage chatMessage;

    public ServerListener(ServerSocket ss) {
        this.ss = ss;
        sccVector = new Vector<ServerClientCommunicator>();
    }

    public void removeServerClientCommunicator(ServerClientCommunicator scc) {
        sccVector.remove(scc);
    }

    public void run() {
        try {
            while (true) {
                Socket s = ss.accept();
                ServerGUI.addMessage(Constants.START_CLIENT_CONNECTED_STRING + ss.getInetAddress());
                try {
                    ServerClientCommunicator scc = new ServerClientCommunicator(s, this);
                    scc.start();
                    sccVector.add(scc);
                } catch (IOException ioe) {
                    System.out.println(ioe.getMessage());
                }
            }
        } catch(BindException be) {
            ServerGUI.addMessage(be.getMessage());
        } catch (IOException ioe) {
            ServerGUI.addMessage(ioe.getMessage());
        } finally {
            if (ss != null) {
                try {
                    ServerGUI.addMessage(Constants.SERVER_STOPPED_STRING);
                    ss.close();
                } catch (IOException ioe) {
                    ServerGUI.addMessage(ioe.getMessage());
                }
            }
        }
    }

    public void sendMessage(ChatMessage chatMessage) {
        this.chatMessage = chatMessage;
        for (ServerClientCommunicator scc : sccVector) {
            scc.sendMessage(chatMessage);
        }
    }
}