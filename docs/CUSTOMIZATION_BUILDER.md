State builder
=============

Certain parameters are only modifiable using the state builder of the `MaterialCalendarView`.
Using the builder prevents from updating the view each time one of the setters is called. The view is updated when calling `Builder#commit()` and that improve performances.
Previously, the fields could be customize using casual setters.

Here is a concrete example of how to use the builder:

```java
mcv.state().edit()
   .setFirstDayOfWeek(Calendar.WEDNESDAY)
   .setMinimumDate(CalendarDay.from(2016, 4, 3))
   .setMaximumDate(CalendarDay.from(2016, 5, 12))
   .setCalendarDisplayMode(CalendarMode.WEEKS)
   .setSaveCurrentPosition(true)
   .commit();
```

## state.edit() vs newState()

Using `mcv.state().edit()` will preserve the current state of the `MaterialCalendarView` while `mcv.newState()` will initialize the builder with new parameters.
Only the fields that are modifiable using the builder can be reset or edit. Here is the list of the fields:

- First Day Of Week
- Minimum Date
- Maximum Date
- Calendar Display Mode

As an example, if you are setting `firstDayOfWeek` inside your xml, and you want to preserve the field when using the builder, you should use `state.edit()`.
However if you don't want to preserve any current parameters from the list above, use `newState()`. In most cases `state.edit()` should be the right method to use.

### First Day Of The Week

The default first day of the week is Sunday. You can set a custom day of the week by setting `mcv_firstDayOfWeek` in xml, or by calling `setFirstDayOfWeek()`.
The xml attribute is an enum of `sunday` through `saturday` and `setFirstDayOfWeek()` accepts values from `java.util.Calendar` such as `Calendar.MONDAY`.


### Date Ranges

By default, the calendar displays months for 200 years before today and 200 years after.
You can specify different minimum and maximum dates by calling `setMinimumDate(CalendarDay)` and `setMaximumDate(CalendarDay)`.
Passing `null` will reset back to the default 200 years.
There are also convenience methods that accept a `Calendar` or a `Date` object and convert them to a `CalendarDay` using the relevant `CalendarDay.from()` factory method.

### Calendar Display Mode

`MaterialCalendarView` propose two display modes: weekly and monthly. You can set the display mode in your xml using the attribute `mcv_calendarMode` with `month` for monthly mode, or `week` for weekly mode.
You can also use the builder `setCalendarDisplayMode(CalendarMode)` parameter.

It is **important** to note that the `CalendarMode.WEEKS` is still experimental.

### Save current position between week and month mode

`SaveCurrentPosition` is set to false by default. When switching between week and month mode, the view redirect you to the today's date. 

When `setSaveCurrentPosition` is set to `true`, the calendar will stay on the current position or last selected date.