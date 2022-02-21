package LeftPanel.Calculator;

import api.CurrencyCalculator;
import api.Option;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CalculatorPanel extends JPanel {

    JRadioButton buyButton;
    JRadioButton sellButton;
    String[] currencies = {"THB","USD","AUD","HKD","CAD","NZD","SGD","EUR","HUF","CHF","GBP",
            "UAH","JPY","CZK","DKK","ISK","NOK","SEK","HRK","RON","BGN","TRY","ILS","CLP",
            "PHP","MXN","ZAR","BRL","MYR","RUB","IDR","INR","KRW","CNY","XDR"};
    private Option option = Option.buy;
    private String currencyLeft = currencies[0];
    private String currencyRight = currencies[1];
    private double moneyInputValue;
    private CurrencyCalculator currencyCalculator;

    private String row1text;
    private String row2text;
    private String row3text;
    private String row4text;


    public CalculatorPanel() {

//        Title label
        JLabel titleLabel = new JLabel();
        titleLabel.setText("Currencies calculator");
        titleLabel.setForeground(new Color(1, 1, 1));
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        titleLabel.setBounds(30, 0, 240, 30);
        titleLabel.setHorizontalTextPosition(JLabel.CENTER);
        titleLabel.setVerticalTextPosition(JLabel.TOP);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setVerticalAlignment(JLabel.CENTER);
        titleLabel.setOpaque(true);


//        Left currency sub panel
        JPanel leftCurrencyPanel = new JPanel();
        buyButton = new JRadioButton("BUY");
        buyButton.setBounds(10, 10, 100, 30);
        buyButton.addActionListener(e -> option = Option.buy);
        buyButton.setSelected(true);

        JComboBox leftCurrency = new JComboBox(currencies);
        leftCurrency.setBounds(10, 50, 100, 30);
        leftCurrency.setSelectedIndex(0);
        leftCurrency.addActionListener(e -> currencyLeft = (String) leftCurrency.getSelectedItem());

        leftCurrencyPanel.setBounds(0, 30, 120, 90);
        leftCurrencyPanel.setLayout(null);
        leftCurrencyPanel.add(buyButton);
        leftCurrencyPanel.add(leftCurrency);
        leftCurrencyPanel.setVisible(true);

//        Arrow image (Doesn't work)
        JLabel arrowLabel = new JLabel();
        ImageIcon arrow = new ImageIcon("/arrow.png");
        arrowLabel.setIcon(arrow);
        arrowLabel.setVerticalAlignment(JLabel.CENTER);
        arrowLabel.setHorizontalAlignment(JLabel.CENTER);
        arrowLabel.setBounds(120, 80, 60, 30);
        arrowLabel.setOpaque(true);

//        Left currency sub panel
        JPanel rightCurrencyPanel = new JPanel();
        sellButton = new JRadioButton("SELL");
        sellButton.setBounds(10, 10, 100, 30);
        sellButton.addActionListener(e -> option = Option.sell);

        JComboBox rightCurrency = new JComboBox(currencies);
        rightCurrency.setBounds(10, 50, 100, 30);
        rightCurrency.setSelectedIndex(1);
        rightCurrency.addActionListener(e -> currencyRight = (String) rightCurrency.getSelectedItem());

        rightCurrencyPanel.setBounds(180, 30, 120, 90);
        rightCurrencyPanel.setLayout(null);
        rightCurrencyPanel.add(sellButton);
        rightCurrencyPanel.add(rightCurrency);
        rightCurrencyPanel.setVisible(true);

//        Grouping radio buttons
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(buyButton);
        buttonGroup.add(sellButton);

//        Money input
        JTextField moneyInput = new JTextField();
        moneyInput.setBounds(10, 130, 280, 30);
        moneyInput.setToolTipText("Type how much currency you would like to calculate");

//        Calculator output panel
        JPanel calculatorOutput = new JPanel();
        calculatorOutput.setLayout(null);
        calculatorOutput.setForeground(new Color(1, 1, 1));
        calculatorOutput.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        calculatorOutput.setBounds(10, 210, 240, 170);
        calculatorOutput.setOpaque(true);

        JLabel row1 = new JLabel();
        row1text = "";
        row1.setText(row1text);
        row1.setForeground(new Color(1, 1, 1));
        row1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        row1.setBounds(0, 10, 240, 30);
        row1.setHorizontalTextPosition(JLabel.LEFT);
        row1.setVerticalTextPosition(JLabel.CENTER);
        row1.setOpaque(true);

        JLabel row2 = new JLabel();
        row2text = "";
        row2.setText(row2text);
        row2.setForeground(new Color(1, 1, 1));
        row2.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        row2.setBounds(0, 40, 240, 30);
        row2.setHorizontalTextPosition(JLabel.LEFT);
        row2.setVerticalTextPosition(JLabel.CENTER);
        row2.setOpaque(true);

        JLabel row3 = new JLabel();
        row3text = "";
        row3.setText(row3text);
        row3.setForeground(new Color(1, 1, 1));
        row3.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        row3.setBounds(0, 70, 240, 30);
        row3.setHorizontalTextPosition(JLabel.LEFT);
        row3.setVerticalTextPosition(JLabel.CENTER);
        row3.setOpaque(true);

        JLabel row4 = new JLabel();
        row4text = "";
        row4.setText("<html>" + row4text + "<html>");
        row4.setForeground(new Color(1, 1, 1));
        row4.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        row4.setBounds(0, 120, 240, 120);
        row4.setHorizontalTextPosition(JLabel.LEFT);
        row4.setVerticalTextPosition(JLabel.TOP);
        row4.setVerticalAlignment(JLabel.TOP);
        row4.setOpaque(true);

        calculatorOutput.add(row1);
        calculatorOutput.add(row2);
        calculatorOutput.add(row3);
        calculatorOutput.add(row4);

//        Calculate button
        JButton calcButton = new JButton();
        calcButton.setBounds(100, 170, 100, 30);
        calcButton.setText("Calculate");
        calcButton.setFocusable(true);
        calcButton.setHorizontalTextPosition(JButton.CENTER);
        calcButton.setVerticalTextPosition(JButton.CENTER);
        calcButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
        calcButton.setBackground(null);
        calcButton.addActionListener(e -> {
            try {
                moneyInputValue = Double.parseDouble(moneyInput.getText());
                currencyCalculator = new CurrencyCalculator(
                        option,
                        currencyLeft,
                        currencyRight,
                        moneyInputValue);

                String currencyLeftVal = String.valueOf(currencyCalculator.currencyLeftVal);
                String currencyRightVal = String.valueOf(currencyCalculator.currencyRightVal);
                String currencyLeftRightVal = String.valueOf(currencyCalculator.currencyLeftRightVal);
                String finalVal = String.valueOf(currencyCalculator.finalValue);
                String strMoneyInput = String.valueOf(moneyInputValue);

                row1text = "1 " +  currencyLeft + " = " + currencyLeftVal + " PLN" ;
                row2text = "1 " +  currencyRight + " = " + currencyRightVal + " PLN" ;
                row3text = "1 " +  currencyLeft + " = " + currencyLeftRightVal + " " + currencyRight;
                row4text = strMoneyInput + " " +  currencyLeft + " = " + finalVal + " " + currencyRight;

                row1.setText(row1text);
                row2.setText(row2text);
                row3.setText(row3text);
                row4.setText("<html>" + row4text + "<html>");

                calculatorOutput.repaint();
            } catch (NumberFormatException e1) {
                JOptionPane.showMessageDialog(this, "Input isn't a number",
                        "Alert", JOptionPane.WARNING_MESSAGE);
            } catch (FileNotFoundException e2) {
                row1.setText("");
                row2.setText("");
                row3.setText("");
                row4text = "No data to compute. Choose another currency.";
                row4.setText("<html>" + row4text + "<html>");
                calculatorOutput.repaint();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        });


//        Settings of main panel
        this.setBounds(0, 200, 300, 600);
        this.setLayout(null);
        this.add(titleLabel);
        this.add(leftCurrencyPanel);
        this.add(arrowLabel);
        this.add(rightCurrencyPanel);
        this.add(moneyInput);
        this.add(calcButton);
        this.add(calculatorOutput);
        this.setVisible(true);

    }
}
