package com.kevinsimard.hibernate.orm.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

public abstract class DatabaseUtil {

    private static DataSource dataSource;

    private static EntityManagerFactory factory;

    public static void initialize() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/example?useSSL=false");
        config.setUsername("root"); config.setPassword("root");

        dataSource = new HikariDataSource(config);

        LocalContainerEntityManagerFactoryBean bean =
            new LocalContainerEntityManagerFactoryBean();

        bean.setDataSource(dataSource);
        bean.afterPropertiesSet();

        factory = bean.getObject();
    }

    public static EntityManager entityManager() {
        if (factory == null) initialize();

        return factory.createEntityManager();
    }

    public static DSLContext dslContext() {
        return DSL.using(dataSource, SQLDialect.MYSQL);
    }
}
