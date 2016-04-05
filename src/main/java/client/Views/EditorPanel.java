package client.Views;

import resources.Document;
import spellchecker.SpellCheckManager;
import uielements.ColorSet;
import uielements.componentuis.FlatButtonUI;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.UndoManager;
import java.awt.*;


public class EditorPanel extends JPanel {
    private JTabbedPane tabbedEditorPane;
    private UndoManager currentManager;
    private JMenuItem undo;
    private JMenuItem redo;
//    private Map<String,JMenuItem> menuItemMap;

//     Manage when undo and redo are available
//    public void updateMI(){
//        undo.setEnabled(currentManager.canUndo());
//        redo.setEnabled(currentManager.canRedo());
//    }
    private SpellCheckManager scm;

    public EditorPanel(JMenuItem undo, JMenuItem redo) {
        this.undo = undo;
        this.redo = redo;
        setLayout(new BorderLayout());
//        this.menuItemMap = menuItemMap;
        tabbedEditorPane = new JTabbedPane();
        tabbedEditorPane.setUI(new FlatButtonUI.FlatTabbedPaneUI());
        tabbedEditorPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                if (tabbedEditorPane.getTabCount() > 0) {
                    currentManager = ((Document) tabbedEditorPane.getSelectedComponent()).getCurrentManager();
//                    updateMI();
                }
                if (tabbedEditorPane.getTabCount() <= 0) {

                }
            }
        });
        add(tabbedEditorPane, BorderLayout.CENTER);
        setBackground(ColorSet.DARK_BLUE_MIDDLE);
    }

    public String getCurrentTabName(){
        String toReturn = "";
        toReturn = tabbedEditorPane.getTitleAt(tabbedEditorPane.getSelectedIndex());
        return toReturn;
    }

    public void setCurrentTabName(String name){
        tabbedEditorPane.setTitleAt(tabbedEditorPane.getSelectedIndex(),name);
    }

    public void newTab(String title, Document document) {
        tabbedEditorPane.addTab("new", document);
        tabbedEditorPane.setSelectedComponent(document); ////////// HMMMMMMMM
    }

    // returns 0 if no tabs left
    public int closeTab() {
        if (tabbedEditorPane.getTabCount() > 0)
            tabbedEditorPane.remove(tabbedEditorPane.getSelectedComponent());
        if (tabbedEditorPane.getTabCount() <= 0)
            return -1;
        return tabbedEditorPane.getTabCount();

    }

    public void saveTab() {
        if (tabbedEditorPane.getSelectedIndex() >= 0) {
            ((Document) tabbedEditorPane.getSelectedComponent()).save();
            tabbedEditorPane.setTitleAt(tabbedEditorPane.getSelectedIndex(), ((Document) tabbedEditorPane.getSelectedComponent()).getName());
        }
    }

    public void selectAll() {
        ((Document) tabbedEditorPane.getSelectedComponent()).selectAll();
    }

    public void cut() {
        ((Document) tabbedEditorPane.getSelectedComponent()).cut();
    }

    public void copy() {
        ((Document) tabbedEditorPane.getSelectedComponent()).copy();
    }

    public void paste() {
        ((Document) tabbedEditorPane.getSelectedComponent()).paste();
    }
    public void runSpellCheck() {
        tabbedEditorPane.requestFocus();
        ((Document) tabbedEditorPane.getSelectedComponent()).runSC();
    }

    public void runConfigure() {
        tabbedEditorPane.requestFocus();
        ((Document) tabbedEditorPane.getSelectedComponent()).runConfigure();
    }

    public boolean isTabs() {
        if (tabbedEditorPane.getTabCount() == 0) return true;
        else return false;
    }

    public EditorPanel getPanel() {
        return this;
    }

    public void tryUndo() {
        try {
            currentManager.undo();
        } catch (CannotRedoException cre) {
            cre.printStackTrace();
        }
//        updateMI();
    }

    public void tryRedo() {
        try {
            currentManager.redo();
        } catch (CannotRedoException cre) {
            cre.printStackTrace();
        }
//        updateMI();
    }

    ChangeListener tabbedChangedListener = new ChangeListener() {
        public void stateChanged(ChangeEvent changeEvent) {
            if (tabbedEditorPane.getTabCount() != 0) {
//                setContentPane(tabbedEditorPane);
                currentManager = ((Document) tabbedEditorPane.getSelectedComponent()).getCurrentManager();
            } else {
//                getContentPane().removeAll(); // uhhh
//                setContentPane(backgroundPanel);
            }
        }
    };

}
