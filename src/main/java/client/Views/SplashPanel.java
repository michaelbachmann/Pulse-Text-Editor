package client.Views;

import uielements.ColorSet;
import uielements.componentuis.FlatButtonUI;
import uielements.componentuis.GetUIComponents;
import utilities.Constants;

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
    JLabel imageLabel, name;
    GridBagConstraints gbc;


    public SplashPanel(){
        super();
        setLayout(new BorderLayout());
        instantiateVariables();
        createGUI();
        setBackground(ColorSet.DARKBLUE);
    }

    public void instantiateVariables() {
        imageLabel = GetUIComponents.getScaledIcon();
        name = GetUIComponents.setBoldLabel(Constants.APP_NAME,115,15);
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
        mainPanel.add(name,gbc);
        gbc.gridy = 2;
        mainPanel.add(rowOne, gbc);
        gbc.gridy = 3;
        mainPanel.add(offline, gbc);
        add(mainPanel, BorderLayout.CENTER);
    }

}
