package ru.job4j.forum.configuration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 1.0
 * @since 10.08.2020
 */

@Configuration
@PropertySource("classpath:application.properties")
@EnableJpaRepositories("ru.job4j.forum.repository")
@EnableTransactionManagement
public class DataConfig {

    /**
     * Метод формирует пул соединений для выбранной в конфигурационном файле базы данных
     * @param url      - url базы данных
     * @param username - имя пользователя
     * @param password - пароль
     * @return - пул соединений
     */

    @Primary
    @Bean
    public DataSource getDataSource(@Value("${spring.datasource.driver-class-name}") String driver,
                                    @Value("${spring.datasource.url}") String url,
                                    @Value("${spring.datasource.username}") String username,
                                    @Value("${spring.datasource.password}") String password) {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(driver);
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        return ds;
    }
}
