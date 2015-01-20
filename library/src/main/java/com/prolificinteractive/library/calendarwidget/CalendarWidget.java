package com.prolificinteractive.library.calendarwidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *
 */
public class CalendarWidget extends LinearLayout implements View.OnClickListener, MonthView.Callbacks {

    private static final DateFormat TITLE_FORMAT = new SimpleDateFormat(
            "MMMM yyyy",
            Locale.getDefault()
    );

    private final TextView title;
    private final DirectionButton buttonPast;
    private final DirectionButton buttonFuture;
    private final MonthView monthView;

    private CalendarWrapper calendar = CalendarWrapper.getInstance();
    private CalendarDay selectedDate = null;
    private CalendarDay minDate = null;
    private CalendarDay maxDate = null;

    private OnDateChangedListener listener;

    private int accentColor = 0;
    private int arrowColor = Color.BLACK;

    public CalendarWidget(Context context) {
        this(context, null);
    }

    public CalendarWidget(Context context, AttributeSet attrs) {
        super(context, attrs);

        calendar.setToFirstDay();

        setOrientation(VERTICAL);
        setClipChildren(false);
        setClipToPadding(false);

        LayoutInflater.from(getContext()).inflate(R.layout.cw__calendar_widget, this);

        title = (TextView) findViewById(R.id.cw__calendar_widget_title);
        buttonPast = (DirectionButton) findViewById(R.id.cw__calendar_widget_button_backwards);
        buttonFuture = (DirectionButton) findViewById(R.id.cw__calendar_widget_button_forward);
        monthView = (MonthView) findViewById(R.id.cw__calendar_widget_month);

        title.setOnClickListener(this);
        buttonPast.setOnClickListener(this);
        buttonFuture.setOnClickListener(this);

        monthView.setCallbacks(this);

        TypedArray a =
                context.getTheme().obtainStyledAttributes(attrs, R.styleable.CalendarWidget, 0, 0);
        try {
            setArrowColor(a.getColor(R.styleable.CalendarWidget_arrowColor, Color.BLACK));
            setSelectionColor(
                    a.getColor(
                            R.styleable.CalendarWidget_selectionColor,
                            getThemeAccentColor(context)
                    )
            );

            int taId = a.getResourceId(R.styleable.CalendarWidget_headerTextAppearance, -1);
            if(taId != -1) {
                setHeaderTextAppearance(taId);
            }

            taId = a.getResourceId(R.styleable.CalendarWidget_weekDayTextAppearance, -1);
            if(taId != -1) {
                setWeekDayTextAppearance(taId);
            }

            taId = a.getResourceId(R.styleable.CalendarWidget_dateTextAppearance, -1);
            if(taId != -1) {
                setDateTextAppearance(taId);
            }

            setShowOtherMonths(a.getBoolean(R.styleable.CalendarWidget_showOtherMonths, false));

        } finally {
            a.recycle();
        }

        setCurrentDate(new CalendarDay());
        updateUi();
    }

    @Override
    public void onDateChanged(CalendarDay date) {
        setSelectedDate(date);

        if(listener != null) {
            listener.onDateChanged(this, date);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.cw__calendar_widget_button_forward) {
            calendar.add(Calendar.MONTH, 1);
            updateUi();
        } else if(id == R.id.cw__calendar_widget_button_backwards) {
            calendar.add(Calendar.MONTH, -1);
            updateUi();
        }
    }

    /**
     * Sets the listener to be notified upon selected date change.
     *
     * @param listener thing to be notified
     */
    public void setOnDateChangedListener(OnDateChangedListener listener) {
        this.listener = listener;
    }

    private void updateUi() {
        title.setText(TITLE_FORMAT.format(calendar.getTime()));
        monthView.setMinimumDate(minDate);
        monthView.setMaximumDate(maxDate);
        monthView.setSelectedDate(selectedDate);
        monthView.setDate(calendar);
        buttonPast.setEnabled(canGoBack());
        buttonFuture.setEnabled(canGoForward());
    }

    /**
     * TODO should this be public?
     *
     * @return true if there is a future month that can be shown
     */
    private boolean canGoForward() {
        if(maxDate == null) {
            return true;
        }

        Calendar maxCal = maxDate.getCalendar();
        maxCal.add(Calendar.MONTH, -1);
        return calendar.compareTo(maxCal) < 0;
    }

    /**
     * TODO should this be public?
     *
     * @return true if there is a previous month that can be shown
     */
    private boolean canGoBack() {
        if(minDate == null) {
            return true;
        }

        Calendar minCal = minDate.getCalendar();
        return calendar.compareTo(minCal) >= 0;
    }

    /**
     * @return the color used for the selection
     */
    public int getSelectionColor() {
        return accentColor;
    }

    /**
     * @param color The selection color
     */
    public void setSelectionColor(int color) {
        accentColor = color;
        monthView.setSelectionColor(color);
        invalidate();
    }

    /**
     * @return color used to draw arrows
     */
    public int getArrowColor() {
        return arrowColor;
    }

    /**
     * @param color the new color for the paging arrows
     */
    public void setArrowColor(int color) {
        arrowColor = color;
        buttonPast.setColor(color);
        buttonFuture.setColor(color);
        invalidate();
    }

    /**
     * @param resourceId The text appearance resource id.
     */
    public void setHeaderTextAppearance(int resourceId) {
        title.setTextAppearance(getContext(), resourceId);
    }

    /**
     * @param resourceId The text appearance resource id.
     */
    public void setDateTextAppearance(int resourceId) {
        monthView.setDateTextAppearance(resourceId);
    }

    /**
     * @param resourceId The text appearance resource id.
     */
    public void setWeekDayTextAppearance(int resourceId) {
        monthView.setWeekDayTextAppearance(resourceId);
    }

    /**
     * @return the currently selected day, or null if no selection
     */
    public CalendarDay getSelectedDate() {
        return selectedDate;
    }

    /**
     * @param calendar a Calendar set to a day to select
     */
    public void setSelectedDate(Calendar calendar) {
        setSelectedDate(new CalendarDay(calendar));
    }

    /**
     * @param date a Date to set as selected
     */
    public void setSelectedDate(Date date) {
        setSelectedDate(new CalendarDay(date));
    }

    /**
     * @param day a CalendarDay to set as selected
     */
    public void setSelectedDate(CalendarDay day) {
        selectedDate = day;
        setCurrentDate(day);
    }

    /**
     * @param calendar a Calendar set to a day to focus the calendar on
     */
    public void setCurrentDate(Calendar calendar) {
        setCurrentDate(new CalendarDay(calendar));
    }

    /**
     * @param date a Date to focus the calendar on
     */
    public void setCurrentDate(Date date) {
        setCurrentDate(new CalendarDay(date));
    }

    /**
     * @param day a CalendarDay to focus the calendar on
     */
    public void setCurrentDate(CalendarDay day) {
        day.copyTo(calendar);
        calendar.setToFirstDay();
        updateUi();
    }

    /**
     * @return the minimum selectable date for the calendar, if any
     */
    public CalendarDay getMinimumDate() {
        return minDate;
    }

    /**
     * @param calendar set the minimum selectable date, null for no minimum
     */
    public void setMinimumDate(Calendar calendar) {
        minDate = calendar == null ? null : new CalendarDay(calendar);
        updateUi();
    }

    /**
     * @return the maximum selectable date for the calendar, if any
     */
    public CalendarDay getMaximumDate() {
        return maxDate;
    }

    /**
     * @param calendar set the maximum selectable date, null for no maximum
     */
    public void setMaximumDate(Calendar calendar) {
        maxDate = calendar == null ? null : new CalendarDay(calendar);
        updateUi();
    }

    /**
     * @param showOtherMonths show days from the previous and next months, default is false
     */
    public void setShowOtherMonths(boolean showOtherMonths) {
        monthView.setShowOtherMonths(showOtherMonths);
    }

    /**
     * @return true if days from previous or next months are shown, otherwise false.
     */
    public boolean getShowOtherMonths() {
        return monthView.getShowOtherMonths();
    }

    private void clampCalendar() {
        if(maxDate != null && calendar.compareTo(maxDate.getCalendar()) >= 0) {
            maxDate.copyTo(calendar);
        }
        if(minDate != null && calendar.compareTo(minDate.getCalendar()) <= 0) {
            minDate.copyTo(calendar);
        }
        calendar.setToFirstDay();
    }

    private static int getThemeAccentColor(Context context) {
        int colorAttr;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            colorAttr = android.R.attr.colorAccent;
        } else {
            //Get colorAccent defined for AppCompat
            colorAttr = context.getResources().getIdentifier("colorAccent", "attr", context.getPackageName());
        }
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(colorAttr, outValue, true);
        return outValue.data;
    }
}
