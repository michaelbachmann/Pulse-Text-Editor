package uielements;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class FlatComboBoxScrollUI extends BasicScrollBarUI {

    @Override
    protected void configureScrollBarColors() {
        trackColor = ColorSet.DARKBLUE;
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        return new Dimension(15,0);
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumb_bounds) {
        g.translate(thumb_bounds.x, thumb_bounds.y);
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Graphics2D aa_g = (Graphics2D) g;

        aa_g.setColor(ColorSet.TURQUOISE);
        aa_g.addRenderingHints(rh);
        aa_g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        aa_g.fillRoundRect(5, 0, 5, thumb_bounds.height-5, 5, 5);
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        JButton button = new JButton();
        button.setMinimumSize(new Dimension(0, 0));
        button.setMaximumSize(new Dimension(0, 0));
        button.setPreferredSize(new Dimension(0, 0));
        return button;
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        JButton button = new JButton();
        button.setMinimumSize(new Dimension(0, 0));
        button.setMaximumSize(new Dimension(0, 0));
        button.setPreferredSize(new Dimension(0, 0));
        return button;
    }

}
