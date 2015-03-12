package com.prolificinteractive.materialcalendarview.sample;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateChangedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Shows off the most basic usage
 */
public class DialogsActivity extends ActionBarActivity {

    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogs);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.button_normal_dialog)
    void onNormalDialogClick() {
        new SimpleDialogFragment().show(getSupportFragmentManager(), "test-normal");
    }

    @OnClick(R.id.button_simple_dialog)
    void onSimpleCalendarDialogClick() {
        new SimpleCalendarDialogFragment().show(getSupportFragmentManager(), "test-simple-calendar");
    }

    public static class SimpleDialogFragment extends DialogFragment {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.title_activity_dialogs)
                    .setMessage("Test Dialog")
                    .setPositiveButton(android.R.string.ok, null)
                    .create();
        }
    }

    public static class SimpleCalendarDialogFragment extends DialogFragment implements OnDateChangedListener {

        private TextView textView;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.dialog_basic, container, false);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            textView = (TextView) view.findViewById(R.id.textView);

            MaterialCalendarView widget = (MaterialCalendarView) view.findViewById(R.id.calendarView);

            widget.setOnDateChangedListener(this);
        }

        @Override
        public void onDateChanged(MaterialCalendarView widget, CalendarDay date) {
            textView.setText(FORMATTER.format(date.getDate()));
        }
    }
}
