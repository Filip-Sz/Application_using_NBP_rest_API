package CentralPanel;

import api.ActualityCurrency;

import javax.swing.*;
import java.awt.*;

public class ExchangeRatesOutput extends JPanel {

    public ExchangeRatesOutput(ActualityCurrency actualityCurrency, int i) {
//        Currency name
        JLabel currencyName = new JLabel();
        currencyName.setText(actualityCurrency.name);
        currencyName.setForeground(new Color(1, 1, 1));
        currencyName.setFont(new Font("Verdana", Font.BOLD, 18));
        currencyName.setBounds(0,0,100, 20);
        currencyName.setHorizontalTextPosition(JLabel.LEFT);
        currencyName.setVerticalTextPosition(JLabel.CENTER);
        currencyName.setOpaque(true);

//        Buying value
        JLabel buyingPrefix = this.generatePrefixLabel("buying", 30);
        double buyingVal = actualityCurrency.buying;
        JLabel buying = this.generateValueLabel(buyingVal, 30);

//        Selling value
        JLabel sellingPrefix = this.generatePrefixLabel("selling", 50);
        double sellingVal = actualityCurrency.selling;
        JLabel selling = this.generateValueLabel(sellingVal, 50);

//        Average value
        JLabel averagePrefix = this.generatePrefixLabel("average", 70);
        double averageVal = actualityCurrency.average;
        JLabel average = this.generateValueLabel(averageVal, 70);

//        Main panel settings
        this.setLayout(null);
        this.setBounds(200*i, 50, 200, 200);
        this.add(currencyName);
        this.add(buyingPrefix);
        this.add(buying);
        this.add(sellingPrefix);
        this.add(selling);
        this.add(averagePrefix);
        this.add(average);
    }

    private JLabel generatePrefixLabel (String prefix, int yPosition) {
        JLabel prefixLabel = new JLabel();
        prefixLabel.setText(prefix + ": ");
        prefixLabel.setForeground(new Color(1, 1, 1));
        prefixLabel.setFont(new Font("Verdana", Font.PLAIN, 14));
        prefixLabel.setBounds(0,yPosition,80, 30);
        prefixLabel.setHorizontalTextPosition(JLabel.LEFT);
        prefixLabel.setVerticalTextPosition(JLabel.CENTER);
        prefixLabel.setOpaque(true);

        return prefixLabel;
    }

    private JLabel generateValueLabel (double val, int yPosition) {
        JLabel valueLabel = new JLabel();
        if (val > 0) {
            valueLabel.setText("<html><font color='green'>" + val + "%" + "</font></html>");
        } else if (val < 0) {
            valueLabel.setText("<html><font color='red'>" + val + "%" + "</font></html>");
        } else {
            valueLabel.setText("<html><font color='blue'>" + val + "%" + "</font></html>");
        }
        valueLabel.setForeground(new Color(1, 1, 1));
        valueLabel.setFont(new Font("Verdana", Font.PLAIN, 14));
        valueLabel.setBounds(80,yPosition,120, 30);
        valueLabel.setHorizontalTextPosition(JLabel.LEFT);
        valueLabel.setVerticalTextPosition(JLabel.CENTER);
        valueLabel.setOpaque(true);

        return valueLabel;
    }
}
