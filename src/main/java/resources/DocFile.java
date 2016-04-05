package resources;

import javax.swing.*;
import java.io.Serializable;


public class DocFile extends JTextArea implements Serializable {
    public static final long serialVersionUID = 88;
    private String name;
    private String content;

    public DocFile(String name, String content) {
        super();
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
