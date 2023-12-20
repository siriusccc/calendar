package com.calendar.backend.controller.user;

import com.calendar.backend.service.user.UploadPicService;
import com.calendar.backend.utils.UploadPic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
public class UploadPicController {
    @Autowired
    private UploadPicService uploadPicService;

    @PostMapping("/user/uploadpic/")
    public Map<String, String> uploadPic(@RequestParam MultipartFile file) throws IOException {
        String picUrl = UploadPic.uploadPic(file);
        return uploadPicService.uploadPic(picUrl);
    }
}

