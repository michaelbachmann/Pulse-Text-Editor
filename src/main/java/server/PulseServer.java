package server;


import utilities.ConfigureSettings;

public class PulseServer {
    private int port;
    private boolean listening;
    private ServerGUI serverGUI;


    public PulseServer() {
        initializeApplication();
    }

    private void initializeApplication() {
        // Setup Port
        port = ConfigureSettings.readPort(ConfigureSettings.getSetings(server.Constants.CONFIG_FILE));  // try setting port
        if (!(port > utilities.Constants.LOW_PORT && port < utilities.Constants.HIGH_PORT)) {
            System.exit(5); // check if in use????
        }
        // Setup GUI
        serverGUI = new ServerGUI(port);

    }

    // Main
    public static void main(String[] args) {
        new PulseServer();
    }

}
