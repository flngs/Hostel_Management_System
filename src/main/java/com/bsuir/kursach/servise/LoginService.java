package com.bsuir.kursach.servise;

import com.bsuir.kursach.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final UserService userService;

    @Autowired
    public LoginService(UserService userService) {
        this.userService = userService;
    }

    public User getUser(String login) {
        return userService.getUser(login);
    }

    public User loginUser(String login, String password) {
        User user = null;

        try {
            user = getUser(login);
        }catch (javax.persistence.NoResultException e) {
            return null;
        }

        if(user != null && user.getPassword().equals(password)) {
            return user;
        }

        return null;
    }
}
