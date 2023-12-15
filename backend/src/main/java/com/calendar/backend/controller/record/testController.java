package com.calendar.backend.controller.record;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/record/")
public class testController {
    @RequestMapping("getinfo/")
    public Map<String, String> getInfo(){
        Map<String, String> map = new HashMap<>();
        map.put("name", "1111");
        map.put("age", "2222");
        return map;
    }
}
