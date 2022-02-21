package LeftPanel;

import LeftPanel.Calculator.CalculatorPanel;
import LeftPanel.Calendar.DatePanel;

import javax.swing.*;
import java.awt.*;

public class LeftPanel extends JPanel {
    public DatePanel datePanel;
    public LeftPanel() {
//        Basic settings
        this.setPreferredSize(new Dimension(300, 1000));
        this.setLayout(null);

//        Adding a date picker panel
        datePanel = new DatePanel();
        datePanel.setBounds(0,0, 300, 250);
        this.add(datePanel);

//        Adding a currencies calculator panel
        CalculatorPanel calculatorPanel = new CalculatorPanel();
        calculatorPanel.setBounds(0, 250, 300, 3000);
        this.add(calculatorPanel);

        this.setVisible(true);
    }

}
