package ru.semenov.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.semenov.model.User;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Repository
@Slf4j
public class UserDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<User> userRowMapper = ((rs, rowNum) ->
            new User(rs.getLong("id"), rs.getString("username")));

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS users (
                     id BIGSERIAL PRIMARY KEY,
                    username VARCHAR(255) UNIQUE NOT NULL
                )
                """);

        log.info("Таблица users была создана при инициализации в конструкторе");
    }

    public User createUser(User user) {
        var sql = "INSERT INTO users (username) VALUES (?)";
        var keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            var ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, user.getUsername());
            return ps;
        }, keyHolder);

        var key = keyHolder.getKey();
        if (nonNull(key)) {
            user.setId(key.longValue());
            log.info("Пользователь создан {}", user);
        } else {
            throw new DataAccessException("ID не сгенерирован") {
            };
        }
        return user;
    }

    public Optional<User> findById(Long id) {
        var sql = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.query(sql, userRowMapper, id)
                .stream()
                .findFirst();
    }

    public List<User> findAll() {
        var sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    public void delete(Long id) {
        var sql = "DELETE FROM users WHERE id = ?";
        int rows = jdbcTemplate.update(sql, id);
        if (rows > 0) {
            log.info("Удален пользователь с ID: {}", id);
        }
    }
}
