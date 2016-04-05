package uielements.componentuis;

import uielements.ColorSet;
import uielements.UIConstants;

import javax.swing.*;
import java.awt.*;


public class GetUIComponents {

    public static JTextField setTextFieldLook(String prefill, int col) {
        JTextField textField= new JTextField(prefill,col);
        textField.setBackground(ColorSet.TEXT_FIELD_BACKGROUND);
        textField.setForeground(ColorSet.BASIC_FONT);
        textField.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        return textField;
    }

    public static JPasswordField setPasswordFieldLook(int col) {
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBackground(ColorSet.TEXT_FIELD_BACKGROUND);
        passwordField.setForeground(ColorSet.BASIC_FONT);
        passwordField.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        return passwordField;
    }

    public static JLabel setBoldLabel(String title, int width, int height) {
        String newTitle = ("<html><b>" + title + "</b></html>");
        JLabel label = new JLabel(newTitle);
        label.setBackground(ColorSet.TEXT_FIELD_BACKGROUND);
        label.setForeground(ColorSet.SECOND_FONT);
        label.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        label.setMinimumSize(new Dimension(width,height));
        label.setPreferredSize(new Dimension(width,height));
        label.setMaximumSize(new Dimension(width,height));
        return label;
    }

    public static JLabel getScaledIcon() {
        ImageIcon icon_image = new ImageIcon(UIConstants.PULSE_ICON);
        Image image = icon_image.getImage(); // transform it
        Image newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        icon_image = new ImageIcon(newimg);  // transform it back
        return new JLabel(icon_image);
    }

}
