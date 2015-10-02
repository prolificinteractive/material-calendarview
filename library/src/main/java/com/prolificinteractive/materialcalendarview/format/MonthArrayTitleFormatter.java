package com.prolificinteractive.materialcalendarview.format;

import android.text.SpannableStringBuilder;

import com.prolificinteractive.materialcalendarview.CalendarDay;

/**
 * Use an array to generate a month/year label
 */
public class MonthArrayTitleFormatter implements TitleFormatter {

    private final CharSequence[] monthLabels;

    /**
     * Format using an array of month labels
     *
     * @param monthLabels an array of 12 labels to use for months, starting with January
     */
    public MonthArrayTitleFormatter(CharSequence[] monthLabels) {
        if (monthLabels == null) {
            throw new IllegalArgumentException("Label array cannot be null");
        }
        if (monthLabels.length < 12) {
            throw new IllegalArgumentException("Label array is too short");
        }
        this.monthLabels = monthLabels;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CharSequence format(CalendarDay day) {
        return new SpannableStringBuilder()
                .append(monthLabels[day.getMonth()])
                .append(" ")
                .append(String.valueOf(day.getYear()));
    }
}
