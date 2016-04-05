package resources;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

// User
public class User implements Serializable {
    public static final long serialVersionUID = 3;
    private String username;
    private String password;
    private String directory;
    private List<String> files;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        files = new Vector<>();
    }

    public User(String username, String password, List<String> files) {
        this.username = username;
        this.password = password;
        this.files = files;
    }

    public void addFile(String filename){
        files.add(filename);
    };

    public String getDirectory() { return directory; }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getFiles() {
        return files;
    }
    @Override
    public String toString() {
        return ("Username: " + username + "\n" +
                "Files: " + "\n" +
                String.join("\n", files));
    }
}
