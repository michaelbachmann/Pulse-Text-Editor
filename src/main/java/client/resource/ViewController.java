//package client;
//
//import resources.Document;
//import spellchecker.SpellCheckManager;
//import uielements.ColorSet;
//import uielements.UIConstants;
//
//import javax.swing.*;
//import javax.swing.filechooser.FileNameExtensionFilter;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.KeyEvent;
//import java.io.File;
//import java.util.*;
//
///**
// * Created by Michael on 4/3/16.
// */
//public class ViewController extends JPanel {
//    JMenuBar menuBar;
//    private JMenu fileMenu, editMenu, spellCheckMenu;
//    private JMenuItem newMI, openMI, saveMI, closeMI, undoMI, redoMI, cutMI, copyMI, pasteMI, selectAllMI, scRunMI, scConfigureMI;
//    private ImageIcon newIcon, openIcon, saveIcon, closeIcon, copyIcon, pasteIcon, selectAllIcon, scIcon, configIcon, cutIcon, redoIcon, undoIcon;
//    private java.util.List<JMenuItem> menuList;
//    private SplashPanel splashPanel;
//    private LoginPanel loginPanel;
//    private EditorPanel editorPanel;
//    private SpellCheckManager scm;
//
//    public ViewController(JMenuBar menuBar, SpellCheckManager scm) {
//        this.menuBar = menuBar;
//        this.scm = scm;
//        setLayout(new CardLayout());
//        editorPanel = new EditorPanel();
//        splashPanel = new SplashPanel();
//        loginPanel = new LoginPanel();
//        instantiateComponents();
//        createGUI();
//        addActions();
//        add("Splash", splashPanel);
//        add("Editor", editorPanel);
//        add("Login", loginPanel);
//    }
//
//    private void setupIcons() {
//        //All credit goes to http://somerandomdude.com/work/sanscons/
//        newIcon = new ImageIcon(UIConstants.ICON_NEW);
//        openIcon = new ImageIcon(UIConstants.ICON_OPEN);
//        saveIcon = new ImageIcon(UIConstants.ICON_SAVE);
//        closeIcon = new ImageIcon(UIConstants.ICON_CLOSE);
//        copyIcon = new ImageIcon(UIConstants.ICON_COPY);
//        pasteIcon = new ImageIcon(UIConstants.ICON_PASTE);
//        scIcon = new ImageIcon(UIConstants.ICON_SPELLCHECK);
//        configIcon = new ImageIcon(UIConstants.ICON_CONFIG);
//        cutIcon = new ImageIcon(UIConstants.ICON_CUT);
//        redoIcon = new ImageIcon(UIConstants.ICON_REDO);
//        undoIcon = new ImageIcon(UIConstants.ICON_UNDO);
//        selectAllIcon = new ImageIcon(UIConstants.ICON_SELECTALL);
//    }
//
//
//    // Instantiate components
//    private void instantiateComponents () {
//        // SpellChecker
//        // Setup All Icons
//        setupIcons();
//        // Menu Bar Setup
//        menuBar = new JMenuBar() {
//            @Override
//            public void paintComponent(Graphics g) {
//                Dimension size = this.getSize();
//                g.drawImage(Toolkit.getDefaultToolkit().getImage(UIConstants.MENU_BAR_IMG),0,0, size.width, size.height, this);
//            }
//        };
//        fileMenu = new JMenu("File") {
//            @Override
//            public JPopupMenu getPopupMenu() {
//                JPopupMenu menu = super.getPopupMenu();
//                menu.setBorder(BorderFactory.createEmptyBorder());
//                return menu;
//            }
//        };
//        editMenu = new JMenu("Edit") {
//            @Override
//            public JPopupMenu getPopupMenu() {
//                JPopupMenu menu = super.getPopupMenu();
//                menu.setBorder(BorderFactory.createEmptyBorder());
//                return menu;
//            }
//        };
//        spellCheckMenu = new JMenu("Spell Check") {
//            @Override
//            public JPopupMenu getPopupMenu() {
//                JPopupMenu menu = super.getPopupMenu();
//                menu.setBorder(BorderFactory.createEmptyBorder());
//                return menu;
//            }
//        };
//
////         Menu Items
//        newMI = new JMenuItem("New", newIcon);
//        openMI = new JMenuItem("Open", openIcon);
//        saveMI = new JMenuItem("Save", saveIcon);
//        closeMI = new JMenuItem("Close", closeIcon);
//        undoMI = new JMenuItem("Undo", undoIcon);
//        redoMI = new JMenuItem("Redo", redoIcon);
//        cutMI = new JMenuItem("Cut", cutIcon);
//        copyMI = new JMenuItem("Copy", copyIcon);
//        pasteMI = new JMenuItem("Paste", pasteIcon);
//        selectAllMI = new JMenuItem("Select All", selectAllIcon);
//        scRunMI = new JMenuItem("Run", scIcon);
//        scConfigureMI = new JMenuItem("Configure", configIcon);
//        menuList = new ArrayList<>();
//        menuList.addAll(Arrays.asList(newMI,openMI,saveMI,closeMI,
//                undoMI,redoMI,cutMI,copyMI,pasteMI,
//                selectAllMI,scRunMI,scConfigureMI));
//    }
//
//    public void createGUI() {
//        fileMenu.add(newMI);
//        fileMenu.add(openMI);
//        fileMenu.add(saveMI);
//        fileMenu.add(closeMI);
//        editMenu.add(undoMI);
//        editMenu.add(redoMI);
//        editMenu.add(cutMI);
//        editMenu.add(copyMI);
//        editMenu.add(pasteMI);
//        editMenu.add(selectAllMI);
//        spellCheckMenu.add(scRunMI);
//        spellCheckMenu.add(scConfigureMI);
//        menuBar.add(fileMenu);
//        menuBar.add(editMenu);
//        menuBar.add(spellCheckMenu);
//        tabOpen(false);
//        undoMI.setEnabled(false);
//        redoMI.setEnabled(false);
//        setUpMenuLook();
//    }
//
//    // Setup JMenuItems
//    private void setUpMenuLook() {
//        // Strip all the borders
//        menuBar.setBorder(BorderFactory.createEmptyBorder());
//        fileMenu.setBorder(BorderFactory.createEmptyBorder(8,8,8,4));
//        editMenu.setBorder(BorderFactory.createEmptyBorder(8,8,8,4));
//        spellCheckMenu.setBorder(BorderFactory.createEmptyBorder(8,8,8,0));
//        menuBar.setBackground(ColorSet.DARKBLUE);
//        fileMenu.setForeground(ColorSet.TURQUOISE);
//        editMenu.setForeground(ColorSet.TURQUOISE);
//        spellCheckMenu.setForeground(ColorSet.TURQUOISE);
//        for (JMenuItem item : menuList) {
//            item.setBackground(ColorSet.DARKBLUE);
//            item.setForeground(ColorSet.BEIGE);
//            item.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
//        }
//    }
//
//    private void addActions() {
//
//        // Shortcut Setup
//        newMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.META_MASK));
//        openMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.META_MASK));
//        saveMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.META_MASK));
//        undoMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.META_MASK));
//        redoMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.META_MASK));
//        cutMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.META_MASK));
//        copyMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.META_MASK));
//        pasteMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.META_MASK));
//        selectAllMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.META_MASK));
//        scRunMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0));
//
//        newMI.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent ge) {
//                tabOpen(true);
//                Document newDocument = new Document(scm);
//                editorPanel.newTab("new", newDocument);
//            }
//        });
//        openMI.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent ge) {
//                // Create new file chooser and prompt for the file
//                tabOpen(true);
//                JFileChooser fileChooser = new JFileChooser();
//                fileChooser.setCurrentDirectory(new File("."));
//                fileChooser.setFileFilter(new FileNameExtensionFilter("txt files (*.txt)", "txt", "text"));
//                int returnValue = fileChooser.showOpenDialog(null);
//                if (returnValue == JFileChooser.APPROVE_OPTION) {
//                    File selectedFile = fileChooser.getSelectedFile();
//                    Document newDocument = new Document(selectedFile, scm);
//                    editorPanel.newTab(newDocument.getName(), newDocument);
//                }
//            }
//        });
//        closeMI.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent ge) {
//                if (editorPanel.closeTab() == -1)
//                    tabOpen(false);
//            }
//        });
//        saveMI.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent ge) {
//                editorPanel.saveTab();
//            }
//        });
//        selectAllMI.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent ge) {
//                editorPanel.selectAll();
//            }
//        });
//        cutMI.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent ge) {
//                editorPanel.cut();
//            }
//        });
//        copyMI.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent ge) {
//                editorPanel.copy();
//            }
//        });
//        pasteMI.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent ge) {
//                editorPanel.paste();
//            }
//        });
//        scRunMI.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent ge) {
//                editorPanel.runSpellCheck();
//            }
//        });
//        scConfigureMI.addActionListener(new ActionListener() {
//            //            editorPanel.
//            public void actionPerformed(ActionEvent ge) {
//                editorPanel.runConfigure();
//            }
//        });
//        undoMI.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                editorPanel.tryUndo();
//            }
//        });
//        redoMI.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                editorPanel.tryRedo();
//            }
//        });
//
//    }
//
//    // Manage when undo and redo are available
////    public void updateMI(){
////        undoMI.setEnabled(currentManager.canUndo());
////        redoMI.setEnabled(currentManager.canRedo());
////    }
//
//    // Helper function to toggle allowable items
//    public void tabOpen(boolean bool) {
//        cutMI.setEnabled(bool);
//        copyMI.setEnabled(bool);
//        pasteMI.setEnabled(bool);
//        selectAllMI.setEnabled(bool);
//        closeMI.setEnabled(bool);
//        scRunMI.setEnabled(bool);
//        scConfigureMI.setEnabled(bool);
//    }
//
//}
