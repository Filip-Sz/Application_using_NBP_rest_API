package LeftPanel.Calendar;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.util.Properties;

public class DatePicker {
    public JDatePickerImpl calendar;

    public DatePicker(int monthDifference) {
//        Today's year, month, day
        int year = java.time.LocalDate.now().getYear();
        int month = java.time.LocalDate.now().getMonthValue();
        int day = java.time.LocalDate.now().getDayOfMonth();
        month = month - 1 - monthDifference;

//        Setting initial date
        UtilDateModel model = new UtilDateModel();
        model.setDate(year, month, day);
        model.setSelected(true);

        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        this.calendar = new JDatePickerImpl(datePanel, new DateLabelFormatter());
    }
}
