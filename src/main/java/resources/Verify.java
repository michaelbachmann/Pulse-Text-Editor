package resources;

// Used to validate credentials
public class Verify {
    public static boolean passMatch(String one, String two) {
        return one.equals(two);
    }

    public static boolean validSignUp(String username, String password){
        if (username == null || password == null) return false;
        else if (password.isEmpty() || username.isEmpty())
            return false;
        else if (!(username.matches("^[a-zA-Z0-9]*$")))
            return false;
        else if (!(password.matches("^(?=.*[a-zA-Z])(?=.*\\d).+$")))
            return false;
    return true;
    }
}
