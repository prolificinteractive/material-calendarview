package com.prolificinteractive.materialcalendarview;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * An imputable representation of a day on a calendar
 */
public final class CalendarDay implements Parcelable {

    /**
     * @return CalendarDay set to today's date
     */
    public static @NonNull CalendarDay today() {
        return from(CalendarUtils.getInstance());
    }

    /**
     *
     * @param year new instance's year
     * @param month new instance's month as defined by {@linkplain java.util.Calendar}
     * @param day new instance's day of month
     * @return CalendarDay set to the specified date
     */
    public static @NonNull CalendarDay from(int year, int month, int day) {
        return new CalendarDay(year, month, day);
    }

    /**
     * @return CalendarDay set to the specified date
     */
    public static CalendarDay from(Calendar calendar) {
        if(calendar == null) {
            return null;
        }
        return from(
                CalendarUtils.getYear(calendar),
                CalendarUtils.getMonth(calendar),
                CalendarUtils.getDay(calendar)
        );
    }

    /**
     * @return CalendarDay set to the specified date
     */
    public static CalendarDay from(Date date) {
        if(date == null) {
            return null;
        }
        return from(CalendarUtils.getInstance(date));
    }

    private final int year;
    private final int month;
    private final int day;

    /**
     * Cache for calls to {@linkplain #getCalendar()}
     */
    private Calendar _calendar;

    /**
     * Cache for calls to {@linkplain #getDate()}
     */
    private Date _date;

    /**
     * Initialized to the current day
     *
     * @see CalendarDay#today()
     */
    @Deprecated
    public CalendarDay() {
        this(CalendarUtils.getInstance());
    }

    /**
     * @see CalendarDay#from(Calendar)
     *
     * @param calendar source to pull date information from for this instance
     */
    @Deprecated
    public CalendarDay(Calendar calendar) {
        this(
                CalendarUtils.getYear(calendar),
                CalendarUtils.getMonth(calendar),
                CalendarUtils.getDay(calendar)
        );
    }

    /**
     * @see CalendarDay#from(Calendar)
     *
     * @param year new instance's year
     * @param month new instance's month as defined by {@linkplain java.util.Calendar}
     * @param day new instance's day of month
     */
    @Deprecated
    public CalendarDay(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * @see CalendarDay#from(Date)
     *
     * @param date source to pull date information from for this instance
     */
    @Deprecated
    public CalendarDay(Date date) {
        this(CalendarUtils.getInstance(date));
    }

    /**
     * @return the year for this day
     */
    public int getYear() {
        return year;
    }

    /**
     * @return the month of the year as defined by {@linkplain java.util.Calendar}
     */
    public int getMonth() {
        return month;
    }

    /**
     * @return the day of the month for this day
     */
    public int getDay() {
        return day;
    }

    /**
     * @return a date with this days information
     */
    public @NonNull Date getDate() {
        if(_date == null) {
            _date = getCalendar().getTime();
        }
        return _date;
    }

    /**
     * @return a new calendar instance with this day information
     */
    public @NonNull Calendar getCalendar() {
        if(_calendar == null) {
            _calendar = CalendarUtils.getInstance();
            copyTo(_calendar);
        }
        return _calendar;
    }

    /**
     * @param calendar calendar to set date information to
     */
    public void copyTo(Calendar calendar) {
        calendar.clear();
        calendar.set(year, month, day);
    }

    /**
     * @param minDate the earliest day, may be null
     * @param maxDate the latest day, may be null
     * @return true if the between (inclusive) the min and max dates.
     */
    public boolean isInRange(CalendarDay minDate, CalendarDay maxDate) {
        return !(minDate != null && minDate.isAfter(this)) &&
                !(maxDate != null && maxDate.isBefore(this));
    }

    /**
     * @param other the other day to test
     * @return true if this is before other, false if equal or after
     */
    public boolean isBefore(@NonNull CalendarDay other) {
        if(other == null) {
            throw new IllegalArgumentException("other cannot be null");
        }
        if (year == other.year) {
            return ((month == other.month) ? (day < other.day) : (month < other.month));
        }
        else {
            return year < other.year;
        }
    }

    /**
     * @param other the other day to test
     * @return true if this is after other, false if equal or before
     */
    public boolean isAfter(@NonNull CalendarDay other) {
        if(other == null) {
            throw new IllegalArgumentException("other cannot be null");
        }

        if (year == other.year) {
            return (month == other.month) ? (day > other.day) : (month > other.month);
        }
        else {
            return year > other.year;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CalendarDay that = (CalendarDay) o;

        return day == that.day && month == that.month && year == that.year;
    }

    @Override
    public int hashCode() {
        return hashCode(year, month, day);
    }

    private static int hashCode(int year, int month, int day) {
        //Should produce hashes like "20150401"
        return (year * 10000) + (month * 100) + day;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "CalendarDay{%d-%d-%d}", year, month + 1, day);
    }

    /*
     * Parcelable Stuff
     */

    public CalendarDay(Parcel in) {
        this(in.readInt(), in.readInt(), in.readInt());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(year);
        dest.writeInt(month);
        dest.writeInt(day);
    }

    public static final Creator<CalendarDay> CREATOR = new Creator<CalendarDay>() {
        public CalendarDay createFromParcel(Parcel in) {
            return new CalendarDay(in);
        }

        public CalendarDay[] newArray(int size) {
            return new CalendarDay[size];
        }
    };
}
