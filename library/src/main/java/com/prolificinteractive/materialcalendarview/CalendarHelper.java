package com.prolificinteractive.materialcalendarview;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.YEAR;

/**
 * Calendar helper class that utilizes a single instance of a Calendar object to convert time in ms
 * to information about the date (day of week, year, month, etc...). Synchronized static methods
 * prevent race conditions from occurring and messing up behavior.
 * Created by faisal-alqadi on 2/20/15.
 */
public class CalendarHelper {
    public static final Calendar cal;
    static{
        cal = Calendar.getInstance();
    }


    /**
     * Get day of month as an int
     * @param ms time in ms
     * @return day of month as an int
     */
    public synchronized static int getDayOfMonth(long ms){
        return get(ms, Calendar.DAY_OF_MONTH);
    }

    /**
     * Get day of week as an int
     * @param ms time in ms
     * @return day of week as an int
     */
    public synchronized static int getDayOfWeek(long ms){
        return get(ms, Calendar.DAY_OF_WEEK);
    }

    /**
     * Get the three letter name of the day
     * @param dayOfWeek day of week as int
     * @return three letter name of day
     */
    public synchronized static String getDayOfWeekDisplayName(int dayOfWeek){
        cal.set(DAY_OF_WEEK, dayOfWeek);
        return cal.getDisplayName(DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
    }

    /**
     * Get month as an int
     * @param ms time in ms
     * @return month as an int
     */
    public synchronized static int getMonth(long ms){
        return get(ms, Calendar.MONTH);
    }

    /**
     * Get year as an int
     * @param ms time in ms
     * @return year as an int
     */
    public synchronized static int getYear(long ms){
        return get(ms, Calendar.YEAR);
    }

    /**
     * Get a certain field from the Calendar {@link java.util.Calendar}
     * @param ms time in ms
     * @return the field specified as an int
     */
    public synchronized static int get(long ms, int field){
        set(ms);
        return cal.get(field);
    }

    /**
     * Get the date string of some time in ms
     * @param ms time in ms
     * @return date string dd-MM-yyy
     */
    public synchronized static String asString(long ms){
        return String.format(Locale.US, "CalendarDay{%d-%d-%d}", getDayOfMonth(ms), getMonth(ms) + 1, get(ms, YEAR));
    }
    /**
     * Increment a certain field from {@link java.util.Calendar} by a certain amount and returns the
     * result as a long
     * @param ms time in ms
     * @param field field to increment
     * @param amount amount to increment field by
     * @return resulting time in ms
     */
    public synchronized static long add(long ms, int field, int amount){
        set(ms);
        cal.add(field, amount);
        return cal.getTime().getTime();
    }
    /**
     * Set a certain field from {@link java.util.Calendar} to a certain amount and returns the
     * result as a long
     * @param ms time in ms
     * @param field field to set
     * @param amount amount to set field to
     * @return resulting time in ms
     */
    public synchronized static long set(long ms, int field, int amount){
        set(ms);
        cal.set(field, amount);
        return cal.getTime().getTime();
    }
    /**
     * Set time to be the first day of whatever month timeInMs is
     * @param timeInMs time in ms
     * @return resulting time of the first day of the month in ms
     */
    public synchronized static long setToFirstDayOfMonth(long timeInMs){
        set(timeInMs);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime().getTime();
    }

    /**
     * Set the underlying calendar instance to the time ms
     * @param ms time in ms to set the calendar to
     */
    private synchronized static void set(long ms){
        cal.setTime(new Date(ms));
    }
    /**
     * Returns true if {@code current}  is between {@code min} and {@code max}
     * @param current time in ms
     * @param min lower limit
     * @param max upper late
     * @return true if date is between min and max
     */
    public static boolean isBetween(long current, long min, long max){
        return (min == 0 && max == 0) || (current > min && current < max);
    }
}
