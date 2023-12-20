package com.calendar.backend.service.impl.user;

import com.calendar.backend.service.user.RegisterService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Override
    public Map<String, String> register(String username, String password, String confirmedPassword) {
        return null;
    }
}
