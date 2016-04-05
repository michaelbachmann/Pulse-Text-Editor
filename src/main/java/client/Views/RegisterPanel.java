package client.Views;

import uielements.ColorSet;
import uielements.componentuis.FlatButtonUI;
import uielements.componentuis.GetUIComponents;

import javax.swing.*;
import java.awt.*;

public class RegisterPanel extends JPanel {
    JPanel rowOne, rowTwo, rowThree, mainPanel;
    JButton login;
    JLabel imageLabel, usernameLabel, passwordLabel,repeatLabel, nameLabel;

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JTextField getPasswordField() {
        return passwordField;
    }

    public JTextField getRepeatField() {
        return repeatField;
    }

    JTextField usernameField, passwordField, repeatField;
    GridBagConstraints gbc;

    public JButton getLogin() {
        return login;
    }

    public RegisterPanel(){
        super();
        setLayout(new BorderLayout());
        instantiateVariables();
        createGUI();
        setBackground(ColorSet.EDITOR_BG_COLOR);
    }

    public void instantiateVariables() {
        usernameLabel = GetUIComponents.setBoldLabel("Username: ",66,15);
        passwordLabel = GetUIComponents.setBoldLabel("Password: ",66,15);
        repeatLabel = GetUIComponents.setBoldLabel("Repeat:   ",66,15);
        nameLabel = GetUIComponents.setBoldLabel(utilities.Constants.APP_NAME,115,15);
        usernameField = GetUIComponents.setTextFieldLook("",20);
        passwordField = GetUIComponents.setPasswordFieldLook(20);
        repeatField = GetUIComponents.setPasswordFieldLook(20);
        imageLabel = GetUIComponents.getScaledIcon();
        login = new JButton("Login");
        mainPanel = new JPanel(new GridBagLayout());
        rowOne = new JPanel(new FlowLayout());
        rowTwo = new JPanel(new FlowLayout());
        rowThree = new JPanel(new FlowLayout());
        gbc = new GridBagConstraints();

    }

    public void createGUI() {
        mainPanel.setBackground(ColorSet.EDITOR_BG_COLOR);
        rowOne.setBackground(ColorSet.EDITOR_BG_COLOR);
        rowTwo.setBackground(ColorSet.EDITOR_BG_COLOR);
        rowThree.setBackground(ColorSet.EDITOR_BG_COLOR);
        login.setUI(new FlatButtonUI());


        rowOne.add(usernameLabel);
        rowOne.add(usernameField);
        rowTwo.add(passwordLabel);
        rowTwo.add(passwordField);
        rowThree.add(repeatLabel);
        rowThree.add(repeatField);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.anchor = GridBagConstraints.CENTER;

        mainPanel.add(imageLabel,gbc);
        gbc.gridy = 1;
        mainPanel.add(nameLabel,gbc);
        gbc.gridy = 2;
        mainPanel.add(rowOne,gbc);
        gbc.gridy = 3;
        mainPanel.add(rowTwo, gbc);
        gbc.gridy = 4;
        mainPanel.add(rowThree,gbc);
        gbc.gridy = 5;
        mainPanel.add(login, gbc);
        add(mainPanel, BorderLayout.CENTER);
    }

}
