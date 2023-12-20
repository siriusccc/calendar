package com.calendar.backend.controller.user;

import com.calendar.backend.service.user.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class InfoController {
    @Autowired
    private InfoService InfoService;

    @GetMapping("/user/info/")
    public Map<String, String> getInfo(){
        return InfoService.getInfo();
    }
}
