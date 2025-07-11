package ru.semenov.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationConfiguration {

    @Value("${jdbc.url}")
    private String jdbcUrl;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;
    @Value("${jdbc.driver}")
    private String driver;
    @Value("${hikari.pool.name}")
    private String poolName;
    @Value("${hikari.maximum.pool.size}")
    private int maxPoolSize;
    @Value("${hikari.minimum.idle}")
    private int minIdle;
    @Value("${hikari.connection.test.query}")
    private String testQuery;

    @Bean
    public DataSource dataSource() {
        var config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);
        config.setPoolName(poolName);
        config.setMaximumPoolSize(maxPoolSize);
        config.setMinimumIdle(minIdle);
        config.setConnectionTestQuery(testQuery);
        return new HikariDataSource(config);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
