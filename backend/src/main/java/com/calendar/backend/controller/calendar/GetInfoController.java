package com.calendar.backend.controller.calendar;

import com.calendar.backend.pojo.Calendar;
import com.calendar.backend.service.calendar.CalendarInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetInfoController {
    @Autowired
    private CalendarInfoService calendarInfoService;
    @GetMapping("/calendar/getinfo/")
    public List<Calendar> getInfo(@RequestParam String date) {
        return calendarInfoService.getList(date);
    }
}
