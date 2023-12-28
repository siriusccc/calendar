package com.calendar.backend.controller.calendar;

import com.calendar.backend.service.calendar.CalendarPicService;
import com.calendar.backend.utils.UploadPic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class PicUploadController {
    @Autowired
    private CalendarPicService calendarPicService;

    @PostMapping("/calendar/upload/")
    public Map<String, String> PicUpload(@RequestParam("file") MultipartFile file,
                                         @RequestParam("date") String date) throws IOException {
        String picUrl = UploadPic.uploadPic(file);
        System.out.println("date:"+date);
        System.out.println(picUrl);
        Map<String, String> map = new HashMap<>();
        map.put("date", date);
        map.put("file", picUrl);
//        return map;
        return calendarPicService.PicUpload(picUrl, date);
    }
}
