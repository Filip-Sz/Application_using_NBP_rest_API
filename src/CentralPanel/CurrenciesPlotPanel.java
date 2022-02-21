package CentralPanel;

import LeftPanel.Calendar.DatePanel;
import api.CurrencyPlotCustom;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CurrenciesPlotPanel extends JPanel {

    String[] currencies = {"THB","USD","AUD","HKD","CAD","NZD","SGD","EUR","HUF","CHF","GBP",
            "UAH","JPY","CZK","DKK","ISK","NOK","SEK","HRK","RON","BGN","TRY","ILS","CLP",
            "PHP","MXN","ZAR","BRL","MYR","RUB","IDR","INR","KRW","CNY","XDR"};
    String[] currenciesChoiceArray = {currencies[0], currencies[1], ""};
    private Date dateStart;
    private Date dateEnd;
    private JFreeChart currenciesPlot;
    private ChartPanel currenciesPlotPanel;

    public CurrenciesPlotPanel(DatePanel datePanel) {
//        Date settings
        Date today = new Date();
        Calendar calStart = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();
        calStart.setTime(today);
        calEnd.setTime(today);
        calStart.add(Calendar.MONTH, -1);
        dateStart = calStart.getTime();
        dateEnd = calEnd.getTime();

//        Description label
        JLabel descriptionLabel = new JLabel();
        descriptionLabel.setText("Choose amount and type of currencies");
        descriptionLabel.setForeground(new Color(1, 1, 1));
        descriptionLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        descriptionLabel.setBounds(60, 10, 300, 30);
        descriptionLabel.setHorizontalTextPosition(JLabel.LEFT);
        descriptionLabel.setVerticalTextPosition(JLabel.CENTER);
        descriptionLabel.setHorizontalAlignment(JLabel.LEFT);
        descriptionLabel.setVerticalAlignment(JLabel.CENTER);
        descriptionLabel.setOpaque(true);

//        Panel of currencies choices
        JPanel currenciesChoicesPanel = new JPanel();
        currenciesChoicesPanel.setBounds(60, 100, 950, 50);
        currenciesChoicesPanel.setLayout(null);
        currenciesChoicesPanel.add(new CurrencyChoice(currencies, 0, 0));
        currenciesChoicesPanel.add(new CurrencyChoice(currencies, 1, 1));


//        Choice of amount of currencies on plot
        JPanel currenciesCountPanel = new JPanel();
        currenciesCountPanel.setBounds(60, 40, 950, 50);
        currenciesCountPanel.setLayout(null);

    //    First button
        JRadioButton firstButton = new JRadioButton("1");
        firstButton.setBounds(0, 10, 80, 30);
        firstButton.addActionListener(e -> {
            currenciesChoicesPanel.removeAll();
            for (int i = 0; i < 3; i++) {currenciesChoiceArray[i] = "";}
            currenciesChoicesPanel.removeAll();
            for (int i = 0; i < 1; i++) {
                currenciesChoicesPanel.add(new CurrencyChoice(currencies, i, i));
                currenciesChoiceArray[i] = currencies[i];
            }
            currenciesChoicesPanel.repaint();
        });
        currenciesCountPanel.add(firstButton);

    //    Second button
        JRadioButton secondButton = new JRadioButton("2");
        secondButton.setBounds(150, 10, 80, 30);
        secondButton.setSelected(true);
        secondButton.addActionListener(e -> {
            currenciesChoicesPanel.removeAll();
            for (int i = 0; i < 3; i++) {currenciesChoiceArray[i] = "";}
            currenciesChoicesPanel.removeAll();
            for (int i = 0; i < 2; i++) {
                currenciesChoicesPanel.add(new CurrencyChoice(currencies, i, i));
                currenciesChoiceArray[i] = currencies[i];
            }
            currenciesChoicesPanel.repaint();
        });
        currenciesCountPanel.add(secondButton);

    //    Third button
        JRadioButton thirdButton = new JRadioButton("3");
        thirdButton.setBounds(300, 10, 80, 30);
        thirdButton.addActionListener(e -> {
            for (int i = 0; i < 3; i++) {currenciesChoiceArray[i] = "";}
            currenciesChoicesPanel.removeAll();
            for (int i = 0; i < 3; i++) {
                currenciesChoicesPanel.add(new CurrencyChoice(currencies, i, i));
                currenciesChoiceArray[i] = currencies[i];
            }
            currenciesChoicesPanel.repaint();
        });
        currenciesCountPanel.add(thirdButton);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(firstButton);
        buttonGroup.add(secondButton);
        buttonGroup.add(thirdButton);

//        Show plot button
        JButton showButton;
        showButton = new JButton();
        showButton.setBounds(830, 1, 100, 30);
        showButton.setText("Show plot");
        showButton.setFocusable(true);
        showButton.setHorizontalTextPosition(JButton.CENTER);
        showButton.setVerticalTextPosition(JButton.CENTER);
        showButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
        showButton.addActionListener(e -> {
            dateStart = datePanel.getDateStart();
            dateEnd = datePanel.getDateEnd();

            currenciesPlot = this.generatePlot();
            currenciesPlotPanel.setChart(currenciesPlot);
            currenciesPlotPanel.repaint();
        });
        currenciesCountPanel.add(showButton);

//        Initial plot
        currenciesPlot = this.generatePlot();
        currenciesPlotPanel = new ChartPanel(currenciesPlot);
        currenciesPlotPanel.setBounds(0, 150, 1000, 600);
        currenciesPlotPanel.setLayout(null);

//        Main panel settings and adding content
        this.setLayout(null);
        this.add(descriptionLabel);
        this.add(currenciesCountPanel);
        this.add(currenciesChoicesPanel);
        this.add(currenciesPlotPanel);
    }

    private class CurrencyChoice extends JComboBox {

        public CurrencyChoice(String[] items, int i, int j) {
            super(items);
            this.setBounds(150*i, 0, 100, 30);
            this.setSelectedIndex(j);
            this.addActionListener(e -> currenciesChoiceArray[i] = (String) this.getSelectedItem());
        }
    }

    private JFreeChart generatePlot() {
        String plotTitle;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDateStart = formatter.format(this.dateStart);
        String strDateEnd = formatter.format(this.dateEnd);

        plotTitle = "Currencies exchange rates" + " from " + strDateStart + " to " + strDateEnd;
        CurrencyPlotCustom currencyPlotCustom = new CurrencyPlotCustom(this.currenciesChoiceArray, strDateStart, strDateEnd);
        DefaultCategoryDataset ds = currencyPlotCustom.dataset;
        double maxPlotValue = currencyPlotCustom.CurrencyMaxRate + 0.2;
//        System.out.println(maxPlotValue);
        double minPlotValue = Math.max(currencyPlotCustom.CurrencyMinRate - 0.2, 0.0);
//        System.out.println(minPlotValue);

        JFreeChart currenciesPlot = ChartFactory.createLineChart(plotTitle,
                "Date", "Value", ds, PlotOrientation.VERTICAL, true, true,
                false);
        currenciesPlot.getCategoryPlot().getRangeAxis().setRange(minPlotValue,maxPlotValue);
        currenciesPlot.getPlot().setBackgroundAlpha(1F);
        currenciesPlot.getPlot().setBackgroundPaint(new Color(217, 217, 217));
        currenciesPlot.setBorderVisible(false);
        currenciesPlot.setBackgroundPaint(null);

        return currenciesPlot;
    }
}
