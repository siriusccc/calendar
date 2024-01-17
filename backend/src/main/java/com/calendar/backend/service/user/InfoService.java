package com.calendar.backend.service.user;

import java.util.Map;

public interface InfoService {
    public Map<String, String> getInfo();
    Map<String, String> updateId(Map<String, String> data);
}
