package com.prolificinteractive.materialcalendarview.sample;

import android.app.Application;
import com.jakewharton.threetenabp.AndroidThreeTen;

public class BaseApp extends Application {
  @Override public void onCreate() {
    super.onCreate();
    AndroidThreeTen.init(this);
  }
}
