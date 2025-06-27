package ru.semenov.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.semenov.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
