package com.calendar.backend.service.calendar;

import com.calendar.backend.pojo.Calendar;

import java.util.List;

public interface CalendarInfoService {
    List<Calendar> getList(String date);
}
