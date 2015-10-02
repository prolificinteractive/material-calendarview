package com.prolificinteractive.materialcalendarview.sample;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;
import android.widget.NumberPicker;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Calendar;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class DynamicSettersActivity extends AppCompatActivity {

    @Bind(R.id.calendarView)
    MaterialCalendarView widget;

    private int currentTileSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_setters);
        ButterKnife.bind(this);

        currentTileSize = MaterialCalendarView.DEFAULT_TILE_SIZE_DP;
    }

    @OnClick(R.id.button_other_dates)
    void onOtherDatesClicked() {
        CharSequence[] items = {
                "Other Months",
                "Out Of Range",
                "Decorated Disabled"
        };
        final int[] itemValues = {
                MaterialCalendarView.SHOW_OTHER_MONTHS,
                MaterialCalendarView.SHOW_OUT_OF_RANGE,
                MaterialCalendarView.SHOW_DECORATED_DISABLED,
        };
        int showOtherDates = widget.getShowOtherDates();
        boolean[] initSelections = {
                MaterialCalendarView.showOtherMonths(showOtherDates),
                MaterialCalendarView.showOutOfRange(showOtherDates),
                MaterialCalendarView.showDecoratedDisabled(showOtherDates),
        };
        new AlertDialog.Builder(this)
                .setTitle("Show Other Dates")
                .setMultiChoiceItems(items, initSelections, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        int showOtherDates = widget.getShowOtherDates();
                        if (isChecked) {
                            //Set flag
                            showOtherDates |= itemValues[which];
                        } else {
                            //Unset flag
                            showOtherDates &= ~itemValues[which];
                        }
                        widget.setShowOtherDates(showOtherDates);
                    }
                })
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    @OnCheckedChanged(R.id.check_text_appearance)
    void onTextAppearanceChecked(boolean checked) {
        if (checked) {
            widget.setHeaderTextAppearance(R.style.TextAppearance_AppCompat_Large);
            widget.setDateTextAppearance(R.style.TextAppearance_AppCompat_Medium);
            widget.setWeekDayTextAppearance(R.style.TextAppearance_AppCompat_Medium);
        } else {
            widget.setHeaderTextAppearance(R.style.TextAppearance_MaterialCalendarWidget_Header);
            widget.setDateTextAppearance(R.style.TextAppearance_MaterialCalendarWidget_Date);
            widget.setWeekDayTextAppearance(R.style.TextAppearance_MaterialCalendarWidget_WeekDay);
        }
        widget.setShowOtherDates(checked ? MaterialCalendarView.SHOW_ALL : MaterialCalendarView.SHOW_NONE);
    }

    @OnCheckedChanged(R.id.check_page_enabled) 
    void onPageEnabledChecked(boolean checked) {
        widget.setPagingEnabled(checked);
    }

    @OnClick(R.id.button_min_date) 
    void onMinClicked() {
        showDatePickerDialog(this, widget.getMinimumDate(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                widget.setMinimumDate(CalendarDay.from(year, monthOfYear, dayOfMonth));
            }
        });
    }

    @OnClick(R.id.button_max_date)
    void onMaxClicked() {
        showDatePickerDialog(this, widget.getMaximumDate(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                widget.setMaximumDate(CalendarDay.from(year, monthOfYear, dayOfMonth));
            }
        });
    }

    @OnClick(R.id.button_selected_date)
    void onSelectedClicked() {
        showDatePickerDialog(this, widget.getSelectedDate(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                widget.setSelectedDate(CalendarDay.from(year, monthOfYear, dayOfMonth));
            }
        });
    }

    @OnClick(R.id.button_toggle_topbar)
    void onToggleTopBarClicked() {
        widget.setTopbarVisible(!widget.getTopbarVisible());
    }

    Random random = new Random();

    @OnClick(R.id.button_set_colors)
    void onColorsClicked() {
        int color = Color.HSVToColor(new float[]{
                random.nextFloat() * 360,
                1f,
                0.75f
        });
        widget.setArrowColor(color);
        widget.setSelectionColor(color);
    }

    @OnClick(R.id.button_set_tile_size)
    void onTileSizeClicked() {
        final NumberPicker view = new NumberPicker(this);
        view.setMinValue(24);
        view.setMaxValue(64);
        view.setWrapSelectorWheel(false);
        view.setValue(currentTileSize);
        new AlertDialog.Builder(this)
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        currentTileSize = view.getValue();
                        widget.setTileSizeDp(currentTileSize);
                    }
                })
                .show();
    }

    @OnClick(R.id.button_clear_selection)
    void onClearSelection() {
        widget.clearSelection();
    }

    @OnClick(R.id.button_selection_mode)
    void onChangeSelectionMode() {
        CharSequence[] items = {
                "No Selection",
                "Single Date",
                "Multiple Dates"
        };
        new AlertDialog.Builder(this)
                .setTitle("Selection Mode")
                .setSingleChoiceItems(items, widget.getSelectionMode(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        widget.setSelectionMode(which);
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private static final int[] DAYS_OF_WEEK = {
            Calendar.SUNDAY,
            Calendar.MONDAY,
            Calendar.TUESDAY,
            Calendar.WEDNESDAY,
            Calendar.THURSDAY,
            Calendar.FRIDAY,
            Calendar.SATURDAY,
    };

    @OnClick(R.id.button_set_first_day)
    void onFirstDayOfWeekClicked() {
        int index = random.nextInt(DAYS_OF_WEEK.length);
        widget.setFirstDayOfWeek(DAYS_OF_WEEK[index]);
    }

    public static void showDatePickerDialog(Context context, CalendarDay day,
                                            DatePickerDialog.OnDateSetListener callback) {
        if (day == null) {
            day = CalendarDay.today();
        }
        DatePickerDialog dialog = new DatePickerDialog(
                context, 0, callback, day.getYear(), day.getMonth(), day.getDay()
        );
        dialog.show();
    }
}
