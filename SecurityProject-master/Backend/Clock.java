package project.security.Backend;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Daniel on 8/14/2015.
 * @deprecated
 */
public class Clock {
    private static LocalTime time;
    private static Date date;
    private static LocalDateTime dateTime;
    private static LocalDateTime vacationEnd, vacationStart;
    //private static Calendar cal, vacationEnd, vacationStart;
    public Clock() {
        time = LocalTime.now();
        date = new Date();
        //cal = Calendar.getInstance();
        dateTime = LocalDateTime.now();
    }
    /*
    public static void setTime(LocalTime time2) {
        time = time2;
        dateTime.
    }
    */
    public static LocalTime getTime() {
        time = dateTime.toLocalTime();
        return time;
    }
    public static LocalDate addDays(int days) {
        dateTime.plusDays(days);
        return dateTime.toLocalDate();
    }
    public static LocalTime addHours(int hours) {
        dateTime.plusHours(hours);
        return dateTime.toLocalTime();
    }
    public static LocalTime addMin(int minutes) {
        dateTime.plusHours(minutes);
        return dateTime.toLocalTime();
    }
    public static DayOfWeek dayOfWeek() {
        return dateTime.getDayOfWeek();
        //return cal.DAY_OF_WEEK;
    }
    public static boolean isWeekend() {
        //if(cal.DAY_OF_WEEK == Calendar.SATURDAY || cal.DAY_OF_WEEK == Calendar.SUNDAY)
        return dayOfWeek() == DayOfWeek.SATURDAY || dayOfWeek() == DayOfWeek.SUNDAY;
    }
    public static void setVacation(int days) {
        vacationStart = dateTime;
        vacationEnd = dateTime;
        vacationEnd.plusDays(days);
        //vacationEnd = cal;
        //vacationStart = cal;
        //vacationEnd.add(Calendar.DATE, days);
    }
    public static boolean onVacation() {
        return dateTime.isBefore(vacationEnd) && dateTime.isAfter(vacationStart);
    }

}
