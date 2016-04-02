package utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/***************************
 *   CONFIGURE SETTINGS    *
 ***************************
 * Syntax for config parser
 *--------------------------
 * It works based on key value pairs and will trim whitespace and ignore blank lines.
 *
 * # = Comment Escape
 * | = Delimiter
 *
 * Example: Port|8080
 * Port would be the String Key String Num
 * Type conversion not added yet
 */
final class ConfigureSettings {
    // Create only in Constructor
    private static final ConfigureSettings mInstance;

    // Static Initializer
    static { mInstance = new ConfigureSettings(); }

    // Returns key (type) value (config param),
    public static Map<String, String> getSetings(String file) {
        Map<String, String> settings = new HashMap<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(server.Constants.CONFIG_FILE));
            parseConfigFile(br, settings);
        } catch (FileNotFoundException fnfe) {
            System.out.println(fnfe.getMessage());
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ioe1) {
                    System.out.println(ioe1.getMessage());
                }
            }
        }
        return settings;
    }


    private static Map<String, String> parseConfigFile(BufferedReader br, Map<String, String> settings) throws IOException {
        String line = br.readLine();
        String key;
        String value;
        StringTokenizer st;

        while (line != null) {
            line = line.trim();
            if (line.startsWith("#")) {// ignore line since it is a comment
                System.out.println(line);
            } else if (line.startsWith(server.Constants.PORT_LABEL_STRING)) {
                System.out.println(line);
                st = new StringTokenizer(line, server.Constants.CONFIG_FILE_DELIMITER);
                key = st.nextToken();
                value = st.nextToken();
                settings.put(key, value);
            } else if (line.length() == 0) {
                // this would be a blank line
            } else {
                // this would be an unrecognized line
                System.out.println(server.Constants.UNRECOGNIZED_LINE + line);
            }
            line = br.readLine();
        }
        return settings;
    }

    // Singleton ensures only one exists
    private ConfigureSettings() {}
}
