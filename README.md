<img src="/images/ic_launcher-web.png" width="300px" />

Material Calendar View [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Material%20Calendar%20View-blue.svg?style=flat)](https://android-arsenal.com/details/1/1531)
======================

A Material design back port of Android's CalendarView. The goal is to have a Material look
and feel, rather than 100% parity with the platform's implementation.

<img src="/images/screencast.gif" alt="Demo Screen Capture" width="300px" />

Usage
-----

1. Add `compile 'com.prolificinteractive:material-calendarview:1.2.0'` to your dependencies.
2. Add `MaterialCalendarView` into your layouts or view hierarchy.
3. Set a `OnDateSelectedListener` or call `MaterialCalendarView.getSelectedDates()` when you need it.

[Javadoc Available Here](http://prolificinteractive.github.io/material-calendarview/)

Example:

```xml
<com.prolificinteractive.materialcalendarview.MaterialCalendarView
    android:id="@+id/calendarView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    app:mcv_showOtherDates="all"
    app:mcv_selectionColor="#00F"
    />
```

Major Change in 1.2.0
---------------------
You can now collapse the calendar view to single week paging by calling
`setCalendarDisplayMode(CalendarMode.WEEKS)`. By default it is set to `CalendarMode.MONTHS`. Thanks
[Dominik Zarzecki](https://github.com/dominikzarzecki)!

Major Change in 1.0.0
---------------------

With the implementation of multiple selection, some of the apis needed to change to support it,
namely `OnDateChangedListener` is now `OnDateSelectedListener`. There are also a bunch of new apis
for multiple selection.

Also, `showOtherDates` is now a set of flags for finer control over which extra dates are shown.

Major Change in 0.8.0
---------------------

The view now responds better to layout parameters.
The functionality is similar to how `adjustViewBounds` works with ImageView,
where the view will try and take up as much space as necessary,
but we base it on tile size instead of an aspect ratio.
The exception being that if a `tileSize` is set,
that will override everything and set the view to that size.

Customization
-------------

One of the aims of this library is to be customizable. The many options include:

* [Define the view's width and height in terms of tile size](docs/CUSTOMIZATION.md#tile-size)
* [Single or Multiple date selection, or disabling selection entirely](docs/CUSTOMIZATION.md#date-selection)
* [Showing dates from other months or those out of range](docs/CUSTOMIZATION.md#showing-other-dates)
* [Setting the first day of the week](docs/CUSTOMIZATION.md#first-day-of-the-week)
* [Show only a range of dates](docs/CUSTOMIZATION.md#date-ranges)
* [Customize the top bar](docs/CUSTOMIZATION.md#topbar-options)
* [Custom labels for the header, weekdays, or individual days](docs/CUSTOMIZATION.md#custom-labels)

### Events, Highlighting, Custom Selectors, and More!

All of this and more can be done via the decorator api. Please check out the [decorator documentation](docs/DECORATORS.md).

### Custom Selectors and Colors

If you provide custom drawables or colors, you'll want to make sure they respond to state.
Check out the [documentation for custom states](docs/CUSTOM_SELECTORS.md).

Contributing
============

Would you like to contribute? Fork us and send a pull request! Be sure to checkout our issues first.

License
=======

>Copyright 2016 Prolific Interactive
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
