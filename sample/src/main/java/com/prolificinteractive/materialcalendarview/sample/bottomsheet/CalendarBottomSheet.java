package com.prolificinteractive.materialcalendarview.sample.bottomsheet;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.sample.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author : hafiq on 01/10/2017.
 */

public class CalendarBottomSheet extends BottomSheetDialogFragment implements OnDateSelectedListener {

    @SuppressLint("SimpleDateFormat")
    private static final DateFormat FORMATTER = new SimpleDateFormat("dd-MM-yyyy");

    @BindView(R.id.calendarView)
    MaterialCalendarView widget;
    @BindView(R.id.bottom_sheet)
    LinearLayout bottomSheet;

    String type;

    public static final String START_DATE = "start_date";
    public static final String END_DATE = "end_date";
    public static final String TYPE = "type";

    CalendarDay maxDate = null;
    CalendarDay minDate = null;

    Unbinder unbinder;

    private FragmentListener.DateChooseCallback resultCallback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static CalendarBottomSheet instance(String type) {
        CalendarBottomSheet fra = new CalendarBottomSheet();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        fra.setArguments(bundle);
        return fra;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        View contentView = View.inflate(getContext(), R.layout.sheet_calendar, null);
        dialog.setContentView(contentView);
        unbinder = ButterKnife.bind(this, contentView);

        type = getArguments().getString(TYPE);


        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                        dismiss();
                    }
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                }
            });

            setCalendar();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        resultCallback = (FragmentListener.DateChooseCallback) context;

    }

    private void setCalendar() {
        widget.setSelectionColor(ContextCompat.getColor(getContext(), android.R.color.holo_blue_bright));

        Calendar calendar = Calendar.getInstance();
        widget.setSelectedDate(calendar.getTime());
        widget.setOnDateChangedListener(this);

    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        if (resultCallback != null) {
            resultCallback.onDateResult(this.type, date, FORMATTER.format(date.getDate()));
            dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

