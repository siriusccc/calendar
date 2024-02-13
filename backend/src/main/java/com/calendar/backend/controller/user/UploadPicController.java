package com.calendar.backend.controller.user;

import com.calendar.backend.service.user.UploadPicService;
import com.calendar.backend.utils.UploadPic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.SchemaOutputResolver;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@RestController
public class UploadPicController {
    @Autowired
    private UploadPicService uploadPicService;
    @Value("${bg.root.path}")
    private String rootPath;
    @PostMapping("/api/user/uploadpic/")
    public Map<String, String> uploadPic(@RequestParam MultipartFile file) throws IOException {
        String picUrl = UploadPic.uploadPic(file);
        return uploadPicService.uploadPic(picUrl);
    }
    @RequestMapping("api/user/uploadbg/")
    public Map<String, String> uploadBg(@RequestParam MultipartFile file) throws IOException {
//        String picUrl = UploadPic.uploadPic(file);
        System.out.println(file.getOriginalFilename());
        String filepath = "../";
        System.out.println(rootPath + file.getOriginalFilename());
        File dest = new File(rootPath + file.getOriginalFilename());
        file.transferTo(dest);
        return uploadPicService.uploadBg(file.getOriginalFilename());
    }
}

