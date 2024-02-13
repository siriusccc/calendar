package com.calendar.backend.service.user;

import java.util.Map;

public interface UploadPicService {
    public Map<String, String> uploadPic(String url);
    public Map<String, String> uploadBg(String url);
}
