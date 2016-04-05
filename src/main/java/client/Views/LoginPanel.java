package client.Views;

import uielements.ColorSet;
import uielements.componentuis.FlatButtonUI;
import uielements.componentuis.GetUIComponents;
import utilities.Constants;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    JPanel rowOne, rowTwo, mainPanel;
    JButton login;
    JLabel imageLabel, usernameLabel, passwordLabel, nameLabel;
    JTextField usernameField;
    JTextField passwordField;
    GridBagConstraints gbc;

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JTextField getPasswordField() {
        return passwordField;
    }

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
        usernameLabel = GetUIComponents.setBoldLabel("Username: ", 66,15);
        passwordLabel = GetUIComponents.setBoldLabel("Password: ",66,15);
        usernameField = GetUIComponents.setTextFieldLook("",20);
        passwordField = GetUIComponents.setPasswordFieldLook(20);
        imageLabel = GetUIComponents.getScaledIcon();
        nameLabel = GetUIComponents.setBoldLabel(Constants.APP_NAME,165,15);
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
