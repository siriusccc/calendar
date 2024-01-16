package com.calendar.backend.service.calendar;

import java.util.Map;

public interface CalendarAddService {
    Map<String, String> add(Map<String, String> data);
    Map<String, String> addContent(Map<String, String> data);
    Map<String, String> removePic(Map<String, String> data);
}
