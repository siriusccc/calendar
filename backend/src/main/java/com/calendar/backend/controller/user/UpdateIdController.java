package com.calendar.backend.controller.user;

import com.calendar.backend.service.user.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UpdateIdController {
    @Autowired
    private InfoService infoService;
    @PostMapping("/api/user/updateid/")
    public Map<String, String> updateId(@RequestParam Map<String, String> data){
        return infoService.updateId(data);
    }
}
