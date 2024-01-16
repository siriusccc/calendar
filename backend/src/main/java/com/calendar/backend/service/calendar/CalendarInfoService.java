package com.calendar.backend.service.calendar;

import com.calendar.backend.pojo.Calendar;

import java.util.List;
import java.util.Map;

public interface CalendarInfoService {
    List<Calendar> getList(String date);
}
