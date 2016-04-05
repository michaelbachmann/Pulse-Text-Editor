package client.Views;

import uielements.ColorSet;
import uielements.componentuis.FlatButtonUI;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class SavePanel extends JPanel {
    private JLabel selectLabel, fileLabel;
    private JList<String> fileList;
    private JPanel mainPanel, rowOne, rowTwo;
    private JScrollPane scrollPane;
    private JButton cancel, save;
    private GridBagConstraints gbc;
    private JTextField textField;
    private String fileName;

    public SavePanel(Vector<String> files, String fileName) {
        super();
        setLayout(new BorderLayout());
        this.fileName = fileName;
        instantiateVariables(files);
        createGUI();
        setVisible(true);
    }

    public JTextField getTextField() {
        return textField;
    }

    public JButton getCancel() {
        return cancel;
    }

    public JButton getSave() {
        return save;
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(420,350);
    }

    public void instantiateVariables(Vector<String> files){
        fileList = new JList<>();
        fileList.setListData(new Vector<String>(files));
        scrollPane = new JScrollPane(fileList);
        scrollPane.setSize(390,200);
        scrollPane.setPreferredSize(new Dimension(390,150));
        mainPanel = new JPanel(new GridBagLayout());
        rowOne = new JPanel(new FlowLayout());
        rowTwo = new JPanel(new FlowLayout());
        selectLabel = new JLabel("<html><b>Select a File: </html></b>");
//        selectLabel = new JLabel("Select a File:");
        fileLabel = new JLabel("<html><b>File: </html></b>");
        cancel = new JButton("Cancel");
        save = new JButton("Save");
        textField = new JTextField(fileName, 29);
        gbc = new GridBagConstraints();
    }

    public void createGUI(){
        fileList.setBackground(ColorSet.DARKBLUE);
        fileList.setBorder(BorderFactory.createEmptyBorder());
        fileList.setForeground(ColorSet.BEIGE);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        selectLabel.setForeground(ColorSet.SALMON);
        fileLabel.setForeground(ColorSet.SALMON);
        cancel.setUI(new FlatButtonUI());
        save.setUI(new FlatButtonUI());
        mainPanel.setBackground(ColorSet.DARKER_BLUE);
        rowTwo.setBackground(ColorSet.DARKER_BLUE);
        rowOne.setBackground(ColorSet.DARKER_BLUE);
        textField.setBorder(BorderFactory.createEmptyBorder());
        textField.setBackground(ColorSet.DARKBLUE);
        textField.setForeground(ColorSet.BEIGE);



        rowOne.add(fileLabel, BorderLayout.NORTH);
        rowOne.add(textField, BorderLayout.NORTH);
        rowTwo.add(cancel);
        rowTwo.add(save);
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        mainPanel.add(selectLabel, gbc);
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(scrollPane, gbc);
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LAST_LINE_START;
        mainPanel.add(rowOne, gbc);
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        mainPanel.add(rowTwo, gbc);
        add(mainPanel, BorderLayout.CENTER);

    }

}
