package client;

import uielements.ColorSet;
import utilities.ConfigureSettings;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class PulseApplication {
    private static int port;
    private static String hostname;

    // MARK: Main Function
    public static void main(String[] args) {
        try { // Set cross-platform Java L&F (also called "Metal")
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

        if (getPortAndHost()){
            try {
                Socket socket = new Socket(hostname,port);
                ClientGUI gui = new ClientGUI(socket);
            } catch (IOException ioe ) {
                System.out.println(ioe.getMessage());
            }
        }
    }

    private static boolean getPortAndHost()  {
        Map<String,String> settings = ConfigureSettings.getSetings(Constants.CONFIG_FILE);
        port = ConfigureSettings.readPort(settings);  // try setting port
        if (!(port > utilities.Constants.LOW_PORT && port < utilities.Constants.HIGH_PORT)) {
            return false;
        }
        if (settings.containsKey(utilities.Constants.HOST_STRING)){
            // check valid host for now dgaf
            if (!settings.get(utilities.Constants.HOST_STRING).equals("localhost")){
                return false; // check for diff hostname
            } else {
                hostname = settings.get(utilities.Constants.HOST_STRING);
            }
        }
        return true;
    }

}