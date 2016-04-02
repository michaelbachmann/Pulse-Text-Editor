package uielements.jcomponents;

import uielements.ColorSet;

import javax.swing.border.AbstractBorder;
import java.awt.*;

public class BottomBorder extends AbstractBorder {
    protected static final int ARC = 12;

    @Override public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(ColorSet.DARKER_BLUE);
        g.fillRect(0, height-5, width, 5);
    }

    @Override public Insets getBorderInsets(Component c) {
        return new Insets(4, 8, 4, 8);
    }

}