package ru.semenov.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.semenov.dao.UserRepository;
import ru.semenov.model.User;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceV2 {
    private final UserRepository repo;

    public User createUser(String username){
        var user = new User();
        user.setUsername(username);
        return repo.save(user);
    }

    public Optional<User> getUser(Long id){
        return repo.findById(id);
    }

    public List<User> getAllUsers(){
        return repo.findAll();
    }

    public void deleteUser(Long id){
        repo.deleteById(id);
    }
}
