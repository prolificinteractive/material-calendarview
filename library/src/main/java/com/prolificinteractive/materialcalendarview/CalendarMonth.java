package com.prolificinteractive.materialcalendarview;

/**
 * @author : hafiq on 12/04/2017.
 */

public enum  CalendarMonth {
    JANUARY(0),
    FEBUARY(1),
    MARCH(2),
    APRIL(3),
    MAY(4),
    JUNE(5),
    JULY(6),
    AUGUST(7),
    SEPTEMBER(8),
    OCTOBER(9),
    NOVEMBER(10),
    DECEMBER(11);

    final int monthNum;

    CalendarMonth(int x) {
        this.monthNum = x;
    }
}
