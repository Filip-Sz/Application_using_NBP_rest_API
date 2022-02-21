package LeftPanel.Calendar;

import org.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatePanel extends JPanel {

    private Date dateStart;
    private Date dateEnd;
    private final Date today = new Date();
    private final JDatePickerImpl datePickerEnd = new DatePicker(0).calendar;
    private final JDatePickerImpl datePickerStart = new DatePicker(1).calendar;
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public DatePanel() {
        dateStart = (Date) datePickerStart.getModel().getValue();
        dateEnd = (Date) datePickerEnd.getModel().getValue();

//        Title label
        JLabel titleLabel = new JLabel();
        titleLabel.setText("Choose interval of dates");
        titleLabel.setHorizontalTextPosition(JLabel.CENTER);
        titleLabel.setVerticalTextPosition(JLabel.TOP);
        titleLabel.setVerticalAlignment(JLabel.CENTER);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        titleLabel.setBounds(50, 10, 200, 30);

//        Start date
        JLabel labelDateStart = new JLabel();
        labelDateStart.setText("From");
        labelDateStart.setVerticalTextPosition(JLabel.CENTER);
        labelDateStart.setHorizontalTextPosition(JLabel.LEFT);
        labelDateStart.setVerticalAlignment(JLabel.CENTER);
        labelDateStart.setHorizontalAlignment(JLabel.LEFT);
        labelDateStart.setOpaque(true);
        labelDateStart.setBounds(5, 5, 50, 30);

        datePickerStart.setBounds(60, 5, 235, 30);
        datePickerStart.addActionListener(e -> {
            Date tmpDate = (Date) datePickerStart.getModel().getValue();
            String strTmpDate = formatter.format(tmpDate);
            String strDateToday = formatter.format(today);
            if(strTmpDate.compareTo(strDateToday) > 0) {
                JOptionPane.showMessageDialog(this, "You can't choose future date." +
                                " Please, choose another day.",
                        "Alert", JOptionPane.WARNING_MESSAGE);
            }
            else {
                dateStart = tmpDate;
            }
        });

        JPanel datePanelStart = new JPanel();
        datePanelStart.add(labelDateStart);
        datePanelStart.add(datePickerStart);
        datePanelStart.setLayout(null);
        datePanelStart.setVisible(true);
        datePanelStart.setBounds(0,50, 300, 40);

//        End date
        JLabel labelDateEnd = new JLabel();
        labelDateEnd.setText("To");
        labelDateEnd.setVerticalTextPosition(JLabel.CENTER);
        labelDateEnd.setHorizontalTextPosition(JLabel.LEFT);
        labelDateEnd.setVerticalAlignment(JLabel.CENTER);
        labelDateEnd.setHorizontalAlignment(JLabel.LEFT);
        labelDateEnd.setBounds(5, 0, 50, 30);
        labelDateEnd.setOpaque(true);

        datePickerEnd.setBounds(60, 0, 235, 30);
        datePickerEnd.addActionListener(e -> {
            Date tmpDate = (Date) datePickerEnd.getModel().getValue();
            String strTmpDate = formatter.format(tmpDate);
            String strDateToday = formatter.format(today);
            if(strTmpDate.compareTo(strDateToday) > 0) {
                JOptionPane.showMessageDialog(this, "You can't choose future date." +
                                " Please, choose another day.",
                        "Alert", JOptionPane.WARNING_MESSAGE);
            }
            else {
                dateEnd = tmpDate;
            }
        });

        JPanel datePanelEnd = new JPanel();
        datePanelEnd.add(labelDateEnd);
        datePanelEnd.add(datePickerEnd);
        datePanelEnd.setVisible(true);
        datePanelEnd.setLayout(null);
        datePanelEnd.setBounds(0,90, 300, 40);

//        Settings of main panel
        this.setBounds(0,0, 300, 200);
        this.setLayout(null);
        this.add(titleLabel);
        this.add(datePanelStart);
        this.add(datePanelEnd);
    }

    public Date getDateStart() {
        return dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }
}
