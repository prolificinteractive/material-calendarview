package com.prolificinteractive.materialcalendarview.sample;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Calendar;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class DynamicSettersActivity extends AppCompatActivity {

    @BindView(R.id.calendarView)
    MaterialCalendarView widget;

    private int currentTileSize;
    private int currentTileWidth;
    private int currentTileHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_setters);
        ButterKnife.bind(this);

        currentTileSize = MaterialCalendarView.DEFAULT_TILE_SIZE_DP;
        currentTileWidth = MaterialCalendarView.DEFAULT_TILE_SIZE_DP;
        currentTileHeight = MaterialCalendarView.DEFAULT_TILE_SIZE_DP;

        widget.setOnTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Toast.makeText(DynamicSettersActivity.this, R.string.today, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    @OnClick(R.id.button_other_dates)
    void onOtherDatesClicked() {
        CharSequence[] items = {
                "Other Months",
                "Out Of Range",
                "Decorated Disabled",
                "Select days outside month"
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
                widget.allowClickDaysOutsideCurrentMonth()
        };
        new AlertDialog.Builder(this)
                .setTitle("Show Other Dates")
                .setMultiChoiceItems(items, initSelections, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (which < 3) {
                            int showOtherDates = widget.getShowOtherDates();
                            if (isChecked) {
                                //Set flag
                                showOtherDates |= itemValues[which];
                            } else {
                                //Unset flag
                                showOtherDates &= ~itemValues[which];
                            }
                            widget.setShowOtherDates(showOtherDates);
                        } else if (which == 3) {
                            widget.setAllowClickDaysOutsideCurrentMonth(isChecked);
                        }
                    }
                })
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    @OnCheckedChanged(R.id.enable_save_current_position)
    void onSaveCurrentPositionChecked(boolean checked) {
        widget.state().edit()
                .isCacheCalendarPositionEnabled(checked)
                .commit();
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

    @OnClick(R.id.button_previous)
    void onPreviousClicked() {
        widget.goToPrevious();
    }

    @OnClick(R.id.button_next)
    void onNextClicked() {
        widget.goToNext();
    }

    @OnClick(R.id.button_min_date)
    void onMinClicked() {
        showDatePickerDialog(this, widget.getMinimumDate(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                widget.state().edit()
                        .setMinimumDate(CalendarDay.from(year, monthOfYear, dayOfMonth))
                        .commit();
            }
        });
    }

    @OnClick(R.id.button_max_date)
    void onMaxClicked() {
        showDatePickerDialog(this, widget.getMaximumDate(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                widget.state().edit()
                        .setMaximumDate(CalendarDay.from(year, monthOfYear, dayOfMonth))
                        .commit();
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

    @OnClick(R.id.button_set_width_height)
    void onTileWidthHeightClicked() {
        final LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        final NumberPicker pickerWidth = new NumberPicker(this);
        pickerWidth.setMinValue(24);
        pickerWidth.setMaxValue(64);
        pickerWidth.setWrapSelectorWheel(false);
        pickerWidth.setValue(currentTileWidth);
        final NumberPicker pickerHeight = new NumberPicker(this);
        pickerHeight.setMinValue(24);
        pickerHeight.setMaxValue(64);
        pickerHeight.setWrapSelectorWheel(false);
        pickerHeight.setValue(currentTileHeight);
        layout.addView(pickerWidth);
        layout.addView(pickerHeight);
        new AlertDialog.Builder(this)
                .setView(layout)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        currentTileWidth = pickerWidth.getValue();
                        currentTileHeight = pickerHeight.getValue();
                        widget.setTileSize(-1);
                        widget.setTileWidthDp(currentTileWidth);
                        widget.setTileHeightDp(currentTileHeight);
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
                "Multiple Dates",
                "Range of Dates"
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

    @OnClick(R.id.button_change_orientation)
    void onButtonChangeOrientation() {
        widget.setTitleAnimationOrientation(
                widget.getTitleAnimationOrientation() == MaterialCalendarView.VERTICAL
                        ? MaterialCalendarView.HORIZONTAL
                        : MaterialCalendarView.VERTICAL);

        Toast.makeText(this,
                widget.getTitleAnimationOrientation() == MaterialCalendarView.VERTICAL
                        ? "Vertical"
                        : "Horizontal",
                Toast.LENGTH_SHORT).show();
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
        widget.state().edit()
                .setFirstDayOfWeek(DAYS_OF_WEEK[index])
                .commit();

    }

    @OnClick(R.id.button_weeks)
    public void onSetWeekMode() {
        widget.state().edit()
                .setCalendarDisplayMode(CalendarMode.WEEKS)
                .commit();
    }

    @OnClick(R.id.button_months)
    public void onSetMonthMode() {
        widget.state().edit()
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();
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
