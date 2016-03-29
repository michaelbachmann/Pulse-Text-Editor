package uielements;

import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class FlatTabbedPaneUI extends BasicTabbedPaneUI {
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
            g.setColor(ColorSet.DARKBLUE);
        else
            g.setColor(ColorSet.BEIGE);

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
            colorToPaint = ColorSet.TURQUOISE;

        // Draw Background
        g.setColor(colorToPaint);
        g.fillRect(rect.x,0,rect.width,28);

        // Bottom color
        g.setColor(ColorSet.DARKBLUE);
        g.fillRect(rect.x,26,rect.width,4);
    }

    // Paint TabArea Turquise
    protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {
        g.setColor(ColorSet.TURQUOISE);
        g.fillRect(0,0,tabPane.getWidth(), 28);
        super.paintTabArea(g, tabPlacement, selectedIndex);
    }

    // Paint vertical edge
    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        Rectangle rect = getTabBounds(tabIndex, new Rectangle(x, y, w, h));
        g.setColor(ColorSet.EDGE_COLOR);
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