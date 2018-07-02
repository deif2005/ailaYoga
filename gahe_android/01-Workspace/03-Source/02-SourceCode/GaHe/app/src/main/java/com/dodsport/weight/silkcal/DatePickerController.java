package com.dodsport.weight.silkcal;

import android.graphics.Canvas;

public interface DatePickerController {
    void onDraw(Canvas canvas);

    public abstract int getMaxYear();

	public abstract void onDayOfMonthSelected(int year, int month, int day);

    public abstract void onDateRangeSelected(final SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays);

}