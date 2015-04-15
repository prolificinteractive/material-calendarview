Material Calendar View
======================

[![Join the chat at https://gitter.im/prolificinteractive/material-calendarview](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/prolificinteractive/material-calendarview?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Material%20Calendar%20View-blue.svg?style=flat)](https://android-arsenal.com/details/1/1531)

![Demo Screen Capture](/images/screencast.gif)

A better looking implementation of Android's CalendarView. The goal is to have a more Material look
and feel, rather than 100% parity with the platform's implementation.

Usage
-----

1. Add `compile 'com.prolificinteractive:material-calendarview:0.3.0'` to your dependencies.
2. Add `MaterialCalendarView` into your layouts or view hierarchy.
3. Set a `OnDateChangedListener` or call `MaterialCalendarView.getSelectedDate()` when you need it.

Example:

```xml
<com.prolificinteractive.materialcalendarview.MaterialCalendarView
    android:id="@+id/calendarView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    app:mcv_showOtherDates="boolean"
    app:mcv_arrowColor="color"
    app:mcv_selectionColor="color"
    app:mcv_headerTextAppearance="style"
    app:mcv_dateTextAppearance="style"
    app:mcv_weekDayTextAppearance="style"
    app:mcv_weekDayLabels="array"
    app:mcv_monthLabels="array"
    app:mcv_tileSize="dimension"
    />
```

Customization
-------------

One of the aims of this library is to be customizable.

Options available in Java and as XML attributes:

| Attribute                 | Java                       | Type      | Description                                                                                                                                                                                                     |
|:--------------------------|:---------------------------|:----------|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| mcv_showOtherDates        | setShowOtherDates()        | boolean   | By default, only days of one month are shown. If this is set `true`,then days from the previous and next months are used to fill the empty space.This also controls showing dates outside of the min-max range. |
| mcv_arrowColor            | setArrowColor()            | color     | Set the color of the arrows used to page the calendar. Black by default.                                                                                                                                        |
| mcv_selectionColor        | setSelectionColor()        | color     | Set the color of the date selector. By default this is the color set by`?android:attr/colorAccent` on 5.0+ or `?attr/colorAccent` from the AppCompat library.                                                   |
| mcv_headerTextAppearance  | setHeaderTextAppearance()  | style     | Override the text appearance of the month-year indicator at the top.                                                                                                                                            |
| mcv_weekDayTextAppearance | setWeekDayTextAppearance() | style     | Override the text appearance of the week day indicators.                                                                                                                                                        |
| mcv_dateTextAppearance    | setDateTextAppearance()    | style     | Override the text appearance of the dates.                                                                                                                                                                      |
| mcv_weekDayLabels         | setWeekDayFormatter()      | array     | Supply custom labels for the days of the week. This sets an `ArrayWeekDayFormatter` on the `CalendarView`.The default uses Java's `Calendar` class to get a `SHORT` display name.                               |
| mcv_monthLabels           | setTitleFormatter()        | array     | Supply custom labels for the months of the year. This sets a `MonthArrayTitleFormatter` on the `CalendarView`.The default implementation formats using `SimpleDateFormat` with a `"MMMM yyyy"` format.          |
| mcv_tileSize              | setTileSizeDp()            | dimension | Set a custom size for each tile. Each day of the calendar is 1 tile, and the top bar is 1 tile high.The entire widget is 7 tiles by 8 tiles. The default tile size is `44dp`.                                   |

Options only available in Java:

| Method            | Description                                                                 |
|:------------------|:----------------------------------------------------------------------------|
| setMinimumDate()  | Set the earliest visible date on the calendar                               |
| setMaximumDate()  | Set the latest visible date on the calendar                                 |
| setSelectedDate() | Set the date to show as selected. Must be within minimum and maximum dates. |

Contributing
============

Would you like to contribute? Fork us and send a pull request! Be sure to checkout our issues first.

License
=======

>Copyright 2015 Prolific Interactive
>
>Licensed under the Apache License, Version 2.0 (the "License");
>you may not use this file except in compliance with the License.
>You may obtain a copy of the License at
>
>   http://www.apache.org/licenses/LICENSE-2.0
>
>Unless required by applicable law or agreed to in writing, software
>distributed under the License is distributed on an "AS IS" BASIS,
>WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
>See the License for the specific language governing permissions and
>limitations under the License.
