Custom Selectors
================

This doc describes how to create custom stateful drawables and colors to be used in the view.

## Stateful-ness in Material CalendarView

There are several places in the view where reacting to state would be useful.
The most obvious is the date selection indicator, but state can also be represented with custom
backgrounds set by decorators or text colors.

The state that is used to represent selected days is `android:checked`.
Days can also be disabled, which is represented with `android:enabled`.

### Drawables

If you want to set a custom selector or background, you'll need to make sure your drawable responds to state.
This is most easily done by using a `selector` in your xml or use `StateListDrawable`.

Here is a basic example of a selector:

```xml
<selector xmlns:android="http://schemas.android.com/apk/res/android"
    android:exitFadeDuration="@android:integer/config_shortAnimTime">

    <item
        android:state_checked="true"
        android:drawable="@drawable/ic_my_selector"
        />

    <item
        android:state_pressed="true"
        android:drawable="@drawable/ic_my_selector"
        />

    <item android:drawable="@android:color/transparent" />
</selector>
```

This is the (truncated) code used to generate the default selector:

 ```java
private static Drawable generateSelector() {
    StateListDrawable drawable = new StateListDrawable();
    drawable.setExitFadeDuration(...);
    drawable.addState(new int[]{android.R.attr.state_checked}, generateCircleDrawable(...));
    drawable.addState(new int[]{android.R.attr.state_pressed}, generateCircleDrawable(...));
    drawable.addState(new int[]{}, generateCircleDrawable(Color.TRANSPARENT));
    return drawable;
}
 ```

### Text Color

In some cases, the default text color isn't the one you want.
In this case, you will need to create a custom text appearance style that sets the text color you need.
You do this by setting `mcv_dateTextAppearance` or calling `setDateTextAppearance()`.
This library provides a text appearance style `TextAppearance.MaterialCalendarWidget.Date`, for you to extend.
And it also has two stateful colors, `mcv_text_date_dark` or `mcv_text_date_light` (which is the default).

Here's an example for a dark theme and a light selector:

```xml
<style name="CustomTextAppearance" parent="TextAppearance.MaterialCalendarWidget.Date">
    <item name="android:textColor">@color/mcv_text_date_dark</item>
</style>
```

Here's an example for a dark theme and a dark selector:

```xml
<!-- In res/values/styles.xml -->
<style name="CustomTextAppearance" parent="TextAppearance.MaterialCalendarWidget.Date">
    <item name="android:textColor">@color/my_date_text_color</item>
</style>
```
```xml
<!-- In res/color/my_date_text_color.xml -->
<selector xmlns:android="http://schemas.android.com/apk/res/android">

    <item
        android:state_checked="true"
        android:color="@android:color/white"
        />

    <item
        android:state_pressed="true"
        android:color="@android:color/white"
        />

    <item
        android:state_enabled="false"
        android:color="#808080"
        />

    <item android:color="@android:color/white" />

</selector>
```
