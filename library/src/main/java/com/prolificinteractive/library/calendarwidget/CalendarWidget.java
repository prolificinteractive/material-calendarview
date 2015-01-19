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
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *
 */
public class CalendarWidget extends LinearLayout implements View.OnClickListener, MonthView.Callbacks, NumberPicker.OnValueChangeListener {

    private static final DateFormat TITLE_FORMAT = new SimpleDateFormat(
        "MMMM yyyy",
        Locale.getDefault()
    );

    private final TextView title;
    private final DirectionButton buttonPast;
    private final DirectionButton buttonFuture;
    private final ViewSwitcher switcher;
    private final MonthView monthView;
    private final NumberPicker yearView;

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

        LayoutInflater.from(getContext()).inflate(R.layout.cw__calendar_widget, this);

        title = (TextView) findViewById(R.id.cw__calendar_widget_title);
        buttonPast = (DirectionButton) findViewById(R.id.cw__calendar_widget_button_backwards);
        buttonFuture = (DirectionButton) findViewById(R.id.cw__calendar_widget_button_forward);
        switcher = (ViewSwitcher) findViewById(R.id.cw__calendar_widget_switcher);
        monthView = (MonthView) findViewById(R.id.cw__calendar_widget_month);
        yearView = (NumberPicker) findViewById(R.id.cw__calendar_widget_year);

        yearView.setOnValueChangedListener(this);

        title.setOnClickListener(this);
        buttonPast.setOnClickListener(this);
        buttonFuture.setOnClickListener(this);

        monthView.setCallbacks(this);

        TypedArray a =
            context.getTheme().obtainStyledAttributes(attrs, R.styleable.CalendarWidget, 0, 0);
        try {
            setArrowColor(a.getColor(R.styleable.CalendarWidget_arrowColor, Color.BLACK));
            setAccentColor(a.getColor(R.styleable.CalendarWidget_selectionColor, getThemeAccentColor()));

            int taId = a.getResourceId(R.styleable.CalendarWidget_headerTextAppearance, -1);
            if(taId != -1) {
                title.setTextAppearance(getContext(), taId);
            }

            taId = a.getResourceId(R.styleable.CalendarWidget_weekdayTextAppearance, -1);
            if(taId != -1) {
                monthView.setWeekdayTextAppearance(taId);
            }

            taId = a.getResourceId(R.styleable.CalendarWidget_dayTextAppearance, -1);
            if(taId != -1) {
                monthView.setDayTextAppearance(taId);
            }

            setShowOtherMonths(a.getBoolean(R.styleable.CalendarWidget_showOtherMonths, false));

        } finally {
            a.recycle();
        }

        setCurrentDate(new CalendarDay());
        updateUi();
    }

    private void updateUi() {
        title.setText(TITLE_FORMAT.format(calendar.getTime()));
        monthView.setMinimumDate(minDate);
        monthView.setMaximumDate(maxDate);
        monthView.setSelectedDate(selectedDate);
        monthView.setDate(calendar);
        buttonPast.setEnabled(canGoBack());
        buttonFuture.setEnabled(canGoForward());

        yearView.setMinValue(minDate == null ? 0 : minDate.getYear());
        yearView.setMaxValue(maxDate == null ? 0 : maxDate.getYear());
        yearView.setValue(calendar.getYear());
    }

    private boolean canGoForward() {
        if(maxDate == null) {
            return true;
        }

        Calendar maxCal = maxDate.getCalendar();
        maxCal.add(Calendar.MONTH, -1);
        return calendar.compareTo(maxCal) < 0;
    }

    private boolean canGoBack() {
        if(minDate == null) {
            return true;
        }

        Calendar minCal = minDate.getCalendar();
        return calendar.compareTo(minCal) >= 0;
    }

    private int getThemeAccentColor() {
        int colorAttr = 0;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            colorAttr = android.R.attr.colorAccent;
        } else {
            colorAttr = getResources().getIdentifier("colorAccent", "attr", getContext().getPackageName());
        }
        TypedValue outValue = new TypedValue();
        getContext().getTheme().resolveAttribute(colorAttr, outValue, true);
        return outValue.data;
    }

    public int getAccentColor() {
        return accentColor;
    }

    public void setAccentColor(int color) {
        accentColor = color;
        monthView.setAccentColor(color);
        invalidate();
    }

    public int getArrowColor() {
        return arrowColor;
    }

    public void setArrowColor(int color) {
        arrowColor = color;
        buttonPast.setColor(color);
        buttonFuture.setColor(color);
        invalidate();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.cw__calendar_widget_button_forward) {
            calendar.add(Calendar.MONTH, 1);
            updateUi();
        } else if(v.getId() == R.id.cw__calendar_widget_button_backwards) {
            calendar.add(Calendar.MONTH, -1);
            updateUi();
        } else if(v.getId() == R.id.cw__calendar_widget_title) {
            switcher.showNext();
        }
    }

    public void setOnDateChangedListener(OnDateChangedListener listener) {
        this.listener = listener;
    }

    public CalendarDay getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(Calendar calendar) {
        setSelectedDate(new CalendarDay(calendar));
    }

    public void setSelectedDate(Date date) {
        setSelectedDate(new CalendarDay(date));
    }

    public void setSelectedDate(CalendarDay day) {
        selectedDate = day;
        setCurrentDate(day);
    }

    public void setCurrentDate(Calendar calendar) {
        setCurrentDate(new CalendarDay(calendar));
    }

    public void setCurrentDate(Date date) {
        setCurrentDate(new CalendarDay(date));
    }

    public void setCurrentDate(CalendarDay day) {
        day.copyTo(calendar);
        calendar.setToFirstDay();
        updateUi();
    }

    public void setMinimumDate(Calendar calendar) {
        minDate = calendar == null ? null : new CalendarDay(calendar);
        updateUi();
    }

    public void setMaximumDate(Calendar calendar) {
        maxDate = calendar == null ? null : new CalendarDay(calendar);
        updateUi();
    }

    public void setShowOtherMonths(boolean showOtherMonths) {
        monthView.setShowOtherMonths(showOtherMonths);
    }

    public boolean getShowOtherMonths() {
        return monthView.getShowOtherMonths();
    }

    @Override
    public void onDateChanged(CalendarDay date) {
        setSelectedDate(date);

        if(listener != null) {
            listener.onDateChanged(this, date);
        }
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        calendar.set(Calendar.YEAR, newVal);
        clampCalendar();
        updateUi();
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
}
