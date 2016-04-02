package client.texteditor;

import client.listeners.MenuItemListener;
import spellchecker.SpellCheckManager;
import spellchecker.StringTuple;
import uielements.ColorSet;
import uielements.ConfigurePanel;
import uielements.FlatScrollBarUI;
import uielements.SpellCheckPanel;

import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class Document extends JPanel {

    // MARK: Member Variables
    public static final long serialVersionUID = 1;
    private int indexStart, indexEnd;
    private Boolean configurePanelActive, spellCheckPanelActive;
    private String name, text;

    private JTextArea textArea;
    private JScrollPane scrollPane;

    private SpellCheckPanel spellCheckPanel;
    private ConfigurePanel configurePanel;
    private UndoManager undoManager;
    private SpellCheckManager currentSCM;

    private Queue<StringTuple> misspelledQueue;
    private ArrayList<String> suggestionList;
    private ArrayList<MenuItemListener> listeners;

    // Delegate: Adds a listener to our list
    public void addListener(MenuItemListener toAdd) { listeners.add(toAdd); }
    // Delegate: Event that tells our menu items to update
    public void updateMenuItems() {
        for (MenuItemListener listener: listeners)
            listener.updateUndoMI();
    }


    // Setup Function
    public void setUp() {
        indexStart = indexEnd = 0;
        configurePanelActive = spellCheckPanelActive = false;

        scrollPane = new JScrollPane();

        textArea = new JTextArea();
        spellCheckPanel = new SpellCheckPanel();
        configurePanel = new ConfigurePanel();
        undoManager = new UndoManager();
        listeners = new ArrayList<MenuItemListener>();

        scrollPane.getVerticalScrollBar().setUI(new FlatScrollBarUI());
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        textArea.setSelectionColor(ColorSet.HOVER_COLOR);
        textArea.setLineWrap(true);
        textArea.setMargin(new Insets(10,10,10,10) );

    }

    // MARK: Constructors
    // New Document Constructor
    public Document(SpellCheckManager scm) {
        super(new BorderLayout());
        currentSCM = scm;
        name = "new";
        setUp();
        scrollPane.getViewport().add(textArea);
        add(scrollPane, BorderLayout.CENTER);
        addActions();
    }

    // Preexisting Document Constructor
    public Document(File file, SpellCheckManager scm) {
        super(new BorderLayout());
        currentSCM = scm;
        name = file.getName();
        BufferedReader br = null;
        setUp();
        try {
            br = new BufferedReader(new FileReader(file));
            // Editor Pane
            textArea.read(br, null);
            scrollPane.getViewport().add(textArea);
        } catch (FileNotFoundException fnfe) {
            System.out.println("FileNotFoundException: " + fnfe.getMessage());
        } catch (IOException ioe) {
            System.out.println("UIExceotion: " + ioe.getMessage());
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException ioe) {
                System.out.println("IOException closing file: " + ioe.getMessage());
            }
        }
        add(scrollPane);
        addActions();
    }

    // Start Spell Checker
    public void runSC() {
        spellCheckPanel.setVisible(true);
        spellCheckPanelActive = true;
        if (configurePanelActive) {
//            configurePanel.setVisible(false); //////////////////////////////////////////////////////////////
            configurePanelActive = false;
        }

        // Get Tuple ArrayList of misspelled words, format = <originalspelling, cleanedupspelling>
        misspelledQueue = new LinkedList<StringTuple>(currentSCM.getMispelledList(textArea.getText()));
        if (spellCheckComplete()) return;  // If nothing to check exit
        suggestionList = currentSCM.getSuggestions(misspelledQueue.element().getCleaned());
        // UpperCase fix, temporary
        if (Character.isUpperCase(misspelledQueue.element().getCleaned().charAt(0)) ) {
            for (int i = 0; i < suggestionList.size(); i++){
                suggestionList.set(i, Character.toUpperCase(suggestionList.get(i).charAt(0)) + suggestionList.get(i).substring(1));
            }
        }
        // populate panel
        spellCheckPanel.populatePanel(suggestionList, misspelledQueue.element().getCleaned());
        text = textArea.getText();
        indexStart = text.indexOf(misspelledQueue.element().getOriginal());
        indexEnd = indexStart + misspelledQueue.element().getCleaned().length();

        // Get first arraylist of suggestions
        focusMisspelledWord(indexStart,indexEnd);
        add(spellCheckPanel, BorderLayout.EAST);
        textArea.setEditable(false);
    }

    // Continue Running Spell Check
    public void updateSCM() {
        misspelledQueue.remove();
        if (spellCheckComplete()) return;

        // Generate suggestions
        suggestionList.clear();
        suggestionList = currentSCM.getSuggestions(misspelledQueue.element().getCleaned());
        // UpperCase fix, temporary
        if (Character.isUpperCase(misspelledQueue.element().getCleaned().charAt(0)) ) {
            for (int i = 0; i < suggestionList.size(); i++){
                suggestionList.set(i, Character.toUpperCase(suggestionList.get(i).charAt(0)) + suggestionList.get(i).substring(1));
            }
        }
        // Populate Shit
        spellCheckPanel.populatePanel(suggestionList, misspelledQueue.element().getCleaned());
        text = textArea.getText();
        indexStart = text.indexOf(misspelledQueue.element().getOriginal());
        indexEnd = indexStart + misspelledQueue.element().getCleaned().length();
        focusMisspelledWord(indexStart,indexEnd);
    }

    // Start SC Configure
    public void runConfigure() {
        configurePanel.setVisible(true);
        configurePanelActive = true;
        textArea.setEditable(true);
        if (spellCheckPanelActive) {
            spellCheckPanel.setVisible(false);
            spellCheckPanelActive = false;
        }
        add(configurePanel, BorderLayout.EAST);
    }

    // MARK: Actions

    public void addActions() {
        // Add listener to text area for UndoManager
        textArea.getDocument().addUndoableEditListener(new UndoableEditListener(){
            public void undoableEditHappened(UndoableEditEvent e) {
                updateMenuItems();
                undoManager.addEdit(e.getEdit());
            }
        });
        // MARK: Configuration Panel
        configurePanel.getClose().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                configurePanelActive = false;
                configurePanel.setVisible(false);
            }
        });
        configurePanel.getDictionaryButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("."));
                fileChooser.setFileFilter(new FileNameExtensionFilter("Wordlist files (*.wl)", "wl"));
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    currentSCM.setDictionaryFile(selectedFile.getPath());
                }
            }
        });
        configurePanel.getKeyboardButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("."));
                fileChooser.setFileFilter(new FileNameExtensionFilter("Keyboard files (*.kb)", "kb"));
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    currentSCM.setKeyboardFile(selectedFile.getPath());
                }
            }
        });
        // MARK: SpellCheck Panel
        spellCheckPanel.getClose().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spellCheckPanelActive = false;
                textArea.setEditable(true);
                spellCheckPanel.setVisible(false);
            }
        });
        spellCheckPanel.getIgnore().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { updateSCM(); }
        });


        spellCheckPanel.getChange().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                replaceWord(spellCheckPanel.getComboBoxValue());
                updateSCM();
            }
        });
        // Add's word to Trie and Dictionary
        spellCheckPanel.getAdd().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentSCM.appendWord(misspelledQueue.element().getCleaned());
                updateSCM();
            }
        });
    }

    // MARK: Member Functions

    // Helper function for SpellChecker, refocuses and selects word given start index and end index
    private void focusMisspelledWord(int indexStart, int indexEnd) {
        textArea.requestFocus();
        textArea.setCaretPosition(indexStart);
        textArea.setSelectionStart(indexStart);
        textArea.setSelectionEnd(indexEnd);
    }

    // Replaces the text from indexStart to indexEnd with str
    private void replaceWord(String str) {
        textArea.requestFocus();
        textArea.replaceRange(str, indexStart, indexEnd);
    }
    // Check to see If Spell Check is done
    public Boolean spellCheckComplete(){
        if (misspelledQueue.isEmpty()) {  // if spelled check is done
            spellCheckPanel.setVisible(false);
            spellCheckPanelActive = false;
            textArea.setEditable(true);
            return true;
        }
        return false;
    }

    // Saves the document
    public void save(){
        // Create New JFileChooser with approveSelection Overridden to show a confirm dialog
        JFileChooser fileChooser = new JFileChooser(){
            private static final long serialVersionUID = 2;
            @Override
            public void approveSelection(){
                File file = getSelectedFile();
                // If the file exists then show a prompt, otherwise just approve the choice
                if(file.exists() && getDialogType() == SAVE_DIALOG){
                    int result = JOptionPane.showConfirmDialog(this,(file.getName() + " already exists\nDo you want to replace it?"),"Confirm Save As",JOptionPane.YES_NO_OPTION);
                    switch(result){
                        case JOptionPane.YES_OPTION:
                            super.approveSelection();
                            return;
                        case JOptionPane.NO_OPTION:
                            return;
                        case JOptionPane.CLOSED_OPTION:
                            return;
                    }
                }
                super.approveSelection();
            }
        };

        // Filter only show text files
        fileChooser.setFileFilter(new FileNameExtensionFilter("txt files (*.txt)", "txt", "text"));
        fileChooser.setDialogTitle("Specify a file to save");
        // If it already exists don't rename it just create a new File Object with the original file
        if (name != "new") fileChooser.setSelectedFile(new File(name));
        /////////////////// ***CAN'T REMEMBER WHY I DID THIS ^, CHECK IF IMPORTANT
        // Get the return value of the selected action of the user
        int returnValue = fileChooser.showSaveDialog(null);
        // If it's approved write the file
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            name = selectedFile.getName();
            try {
                FileWriter fileWriter = new FileWriter(selectedFile);
                textArea.write(fileWriter);
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // MARK: Getter's and Setters
    public String getName () { return name; }
    public String getText () { return textArea.getText(); }
    public UndoManager getCurrentManager () { return undoManager; }
    public void copy () { textArea.copy(); }
    public void paste () { textArea.paste(); }
    public void selectAll () { textArea.selectAll(); }
    public void cut () { textArea.cut(); }
    public void setCurrentSCM ( SpellCheckManager scm ) { currentSCM = scm; }
}