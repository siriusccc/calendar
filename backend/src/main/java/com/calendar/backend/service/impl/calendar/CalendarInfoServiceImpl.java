package com.calendar.backend.service.impl.calendar;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.calendar.backend.mapper.CalendarMapper;
import com.calendar.backend.pojo.Calendar;
import com.calendar.backend.service.calendar.CalendarInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalendarInfoServiceImpl implements CalendarInfoService {
    @Autowired
    private CalendarMapper calendarMapper;
    @Override
    public List<Calendar> getList(String date) {
        QueryWrapper<Calendar> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date", date);
//        System.out.println(calendarMapper.selectList(queryWrapper));
        return calendarMapper.selectList(queryWrapper);
    }
}
