Customization Options
=====================

```xml
<com.prolificinteractive.materialcalendarview.MaterialCalendarView
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/calendarView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:mcv_showOtherDates="boolean"
    app:mcv_arrowColor="color"
    app:mcv_selectionColor="color"
    app:mcv_headerTextAppearance="style"
    app:mcv_dateTextAppearance="style"
    app:mcv_weekDayTextAppearance="style"
    app:mcv_weekDayLabels="array"
    app:mcv_monthLabels="array"
    app:mcv_tileSize="dimension"
    app:mcv_tileWidth="dimension"
    app:mcv_tileHeight="dimension"
    app:mcv_firstDayOfWeek="enum"
    app:mcv_leftArrowMask="drawable"
    app:mcv_rightArrowMask="drawable"
    app:mcv_calendarMode="mode"
    />
```

## Tile Size

One of the fundamental concepts in this library is that of `tileSize`.
Even if you don't set one explicitly, one is calculated and is used in sizing the view.

By default, with no tile size set, the view will scale to fill as much space as it can.
The functionality is similar to ImageView with `adjustViewBounds` set to true.

If a tileSize is set, that will override the `layout_width` and `layout_height` set.

The view is 7 tiles wide and 8 tiles high (with the top bar visible).

### Width and Height

You also have the possibility to use `tileWidth` and `tileHeight` separately. I would recommend using either `tileSize` or, `tileWidth` and `tileHeight`.


## Date Selection

We support four modes of selection: single, multiple, range or none. The default is single selection.
The mode can be changed by calling `setSelectionMode()` and passing the appropriate constant (`SELECTION_MODE_NONE`, `SELECTION_MODE_SINGLE`, `SELECTION_MODE_RANGE` or `SELECTION_MODE_MULTIPLE`).
If you change to single selection, all selected days except the last selected will be cleared.
If you change to none or range, all selected days will be cleared.

You can set an `OnDateSelectedListener` to listen for selections, make sure to take into account multiple calls for the same date and state. In case of range selection, use `OnRangeSelectedListener` which returns the list of date from the range including first and last.
You can manually select or deselect dates by calling `setDateSelected()`.
Use `setSelectedDate()` to clear the current selection(s) and select the provided date.

There are also: `clearSelection()`, `getSelectedDates()`, and `getSelectionMode()`; which should work as you would expect.


## Showing Other Dates

By default, only days of one month, in the min-max range, are shown.
You can customize this by setting `mcv_showOtherDates` in xml, or by calling `setShowOtherDates()`.

Avaliable flags are:
* `other_months`: Show additional days from the previous and next months
    * This flag also enables the `out_of_range` flag, to prevent weird blank areas that nobody wants.
* `out_of_range`: Show dates that are outside of the minimum and maximum date range
    * This will only affect dates in the current month. Use the `other_months` flag to show other months.
* `decorated_disabled`: Show dates that are set as disabled by a decorator
    * This will only affect dates in the current month and inside the minimum/maximum date range.
* `none`: An alias that sets none of the flags
* `all`: An alias that sets all of the flags
* `defaults`: An alias of flags set by default. Currently this is only `decorated_disabled`.

There are similar constants on `MaterialCalendarView` such as `MaterialCalendarView.SHOW_DECORATED_DISABLED`.


## Selection Color

The default color of the calendar selector is the one set referenced by `?android:attr/colorAccent` on 5.0+ or `?attr/colorAccent` from the AppCompat library (black as a last resort).
You can provide a custom color by setting `mcv_selectionColor` in xml, or by calling `setSelectionColor()`.

If you want more control than just color, you can use the [decorator api](DECORATORS.md) to set a [custom selector](CUSTOM_SELECTORS.md).


## Topbar Options

### Visibility

You can hide or show the topbar (arrow buttons and month label) by calling `setTopbarVisible(boolean)`
The default is visible.

### Arrow Color

You can change the topbar arrow colors by setting `mcv_arrowColor` in xml, or calling `setArrowColor(int)`.
This will color the left and right arrow masks using a color filter.

### Arrow Masks

Arrow masks are drawables that will be colored with arrow color and be drawn for previous and next month buttons.
The color is applied with a color filter using `PorterDuff.Mode.SRC_ATOP`.
They can be set in xml using `mcv_leftArrowMask` and `mcv_rightArrowMask` or by calling `setLeftArrowMask()` and `setRightArrowMask()`.


## Custom Labels

### Header

You can customize the label displayed in the header by setting a custom `TitleFormatter` by calling `setTitleFormatter()`.
The formatter's `format()` method will be called with a `CalendarDay` containing the month and year you should format.
The default implementation uses a `SimpleDateFormat` with a format of `"MMMM yyyy"`.
The library provides a `DateFormatTitleFormatter` and `MonthArrayTitleFormatter` for convenience.

You can also set a string array resource with `mcv_monthLabels` that will use the `MonthArrayTitleFormatter` to format the title with the months provided.

### WeekDays

You can supply a custom formatter for weekdays with a `WeekDayFormatter` by calling `setWeekDayFormatter()`.
The default implementation is a `CalendarWeekDayFormatter`, which uses `java.util.Calendar` to get weekday labels.
We also provide `ArrayWeekDayFormatter`, which uses `CharSequence` array as week day labels.
You can set `mcv_weekDayLabels` in xml with a string array resource, which will set an `ArrayWeekDayFormatter`.

### DayFormatter

You can set custom day labels by passing a `DayFormatter` to the `setDayFormatter()` method.
The default is a `DateFormatDayFormatter`, which uses a `SimpleDateFormat` with format `"d"`.

Unlike the formatters for Header or WeekDays, this formatter returns a String.
If you want to use spans on your day labels, you will need to use the [decorator api](DECORATORS.md).


## Text Appearances

There are three different text appearances you can set:

* Header: `mcv_headerTextAppearance` or `setHeaderTextAppearance()`
* Weekday: `mcv_weekDayTextAppearance` or `setWeekDayTextAppearance()`
* Date: `mcv_dateTextAppearance` or `setDateTextAppearance()`

The header text appearance is used for the topbar month label.
The weekday is for the row of weekday labels, and date is for the individual days.

For date text appearance, make sure you respond to presses and states. [Read more here](CUSTOM_SELECTORS.md).
