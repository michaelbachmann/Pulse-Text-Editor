package resources;

import java.io.Serializable;
import java.util.List;

// User
public class User implements Serializable {
    private String username;
    private String password;
    private List<String> files;
}
