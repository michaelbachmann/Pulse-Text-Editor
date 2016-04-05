package client.Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class SaveDialog {

    public static final int YES = 0;
    public static final int NO = -1;
    public static final String EMPTY_STRING = "";

    private int choice = NO;
    private String fileName = EMPTY_STRING;

    public String getFileName() {
        return fileName;
    }

    public SaveDialog(String fileName) {
        this.fileName = fileName;
    }

    public int showYesNoMessage(String title, Vector<String> files) {
        SavePanel savePanel = new SavePanel(files, fileName);

        savePanel.getSave().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice = YES;
                fileName = savePanel.getTextField().getText();
                SwingUtilities.getWindowAncestor(savePanel).dispose();
            }
        });


        savePanel.getCancel().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice = NO;
                SwingUtilities.getWindowAncestor(savePanel).dispose();
            }
        });

        JDialog dialog = new JDialog();
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(new Dimension(410,275));
        dialog.setPreferredSize(new Dimension(410,275));
        dialog.setModal(true);
        dialog.setTitle(title);
        dialog.getContentPane().add(savePanel);
        dialog.pack();
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        return choice;
    }
}