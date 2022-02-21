package CentralPanel;

import LeftPanel.Calendar.DatePanel;

import javax.swing.*;
import java.awt.*;

public class CentralPanel extends JPanel{

    public CentralPanel(DatePanel datePanel) {
        this.setPreferredSize(new Dimension(900, 800));
        this.setLayout(new GridLayout(1,1));
        this.setVisible(true);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setPreferredSize(new Dimension(900, 750));
        CurrenciesPlotPanel plotPanel = new CurrenciesPlotPanel(datePanel);
        ExchangeRatesPanel exchangeRatesPanel = new ExchangeRatesPanel();
        GoldPlotPanel goldPlotPanel = new GoldPlotPanel(datePanel);
        InfoPanel infoPanel = new InfoPanel();
        tabs.add("Currencies exchange rates plot", plotPanel);
        tabs.add("Current exchange rates", exchangeRatesPanel);
        tabs.add("Gold exchange rates plot", goldPlotPanel);
        tabs.add("Information about authors", infoPanel);
        this.add(tabs);
    }

}
