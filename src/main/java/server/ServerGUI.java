package server;

import uielements.ColorSet;
import uielements.componentuis.FlatButtonUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;

// GUI for server application
public class ServerGUI extends JFrame {
    // MARK: Member Variables
    private JScrollPane scrollPane;
    private static JTextArea log;
    private JButton startStopButton;
    private int port;
    private boolean listening;

    private ServerSocket ss = null;
    private static ServerListener serverListener;


    public ServerGUI(int port){
        super(Constants.SERVER_NAME_STRING);
        listening = false;
        this.port = port;
        setSize(500,500);
        setLocationRelativeTo(null);
        initializeVariables();
        createGUI();
        addActions();
        setVisible(true);
    }



    public void initializeVariables() {
        scrollPane = new JScrollPane();
        log = new JTextArea();
        startStopButton = new JButton("Start");
    }

    public void createGUI() {
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUI(new FlatButtonUI.FlatScrollBarUI());
        scrollPane.getViewport().add(log);
        log.setSelectionColor(ColorSet.HOVER_COLOR);
        log.setBackground(ColorSet.DARKER_BLUE);
        log.setForeground(ColorSet.BEIGE);
        log.setLineWrap(true);
        log.setMargin(new Insets(10,10,10,10) );
        log.setEditable(false);
        startStopButton.setUI(new FlatButtonUI());
        add(scrollPane, BorderLayout.CENTER);
        add(startStopButton, BorderLayout.SOUTH);
    }

    public void addActions() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        startStopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!listening) {
                    startStopButton.setText("Stop");
                    listening = true;
                    startListening();
                } else {
                    startStopButton.setText("Start");
                    listening = false;
                    stopListening();
                }
            }
        });

//            try {
//                ServerSocket tempss = new ServerSocket(port);
//                portLock.lock();
//                ss = tempss;
//                portCondition.signal();
//                portLock.unlock();
//                PortGUI.this.setVisible(false);
//            } catch (IOException ioe) {
//                // this will get thrown if I can't bind to portNumber
//                portErrorLabel.setText(Constants.portAlreadyInUseString);
//            }


    }

    public void startListening() {
        try {
            ss = new ServerSocket(port);
            serverListener = new ServerListener(ss);
            serverListener.start();
            ServerGUI.addMessage(Constants.SERVER_STARTED_STRING + port);
        } catch (IOException ioe) {
            ServerGUI.addMessage("Error: ioe start listening - " + ioe.getMessage() );
        }
    }

    public void stopListening() {
        try {
            serverListener.requestStop();
            ss.close();
            serverListener.interrupt();
            ServerGUI.addMessage(Constants.SERVER_STOPPED_STRING + " fasho");
        } catch (IOException ioe) {
            ServerGUI.addMessage("Error: ioe stop listening - " + ioe.getMessage());
        }
    }

    public static void addMessage(String message) {
        if (log.getText() != null && log.getText().trim().length() > 0) {
            log.append("\n" + message);
        } else {
            log.setText(message);
        }
    }

}
