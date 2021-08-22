// Author: ebrookes

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

    private static String[] eventHours = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

    private static String[] eventMinutes = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
            "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
            "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47",
            "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" };

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
    public static String[] getEventHours() {
        return eventHours;
    }
    public static String[] getEventMinutes() {
        return eventMinutes;
    }
}
