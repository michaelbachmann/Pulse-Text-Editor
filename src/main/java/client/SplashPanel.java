package client;

import uielements.Constantsssss;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Michael on 4/2/16.
 */
// Background panel inner class to display centered logo

public class SplashPanel extends JPanel {
        private Image img;
//        public SplashPanel(Image img) {
////            this.img = img;
////        }

        public SplashPanel() {
            ImageIcon icon_image = new ImageIcon(Constantsssss.PULSE_ICON);
            img = icon_image.getImage();
            setVisible(true);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.translate(this.getParent().getWidth() / 2, this.getParent().getHeight() / 2);
            g2d.translate(-100 / 2, -100/2);
            g2d.drawImage(img, 0, 0, 100, 100, null);
        }
}
