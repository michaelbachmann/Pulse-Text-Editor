package client;

import com.apple.eawt.Application;
import resources.Document;
import spellchecker.SpellCheckManager;
import uielements.ColorSet;
import uielements.Constantsssss;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class ClientGUI extends JFrame {

    // MARK: Member Variables
    private static final long serialVersionUID = 1L;
    private JMenuBar menuBar;
    private JMenu fileMenu, editMenu, spellCheckMenu;
    private JMenuItem newMI, openMI, saveMI, closeMI, undoMI, redoMI, cutMI, copyMI, pasteMI, selectAllMI, scRunMI, scConfigureMI;
    private List<JMenuItem> menuList;
//    private JTabbedPane tabbedEditorPane;
    private SpellCheckManager scm;
//    private UndoManager currentManager;
    // Delegate: Class to manage events between editor and documents
//    private MenuItemDelegate delegate = new MenuItemDelegate(this);
    private ImageIcon newIcon, openIcon, saveIcon, closeIcon, copyIcon, pasteIcon, selectAllIcon, scIcon, configIcon, cutIcon, redoIcon, undoIcon;
    private SplashPanel backgroundPanel;
    private EditorPanel editorPanel;
    private Map<String,JMenuItem> menuItems;
    private JPanel viewController;

    // MARK: Constructor
    public ClientGUI () {
        super("Pulse");
//        menuItems = new HashMap<>();
        instantiateComponents();
        createGUI();
        addActions();
        setupOSXIcon();
        setupFont();
        editorPanel = new EditorPanel(undoMI, redoMI);
        viewController = new JPanel(new CardLayout());
        viewController.add("Editor", editorPanel);
        CardLayout cardLayout = (CardLayout) viewController.getLayout();
        cardLayout.show(viewController, "Editor");
        setContentPane(viewController);

        this.setVisible(true);
    }

    private void setupIcons() {
        //All credit goes to http://somerandomdude.com/work/sanscons/
        newIcon = new ImageIcon(Constantsssss.ICON_NEW);
        openIcon = new ImageIcon(Constantsssss.ICON_OPEN);
        saveIcon = new ImageIcon(Constantsssss.ICON_SAVE);
        closeIcon = new ImageIcon(Constantsssss.ICON_CLOSE);
        copyIcon = new ImageIcon(Constantsssss.ICON_COPY);
        pasteIcon = new ImageIcon(Constantsssss.ICON_PASTE);
        scIcon = new ImageIcon(Constantsssss.ICON_SPELLCHECK);
        configIcon = new ImageIcon(Constantsssss.ICON_CONFIG);
        cutIcon = new ImageIcon(Constantsssss.ICON_CUT);
        redoIcon = new ImageIcon(Constantsssss.ICON_REDO);
        undoIcon = new ImageIcon(Constantsssss.ICON_UNDO);
        selectAllIcon = new ImageIcon(Constantsssss.ICON_SELECTALL);
    }

    // Instantiate components
    private void instantiateComponents () {
        // SpellChecker
        scm = new SpellCheckManager();
//        pulseMenuBar = new PulseMenuBar();
        // Setup All Icons
        setupIcons();
//         Menu Bar Setup
        menuBar = new JMenuBar() {
            @Override
            public void paintComponent(Graphics g) {
                Dimension size = this.getSize();
                g.drawImage(Toolkit.getDefaultToolkit().getImage(Constantsssss.MENU_BAR_IMG),0,0, size.width, size.height, this);
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

//         Menu Items
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
//         add to map
//        for (JMenuItem item: menuList) {
//            menuItems.put(item.getText(), item);
//        }

//        tabbedEditorPane = new JTabbedPane();
//        tabbedEditorPane.setUI(new FlatButtonUI.FlatTabbedPaneUI());
    }

    private void createGUI() {
        setSize(Constants.APPLICATION_WIDTH,Constants.APPLICATION_HEIGHT);
        setLocationRelativeTo(null);
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
        tabOpen(false);
        undoMI.setEnabled(false);
        redoMI.setEnabled(false);
//        add(tabbedEditorPane);
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
//    ChangeListener tabbedChangedListener = new ChangeListener() {
//        public void stateChanged(ChangeEvent changeEvent) {
//            if (tabbedEditorPane.getTabCount() != 0) {
//                setContentPane(tabbedEditorPane);
//                currentManager = ((Document) tabbedEditorPane.getSelectedComponent()).getCurrentManager();
//            } else {
//                getContentPane().removeAll(); // uhhh
//                setContentPane(backgroundPanel);
//            }
//        }
//    };

    private void addActions() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        tabbedEditorPane.addChangeListener(tabbedChangedListener);

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
                tabOpen(true);
                Document newDocument = new Document(scm);
                editorPanel.newTab("new", newDocument);
//                tabbedEditorPane.addTab("new", newDocument);
//                tabbedEditorPane.setSelectedComponent(newDocument); ////////// HMMMMMMMM
//                newDocument.addListener(delegate);  // Delegate: Add's our delegate to to document to manage connection between editor and document
            }
        });
        openMI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ge) {
                // Create new file chooser and prompt for the file
                tabOpen(true);
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("."));
                fileChooser.setFileFilter(new FileNameExtensionFilter("txt files (*.txt)", "txt", "text"));
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    Document newDocument = new Document(selectedFile, scm);
                    editorPanel.newTab(newDocument.getName(), newDocument);
//                    tabbedEditorPane.addTab(newDocument.getName(), newDocument);
//                    tabbedEditorPane.setSelectedComponent(newDocument); ////////// HMMMMMMMM
//                    newDocument.addListener(delegate);  // Delegate: Add's our delegate to to document to manage connection between editor and document
                }
            }
        });
        closeMI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ge) {
                // getSelectedIndex returns -1 if there are no tabs
//                if (tabbedEditorPane.getTabCount() >= 0)
//                    tabbedEditorPane.remove(tabbedEditorPane.getSelectedComponent());
//                if (tabbedEditorPane.getTabCount() == 0)
//                    allowItemsSelectable(false);
                if (editorPanel.closeTab() == -1)
                    tabOpen(false);
            }
        });
        saveMI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ge) {
                editorPanel.saveTab();
                // getSelectedIndex returns -1 if there are no tabs
                // Call Document save method to write the file and change the tab name
//                if (tabbedEditorPane.getSelectedIndex() >= 0) {
//                    ((Document) tabbedEditorPane.getSelectedComponent()).save();
//                    tabbedEditorPane.setTitleAt(tabbedEditorPane.getSelectedIndex(), ((Document) tabbedEditorPane.getSelectedComponent()).getName());
//                }
            }
        });
        selectAllMI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ge) {
                editorPanel.selectAll();
            }
        });
        cutMI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ge) {
                editorPanel.cut();
//                ((Document) tabbedEditorPane.getSelectedComponent()).cut();
            }
        });
        copyMI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ge) {
                editorPanel.copy();
//                ((Document) tabbedEditorPane.getSelectedComponent()).copy();
            }
        });
        pasteMI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ge) {
                editorPanel.paste();
//                ((Document) tabbedEditorPane.getSelectedComponent()).paste();
            }
        });
        scRunMI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ge) {
                editorPanel.runSpellCheck();
//                tabbedEditorPane.requestFocus();
//                ((Document) tabbedEditorPane.getSelectedComponent()).runSC();
            }
        });
        scConfigureMI.addActionListener(new ActionListener() {
//            editorPanel.
            public void actionPerformed(ActionEvent ge) {
                editorPanel.runConfigure();
//                tabbedEditorPane.requestFocus();
//                ((Document) tabbedEditorPane.getSelectedComponent()).runConfigure();
            }
        });
        undoMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editorPanel.tryUndo();
//                try {
//                    currentManager.undo();
//                } catch (CannotRedoException cre) {
//                    cre.printStackTrace();
//                }
//                updateMI();
            }
        });
        redoMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editorPanel.tryRedo();
//                try {
//                    currentManager.redo();
//                } catch (CannotRedoException cre) {
//                    cre.printStackTrace();
//                }
//                updateMI();
            }
        });
    }




    // Manage when undo and redo are available
//    public void updateMI(){
//        undoMI.setEnabled(currentManager.canUndo());
//        redoMI.setEnabled(currentManager.canRedo());
//    }

    // Helper function to toggle allowable items
    public void tabOpen(boolean bool) {
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
//    class MenuItemDelegate implements MenuItemListener {
//        ClientGUI editor;
//        MenuItemDelegate(ClientGUI editor) {
//            this.editor = editor;
//        }
//        @Override
//        public void updateUndoMI() { updateMI(); }
//    }


    /********************************************************************************
     * PURE GRAPHICS
     *
     *
     *
     *
     * */

    public void setupOSXIcon() {
        // Try to set the application icon for os x
        try {
            Application application = Application.getApplication();
            ImageIcon icon_image = new ImageIcon(Constantsssss.PULSE_ICON);
            Image dock_image = icon_image.getImage();
//            backgroundPanel = new SplashPanel(dock_image);
//            backgroundPanel.setVisible(true);
//            setContentPane(backgroundPanel);
            application.setDockIconImage(dock_image);
            setIconImage(icon_image.getImage());  // set for other OS's
            setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon(Constantsssss.CURSOR_ICON).getImage(),new Point(0,0),"custom cursor"));
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
            Font myFont = Font.createFont(Font.TRUETYPE_FONT, new File(Constantsssss.MAIN_FONT)).deriveFont(Font.PLAIN, 12f);
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

}