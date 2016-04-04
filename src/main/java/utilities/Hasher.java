package utilities;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class Hasher {
    public static String encryptPassword(String password) {
        String sha1_hash = "";
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getBytes("UTF-8"));

            // Get byte hash
            final byte[] hash = crypt.digest();

            // Format into hex string
            Formatter formatter = new Formatter();
            for (byte b : hash) {
                formatter.format("%02x", b);
            }

            // Turn it back to a string close the formatter and return the hash
            sha1_hash = formatter.toString();
            formatter.close();
        }
        catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }  catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sha1_hash;
    }
}
