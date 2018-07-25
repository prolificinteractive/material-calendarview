package com.prolificinteractive.materialcalendarview.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import java.util.List;

/**
 * Because the calendar has a lot of getters method, this activity is here to demonstrate what each
 * getter is returning. For more information, make sure to check the documentation.
 */
public class GettersActivity extends AppCompatActivity {
  public static final CharSequence[] ITEMS =
      new CharSequence[] { "NONE", "SINGLE", "MULTIPLE", "RANGE" };

  @BindView(R.id.calendarView) MaterialCalendarView widget;

  @Override protected void onCreate(@Nullable final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_getters);
    ButterKnife.bind(this);
  }

  @OnCheckedChanged(R.id.calendar_mode)
  void onCalendarModeChanged(boolean checked) {
    final CalendarMode mode = checked ? CalendarMode.WEEKS : CalendarMode.MONTHS;
    widget.state().edit().setCalendarDisplayMode(mode).commit();
  }

  @OnClick(R.id.button_selection_mode) void onChangeSelectionMode() {
    new AlertDialog.Builder(this)
        .setTitle("Selection Mode")
        .setSingleChoiceItems(ITEMS, widget.getSelectionMode(), (dialog, which) -> {
          widget.setSelectionMode(which);
          dialog.dismiss();
        })
        .show();
  }

  @OnClick(R.id.get_current_date) public void getCurrentDatesClick(final View v) {
    Toast.makeText(this, widget.getCurrentDate().toString(), Toast.LENGTH_SHORT).show();
    Log.e("GettersActivity", widget.getCurrentDate().toString());
  }

  @OnClick(R.id.get_selected_date) public void getSelectedDatesClick(final View v) {
    final CalendarDay selectedDate = widget.getSelectedDate();
    if (selectedDate != null) {
      Toast.makeText(this, selectedDate.toString(), Toast.LENGTH_SHORT).show();
      Log.e("GettersActivity", selectedDate.toString());
    } else {
      Toast.makeText(this, "No Selection", Toast.LENGTH_SHORT).show();
    }
  }

  @OnClick(R.id.get_selected_dates) public void getSelectedDateClick(final View v) {
    final List<CalendarDay> selectedDates = widget.getSelectedDates();
    if (!selectedDates.isEmpty()) {
      Toast.makeText(this, selectedDates.toString(), Toast.LENGTH_SHORT).show();
      Log.e("GettersActivity", selectedDates.toString());
    } else {
      Toast.makeText(this, "No Selection", Toast.LENGTH_SHORT).show();
    }
  }

  @OnClick(R.id.get_selection_mode) public void getSelectionModeClick(final View v) {
    Toast.makeText(this, ITEMS[widget.getSelectionMode()], Toast.LENGTH_SHORT).show();
    Log.e("GettersActivity", ITEMS[widget.getSelectionMode()].toString());
  }
}
