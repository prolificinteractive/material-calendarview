package com.prolificinteractive.materialcalendarview;

import android.graphics.drawable.Drawable;

/**
 * Abstraction layer to help in decorating Day views
 */
public final class DayViewFacade {

    private DayView dayView;
    private CharSequence initialText;

    public DayViewFacade() {
    }

    /**
     * Set the drawable to use in the 'normal' state of the Day view
     *
     * @param drawable Drawable to use, null for default
     */
    public void setBackgroundUnselected(Drawable drawable) {
        dayView.setCustomBackground(drawable);
    }

    /**
     * Set the entire background drawable of the Day view
     *
     * @param drawable the drawable for the Day view
     */
    public void setBackground(Drawable drawable) {
        dayView.setBackgroundDrawable(drawable);
    }

    /**
     * @return the text to be decorated. This will always be the same
     * even if after you call {@linkplain #setText(CharSequence)}
     */
    public CharSequence getText() {
        return initialText;
    }

    /**
     * Set the text on the Day view
     * @param text Text to display
     */
    public void setText(CharSequence text) {
        dayView.setText(text);
    }

    /**
     * @return The {@linkplain CalendarDay} for the Day view being decorated
     */
    public CalendarDay getDate() {
        return dayView.getDate();
    }

    protected void setDayView(DayView dayView) {
        this.dayView = dayView;
        this.initialText = dayView.getText();
    }
}
