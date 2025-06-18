package ru.semenov.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import static ru.semenov.utils.DatasourceUtil.setConfigParams;

@Configuration
@ComponentScan("ru.semenov")
public class ApplicationConfiguration {

    @Bean
    public DataSource dataSource() {
        var config = new HikariConfig();

        var configWithParams = setConfigParams(config);

        return new HikariDataSource(configWithParams);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

}
