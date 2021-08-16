package ebrookes.calendar;

import javafx.scene.control.Label;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

public class Calendar {

    private Year currentYear;
    private Month currentMonth;
    private int currentDay;

    public Calendar() {
        setCurrentDate();

    }

    public Year getCurrentYear() {
        return currentYear;
    }

    public Month getCurrentMonth() {
        return currentMonth;
    }

    public int getCurrentDay() {
        return currentDay;
    }

    private void setCurrentDate() {
        Date date = new Date();

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String temp = formatter.format(date);

        currentDay = Integer.parseInt(temp.substring(0, 2));
        currentMonth = Month.of(Integer.parseInt(temp.substring(3, 5)));
        currentYear = Year.of(Integer.parseInt(temp.substring(6, 10)));

        System.out.println(currentDay);
        System.out.println(currentMonth.getDisplayName(TextStyle.FULL, Locale.CANADA));
        System.out.println(currentYear);
    }
    private void createMonth() {
        int daysInMonth;

        if (!currentYear.isLeap())
            daysInMonth = currentMonth.length(false);
        else
            daysInMonth = currentMonth.length(true);

    }
    public void genDayLabels (Label[] dayLabels, Year year, Month month) {

        DayOfWeek firstDay = DayOfWeek.from(LocalDate.of(year.getValue(), month, 1));
        int index = firstDay.getValue();

        if (index == 7)
            index = 0;

        dayLabels[index].setText("1");
        dayLabels[index].setDisable(false);

        int daysInMonth = month.length(year.isLeap());
        int daysInPrevMonth = month.minus(1).length(year.isLeap());

        // Set days for previous month and grey out
        for (int i = 0; i < index; i++) {
            dayLabels[i].setText(String.valueOf(i + 1 + daysInPrevMonth - index));
            dayLabels[i].setDisable(true);
        }
        // Set days for viewing month
        for (int i = index + 1; i < index + daysInMonth; i++) {
            dayLabels[i].setText(String.valueOf(i-index+1));
            dayLabels[i].setDisable(false);
        }
        // Set days for next month and grey out
        for (int i = index + daysInMonth; i < dayLabels.length; i++) {
            dayLabels[i].setText(String.valueOf(i - index + 1 - daysInMonth));
            dayLabels[i].setDisable(true);
        }
    }
}
