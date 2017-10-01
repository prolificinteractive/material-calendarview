package com.prolificinteractive.materialcalendarview.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.sample.bottomsheet.CalendarBottomSheet;
import com.prolificinteractive.materialcalendarview.sample.bottomsheet.FragmentListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author : hafiq on 12/04/2017.
 */

public class BasicBottomSheetActivity extends AppCompatActivity implements FragmentListener.DateChooseCallback {

    @BindView(R.id.first_text)
    TextView firstText;
    @BindView(R.id.second_text)
    TextView secondText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_bottom_sheet);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.first_text)
    protected void chooseStartDate() {
        CalendarBottomSheet calendarBottomSheet = CalendarBottomSheet.instance(CalendarBottomSheet.START_DATE);
        calendarBottomSheet.show(getSupportFragmentManager(), "start_date");
    }

    @OnClick(R.id.second_text)
    protected void chooseEndDate() {
        CalendarBottomSheet calendarBottomSheet = CalendarBottomSheet.instance(CalendarBottomSheet.END_DATE);
        calendarBottomSheet.show(getSupportFragmentManager(), "end_date");
    }

    @Override
    public void onDateResult(String id, CalendarDay day, String value) {
        switch (id) {
            case CalendarBottomSheet.START_DATE :
                firstText.setText(value);
                break;
            case CalendarBottomSheet.END_DATE :
                secondText.setText(value);
                break;
            default:
                break;
        }
    }
}
