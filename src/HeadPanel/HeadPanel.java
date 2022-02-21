package HeadPanel;

import javax.swing.*;
import java.awt.*;

public class HeadPanel extends JPanel {
    public HeadPanel() {
//        Adding label
        JLabel titleLabel = new JLabel();
        titleLabel.setText("NBP exchange rates");
        titleLabel.setHorizontalTextPosition(JLabel.CENTER);
        titleLabel.setVerticalTextPosition(JLabel.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        titleLabel.setBounds(5, 2, 200, 20);

        this.setBackground(new Color(211, 211, 211, 255));
        this.setPreferredSize(new Dimension(1200, 24));
        this.setLayout(null);
        this.add(titleLabel);
        this.setVisible(true);
    }
}
