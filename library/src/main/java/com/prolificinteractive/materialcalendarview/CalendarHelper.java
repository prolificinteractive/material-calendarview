package com.prolificinteractive.materialcalendarview;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.YEAR;

/**
 * Created by faisal-alqadi on 2/20/15.
 */
public class CalendarHelper {
    public static final Calendar cal;
    static{
        cal = Calendar.getInstance();
    }


    public synchronized static int getDayOfMonth(long ms){
        return get(ms, Calendar.DAY_OF_MONTH);
    }

    public synchronized static int getDayOfWeek(long ms){
        return get(ms, Calendar.DAY_OF_WEEK);
    }

    public synchronized static String getDayOfWeekDisplayName(int dayOfWeek){
        cal.set(DAY_OF_WEEK, dayOfWeek);
        return cal.getDisplayName(DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
    }

    public synchronized static int getMonth(long ms){
        return get(ms, Calendar.MONTH);
    }

    public synchronized static int getYear(long ms){
        return get(ms, Calendar.YEAR);
    }

    public synchronized static int get(long ms, int field){
        set(ms);
        return cal.get(field);
    }

    public synchronized static String asString(long ms){
        return String.format(Locale.US, "CalendarDay{%d-%d-%d}", getDayOfMonth(ms), getMonth(ms) + 1, get(ms, YEAR));
    }

    public synchronized static long add(long ms, int field, int amount){
        set(ms);
        cal.add(field, amount);
        return cal.getTime().getTime();
    }

    public synchronized static long set(long ms, int field, int amount){
        set(ms);
        cal.set(field, amount);
        return cal.getTime().getTime();
    }

    public synchronized static long setToFirstDayOfMonth(long ms){
        set(ms);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime().getTime();
    }

    private synchronized static void set(long ms){
        cal.setTime(new Date(ms));
    }

    public static boolean isInRange(long ms, long min, long max){
        return (min == 0 && max == 0) || (ms > min && ms < max);
    }
}
