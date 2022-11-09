package com.bsuir.kursach.repository;

import com.bsuir.kursach.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
    User findByLogin(String login);
}
