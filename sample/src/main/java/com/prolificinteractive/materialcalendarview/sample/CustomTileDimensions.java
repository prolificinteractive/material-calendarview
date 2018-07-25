package com.prolificinteractive.materialcalendarview.sample;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

/**
 * Created by maragues on 17/06/16.
 */
public class CustomTileDimensions extends AppCompatActivity {

  @BindView(R.id.calendarView)
  MaterialCalendarView widget;

  private int currentTileWidth;
  private int currentTileHeight;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_custom_tile);
    ButterKnife.bind(this);

    currentTileWidth = MaterialCalendarView.DEFAULT_TILE_SIZE_DP;
    currentTileHeight = MaterialCalendarView.DEFAULT_TILE_SIZE_DP;

    widget.addDecorator(new TodayDecorator());
  }

  @OnClick(R.id.custom_tile_match_parent)
  public void onMatchParentClick() {
    widget.setTileSize(LinearLayout.LayoutParams.MATCH_PARENT);
  }

  @OnClick(R.id.custom_tile_width_match_parent)
  public void onWidthMatchParentClick() {
    widget.setTileWidth(LinearLayout.LayoutParams.MATCH_PARENT);
  }

  @OnClick(R.id.custom_tile_height_match_parent)
  public void onHeightMatchParentClick() {
    widget.setTileHeight(LinearLayout.LayoutParams.MATCH_PARENT);
  }

  @OnClick(R.id.custom_tile_width_size)
  public void onWidthClick() {
    final NumberPicker view = new NumberPicker(this);
    view.setMinValue(24);
    view.setMaxValue(64);
    view.setWrapSelectorWheel(false);
    view.setValue(currentTileWidth);
    new AlertDialog.Builder(this)
        .setView(view)
        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(@NonNull DialogInterface dialog, int which) {
            currentTileWidth = view.getValue();
            widget.setTileWidthDp(currentTileWidth);
          }
        })
        .show();
  }

  @OnClick(R.id.custom_tile_height_size)
  public void onHeightClick() {
    final NumberPicker view = new NumberPicker(this);
    view.setMinValue(24);
    view.setMaxValue(64);
    view.setWrapSelectorWheel(false);
    view.setValue(currentTileHeight);
    new AlertDialog.Builder(this)
        .setView(view)
        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(@NonNull DialogInterface dialog, int which) {
            currentTileHeight = view.getValue();
            widget.setTileHeightDp(currentTileHeight);
          }
        })
        .show();
  }

  private class TodayDecorator implements DayViewDecorator {

    private final CalendarDay today;
    private final Drawable backgroundDrawable;

    public TodayDecorator() {
      today = CalendarDay.today();
      backgroundDrawable = getResources().getDrawable(R.drawable.today_circle_background);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
      return today.equals(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
      view.setBackgroundDrawable(backgroundDrawable);
    }
  }
}
