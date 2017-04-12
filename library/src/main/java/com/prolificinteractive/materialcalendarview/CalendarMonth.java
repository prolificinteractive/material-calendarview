package com.prolificinteractive.materialcalendarview;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : hafiq on 12/04/2017.
 */

public enum  CalendarMonth {
    JANUARY(0,"Jan"),
    FEBUARY(1,"Feb"),
    MARCH(2,"Mac"),
    APRIL(3,"Apr"),
    MAY(4,"May"),
    JUNE(5,"Jun"),
    JULY(6,"Jul"),
    AUGUST(7,"Aug"),
    SEPTEMBER(8,"Sep"),
    OCTOBER(9,"Oct"),
    NOVEMBER(10,"Nov"),
    DECEMBER(11,"Dec");

    final int monthNum;
    final String name;

    CalendarMonth(int x,String name) {
        this.monthNum = x;
        this.name = name;
    }

    public static CalendarMonth getMonth(int x){
        for (CalendarMonth calendarMonth:values()){
            if (calendarMonth.monthNum == x)
                return calendarMonth;
        }
        return null;
    }

    public static List<String> getList(){
        List<String> month = new ArrayList<>();
        for (CalendarMonth calendarMonth:values()){
            month.add(calendarMonth.name());
        }

        return month;
    }
}
