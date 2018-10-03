package com.prolificinteractive.materialcalendarview;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ArrayRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
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
import java.util.List;
import java.util.Locale;
import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;
import org.threeten.bp.temporal.WeekFields;

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

  public static final int INVALID_TILE_DIMENSION = -10;

  /**
   * {@linkplain IntDef} annotation for selection mode.
   *
   * @see #setSelectionMode(int)
   * @see #getSelectionMode()
   */
  @Retention(RetentionPolicy.SOURCE)
  @IntDef(
      { SELECTION_MODE_NONE, SELECTION_MODE_SINGLE, SELECTION_MODE_MULTIPLE, SELECTION_MODE_RANGE })
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
   * Selection mode which allows selection of a range between two dates
   */
  public static final int SELECTION_MODE_RANGE = 3;

  /**
   * {@linkplain IntDef} annotation for showOtherDates.
   *
   * @see #setShowOtherDates(int)
   * @see #getShowOtherDates()
   */
  @SuppressLint("UniqueConstants")
  @Retention(RetentionPolicy.SOURCE)
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
  public static final int SHOW_OUT_OF_RANGE = 1 << 1;

  /**
   * Show days that are individually disabled with decorators.
   * This will only show dates in the current month and inside the minimum and maximum date range.
   */
  public static final int SHOW_DECORATED_DISABLED = 1 << 2;

  /**
   * The default flags for showing non-enabled dates. Currently only shows {@link
   * #SHOW_DECORATED_DISABLED}
   */
  public static final int SHOW_DEFAULTS = SHOW_DECORATED_DISABLED;

  /**
   * Show all the days
   */
  public static final int SHOW_ALL =
      SHOW_OTHER_MONTHS | SHOW_OUT_OF_RANGE | SHOW_DECORATED_DISABLED;

  /**
   * Use this orientation to animate the title vertically
   */
  public static final int VERTICAL = 0;

  /**
   * Use this orientation to animate the title horizontally
   */
  public static final int HORIZONTAL = 1;

  /**
   * Default tile size in DIPs. This is used in cases where there is no tile size specificed and the
   * view is set to {@linkplain ViewGroup.LayoutParams#WRAP_CONTENT WRAP_CONTENT}
   */
  public static final int DEFAULT_TILE_SIZE_DP = 44;
  private static final int DEFAULT_DAYS_IN_WEEK = 7;
  private static final int DEFAULT_MAX_WEEKS = 6;
  private static final int DAY_NAMES_ROW = 1;

  private final TitleChanger titleChanger;

  private final TextView title;
  private final ImageView buttonPast;
  private final ImageView buttonFuture;
  private final CalendarPager pager;
  private CalendarPagerAdapter<?> adapter;
  private CalendarDay currentMonth;
  private LinearLayout topbar;
  private CalendarMode calendarMode;
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

  private final ViewPager.OnPageChangeListener pageChangeListener =
      new ViewPager.OnPageChangeListener() {
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
  private OnDateLongClickListener longClickListener;
  private OnMonthChangedListener monthListener;
  private OnRangeSelectedListener rangeListener;

  CharSequence calendarContentDescription;
  private int accentColor = 0;
  private int tileHeight = INVALID_TILE_DIMENSION;
  private int tileWidth = INVALID_TILE_DIMENSION;
  @SelectionMode
  private int selectionMode = SELECTION_MODE_SINGLE;
  private boolean allowClickDaysOutsideCurrentMonth = true;
  private DayOfWeek firstDayOfWeek;
  private boolean showWeekDays;

  private State state;

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

    final LayoutInflater inflater =
        (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
    final View content = inflater.inflate(R.layout.calendar_view, null, false);

    topbar = content.findViewById(R.id.header);
    buttonPast = content.findViewById(R.id.previous);
    title = content.findViewById(R.id.month_name);
    buttonFuture = content.findViewById(R.id.next);
    pager = new CalendarPager(getContext());

    buttonPast.setOnClickListener(onClickListener);
    buttonFuture.setOnClickListener(onClickListener);

    titleChanger = new TitleChanger(title);

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
      int calendarModeIndex = a.getInteger(
          R.styleable.MaterialCalendarView_mcv_calendarMode,
          0
      );
      int firstDayOfWeekInt = a.getInteger(
          R.styleable.MaterialCalendarView_mcv_firstDayOfWeek,
          -1
      );

      titleChanger.setOrientation(
          a.getInteger(
              R.styleable.MaterialCalendarView_mcv_titleAnimationOrientation,
              VERTICAL
          ));

      if (firstDayOfWeekInt >= 1 && firstDayOfWeekInt <= 7) {
        firstDayOfWeek = DayOfWeek.of(firstDayOfWeekInt);
      } else {
        firstDayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
      }

      showWeekDays = a.getBoolean(R.styleable.MaterialCalendarView_mcv_showWeekDays, true);

      newState()
          .setFirstDayOfWeek(firstDayOfWeek)
          .setCalendarDisplayMode(CalendarMode.values()[calendarModeIndex])
          .setShowWeekDays(showWeekDays)
          .commit();

      setSelectionMode(a.getInteger(
          R.styleable.MaterialCalendarView_mcv_selectionMode,
          SELECTION_MODE_SINGLE
      ));

      final int tileSize = a.getLayoutDimension(
          R.styleable.MaterialCalendarView_mcv_tileSize,
          INVALID_TILE_DIMENSION
      );
      if (tileSize > INVALID_TILE_DIMENSION) {
        setTileSize(tileSize);
      }

      final int tileWidth = a.getLayoutDimension(
          R.styleable.MaterialCalendarView_mcv_tileWidth,
          INVALID_TILE_DIMENSION
      );
      if (tileWidth > INVALID_TILE_DIMENSION) {
        setTileWidth(tileWidth);
      }

      final int tileHeight = a.getLayoutDimension(
          R.styleable.MaterialCalendarView_mcv_tileHeight,
          INVALID_TILE_DIMENSION
      );
      if (tileHeight > INVALID_TILE_DIMENSION) {
        setTileHeight(tileHeight);
      }

      setLeftArrow(
          a.getResourceId(
              R.styleable.MaterialCalendarView_mcv_leftArrow,
              R.drawable.mcv_action_previous
          )
      );
      setRightArrow(
          a.getResourceId(
              R.styleable.MaterialCalendarView_mcv_rightArrow,
              R.drawable.mcv_action_next
          )
      );

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

      setAllowClickDaysOutsideCurrentMonth(a.getBoolean(
          R.styleable.MaterialCalendarView_mcv_allowClickDaysOutsideCurrentMonth,
          true
      ));
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      a.recycle();
    }

    // Adapter is created while parsing the TypedArray attrs, so setup has to happen after
    setupChildren();

    currentMonth = CalendarDay.today();
    setCurrentDate(currentMonth);

    if (isInEditMode()) {
      removeView(pager);
      MonthView monthView = new MonthView(this, currentMonth, getFirstDayOfWeek(), true);
      monthView.setSelectionColor(getSelectionColor());
      monthView.setDateTextAppearance(adapter.getDateTextAppearance());
      monthView.setWeekDayTextAppearance(adapter.getWeekDayTextAppearance());
      monthView.setShowOtherDates(getShowOtherDates());
      addView(monthView, new LayoutParams(calendarMode.visibleWeeksCount + DAY_NAMES_ROW));
    }
  }

  private void setupChildren() {
    addView(topbar);

    pager.setId(R.id.mcv_pager);
    pager.setOffscreenPageLimit(1);
    int tileHeight = showWeekDays ? calendarMode.visibleWeeksCount + DAY_NAMES_ROW
                                  : calendarMode.visibleWeeksCount;
    addView(pager, new LayoutParams(tileHeight));
  }

  private void updateUi() {
    titleChanger.change(currentMonth);
    enableView(buttonPast, canGoBack());
    enableView(buttonFuture, canGoForward());
  }

  /**
   * Change the selection mode of the calendar. The default mode is {@linkplain
   * #SELECTION_MODE_SINGLE}
   *
   * @param mode the selection mode to change to. This must be one of
   * {@linkplain #SELECTION_MODE_NONE}, {@linkplain #SELECTION_MODE_SINGLE},
   * {@linkplain #SELECTION_MODE_RANGE} or {@linkplain #SELECTION_MODE_MULTIPLE}.
   * Unknown values will act as {@linkplain #SELECTION_MODE_SINGLE}
   * @see #getSelectionMode()
   * @see #SELECTION_MODE_NONE
   * @see #SELECTION_MODE_SINGLE
   * @see #SELECTION_MODE_MULTIPLE
   * @see #SELECTION_MODE_RANGE
   */
  public void setSelectionMode(final @SelectionMode int mode) {
    final @SelectionMode int oldMode = this.selectionMode;
    this.selectionMode = mode;
    switch (mode) {
      case SELECTION_MODE_RANGE:
        clearSelection();
        break;
      case SELECTION_MODE_MULTIPLE:
        break;
      case SELECTION_MODE_SINGLE:
        if (oldMode == SELECTION_MODE_MULTIPLE || oldMode == SELECTION_MODE_RANGE) {
          //We should only have one selection now, so we should pick one
          List<CalendarDay> dates = getSelectedDates();
          if (!dates.isEmpty()) {
            setSelectedDate(getSelectedDate());
          }
        }
        break;
      default:
      case SELECTION_MODE_NONE:
        this.selectionMode = SELECTION_MODE_NONE;
        if (oldMode != SELECTION_MODE_NONE) {
          //No selection! Clear out!
          clearSelection();
        }
        break;
    }

    adapter.setSelectionEnabled(selectionMode != SELECTION_MODE_NONE);
  }

  /**
   * Go to previous month or week without using the button {@link #buttonPast}. Should only go to
   * previous if {@link #canGoBack()} is true, meaning it's possible to go to the previous month
   * or week.
   */
  public void goToPrevious() {
    if (canGoBack()) {
      pager.setCurrentItem(pager.getCurrentItem() - 1, true);
    }
  }

  /**
   * Go to next month or week without using the button {@link #buttonFuture}. Should only go to
   * next if {@link #canGoForward()} is enabled, meaning it's possible to go to the next month or
   * week.
   */
  public void goToNext() {
    if (canGoForward()) {
      pager.setCurrentItem(pager.getCurrentItem() + 1, true);
    }
  }

  /**
   * Get the current selection mode. The default mode is {@linkplain #SELECTION_MODE_SINGLE}
   *
   * @return the current selection mode
   * @see #setSelectionMode(int)
   * @see #SELECTION_MODE_NONE
   * @see #SELECTION_MODE_SINGLE
   * @see #SELECTION_MODE_MULTIPLE
   * @see #SELECTION_MODE_RANGE
   */
  @SelectionMode
  public int getSelectionMode() {
    return selectionMode;
  }

  /**
   * Use {@link #getTileWidth()} or {@link #getTileHeight()} instead. This method is deprecated
   * and will just return the largest of the two sizes.
   *
   * @return tile height or width, whichever is larger
   */
  @Deprecated
  public int getTileSize() {
    return Math.max(tileHeight, tileWidth);
  }

  /**
   * Set the size of each tile that makes up the calendar.
   * Each day is 1 tile, so the widget is 7 tiles wide and 7 or 8 tiles tall
   * depending on the visibility of the {@link #topbar}.
   *
   * @param size the new size for each tile in pixels
   */
  public void setTileSize(int size) {
    this.tileWidth = size;
    this.tileHeight = size;
    requestLayout();
  }

  /**
   * @param tileSizeDp the new size for each tile in dips
   * @see #setTileSize(int)
   */
  public void setTileSizeDp(int tileSizeDp) {
    setTileSize(dpToPx(tileSizeDp));
  }

  /**
   * @return the height of tiles in pixels
   */
  public int getTileHeight() {
    return tileHeight;
  }

  /**
   * Set the height of each tile that makes up the calendar.
   *
   * @param height the new height for each tile in pixels
   */
  public void setTileHeight(int height) {
    this.tileHeight = height;
    requestLayout();
  }

  /**
   * @param tileHeightDp the new height for each tile in dips
   * @see #setTileHeight(int)
   */
  public void setTileHeightDp(int tileHeightDp) {
    setTileHeight(dpToPx(tileHeightDp));
  }

  /**
   * @return the width of tiles in pixels
   */
  public int getTileWidth() {
    return tileWidth;
  }

  /**
   * Set the width of each tile that makes up the calendar.
   *
   * @param width the new width for each tile in pixels
   */
  public void setTileWidth(int width) {
    this.tileWidth = width;
    requestLayout();
  }

  /**
   * @param tileWidthDp the new width for each tile in dips
   * @see #setTileWidth(int)
   */
  public void setTileWidthDp(int tileWidthDp) {
    setTileWidth(dpToPx(tileWidthDp));
  }

  private int dpToPx(int dp) {
    return (int) TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics()
    );
  }

  /**
   * Whether the pager can page forward, meaning the future month is enabled.
   *
   * @return true if there is a future month that can be shown
   */
  public boolean canGoForward() {
    return pager.getCurrentItem() < (adapter.getCount() - 1);
  }

  /**
   * Whether the pager can page backward, meaning the previous month is enabled.
   *
   * @return true if there is a previous month that can be shown
   */
  public boolean canGoBack() {
    return pager.getCurrentItem() > 0;
  }

  /**
   * Pass all touch events to the pager so scrolling works on the edges of the calendar view.
   */
  @Override
  public boolean onTouchEvent(MotionEvent event) {
    return pager.dispatchTouchEvent(event);
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
   * Set content description for button past
   *
   * @param description String to use as content description
   */
  public void setContentDescriptionArrowPast(final CharSequence description) {
    buttonPast.setContentDescription(description);
  }

  /**
   * Set content description for button future
   *
   * @param description String to use as content description
   */
  public void setContentDescriptionArrowFuture(final CharSequence description) {
    buttonFuture.setContentDescription(description);
  }

  /**
   * Set content description for calendar
   *
   * @param description String to use as content description
   */
  public void setContentDescriptionCalendar(final CharSequence description) {
    calendarContentDescription = description;
  }

  /**
   * Get content description for calendar
   *
   * @return calendar's content description
   */
  public CharSequence getCalendarContentDescription() {
    return calendarContentDescription != null
           ? calendarContentDescription
           : getContext().getString(R.string.calendar);
  }

  /**
   * Set a formatter for day content description.
   *
   * @param formatter the new formatter, null for default
   */
  public void setDayFormatterContentDescription(DayFormatter formatter) {
    adapter.setDayFormatterContentDescription(formatter);
  }

  /**
   * @return icon used for the left arrow
   */
  public Drawable getLeftArrow() {
    return buttonPast.getDrawable();
  }

  /**
   * @param icon the new icon to use for the left paging arrow
   */
  public void setLeftArrow(@DrawableRes final int icon) {
    buttonPast.setImageResource(icon);
  }

  /**
   * @return icon used for the right arrow
   */
  public Drawable getRightArrow() {
    return buttonFuture.getDrawable();
  }

  /**
   * @param icon the new icon to use for the right paging arrow
   */
  public void setRightArrow(@DrawableRes final int icon) {
    buttonFuture.setImageResource(icon);
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
   * Get the currently selected date, or null if no selection. Depending on the selection mode,
   * you might get different results.
   *
   * <p>For {@link #SELECTION_MODE_SINGLE}, returns the selected date.</p>
   * <p>For {@link #SELECTION_MODE_MULTIPLE}, returns the last date selected.</p>
   * <p>For {@link #SELECTION_MODE_RANGE}, returns the last date of the range. In most cases, you
   * should probably be using {@link #getSelectedDates()}.</p>
   * <p>For {@link #SELECTION_MODE_NONE}, returns null.</p>
   *
   * @return The selected day, or null if no selection. If in multiple selection mode, this
   * will return the last date of the list of selected dates.
   * @see MaterialCalendarView#getSelectedDates()
   */
  @Nullable public CalendarDay getSelectedDate() {
    List<CalendarDay> dates = adapter.getSelectedDates();
    if (dates.isEmpty()) {
      return null;
    } else {
      return dates.get(dates.size() - 1);
    }
  }

  /**
   * Return the list of currently selected dates. Mostly useful for {@link #SELECTION_MODE_MULTIPLE}
   * and {@link #SELECTION_MODE_RANGE}. For the other modes, check {@link #getSelectedDate()}.
   *
   * <p>For {@link #SELECTION_MODE_MULTIPLE}, returns the list in the order of selection.</p>
   * <p>For {@link #SELECTION_MODE_RANGE}, returns the range of dates ordered chronologically.</p>
   *
   * @return All of the currently selected dates.
   * @see MaterialCalendarView#getSelectedDate()
   */
  @NonNull public List<CalendarDay> getSelectedDates() {
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
   * @param date a Date set to a day to select. Null to clear selection
   */
  public void setSelectedDate(@Nullable LocalDate date) {
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
   * @param day a CalendarDay to change. Passing null does nothing
   * @param selected true if day should be selected, false to deselect
   */
  public void setDateSelected(@Nullable CalendarDay day, boolean selected) {
    if (day == null) {
      return;
    }
    adapter.setDateSelected(day, selected);
  }

  /**
   * Get the current first day of the month in month mode, or the first visible day of the
   * currently visible week.
   *
   * For example, in week mode, if the week is July 29th, 2018 to August 4th, 2018,
   * this will return July 29th, 2018. If in month mode and the month is august, then this method
   * will return August 1st, 2018.
   *
   * @return The current month or week shown, will be set to first day of the month in month mode,
   * or the first visible day for a week.
   */
  public CalendarDay getCurrentDate() {
    return adapter.getItem(pager.getCurrentItem());
  }

  /**
   * Set the calendar to a specific month or week based on a date.
   *
   * In month mode, the calendar will be set to the corresponding month.
   *
   * In week mode, the calendar will be set to the corresponding week.
   *
   * @param calendar a Calendar set to a day to focus the calendar on. Null will do nothing
   */
  public void setCurrentDate(@Nullable LocalDate calendar) {
    setCurrentDate(CalendarDay.from(calendar));
  }

  /**
   * Set the calendar to a specific month or week based on a date.
   *
   * In month mode, the calendar will be set to the corresponding month.
   *
   * In week mode, the calendar will be set to the corresponding week.
   *
   * @param day a CalendarDay to focus the calendar on. Null will do nothing
   */
  public void setCurrentDate(@Nullable CalendarDay day) {
    setCurrentDate(day, true);
  }

  /**
   * Set the calendar to a specific month or week based on a date.
   *
   * In month mode, the calendar will be set to the corresponding month.
   *
   * In week mode, the calendar will be set to the corresponding week.
   *
   * @param day a CalendarDay to focus the calendar on. Null will do nothing
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
   * @return the maximum selectable date for the calendar, if any
   */
  public CalendarDay getMaximumDate() {
    return maxDate;
  }

  /**
   * The default value is {@link #SHOW_DEFAULTS}, which currently is just {@link
   * #SHOW_DECORATED_DISABLED}.
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
   * Allow the user to click on dates from other months that are not out of range. Go to next or
   * previous month if a day outside the current month is clicked. The day still need to be
   * enabled to be selected.
   * Default value is true. Should be used with {@link #SHOW_OTHER_MONTHS}.
   *
   * @param enabled True to allow the user to click on a day outside current month displayed
   */
  public void setAllowClickDaysOutsideCurrentMonth(final boolean enabled) {
    this.allowClickDaysOutsideCurrentMonth = enabled;
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
   * @return true if allow click on days outside current month displayed
   */
  public boolean allowClickDaysOutsideCurrentMonth() {
    return allowClickDaysOutsideCurrentMonth;
  }

  /**
   * @return true if the week days names are shown
   */
  public boolean isShowWeekDays() {
    return showWeekDays;
  }

  /**
   * Set a custom formatter for the month/year title
   *
   * @param titleFormatter new formatter to use, null to use default formatter
   */
  public void setTitleFormatter(@Nullable TitleFormatter titleFormatter) {
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
   * Change the title animation orientation to have a different look and feel.
   *
   * @param orientation {@link MaterialCalendarView#VERTICAL} or {@link
   * MaterialCalendarView#HORIZONTAL}
   */
  public void setTitleAnimationOrientation(final int orientation) {
    titleChanger.setOrientation(orientation);
  }

  /**
   * Get the orientation of the animation of the title.
   *
   * @return Title animation orientation {@link MaterialCalendarView#VERTICAL} or {@link
   * MaterialCalendarView#HORIZONTAL}
   */
  public int getTitleAnimationOrientation() {
    return titleChanger.getOrientation();
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

  /**
   * Get the current {@link CalendarMode} set of the Calendar.
   *
   * @return Whichever mode the calendar is currently in.
   */
  public CalendarMode getCalendarMode() {
    return calendarMode;
  }

  @Override
  protected Parcelable onSaveInstanceState() {
    SavedState ss = new SavedState(super.onSaveInstanceState());
    ss.showOtherDates = getShowOtherDates();
    ss.allowClickDaysOutsideCurrentMonth = allowClickDaysOutsideCurrentMonth();
    ss.minDate = getMinimumDate();
    ss.maxDate = getMaximumDate();
    ss.selectedDates = getSelectedDates();
    ss.selectionMode = getSelectionMode();
    ss.topbarVisible = getTopbarVisible();
    ss.dynamicHeightEnabled = mDynamicHeightEnabled;
    ss.currentMonth = currentMonth;
    ss.cacheCurrentPosition = state.cacheCurrentPosition;
    return ss;
  }

  @Override
  protected void onRestoreInstanceState(Parcelable state) {
    SavedState ss = (SavedState) state;
    super.onRestoreInstanceState(ss.getSuperState());
    state().edit()
        .setMinimumDate(ss.minDate)
        .setMaximumDate(ss.maxDate)
        .isCacheCalendarPositionEnabled(ss.cacheCurrentPosition)
        .commit();

    setShowOtherDates(ss.showOtherDates);
    setAllowClickDaysOutsideCurrentMonth(ss.allowClickDaysOutsideCurrentMonth);
    clearSelection();
    for (CalendarDay calendarDay : ss.selectedDates) {
      setDateSelected(calendarDay, true);
    }
    setTopbarVisible(ss.topbarVisible);
    setSelectionMode(ss.selectionMode);
    setDynamicHeightEnabled(ss.dynamicHeightEnabled);
    setCurrentDate(ss.currentMonth);
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
    if (min != null) {
      currentMonth = min.isAfter(currentMonth) ? min : currentMonth;
    }
    int position = adapter.getIndexForDay(c);
    pager.setCurrentItem(position, false);
    updateUi();
  }

  public static class SavedState extends BaseSavedState {

    int showOtherDates = SHOW_DEFAULTS;
    boolean allowClickDaysOutsideCurrentMonth = true;
    CalendarDay minDate = null;
    CalendarDay maxDate = null;
    List<CalendarDay> selectedDates = new ArrayList<>();
    boolean topbarVisible = true;
    int selectionMode = SELECTION_MODE_SINGLE;
    boolean dynamicHeightEnabled = false;
    CalendarDay currentMonth = null;
    boolean cacheCurrentPosition;

    SavedState(Parcelable superState) {
      super(superState);
    }

    @Override
    public void writeToParcel(@NonNull Parcel out, int flags) {
      super.writeToParcel(out, flags);
      out.writeInt(showOtherDates);
      out.writeByte((byte) (allowClickDaysOutsideCurrentMonth ? 1 : 0));
      out.writeParcelable(minDate, 0);
      out.writeParcelable(maxDate, 0);
      out.writeTypedList(selectedDates);
      out.writeInt(topbarVisible ? 1 : 0);
      out.writeInt(selectionMode);
      out.writeInt(dynamicHeightEnabled ? 1 : 0);
      out.writeParcelable(currentMonth, 0);
      out.writeByte((byte) (cacheCurrentPosition ? 1 : 0));
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
      showOtherDates = in.readInt();
      allowClickDaysOutsideCurrentMonth = in.readByte() != 0;
      ClassLoader loader = CalendarDay.class.getClassLoader();
      minDate = in.readParcelable(loader);
      maxDate = in.readParcelable(loader);
      in.readTypedList(selectedDates, CalendarDay.CREATOR);
      topbarVisible = in.readInt() == 1;
      selectionMode = in.readInt();
      dynamicHeightEnabled = in.readInt() == 1;
      currentMonth = in.readParcelable(loader);
      cacheCurrentPosition = in.readByte() != 0;
    }
  }

  private static int getThemeAccentColor(Context context) {
    int colorAttr;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      colorAttr = android.R.attr.colorAccent;
    } else {
      //Get colorAccent defined for AppCompat
      colorAttr =
          context.getResources().getIdentifier("colorAccent", "attr", context.getPackageName());
    }
    TypedValue outValue = new TypedValue();
    context.getTheme().resolveAttribute(colorAttr, outValue, true);
    return outValue.data;
  }

  /**
   * @return The first day of the week as a {@linkplain Calendar} day constant.
   */
  public DayOfWeek getFirstDayOfWeek() {
    return firstDayOfWeek;
  }

  /**
   * By default, the calendar will take up all the space needed to show any month (6 rows).
   * By enabling dynamic height, the view will change height dependant on the visible month.
   * <p>
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
   * Sets the listener to be notified upon long clicks on dates.
   *
   * @param longClickListener thing to be notified
   */
  public void setOnDateLongClickListener(OnDateLongClickListener longClickListener) {
    this.longClickListener = longClickListener;
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
   * Sets the listener to be notified upon a range has been selected.
   *
   * @param listener thing to be notified
   */
  public void setOnRangeSelectedListener(OnRangeSelectedListener listener) {
    this.rangeListener = listener;
  }

  /**
   * Add listener to the title or null to remove it.
   *
   * @param listener Listener to be notified.
   */
  public void setOnTitleClickListener(final OnClickListener listener) {
    title.setOnClickListener(listener);
  }

  /**
   * Dispatch date change events to a listener, if set
   *
   * @param day the day that was selected
   * @param selected true if the day is now currently selected, false otherwise
   */
  protected void dispatchOnDateSelected(final CalendarDay day, final boolean selected) {
    if (listener != null) {
      listener.onDateSelected(MaterialCalendarView.this, day, selected);
    }
  }

  /**
   * Dispatch a range of days to a range listener, if set, ordered chronologically.
   *
   * @param days Enclosing days ordered from first to last day.
   */
  protected void dispatchOnRangeSelected(@NonNull final List<CalendarDay> days) {
    if (rangeListener != null) {
      rangeListener.onRangeSelected(MaterialCalendarView.this, days);
    }
  }

  /**
   * Dispatch date change events to a listener, if set
   *
   * @param day first day of the new month
   */
  protected void dispatchOnMonthChanged(final CalendarDay day) {
    if (monthListener != null) {
      monthListener.onMonthChanged(MaterialCalendarView.this, day);
    }
  }

  /**
   * Call by {@link CalendarPagerView} to indicate that a day was clicked and we should handle it.
   * This method will always process the click to the selected date.
   *
   * @param date date of the day that was clicked
   * @param nowSelected true if the date is now selected, false otherwise
   */
  protected void onDateClicked(@NonNull CalendarDay date, boolean nowSelected) {
    switch (selectionMode) {
      case SELECTION_MODE_MULTIPLE: {
        adapter.setDateSelected(date, nowSelected);
        dispatchOnDateSelected(date, nowSelected);
      }
      break;
      case SELECTION_MODE_RANGE: {
        final List<CalendarDay> currentSelection = adapter.getSelectedDates();

        if (currentSelection.size() == 0) {
          // Selecting the first date of a range
          adapter.setDateSelected(date, nowSelected);
          dispatchOnDateSelected(date, nowSelected);
        } else if (currentSelection.size() == 1) {
          // Selecting the second date of a range
          final CalendarDay firstDaySelected = currentSelection.get(0);
          if (firstDaySelected.equals(date)) {
            // Right now, we are not supporting a range of one day, so we are removing the day instead.
            adapter.setDateSelected(date, nowSelected);
            dispatchOnDateSelected(date, nowSelected);
          } else if (firstDaySelected.isAfter(date)) {
            // Selecting a range, dispatching in reverse order...
            adapter.selectRange(date, firstDaySelected);
            dispatchOnRangeSelected(adapter.getSelectedDates());
          } else {
            // Selecting a range, dispatching in order...
            adapter.selectRange(firstDaySelected, date);
            dispatchOnRangeSelected(adapter.getSelectedDates());
          }
        } else {
          // Clearing selection and making a selection of the new date.
          adapter.clearSelections();
          adapter.setDateSelected(date, nowSelected);
          dispatchOnDateSelected(date, nowSelected);
        }
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
   * Select a fresh range of date including first day and last day.
   *
   * @param firstDay first day of the range to select
   * @param lastDay last day of the range to select
   */
  public void selectRange(final CalendarDay firstDay, final CalendarDay lastDay) {
    if (firstDay == null || lastDay == null) {
      return;
    } else if (firstDay.isAfter(lastDay)) {
      adapter.selectRange(lastDay, firstDay);
      dispatchOnRangeSelected(adapter.getSelectedDates());
    } else {
      adapter.selectRange(firstDay, lastDay);
      dispatchOnRangeSelected(adapter.getSelectedDates());
    }
  }

  /**
   * Call by {@link CalendarPagerView} to indicate that a day was clicked and we should handle it
   */
  protected void onDateClicked(final DayView dayView) {
    final CalendarDay currentDate = getCurrentDate();
    final CalendarDay selectedDate = dayView.getDate();
    final int currentMonth = currentDate.getMonth();
    final int selectedMonth = selectedDate.getMonth();

    if (calendarMode == CalendarMode.MONTHS
        && allowClickDaysOutsideCurrentMonth
        && currentMonth != selectedMonth) {
      if (currentDate.isAfter(selectedDate)) {
        goToPrevious();
      } else if (currentDate.isBefore(selectedDate)) {
        goToNext();
      }
    }
    onDateClicked(dayView.getDate(), !dayView.isChecked());
  }

  /**
   * Call by {@link CalendarPagerView} to indicate that a day was long clicked and we should handle
   * it
   */
  protected void onDateLongClicked(final DayView dayView) {
    if (longClickListener != null) {
      longClickListener.onDateLongClick(MaterialCalendarView.this, dayView.getDate());
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
    int measureTileWidth = -1;
    int measureTileHeight = -1;

    if (this.tileWidth != INVALID_TILE_DIMENSION || this.tileHeight != INVALID_TILE_DIMENSION) {
      if (this.tileWidth > 0) {
        //We have a tileWidth set, we should use that
        measureTileWidth = this.tileWidth;
      } else {
        measureTileWidth = desiredTileWidth;
      }
      if (this.tileHeight > 0) {
        //We have a tileHeight set, we should use that
        measureTileHeight = this.tileHeight;
      } else {
        measureTileHeight = desiredTileHeight;
      }
    } else if (specWidthMode == MeasureSpec.EXACTLY || specWidthMode == MeasureSpec.AT_MOST) {
      if (specHeightMode == MeasureSpec.EXACTLY) {
        //Pick the smaller of the two explicit sizes
        measureTileSize = Math.min(desiredTileWidth, desiredTileHeight);
      } else {
        //Be the width size the user wants
        measureTileSize = desiredTileWidth;
      }
    } else if (specHeightMode == MeasureSpec.EXACTLY || specHeightMode == MeasureSpec.AT_MOST) {
      //Be the height size the user wants
      measureTileSize = desiredTileHeight;
    }

    if (measureTileSize > 0) {
      //Use measureTileSize if set
      measureTileHeight = measureTileSize;
      measureTileWidth = measureTileSize;
    } else if (measureTileSize <= 0) {
      if (measureTileWidth <= 0) {
        //Set width to default if no value were set
        measureTileWidth = dpToPx(DEFAULT_TILE_SIZE_DP);
      }
      if (measureTileHeight <= 0) {
        //Set height to default if no value were set
        measureTileHeight = dpToPx(DEFAULT_TILE_SIZE_DP);
      }
    }

    //Calculate our size based off our measured tile size
    int measuredWidth = measureTileWidth * DEFAULT_DAYS_IN_WEEK;
    int measuredHeight = measureTileHeight * viewTileHeight;

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
          DEFAULT_DAYS_IN_WEEK * measureTileWidth,
          MeasureSpec.EXACTLY
      );

      int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
          p.height * measureTileHeight,
          MeasureSpec.EXACTLY
      );

      child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }
  }

  private int getWeekCountBasedOnMode() {
    int weekCount = calendarMode.visibleWeeksCount;
    final boolean isInMonthsMode = calendarMode.equals(CalendarMode.MONTHS);
    if (isInMonthsMode && mDynamicHeightEnabled && adapter != null && pager != null) {
      final LocalDate cal = adapter.getItem(pager.getCurrentItem()).getDate();
      final LocalDate tempLastDay = cal.withDayOfMonth(cal.lengthOfMonth());
      weekCount = tempLastDay.get(WeekFields.of(firstDayOfWeek, 1).weekOfMonth());
    }
    return showWeekDays ? weekCount + DAY_NAMES_ROW : weekCount;
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

  /**
   * Preserve the current parameters of the Material Calendar View.
   */
  public State state() {
    return state;
  }

  /**
   * Initialize the parameters from scratch.
   */
  public StateBuilder newState() {
    return new StateBuilder();
  }

  public class State {
    private final CalendarMode calendarMode;
    private final DayOfWeek firstDayOfWeek;
    private final CalendarDay minDate;
    private final CalendarDay maxDate;
    private final boolean cacheCurrentPosition;
    private final boolean showWeekDays;

    private State(final StateBuilder builder) {
      calendarMode = builder.calendarMode;
      firstDayOfWeek = builder.firstDayOfWeek;
      minDate = builder.minDate;
      maxDate = builder.maxDate;
      cacheCurrentPosition = builder.cacheCurrentPosition;
      showWeekDays = builder.showWeekDays;
    }

    /**
     * Modify parameters from current state.
     */
    public StateBuilder edit() {
      return new StateBuilder(this);
    }
  }

  public class StateBuilder {
    private CalendarMode calendarMode;
    private DayOfWeek firstDayOfWeek;
    private boolean cacheCurrentPosition = false;
    private CalendarDay minDate = null;
    private CalendarDay maxDate = null;
    private boolean showWeekDays;

    public StateBuilder() {
      calendarMode = CalendarMode.MONTHS;
      firstDayOfWeek =
          LocalDate.now().with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1).getDayOfWeek();
    }

    private StateBuilder(final State state) {
      calendarMode = state.calendarMode;
      firstDayOfWeek = state.firstDayOfWeek;
      minDate = state.minDate;
      maxDate = state.maxDate;
      cacheCurrentPosition = state.cacheCurrentPosition;
      showWeekDays = state.showWeekDays;
    }

    /**
     * Sets the first day of the week.
     * <p>
     * Uses the {@link DayOfWeek} day constants.
     *
     * @param day The first day of the week as a {@link DayOfWeek} enum.
     * @see java.util.Calendar
     */
    public StateBuilder setFirstDayOfWeek(DayOfWeek day) {
      this.firstDayOfWeek = day;
      return this;
    }

    /**
     * Set calendar display mode. The default mode is Months.
     * When switching between modes will select todays date, or the selected date,
     * if selection mode is single.
     *
     * @param mode - calendar mode
     */
    public StateBuilder setCalendarDisplayMode(CalendarMode mode) {
      this.calendarMode = mode;
      return this;
    }

    /**
     * @param date set the minimum selectable date, null for no minimum
     */
    public StateBuilder setMinimumDate(@Nullable LocalDate date) {
      setMinimumDate(CalendarDay.from(date));
      return this;
    }

    /**
     * @param calendar set the minimum selectable date, null for no minimum
     */
    public StateBuilder setMinimumDate(@Nullable CalendarDay calendar) {
      minDate = calendar;
      return this;
    }

    /**
     * @param date set the maximum selectable date, null for no maximum
     */
    public StateBuilder setMaximumDate(@Nullable LocalDate date) {
      setMaximumDate(CalendarDay.from(date));
      return this;
    }

    /**
     * @param calendar set the maximum selectable date, null for no maximum
     */
    public StateBuilder setMaximumDate(@Nullable CalendarDay calendar) {
      maxDate = calendar;
      return this;
    }

    /**
     * @param showWeekDays true to show week days names
     */
    public StateBuilder setShowWeekDays(boolean showWeekDays) {
      this.showWeekDays = showWeekDays;
      return this;
    }

    /**
     * Use this method to enable saving the current position when switching
     * between week and month mode. By default, the calendar update to the latest selected date
     * or the current date. When set to true, the view will used the month that the calendar is
     * currently on.
     *
     * @param cacheCurrentPosition Set to true to cache the current position, false otherwise.
     */
    public StateBuilder isCacheCalendarPositionEnabled(final boolean cacheCurrentPosition) {
      this.cacheCurrentPosition = cacheCurrentPosition;
      return this;
    }

    public void commit() {
      MaterialCalendarView.this.commit(new State(this));
    }
  }

  private void commit(State state) {
    // Use the calendarDayToShow to determine which date to focus on for the case of switching between month and week views
    CalendarDay calendarDayToShow = null;
    if (adapter != null && state.cacheCurrentPosition) {
      calendarDayToShow = adapter.getItem(pager.getCurrentItem());
      if (calendarMode != state.calendarMode) {
        CalendarDay currentlySelectedDate = getSelectedDate();
        if (calendarMode == CalendarMode.MONTHS && currentlySelectedDate != null) {
          // Going from months to weeks
          LocalDate lastVisibleCalendar = calendarDayToShow.getDate();
          CalendarDay lastVisibleCalendarDay = CalendarDay.from(lastVisibleCalendar.plusDays(1));
          if (currentlySelectedDate.equals(calendarDayToShow) ||
              (currentlySelectedDate.isAfter(calendarDayToShow) && currentlySelectedDate.isBefore(
                  lastVisibleCalendarDay))) {
            // Currently selected date is within view, so center on that
            calendarDayToShow = currentlySelectedDate;
          }
        } else if (calendarMode == CalendarMode.WEEKS) {
          // Going from weeks to months
          LocalDate lastVisibleCalendar = calendarDayToShow.getDate();
          CalendarDay lastVisibleCalendarDay = CalendarDay.from(lastVisibleCalendar.plusDays(6));
          if (currentlySelectedDate != null &&
              (currentlySelectedDate.equals(calendarDayToShow) || currentlySelectedDate.equals(
                  lastVisibleCalendarDay) ||
                  (currentlySelectedDate.isAfter(calendarDayToShow)
                      && currentlySelectedDate.isBefore(lastVisibleCalendarDay)))) {
            // Currently selected date is within view, so center on that
            calendarDayToShow = currentlySelectedDate;
          } else {
            calendarDayToShow = lastVisibleCalendarDay;
          }
        }
      }
    }

    this.state = state;
    // Save states parameters
    calendarMode = state.calendarMode;
    firstDayOfWeek = state.firstDayOfWeek;
    minDate = state.minDate;
    maxDate = state.maxDate;
    showWeekDays = state.showWeekDays;

    // Recreate adapter
    final CalendarPagerAdapter<?> newAdapter;
    switch (calendarMode) {
      case MONTHS:
        newAdapter = new MonthPagerAdapter(this);
        break;
      case WEEKS:
        newAdapter = new WeekPagerAdapter(this);
        break;
      default:
        throw new IllegalArgumentException("Provided display mode which is not yet implemented");
    }
    if (adapter == null) {
      adapter = newAdapter;
    } else {
      adapter = adapter.migrateStateAndReturn(newAdapter);
    }
    adapter.setShowWeekDays(showWeekDays);
    pager.setAdapter(adapter);
    setRangeDates(minDate, maxDate);

    // Reset height params after mode change
    int tileHeight = showWeekDays ? calendarMode.visibleWeeksCount + DAY_NAMES_ROW
                                  : calendarMode.visibleWeeksCount;
    pager.setLayoutParams(new LayoutParams(tileHeight));

    setCurrentDate(
        selectionMode == SELECTION_MODE_SINGLE && !adapter.getSelectedDates().isEmpty()
        ? adapter.getSelectedDates().get(0)
        : CalendarDay.today());

    if (calendarDayToShow != null) {
      pager.setCurrentItem(adapter.getIndexForDay(calendarDayToShow));
    }

    invalidateDecorators();
    updateUi();
  }

  /**
   * Used for enabling or disabling views, while also changing the alpha.
   *
   * @param view The view to enable or disable.
   * @param enable Whether to enable or disable the view.
   */
  private static void enableView(final View view, final boolean enable) {
    view.setEnabled(enable);
    view.setAlpha(enable ? 1f : 0.1f);
  }
}
