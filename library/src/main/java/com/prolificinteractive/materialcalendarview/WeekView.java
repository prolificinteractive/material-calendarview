package tc.tvcalendar.ui;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import tc.tvcalendar.ui.format.WeekDayFormatter;

public class WeekView extends LinearLayout implements View.OnClickListener{
    protected static final int DEFAULT_DAYS_IN_WEEK = 7;
    protected static final int DEFAULT_MAX_WEEKS = 1;
    protected static final int DEFAULT_MONTH_TILE_HEIGHT = DEFAULT_MAX_WEEKS + 1;


    public interface Callbacks {

        void onDateChanged(CalendarDay date);
    }

    private Callbacks callbacks;

    private final ArrayList<WeekDayView> weekDayViews = new ArrayList<>();
    private final ArrayList<DayView> monthDayViews = new ArrayList<>();

    private final Calendar calendarOfRecord = CalendarUtils.getInstance();
    private final Calendar tempWorkingCalendar = CalendarUtils.getInstance();

    private int firstDayOfWeek = Calendar.SUNDAY;

    private CalendarDay selection = null;
    private CalendarDay minDate = null;
    private CalendarDay maxDate = null;

    private boolean showOtherDates = false;
//    private boolean showInWeek = false;

    private List<DayViewDecorator> dayViewDecorators;

    public WeekView(Context context){
    	super(context);
    	setOrientation(VERTICAL);
        setClipChildren(false);
        setClipToPadding(false);

        LinearLayout row = makeRow(this);
        for (int i = 0; i < DEFAULT_DAYS_IN_WEEK; i++) {
            WeekDayView weekDayView = new WeekDayView(context);
            weekDayViews.add(weekDayView);
            row.addView(weekDayView, new LayoutParams(0, LayoutParams.MATCH_PARENT, 1f));
        }
        row = makeRow(this);
        for(int i = 0; i < DEFAULT_DAYS_IN_WEEK; i++) {
            DayView dayView = new DayView(context);
            dayView.setOnClickListener(this);
            monthDayViews.add(dayView);
            row.addView(dayView, new LayoutParams(0, LayoutParams.MATCH_PARENT, 1f));
        }
        setFirstDayOfWeek(firstDayOfWeek);
        setSelectedDate(new CalendarDay());
    }

    public void setDayViewDecorators(List<DayViewDecorator> dayViewDecorators) {
        this.dayViewDecorators = dayViewDecorators;
        updateUi();
    }

    private static LinearLayout makeRow(LinearLayout parent) {
        LinearLayout row = new LinearLayout(parent.getContext());
        row.setOrientation(HORIZONTAL);
        parent.addView(row, new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1f));
        return row;
    }

    public void setWeekDayTextAppearance(int taId) {
        for(WeekDayView weekDayView : weekDayViews) {
            weekDayView.setTextAppearance(getContext(), taId);
        }
    }

    public void setDateTextAppearance(int taId) {
        for(DayView dayView : monthDayViews) {
            dayView.setTextAppearance(getContext(), taId);
        }
    }

    public void setShowOtherDates(boolean show) {
        this.showOtherDates = show;
        updateUi();
    }

    public void setSelectionColor(int color) {
        for(DayView dayView : monthDayViews) {
            dayView.setSelectionColor(color);
        }
    }

    private Calendar resetAndGetWorkingCalendar() {
    	 CalendarUtils.copyDateTo(calendarOfRecord, tempWorkingCalendar);
         int dow = CalendarUtils.getDayOfWeek(tempWorkingCalendar);
         int delta = firstDayOfWeek - dow;
         boolean removeRow = showOtherDates ? delta >= 0 : delta > 0;
         if(removeRow) {
             delta -= DEFAULT_DAYS_IN_WEEK;
         }
         tempWorkingCalendar.add(Calendar.DATE, delta);
         return tempWorkingCalendar;
    }

    public void setFirstDayOfWeek(int dayOfWeek) {
        this.firstDayOfWeek = dayOfWeek;

        Calendar calendar = resetAndGetWorkingCalendar();
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        for(WeekDayView dayView : weekDayViews) {
            dayView.setDayOfWeek(calendar);
            calendar.add(Calendar.DATE, 1);
        }
    }

    public void setWeekDayFormatter(WeekDayFormatter formatter) {
        for(WeekDayView dayView : weekDayViews) {
            dayView.setWeekDayFormatter(formatter);
        }
    }

    public void setMinimumDate(CalendarDay minDate) {
        this.minDate = minDate;
        updateUi();
    }

    public void setMaximumDate(CalendarDay maxDate) {
        this.maxDate = maxDate;
        updateUi();
    }

    public void setDate(CalendarDay month) {
        month.copyTo(calendarOfRecord);
//        if(showInWeek) {
//            CalendarUtils.setToFirstDay(calendarOfRecord);
//        }
        updateUi();
    }

    public void setSelectedDate(CalendarDay cal) {
        selection = cal;
        updateUi();
    }

//    public void setTypeOfShow(boolean show){
//        showInWeek = show;
//        updateUi();
//    }

    protected void updateUi() {
        int ourTime = CalendarUtils.getWeek(calendarOfRecord);
        Calendar calendar = resetAndGetWorkingCalendar();
//        int currentTime = 0;
        for(DayView dayView : monthDayViews) {
            CalendarDay day = new CalendarDay(calendar);
            dayView.setDay(day);
//            currentTime = showInWeek ? day.getMonth() : day.getWeek();
            dayView.setupSelection(showOtherDates, day.isInRange(minDate, maxDate), day.getWeek() == ourTime);
            dayView.setChecked(day.equals(selection));
            applyDecorators(dayView,day);
            calendar.add(Calendar.DATE, 1);
        }
        postInvalidate();
    }

    private void applyDecorators(DayView dayView, CalendarDay day){
        if(dayViewDecorators != null) {
            DayViewFacade facade = new DayViewFacade();
            for(DayViewDecorator decorator : dayViewDecorators){
                if(decorator.shouldDecorate(day)){
                    facade.setDayView(dayView);
                    decorator.decorate(facade);
                }
            }
        }
    }

    public void setCallbacks(Callbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public void onClick(View v) {
        if(v instanceof DayView) {
            for(DayView other : monthDayViews) {
                other.setChecked(false);
            }
            DayView dayView = (DayView) v;
            dayView.setChecked(true);
            Log.i("weekDayViews",dayView.getDate()+"");
            CalendarDay date = dayView.getDate();
            if(date.equals(selection)) {
                return;
            }
            selection = date;

            if(callbacks != null) {
                callbacks.onDateChanged(dayView.getDate());
            }
        }
    }

}
