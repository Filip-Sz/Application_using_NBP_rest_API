package CentralPanel;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {
    public InfoPanel () {
        JLabel descriptionLabel = new JLabel();
        descriptionLabel.setText("<html>" + "This application was created by Jędrzej Sokołowski and Filip Szympliński as a java " +
                        "programming project for Advanced Object-oriented and Functional Programming. It implements Rest " +
                "API from Polish National Bank in order to obtain various information about currencies and gold " +
                "exchange rates. The application allows users to compare different rates throughout specific periods " +
                "of time, to use a currencies calculator and also to analyze currencies using other features." +
                "</html>"
);
        descriptionLabel.setForeground(new Color(1, 1, 1));
        descriptionLabel.setFont(new Font("Verdana", Font.PLAIN, 16));
        descriptionLabel.setBounds(50, 20, 850, 150);
        descriptionLabel.setHorizontalTextPosition(JLabel.LEFT);
        descriptionLabel.setVerticalTextPosition(JLabel.TOP);
        descriptionLabel.setHorizontalAlignment(JLabel.LEFT);
        descriptionLabel.setVerticalAlignment(JLabel.TOP);
        descriptionLabel.setOpaque(true);

        JLabel contacts = new JLabel();
        contacts.setText("Contact to the authors and links:");
        contacts.setFont(new Font("Verdana", Font.PLAIN, 16));
        contacts.setBounds(50, 170, 850, 20);
        contacts.setHorizontalTextPosition(JLabel.LEFT);
        contacts.setVerticalTextPosition(JLabel.TOP);
        contacts.setHorizontalAlignment(JLabel.LEFT);
        contacts.setVerticalAlignment(JLabel.TOP);
        contacts.setOpaque(true);

        JLabel contacts2 = new JLabel();
        contacts2.setText("<html>" + "https://github.com/Filip-Sz " +
                "https://github.com/sokolowskij\n" +
                "http://api.nbp.pl" +
                "</html>");
        contacts2.setFont(new Font("Verdana", Font.PLAIN, 16));
        contacts2.setBounds(70, 190, 260, 70);
        contacts2.setHorizontalTextPosition(JLabel.LEFT);
        contacts2.setVerticalTextPosition(JLabel.TOP);
        contacts2.setHorizontalAlignment(JLabel.LEFT);
        contacts2.setVerticalAlignment(JLabel.TOP);
        contacts2.setOpaque(true);

        this.setLayout(null);
        this.setBounds(0,0, 800, 200);
        this.add(descriptionLabel);
        this.add(contacts);
        this.add(contacts2);
    }
}
