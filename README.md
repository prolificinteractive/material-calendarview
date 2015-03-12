Material Calendar View
======================

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Material%20Calendar%20View-blue.svg?style=flat)](https://android-arsenal.com/details/1/1531)

![Demo Screen Capture](/images/screencast.gif)

A better looking implementation of Android's CalendarView. The goal is to have a more Material look
and feel, rather than 100% parity with the platform's implementation.

Usage
-----

1. Add `compile 'com.prolificinteractive:material-calendarview:0.2.5'` to your dependencies.
2. Add `MaterialCalendarView` into your layouts or view hierarchy.
3. Set a `OnDateChangedListener` or call `MaterialCalendarView.getSelectedDate()` when you need it.

Customization
-------------

One of the aims of this library is to be customizable. Below is an example of custom xml attributes
available.

```xml
<com.prolificinteractive.materialcalendarview.MaterialCalendarView
    android:id="@+id/calendarView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    app:showOtherDates="boolean"
    app:arrowColor="color"
    app:selectionColor="color"
    app:headerTextAppearance="style"
    app:dateTextAppearance="style"
    app:weekDayTextAppearance="style"
    app:weekDayLabels="array"
    app:monthLabels="array"
    />
```

### showOtherDates

By default, only days of one month are shown. If this is set `true`,
then days from the previous and next months are used to fill the empty space.
This also controls showing dates outside of the min-max range.

### arrowColor

Set the color of the arrows used to page the calendar. Black by default.

### selectionColor

Set the color of the date selector. By default this is the color set by
`?android:attr/colorAccent` on 5.0+ or `?attr/colorAccent` from the AppCompat library.

### headerTextAppearance

Override the text appearance of the month-year indicator at the top.

### weekDayTextAppearance

Override the text appearance of the week day indicators.

### dateTextAppearance

Override the text appearance of the dates.

### weekDayLabels

Supply custom labels for the days of the week. This sets an `ArrayWeekDayFormatter` on the `CalendarView`.
The default uses Java's `Calendar` class to get a `SHORT` display name.

### monthLabels

Supply custom labels for the months of the year. This sets a `MonthArrayTitleFormatter` on the `CalendarView`.
The default implementation formats using `SimpleDateFormat` with a `"MMMM yyyy"` format.

Contributing
============

Would you like to contribute? Fork us and send a pull request! Be sure to checkout our issues first.

License
=======

>Copyright 2014 Prolific Interactive
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
