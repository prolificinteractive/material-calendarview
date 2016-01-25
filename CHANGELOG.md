Change Log
==========

Version 1.2.0 *(2016-01-24)*
----------------------------

* Fix: Disable paging also disables arrows
* Fix: Allow paging from the entire width of the view
* New: Dynamic Height, the calendar can now resize its height based on the currently visible month
* New: Add single week calendar mode

Version 1.1.0 *(2015-10-19)*
----------------------------

* New: Ability to disable month swiping with `setPagingEnabled()`
* Fix [#149](https://github.com/prolificinteractive/material-calendarview/issues/149):
  save selected dates as a typed List instead of an array.
* Change: Some preformance optimizations

Version 1.0.1 *(2015-09-30)*
----------------------------

* Fix [#143](https://github.com/prolificinteractive/material-calendarview/issues/143):
 flag implementation for `showOtherDates` was a little weird and ambiguous. Clarified and added documentation.

Version 1.0.0 *(2015-09-30)*
----------------------------

* New: Added ability to select multiple dates or disable selection completely
* Change: `OnDateChangedListener` has become `OnDateSelectedListener` with different functionality
* Change: `showOtherDates` is now a integer flag for finer control over which days are shown
* Change: `CalendarDay.toString()` no longer adds one to the month

Version 0.8.1 *(2015-08-28)*
----------------------------

* Fix: Setting the topbar to invisible didn't correctly resize the view
* Change: Made `DayViewFacade` non final for unit test mocking

Version 0.8.0 *(2015-08-21)*
----------------------------

* Change: The view now responds better to layout parameters.
The functionality is similar to how `adjustViewBounds` works with ImageView,
where the view will try and take up as much space as necessary,
but we base it on tile size instead of an aspect ratio.
The exception being that if a `tileSize` is set,
that will override everything and set the view to that size.
* Fix: Use more efficent method for indexing months

Version 0.7.0 *(2015-07-09)*
----------------------------

* Fix: Being in certain timezones only showed the last week of the month
* Fix: Decorating with a custom selection drawable now works correctly
* Change: Now detect the first day of the week based off of Locale
* New: You can now change the current month without animating using `setCurrentDate(day, false)`
* Fix: Null pointer when trying to remove decorators when none have been added
* Fix: Improve Javadoc

Version 0.6.1 *(2015-06-29)*
----------------------------

* Fix: Hidding top bar will no longer distort the calendar
* Change: top bar visibility, first day of the week, and tile size are now saved during rotation

Version 0.6.0 *(2015-06-24)*
----------------------------

* New: DayViewDecorators now support disabling individual days
* New: You can set custom masks for arrows
* New: You can now set the top bar (arrows and title) as no visible

Version 0.5.0 *(2015-06-17)*
----------------------------

* Change: There are several factory methods on `CalendarDay` which should be used in place of the now deprecated constructors
* Bugfix: You can now clear the selected date. Either by passing null or calling `clearSelection()`
* New: You can now supply a custom `DayFormatter` to format day labels.

Version 0.4.0 *(2015-05-18)*
----------------------------

* Change: Revamp DayViewDecorators to be more efficent. See README for differences.

Version 0.3.2 *(2015-05-03)*
----------------------------

 * New: Added ability to change the first day of the month
 * New: Added month change listener

Version 0.3.1 *(2015-04-23)*
----------------------------

 * New: Added DayViewDecorator and DayViewFacade to allow for day decorating

Version 0.3.0 *(2015-04-15)*
----------------------------

 * Change: Namespaced library resources. Everything is now prefixed with `mcv_`
 * New: Widget resizing is now possible by setting the tileSize
     * Change: XML layouts are no longer used
     * Change: Default tile size is now an integer resource

Version 0.2.5 *(2015-03-11)*
----------------------------

 * New: Customize labels for weekdays and months

Version 0.2.4 *(2015-03-06)*
----------------------------

 * Bugfix: Full fix issue that was supposed to be fixed in 0.2.2
 * Bugfix: Text didn't center on RTL apps, default the text alignment to center

Version 0.2.2 *(2015-02-26)*
----------------------------

 * Bugfix: Using `Date` for setting anything in the library wasn't getting set correctly
 * New: Added `Date` variants for min and max setters


Version 0.2.1
-------------

 * Start of ChangeLog
