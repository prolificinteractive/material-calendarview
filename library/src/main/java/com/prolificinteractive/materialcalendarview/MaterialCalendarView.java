package com.prolificinteractive.materialcalendarview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ArrayRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
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
 *
 * @see R.styleable#MaterialCalendarView_mcv_arrowColor
 * @see R.styleable#MaterialCalendarView_mcv_selectionColor
 * @see R.styleable#MaterialCalendarView_mcv_headerTextAppearance
 * @see R.styleable#MaterialCalendarView_mcv_dateTextAppearance
 * @see R.styleable#MaterialCalendarView_mcv_weekDayTextAppearance
 * @see R.styleable#MaterialCalendarView_mcv_showOtherDates
 */
public class MaterialCalendarView extends FrameLayout {

    private static final TitleFormatter DEFAULT_TITLE_FORMATTER = new DateFormatTitleFormatter();

    private final TextView title;
    private final DirectionButton buttonPast;
    private final DirectionButton buttonFuture;
    private final ViewPager pager;
    private final MonthPagerAdapter adapter;
    private CalendarDay currentWeek;
    private TitleFormatter titleFormatter = DEFAULT_TITLE_FORMATTER;
    private static int flagoftime = Calendar.WEEK_OF_MONTH;
    private long [] numhit =new long[2];

    List<DayViewDecorator> dayViewDecorators;

    private final WeekView.Callbacks WeekViewCallbacks = new WeekView.Callbacks() {
        @Override
        public void onDateChanged(CalendarDay date) {
            currentWeek = date;
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
            }else if(v == title){
                System.arraycopy(numhit,1,numhit,0,numhit.length-1);
                numhit[numhit.length-1] = SystemClock.uptimeMillis();
                if(numhit[0]-numhit[1]<=500){
                    setSelectedDate(new Date());
                }
            }
        }
    };

    private final ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            currentWeek = adapter.getItem(position);

            if(monthListener != null) {
                monthListener.onMonthChanged(MaterialCalendarView.this, currentWeek);
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

        adapter.setCallbacks(WeekViewCallbacks);

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
            Log.e("Attr Error", "error" , e);
        }
        finally {
            a.recycle();
        }

        currentWeek = new CalendarDay();
        setCurrentDate(currentWeek);
    }

    private void setupChildren() {
        int tileSize = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                getResources().getInteger(R.integer.mcv_default_tile_size),
                getResources().getDisplayMetrics()
        );
        root = new LinearLayout(getContext());
        root.setOrientation(LinearLayout.VERTICAL);
        root.setClipChildren(false);
        root.setClipToPadding(false);
        LayoutParams p = new LayoutParams(
                tileSize * WeekView.DEFAULT_DAYS_IN_WEEK,
                //change
                tileSize * (WeekView.DEFAULT_MONTH_TILE_HEIGHT + 1)
        );
        p.gravity = Gravity.CENTER;
        addView(root, p);


        pager.setId(R.id.mcv_pager);
        pager.setOffscreenPageLimit(1);
        root.addView(pager, new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, 0, WeekView.DEFAULT_MONTH_TILE_HEIGHT
        ));

        LinearLayout bottombar = new LinearLayout(getContext());
        bottombar.setOrientation(LinearLayout.HORIZONTAL);
        bottombar.setClipChildren(false);
        bottombar.setClipToPadding(false);
        root.addView(bottombar, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1));


        title.setGravity(Gravity.CENTER);
        bottombar.addView(title, new LinearLayout.LayoutParams(
                0, LayoutParams.MATCH_PARENT, 1
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
        if(currentWeek != null) {
            title.setText(titleFormatter.format(currentWeek));
        }
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
                size * WeekView.DEFAULT_DAYS_IN_WEEK,
                size * (WeekView.DEFAULT_MONTH_TILE_HEIGHT + 1)
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
     * @return The current day shown, will be set to first day of the month
     */
    public CalendarDay getCurrentDate() {
        return adapter.getItem(pager.getCurrentItem());
    }

    /**
     * @param day a CalendarDay to focus the calendar on
     */
    public void setCurrentDate(CalendarDay day) {
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
    public void setMinimumDate(Calendar calendar) {
        setMinimumDate(calendar == null ? null : new CalendarDay(calendar));
        setRangeDates(minDate, maxDate);
    }

    /**
     * @param date set the minimum selectable date, null for no minimum
     */
    public void setMinimumDate(Date date) {
        setMinimumDate(date == null ? null : new CalendarDay(date));
        setRangeDates(minDate, maxDate);
    }

    /**
     * @param calendar set the minimum selectable date, null for no minimum
     */
    public void setMinimumDate(CalendarDay calendar) {
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
    public void setMaximumDate(Calendar calendar) {
        setMaximumDate(calendar == null ? null : new CalendarDay(calendar));
        setRangeDates(minDate, maxDate);
    }

    /**
     * @param date set the maximum selectable date, null for no maximum
     */
    public void setMaximumDate(Date date) {
        setMaximumDate(date == null ? null : new CalendarDay(date));
        setRangeDates(minDate, maxDate);
    }

    /**
     * @param calendar set the maximum selectable date, null for no maximum
     */
    public void setMaximumDate(CalendarDay calendar) {
        maxDate = calendar;
        setRangeDates(minDate, maxDate);
    }

    /**
     *
     * By default, only days of one month are shown. If this is set true,
     * then days from the previous and next singletimes are used to fill the empty space.
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
     * @return true if days from previous or next singletimes are shown, otherwise false.
     */
    public boolean getShowOtherDates() {
        return adapter.getShowOtherDates();
    }

    /**
     * Set a custom formatter for the month/year title
     * @param titleFormatter new formatter to use, null to use default formatter
     */
    public void setTitleFormatter(TitleFormatter titleFormatter) {
        this.titleFormatter = titleFormatter == null ? DEFAULT_TITLE_FORMATTER : titleFormatter;
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
        CalendarDay c = currentWeek;
        adapter.setRangeDates(min, max);
        currentWeek = c;
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
     * @see Calendar
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
        return adapter.firstDayOfTheWeek;
    }

    private static class MonthPagerAdapter extends PagerAdapter {

        private static final int TAG_ITEM = R.id.mcv_pager;

        private final MaterialCalendarView view;
        private final LinkedList<WeekView> currentViews;
        private final ArrayList<CalendarDay> singletimes;

        private WeekView.Callbacks callbacks = null;
        private Integer color = null;
        private Integer dateTextAppearance = null;
        private Integer weekDayTextAppearance = null;
        private Boolean showOtherDates = null;
        private Boolean showInWeek = true;
        private CalendarDay minDate = null;
        private CalendarDay maxDate = null;
        private CalendarDay selectedDate = null;
        private WeekDayFormatter weekDayFormatter = WeekDayFormatter.DEFAULT;
        private List<DayViewDecorator> decorators;
        private int firstDayOfTheWeek;


        private MonthPagerAdapter(MaterialCalendarView view) {
            this.view = view;
            currentViews = new LinkedList<>();
            singletimes = new ArrayList<>();
            setRangeDates(null, null);
        }


        public void setDecorators(List<DayViewDecorator> decorators){
            this.decorators = decorators;
        }

        public void invalidateDecorators() {
            for(WeekView WeekView : currentViews) {
                WeekView.updateUi();
            }
        }

        @Override
        public int getCount() {
            return singletimes.size();
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
            for (int i = 0; i < singletimes.size(); i++) {
                CalendarDay week = singletimes.get(i);
                if (day.getYear() == week.getYear() ) {
                	if(flagoftime==Calendar.MONTH){
                		 return i;
                	}else if(day.getWeek() == week.getWeek()){

                		return i;
                	}
                }
            }
            return getCount() / 2;
        }

        @Override
        public int getItemPosition(Object object) {
            if(!(object instanceof WeekView)) {
                return POSITION_NONE;
            }
            WeekView WeekView = (WeekView) object;
            CalendarDay month = (CalendarDay) WeekView.getTag(TAG_ITEM);
            if(month == null) {
                return POSITION_NONE;
            }
            int index = singletimes.indexOf(month);
            if(index < 0) {
                return POSITION_NONE;
            }
            return index;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            CalendarDay time = singletimes.get(position);
            WeekView WeekView = new WeekView(container.getContext());
            WeekView.setTag(TAG_ITEM, time);

            WeekView.setFirstDayOfWeek(firstDayOfTheWeek);
            WeekView.setWeekDayFormatter(weekDayFormatter);
            WeekView.setCallbacks(callbacks);
            if(color != null) {
                WeekView.setSelectionColor(color);
            }
            if(dateTextAppearance != null) {
                WeekView.setDateTextAppearance(dateTextAppearance);
            }
            if(weekDayTextAppearance != null) {
                WeekView.setWeekDayTextAppearance(weekDayTextAppearance);
            }
            if(showOtherDates != null) {
                WeekView.setShowOtherDates(showOtherDates);
            }
//            if(showInWeek != null) {
//                WeekView.setTypeOfShow(showInWeek);
//            }
            WeekView.setMinimumDate(minDate);
            WeekView.setMaximumDate(maxDate);
            WeekView.setSelectedDate(selectedDate);

            WeekView.setDate(time);

            container.addView(WeekView);
            currentViews.add(WeekView);

            if(decorators!=null) {
                WeekView.setDayViewDecorators(decorators);
            }

            return WeekView;
        }

        public void setFirstDayOfWeek(int day) {
            firstDayOfTheWeek = day;
            for(WeekView WeekView : currentViews) {
                WeekView.setFirstDayOfWeek(firstDayOfTheWeek);
            }
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            WeekView WeekView = (WeekView) object;
            currentViews.remove(WeekView);
            container.removeView(WeekView);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public void setCallbacks(WeekView.Callbacks callbacks) {
            this.callbacks = callbacks;
            for(WeekView WeekView : currentViews) {
                WeekView.setCallbacks(callbacks);
            }
        }

        public void setSelectionColor(int color) {
            this.color = color;
            for(WeekView WeekView : currentViews) {
                WeekView.setSelectionColor(color);
            }
        }

        public void setDateTextAppearance(int taId) {
            if(taId == 0) {
                return;
            }
            this.dateTextAppearance = taId;
            for(WeekView WeekView : currentViews) {
                WeekView.setDateTextAppearance(taId);
            }
        }

        public void setShowOtherDates(boolean show) {
            this.showOtherDates = show;
            for(WeekView WeekView : currentViews) {
                WeekView.setShowOtherDates(show);
            }
        }
//
//        public void setShowInWeek(boolean showInWeek) {
//            this.showInWeek = showInWeek;
//            for(WeekView WeekView : currentViews) {
//                WeekView.setShowOtherDates(showInWeek);
//            }
//        }

        public void setWeekDayFormatter(WeekDayFormatter formatter) {
            this.weekDayFormatter = formatter;
            for(WeekView WeekView : currentViews) {
                WeekView.setWeekDayFormatter(formatter);
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
            for(WeekView WeekView : currentViews) {
                WeekView.setWeekDayTextAppearance(taId);
            }
        }

        public void setRangeDates(CalendarDay min, CalendarDay max) {
            this.minDate = min;
            this.maxDate = max;
            for(WeekView WeekView : currentViews) {
                WeekView.setMinimumDate(min);
                WeekView.setMaximumDate(max);
            }

            if(min == null) {
                Calendar worker = CalendarUtils.getInstance();
                worker.add(Calendar.YEAR, -50);
                min = new CalendarDay(worker);
            }

            if(max == null) {
                Calendar worker = CalendarUtils.getInstance();
                worker.add(Calendar.YEAR, 50);
                max = new CalendarDay(worker);
            }

            Calendar worker = CalendarUtils.getInstance();
            min.copyTo(worker);
            if(showInWeek){
                CalendarUtils.setToFirstDay(worker);
            }
            singletimes.clear();
            CalendarDay workingTime = new CalendarDay(worker);
            while (!max.isBefore(workingTime)) {
                singletimes.add(new CalendarDay(worker));
                flagoftime = Calendar.WEEK_OF_MONTH;
                worker.add(flagoftime, 1);
                workingTime = new CalendarDay(worker);
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

        public void setSelectedDate(CalendarDay date) {
            this.selectedDate = getValidSelectedDate(date);
            for(WeekView WeekView : currentViews) {
                WeekView.setSelectedDate(selectedDate);
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
            return singletimes.get(position);
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



    }

    public void addDecorators(DayViewDecorator... decorators){
        if(dayViewDecorators == null){
            dayViewDecorators = new ArrayList<>();
        }

        for(DayViewDecorator decorator : decorators) {
            dayViewDecorators.add(decorator);
        }

        adapter.setDecorators(dayViewDecorators);
        invalidateDecorators();
    }

    public void addDecorator(DayViewDecorator decorator){
        if(dayViewDecorators == null){
            dayViewDecorators = new ArrayList<>();
        }
        dayViewDecorators.add(decorator);
        adapter.setDecorators(dayViewDecorators);
        invalidateDecorators();
    }

    public void removeDecorators(){
        dayViewDecorators = null;
        adapter.setDecorators(null);
        invalidateDecorators();
    }

    public void removeDecorator(DayViewDecorator decorator){
        dayViewDecorators.remove(decorator);
        adapter.setDecorators(dayViewDecorators);
        invalidateDecorators();
    }

    public void invalidateDecorators(){
        adapter.invalidateDecorators();
    }

}
