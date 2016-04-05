//package client.Views;
//
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.Arrays;
//import java.util.Vector;
//
//public class SaveOnlineDialog extends JDialog {
//    private Vector<String> files;
//    private String[] list = {"FileOne.txt", "File2.txt", "XYZ.txt"};
//    public SaveOnlineDialog(JFrame parent, String title) {
//        super(parent, title);
//        System.out.println("creating the window..");
//        setResizable(false);
//        setLocationRelativeTo(null);
//
//        // Create a message
//        files = new Vector<>();
//        files.addAll(Arrays.asList(list));
//        SavePanel save = new SavePanel(files);
//        getContentPane().add(save, BorderLayout.CENTER);
//        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//        pack();
//        setVisible(true);
//    }
//
//    @Override
//    public Dimension getPreferredSize(){
//        return new Dimension(410,275);
//    }
//
//    // override the createRootPane inherited by the JDialog, to create the rootPane.
//    // create functionality to close the window when "Escape" button is pressed
//    public JRootPane createRootPane() {
//        JRootPane rootPane = new JRootPane();
//        KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");
//        Action action = new AbstractAction() {
//
//            private static final long serialVersionUID = 1L;
//
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("escaping..");
//                setVisible(false);
//                dispose();
//            }
//        };
//        InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
//        inputMap.put(stroke, "ESCAPE");
//        rootPane.getActionMap().put("ESCAPE", action);
//        return rootPane;
//    }
//
//    // an action listener to be used when an action is performed
//    // (e.g. button is pressed)
//    class MyActionListener implements ActionListener {
//
//        //close and dispose of the window.
//        public void actionPerformed(ActionEvent e) {
//            System.out.println("disposing the window..");
//            setVisible(false);
//            dispose();
//        }
//    }
//
//}
