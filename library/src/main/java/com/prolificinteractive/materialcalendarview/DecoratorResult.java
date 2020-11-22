package com.prolificinteractive.materialcalendarview;

import com.prolificinteractive.materialcalendarview.listeners.DayViewDecorator;

class DecoratorResult {
  public final DayViewDecorator decorator;
  public final DayViewFacade result;

  DecoratorResult(DayViewDecorator decorator, DayViewFacade result) {
    this.decorator = decorator;
    this.result = result;
  }
}
