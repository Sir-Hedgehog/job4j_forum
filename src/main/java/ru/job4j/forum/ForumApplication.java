package ru.job4j.forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import liquibase.integration.spring.SpringLiquibase;
import javax.sql.DataSource;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 2.0
 * @since 27.08.2020
 */

@SpringBootApplication
public class ForumApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ForumApplication.class);
    }

    /**
     * Метод указывает на работу со схемами liquibase
     * @param datasource - целевая база данных
     * @return - схемы liquibase
     */

    @Bean(name = "Forum")
    public SpringLiquibase liquibase(final DataSource datasource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:liquibase-changeLog.xml");
        liquibase.setDataSource(datasource);
        return liquibase;
    }

    /**
     * Метод запуска программы
     */

    public static void main(String[] args) {
        SpringApplication.run(ForumApplication.class, args);
    }
}
