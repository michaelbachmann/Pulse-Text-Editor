package uielements.componentuis;

import uielements.ColorSet;
import uielements.jcomponents.BottomBorder;
import uielements.jcomponents.FlatBasicComboPopup;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

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

    public static class FlatComboBoxUI extends BasicComboBoxUI
    {
        protected void installDefaults()
        {
            super.installDefaults();
            comboBox.setOpaque(true );
            comboBox.setBackground( ColorSet.DARKBLUE );
            comboBox.setForeground( ColorSet.BEIGE );
            UIManager.put("ComboBox.selectionForeground", ColorSet.SALMON);
            UIManager.put("ComboBox.selectionBackground", ColorSet.DARKBLUE);
            UIManager.put("ComboBox.buttonDarkShadow", ColorSet.SALMON);
            comboBox.setBorder(BorderFactory.createEmptyBorder());
            comboBox.setBorder(new BottomBorder());
        }

        @Override
        protected ComboPopup createPopup() {
            BasicComboPopup flat_popup = new FlatBasicComboPopup(comboBox);
            flat_popup.getAccessibleContext().setAccessibleParent(comboBox);
            return flat_popup;
        }

        @Override
        protected JButton createArrowButton() {
            JButton button = new JButton(new ImageIcon("resources/img/ComboButton.png"));
            button.setBorder(BorderFactory.createEmptyBorder());
            button.setMinimumSize(new Dimension(15, 15));
            button.setMaximumSize(new Dimension(15, 15));
            button.setPreferredSize(new Dimension(15, 15));
            button.setContentAreaFilled(false);
            button.setBackground(ColorSet.DARKBLUE);
            return button;
        }

    }

    public static class FlatScrollBarUI extends BasicScrollBarUI {

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

    public static class FlatTabbedPaneUI extends BasicTabbedPaneUI {
        private int lastTab = -1;

        protected void installDefaults() {
            super.installDefaults();
            tabPane.addMouseListener(new TabRolloverListener());
            tabPane.addMouseMotionListener(new TabRolloverListener());
            tabAreaInsets = new Insets(0, 0, 0, 0);
            tabInsets = new Insets(8, 4, 8, 4);
        }

        protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title, Rectangle textRect, boolean isSelected)  {
            Rectangle rect = rects[tabIndex];
            g.translate(rect.x,0);
            FontMetrics fm = getFontMetrics();

            if (isSelected)
                g.setColor(ColorSet.SALMON);
            else if (getRolloverTab() == tabIndex)
                g.setColor(ColorSet.SALMON);
            else
                g.setColor(ColorSet.HOVER_COLOR);

            // Draw String and translate back
            g.drawString(title, (rect.width / 2 - fm.stringWidth(title) / 2) , maxTabHeight / 2 + fm.getMaxDescent() + 2);
            g.translate(rect.x * -1, 0);
        }


        protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
            Color colorToPaint;
            Rectangle rect = rects[tabIndex];

            // If the tab is selected paint it dark blue else paint it turquoise
            if (isSelected)
                colorToPaint = ColorSet.DARKBLUE;
            else
                colorToPaint = ColorSet.LIGHT_BLUE;

            // Draw Background
            g.setColor(colorToPaint);
            g.fillRect(rect.x,0,rect.width,28);

            // Bottom color
            g.setColor(ColorSet.DARKBLUE);
            g.fillRect(rect.x,26,rect.width,4);
        }

        // Paint TabArea Turquise
        protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {
            g.setColor(ColorSet.LIGHT_BLUE);
            g.fillRect(0,0,tabPane.getWidth(), 28);
            super.paintTabArea(g, tabPlacement, selectedIndex);
        }

        // Paint vertical edge
        protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
            Rectangle rect = getTabBounds(tabIndex, new Rectangle(x, y, w, h));
            g.setColor(ColorSet.DARKER_BLUE);
            g.drawLine(rect.x + rect.width, 0, rect.x + rect.width, 28);
        }

        // Make sure no borders are drawn and tab sizes do not change
        protected void paintContentBorderTopEdge( Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h ) { }
        protected void paintContentBorderBottomEdge( Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h ) { }
        protected void paintContentBorderRightEdge( Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h ) { }
        protected void paintContentBorderLeftEdge( Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h ) { }
        protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) { }
        protected int getTabLabelShiftY(int tabPlacement, int tabIndex, boolean isSelected) {
            return 0;
        }
        protected int getTabLabelShiftX(int tabPlacement, int tabIndex, boolean isSelected) { return 0; }
        protected Insets getContentBorderInsets(int tabPlacement) {
            return new Insets(0, 0, 0, 0);
        }
        protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) { return 28; }
        protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
            int width = super.calculateTabWidth(tabPlacement, tabIndex, metrics);
            return width + 10;  // LR padding
        }

        // Custom Rollover effect
        private class TabRolloverListener extends MouseAdapter implements MouseMotionListener {
            public void mouseMoved(MouseEvent e) { rollOver(); }
            public void mouseEntered(MouseEvent e) { rollOver(); }
            public void mouseExited(MouseEvent e) {
                tabPane.repaint();
            }
            private void rollOver() {
                // Only repaint once on hover
                if (getRolloverTab() != lastTab) {
                    lastTab = getRolloverTab();
                    tabPane.repaint();
                }
            }
        }

    }
}