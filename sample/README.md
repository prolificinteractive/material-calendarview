Material CalendarView Sample App
================================

The sample app contains a mixture of implementations to help test and debug during development,
and to help new users understand how to implement some functionality.

# Basic Example
<img src="images/00-Basic Example.gif" alt="Basic Example" width="300px" />

# Calendar Selection Modes
<img src="images/01-Calendar Selection Modes.gif" alt="Calendar Selection Modes" width="300px" />

- Used XML to achieve effect: `app:mcv_selectionMode`

# Calendar with Decorators
<img src="images/02-Calendar with Decorators.gif" alt="Calendar with Decorators" width="300px" />

- Used Decorators to achieve effect: [decorator documentation](https://github.com/prolificinteractive/material-calendarview/wiki/Decorators)

# Calendar with Dynamic Modes
<img src="images/03-Calendar with Dynamic Modes.gif" alt="Calendar with Dynamic Modes" width="300px" />

- Used function to achieve month to day effect: `widget.state().edit().setCalendarDisplayMode(CalendarMode.WEEKS)`

# Disabled Days Example
<img src="images/04-Disabled Days Example.gif" alt="Disabled Days Example" width="300px" />

- Used Decorators to achieve disabled days effect: [decorator documentation](https://github.com/prolificinteractive/material-calendarview/wiki/Decorators)

# XML Customization Example
<img src="images/05-XML Customization Example.gif" alt="XML Customization Example" width="300px" />

- Used XML to achieve effect: [all customizations](https://github.com/prolificinteractive/material-calendarview/blob/master/sample/src/main/res/layout/activity_customization.xml)

# Programmatic Customization Example
- Used functions to achieve same effect as XML: [all customizations](https://github.com/prolificinteractive/material-calendarview/blob/master/sample/src/main/java/com/prolificinteractive/materialcalendarview/sample/CustomizeCodeActivity.java)

# Dynamic Setters Test
<img src="images/07-Dynamic Setters Test.gif" alt="Dynamic Setters Test" width="300px" />

- Extensive changes made programmatically: [java file](https://github.com/prolificinteractive/material-calendarview/blob/master/sample/src/main/java/com/prolificinteractive/materialcalendarview/sample/DynamicSettersActivity.java)

# Custom tile width/height
<img src="images/08-Custom tile width height.gif" alt="Custom tile width height" width="300px" />

- Used functions to achieve effect: `setTileSize`, `setTileWidth`, `setTileHeight`

# Calendar in Dialogs
<img src="images/09-Calendar in Dialogs.gif" alt="Calendar in Dialogs" width="300px" />

- Used Dialog Fragments: [java file](https://github.com/prolificinteractive/material-calendarview/blob/master/sample/src/main/java/com/prolificinteractive/materialcalendarview/sample/DialogsActivity.java)

# Multiple Basic Calendars
<img src="images/10-Multiple Basic Calendars.gif" alt="Multiple Basic Calendars" width="300px" />

# Many-Sized Calendars
<img src="images/11-Many-Sized Calendars.gif" alt="Many-Sized Calendars" width="300px" />

- Used XML to achieve effect: `android:layout_width`, `android:layout_height`, `app:mcv_tileSize`, `app:mcv_tileWidth`, `app:mcv_tileHeight`

# Calendar with Getters
<img src="images/12-Calendar with Getters.gif" alt="Calendar with Getters" width="300px" />

- Used functions to achieve effect: `getCurrentDate`, `getSelectedDate`, `getSelectedDates`, `getSelectionMode`
