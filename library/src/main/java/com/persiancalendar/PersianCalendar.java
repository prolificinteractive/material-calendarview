package com.persiancalendar;
/**
 * Persian Calendar see: http://code.google.com/p/persian-calendar/
 * Copyright (C) 2012  Mortezaadi@gmail.com
 * PersianCalendar.java
 * <p/>
 * Persian Calendar is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 *
 * <strong> Persian(Shamsi) calendar </strong>
 * <p>
 * </p>
 * <p>
 * The calendar consists of 12 months, the first six of which are 31 days, the
 * next five 30 days, and the final month 29 days in a normal year and 30 days
 * in a leap year.
 * </p>
 * <p>
 * As one of the few calendars designed in the era of accurate positional
 * astronomy, the Persian calendar uses a very complex leap year structure which
 * makes it the most accurate solar calendar in use today. Years are grouped
 * into cycles which begin with four normal years after which every fourth
 * subsequent year in the cycle is a leap year. Cycles are grouped into grand
 * cycles of either 128 years (composed of cycles of 29, 33, 33, and 33 years)
 * or 132 years, containing cycles of of 29, 33, 33, and 37 years. A great grand
 * cycle is composed of 21 consecutive 128 year grand cycles and a final 132
 * grand cycle, for a total of 2820 years. The pattern of normal and leap years
 * which began in 1925 will not repeat until the year 4745!
 * </p>
 * </p> Each 2820 year great grand cycle contains 2137 normal years of 365 days
 * and 683 leap years of 366 days, with the average year length over the great
 * grand cycle of 365.24219852. So close is this to the actual solar tropical
 * year of 365.24219878 days that the Persian calendar accumulates an error of
 * one day only every 3.8 million years. As a purely solar calendar, months are
 * not synchronized with the phases of the Moon. </p>
 * <p>
 * </p>
 *
 * <p>
 * <strong>PersianCalendar</strong> by extending Default GregorianCalendar
 * provides capabilities such as:
 * </p>
 * <p>
 * </p>
 *
 * <li>you can set the date in Persian by setPersianDate(persianYear,
 * persianMonth, persianDay) and get the Gregorian date or vice versa</li>
 * <p>
 * </p>
 * <li>determine is the current date is Leap year in persian calendar or not by
 * IsPersianLeapYear()</li>
 * <p>
 * </p>
 * <li>getPersian short and long Date String getPersianShortDate() and
 * getPersianLongDate you also can set delimiter to assign delimiter of returned
 * dateString</li>
 * <p>
 * </p>
 * <li>Parse string based on assigned delimiter</li>
 * <p>
 * </p>
 * <p>
 * </p>
 * <p>
 * </p>
 * <p>
 * <strong> Example </strong>
 * </p>
 * <p>
 * </p>
 *
 * <pre>
 * {@code
 *       PersianCalendar persianCal = new PersianCalendar();
 *       System.out.println(persianCal.getPersianShortDate());
 *
 *       persianCal.set(1982, Calendar.MAY, 22);
 *       System.out.println(persianCal.getPersianShortDate());
 *
 *       persianCal.setDelimiter(" , ");
 *       persianCal.parse("1361 , 03 , 01");
 *       System.out.println(persianCal.getPersianShortDate());
 *
 *       persianCal.setPersianDate(1361, 3, 1);
 *       System.out.println(persianCal.getPersianLongDate());
 *       System.out.println(persianCal.getTime());
 *
 *       persianCal.addPersianDate(Calendar.MONTH, 33);
 *       persianCal.addPersianDate(Calendar.YEAR, 5);
 *       persianCal.addPersianDate(Calendar.DATE, 50);
 *
 * }
 *
 * <pre>
 * @author Morteza  contact: <a href="mailto:Mortezaadi@gmail.com">Mortezaadi@gmail.com</a>
 * @version 1.1
 */
public class PersianCalendar extends GregorianCalendar {

    private static final long serialVersionUID = 5541422440580682494L;

    private int persianYear;
    private int persianMonth;
    private int persianDay;
    // use to seperate PersianDate's field and also Parse the DateString based
    // on this delimiter
    private String delimiter = "/";

    private long convertToMilis(long julianDate) {
        return PersianCalendarConstants.MILLIS_JULIAN_EPOCH + julianDate * PersianCalendarConstants.MILLIS_OF_A_DAY
                + PersianCalendarUtils.ceil(getTimeInMillis() - PersianCalendarConstants.MILLIS_JULIAN_EPOCH, PersianCalendarConstants.MILLIS_OF_A_DAY);
    }

    /**
     * default constructor
     *
     * most of the time we don't care about TimeZone when we persisting Date or
     * doing some calculation on date. <strong> Default TimeZone was set to
     * "GMT" </strong> in order to make developer to work more convenient with
     * the library; however you can change the TimeZone as you do in
     * GregorianCalendar by calling setTimeZone()
     */
    public PersianCalendar(long millis) {
        setTimeInMillis(millis);
    }

    /**
     * default constructor
     *
     * most of the time we don't care about TimeZone when we persisting Date or
     * doing some calculation on date. <strong> Default TimeZone was set to
     * "GMT" </strong> in order to make developer to work more convenient with
     * the library; however you can change the TimeZone as you do in
     * GregorianCalendar by calling setTimeZone()
     */
    public PersianCalendar() {
        setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    /**
     * Calculate persian date from current Date and populates the corresponding
     * fields(persianYear, persianMonth, persianDay)
     */
    protected void calculatePersianDate() {
        long julianDate = ((long) Math.floor((getTimeInMillis() - PersianCalendarConstants.MILLIS_JULIAN_EPOCH)) / PersianCalendarConstants.MILLIS_OF_A_DAY);
        long PersianRowDate = PersianCalendarUtils.julianToPersian(julianDate);
        long year = PersianRowDate >> 16;
        int month = (int) (PersianRowDate & 0xff00) >> 8;
        int day = (int) (PersianRowDate & 0xff);
        this.persianYear = (int) (year > 0 ? year : year - 1);
        this.persianMonth = month;
        this.persianDay = day;
    }

    /**
     *
     * Determines if the given year is a leap year in persian calendar. Returns
     * true if the given year is a leap year.
     *
     * @return boolean
     */
    public boolean isPersianLeapYear() {
        // calculatePersianDate();
        return PersianCalendarUtils.isPersianLeapYear(this.persianYear);
    }

    /**
     * set the persian date it converts PersianDate to the Julian and assigned
     * equivalent milliseconds to the instance
     *
     * @param persianYear persianYear
     * @param persianMonth persianMonth
     * @param persianDay persianDay
     */
    public void setPersianDate(int persianYear, int persianMonth, int persianDay) {
        this.persianYear = persianYear;
        this.persianMonth = persianMonth;
        this.persianDay = persianDay;
        setTimeInMillis(convertToMilis(PersianCalendarUtils.persianToJulian(this.persianYear > 0 ? this.persianYear : this.persianYear + 1, this.persianMonth - 1, this.persianDay)));
    }

    public int getPersianYear() {
        // calculatePersianDate();
        return this.persianYear;
    }

    /**
     *
     * @return int persian month number
     */
    public int getPersianMonth() {
        // calculatePersianDate();
        return this.persianMonth + 1;
    }

    /**
     *
     * @return String persian month name
     */
    public String getPersianMonthName() {
        // calculatePersianDate();
        return PersianCalendarConstants.persianMonthNames[this.persianMonth];
    }

    /**
     *
     * @return int Persian day in month
     */
    public int getPersianDay() {
        // calculatePersianDate();
        return this.persianDay;
    }

    /**
     *
     * @return String Name of the day in week
     */
    public String getPersianWeekDayName() {
        switch (get(DAY_OF_WEEK)) {
            case SATURDAY:
                return PersianCalendarConstants.persianWeekDays[0];
            case SUNDAY:
                return PersianCalendarConstants.persianWeekDays[1];
            case MONDAY:
                return PersianCalendarConstants.persianWeekDays[2];
            case TUESDAY:
                return PersianCalendarConstants.persianWeekDays[3];
            case WEDNESDAY:
                return PersianCalendarConstants.persianWeekDays[4];
            case THURSDAY:
                return PersianCalendarConstants.persianWeekDays[5];
            default:
                return PersianCalendarConstants.persianWeekDays[6];
        }

    }

    /**
     *
     * @return String of Persian Date ex: شنبه 01 خرداد 1361
     */
    public String getPersianLongDate() {
        return getPersianWeekDayName() + "  " + this.persianDay + "  " + getPersianMonthName() + "  " + this.persianYear;
    }

    public String getPersianLongDateAndTime() {
        return getPersianLongDate() + " ساعت " + get(HOUR_OF_DAY) + ":" + get(MINUTE) + ":" + get(SECOND);
    }

    /**
     *
     * @return String of persian date formatted by
     *         'YYYY[delimiter]mm[delimiter]dd' default delimiter is '/'
     */
    public String getPersianShortDate() {
        // calculatePersianDate();
        return "" + formatToMilitary(this.persianYear) + delimiter + formatToMilitary(getPersianMonth()) + delimiter + formatToMilitary(this.persianDay);
    }

    public String getPersianShortDateTime() {
        return "" + formatToMilitary(this.persianYear) + delimiter + formatToMilitary(getPersianMonth()) + delimiter + formatToMilitary(this.persianDay) + " " + formatToMilitary(this.get(HOUR_OF_DAY)) + ":" + formatToMilitary(get(MINUTE))
                + ":" + formatToMilitary(get(SECOND));
    }

    private String formatToMilitary(int i) {
        return (i < 9) ? "0" + i : String.valueOf(i);
    }

    /**
     * add specific amout of fields to the current date for now doesnt handle
     * before 1 farvardin hejri (before epoch)
     *
     * @param field field
     * @param amount amount
     *            <pre>
     *  Usage:
     *  {@code
     *  addPersianDate(Calendar.YEAR, 2);
     *  addPersianDate(Calendar.MONTH, 3);
     *  }
     * </pre>
     *
     *            u can also use Calendar.HOUR_OF_DAY,Calendar.MINUTE,
     *            Calendar.SECOND, Calendar.MILLISECOND etc
     */
    //
    public void addPersianDate(int field, int amount) {
        if (amount == 0) {
            return; // Do nothing!
        }

        if (field < 0 || field >= ZONE_OFFSET) {
            throw new IllegalArgumentException();
        }

        if (field == YEAR) {
            setPersianDate(this.persianYear + amount, getPersianMonth(), this.persianDay);
            return;
        } else if (field == MONTH) {
            setPersianDate(this.persianYear + ((getPersianMonth() + amount) / 12), (getPersianMonth() + amount) % 12, this.persianDay);
            return;
        }
        add(field, amount);
        calculatePersianDate();
    }

    /**
     * <pre>
     *    use <code>{@link PersianDateParser}</code> to parse string
     *    and get the Persian Date.
     * </pre>
     *
     * @see PersianDateParser
     * @param dateString dateString
     */
    public void parse(String dateString) {
        PersianCalendar p = new PersianDateParser(dateString, delimiter).getPersianDate();
        setPersianDate(p.getPersianYear(), p.getPersianMonth(), p.getPersianDay());
    }

    public String getDelimiter() {
        return delimiter;
    }

    /**
     * assign delimiter to use as a separator of date fields.
     *
     * @param delimiter delimiter
     */
    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public String toString() {
        String str = super.toString();
        return str.substring(0, str.length() - 1) + ",PersianDate=" + getPersianShortDate() + "]";
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);

    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public void set(int field, int value) {
        super.set(field, value);
        calculatePersianDate();
    }

    @Override
    public void setTimeInMillis(long millis) {
        super.setTimeInMillis(millis);
        calculatePersianDate();
    }

    @Override
    public void setTimeZone(TimeZone zone) {
        super.setTimeZone(zone);
        calculatePersianDate();
    }
}
