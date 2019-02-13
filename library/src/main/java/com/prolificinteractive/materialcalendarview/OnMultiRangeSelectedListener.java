package com.prolificinteractive.materialcalendarview;

import android.support.annotation.NonNull;

import java.util.List;

public interface OnMultiRangeSelectedListener {
    void onMultiRangeSelected(@NonNull MaterialCalendarView widget, @NonNull List<CalendarDay> dates);
}
