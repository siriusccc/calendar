package com.calendar.backend.service.impl.calendar;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.calendar.backend.mapper.CalendarMapper;
import com.calendar.backend.pojo.Calendar;
import com.calendar.backend.pojo.User;
import com.calendar.backend.service.calendar.CalendarAddService;
import com.calendar.backend.service.impl.util.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class CalendarAddServiceImpl implements CalendarAddService {
    @Autowired
    public CalendarMapper calendarMapper;
    @Override
    public Map<String, String> add(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        String date = data.get("date");
        String content = data.get("content");

        Map<String, String> map = new HashMap<>();

        if (date == null || date.isEmpty()){
            map.put("error_message","日期不能为空");
            return map;
        }

        if (content == null || content.length() == 0){
            map.put("error_message","内容不能为空");
            return map;
        }

        Calendar calendar = new Calendar(null, date, content, null, user.getId(), new Date());
        calendarMapper.insert(calendar);
        map.put("error_message", "success");

        return map;
    }

    @Override
    public Map<String, String> addContent(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        String content = data.get("content");
        String picUrl = data.get("picurl");

        QueryWrapper<Calendar> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("picurl", picUrl);

        Calendar calendar = calendarMapper.selectOne(queryWrapper);

        Calendar new_calendar = new Calendar(
            calendar.getId(),
            calendar.getDate(),
            content,
            picUrl,
            user.getId(),
            new Date()
        );

        calendarMapper.updateById(new_calendar);

        Map<String, String> map = new HashMap<>();
        map.put("error_message", "success");

        return map;
    }

    @Override
    public Map<String, String> removePic(Map<String, String> data) {
        String picUrl = data.get("picurl");

        QueryWrapper<Calendar> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("picurl", picUrl);

        calendarMapper.delete(queryWrapper);

        Map<String, String> map = new HashMap<>();
        map.put("error_message", "success");

        return map;
    }


}
