package com.prolificinteractive.materialcalendarview.sample;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.UniqueDecorator;
import com.prolificinteractive.materialcalendarview.sample.decorators.HighlightWeekendsDecorator;
import com.prolificinteractive.materialcalendarview.sample.decorators.OneDayUniqueDecorator;

import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.Executors;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Shows off the most basic usage
 */
public class UniqueActivity extends AppCompatActivity implements OnDateSelectedListener {

    @Bind(R.id.calendarView)
    MaterialCalendarView widget;
    private OneDayUniqueDecorator oneDayDecorator;
    private HashMap<CalendarDay, String> dates;
    private UniqueDecorator uDecor;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unique);
        ButterKnife.bind(this);

        widget.setOnDateChangedListener(this);
        widget.setShowOtherDates(MaterialCalendarView.SHOW_NONE);

        calendar = Calendar.getInstance();

        calendar.set(calendar.get(Calendar.YEAR), Calendar.JANUARY, 1);
        widget.setMinimumDate(calendar.getTime());

        calendar.set(calendar.get(Calendar.YEAR), Calendar.DECEMBER, 31);
        widget.setMaximumDate(calendar.getTime());
        widget.setTileSizeDp(50);
        widget.setDistanceBetweenRowsDP(20);

        oneDayDecorator = new OneDayUniqueDecorator(this, Color.RED, Color.WHITE);
        widget.addDecorators(new HighlightWeekendsDecorator(), oneDayDecorator);
        oneDayDecorator.setDate(calendar.getTime());

        new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        //If you change a decorate, you need to invalidate decorators
        oneDayDecorator.setDate(date.getDate());
        if (uDecor != null)
            uDecor.setDate(date.getDate());
        widget.invalidateDecorators();
    }

    /**
     * Simulate an API call to show how to add decorators
     */
    private class ApiSimulator extends AsyncTask<Void, Void, HashMap<CalendarDay, String>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            widget.setEnabled(false);
        }

        @Override
        protected HashMap<CalendarDay, String> doInBackground(@NonNull Void... voids) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -2);
            dates = new HashMap<>();
            for (int i = 0; i < 30; i++) {
                CalendarDay day = CalendarDay.from(calendar);
                dates.put(day, i + "");
                calendar.add(Calendar.DATE, 5);
            }

            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull HashMap<CalendarDay, String> calendarDays) {
            super.onPostExecute(calendarDays);
            oneDayDecorator.setMap(calendarDays);
            if (isFinishing()) {
                return;
            }

            uDecor = new UniqueDecorator(UniqueActivity.this, Color.RED, calendarDays);
            uDecor.setTextSizeSP(12);
            uDecor.setTextUnderline(true);
            uDecor.setSpacingDP(12);
            widget.addDecorator(uDecor);

            widget.setEnabled(true);
        }
    }
}
