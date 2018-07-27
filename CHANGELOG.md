Change Log
==========

Version 2.0 *(TBD)*
----------------------------
* New: Changed `java.util.Calendar` in favor of `java.time.LocalDate`.
* Fix: Range selection ordering & Issues
* Fix: Timezone Issues
* Fix: Now the Month is indexed from 1 to 12 (previously 0-11 due to `java.util` api)
* Code Style Reformat
  
Version 1.6.1 *(2018-07-23)*
----------------------------
* New: A new xml parameter for choosing selection mode `app:mcv_selectionMode="single"` with the possible values being none, single, multiple and range. Default mode is still single.
* Fix: Issue with weekdays not being shown after rotation changes.

Version 1.6.0 *(2018-07-06)*
----------------------------
* New: Added api for setting long click listener for cells using `void setOnDateLongClickListener(OnDateLongClickListener longClickListener)`.
* New: Show and Hide WeekDays row in xml and Programmatically.
* New: Added api for setting content description formatter `void setDayFormatterContentDescription(DayFormatter formatter)`.
* New: Apply custom fonts through text appearance styling.
* Fix: Talkback improvements.
* Fix: Range mode selection issues.

Version 1.5.0 *(2018-05-15)*
----------------------------
* Update: Dependency has now been migrated to Jitpack. Refer to the installation section to install the latest using gradle.
* Update: Gradle has been updated to the latest.

Version 1.4.3 *(2017-02-21)*
----------------------------
* New: Added set click listener method for title
* New: Added `setSaveCurrentPosition` builder method to use the current position when switching mode
* Fix: Fixed title update when only year changed
* Fix: Fixed decorator rendering with custom tile dimensions on API Level 21
* Fix: Added missing `dynamicHeightEnabled` save in `onSaveInstanceState()`
* Fix: If we used `match_parent` for the MaterialCalendarView but the size available is not enough, the view was truncated

Version 1.4.2 *(2016-10-10)*
----------------------------
* New: match_parent is now supported by tileSize, tileWidth and tileHeight
* New: api for title animation orientation horizontal/vertical `setTitleAnimationOrientation`
* Fix: Issue with custom arrow mask being overwritten when set in xml
* Fix: Issue when clicking date outside of current year would trigger the wrong goToNext or goToPrevious method
* Fix: WeekDayFormatter would overwritten first day of the week

Version 1.4.0 *(2016-06-01)*
----------------------------
* New: Add select range functionality. Use `setSelectionMode(MaterialCalendarView.SELECTION_MODE_RANGE)` and `setOnRangeSelectedListener`
* Breaking Change: `setFirstDayOfWeek`, `setMin/MaxDate`, and `setCalendarDisplayMode` are moved to a `State` object. Call `mcv.state().edit()` to edit them and commit the changes with `commit`. See [CUSTOMIZATION_BUILDER](docs/CUSTOMIZATION_BUILDER.md) for usage details.
* Change: Updated documentation regarding 1.3.0 additions
* Fix: Current month and title pager updates correctly if minDate is set after the current month
* Fix: Week number difference calculation correctly accounts for DST offsets. Thanks Igor Levaja!
* Fix: Date formatter uses L instead of M for month (standalone instead of context sensitive)

Version 1.3.0 *(2016-05-16)*
----------------------------

* New: MCV `goToNext` and `goToPrevious` API to programmatically trigger paging
* New: Allow users to click on dates outside of current month with `setAllowClickDaysOutsideCurrentMonth`
* New: Set tile width/height separately rather than single tile size
* New: Attributes: mcv_tileWidth, mcv_tileHeight, mcv_calendarMode
* Change: `CalendarMode.WEEK` officially marked `@Experimental`, use with caution
* Change: `getTileSize` is deprecated, use `getTileWidth` and `getTileHeight`. `setTileSize` still works as a convenience method to set width and height at the same time.
* Fix: Issue with arrow not enabled when setting maxDate
* Fix: Issue with number of pages not calculated correctly with maxDate causing last page to be unreachable
* Fix: TalkBack content descriptions for pager view, forward/back arrows, and ability to set them manually
* Fix: Crash while in Week mode when `CalendarPagerAdapter#getItemPosition` is called
* Fix: Calendar Mode is retained on restore instance state
* Fix: Min/Max date range is retained on restore instance state
* Issue: Week mode - Restore instance state shows the previous week of the one that was saved
* Issue: Week mode - Some combinations of first day of week, min/max date can cause the last week not to be pagable

Version 1.2.1 *(2016-05-05)*
----------------------------

* Fix: Scrolling for nested view pagers
* Fix: Crash on switching Calendar modes with no days selected

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
* Change: Some performance optimizations

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
* Fix: Use more efficient method for indexing months

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
