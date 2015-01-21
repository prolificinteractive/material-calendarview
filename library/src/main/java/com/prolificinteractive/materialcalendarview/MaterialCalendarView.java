package com.prolificinteractive.materialcalendarview;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prolificinteractive.library.calendarwidget.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

/**
 *
 */
public class MaterialCalendarView extends LinearLayout {

    private final TextView title;
    private final DirectionButton buttonPast;
    private final DirectionButton buttonFuture;
    private final ViewPager pager;
    private final MonthPagerAdapter adapter;
    private CalendarDay currentMonth;
    private DateFormat titleFormat = new SimpleDateFormat(
        "MMMM yyyy", Locale.getDefault()
    );

    private final MonthView.Callbacks monthViewCallbacks = new MonthView.Callbacks() {
        @Override
        public void onDateChanged(CalendarDay date) {
            setSelectedDate(date);

            if(listener != null) {
                listener.onDateChanged(MaterialCalendarView.this, date);
            }
        }
    };

    private final OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if(id == R.id.cw__calendar_widget_button_forward) {
                pager.setCurrentItem(pager.getCurrentItem() + 1, true);
            } else if(id == R.id.cw__calendar_widget_button_backwards) {
                pager.setCurrentItem(pager.getCurrentItem() - 1, true);
            }
        }
    };

    private final ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            currentMonth = adapter.getItem(position);
            updateUi(currentMonth);
        }

        @Override public void onPageScrollStateChanged(int state) {}

        @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
    };

    private final DataSetObserver dataObserver = new DataSetObserver() {
        @Override
        public void onInvalidated() {
            super.onInvalidated();
            thing();
        }

        @Override
        public void onChanged() {
            super.onChanged();
            thing();
        }

        private void thing() {
            pager.setCurrentItem(adapter.getIndexForDay(currentMonth));
        }
    };

    private CalendarDay minDate = null;
    private CalendarDay maxDate = null;

    private OnDateChangedListener listener;

    private int accentColor = 0;
    private int arrowColor = Color.BLACK;

    public MaterialCalendarView(Context context) {
        this(context, null);
    }

    public MaterialCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setOrientation(VERTICAL);
        setClipChildren(false);
        setClipToPadding(false);

        LayoutInflater.from(getContext()).inflate(R.layout.cw__calendar_widget, this);

        title = (TextView) findViewById(R.id.cw__calendar_widget_title);
        buttonPast = (DirectionButton) findViewById(R.id.cw__calendar_widget_button_backwards);
        buttonFuture = (DirectionButton) findViewById(R.id.cw__calendar_widget_button_forward);
        pager = (ViewPager) findViewById(R.id.cw__pager);

        title.setOnClickListener(onClickListener);
        buttonPast.setOnClickListener(onClickListener);
        buttonFuture.setOnClickListener(onClickListener);

        adapter = new MonthPagerAdapter(getContext());
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(pageChangeListener);

        adapter.setCallbacks(monthViewCallbacks);
        adapter.registerDataSetObserver(dataObserver);

        TypedArray a =
            context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.MaterialCalendarView, 0, 0);
        try {
            setArrowColor(a.getColor(R.styleable.MaterialCalendarView_arrowColor, Color.BLACK));
            setSelectionColor(
                a.getColor(
                    R.styleable.MaterialCalendarView_selectionColor,
                    getThemeAccentColor(context)
                )
            );

            int taId = a.getResourceId(R.styleable.MaterialCalendarView_headerTextAppearance, -1);
            if (taId != -1) {
                setHeaderTextAppearance(taId);
            }

            taId = a.getResourceId(R.styleable.MaterialCalendarView_weekDayTextAppearance, -1);
            if (taId != -1) {
                setWeekDayTextAppearance(taId);
            }

            taId = a.getResourceId(R.styleable.MaterialCalendarView_dateTextAppearance, -1);
            if (taId != -1) {
                setDateTextAppearance(taId);
            }

            setShowOtherMonths(
                a.getBoolean(R.styleable.MaterialCalendarView_showOtherMonths, false));
        } finally {
            a.recycle();
        }

        currentMonth = new CalendarDay();
        setCurrentDate(currentMonth);
    }

    /**
     * Sets the listener to be notified upon selected date change.
     *
     * @param listener thing to be notified
     */
    public void setOnDateChangedListener(OnDateChangedListener listener) {
        this.listener = listener;
    }

    private void updateUi(CalendarDay day) {
        title.setText(titleFormat.format(day.getDate()));
        buttonPast.setEnabled(canGoBack());
        buttonFuture.setEnabled(canGoForward());
    }

    /**
     * TODO should this be public?
     *
     * @return true if there is a future month that can be shown
     */
    private boolean canGoForward() {
        return pager.getCurrentItem() < (adapter.getCount() - 1);
    }

    /**
     * TODO should this be public?
     *
     * @return true if there is a previous month that can be shown
     */
    private boolean canGoBack() {
        return pager.getCurrentItem() > 0;
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
        adapter.setSelectionColor(color);
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
        adapter.setDateTextAppearance(resourceId);
    }

    /**
     * @param resourceId The text appearance resource id.
     */
    public void setWeekDayTextAppearance(int resourceId) {
        adapter.setWeekDayTextAppearance(resourceId);
    }

    /**
     * @return the currently selected day, or null if no selection
     */
    public CalendarDay getSelectedDate() {
        return adapter.getSelectedDate();
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
        adapter.setSelectedDate(day);
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
        int index = adapter.getIndexForDay(day);
        pager.setCurrentItem(index);
        updateUi(day);
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
        adapter.setRangeDates(minDate, maxDate);
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
        adapter.setRangeDates(minDate, maxDate);
    }

    /**
     * @param showOtherMonths show days from the previous and next months, default is false
     */
    public void setShowOtherMonths(boolean showOtherMonths) {
        adapter.setShowOtherMonths(showOtherMonths);
    }

    /**
     * @return true if days from previous or next months are shown, otherwise false.
     */
    public boolean getShowOtherMonths() {
        return adapter.getShowOtherMonths();
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

    private static class MonthPagerAdapter extends PagerAdapter {

        private static final int TAG_ITEM = R.id.cw__pager;

        private final LayoutInflater inflater;
        private final LinkedList<MonthView> currentViews;
        private final ArrayList<CalendarDay> months;

        private MonthView.Callbacks callbacks = null;
        private Integer color = null;
        private Integer dateTextAppearance = null;
        private Integer weekDayTextAppearance = null;
        private Boolean showOtherMonths = null;
        private CalendarDay minDate = null;
        private CalendarDay maxDate = null;
        private CalendarDay selectedDate = null;

        private MonthPagerAdapter(Context context) {
            this.inflater = LayoutInflater.from(context);
            currentViews = new LinkedList<>();
            months = new ArrayList<>();
            setRangeDates(null, null);
        }

        @Override
        public int getCount() {
            return months.size();
        }

        public int getIndexForDay(CalendarDay day) {
            if(minDate != null && day.isBefore(minDate)) {
                return 0;
            }
            if(maxDate != null && day.isAfter(maxDate)) {
                return getCount() - 1;
            }
            for (int i = 0; i < months.size(); i++) {
                CalendarDay month = months.get(i);
                if (day.getYear() == month.getYear() && day.getMonth() == month.getMonth()) {
                    return i;
                }
            }
            return getCount() / 2;
        }

        @Override
        public int getItemPosition(Object object) {
            if(!(object instanceof MonthView)) {
                return POSITION_NONE;
            }
            MonthView monthView = (MonthView) object;
            CalendarDay month = (CalendarDay) monthView.getTag(TAG_ITEM);
            if(month == null) {
                return POSITION_NONE;
            }
            int index = months.indexOf(month);
            if(index < 0) {
                return POSITION_NONE;
            }
            return index;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            CalendarDay month = months.get(position);
            MonthView monthView =
                (MonthView) inflater.inflate(R.layout.cw__month_view, container, false);
            monthView.setTag(TAG_ITEM, month);

            monthView.setCallbacks(callbacks);
            if(color != null) {
                monthView.setSelectionColor(color);
            }
            if(dateTextAppearance != null) {
                monthView.setDateTextAppearance(dateTextAppearance);
            }
            if(weekDayTextAppearance != null) {
                monthView.setDateTextAppearance(weekDayTextAppearance);
            }
            if(showOtherMonths != null) {
                monthView.setShowOtherMonths(showOtherMonths);
            }
            monthView.setMinimumDate(minDate);
            monthView.setMaximumDate(maxDate);
            monthView.setSelectedDate(selectedDate);

            monthView.setDate(month);

            container.addView(monthView);
            currentViews.add(monthView);
            return monthView;
        }

        @Override
        public void startUpdate(ViewGroup container) {
            super.startUpdate(container);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            MonthView monthView = (MonthView) object;
            currentViews.remove(monthView);
            container.removeView(monthView);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public void setCallbacks(MonthView.Callbacks callbacks) {
            this.callbacks = callbacks;
            for(MonthView monthView : currentViews) {
                monthView.setCallbacks(callbacks);
            }
        }

        public void setSelectionColor(int color) {
            this.color = color;
            for(MonthView monthView : currentViews) {
                monthView.setSelectionColor(color);
            }
        }

        public void setDateTextAppearance(int dateTextAppearance) {
            this.dateTextAppearance = dateTextAppearance;
            for(MonthView monthView : currentViews) {
                monthView.setDateTextAppearance(dateTextAppearance);
            }
        }

        public void setShowOtherMonths(boolean showOtherMonths) {
            this.showOtherMonths = showOtherMonths;
            for(MonthView monthView : currentViews) {
                monthView.setShowOtherMonths(showOtherMonths);
            }
        }

        public boolean getShowOtherMonths() {
            return showOtherMonths;
        }

        public void setWeekDayTextAppearance(int weekDayTextAppearance) {
            this.weekDayTextAppearance = weekDayTextAppearance;
            for(MonthView monthView : currentViews) {
                monthView.setWeekDayTextAppearance(weekDayTextAppearance);
            }
        }

        public void setRangeDates(CalendarDay minDate, CalendarDay maxDate) {
            this.minDate = minDate;
            this.maxDate = maxDate;

            if(minDate == null) {
                CalendarWrapper worker = CalendarWrapper.getInstance();
                worker.add(Calendar.YEAR, -200);
                minDate = new CalendarDay(worker);
            }

            if(maxDate == null) {
                CalendarWrapper worker = CalendarWrapper.getInstance();
                worker.add(Calendar.YEAR, 200);
                maxDate = new CalendarDay(worker);
            }

            CalendarWrapper worker = CalendarWrapper.getInstance();
            minDate.copyTo(worker);
            months.clear();
            CalendarDay workingMonth = new CalendarDay(worker);
            while (!maxDate.isBefore(workingMonth)) {
                months.add(new CalendarDay(worker));
                worker.add(Calendar.MONTH, 1);
                workingMonth = new CalendarDay(worker);
            }
            notifyDataSetChanged();
        }

        public void setSelectedDate(CalendarDay selectedDate) {
            this.selectedDate = selectedDate;
            for(MonthView monthView : currentViews) {
                monthView.setSelectedDate(selectedDate);
            }
        }

        public CalendarDay getItem(int position) {
            return months.get(position);
        }

        public CalendarDay getSelectedDate() {
            return selectedDate;
        }
    }

}
