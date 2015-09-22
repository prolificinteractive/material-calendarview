DayViewDecorators
=================

The decorator API is a flexible way to customize individual days.
Specifically, it allows you to:

* Set custom backgrounds
* Set custom selectors
* Apply spans to the entire day's text
    * We provide `DotSpan` which will draw a dot centered below the text
* Set dates as disabled

This doc will explain how the API works and examples of how to use it.

## How It Works

A `DayViewDecorator` is an interface that has only two methods you need to implement, `shouldDecorate(CalendarDay)` and `decorate(DayViewFacade)`.
`shouldDecorate()` is called for every date in the calendar to determine if the decorator should be applied to that date.
`decorate()` is called only one time to gather the customizations used for this decorator.
This is so we can cache the decorations and efficiently apply them to many days.

The `decorate()` method provides you with a `DayViewFacade` that has four methods to allow decoration:

1. `setBackgroundDrawable(Drawable)`
    * You can set a drawable to draw behind everything else.
    * This also responds to state changes.
2. `setSelectionDrawable(Drawable)`
    * This customizes the selection indicator.
3. `addSpan(Object)`
    * Allows you to set a span on the entire day label.
    * We provide a `DotSpan` that draws a dot centered below the label.
    * For an introduction to spans, see [this article](http://androidcocktail.blogspot.com/2014/03/android-spannablestring-example.html).
    * If you want to learn more about custom spans, check out [this article](http://flavienlaurent.com/blog/2014/01/31/spans/).
    * The span is set using `setSpan(yourSpan, 0, label.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);`
4. `setDaysDisabled(boolean)`
    * Allows you to disable and re-enable days.
    * This will not affect minimum and maximum dates.
    * Days decorated as disabled can be re-enabled with other decorators.

If one of your decorators changes after it has been added to the calendar view,
make sure you call `invalidateDecorators()` to have those changes reflected.
The decorators are automatically invalidated when you add or remove decorators from the view.

To add a decorator to the calendar, you can call `addDecorator()`.
The order that decorators are added are the order in which they will be applied.
You can remove decorators by calling `removeDecorator()` or `removeDecorators()`.

When implementing a `DayViewDecorator`, make sure that they are as efficient as possible.
Remember that `shouldDecorate()` needs to be called 42 times for each month view.
An easy way to be more efficient is to convert your data to `CalendarDay`s outside of `shouldDecorate()`.

## Responding To State

If you provide custom drawables, make sure they respond to touches and states.
Read more in the [custom selector documentation](CUSTOM_SELECTORS.md).

## Examples

This section details some example uses.
You can also check out the sample app's `BasicActivityDecorated` activity for some examples.

### Events

Here is a simple example decorator that will draw a dot under a set of dates.

```java
public class EventDecorator implements DayViewDecorator {

    private final int color;
    private final HashSet<CalendarDay> dates;

    public EventDecorator(int color, Collection<CalendarDay> dates) {
        this.color = color;
        this.dates = new HashSet<>(dates);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(5, color));
    }
}
```
