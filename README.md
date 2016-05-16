<img src="/images/hero.png"/>

Material Calendar View [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Material%20Calendar%20View-blue.svg?style=flat)](https://android-arsenal.com/details/1/1531)
======================

A Material design back port of Android's CalendarView. The goal is to have a Material look
and feel, rather than 100% parity with the platform's implementation.

<img src="/images/screencast.gif" alt="Demo Screen Capture" width="300px" />

Usage
-----

1. Add `compile 'com.prolificinteractive:material-calendarview:1.3.0'` to your dependencies.
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
#### @Experimental
`CalendarMode.WEEK` and all week mode functionality is officially marked `@Experimental`. All APIs
marked `@Experimental` are subject to change quickly and should not be used in production code. They
are allowed for testing and feedback.

Major Change in 1.3.0
---------------------
* Breaking change: `getTileSize` is deprecated. Use `getTileWidth` or `getTileHeight`.
* Added `goToNext` and `goToPrevious` API to programmatically trigger paging
* Allow users to click on dates outside of current month with `setAllowClickDaysOutsideCurrentMonth`
* Set tile width/height separately rather than single tile size with `setTileWidth` and `setTileHeight`
* Attributes: mcv_tileWidth, mcv_tileHeight, mcv_calendarMode

See other changes in the [CHANGELOG](/CHANGELOG.md).

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

## License

Material Calendar View is Copyright (c) 2016 Prolific Interactive. It may be redistributed under the terms specified in the [LICENSE] file.

[LICENSE]: /LICENSE

## Maintainers

![prolific](https://s3.amazonaws.com/prolificsitestaging/logos/Prolific_Logo_Full_Color.png)

Material Calendar View is maintained and funded by Prolific Interactive. The names and logos are trademarks of Prolific Interactive.
