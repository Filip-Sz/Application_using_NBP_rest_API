import CentralPanel.CentralPanel;
import HeadPanel.HeadPanel;
import LeftPanel.LeftPanel;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        HeadPanel headPanel = new HeadPanel();
        LeftPanel leftPanel = new LeftPanel();
        CentralPanel centralPanel = new CentralPanel(leftPanel.datePanel);
        mainFrame.add(headPanel, BorderLayout.NORTH);
        mainFrame.add(leftPanel, BorderLayout.WEST);
        mainFrame.add(centralPanel, BorderLayout.CENTER);

        mainFrame.setVisible(true);
    }
}
