package com.prolificinteractive.materialcalendarview.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * In response to the issue comment at
 * https://github.com/prolificinteractive/material-calendarview/issues/8#issuecomment-241205704
 * , test activity with multiple MaterialCalendarViews
 */
public class MultipleViewActivity extends AppCompatActivity{
    //number of MaterialCalendarViews to display in list
    static final int NUM_ENTRIES = 3;

    @BindView(R.id.calendar_list) RecyclerView calendarList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple);
        ButterKnife.bind(this);

        //setup RecyclerView
        calendarList.setLayoutManager(new LinearLayoutManager(this));
        calendarList.setAdapter(new MultipleViewAdapter(this));
    }

    /**
     * Adapter for RecyclerView
     */
    class MultipleViewAdapter extends RecyclerView.Adapter<MultipleViewAdapter.EntryViewHolder> {
        final LayoutInflater inflater;

        MultipleViewAdapter(final Context context) {
            inflater = LayoutInflater.from(context);
        }

        @Override
        public EntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view = inflater.inflate(R.layout.calendar_list_entry, parent, false);
            return new EntryViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return NUM_ENTRIES;
        }

        @Override
        public void onBindViewHolder(EntryViewHolder holder, int position) {
            //set selected date to today
            final Calendar instance = Calendar.getInstance();
            holder.calendarView.setSelectedDate(instance.getTime());
        }

        /**
         * View holder for list entry
         */
        class EntryViewHolder extends RecyclerView.ViewHolder {
            final MaterialCalendarView calendarView;

            EntryViewHolder(final View itemView) {
                super(itemView);
                calendarView = (MaterialCalendarView) itemView.findViewById(R.id.list_entry);
            }
        }
    }
}
