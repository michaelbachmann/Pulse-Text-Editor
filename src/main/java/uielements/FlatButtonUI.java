package uielements;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class FlatButtonUI extends BasicButtonUI {

    @Override
    public void installUI (JComponent c) {
        super.installUI(c);
        AbstractButton button = (AbstractButton) c;
        button.setBorder(new EmptyBorder(5, 15, 10, 15));
        button.setRolloverEnabled(true);
    }


    @Override
    public void paint (Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        if (b.getModel().isRollover()) {
            normalBackground(g,c);
            c.setForeground(ColorSet.SALMON);
        }
        else if (b.getModel().isPressed())
            pressedBackground(g,c);
        else
            normalBackground(g,c);

        super.paint(g, c);
    }

    private void normalBackground (Graphics g, JComponent c) {
        Dimension dimension = c.getSize();
        // Draw Bottom 3D Effect
        g.setColor(ColorSet.DARKER_BLUE);
        g.fillRect(0, 0, dimension.width, dimension.height);
        // Draw Top Part
        g.setColor(ColorSet.DARKBLUE);
        g.fillRect(0, 0, dimension.width, dimension.height - 5);
        c.setForeground(ColorSet.BEIGE);
    }

    private void pressedBackground (Graphics g, JComponent c) {
        Dimension dimension = c.getSize();
        // Draw Bottom 3D Effect
        g.setColor(ColorSet.EDGE_COLOR);
        g.fillRect(0, 0, dimension.width, dimension.height);
        // Draw Top Part
        g.setColor(ColorSet.HOVER_COLOR);
        g.fillRect(0, 0, dimension.width, dimension.height - 5);
        c.setForeground(ColorSet.DARKBLUE);
    }
}