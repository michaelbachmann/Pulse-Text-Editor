package server;


public class Constants {
    public static final String SERVER_NAME_STRING = "Pulse Server";

    // Log Strings
    public static final String PORT_ERROR_STRING_STRING = "Please set a valid port in the configuration file" + utilities.Constants.LOW_PORT + " and " + utilities.Constants.HIGH_PORT;
    public static final String PORT_IN_USE_ERROR_STRING = "Port already in use. Select another port" + utilities.Constants.LOW_PORT + " and " + utilities.Constants.HIGH_PORT;
    public static final String START_CLIENT_CONNECTED_STRING = "Client with IP address ";
    public static final String END_CLIENT_CONNECTED_STRING = " connected.";
    public static final String CLIENT_DISCONNETED_STRING = "Client disconnected.";

    // Config Parsing
    public static final String CONFIG_FILE = "resources/config/server_config.txt";
    public static final String CONFIG_FILE_DELIMITER = "|";
    public static final String PORT_LABEL_STRING = "Port";
    public static final String UNRECOGNIZED_LINE = "Unrecognized line in file: ";
    public static final String SERVER_STARTED_STRING = "Server started on Port: ";
    public static final String SERVER_STOPPED_STRING = "Server Stopped";

}
