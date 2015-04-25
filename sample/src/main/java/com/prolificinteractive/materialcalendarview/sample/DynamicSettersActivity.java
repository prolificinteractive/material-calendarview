package com.prolificinteractive.materialcalendarview.sample;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateChangedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class DynamicSettersActivity extends ActionBarActivity implements OnDateChangedListener {

    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();

    @InjectView(R.id.textView) TextView textView;
    @InjectView(R.id.calendarView) MaterialCalendarView widget;
    private int currentTileSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_setters);
        ButterKnife.inject(this);

        currentTileSize = getResources().getInteger(R.integer.mcv_default_tile_size);

        widget.setOnDateChangedListener(this);
    }

    @Override
    public void onDateChanged(MaterialCalendarView widget, CalendarDay date) {
        textView.setText(FORMATTER.format(date.getDate()));
    }

    @OnCheckedChanged(R.id.check_other_dates) void onOtherMonthsChecked(boolean checked) {
        widget.setShowOtherDates(checked);
    }

    @OnCheckedChanged(R.id.check_text_appearance) void onTextAppearanceChecked(boolean checked) {
        if(checked) {
            widget.setHeaderTextAppearance(R.style.TextAppearance_AppCompat_Large);
            widget.setDateTextAppearance(R.style.TextAppearance_AppCompat_Medium);
            widget.setWeekDayTextAppearance(R.style.TextAppearance_AppCompat_Medium);
        }
        else {
            widget.setHeaderTextAppearance(R.style.TextAppearance_MaterialCalendarWidget_Header);
            widget.setDateTextAppearance(R.style.TextAppearance_MaterialCalendarWidget_Date);
            widget.setWeekDayTextAppearance(R.style.TextAppearance_MaterialCalendarWidget_WeekDay);
        }
        widget.setShowOtherDates(checked);
    }

    @OnClick(R.id.button_min_date) void onMinClicked() {
        showDatePickerDialog(this, widget.getMinimumDate(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                widget.setMinimumDate(new CalendarDay(year, monthOfYear, dayOfMonth));
            }
        });
    }

    @OnClick(R.id.button_max_date) void onMaxClicked() {
        showDatePickerDialog(this, widget.getMaximumDate(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                widget.setMaximumDate(new CalendarDay(year, monthOfYear, dayOfMonth));
            }
        });
    }

    @OnClick(R.id.button_selected_date) void onSelectedClicked() {
        showDatePickerDialog(this, widget.getSelectedDate(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                widget.setSelectedDate(new CalendarDay(year, monthOfYear, dayOfMonth));
            }
        });
    }

    Random random = new Random();

    @OnClick(R.id.button_set_colors) void onColorsClicked() {
        int color = Color.HSVToColor(new float[]{
            random.nextFloat() * 360,
            1f,
            0.75f
        });
        widget.setArrowColor(color);
        widget.setSelectionColor(color);
    }

    @OnClick(R.id.button_set_tile_size) void onTileSizeClicked() {
        final NumberPicker view = new NumberPicker(this);
        view.setMinValue(24);
        view.setMaxValue(64);
        view.setWrapSelectorWheel(false);
        view.setValue(currentTileSize);
        new AlertDialog.Builder(this)
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        currentTileSize = view.getValue();
                        widget.setTileSizeDp(currentTileSize);
                    }
                })
                .show();
    }

    public static void showDatePickerDialog(Context context, CalendarDay day,
        DatePickerDialog.OnDateSetListener callback) {
        if(day == null) {
            day = CalendarDay.today();
        }
        DatePickerDialog dialog = new DatePickerDialog(
            context, 0, callback, day.getYear(), day.getMonth(), day.getDay()
        );
        dialog.show();
    }
}
