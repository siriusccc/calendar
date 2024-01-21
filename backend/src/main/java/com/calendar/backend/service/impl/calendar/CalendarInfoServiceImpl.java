package com.calendar.backend.service.impl.calendar;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.calendar.backend.mapper.CalendarMapper;
import com.calendar.backend.pojo.Calendar;
import com.calendar.backend.pojo.User;
import com.calendar.backend.service.calendar.CalendarInfoService;
import com.calendar.backend.service.impl.util.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CalendarInfoServiceImpl implements CalendarInfoService {
    @Autowired
    private CalendarMapper calendarMapper;
    @Override
    public List<Calendar> getList(String date) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        QueryWrapper<Calendar> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date", date);
        int user_id = user.getId();
        int level = user.getLevel();

        List<Calendar> List = new ArrayList<>();
        List<Calendar> calendarList = calendarMapper.selectList(queryWrapper);

        for(Calendar calendar: calendarList){
            if(calendar.getUserId() == user_id || level == 0){
                List.add(calendar);
            }
        }
        return List;
    }

    @Override
    public List<Calendar> getAllList() {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        int level = user.getLevel();

        QueryWrapper<Calendar> queryWrapper = new QueryWrapper<>();

        if (level != 0){
            queryWrapper.eq("user_id", user.getId());
        } else {
            queryWrapper.allEq(null);
        }

        return calendarMapper.selectList(queryWrapper);
    }
}
