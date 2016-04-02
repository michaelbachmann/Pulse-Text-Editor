package server;

import uielements.ColorSet;
import uielements.FlatButtonUI;
import uielements.FlatScrollBarUI;

import javax.swing.*;
import java.awt.*;

// GUI for server application
public class ServerGUI extends JFrame {
    // MARK: Member Variables
    private JScrollPane scrollPane;
    private static JTextArea log;
    private JButton startStopButton;

    public ServerGUI(){
        super(Constants.SERVER_NAME_STRING);
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
        scrollPane.getVerticalScrollBar().setUI(new FlatScrollBarUI());
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
    }

    public static void addMessage(String message) {
        if (log.getText() != null && log.getText().trim().length() > 0) {
            log.append("\n" + message);
        } else {
            log.setText(message);
        }
    }

    public JButton getStartStopButton() {
        return startStopButton;
    }

    public void setStartStopButton(JButton startStopButton) {
        this.startStopButton = startStopButton;
    }

}
