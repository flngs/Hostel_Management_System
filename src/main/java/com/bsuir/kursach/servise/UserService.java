package com.bsuir.kursach.servise;

import com.bsuir.kursach.entity.User;
import com.bsuir.kursach.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User getUser(String login) {
        return userRepo.findByLogin(login);
    }

    public User getUser(long id) {
        Optional<User> user = userRepo.findById(id);
        return user.orElseThrow();
    }

    public List<User> getAllUsers() {
        return (List<User>) userRepo.findAll();
    }

    // or update
    public void saveUser(User user) {
        userRepo.save(user);
    }

    public void deleteUser(long id) {
        userRepo.deleteById(id);
    }
}
