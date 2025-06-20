package ru.semenov.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.semenov.dao.UserDao;
import ru.semenov.model.User;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User createUser(String username) {
        User user = new User(null, username);
        return userDao.createUser(user);
    }

    public Optional<User> getUser(Long id) {
        return userDao.findById(id);
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public void deleteUser(Long id) {
        userDao.delete(id);
    }
}
