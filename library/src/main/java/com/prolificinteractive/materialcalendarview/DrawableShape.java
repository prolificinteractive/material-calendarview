package com.prolificinteractive.materialcalendarview;

public enum DrawableShape {

    CIRCLE(2),
    RECTANGLE(1);

    final int selectedDateDrawableShape;

    DrawableShape(int selectedDateDrawableShape) {
        this.selectedDateDrawableShape = selectedDateDrawableShape;
    }
}