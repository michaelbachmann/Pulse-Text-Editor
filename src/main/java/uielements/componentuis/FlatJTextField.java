package uielements.componentuis;

import uielements.ColorSet;

import javax.swing.*;
import java.awt.*;


public class FlatJTextField extends JTextField {
    public FlatJTextField(String prefill, int col) {
        super(prefill,col);
        setBackground(ColorSet.DARKBLUE);
        setMargin(new Insets(2,2,2,2));
        setForeground(ColorSet.BASIC_FONT);
        setBorder(BorderFactory.createEmptyBorder());
        repaint();
    }
}
