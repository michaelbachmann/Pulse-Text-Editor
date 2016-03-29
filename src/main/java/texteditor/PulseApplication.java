package texteditor;

import com.apple.eawt.Application;
import listeners.MenuItemListener;
import resources.Constants;
import spellchecker.SpellCheckManager;
import uielements.ColorSet;
import uielements.FlatTabbedPaneUI;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

public class PulseApplication extends JFrame {

    // MARK: Member Variables
    private static final long serialVersionUID = 1L;
    private JMenuBar menuBar;
    private JMenu fileMenu, editMenu, spellCheckMenu;
    private JMenuItem newMI, openMI, saveMI, closeMI, undoMI, redoMI, cutMI, copyMI, pasteMI, selectAllMI, scRunMI, scConfigureMI;
    private List<JMenuItem> menuList;
    private JTabbedPane tabbedEditorPane;
    private SpellCheckManager scm;
    private UndoManager currentManager;
    // Delegate: Class to manage events between editor and documents
    private MenuItemDelegate delegate = new MenuItemDelegate(this);
    private ImageIcon newIcon, openIcon, saveIcon, closeIcon, copyIcon, pasteIcon, selectAllIcon, scIcon, configIcon, cutIcon, redoIcon, undoIcon;
    private BackgroundPanel background;
    private JPanel background_panel;

    // MARK: Constructor
    public PulseApplication () {
        super("Pulse");
        instantiateComponents();
        createGUI();
        addActions();
        setupOSXIcon();
        setupFont();
        this.setVisible(true);
    }

    public void setupOSXIcon() {
        // Try to set the application icon for os x
        try {
            Application application = Application.getApplication();
            ImageIcon icon_image = new ImageIcon(Constants.PULSE_ICON);
            Image dock_image = icon_image.getImage();
            background = new BackgroundPanel(dock_image);
            background.setVisible(true);
            setContentPane(background);
            application.setDockIconImage(dock_image);
            setIconImage(icon_image.getImage());  // set for other OS's
            setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon(Constants.CURSOR_ICON).getImage(),new Point(0,0),"custom cursor"));
        } catch (Exception e) {
            e.getMessage();
        }
    }

    // Trys to setup app font, uses default if not
    public void setupFont() {
        //create the font to use. Specify the size!
        try {
            // All credit for this font goes to the original owner
            // http://www.1001fonts.com/open-sans-font.html#license
            Font myFont = Font.createFont(Font.TRUETYPE_FONT, new File(Constants.MAIN_FONT)).deriveFont(Font.PLAIN, 12f);
            GraphicsEnvironment gc = GraphicsEnvironment.getLocalGraphicsEnvironment();
            gc.registerFont(myFont);
            setUIFont(myFont);
        } catch (IOException e) {
            e.printStackTrace();
        } catch(FontFormatException e) {
            e.printStackTrace();
        }
    }

    // For Setting OS X Doc Icon
    public boolean exists(String className) {
        try {
            Class.forName( className, false, null );
            return true;
        } catch (ClassNotFoundException exception) {
            return false;
        }
    }

    // For Setting OS X Doc Icon
    public void setIcon( BufferedImage icn ) {
        if ( exists( "com.apple.eawt.Application" ) )
            Application.getApplication().setDockIconImage( icn );
    }

    private void setupIcons() {
        //All credit goes to http://somerandomdude.com/work/sanscons/
        newIcon = new ImageIcon(Constants.ICON_NEW);
        openIcon = new ImageIcon(Constants.ICON_OPEN);
        saveIcon = new ImageIcon(Constants.ICON_SAVE);
        closeIcon = new ImageIcon(Constants.ICON_CLOSE);
        copyIcon = new ImageIcon(Constants.ICON_COPY);
        pasteIcon = new ImageIcon(Constants.ICON_PASTE);
        scIcon = new ImageIcon(Constants.ICON_SPELLCHECK);
        configIcon = new ImageIcon(Constants.ICON_CONFIG);
        cutIcon = new ImageIcon(Constants.ICON_CUT);
        redoIcon = new ImageIcon(Constants.ICON_REDO);
        undoIcon = new ImageIcon(Constants.ICON_UNDO);
        selectAllIcon = new ImageIcon(Constants.ICON_SELECTALL);
    }

    // Instantiate components
    private void instantiateComponents () {
        // SpellChecker
        scm = new SpellCheckManager();
        // Setup All Icons
        setupIcons();
        // Menu Bar Setup
        menuBar = new JMenuBar() {
            @Override
            public void paintComponent(Graphics g) {
                Dimension size = this.getSize();
                g.drawImage(Toolkit.getDefaultToolkit().getImage(Constants.MENU_BAR_IMG),0,0, size.width, size.height, this);
            }
        };
        fileMenu = new JMenu("File") {
            @Override
            public JPopupMenu getPopupMenu() {
                JPopupMenu menu = super.getPopupMenu();
                menu.setBorder(BorderFactory.createEmptyBorder());
                return menu;
            }
        };
        editMenu = new JMenu("Edit") {
            @Override
            public JPopupMenu getPopupMenu() {
                JPopupMenu menu = super.getPopupMenu();
                menu.setBorder(BorderFactory.createEmptyBorder());
                return menu;
            }
        };
        spellCheckMenu = new JMenu("Spell Check") {
            @Override
            public JPopupMenu getPopupMenu() {
                JPopupMenu menu = super.getPopupMenu();
                menu.setBorder(BorderFactory.createEmptyBorder());
                return menu;
            }
        };

        // Menu Items
        newMI = new JMenuItem("New", newIcon);
        openMI = new JMenuItem("Open", openIcon);
        saveMI = new JMenuItem("Save", saveIcon);
        closeMI = new JMenuItem("Close", closeIcon);
        undoMI = new JMenuItem("Undo", undoIcon);
        redoMI = new JMenuItem("Redo", redoIcon);
        cutMI = new JMenuItem("Cut", cutIcon);
        copyMI = new JMenuItem("Copy", copyIcon);
        pasteMI = new JMenuItem("Paste", pasteIcon);
        selectAllMI = new JMenuItem("Select All", selectAllIcon);
        scRunMI = new JMenuItem("Run", scIcon);
        scConfigureMI = new JMenuItem("Configure", configIcon);
        menuList = new ArrayList<>();
        menuList.addAll(Arrays.asList(newMI,openMI,saveMI,closeMI,
                undoMI,redoMI,cutMI,copyMI,pasteMI,
                selectAllMI,scRunMI,scConfigureMI));

        tabbedEditorPane = new JTabbedPane();
        tabbedEditorPane.setUI(new FlatTabbedPaneUI());
    }

    private void createGUI() {
        setSize(800,600);
        setLocation(100,100);
        fileMenu.add(newMI);
        fileMenu.add(openMI);
        fileMenu.add(saveMI);
        fileMenu.add(closeMI);
        editMenu.add(undoMI);
        editMenu.add(redoMI);
        editMenu.add(cutMI);
        editMenu.add(copyMI);
        editMenu.add(pasteMI);
        editMenu.add(selectAllMI);
        spellCheckMenu.add(scRunMI);
        spellCheckMenu.add(scConfigureMI);
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(spellCheckMenu);
        setJMenuBar(menuBar);
        allowItemsSelectable(false);
        undoMI.setEnabled(false);
        redoMI.setEnabled(false);
        add(tabbedEditorPane);
        setUpMenuLook();
    }

    // Setup JMenuItems
    private void setUpMenuLook() {
        // Strip all the borders
        menuBar.setBorder(BorderFactory.createEmptyBorder());
        fileMenu.setBorder(BorderFactory.createEmptyBorder(8,8,8,4));
        editMenu.setBorder(BorderFactory.createEmptyBorder(8,8,8,4));
        spellCheckMenu.setBorder(BorderFactory.createEmptyBorder(8,8,8,0));
        menuBar.setBackground(ColorSet.DARKBLUE);
        fileMenu.setForeground(ColorSet.TURQUOISE);
        editMenu.setForeground(ColorSet.TURQUOISE);
        spellCheckMenu.setForeground(ColorSet.TURQUOISE);
        for (JMenuItem item : menuList) {
            item.setBackground(ColorSet.DARKBLUE);
            item.setForeground(ColorSet.BEIGE);
            item.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
        }
    }

    // Force font for all components
    public static void setUIFont(Font f) {
        Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof Font)
                UIManager.put(key,f);
        }
    }

    // Listens for changes in tabs
    ChangeListener tabbedChangedListener = new ChangeListener() {
        public void stateChanged(ChangeEvent changeEvent) {
            if (tabbedEditorPane.getTabCount() != 0) {
                setContentPane(tabbedEditorPane);
                currentManager = ((Document) tabbedEditorPane.getSelectedComponent()).getCurrentManager();
            } else {
                getContentPane().removeAll(); // uhhh
                setContentPane(background);
            }
        }
    };

    private void addActions() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tabbedEditorPane.addChangeListener(tabbedChangedListener);

        // Shortcut Setup
        newMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.META_MASK));
        openMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.META_MASK));
        saveMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.META_MASK));
        undoMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.META_MASK));
        redoMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.META_MASK));
        cutMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.META_MASK));
        copyMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.META_MASK));
        pasteMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.META_MASK));
        selectAllMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.META_MASK));
        scRunMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0));

        newMI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ge) {
//                setContentPane(tabbedEditorPane);
                allowItemsSelectable(true);
                Document newDocument = new Document(scm);
                tabbedEditorPane.addTab("new", newDocument);
                tabbedEditorPane.setSelectedComponent(newDocument); ////////// HMMMMMMMM
                newDocument.addListener(delegate);  // Delegate: Add's our delegate to to document to manage connection between editor and document
            }
        });
        openMI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ge) {
                // Create new file chooser and prompt for the file
                allowItemsSelectable(true);
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("."));
                fileChooser.setFileFilter(new FileNameExtensionFilter("txt files (*.txt)", "txt", "text"));
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    Document newDocument = new Document(selectedFile, scm);
                    tabbedEditorPane.addTab(newDocument.getName(), newDocument);
                    tabbedEditorPane.setSelectedComponent(newDocument); ////////// HMMMMMMMM
                    newDocument.addListener(delegate);  // Delegate: Add's our delegate to to document to manage connection between editor and document
                }
            }
        });
        closeMI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ge) {
                // getSelectedIndex returns -1 if there are no tabs
                if (tabbedEditorPane.getTabCount() >= 0)
                    tabbedEditorPane.remove(tabbedEditorPane.getSelectedComponent());
                if (tabbedEditorPane.getTabCount() == 0)
                    allowItemsSelectable(false);
            }
        });
        saveMI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ge) {
                // getSelectedIndex returns -1 if there are no tabs
                // Call Document save method to write the file and change the tab name
                if (tabbedEditorPane.getSelectedIndex() >= 0) {
                    ((Document) tabbedEditorPane.getSelectedComponent()).save();
                    tabbedEditorPane.setTitleAt(tabbedEditorPane.getSelectedIndex(), ((Document) tabbedEditorPane.getSelectedComponent()).getName());
                }
            }
        });
        selectAllMI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ge) {
                ((Document) tabbedEditorPane.getSelectedComponent()).selectAll();
            }
        });
        cutMI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ge) {
                ((Document) tabbedEditorPane.getSelectedComponent()).cut();
            }
        });
        copyMI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ge) {
                ((Document) tabbedEditorPane.getSelectedComponent()).copy();
            }
        });
        pasteMI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ge) {
                ((Document) tabbedEditorPane.getSelectedComponent()).paste();
            }
        });
        scRunMI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ge) {
                tabbedEditorPane.requestFocus();
                ((Document) tabbedEditorPane.getSelectedComponent()).runSC();
            }
        });
        scConfigureMI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ge) {
                tabbedEditorPane.requestFocus();
                ((Document) tabbedEditorPane.getSelectedComponent()).runConfigure();
            }
        });
        undoMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    currentManager.undo();
                } catch (CannotRedoException cre) {
                    cre.printStackTrace();
                }
                updateMI();
            }
        });
        redoMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    currentManager.redo();
                } catch (CannotRedoException cre) {
                    cre.printStackTrace();
                }
                updateMI();
            }
        });
    }

    // Manage when undo and redo are available
    public void updateMI(){
        undoMI.setEnabled(currentManager.canUndo());
        redoMI.setEnabled(currentManager.canRedo());
    }

    // Helper function to toggle allowable items
    private void allowItemsSelectable(boolean bool) {
        cutMI.setEnabled(bool);
        copyMI.setEnabled(bool);
        pasteMI.setEnabled(bool);
        selectAllMI.setEnabled(bool);
        closeMI.setEnabled(bool);
        scRunMI.setEnabled(bool);
        scConfigureMI.setEnabled(bool);
    }

    // Mark: Delegate
    // This class acts as a delegate between the Document and Editor
    // It maintains an instance of an editor and is passed "this" when the editor
    // is instantiated. This is done to get a reference to our SCM that is
    // a member of our editor class
    class MenuItemDelegate implements MenuItemListener {
        PulseApplication editor;
        MenuItemDelegate(PulseApplication editor) {
            this.editor = editor;
        }
        @Override
        public void updateUndoMI() { updateMI(); }
    }

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

        PulseApplication gui = new PulseApplication();
    }

    // Background panel inner class to display centered logo
    class BackgroundPanel extends JPanel {
        private Image img;
        public BackgroundPanel(Image img) {
            this.img = img;
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.translate(this.getParent().getWidth() / 2, this.getParent().getHeight() / 2);
            g2d.translate(-100 / 2, -100/2);
            g2d.drawImage(img, 0, 0, 100, 100, null);
        }
    }


}