package com.calendar.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.calendar.backend.pojo.Calendar;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CalendarMapper extends BaseMapper<Calendar> {
}