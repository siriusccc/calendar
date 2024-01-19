package com.calendar.backend.service.impl.user;

import com.calendar.backend.mapper.UserMapper;
import com.calendar.backend.pojo.User;
import com.calendar.backend.service.impl.util.UserDetailsImpl;
import com.calendar.backend.service.user.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InfoServiceImpl implements InfoService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public Map<String, String> getInfo() {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();

        User user = loginUser.getUser();

        Map<String, String> map = new HashMap<>();
        map.put("error_message", "success");
        map.put("id", user.getId().toString());
        map.put("username", user.getUsername());
        map.put("avatar", user.getAvatar());
        return map;
    }

    @Override
    public Map<String, String> updateId(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        String username = data.get("username");

        Map<String, String> map = new HashMap<>();
        if (username == null || username.length() == 0){
            map.put("error_message","名称不能为空");
            return map;
        }
        if (username.length() > 20){
            map.put("error_message","名称不能太长");
            return map;
        }

        User new_user = new User(
                user.getId(),
                username,
                user.getPassword(),
                user.getAvatar(),
                user.getLevel()
        );

        userMapper.updateById(new_user);

        map.put("error_message", "success");
        map.put("data", username);
        return map;
    }
}
