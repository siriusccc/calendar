package com.calendar.backend.controller.calendar;

import com.calendar.backend.service.calendar.CalendarAddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DelPicController {
    @Autowired
    private CalendarAddService calendarAddService;

    @PostMapping("/api/calendar/delpic/")
    public Map<String, String> delpic(@RequestParam Map<String, String> data){
        return calendarAddService.removePic(data);
    }

}
