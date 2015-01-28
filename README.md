Material Calendar View
======================

![Demo Screen Capture](/images/screencast.gif)

A better looking implementation of android's CalendarView. The goal is to have a more Material look
and feel, rather than 100% parity with the platform's implementation.

Usage
-----

1. Import the library into your project. Currently only available to Prolific P's.
    1. Setup the PiBrary-Android repo for you and your project
    2. Add `compile 'com.prolificinteractive:materialcalendarview:+'`
2. Add `MaterialCalendarView` into your layouts or view hierarchy.
3. Set a `OnDateChangedListener` or call `MaterialCalendarView.getSelectedDate()` when you need it.

Customization
-------------

One of the aims of this library is to be customizable. Below is an example of custom xml attributes
available.

```
<com.prolificinteractive.materialcalendarview.MaterialCalendarView
    android:id="@+id/calendarView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:showOtherDates="boolean"
    app:arrowColor="color"
    app:selectionColor="color"
    app:headerTextAppearance="style"
    app:dateTextAppearance="style"
    app:weekDayTextAppearance="style"
    />
```

### showOtherDates

By default, only days of one month are shown. If this is set true,
then days from the previous and next months are used to fill the empty space.
This also controls showing dates outside of the min-max range.

### arrowColor

Set the color of the arrows used to page the calendar. Black by default.

### selectionColor

Set the color of the date selector. By default this is the color set by
`?android:attr/colorPrimary` on 5.0+ or `?attr/colorPrimary` from the AppCompat library.

### headerTextAppearance

Override the text appearance of the month-year indicator at the top

### weekDayTextAppearance

Override the text appearance of the week day indicators

### dateTextAppearance

Override the text appearance of the dates

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