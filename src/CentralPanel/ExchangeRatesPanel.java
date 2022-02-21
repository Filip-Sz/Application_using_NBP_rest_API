package CentralPanel;

import api.ActualityCurrency;
import api.ActualityHandler;
import api.ActualityRange;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ExchangeRatesPanel extends JPanel {
    private JPanel rangeInputPanel;
    private JPanel choicePanel;
    private JPanel outputPanel;
    private ActualityHandler actualityHandler;
    private List<ActualityCurrency> currenciesList;
    private String intervalChoice = "Weekly";

    private ExchangeRatesOutput exchangeRatesOutputEUR;
    private ExchangeRatesOutput exchangeRatesOutputUSD;
    private ExchangeRatesOutput exchangeRatesOutputCHF;
    private ExchangeRatesOutput exchangeRatesOutputGBP;

    public ExchangeRatesPanel() {

//        Exchange rates date rage input
        rangeInputPanel = new JPanel();
        rangeInputPanel.setLayout(null);
        rangeInputPanel.setBounds(100, 25, 900, 50);

        choicePanel = new JPanel();
        choicePanel.setLayout(null);
        choicePanel.setBounds(100, 100, 100, 50);

        outputPanel = new JPanel();
        outputPanel.setLayout(null);
        outputPanel.setBounds(100, 100, 900, 200);

//        Buttons for rangeInputPanel
        JButton dailyButton = this.generateButton("Daily", ActualityRange.Daily, 0);
        JButton weeklyButton = this.generateButton("Weekly", ActualityRange.Weekly, 300);
        JButton monthlyButton = this.generateButton("Monthly", ActualityRange.Monthly, 600);

        rangeInputPanel.add(dailyButton);
        rangeInputPanel.add(weeklyButton);
        rangeInputPanel.add(monthlyButton);

//        JLabel for choicePanel
        JLabel choiceLabel = this.generateLabel(intervalChoice);
        choicePanel.add(choiceLabel);

//        Initial comparision date range is one week
        this.update(ActualityRange.Weekly);

//        Settings of main panel
        this.setLayout(null);
        this.setBounds(0,0, 900, 400);
        this.add(rangeInputPanel);
        this.add(choicePanel);
        this.add(outputPanel);
    }

    private void update(ActualityRange actualityRange) {
        actualityHandler = new ActualityHandler(actualityRange);
        this.currenciesList = actualityHandler.getCurrenciesList();
        ActualityCurrency actualityCurrencyEUR = currenciesList.get(0);
        exchangeRatesOutputEUR = new ExchangeRatesOutput(actualityCurrencyEUR, 0);
        ActualityCurrency actualityCurrencyUSD = currenciesList.get(1);
        exchangeRatesOutputUSD = new ExchangeRatesOutput(actualityCurrencyUSD, 1);
        ActualityCurrency actualityCurrencyCHF = currenciesList.get(2);
        exchangeRatesOutputCHF = new ExchangeRatesOutput(actualityCurrencyCHF, 2);
        ActualityCurrency actualityCurrencyGBP = currenciesList.get(3);
        exchangeRatesOutputGBP = new ExchangeRatesOutput(actualityCurrencyGBP, 3);

        outputPanel.removeAll();
        outputPanel.add(exchangeRatesOutputEUR);
        outputPanel.add(exchangeRatesOutputUSD);
        outputPanel.add(exchangeRatesOutputCHF);
        outputPanel.add(exchangeRatesOutputGBP);
        outputPanel.repaint();
    }

    private JButton generateButton (String text, ActualityRange actualityRange2, int xPosition) {
        JButton button = new JButton(text);
        button.setBounds(xPosition, 10, 100, 30);
        button.setFocusable(true);
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.CENTER);
        button.setFont(new Font("Times New Roman", Font.BOLD, 15));
        button.addActionListener(e -> {
            this.choicePanel.removeAll();
            this.choicePanel.add(this.generateLabel(text));
            this.update(actualityRange2);
            this.repaint();
        });

        return button;
    }

    private JLabel generateLabel (String intervalChoice) {
        JLabel choiceLabel = new JLabel();
        choiceLabel.setText(intervalChoice);
        choiceLabel.setForeground(new Color(1, 1, 1));
        choiceLabel.setFont(new Font("Verdana", Font.PLAIN, 16));
        choiceLabel.setBounds(0,0,100, 20);
        choiceLabel.setHorizontalTextPosition(JLabel.LEFT);
        choiceLabel.setVerticalTextPosition(JLabel.CENTER);
        choiceLabel.setOpaque(true);

        return choiceLabel;
    }
}
