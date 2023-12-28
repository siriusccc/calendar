package com.calendar.backend.service.impl.user;

import com.calendar.backend.mapper.UserMapper;
import com.calendar.backend.pojo.User;
import com.calendar.backend.service.impl.util.UserDetailsImpl;
import com.calendar.backend.service.user.UploadPicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UploadPicServiceImpl implements UploadPicService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public Map<String, String> uploadPic(String url) {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        Map<String, String> map = new HashMap<>();

        User new_user = new User(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                url
        );

        userMapper.updateById(new_user);
        map.put("error_message", "success");
        return map;
    }
}
