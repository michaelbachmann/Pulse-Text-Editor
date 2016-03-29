package uielements;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;


public class FlatScrollBarUI extends BasicScrollBarUI {

    @Override
    protected void configureScrollBarColors() {
        trackColor = ColorSet.TURQUOISE;
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        return new Dimension(15,0);
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumb_bounds) {
        g.translate(thumb_bounds.x, thumb_bounds.y);
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Graphics2D clean_g = (Graphics2D) g;
        clean_g.setColor(ColorSet.DARKER_BLUE);
        clean_g.addRenderingHints(rh);
        clean_g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        clean_g.fillRoundRect(5, 0, 5, thumb_bounds.height-5, 5, 5);
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        JButton button = new JButton(new ImageIcon("resources/img/up-arrow.png"));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setMinimumSize(new Dimension(15, 15));
        button.setMaximumSize(new Dimension(15, 15));
        button.setPreferredSize(new Dimension(15, 15));
        return button;
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        JButton button = new JButton(new ImageIcon("resources/img/down-arrow.png"));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setMinimumSize(new Dimension(15, 15));
        button.setMaximumSize(new Dimension(15, 15));
        button.setPreferredSize(new Dimension(15, 15));
        return button;
    }
}
