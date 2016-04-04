package client.Views;

import uielements.ColorSet;
import uielements.Constantsssss;
import uielements.componentuis.FlatButtonUI;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    JPanel rowOne, rowTwo, mainPanel;
    JButton login;
    ImageIcon icon_image;
    JLabel imageLabel, usernameLabel, passwordLabel;
    JTextField usernameField, passwordField;
    GridBagConstraints gbc;

    public JButton getLogin() {
        return login;
    }

    public LoginPanel(){
        super();
        setLayout(new BorderLayout());
        instantiateVariables();
        createGUI();
        setBackground(ColorSet.DARKBLUE);
    }

    public void instantiateVariables() {
        usernameLabel = new JLabel("Username: ");
        passwordLabel = new JLabel("Password: ");
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        icon_image = new ImageIcon(Constantsssss.PULSE_ICON);
        Image image = icon_image.getImage(); // transform it
        Image newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        icon_image = new ImageIcon(newimg);  // transform it back
        imageLabel = new JLabel(icon_image);
        login = new JButton("Login");
        mainPanel = new JPanel(new GridBagLayout());
        rowOne = new JPanel(new FlowLayout());
        rowTwo = new JPanel(new FlowLayout());
        gbc = new GridBagConstraints();

    }

    public void createGUI() {
        mainPanel.setBackground(ColorSet.EDITOR_BG_COLOR);
        rowOne.setBackground(ColorSet.EDITOR_BG_COLOR);
        rowTwo.setBackground(ColorSet.EDITOR_BG_COLOR);
        login.setUI(new FlatButtonUI());


        rowOne.add(usernameLabel);
        rowOne.add(usernameField);
        rowTwo.add(passwordLabel);
        rowTwo.add(passwordField);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.anchor = GridBagConstraints.CENTER;

        mainPanel.add(imageLabel,gbc);
        gbc.gridy = 1;
        mainPanel.add(rowOne,gbc);
        gbc.gridy = 2;
        mainPanel.add(rowTwo, gbc);
        gbc.gridy = 3;
        mainPanel.add(login, gbc);
        add(mainPanel, BorderLayout.CENTER);
    }

}