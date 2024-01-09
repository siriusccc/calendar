package com.calendar.backend.controller.calendar;

import com.calendar.backend.service.calendar.CalendarAddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddInfoController {
    @Autowired
    private CalendarAddService calendarAddService;

    @PostMapping("/api/calendar/addinfo/")
    public Map<String, String> addInfo(@RequestParam Map<String, String> data) {
        return calendarAddService.add(data);
    }
}
