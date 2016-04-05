package client.Views;

import uielements.ColorSet;
import uielements.componentuis.FlatButtonUI;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class ConfigurePanel extends JPanel {

    // MARK: Member Variables
    public static final long serialVersionUID = 1;
    private JLabel dictionaryFilePath, keyboardFilePath;
    private JButton dictionaryButton, keyboardButton,close;
    private GridBagConstraints gbc;
    private JPanel northPanel;

    // MARK: Constructors
    public ConfigurePanel() {
        super(new BorderLayout());
        instantiateComponents();
        createGUI();
    }

    // Instantiate Swing components
    private void instantiateComponents() {
        northPanel = new JPanel( new GridBagLayout() );
        northPanel.setPreferredSize( new Dimension( 240, 125 ) );
        northPanel.setBorder( BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(
                        EtchedBorder.RAISED, ColorSet.DARKBLUE,
                        ColorSet.DARKBLUE), "Configure") );
        dictionaryButton = new JButton( "Select WordList..." );
        keyboardButton = new JButton( "Select Keyboard..." );
        dictionaryFilePath = new JLabel( "Resources/wordlist.wl" );
        keyboardFilePath = new JLabel( "Resources/qwerty-us.kb" );
        close = new JButton( "Close" );
        gbc = new GridBagConstraints();
    }

    // Add components
    private void createGUI() {
        // Set up UI stuff
        setBackground(ColorSet.LIGHT_BLUE);
        dictionaryButton.setUI(new FlatButtonUI());
        keyboardButton.setUI(new FlatButtonUI());
        close.setUI(new FlatButtonUI());
        northPanel.setBackground(ColorSet.LIGHT_BLUE);
        dictionaryFilePath.setForeground(ColorSet.DARKBLUE);
        keyboardFilePath.setForeground(ColorSet.DARKBLUE);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 1;
        gbc.weightx = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        northPanel.add( dictionaryFilePath, gbc );
        gbc.gridy = 1;
        northPanel.add( dictionaryButton, gbc );
        gbc.gridy = 2;
        northPanel.add( keyboardFilePath, gbc );
        gbc.gridy = 3;
        northPanel.add( keyboardButton, gbc );
        add( northPanel, BorderLayout.NORTH );
        add( close, BorderLayout.SOUTH );
    }

    // MARK: Getter's and Setter's
    public JButton getDictionaryButton() { return dictionaryButton; }
    public JButton getKeyboardButton() { return keyboardButton; }
    public JButton getClose() { return close; }
    public String getKeyboardPath(){ return keyboardFilePath.getText(); }
    public String getDictionaryPath() { return dictionaryFilePath.getText(); }
    public void setKeyboardLabel( String path ) { keyboardFilePath.setText(path); }
    public void setDictionaryLabel( String path ) { dictionaryFilePath.setText(path); }
}
