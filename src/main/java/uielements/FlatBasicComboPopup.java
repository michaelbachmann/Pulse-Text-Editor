package uielements;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboPopup;

public class FlatBasicComboPopup extends BasicComboPopup {

    public FlatBasicComboPopup(JComboBox combo) {
        super(combo);
        setBorder(BorderFactory.createLineBorder(ColorSet.DARKBLUE,8));
    }

    // Must override this to use custom scrollbar
    @Override
    protected JScrollPane createScroller(){
        JScrollPane scrollPane = new JScrollPane(list, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUI(new FlatComboBoxScrollUI());
        return scrollPane;
    }
}
