package com.prolificinteractive.materialcalendarview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ArrayRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.DateFormatTitleFormatter;
import com.prolificinteractive.materialcalendarview.format.DayFormatter;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;
import com.prolificinteractive.materialcalendarview.format.WeekDayFormatter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
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
 * {@linkplain OnDateSelectedListener}
 * </p>
 * <p>
 * <strong>Note:</strong> if this view's size isn't divisible by 7,
 * the contents will be centered inside such that the days in the calendar are equally square.
 * For example, 600px isn't divisible by 7, so a tile size of 85 is choosen, making the calendar
 * 595px wide. The extra 5px are distributed left and right to get to 600px.
 * </p>
 */
public class MaterialCalendarView extends ViewGroup {

    /**
     * {@linkplain IntDef} annotation for selection mode.
     *
     * @see #setSelectionMode(int)
     * @see #getSelectionMode()
     */
    @Retention(RetentionPolicy.RUNTIME)
    @IntDef({SELECTION_MODE_NONE, SELECTION_MODE_SINGLE, SELECTION_MODE_MULTIPLE})
    public @interface SelectionMode {
    }

    /**
     * Selection mode that disallows all selection.
     * When changing to this mode, current selection will be cleared.
     */
    public static final int SELECTION_MODE_NONE = 0;

    /**
     * Selection mode that allows one selected date at one time. This is the default mode.
     * When switching from {@linkplain #SELECTION_MODE_MULTIPLE}, this will select the same date
     * as from {@linkplain #getSelectedDate()}, which should be the last selected date
     */
    public static final int SELECTION_MODE_SINGLE = 1;

    /**
     * Selection mode which allows more than one selected date at one time.
     */
    public static final int SELECTION_MODE_MULTIPLE = 2;

    /**
     * {@linkplain IntDef} annotation for showOtherDates.
     *
     * @see #setShowOtherDates(int)
     * @see #getShowOtherDates()
     */
    @SuppressLint("UniqueConstants")
    @Retention(RetentionPolicy.RUNTIME)
    @IntDef(flag = true, value = {
            SHOW_NONE, SHOW_ALL, SHOW_DEFAULTS,
            SHOW_OUT_OF_RANGE, SHOW_OTHER_MONTHS, SHOW_DECORATED_DISABLED
    })
    public @interface ShowOtherDates {
    }

    /**
     * Do not show any non-enabled dates
     */
    public static final int SHOW_NONE = 0;

    /**
     * Show dates from the proceeding and successive months, in a disabled state.
     * This flag also enables the {@link #SHOW_OUT_OF_RANGE} flag to prevent odd blank areas.
     */
    public static final int SHOW_OTHER_MONTHS = 1;

    /**
     * Show dates that are outside of the min-max range.
     * This will only show days from the current month unless {@link #SHOW_OTHER_MONTHS} is enabled.
     */
    public static final int SHOW_OUT_OF_RANGE = 2;

    /**
     * Show days that are individually disabled with decorators.
     * This will only show dates in the current month and inside the minimum and maximum date range.
     */
    public static final int SHOW_DECORATED_DISABLED = 4;

    /**
     * The default flags for showing non-enabled dates. Currently only shows {@link #SHOW_DECORATED_DISABLED}
     */
    public static final int SHOW_DEFAULTS = SHOW_DECORATED_DISABLED;

    /**
     * Show all the days
     */
    public static final int SHOW_ALL = SHOW_OTHER_MONTHS | SHOW_OUT_OF_RANGE | SHOW_DECORATED_DISABLED;

    /**
     * Default tile size in DIPs. This is used in cases where there is no tile size specificed and the view is set to {@linkplain ViewGroup.LayoutParams#WRAP_CONTENT WRAP_CONTENT}
     */
    public static final int DEFAULT_TILE_SIZE_DP = 44;
    private static final int DEFAULT_DAYS_IN_WEEK = 7;
    private static final int DAY_NAMES_ROW = 1;

    private static final TitleFormatter DEFAULT_TITLE_FORMATTER = new DateFormatTitleFormatter();
    private final TitleChanger titleChanger;

    private final TextView title;
    private final DirectionButton buttonPast;
    private final DirectionButton buttonFuture;
    private final CalendarPager pager;
    private CalendarPagerAdapter<?> adapter;
    private CalendarDay currentMonth;
    private LinearLayout topbar;
    private CalendarMode calendarMode = CalendarMode.MONTHS;
    /**
     * Used for the dynamic calendar height.
     */
    private boolean mDynamicHeightEnabled;

    private final ArrayList<DayViewDecorator> dayViewDecorators = new ArrayList<>();

    private final OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == buttonFuture) {
                pager.setCurrentItem(pager.getCurrentItem() + 1, true);
            } else if (v == buttonPast) {
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

            dispatchOnMonthChanged(currentMonth);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }
    };

    private CalendarDay minDate = null;
    private CalendarDay maxDate = null;

    private OnDateSelectedListener listener;
    private OnMonthChangedListener monthListener;

    private int accentColor = 0;
    private int arrowColor = Color.BLACK;
    private Drawable leftArrowMask;
    private Drawable rightArrowMask;
    private int tileSize = -1;
    @SelectionMode
    private int selectionMode = SELECTION_MODE_SINGLE;

    public MaterialCalendarView(Context context) {
        this(context, null);
    }

    public MaterialCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //If we're on good Android versions, turn off clipping for cool effects
            setClipToPadding(false);
            setClipChildren(false);
        } else {
            //Old Android does not like _not_ clipping view pagers, we need to clip
            setClipChildren(true);
            setClipToPadding(true);
        }

        buttonPast = new DirectionButton(getContext());
        title = new TextView(getContext());
        buttonFuture = new DirectionButton(getContext());
        pager = new CalendarPager(getContext());

        setupChildren();

        title.setOnClickListener(onClickListener);
        buttonPast.setOnClickListener(onClickListener);
        buttonFuture.setOnClickListener(onClickListener);

        titleChanger = new TitleChanger(title);
        titleChanger.setTitleFormatter(DEFAULT_TITLE_FORMATTER);
        adapter = new MonthPagerAdapter(this);
        adapter.setTitleFormatter(DEFAULT_TITLE_FORMATTER);
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(pageChangeListener);
        pager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                position = (float) Math.sqrt(1 - Math.abs(position));
                page.setAlpha(position);
            }
        });

        TypedArray a = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.MaterialCalendarView, 0, 0);
        try {

            int tileSize = a.getDimensionPixelSize(R.styleable.MaterialCalendarView_mcv_tileSize, -1);
            if (tileSize > 0) {
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
            if (array != null) {
                setWeekDayFormatter(new ArrayWeekDayFormatter(array));
            }

            array = a.getTextArray(R.styleable.MaterialCalendarView_mcv_monthLabels);
            if (array != null) {
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
            //noinspection ResourceType
            setShowOtherDates(a.getInteger(
                    R.styleable.MaterialCalendarView_mcv_showOtherDates,
                    SHOW_DEFAULTS
            ));

            int firstDayOfWeek = a.getInteger(
                    R.styleable.MaterialCalendarView_mcv_firstDayOfWeek,
                    -1
            );
            if (firstDayOfWeek < 0) {
                //Allowing use of Calendar.getInstance() here as a performance optimization
                firstDayOfWeek = Calendar.getInstance().getFirstDayOfWeek();
            }
            setFirstDayOfWeek(firstDayOfWeek);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            a.recycle();
        }

        currentMonth = CalendarDay.today();
        setCurrentDate(currentMonth);

        if (isInEditMode()) {
            removeView(pager);
            MonthView monthView = new MonthView(this, currentMonth, getFirstDayOfWeek());
            monthView.setSelectionColor(getSelectionColor());
            monthView.setDateTextAppearance(adapter.getDateTextAppearance());
            monthView.setWeekDayTextAppearance(adapter.getWeekDayTextAppearance());
            monthView.setShowOtherDates(getShowOtherDates());
            addView(monthView, new LayoutParams(calendarMode.visibleWeeksCount + DAY_NAMES_ROW));
        }
    }

    private void setupChildren() {

        topbar = new LinearLayout(getContext());
        topbar.setOrientation(LinearLayout.HORIZONTAL);
        topbar.setClipChildren(false);
        topbar.setClipToPadding(false);
        addView(topbar, new LayoutParams(1));

        buttonPast.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        buttonPast.setImageResource(R.drawable.mcv_action_previous);
        topbar.addView(buttonPast, new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1));

        title.setGravity(Gravity.CENTER);
        topbar.addView(title, new LinearLayout.LayoutParams(
                0, LayoutParams.MATCH_PARENT, DEFAULT_DAYS_IN_WEEK - 2
        ));

        buttonFuture.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        buttonFuture.setImageResource(R.drawable.mcv_action_next);
        topbar.addView(buttonFuture, new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1));

        pager.setId(R.id.mcv_pager);
        pager.setOffscreenPageLimit(1);
        addView(pager, new LayoutParams(calendarMode.visibleWeeksCount + DAY_NAMES_ROW));
    }

    private void updateUi() {
        titleChanger.change(currentMonth);
        buttonPast.setEnabled(canGoBack());
        buttonFuture.setEnabled(canGoForward());
    }

    /**
     * Change the selection mode of the calendar. The default mode is {@linkplain #SELECTION_MODE_SINGLE}
     *
     * @param mode the selection mode to change to. This must be one of
     *             {@linkplain #SELECTION_MODE_NONE}, {@linkplain #SELECTION_MODE_SINGLE}, or {@linkplain #SELECTION_MODE_MULTIPLE}.
     *             Unknown values will act as {@linkplain #SELECTION_MODE_SINGLE}
     * @see #getSelectionMode()
     * @see #SELECTION_MODE_NONE
     * @see #SELECTION_MODE_SINGLE
     * @see #SELECTION_MODE_MULTIPLE
     */
    public void setSelectionMode(final @SelectionMode int mode) {
        final @SelectionMode int oldMode = this.selectionMode;
        switch (mode) {
            case SELECTION_MODE_MULTIPLE: {
                this.selectionMode = SELECTION_MODE_MULTIPLE;
            }
            break;
            default:
            case SELECTION_MODE_SINGLE: {
                this.selectionMode = SELECTION_MODE_SINGLE;
                if (oldMode == SELECTION_MODE_MULTIPLE) {
                    //We should only have one selection now, so we should pick one
                    List<CalendarDay> dates = getSelectedDates();
                    if (!dates.isEmpty()) {
                        setSelectedDate(getSelectedDate());
                    }
                }
            }
            break;
            case SELECTION_MODE_NONE: {
                this.selectionMode = SELECTION_MODE_NONE;
                if (oldMode != SELECTION_MODE_NONE) {
                    //No selection! Clear out!
                    clearSelection();
                }
            }
            break;
        }

        adapter.setSelectionEnabled(selectionMode != SELECTION_MODE_NONE);
    }

    /**
     * Set calendar display mode. The default mode is Months.
     * When switching between modes will select todays date, or the selected date,
     * if selection mode is single.
     *
     * @param mode - calendar mode
     */
    public void setCalendarDisplayMode(CalendarMode mode) {
        if (calendarMode.equals(mode)) {
            return;
        }

        CalendarPagerAdapter<?> newAdapter;
        switch (mode) {
            case MONTHS:
                newAdapter = new MonthPagerAdapter(this);
                break;
            case WEEKS:
                newAdapter = new WeekPagerAdapter(this);
                break;
            default:
                throw new IllegalArgumentException("Provided display mode which is not yet implemented");
        }
        adapter = adapter.migrateStateAndReturn(newAdapter);
        pager.setAdapter(adapter);
        calendarMode = mode;
        setCurrentDate(selectionMode == SELECTION_MODE_SINGLE
                ? adapter.getSelectedDates().get(0)
                : CalendarDay.today());
        invalidateDecorators();
        updateUi();
    }

    /**
     * Get the current selection mode. The default mode is {@linkplain #SELECTION_MODE_SINGLE}
     *
     * @return the current selection mode
     * @see #setSelectionMode(int)
     * @see #SELECTION_MODE_NONE
     * @see #SELECTION_MODE_SINGLE
     * @see #SELECTION_MODE_MULTIPLE
     */
    @SelectionMode
    public int getSelectionMode() {
        return selectionMode;
    }

    /**
     * @return the size of tiles in pixels
     */
    public int getTileSize() {
        return tileSize;
    }

    /**
     * Set the size of each tile that makes up the calendar.
     * Each day is 1 tile, so the widget is 7 tiles wide and 7 or 8 tiles tall
     * depending on the visibility of the {@link #topbar}.
     *
     * @param size the new size for each tile in pixels
     */
    public void setTileSize(int size) {
        this.tileSize = size;
        requestLayout();
    }

    /**
     * @param tileSizeDp the new size for each tile in dips
     * @see #setTileSize(int)
     */
    public void setTileSizeDp(int tileSizeDp) {
        setTileSize(dpToPx(tileSizeDp));
    }

    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics()
        );
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
     * Pass all touch events to the pager so scrolling works on the edges of the calendar view.
     * 
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return pager.dispatchTouchEvent(event);
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
        if (color == 0) {
            if (!isInEditMode()) {
                return;
            } else {
                color = Color.GRAY;
            }
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
        if (color == 0) {
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
     * @return the selected day, or null if no selection. If in multiple selection mode, this
     * will return the last selected date
     */
    public CalendarDay getSelectedDate() {
        List<CalendarDay> dates = adapter.getSelectedDates();
        if (dates.isEmpty()) {
            return null;
        } else {
            return dates.get(dates.size() - 1);
        }
    }

    /**
     * @return all of the currently selected dates
     */
    @NonNull
    public List<CalendarDay> getSelectedDates() {
        return adapter.getSelectedDates();
    }

    /**
     * Clear the currently selected date(s)
     */
    public void clearSelection() {
        List<CalendarDay> dates = getSelectedDates();
        adapter.clearSelections();
        for (CalendarDay day : dates) {
            dispatchOnDateSelected(day, false);
        }
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
     * @param date a Date to set as selected. Null to clear selection
     */
    public void setSelectedDate(@Nullable CalendarDay date) {
        clearSelection();
        if (date != null) {
            setDateSelected(date, true);
        }
    }

    /**
     * @param calendar a Calendar to change. Passing null does nothing
     * @param selected true if day should be selected, false to deselect
     */
    public void setDateSelected(@Nullable Calendar calendar, boolean selected) {
        setDateSelected(CalendarDay.from(calendar), selected);
    }

    /**
     * @param date     a Date to change. Passing null does nothing
     * @param selected true if day should be selected, false to deselect
     */
    public void setDateSelected(@Nullable Date date, boolean selected) {
        setDateSelected(CalendarDay.from(date), selected);
    }

    /**
     * @param day      a CalendarDay to change. Passing null does nothing
     * @param selected true if day should be selected, false to deselect
     */
    public void setDateSelected(@Nullable CalendarDay day, boolean selected) {
        if (day == null) {
            return;
        }
        adapter.setDateSelected(day, selected);
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
     * @return The current month shown, will be set to first day of the month
     */
    public CalendarDay getCurrentDate() {
        return adapter.getItem(pager.getCurrentItem());
    }

    /**
     * @param day a CalendarDay to focus the calendar on. Null will do nothing
     */
    public void setCurrentDate(@Nullable CalendarDay day) {
        setCurrentDate(day, true);
    }

    /**
     * @param day             a CalendarDay to focus the calendar on. Null will do nothing
     * @param useSmoothScroll use smooth scroll when changing months.
     */
    public void setCurrentDate(@Nullable CalendarDay day, boolean useSmoothScroll) {
        if (day == null) {
            return;
        }
        int index = adapter.getIndexForDay(day);
        pager.setCurrentItem(index, useSmoothScroll);
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
     * The default value is {@link #SHOW_DEFAULTS}, which currently is just {@link #SHOW_DECORATED_DISABLED}.
     * This means that the default visible days are of the current month, in the min-max range.
     *
     * @param showOtherDates flags for showing non-enabled dates
     * @see #SHOW_ALL
     * @see #SHOW_NONE
     * @see #SHOW_DEFAULTS
     * @see #SHOW_OTHER_MONTHS
     * @see #SHOW_OUT_OF_RANGE
     * @see #SHOW_DECORATED_DISABLED
     */
    public void setShowOtherDates(@ShowOtherDates int showOtherDates) {
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
     * @param weekDayLabels Labels to use for the days of the week
     * @see com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter
     * @see #setWeekDayFormatter(com.prolificinteractive.materialcalendarview.format.WeekDayFormatter)
     */
    public void setWeekDayLabels(CharSequence[] weekDayLabels) {
        setWeekDayFormatter(new ArrayWeekDayFormatter(weekDayLabels));
    }

    /**
     * Set a {@linkplain com.prolificinteractive.materialcalendarview.format.WeekDayFormatter}
     * with the provided week day labels
     *
     * @param arrayRes String array resource of week day labels
     * @see com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter
     * @see #setWeekDayFormatter(com.prolificinteractive.materialcalendarview.format.WeekDayFormatter)
     */
    public void setWeekDayLabels(@ArrayRes int arrayRes) {
        setWeekDayLabels(getResources().getTextArray(arrayRes));
    }

    /**
     * @return int of flags used for showing non-enabled dates
     * @see #SHOW_ALL
     * @see #SHOW_NONE
     * @see #SHOW_DEFAULTS
     * @see #SHOW_OTHER_MONTHS
     * @see #SHOW_OUT_OF_RANGE
     * @see #SHOW_DECORATED_DISABLED
     */
    @ShowOtherDates
    public int getShowOtherDates() {
        return adapter.getShowOtherDates();
    }

    /**
     * Set a custom formatter for the month/year title
     *
     * @param titleFormatter new formatter to use, null to use default formatter
     */
    public void setTitleFormatter(TitleFormatter titleFormatter) {
        if (titleFormatter == null) {
            titleFormatter = DEFAULT_TITLE_FORMATTER;
        }
        titleChanger.setTitleFormatter(titleFormatter);
        adapter.setTitleFormatter(titleFormatter);
        updateUi();
    }

    /**
     * Set a {@linkplain com.prolificinteractive.materialcalendarview.format.TitleFormatter}
     * using the provided month labels
     *
     * @param monthLabels month labels to use
     * @see com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter
     * @see #setTitleFormatter(com.prolificinteractive.materialcalendarview.format.TitleFormatter)
     */
    public void setTitleMonths(CharSequence[] monthLabels) {
        setTitleFormatter(new MonthArrayTitleFormatter(monthLabels));
    }

    /**
     * Set a {@linkplain com.prolificinteractive.materialcalendarview.format.TitleFormatter}
     * using the provided month labels
     *
     * @param arrayRes String array resource of month labels to use
     * @see com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter
     * @see #setTitleFormatter(com.prolificinteractive.materialcalendarview.format.TitleFormatter)
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
        topbar.setVisibility(visible ? View.VISIBLE : View.GONE);
        requestLayout();
    }

    /**
     * @return true if the topbar is visible
     */
    public boolean getTopbarVisible() {
        return topbar.getVisibility() == View.VISIBLE;
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
        ss.selectedDates = getSelectedDates();
        ss.firstDayOfWeek = getFirstDayOfWeek();
        ss.selectionMode = getSelectionMode();
        ss.tileSizePx = getTileSize();
        ss.topbarVisible = getTopbarVisible();
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
        clearSelection();
        for (CalendarDay calendarDay : ss.selectedDates) {
            setDateSelected(calendarDay, true);
        }
        setFirstDayOfWeek(ss.firstDayOfWeek);
        setTileSize(ss.tileSizePx);
        setTopbarVisible(ss.topbarVisible);
        setSelectionMode(ss.selectionMode);
        setDynamicHeightEnabled(ss.dynamicHeightEnabled);
    }

    @Override
    protected void dispatchSaveInstanceState(@NonNull SparseArray<Parcelable> container) {
        dispatchFreezeSelfOnly(container);
    }

    @Override
    protected void dispatchRestoreInstanceState(@NonNull SparseArray<Parcelable> container) {
        dispatchThawSelfOnly(container);
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
        int showOtherDates = SHOW_DEFAULTS;
        CalendarDay minDate = null;
        CalendarDay maxDate = null;
        List<CalendarDay> selectedDates = new ArrayList<>();
        int firstDayOfWeek = Calendar.SUNDAY;
        int tileSizePx = -1;
        boolean topbarVisible = true;
        int selectionMode = SELECTION_MODE_SINGLE;
        boolean dynamicHeightEnabled = false;

        SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(@NonNull Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(color);
            out.writeInt(dateTextAppearance);
            out.writeInt(weekDayTextAppearance);
            out.writeInt(showOtherDates);
            out.writeParcelable(minDate, 0);
            out.writeParcelable(maxDate, 0);
            out.writeTypedList(selectedDates);
            out.writeInt(firstDayOfWeek);
            out.writeInt(tileSizePx);
            out.writeInt(topbarVisible ? 1 : 0);
            out.writeInt(selectionMode);
            out.writeInt(dynamicHeightEnabled ? 1 : 0);
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
            showOtherDates = in.readInt();
            ClassLoader loader = CalendarDay.class.getClassLoader();
            minDate = in.readParcelable(loader);
            maxDate = in.readParcelable(loader);
            in.readTypedList(selectedDates, CalendarDay.CREATOR);
            firstDayOfWeek = in.readInt();
            tileSizePx = in.readInt();
            topbarVisible = in.readInt() == 1;
            selectionMode = in.readInt();
            dynamicHeightEnabled = in.readInt() == 1;
        }
    }

    private static int getThemeAccentColor(Context context) {
        int colorAttr;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
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
     * <p/>
     * Uses the java.util.Calendar day constants.
     *
     * @param day The first day of the week as a java.util.Calendar day constant.
     * @see java.util.Calendar
     */
    public void setFirstDayOfWeek(int day) {
        adapter.setFirstDayOfWeek(day);
    }

    /**
     * @return The first day of the week as a {@linkplain Calendar} day constant.
     */
    public int getFirstDayOfWeek() {
        return adapter.getFirstDayOfWeek();
    }

    /**
     * By default, the calendar will take up all the space needed to show any month (6 rows).
     * By enabling dynamic height, the view will change height dependant on the visible month.
     * <p/>
     * This means months that only need 5 or 4 rows to show the entire month will only take up
     * that many rows, and will grow and shrink as necessary.
     *
     * @param useDynamicHeight true to have the view different heights based on the visible month
     */
    public void setDynamicHeightEnabled(boolean useDynamicHeight) {
        this.mDynamicHeightEnabled = useDynamicHeight;
    }

    /**
     * @return the dynamic height state - true if enabled.
     */
    public boolean isDynamicHeightEnabled() {
        return mDynamicHeightEnabled;
    }


    /**
     * Add a collection of day decorators
     *
     * @param decorators decorators to add
     */
    public void addDecorators(Collection<? extends DayViewDecorator> decorators) {
        if (decorators == null) {
            return;
        }

        dayViewDecorators.addAll(decorators);
        adapter.setDecorators(dayViewDecorators);
    }

    /**
     * Add several day decorators
     *
     * @param decorators decorators to add
     */
    public void addDecorators(DayViewDecorator... decorators) {
        addDecorators(Arrays.asList(decorators));
    }

    /**
     * Add a day decorator
     *
     * @param decorator decorator to add
     */
    public void addDecorator(DayViewDecorator decorator) {
        if (decorator == null) {
            return;
        }
        dayViewDecorators.add(decorator);
        adapter.setDecorators(dayViewDecorators);
    }

    /**
     * Remove all decorators
     */
    public void removeDecorators() {
        dayViewDecorators.clear();
        adapter.setDecorators(dayViewDecorators);
    }

    /**
     * Remove a specific decorator instance. Same rules as {@linkplain List#remove(Object)}
     *
     * @param decorator decorator to remove
     */
    public void removeDecorator(DayViewDecorator decorator) {
        dayViewDecorators.remove(decorator);
        adapter.setDecorators(dayViewDecorators);
    }

    /**
     * Invalidate decorators after one has changed internally. That is, if a decorator mutates, you
     * should call this method to update the widget.
     */
    public void invalidateDecorators() {
        adapter.invalidateDecorators();
    }

    /*
     * Listener/Callback Code
     */

    /**
     * Sets the listener to be notified upon selected date changes.
     *
     * @param listener thing to be notified
     */
    public void setOnDateChangedListener(OnDateSelectedListener listener) {
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

    /**
     * Dispatch date change events to a listener, if set
     *
     * @param day      the day that was selected
     * @param selected true if the day is now currently selected, false otherwise
     */
    protected void dispatchOnDateSelected(final CalendarDay day, final boolean selected) {
        OnDateSelectedListener l = listener;
        if (l != null) {
            l.onDateSelected(MaterialCalendarView.this, day, selected);
        }
    }

    /**
     * Dispatch date change events to a listener, if set
     *
     * @param day first day of the new month
     */
    protected void dispatchOnMonthChanged(final CalendarDay day) {
        OnMonthChangedListener l = monthListener;
        if (l != null) {
            l.onMonthChanged(MaterialCalendarView.this, day);
        }
    }

    /**
     * Call by MonthView to indicate that a day was clicked and we should handle it
     *
     * @param date        date of the day that was clicked
     * @param nowSelected true if the date is now selected, false otherwise
     */
    protected void onDateClicked(@NonNull CalendarDay date, boolean nowSelected) {
        switch (selectionMode) {
            case SELECTION_MODE_MULTIPLE: {
                adapter.setDateSelected(date, nowSelected);
                dispatchOnDateSelected(date, nowSelected);
            }
            break;
            default:
            case SELECTION_MODE_SINGLE: {
                adapter.clearSelections();
                adapter.setDateSelected(date, true);
                dispatchOnDateSelected(date, true);
            }
            break;
        }
    }

    /**
     * Called by the adapter for cases when changes in state result in dates being unselected
     *
     * @param date date that should be de-selected
     */
    protected void onDateUnselected(CalendarDay date) {
        dispatchOnDateSelected(date, false);
    }

    /*
     * Show Other Dates Utils
     */

    /**
     * @param showOtherDates int flag for show other dates
     * @return true if the other months flag is set
     */
    public static boolean showOtherMonths(@ShowOtherDates int showOtherDates) {
        return (showOtherDates & SHOW_OTHER_MONTHS) != 0;
    }

    /**
     * @param showOtherDates int flag for show other dates
     * @return true if the out of range flag is set
     */
    public static boolean showOutOfRange(@ShowOtherDates int showOtherDates) {
        return (showOtherDates & SHOW_OUT_OF_RANGE) != 0;
    }

    /**
     * @param showOtherDates int flag for show other dates
     * @return true if the decorated disabled flag is set
     */
    public static boolean showDecoratedDisabled(@ShowOtherDates int showOtherDates) {
        return (showOtherDates & SHOW_DECORATED_DISABLED) != 0;
    }

    /*
     * Custom ViewGroup Code
     */

    /**
     * {@inheritDoc}
     */
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        final int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int specWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int specHeightSize = MeasureSpec.getSize(heightMeasureSpec);
        final int specHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        //We need to disregard padding for a while. This will be added back later
        final int desiredWidth = specWidthSize - getPaddingLeft() - getPaddingRight();
        final int desiredHeight = specHeightSize - getPaddingTop() - getPaddingBottom();

        final int weekCount = getWeekCountBasedOnMode();

        final int viewTileHeight = getTopbarVisible() ? (weekCount + 1) : weekCount;

        //Calculate independent tile sizes for later
        int desiredTileWidth = desiredWidth / DEFAULT_DAYS_IN_WEEK;
        int desiredTileHeight = desiredHeight / viewTileHeight;

        int measureTileSize = -1;

        if (this.tileSize > 0) {
            //We have a tileSize set, we should use that
            measureTileSize = this.tileSize;
        } else if (specWidthMode == MeasureSpec.EXACTLY) {
            if (specHeightMode == MeasureSpec.EXACTLY) {
                //Pick the larger of the two explicit sizes
                measureTileSize = Math.max(desiredTileWidth, desiredTileHeight);
            } else {
                //Be the width size the user wants
                measureTileSize = desiredTileWidth;
            }
        } else if (specHeightMode == MeasureSpec.EXACTLY) {
            //Be the height size the user wants
            measureTileSize = desiredTileHeight;
        }

        //Uh oh! We need to default to something, quick!
        if (measureTileSize <= 0) {
            measureTileSize = dpToPx(DEFAULT_TILE_SIZE_DP);
        }

        //Calculate our size based off our measured tile size
        int measuredWidth = measureTileSize * DEFAULT_DAYS_IN_WEEK;
        int measuredHeight = measureTileSize * viewTileHeight;

        //Put padding back in from when we took it away
        measuredWidth += getPaddingLeft() + getPaddingRight();
        measuredHeight += getPaddingTop() + getPaddingBottom();

        //Contract fulfilled, setting out measurements
        setMeasuredDimension(
                //We clamp inline because we want to use un-clamped versions on the children
                clampSize(measuredWidth, widthMeasureSpec),
                clampSize(measuredHeight, heightMeasureSpec)
        );

        int count = getChildCount();

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);

            LayoutParams p = (LayoutParams) child.getLayoutParams();

            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
                    DEFAULT_DAYS_IN_WEEK * measureTileSize,
                    MeasureSpec.EXACTLY
            );

            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                    p.height * measureTileSize,
                    MeasureSpec.EXACTLY
            );

            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        }
    }

    private int getWeekCountBasedOnMode() {
        int weekCount = calendarMode.visibleWeeksCount;
        boolean isInMonthsMode = calendarMode.equals(CalendarMode.MONTHS);
        if (isInMonthsMode && mDynamicHeightEnabled && adapter != null && pager != null) {
            Calendar cal = (Calendar) adapter.getItem(pager.getCurrentItem()).getCalendar().clone();
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            //noinspection ResourceType
            cal.setFirstDayOfWeek(getFirstDayOfWeek());
            weekCount = cal.get(Calendar.WEEK_OF_MONTH);
        }
        return weekCount + DAY_NAMES_ROW;
    }

    /**
     * Clamp the size to the measure spec.
     *
     * @param size Size we want to be
     * @param spec Measure spec to clamp against
     * @return the appropriate size to pass to {@linkplain View#setMeasuredDimension(int, int)}
     */
    private static int clampSize(int size, int spec) {
        int specMode = MeasureSpec.getMode(spec);
        int specSize = MeasureSpec.getSize(spec);
        switch (specMode) {
            case MeasureSpec.EXACTLY: {
                return specSize;
            }
            case MeasureSpec.AT_MOST: {
                return Math.min(size, specSize);
            }
            case MeasureSpec.UNSPECIFIED:
            default: {
                return size;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int count = getChildCount();

        final int parentLeft = getPaddingLeft();
        final int parentWidth = right - left - parentLeft - getPaddingRight();

        int childTop = getPaddingTop();

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() == View.GONE) {
                continue;
            }

            final int width = child.getMeasuredWidth();
            final int height = child.getMeasuredHeight();

            int delta = (parentWidth - width) / 2;
            int childLeft = parentLeft + delta;

            child.layout(childLeft, childTop, childLeft + width, childTop + height);

            childTop += height;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(1);
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(1);
    }


    @Override
    public void onInitializeAccessibilityEvent(@NonNull AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(MaterialCalendarView.class.getName());
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(@NonNull AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(MaterialCalendarView.class.getName());
    }

    /**
     * Simple layout params for MaterialCalendarView. The only variation for layout is height.
     */
    protected static class LayoutParams extends MarginLayoutParams {

        /**
         * Create a layout that matches parent width, and is X number of tiles high
         *
         * @param tileHeight view height in number of tiles
         */
        public LayoutParams(int tileHeight) {
            super(MATCH_PARENT, tileHeight);
        }

    }

    /**
     * Enable or disable the ability to swipe between months.
     *
     * @param pagingEnabled pass false to disable paging, true to enable (default)
     */
    public void setPagingEnabled(boolean pagingEnabled) {
        pager.setPagingEnabled(pagingEnabled);
        updateUi();
    }

    /**
     * @return true if swiping months is enabled, false if disabled. Default is true.
     */
    public boolean isPagingEnabled() {
        return pager.isPagingEnabled();
    }
}
