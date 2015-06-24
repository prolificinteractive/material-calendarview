package com.prolificinteractive.materialcalendarview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ArrayRes;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.DateFormatTitleFormatter;
import com.prolificinteractive.materialcalendarview.format.DayFormatter;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;
import com.prolificinteractive.materialcalendarview.format.WeekDayFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
 * {@linkplain com.prolificinteractive.materialcalendarview.OnDateChangedListener}
 * </p>
 */
public class MaterialCalendarView extends FrameLayout {

    /**
     *
     */
    public static final int DEFAULT_TILE_SIZE_DP = 44;

    private static final TitleFormatter DEFAULT_TITLE_FORMATTER = new DateFormatTitleFormatter();
    private final TitleChanger titleChanger;

    private final TextView title;
    private final DirectionButton buttonPast;
    private final DirectionButton buttonFuture;
    private final ViewPager pager;
    private final MonthPagerAdapter adapter;
    private CalendarDay currentMonth;
    private LinearLayout topbar;

    private final ArrayList<DayViewDecorator> dayViewDecorators = new ArrayList<>();

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
            if(v == buttonFuture) {
                pager.setCurrentItem(pager.getCurrentItem() + 1, true);
            } else if(v == buttonPast) {
                pager.setCurrentItem(pager.getCurrentItem() - 1, true);
            }
        }
    };

    private final ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            titleChanger.setPreviousMonth(currentMonth);
            currentMonth = adapter.getItem(position);
            updateUi();

            if(monthListener != null) {
                monthListener.onMonthChanged(MaterialCalendarView.this, currentMonth);
            }
        }

        @Override public void onPageScrollStateChanged(int state) {}

        @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
    };

    private CalendarDay minDate = null;
    private CalendarDay maxDate = null;

    private OnDateChangedListener listener;
    private OnMonthChangedListener monthListener;

    private int accentColor = 0;
    private int arrowColor = Color.BLACK;
    private Drawable leftArrowMask;
    private Drawable rightArrowMask;

    private LinearLayout root;

    public MaterialCalendarView(Context context) {
        this(context, null);
    }

    public MaterialCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setClipChildren(false);
        setClipToPadding(false);

        buttonPast = new DirectionButton(getContext());
        title = new TextView(getContext());
        buttonFuture = new DirectionButton(getContext());
        pager = new ViewPager(getContext());

        setupChildren();

        title.setOnClickListener(onClickListener);
        buttonPast.setOnClickListener(onClickListener);
        buttonFuture.setOnClickListener(onClickListener);

        titleChanger = new TitleChanger(title);
        titleChanger.setTitleFormatter(DEFAULT_TITLE_FORMATTER);
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

            int tileSize = a.getDimensionPixelSize(R.styleable.MaterialCalendarView_mcv_tileSize, -1);
            if(tileSize > 0) {
                setTileSize(tileSize);
            }

            setArrowColor(a.getColor(
                R.styleable.MaterialCalendarView_mcv_arrowColor,
                Color.BLACK
            ));
            Drawable leftMask = a.getDrawable(
                R.styleable.MaterialCalendarView_mcv_leftArrowMask
            );
            if (leftMask == null) {
                leftMask = getResources().getDrawable(R.drawable.mcv_action_previous);
            }
            setLeftArrowMask(leftMask);
            Drawable rightMask = a.getDrawable(
                R.styleable.MaterialCalendarView_mcv_rightArrowMask
            );
            if (rightMask == null) {
                rightMask = getResources().getDrawable(R.drawable.mcv_action_next);
            }
            setRightArrowMask(rightMask);

            setSelectionColor(
                a.getColor(
                    R.styleable.MaterialCalendarView_mcv_selectionColor,
                    getThemeAccentColor(context)
                )
            );

            CharSequence[] array = a.getTextArray(R.styleable.MaterialCalendarView_mcv_weekDayLabels);
            if(array != null) {
                setWeekDayFormatter(new ArrayWeekDayFormatter(array));
            }

            array = a.getTextArray(R.styleable.MaterialCalendarView_mcv_monthLabels);
            if(array != null) {
                setTitleFormatter(new MonthArrayTitleFormatter(array));
            }

            setHeaderTextAppearance(a.getResourceId(
                    R.styleable.MaterialCalendarView_mcv_headerTextAppearance,
                    R.style.TextAppearance_MaterialCalendarWidget_Header
            ));
            setWeekDayTextAppearance(a.getResourceId(
                    R.styleable.MaterialCalendarView_mcv_weekDayTextAppearance,
                    R.style.TextAppearance_MaterialCalendarWidget_WeekDay
            ));
            setDateTextAppearance(a.getResourceId(
                    R.styleable.MaterialCalendarView_mcv_dateTextAppearance,
                    R.style.TextAppearance_MaterialCalendarWidget_Date
            ));
            setShowOtherDates(a.getBoolean(
                    R.styleable.MaterialCalendarView_mcv_showOtherDates,
                    false
            ));
            setFirstDayOfWeek(a.getInt(
                    R.styleable.MaterialCalendarView_mcv_firstDayOfWeek,
                    Calendar.SUNDAY
            ));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            a.recycle();
        }

        currentMonth = CalendarDay.today();
        setCurrentDate(currentMonth);
    }

    private void setupChildren() {
        int tileSize = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                DEFAULT_TILE_SIZE_DP,
                getResources().getDisplayMetrics()
        );

        root = new LinearLayout(getContext());
        root.setOrientation(LinearLayout.VERTICAL);
        root.setClipChildren(false);
        root.setClipToPadding(false);
        LayoutParams p = new LayoutParams(
                tileSize * MonthView.DEFAULT_DAYS_IN_WEEK,
                tileSize * (MonthView.DEFAULT_MONTH_TILE_HEIGHT + 1)
        );
        p.gravity = Gravity.CENTER;
        addView(root, p);

        topbar = new LinearLayout(getContext());
        topbar.setOrientation(LinearLayout.HORIZONTAL);
        topbar.setClipChildren(false);
        topbar.setClipToPadding(false);
        root.addView(topbar, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1));

        buttonPast.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        buttonPast.setImageResource(R.drawable.mcv_action_previous);
        topbar.addView(buttonPast, new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1));

        title.setGravity(Gravity.CENTER);
        topbar.addView(title, new LinearLayout.LayoutParams(
                0, LayoutParams.MATCH_PARENT, MonthView.DEFAULT_DAYS_IN_WEEK - 2
        ));

        buttonFuture.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        buttonFuture.setImageResource(R.drawable.mcv_action_next);
        topbar.addView(buttonFuture, new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1));

        pager.setId(R.id.mcv_pager);
        pager.setOffscreenPageLimit(1);
        root.addView(pager, new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, 0, MonthView.DEFAULT_MONTH_TILE_HEIGHT
        ));
    }

    /**
     * Sets the listener to be notified upon selected date changes.
     *
     * @param listener thing to be notified
     */
    public void setOnDateChangedListener(OnDateChangedListener listener) {
        this.listener = listener;
    }

    /**
     * Sets the listener to be notified upon month changes.
     *
     * @param listener thing to be notified
     */
    public void setOnMonthChangedListener(OnMonthChangedListener listener) {
        this.monthListener = listener;
    }

    private void updateUi() {
        titleChanger.change(currentMonth);
        buttonPast.setEnabled(canGoBack());
        buttonFuture.setEnabled(canGoForward());
    }

    /**
     * Set the size of each tile that makes up the calendar.
     * Each day is 1 tile, so the widget is 7 tiles wide and 8 tiles tall.
     *
     * @param size the new size for each tile in pixels
     */
    public void setTileSize(int size) {
        LayoutParams p = new LayoutParams(
                size * MonthView.DEFAULT_DAYS_IN_WEEK,
                size * (MonthView.DEFAULT_MONTH_TILE_HEIGHT + 1)
        );
        p.gravity = Gravity.CENTER;
        root.setLayoutParams(p);
    }

    /**
     * @see #setTileSize(int)
     *
     * @param tileSizeDp the new size for each tile in dips
     */
    public void setTileSizeDp(int tileSizeDp) {
        setTileSize((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, tileSizeDp, getResources().getDisplayMetrics()
        ));
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
     * @return icon used for the left arrow
     */
    public Drawable getLeftArrowMask() {
        return leftArrowMask;
    }

    /**
     * @param icon the new icon to use for the left paging arrow
     */
    public void setLeftArrowMask(Drawable icon) {
        leftArrowMask = icon;
        buttonPast.setImageDrawable(icon);
    }

    /**
     * @return icon used for the right arrow
     */
    public Drawable getRightArrowMask() {
        return rightArrowMask;
    }

    /**
     * @param icon the new icon to use for the right paging arrow
     */
    public void setRightArrowMask(Drawable icon) {
        rightArrowMask = icon;
        buttonFuture.setImageDrawable(icon);
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
     * Clear the current selection
     */
    public void clearSelection() {
        setSelectedDate((CalendarDay) null);
    }

    /**
     * @param calendar a Calendar set to a day to select. Null to clear selection
     */
    public void setSelectedDate(@Nullable Calendar calendar) {
        setSelectedDate(CalendarDay.from(calendar));
    }

    /**
     * @param date a Date to set as selected. Null to clear selection
     */
    public void setSelectedDate(@Nullable Date date) {
        setSelectedDate(CalendarDay.from(date));
    }

    /**
     * @param day a CalendarDay to set as selected. Null to clear selection
     */
    public void setSelectedDate(@Nullable CalendarDay day) {
        adapter.setSelectedDate(day);
        setCurrentDate(day);
    }

    /**
     * @param calendar a Calendar set to a day to focus the calendar on. Null will do nothing
     */
    public void setCurrentDate(@Nullable Calendar calendar) {
        setCurrentDate(CalendarDay.from(calendar));
    }

    /**
     * @param date a Date to focus the calendar on. Null will do nothing
     */
    public void setCurrentDate(@Nullable Date date) {
        setCurrentDate(CalendarDay.from(date));
    }

    /**
     * @return The current day shown, will be set to first day of the month
     */
    public CalendarDay getCurrentDate() {
        return adapter.getItem(pager.getCurrentItem());
    }

    /**
     * @param day a CalendarDay to focus the calendar on. Null will do nothing
     */
    public void setCurrentDate(@Nullable CalendarDay day) {
        if(day == null) {
            return;
        }
        int index = adapter.getIndexForDay(day);
        pager.setCurrentItem(index);
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
    public void setMinimumDate(@Nullable Calendar calendar) {
        setMinimumDate(CalendarDay.from(calendar));
    }

    /**
     * @param date set the minimum selectable date, null for no minimum
     */
    public void setMinimumDate(@Nullable Date date) {
        setMinimumDate(CalendarDay.from(date));
    }

    /**
     * @param calendar set the minimum selectable date, null for no minimum
     */
    public void setMinimumDate(@Nullable CalendarDay calendar) {
        minDate = calendar;
        setRangeDates(minDate, maxDate);
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
    public void setMaximumDate(@Nullable Calendar calendar) {
        setMaximumDate(CalendarDay.from(calendar));
    }

    /**
     * @param date set the maximum selectable date, null for no maximum
     */
    public void setMaximumDate(@Nullable Date date) {
        setMaximumDate(CalendarDay.from(date));
    }

    /**
     * @param calendar set the maximum selectable date, null for no maximum
     */
    public void setMaximumDate(@Nullable CalendarDay calendar) {
        maxDate = calendar;
        setRangeDates(minDate, maxDate);
    }

    /**
     *
     * By default, only days of one month are shown. If this is set true,
     * then days from the previous and next months are used to fill the empty space.
     * This also controls showing dates outside of the min-max range.
     *
     * @param showOtherDates show other days, default is false
     */
    public void setShowOtherDates(boolean showOtherDates) {
        adapter.setShowOtherDates(showOtherDates);
    }

    /**
     * Set a formatter for weekday labels.
     *
     * @param formatter the new formatter, null for default
     */
    public void setWeekDayFormatter(WeekDayFormatter formatter) {
        adapter.setWeekDayFormatter(formatter == null ? WeekDayFormatter.DEFAULT : formatter);
    }

    /**
     * Set a formatter for day labels.
     *
     * @param formatter the new formatter, null for default
     */
    public void setDayFormatter(DayFormatter formatter) {
        adapter.setDayFormatter(formatter == null ? DayFormatter.DEFAULT : formatter);
    }

    /**
     * Set a {@linkplain com.prolificinteractive.materialcalendarview.format.WeekDayFormatter}
     * with the provided week day labels
     *
     * @see com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter
     * @see #setWeekDayFormatter(com.prolificinteractive.materialcalendarview.format.WeekDayFormatter)
     *
     * @param weekDayLabels Labels to use for the days of the week
     */
    public void setWeekDayLabels(CharSequence[] weekDayLabels) {
        setWeekDayFormatter(new ArrayWeekDayFormatter(weekDayLabels));
    }

    /**
     * Set a {@linkplain com.prolificinteractive.materialcalendarview.format.WeekDayFormatter}
     * with the provided week day labels
     *
     * @see com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter
     * @see #setWeekDayFormatter(com.prolificinteractive.materialcalendarview.format.WeekDayFormatter)
     *
     * @param arrayRes String array resource of week day labels
     */
    public void setWeekDayLabels(@ArrayRes int arrayRes) {
        setWeekDayLabels(getResources().getTextArray(arrayRes));
    }

    /**
     * @return true if days from previous or next months are shown, otherwise false.
     */
    public boolean getShowOtherDates() {
        return adapter.getShowOtherDates();
    }

    /**
     * Set a custom formatter for the month/year title
     * @param titleFormatter new formatter to use, null to use default formatter
     */
    public void setTitleFormatter(TitleFormatter titleFormatter) {
        titleChanger.setTitleFormatter(titleFormatter == null ? DEFAULT_TITLE_FORMATTER : titleFormatter);
        updateUi();
    }

    /**
     * Set a {@linkplain com.prolificinteractive.materialcalendarview.format.TitleFormatter}
     * using the provided month labels
     *
     * @see com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter
     * @see #setTitleFormatter(com.prolificinteractive.materialcalendarview.format.TitleFormatter)
     *
     * @param monthLabels month labels to use
     */
    public void setTitleMonths(CharSequence[] monthLabels) {
        setTitleFormatter(new MonthArrayTitleFormatter(monthLabels));
    }

    /**
     * Set a {@linkplain com.prolificinteractive.materialcalendarview.format.TitleFormatter}
     * using the provided month labels
     *
     * @see com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter
     * @see #setTitleFormatter(com.prolificinteractive.materialcalendarview.format.TitleFormatter)
     *
     * @param arrayRes String array resource of month labels to use
     */
    public void setTitleMonths(@ArrayRes int arrayRes) {
        setTitleMonths(getResources().getTextArray(arrayRes));
    }

    /**
     * Sets the visibility {@link #topbar}, which contains
     * the previous month button {@link #buttonPast}, next month button {@link #buttonFuture},
     * and the month title {@link #title}.
     *
     * @param visible Boolean indicating if the topbar is visible
     */
    public void setTopbarVisible(boolean visible) {
        if (visible)
            topbar.setVisibility(View.VISIBLE);
        else
            topbar.setVisibility(View.GONE);
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
        setShowOtherDates(ss.showOtherDates);
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

    private void setRangeDates(CalendarDay min, CalendarDay max) {
        CalendarDay c = currentMonth;
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
        CalendarDay minDate = null;
        CalendarDay maxDate = null;
        CalendarDay selectedDate = null;

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
            out.writeParcelable(minDate, 0);
            out.writeParcelable(maxDate, 0);
            out.writeParcelable(selectedDate, 0);
        }

        public static final Parcelable.Creator<SavedState> CREATOR
            = new Parcelable.Creator<SavedState>() {
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
            ClassLoader loader = CalendarDay.class.getClassLoader();
            minDate = in.readParcelable(loader);
            maxDate = in.readParcelable(loader);
            selectedDate = in.readParcelable(loader);
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

    /**
     * Sets the first day of the week.
     *
     * Uses the java.util.Calendar day constants.
     * @see java.util.Calendar
     *
     * @param day The first day of the week as a java.util.Calendar day constant.
     */
    public void setFirstDayOfWeek(int day) {
        adapter.setFirstDayOfWeek(day);
    }

    /**
     *
     * @return The first day of the week as a java.util.Calendar day constant.
     */
    public int getFirstDayOfWeek() {
        return adapter.getFirstDayOfWeek();
    }

    public void addDecorators(Collection<? extends DayViewDecorator> decorators) {
        if(decorators == null) {
            return;
        }

        dayViewDecorators.addAll(decorators);
        adapter.setDecorators(dayViewDecorators);
    }

    public void addDecorators(DayViewDecorator... decorators) {
        addDecorators(Arrays.asList(decorators));
    }

    public void addDecorator(DayViewDecorator decorator) {
        if(decorator == null) {
            return;
        }
        dayViewDecorators.add(decorator);
        adapter.setDecorators(dayViewDecorators);
    }

    public void removeDecorators() {
        dayViewDecorators.clear();
        adapter.setDecorators(dayViewDecorators);
    }

    public void removeDecorator(DayViewDecorator decorator) {
        dayViewDecorators.remove(decorator);
        adapter.setDecorators(dayViewDecorators);
    }

    public void invalidateDecorators() {
        adapter.invalidateDecorators();
    }

    private static class MonthPagerAdapter extends PagerAdapter {

        private final MaterialCalendarView view;
        private final LinkedList<MonthView> currentViews;
        private final ArrayList<CalendarDay> months;

        private MonthView.Callbacks callbacks = null;
        private Integer color = null;
        private Integer dateTextAppearance = null;
        private Integer weekDayTextAppearance = null;
        private Boolean showOtherDates = null;
        private CalendarDay minDate = null;
        private CalendarDay maxDate = null;
        private CalendarDay selectedDate = null;
        private WeekDayFormatter weekDayFormatter = WeekDayFormatter.DEFAULT;
        private DayFormatter dayFormatter = DayFormatter.DEFAULT;
        private List<DayViewDecorator> decorators = null;
        private List<DecoratorResult> decoratorResults = null;
        private int firstDayOfTheWeek = Calendar.SUNDAY;


        private MonthPagerAdapter(MaterialCalendarView view) {
            this.view = view;
            currentViews = new LinkedList<>();
            months = new ArrayList<>();
            setRangeDates(null, null);
        }


        public void setDecorators(List<DayViewDecorator> decorators){
            this.decorators = decorators;
            invalidateDecorators();
        }

        public void invalidateDecorators() {
            decoratorResults = new ArrayList<>();
            for(DayViewDecorator decorator : decorators) {
                DayViewFacade facade = new DayViewFacade();
                decorator.decorate(facade);
                if(facade.isDecorated()) {
                    decoratorResults.add(new DecoratorResult(decorator, facade));
                }
            }
            for(MonthView monthView : currentViews) {
                monthView.setDayViewDecorators(decoratorResults);
            }
        }

        @Override
        public int getCount() {
            return months.size();
        }

        public int getIndexForDay(CalendarDay day) {
            if(day == null) {
                return getCount() / 2;
            }
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
            CalendarDay month = monthView.getMonth();
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
            MonthView monthView = new MonthView(container.getContext(), month, firstDayOfTheWeek);

            monthView.setWeekDayFormatter(weekDayFormatter);
            monthView.setDayFormatter(dayFormatter);
            monthView.setCallbacks(callbacks);
            if(color != null) {
                monthView.setSelectionColor(color);
            }
            if(dateTextAppearance != null) {
                monthView.setDateTextAppearance(dateTextAppearance);
            }
            if(weekDayTextAppearance != null) {
                monthView.setWeekDayTextAppearance(weekDayTextAppearance);
            }
            if(showOtherDates != null) {
                monthView.setShowOtherDates(showOtherDates);
            }
            monthView.setMinimumDate(minDate);
            monthView.setMaximumDate(maxDate);
            monthView.setSelectedDate(selectedDate);

            container.addView(monthView);
            currentViews.add(monthView);

            monthView.setDayViewDecorators(decoratorResults);

            return monthView;
        }

        public void setFirstDayOfWeek(int day) {
            firstDayOfTheWeek = day;
            for(MonthView monthView : currentViews) {
                monthView.setFirstDayOfWeek(firstDayOfTheWeek);
            }
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

        public void setWeekDayFormatter(WeekDayFormatter formatter) {
            this.weekDayFormatter = formatter;
            for(MonthView monthView : currentViews) {
                monthView.setWeekDayFormatter(formatter);
            }
        }

        public void setDayFormatter(DayFormatter formatter) {
            this.dayFormatter = formatter;
            for(MonthView monthView : currentViews) {
                monthView.setDayFormatter(formatter);
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

        public void setRangeDates(CalendarDay min, CalendarDay max) {
            this.minDate = min;
            this.maxDate = max;
            for(MonthView monthView : currentViews) {
                monthView.setMinimumDate(min);
                monthView.setMaximumDate(max);
            }

            if(min == null) {
                Calendar worker = CalendarUtils.getInstance();
                worker.add(Calendar.YEAR, -200);
                min = CalendarDay.from(worker);
            }

            if(max == null) {
                Calendar worker = CalendarUtils.getInstance();
                worker.add(Calendar.YEAR, 200);
                max = CalendarDay.from(worker);
            }

            Calendar worker = CalendarUtils.getInstance();
            min.copyTo(worker);
            CalendarUtils.setToFirstDay(worker);
            months.clear();
            CalendarDay workingMonth = CalendarDay.from(worker);
            while (!max.isBefore(workingMonth)) {
                months.add(CalendarDay.from(worker));
                worker.add(Calendar.MONTH, 1);
                workingMonth = CalendarDay.from(worker);
            }
            CalendarDay prevDate = selectedDate;
            notifyDataSetChanged();
            setSelectedDate(prevDate);
            if(prevDate != null) {
                if(!prevDate.equals(selectedDate)) {
                    callbacks.onDateChanged(selectedDate);
                }
            }
        }

        public void setSelectedDate(@Nullable CalendarDay date) {
            CalendarDay prevDate = selectedDate;
            this.selectedDate = getValidSelectedDate(date);
            for(MonthView monthView : currentViews) {
                monthView.setSelectedDate(selectedDate);
            }

            if(date == null && prevDate != null) {
                callbacks.onDateChanged(null);
            }
        }

        private CalendarDay getValidSelectedDate(CalendarDay date) {
            if(date == null) {
                return null;
            }
            if(minDate != null && minDate.isAfter(date)) {
                return minDate;
            }
            if(maxDate != null && maxDate.isBefore(date)) {
                return maxDate;
            }
            return date;
        }

        public CalendarDay getItem(int position) {
            return months.get(position);
        }

        public CalendarDay getSelectedDate() {
            return selectedDate;
        }

        protected int getDateTextAppearance() {
            return dateTextAppearance == null ? 0 : dateTextAppearance;
        }

        protected int getWeekDayTextAppearance() {
            return weekDayTextAppearance == null ? 0 : weekDayTextAppearance;
        }

        public int getFirstDayOfWeek() {
            return firstDayOfTheWeek;
        }
    }
}
