package com.calendar.backend.controller.record;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/record/")
public class recordController {
    @RequestMapping("index/")
    public String index(){
        return "record/index.html";
    }
}
