package client.Views;

import uielements.ColorSet;
import uielements.Constantsssss;
import uielements.componentuis.FlatButtonUI;

import javax.swing.*;
import java.awt.*;

public class SplashPanel extends JPanel {
    JPanel rowOne, mainPanel;

    public JButton getLogin() {
        return login;
    }

    public JButton getOffline() {
        return offline;
    }

    public JButton getRegister() {
        return register;
    }

    JButton login, offline, register;
    ImageIcon icon_image;
    JLabel imageLabel, filler;
    GridBagConstraints gbc;


    public SplashPanel(){
        super();
        setLayout(new BorderLayout());
        instantiateVariables();
        createGUI();
        setBackground(ColorSet.DARKBLUE);
    }

    public void instantiateVariables() {
        icon_image = new ImageIcon(Constantsssss.PULSE_ICON);
        Image image = icon_image.getImage(); // transform it
        Image newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        icon_image = new ImageIcon(newimg);  // transform it back
        imageLabel = new JLabel(icon_image);
        filler = new JLabel("PuLse Text Editor");
        login = new JButton("Login");
        offline = new JButton("Offline");
        register = new JButton("Sign Up");

        mainPanel = new JPanel(new GridBagLayout());
        rowOne = new JPanel(new FlowLayout());
        gbc = new GridBagConstraints();

    }

    public void createGUI() {
        mainPanel.setBackground(ColorSet.EDITOR_BG_COLOR);
        rowOne.setBackground(ColorSet.EDITOR_BG_COLOR);
        login.setUI(new FlatButtonUI());
        register.setUI(new FlatButtonUI());
        offline.setUI(new FlatButtonUI());

        rowOne.add(login);
        rowOne.add(register);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.anchor = GridBagConstraints.CENTER;

        mainPanel.add(imageLabel,gbc);
        gbc.gridy = 1;
        mainPanel.add(filler,gbc);
        gbc.gridy = 2;
        mainPanel.add(rowOne, gbc);
        gbc.gridy = 3;
        mainPanel.add(offline, gbc);
        add(mainPanel, BorderLayout.CENTER);
    }

}
