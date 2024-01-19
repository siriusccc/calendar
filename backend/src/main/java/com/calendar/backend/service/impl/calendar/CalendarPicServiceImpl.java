package com.calendar.backend.service.impl.calendar;

import com.calendar.backend.mapper.CalendarMapper;
import com.calendar.backend.pojo.Calendar;
import com.calendar.backend.pojo.User;
import com.calendar.backend.service.calendar.CalendarPicService;
import com.calendar.backend.service.impl.util.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class CalendarPicServiceImpl implements CalendarPicService {
    @Autowired
    private CalendarMapper calendarMapper;
    @Override
    public Map<String, String> PicUpload(String url, String date) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        Map<String, String> map = new HashMap<>();

        Calendar calendar = new Calendar(null, date, null, url, user.getId(), new Date());

        calendarMapper.insert(calendar);

        map.put("error_message", "success");

        return map;
    }
}
