package CentralPanel;

import LeftPanel.Calendar.DatePanel;
import api.GoldUSDPlot;
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

public class GoldPlotPanel extends JPanel {

    private Date dateStart;
    private Date dateEnd;
    private JFreeChart goldUSDPlot;
    private ChartPanel goldUSDPlotPanel;

    public GoldPlotPanel(DatePanel datePanel) {

//        Date settings
        Date today = new Date();
        Calendar calStart = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();
        calStart.setTime(today);
        calEnd.setTime(today);
        calStart.add(Calendar.MONTH, -1);
        dateStart = calStart.getTime();
        dateEnd = calEnd.getTime();

//        Show plot button
        JButton showButton;
        showButton = new JButton();
        showButton.setBounds(890, 15, 100, 30);
        showButton.setText("Show plot");
        showButton.setFocusable(true);
        showButton.setHorizontalTextPosition(JButton.CENTER);
        showButton.setVerticalTextPosition(JButton.CENTER);
        showButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
        showButton.addActionListener(e -> {
            dateStart = datePanel.getDateStart();
            dateEnd = datePanel.getDateEnd();

            goldUSDPlot = this.generatePlot();
            goldUSDPlotPanel.setChart(goldUSDPlot);
            goldUSDPlotPanel.repaint();
        });

//        Initial plot
        goldUSDPlot = this.generatePlot();
        goldUSDPlotPanel = new ChartPanel(goldUSDPlot);
        goldUSDPlotPanel.setBounds(0, 50, 1000, 600);
        goldUSDPlotPanel.setLayout(null);

//        Main panel settings
        this.setLayout(null);
        this.add(showButton);
        this.add(goldUSDPlotPanel);
    }

    private JFreeChart generatePlot() {
        String plotTitle;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDateStart = formatter.format(this.dateStart);
        String strDateEnd = formatter.format(this.dateEnd);

        plotTitle = "Gold exchange rates" + " from " + strDateStart + " to " + strDateEnd;
        GoldUSDPlot goldUSD = new GoldUSDPlot(strDateStart, strDateEnd);
        DefaultCategoryDataset ds = goldUSD.dataset;
        double maxPlotValue = goldUSD.CurrencyMaxRate + 3;
//        System.out.println(maxPlotValue);
        double minPlotValue = Math.max(goldUSD.CurrencyMinRate - 3, 0.0);
//        System.out.println(minPlotValue);

        JFreeChart goldUSDPlot = ChartFactory.createLineChart(plotTitle,
                "Date", "Value", ds, PlotOrientation.VERTICAL, true, true,
                false);
        goldUSDPlot.getCategoryPlot().getRangeAxis().setRange(minPlotValue,maxPlotValue);
        goldUSDPlot.getPlot().setBackgroundAlpha(1F);
        goldUSDPlot.getPlot().setBackgroundPaint(new Color(217, 217, 217));
        goldUSDPlot.setBorderVisible(false);
        goldUSDPlot.setBackgroundPaint(null);

        return goldUSDPlot;
    }
}
