package uielements;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.*;

public class FlatComboBoxUI extends BasicComboBoxUI
{



    protected void installDefaults()
    {
        super.installDefaults();
        comboBox.setOpaque(true );
        comboBox.setBackground( ColorSet.DARKBLUE );
        comboBox.setForeground( ColorSet.BEIGE );
        UIManager.put("ComboBox.selectionForeground", ColorSet.SALMON);
        UIManager.put("ComboBox.selectionBackground", ColorSet.DARKBLUE);
        UIManager.put("ComboBox.buttonDarkShadow", ColorSet.SALMON);
        comboBox.setBorder(BorderFactory.createEmptyBorder());
        comboBox.setBorder(new BottomBorder());
    }

    @Override
    protected ComboPopup createPopup() {
        BasicComboPopup flat_popup = new FlatBasicComboPopup(comboBox);
        flat_popup.getAccessibleContext().setAccessibleParent(comboBox);
        return flat_popup;
    }

    @Override
    protected JButton createArrowButton() {
        JButton button = new JButton(new ImageIcon("resources/img/ComboButton.png"));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setMinimumSize(new Dimension(15, 15));
        button.setMaximumSize(new Dimension(15, 15));
        button.setPreferredSize(new Dimension(15, 15));
        button.setContentAreaFilled(false);
        button.setBackground(ColorSet.DARKBLUE);
        return button;
    }

}