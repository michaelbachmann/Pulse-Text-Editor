package server;

import java.sql.*;
import java.util.Vector;

public class DBController {
    public static boolean saveFile(String username, String filename) {
        boolean success = false;
        Connection conn = null;
        try {
            Class.forName(Constants.SQL_DRIVER);
            conn = DriverManager.getConnection(Constants.SQL_CONNECTION);
            // match pass and userID

            PreparedStatement ps = conn.prepareStatement("SELECT files FROM Directory where userID =? and files =?");
            ps.setString(1, username); // set string for the first question mark to this value
            ps.setString(2, filename);
            // Get the response
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) { // file doesnt exists save it
                String fileAdd = "insert into Directory (userID,files)\n" +
                        "\tvalues ('"+ username +"','" + filename +"')";
                if (conn.createStatement().executeUpdate(fileAdd) == 0) {
                    System.out.println("Couldn't add file " + filename + " for user " + username);
                } else {
                    System.out.println("Added: " + filename + " for user " +username);
                    success = true;
                }
                success = true;
            } else {
                System.out.println("File: " + filename + "already exists");
            }
        } catch (SQLException sqle) {
            System.out.println("sqle: " + sqle.getMessage());
        } catch (ClassNotFoundException cnfe) {
            System.out.println("cnfe: " + cnfe.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException sqle) {
                System.out.println("sqle closing conn: " + sqle.getMessage());
            }
        }
        return success;
    }


    public static Vector<String> getRemoteFiles(String username) {
        Vector<String> remoteFileList = new Vector<>();
        Connection conn = null;
        try {
            Class.forName(Constants.SQL_DRIVER);
            conn = DriverManager.getConnection(Constants.SQL_CONNECTION);
            // match pass and userID
            PreparedStatement ps = conn.prepareStatement("SELECT files FROM Directory WHERE userID=?");
            ps.setString(1, username); // set string for the first question mark to this value
            // Get the response
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

//                    String file = resultSet.getString("files");
                    remoteFileList.add(rs.getString("files"));
            }
            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            System.out.println("sqle: " + sqle.getMessage());
        } catch (ClassNotFoundException cnfe) {
            System.out.println("cnfe: " + cnfe.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException sqle) {
                System.out.println("sqle closing conn: " + sqle.getMessage());
            }
        }
        return remoteFileList;
    }

    public static boolean loginUser(String username, String password) {
        boolean success = false;
        Connection conn = null;
        try {
            Class.forName(Constants.SQL_DRIVER);
            conn = DriverManager.getConnection(Constants.SQL_CONNECTION);
            // match pass and userID
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM User WHERE userID=? AND password=?");
            ps.setString(1, username); // set string for the first question mark to this value
            ps.setString(2, password);
            // Get the response
            ResultSet rs = ps.executeQuery();

            if (rs.next()) // valid credentials
                success = true;
        } catch (SQLException sqle) {
            System.out.println("sqle: " + sqle.getMessage());
        } catch (ClassNotFoundException cnfe) {
            System.out.println("cnfe: " + cnfe.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException sqle) {
                System.out.println("sqle closing conn: " + sqle.getMessage());
            }
        }
        return success;
    }


    public static boolean createUser(String username, String password) {
        boolean success = false;
        Connection conn = null;
        try {
            Class.forName(Constants.SQL_DRIVER);
            conn = DriverManager.getConnection(Constants.SQL_CONNECTION);
            // send in ahead of time
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM User WHERE userID=?");
            ps.setString(1, username); // set string for the first question mark to this value
            // Get the response
            ResultSet rs = ps.executeQuery();

            // See if the user exists if not create it
            if (!rs.next()) {  // username doesnt exit create user and login
                String userToAdd = "insert into User (userID,password)\n" +
                        "\tvalues ('"+ username +"','" + password +"')";
                if (conn.createStatement().executeUpdate(userToAdd) == 0) {
                    System.out.println("Couldn't add user" + username);
                } else {
                    System.out.println("Added that fool - " + username);
                    success = true;
                }
            } else {
                System.out.println("Username: " + rs.getString(Constants.SQL_USER_ID) + " already exists");
            }
        } catch (SQLException sqle) {
            System.out.println("sqle: " + sqle.getMessage());
        } catch (ClassNotFoundException cnfe) {
            System.out.println("cnfe: " + cnfe.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException sqle) {
                System.out.println("sqle closing conn: " + sqle.getMessage());
            }
        }
        return success;
    }

    public static void main(String[] args) {
        Vector<String> remoteFiles;
        if (DBController.loginUser("MichaelB", "aisfnisajvnu2")){
            System.out.println("Succcess");
            remoteFiles = DBController.getRemoteFiles("MichaelB");
            if (remoteFiles.isEmpty()) {
                System.out.println("no files");
            } else {
                for (String s: remoteFiles) {
                    System.out.println(s);
                }
            }
            if (DBController.saveFile("MichaelB", "BrillzNucca.txt")) {
                System.out.println("YACK YACK YACK");
            } else {
                System.out.println("FACK FACK FACK");
            }
        }

    }
}
