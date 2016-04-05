package client;

import uielements.ColorSet;

import javax.swing.*;

public class PulseApplication {
    // MARK: Main Function
    public static void main(String[] args) {
        try {
            UIManager.put("JMenuBar.selectionBackground", ColorSet.DARKBLUE);
            UIManager.put("MenuItem.selectionBackground", ColorSet.DARKBLUE);
            UIManager.put("MenuItem.selectionForeground", ColorSet.SALMON);
            UIManager.put("Menu.selectionBackground", ColorSet.DARKBLUE);
            UIManager.put("Menu.selectionForeground", ColorSet.SALMON);
            UIManager.setLookAndFeel(
                    UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e) { e.printStackTrace(); }
        catch (ClassNotFoundException e) { e.printStackTrace(); }
        catch (InstantiationException e) { e.printStackTrace(); }
        catch (IllegalAccessException e) { e.printStackTrace(); }

        ClientGUI gui = new ClientGUI();

    }
}