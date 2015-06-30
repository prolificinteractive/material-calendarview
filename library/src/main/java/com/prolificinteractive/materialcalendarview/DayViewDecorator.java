package com.prolificinteractive.materialcalendarview;

/**
 * Decorate Day views with drawables and text manipulation
 */
public interface DayViewDecorator {

    /**
     * @param day {@linkplain CalendarDay} to possibly decorate
     *
     * @return true if this decorator should be applied to the provided day
     */
    boolean shouldDecorate(CalendarDay day);

    /**
     * @param view View to decorate
     */
    void decorate(DayViewFacade view);

}
