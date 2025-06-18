package ru.semenov.utils;

import com.zaxxer.hikari.HikariConfig;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DatasourceUtil {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5433/userdb";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "secret";
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String POOL_NAME = "HikariCPSpringDemoPool";

    public static HikariConfig setConfigParams(HikariConfig config) {
        config.setJdbcUrl(JDBC_URL);
        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);
        config.setDriverClassName(DRIVER);
        config.setMaximumPoolSize(100);
        config.setMinimumIdle(2);
        config.setPoolName(POOL_NAME);
        config.setConnectionTestQuery("SELECT 1");
        return config;
    }
}
