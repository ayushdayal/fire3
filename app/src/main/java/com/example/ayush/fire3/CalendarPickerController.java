package com.example.ayush.fire3;


import com.example.ayush.fire3.models.CalendarEvent;
import com.example.ayush.fire3.models.IDayItem;

import java.util.Calendar;

public interface CalendarPickerController {
    void onDaySelected(IDayItem dayItem);

    void onEventSelected(CalendarEvent event);

    void onScrollToDate(Calendar calendar);
}
