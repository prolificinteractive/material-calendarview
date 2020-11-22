package com.prolificinteractive.materialcalendarview

import android.os.Parcel
import android.os.Parcelable
import org.threeten.bp.LocalDate

class CalendarDay
/**
 * @param date [LocalDate] instance
 */
private constructor(
        /**
         * Everything is based on this variable for [CalendarDay].
         */
        var date: LocalDate
) : Parcelable {

    /**
     * @param year new instance's year
     * @param month new instance's month as defined by [java.util.Calendar]
     * @param day new instance's day of month
     */
    private constructor(year: Int, month: Int, day: Int) : this(LocalDate.of(year, month, day))

    val year: Int
        /**
         * Get the year
         *
         * @return the year for this day
         */
        get() = date.year


    val month: Int
        /**
         * Get the month, represented by values from {@linkplain LocalDate}
         *
         * @return the month of the year as defined by {@linkplain LocalDate}
         */
        get() = date.monthValue


    val day: Int
        /**
         * Get the day
         *
         * @return the day of the month for this day
         */
        get() = date.dayOfMonth

    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readInt(), parcel.readInt())

    /**
     * Determine if this day is within a specified range
     *
     * @param minDate the earliest day, may be null
     * @param maxDate the latest day, may be null
     * @return true if the between (inclusive) the min and max dates.
     */

    fun isInRange(minDate: CalendarDay?, maxDate: CalendarDay?): Boolean {
        return !(minDate != null && minDate.isAfter(this)) &&
                !(maxDate != null && maxDate.isBefore(this))
    }


    /**
     * Determine if this day is before the given instance
     *
     * @param other the other day to test
     * @return true if this is before other, false if equal or after
     */
    infix fun isBefore(other: CalendarDay): Boolean {
        return date.isBefore(other.date)
    }

    /**
     * Determine if this day is after the given instance
     *
     * @param other the other day to test
     * @return true if this is after other, false if equal or before
     */
    infix fun isAfter(other: CalendarDay): Boolean {
        return date.isAfter(other.date)
    }

    override fun equals(other: Any?): Boolean {
        return other is CalendarDay && date == other.date
    }

    override fun hashCode(): Int = hashCode(date.year, date.monthValue, date.dayOfMonth)

    override fun toString(): String {
        return ("CalendarDay{" + date.year + "-" + date.monthValue + "-" + date.dayOfMonth + "}")
    }


    companion object {

        /**
         * Get a new instance set to today
         *
         * @return CalendarDay set to today's date
         */
        @JvmStatic
        fun today(): CalendarDay? {
            return from(LocalDate.now())
        }

        /**
         * Get a new instance set to the specified day
         *
         * @param year new instance's year
         * @param month new instance's month as defined by [java.util.Calendar]
         * @param day new instance's day of month
         * @return CalendarDay set to the specified date
         */
        @JvmStatic
        fun from(year: Int, month: Int, day: Int): CalendarDay {
            return CalendarDay(year, month, day)
        }

        /**
         * Get a new instance set to the specified day
         *
         * @param date [LocalDate] to pull date information from. Passing null will return null
         * @return CalendarDay set to the specified date
         */
        @JvmStatic
        fun from(date: LocalDate?): CalendarDay? {
            return date?.let {
                CalendarDay(date)
            }
        }

        @JvmStatic
        private fun hashCode(year: Int, month: Int, day: Int): Int {
            //Should produce hashes like "20150401"
            return year * 10000 + month * 100 + day
        }

        @JvmStatic
        val CREATOR = object : Parcelable.Creator<CalendarDay> {
            override fun createFromParcel(parcel: Parcel): CalendarDay {
                return CalendarDay(parcel)
            }

            override fun newArray(size: Int): Array<CalendarDay?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(date.year)
        dest.writeInt(date.monthValue)
        dest.writeInt(date.dayOfMonth)
    }

    override fun describeContents(): Int = 0

}