package server;


import resources.ChatMessage;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ServerListener extends Thread {
    private ServerSocket ss;
    private Vector<ServerClientCommunicator> sccVector;
    private ChatMessage chatMessage;

    public void requestStop() {
        try {
            ss.close();
            ServerGUI.addMessage("Request Stop Close");
        } catch (IOException ioe) {
            ServerGUI.addMessage("Request Stop IOE - " + ioe.getMessage());
        }
        for (ServerClientCommunicator sc: sccVector) {
            sc.requestStop();
        }
    }

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
                ServerGUI.addMessage(Constants.START_CLIENT_CONNECTED_STRING + s.getInetAddress() + Constants.END_CLIENT_CONNECTED_STRING);
                try {
                    // this line can throw an IOException
                    // if it does, we won't start the thread
                    ServerClientCommunicator scc = new ServerClientCommunicator(s, this);
                    scc.start();
                    sccVector.add(scc);
                } catch (IOException ioe) {
                    ServerGUI.addMessage("Server Listener IOE: " + ioe.getMessage());
                }
            }
        } catch(BindException be) {
            ServerGUI.addMessage("Server Listener BindException - " + be.getMessage());
        } catch (IOException ioe) {
            ServerGUI.addMessage("Server Listener: No Longer Accepting Messages -  IOE: " + ioe.getMessage());
        } finally {
            if (ss != null) {
                try {
                    ServerGUI.addMessage("Server Listener - " + Constants.SERVER_STOPPED_STRING);
                    ss.close();
                } catch (IOException ioe) {
                    ServerGUI.addMessage("Error closing Server Listener Thread " + ioe.getMessage());
                }
            }
        }
    }

//    public void sendMessage(User user) {
//        this.chatMessage = chatMessage;
//        for (ServerClientCommunicator scc : sccVector) {
//            scc.sendUser(chatMessage);
//        }
//    }
}