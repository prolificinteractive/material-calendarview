package com.prolificinteractive.materialcalendarview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

/**
 * <p>
 * This class is a calendar widget for displaying and selecting dates.
 * The range of dates supported by this calendar is configurable.
 * A user can select a date by taping on it and can page the calendar to a desired date.
 * </p>
 * <p>
 * By default, the range of dates shown is from 200 years in the past to 200 years in the future.
 * This can be extended or shortened by configuring the minimum and maximum dates.
 * </p>
 * <p>
 * When selecting a date out of range, or when the range changes so the selection becomes outside,
 * The date closest to the previous selection will become selected. This will also trigger the
 * {@linkplain OnDateChangedListener}
 * </p>
 *
 * @see R.styleable#MaterialCalendarView_arrowColor
 * @see R.styleable#MaterialCalendarView_selectionColor
 * @see R.styleable#MaterialCalendarView_headerTextAppearance
 * @see R.styleable#MaterialCalendarView_dateTextAppearance
 * @see R.styleable#MaterialCalendarView_weekDayTextAppearance
 * @see R.styleable#MaterialCalendarView_showOtherDates
 */
public class MaterialCalendarView extends FrameLayout {

    private final TextView title;
    private final DirectionButton buttonPast;
    private final DirectionButton buttonFuture;
    private final ViewPager pager;
    private final MonthPagerAdapter adapter;
    private long currentMonth;
    private DateFormat titleFormat = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());

    private final MonthView.Callbacks monthViewCallbacks = new MonthView.Callbacks() {
        @Override
        public void onDateChanged(long date) {
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
            updateUi();
        }

        @Override public void onPageScrollStateChanged(int state) {}

        @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
    };

    private long minDate;
    private long maxDate;

    private OnDateChangedListener listener;

    private int accentColor = 0;
    private int arrowColor = Color.BLACK;
    private long currentDate;

    public MaterialCalendarView(Context context) {
        this(context, null);
    }

    public MaterialCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);

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

        adapter = new MonthPagerAdapter(this);
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(pageChangeListener);
        pager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                position = (float) Math.sqrt(1 - Math.abs(position));
                page.setAlpha(position);
            }
        });

        adapter.setCallbacks(monthViewCallbacks);

        TypedArray a = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.MaterialCalendarView, 0, 0);
        try {
            setArrowColor(a.getColor(
                R.styleable.MaterialCalendarView_arrowColor,
                Color.BLACK
            ));
            setSelectionColor(
                a.getColor(
                    R.styleable.MaterialCalendarView_selectionColor,
                    getThemeAccentColor(context)
                )
            );

            setHeaderTextAppearance(a.getResourceId(
                R.styleable.MaterialCalendarView_headerTextAppearance,
                R.style.TextAppearance_MaterialCalendarWidget_Header
            ));
            setWeekDayTextAppearance(a.getResourceId(
                R.styleable.MaterialCalendarView_weekDayTextAppearance,
                R.style.TextAppearance_MaterialCalendarWidget_WeekDay
            ));
            setDateTextAppearance(a.getResourceId(
                R.styleable.MaterialCalendarView_dateTextAppearance,
                R.style.TextAppearance_MaterialCalendarWidget_Date
            ));
            setShowOtherDates(a.getBoolean(
                    R.styleable.MaterialCalendarView_showOtherDates,
                    false
            ), false);

        }
        catch (Exception e) {
            Log.e("Attr Error", "error" , e);
        }
        finally {
            a.recycle();
        }

        currentMonth = CalendarHelper.setToFirstDayOfMonth(new Date().getTime());
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

    private void updateUi() {
        if(currentMonth != 0) {
            title.setText(titleFormat.format(currentMonth));
        }
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
        if(color == 0) {
            return;
        }
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
        if(color == 0) {
            return;
        }
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
     * @param dateTextResourceId The text appearance resource id.
     */
    public void setDateTextAppearance(int dateTextResourceId) {
        adapter.setDateTextAppearance(dateTextResourceId);
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
    public long getSelectedDate() {
        return adapter.getSelectedDate();
    }

    /**
     * @param date a Date to set as selected
     */
    public void setSelectedDate(Date date) {
        setSelectedDate(date.getTime());
    }

    /**
     * @param day a day in ms to set as selected
     */
    public void setSelectedDate(long day) {
        adapter.setSelectedDate(day);
        setCurrentDate(day);
    }

    /**
     * @param date a Date to focus the calendar on
     */
    public void setCurrentDate(Date date) {
        setCurrentDate(date.getTime());
    }

    /**
     * @return The current day shown, will be set to first day of the month
     */
    public long getCurrentDate() {
        return currentDate;
    }

    /**
     * @param day a day in ms to focus the calendar on
     */
    public void setCurrentDate(long day) {
        int index = adapter.getIndexForDay(day);
        pager.setCurrentItem(index);
        updateUi();
    }

    /**
     * @return the minimum selectable date for the calendar, if any
     */
    public long getMinimumDate() {
        return minDate;
    }

    /**
     * * @param minDate set the minimum selectable date in ms, 0 for no minimum
     */
    public void setMinimumDate(long minDate) {
        this.minDate = minDate;
        setRangeDates(minDate, maxDate);
    }

    /**
     * * @param minDate set the minimum selectable date, null for no maximum
     */
    public void setMinimumDate(Date minDate) {
        setMinimumDate(minDate == null ? 0 : minDate.getTime());
    }

    /**
     * @return the maximum selectable date for the calendar, if any
     */
    public long getMaximumDate() {
        return maxDate;
    }

    /**
     * @param maxDate set the maximum selectable date in ms, 0 for no maximum
     */
    public void setMaximumDate(long maxDate) {
        this.maxDate = maxDate;
        setRangeDates(minDate, maxDate);
    }

    /**
     * * @param maxDate set the maximum selectable date, null for no maximum
     */
    public void setMaximumDate(Date maxDate) {
        setMaximumDate(maxDate == null ? 0 : maxDate.getTime());
    }

    /**
     *
     * By default, only days of one month are shown. If this is set true,
     * then days from the previous and next months are used to fill the empty space.
     * This also controls showing dates outside of the min-max range.
     *
     * @param showOtherDates show other days, default is false
     * @param forceUpdate whether or not to force an update in the UI
     */
    public void setShowOtherDates(boolean showOtherDates, boolean forceUpdate) {
        adapter.setShowOtherDates(showOtherDates);
    }

    /**
     * @return true if days from previous or next months are shown, otherwise false.
     */
    public boolean getShowOtherDates() {
        return adapter.getShowOtherDates();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(super.onSaveInstanceState());
        ss.color = getSelectionColor();
        ss.dateTextAppearance = adapter.getDateTextAppearance();
        ss.weekDayTextAppearance = adapter.getWeekDayTextAppearance();
        ss.showOtherDates = getShowOtherDates();
        ss.minDate = getMinimumDate();
        ss.maxDate = getMaximumDate();
        ss.selectedDate = getSelectedDate();
        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setSelectionColor(ss.color);
        setDateTextAppearance(ss.dateTextAppearance);
        setWeekDayTextAppearance(ss.weekDayTextAppearance);
        setShowOtherDates(ss.showOtherDates, true);
        setRangeDates(ss.minDate, ss.maxDate);
        setSelectedDate(ss.selectedDate);
    }

    @Override
    protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
        //super.dispatchSaveInstanceState(container);
        super.dispatchFreezeSelfOnly(container);
    }

    @Override
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        //super.dispatchRestoreInstanceState(container);
        super.dispatchThawSelfOnly(container);
    }

    private void setRangeDates(long min, long max) {
        long c = currentMonth;
        adapter.setRangeDates(min, max);
        currentMonth = c;
        int position = adapter.getIndexForDay(c);
        pager.setCurrentItem(position, false);
    }

    public static class SavedState extends BaseSavedState {

        int color = 0;
        int dateTextAppearance = 0;
        int weekDayTextAppearance = 0;
        boolean showOtherDates = false;
        long minDate = 0;
        long  maxDate = 0;
        long selectedDate = 0;

        SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(color);
            out.writeInt(dateTextAppearance);
            out.writeInt(weekDayTextAppearance);
            out.writeInt(showOtherDates ? 1 : 0);
            out.writeLong(minDate);
            out.writeLong(maxDate);
            out.writeLong(selectedDate);
        }

        public static final Creator<SavedState> CREATOR
            = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

        private SavedState(Parcel in) {
            super(in);
            color = in.readInt();
            dateTextAppearance = in.readInt();
            weekDayTextAppearance = in.readInt();
            showOtherDates = in.readInt() == 1;
            minDate = in.readLong();
            maxDate = in.readLong();
            selectedDate = in.readLong();
        }
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
        private final ArrayList<Long> months;

        private MonthView.Callbacks callbacks = null;
        private Integer color = null;
        private Integer dateTextAppearance = null;
        private Integer weekDayTextAppearance = null;
        private Boolean showOtherDates = null;
        private long minDate = 0;
        private long maxDate = 0;
        private long selectedDate = 0;

        private MonthPagerAdapter(MaterialCalendarView view) {
            this.inflater = LayoutInflater.from(view.getContext());
            currentViews = new LinkedList<>();
            months = new ArrayList<>();
            setRangeDates(0, 0);
        }

        @Override
        public int getCount() {
            return months.size();
        }

        public int getIndexForDay(long day) {
            if(day == 0) {
                return getCount() / 2;
            }
            if(minDate != 0 && day < minDate) {
                return 0;
            }
            if(maxDate != 0 && day > maxDate) {
                return getCount() - 1;
            }
            for (int i = 0; i < months.size(); i++) {
                long month = months.get(i);
                if (CalendarHelper.getYear(day) == CalendarHelper.getYear(month) && CalendarHelper.getMonth(day) == CalendarHelper.getMonth(month)) {
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
            long month = (long) monthView.getTag(TAG_ITEM);
            if(month == 0) {
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
            long month = months.get(position);

            MonthView monthView =
                (MonthView) inflater.inflate(R.layout.cw__month_view, container, false);
            monthView.setTag(TAG_ITEM, month);
            monthView.setCallbacks(callbacks);
            monthView.set(minDate, maxDate, month, selectedDate, showOtherDates);

            if(color != null) {
                monthView.setSelectionColor(color);
            }
            if(dateTextAppearance != null) {
                monthView.setDateTextAppearance(dateTextAppearance);
            }
            if(weekDayTextAppearance != null) {
                monthView.setWeekDayTextAppearance(weekDayTextAppearance);
            }
            container.addView(monthView);
            currentViews.add(monthView);

            return monthView;
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

        public void setDateTextAppearance(int taId) {
            if(taId == 0) {
                return;
            }
            this.dateTextAppearance = taId;
            for(MonthView monthView : currentViews) {
                monthView.setDateTextAppearance(taId);
            }
        }

        public void setShowOtherDates(boolean show) {
            this.showOtherDates = show;
            for(MonthView monthView : currentViews) {
                monthView.setShowOtherDates(show);
            }
        }

        public boolean getShowOtherDates() {
            return showOtherDates;
        }

        public void setWeekDayTextAppearance(int taId) {
            if(taId == 0) {
                return;
            }
            this.weekDayTextAppearance = taId;
            for(MonthView monthView : currentViews) {
                monthView.setWeekDayTextAppearance(taId);
            }
        }

        public void setRangeDates(long min, long max) {
            this.minDate = min;
            this.maxDate = max;
            for(MonthView monthView : currentViews) {
                monthView.setRanges(min, max);
            }
            if(min == 0) {
                min = CalendarHelper.add(CalendarHelper.setToFirstDayOfMonth(new Date().getTime()), Calendar.YEAR, -200);
            }

            if(max == 0) {
                max = CalendarHelper.add(CalendarHelper.setToFirstDayOfMonth(new Date().getTime()), Calendar.YEAR, 200);
            }
            months.clear();
            long workingMonth = min;
            while (max > workingMonth) {
                months.add(workingMonth);
                workingMonth = CalendarHelper.add(workingMonth, Calendar.MONTH, 1);
            }
            long prevDate = selectedDate;
            notifyDataSetChanged();
            setSelectedDate(prevDate);
            if(prevDate != 0 && prevDate != selectedDate) {
                callbacks.onDateChanged(selectedDate);
            }
        }

        public void setSelectedDate(long date) {
            this.selectedDate = getValidSelectedDate(date);
            if(selectedDate == 0)
                return;
            for(MonthView monthView : currentViews) {
                monthView.setSelectedDate(selectedDate);
            }
        }


        private long getValidSelectedDate(long date) {
            if(date == 0) {
                return 0;
            }
            if(minDate != 0 && minDate > date) {
                return minDate;
            }
            if(maxDate != 0 && maxDate < date) {
                return maxDate;
            }
            return date;
        }

        public long getItem(int position) {
            return months.get(position);
        }

        public long getSelectedDate() {
            return selectedDate;
        }

        protected int getDateTextAppearance() {
            return dateTextAppearance == null ? 0 : dateTextAppearance;
        }

        protected int getWeekDayTextAppearance() {
            return weekDayTextAppearance == null ? 0 : weekDayTextAppearance;
        }

    }

}
