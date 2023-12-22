package com.calendar.backend.service.impl.user;

import com.calendar.backend.pojo.User;
import com.calendar.backend.service.impl.util.UserDetailsImpl;
import com.calendar.backend.service.user.LoginService;
import com.calendar.backend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Override
    public Map<String, String> getToken(String username, String password) {
        System.out.println(username);
        System.out.println(password);
        System.out.println("1");
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);
        System.out.println(authenticationToken);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        System.out.println(authenticate);
        System.out.println(username);
        System.out.println("2");

        UserDetailsImpl loginUser = (UserDetailsImpl) authenticate.getPrincipal();
        System.out.println(username);
        System.out.println("3");
        User user = loginUser.getUser();
        System.out.println(username);
        System.out.println("4");

        String jwt = JwtUtil.createJWT(user.getId().toString());
        System.out.println(username);
        System.out.println("5");

        Map<String, String> map = new HashMap<>();
        map.put("error_message", "success");
        map.put("token", jwt);

        System.out.println(map);

        return map;
    }
}
