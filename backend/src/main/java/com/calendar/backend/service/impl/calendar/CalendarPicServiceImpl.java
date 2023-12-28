package com.calendar.backend.service.impl.calendar;

import com.calendar.backend.mapper.CalendarMapper;
import com.calendar.backend.pojo.Calendar;
import com.calendar.backend.service.calendar.CalendarPicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CalendarPicServiceImpl implements CalendarPicService {
    @Autowired
    private CalendarMapper calendarMapper;
    @Override
    public Map<String, String> PicUpload(String url, String date) {
        Map<String, String> map = new HashMap<>();

        Calendar calendar = new Calendar(null, date, null, url);

        calendarMapper.insert(calendar);

        map.put("error_message", "success");

        return map;
    }
}
