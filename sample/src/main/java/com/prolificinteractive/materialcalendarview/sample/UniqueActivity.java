package com.prolificinteractive.materialcalendarview.sample;

import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
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
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unique);
        ButterKnife.bind(this);

        widget.setOnDateChangedListener(this);
        widget.setShowOtherDates(MaterialCalendarView.SHOW_NONE);

        calendar = Calendar.getInstance();

        calendar.set(calendar.get(Calendar.YEAR), Calendar.JANUARY, 1);

        calendar.set(calendar.get(Calendar.YEAR), Calendar.DECEMBER, 31);
        widget.setTileHeightDp(64);
        widget.setTileWidthDp(50);

        oneDayDecorator = new OneDayUniqueDecorator(this, Color.RED, Color.WHITE);
        widget.addDecorators(oneDayDecorator);
        oneDayDecorator.setDate(calendar.getTime());

        new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        //If you change a decorate, you need to invalidate decorators
        oneDayDecorator.setDate(date.getDate());
        if (uDecor != null)
            uDecor.setDate(date.getDate());
        widget.invalidateDecorators();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Unique Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.prolificinteractive.materialcalendarview.sample/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Unique Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.prolificinteractive.materialcalendarview.sample/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
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