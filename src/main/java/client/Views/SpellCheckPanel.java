package client.Views;

import uielements.ColorSet;
import uielements.componentuis.FlatButtonUI;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class SpellCheckPanel extends JPanel {
    public static final long serialVersionUID = 1;
    private JLabel currentWord;
    private JButton ignore, add, change, close;
    private JComboBox<String> comboBox;
    private ArrayList<String> mispelledWords;
    JLabel spelling;
    JPanel testPanel;
    Font labelFont;

    public SpellCheckPanel() {
        super(new BorderLayout());
        // Font for Label
        try {
            //create the font to use. Specify the size!
            labelFont = Font.createFont(Font.TRUETYPE_FONT, new File("resources/font/OpenSans-Regular.ttf")).deriveFont(Font.PLAIN, 16f);
            GraphicsEnvironment gc = GraphicsEnvironment.getLocalGraphicsEnvironment();
            gc.registerFont(labelFont);
        } catch(FontFormatException e) {
            System.out.println(e.getMessage());
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        setBackground(ColorSet.TURQUOISE);
        instantiateComponents();
        createGUI();
    }

    public void updateWord(String mispelledWord) {
        currentWord.setText("Spelling: " + mispelledWord);
    }

    public void populatePanel(ArrayList<String> suggestions, String mispelledWord) {
        currentWord.setText("Spelling: " + mispelledWord);
        comboBox.removeAllItems();
        for (String word : suggestions)
            comboBox.addItem(word);
    }

    public void populateMispelledWords(ArrayList<String> word) {
        mispelledWords = word;
        currentWord.setText("Spelling: " + mispelledWords.get(0));
    }

    public String getComboBoxValue() {
        return (String)comboBox.getSelectedItem();
    }


    private void instantiateComponents() {
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        testPanel = new JPanel(new GridLayout(4,2,5,5));
        testPanel.setPreferredSize(new Dimension( 240, 125 ));
        testPanel.setBackground(ColorSet.TURQUOISE);
        spelling = new JLabel("Spell Check");
        currentWord = new JLabel("Spelling: ");
        ignore = new JButton("Ignore");
        add = new JButton("Add");
        change =  new JButton("Change");
        close = new JButton("Close");
        comboBox = new JComboBox<String>();
        comboBox.setUI(new FlatButtonUI.FlatComboBoxUI());

        spelling.setForeground(ColorSet.DARKBLUE);
        spelling.setFont(labelFont);
        currentWord.setForeground(ColorSet.BEIGE);
    }



    private void createGUI() {
        close.setUI(new FlatButtonUI());
        ignore.setUI(new FlatButtonUI());
        add.setUI(new FlatButtonUI());
        change.setUI(new FlatButtonUI());

        testPanel.add(spelling);
        testPanel.add(new JLabel(""));
        testPanel.add(currentWord);
        testPanel.add(new JLabel(""));
        testPanel.add(ignore);
        testPanel.add(add);
        testPanel.add(comboBox);
        testPanel.add(change);

        add(testPanel, BorderLayout.NORTH);
        add(close, BorderLayout.SOUTH);
    }

    public void setComboBox(ArrayList<String> array) {
        for (String str : array) {
            comboBox.addItem(str);
        }
    }

    public JButton getIgnore() {
        return ignore;
    }
    public JButton getChange() {
        return change;
    }
    public JButton getAdd() {
        return add;
    }
    public JButton getClose() {
        return close;
    }
}
