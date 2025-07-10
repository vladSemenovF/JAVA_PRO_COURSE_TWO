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
public class UserService {
    private final UserRepository repo;

    public User createUser(String username){
        var user = new User();
        user.setUsername(username);
        return repo.save(user);
    }

    public User getUser(Long id){
        return repo.findById(id)
                .orElseThrow(RuntimeException::new);
    }

    public List<User> getAllUsers(){
        return repo.findAll();
    }

    public void deleteUser(Long id){
        repo.deleteById(id);
    }
}
