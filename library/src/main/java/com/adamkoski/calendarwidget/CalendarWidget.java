package com.adamkoski.calendarwidget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adamkoski.library.calendarwidget.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.MONTH;

/**
 *
 */
public class CalendarWidget extends LinearLayout implements View.OnClickListener, MonthView.Callbacks {

    private static final DateFormat TITLE_FORMAT = new SimpleDateFormat("MMMM yyyy");
    private TextView title;
    private DirectionButton buttonPast;
    private DirectionButton buttonFuture;
    private MonthView monthView;

    private Calendar calendar;
    private Calendar selectedDate = CalendarUtils.copy(Calendar.getInstance());
    private CalendarDay minDate = null;
    private CalendarDay maxDate = null;

    private OnDateChangedListener listener;

    public CalendarWidget(Context context) {
        super(context);
        init();
    }

    public CalendarWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CalendarWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CalendarWidget(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {

        calendar = Calendar.getInstance();
        CalendarUtils.setToFirstDay(calendar);

        setOrientation(VERTICAL);

        LayoutInflater.from(getContext()).inflate(R.layout.cw__calendar_widget, this);

        title = (TextView) findViewById(R.id.___calendar_widget_title);
        buttonPast = (DirectionButton) findViewById(R.id.___calendar_widget_button_backwards);
        buttonFuture = (DirectionButton) findViewById(R.id.___calendar_widget_button_forward);
        monthView = (MonthView) findViewById(R.id.___calendar_widget_month);

        title.setOnClickListener(this);
        buttonPast.setOnClickListener(this);
        buttonFuture.setOnClickListener(this);

        monthView.setCallbacks(this);

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

        setColor(getAccentColor());
    }

    private boolean canGoForward() {
        if(maxDate == null) {
            return true;
        }

        Calendar maxCal = maxDate.getCalendar();
        maxCal.add(MONTH, -1);
        return maxCal.compareTo(calendar) >= 0;
    }

    private boolean canGoBack() {
        if(minDate == null) {
            return true;
        }

        Calendar minCal = minDate.getCalendar();
        return minCal.compareTo(calendar) < 0;
    }

    private int getAccentColor() {
        int colorAttr = 0;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            colorAttr = android.R.attr.colorAccent;
        }
        else {
            colorAttr = getResources().getIdentifier("colorAccent", "attr", getContext().getPackageName());
        }
        TypedValue outValue = new TypedValue();
        getContext().getTheme().resolveAttribute(colorAttr, outValue, true);
        return outValue.data;
    }

    public void setColor(int color) {
        buttonPast.setColor(color);
        buttonFuture.setColor(color);
        monthView.setColor(color);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.___calendar_widget_button_forward) {
            calendar.add(MONTH, 1);
            updateUi();
        }
        else if(v.getId() == R.id.___calendar_widget_button_backwards) {
            calendar.add(MONTH, -1);
            updateUi();
        }
        else if(v.getId() == R.id.___calendar_widget_title) {
            //TODO show year selector
        }
    }

    public void setOnDateChangedListener(OnDateChangedListener listener) {
        this.listener = listener;
    }

    public CalendarDay getSelectedDate() {
        return new CalendarDay(selectedDate);
    }

    public void setSelectedDate(Calendar calendar) {
        setSelectedDate(new CalendarDay(calendar));
    }

    public void setSelectedDate(Date date) {
        setSelectedDate(new CalendarDay(date));
    }

    public void setSelectedDate(CalendarDay day) {
        day.copyTo(selectedDate);
        day.copyTo(calendar);
        CalendarUtils.setToFirstDay(calendar);
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

    @Override
    public void onDateChanged(CalendarDay date) {
        setSelectedDate(date);

        if(listener != null) {
            listener.onDateChanged(this, date);
        }
    }
}
