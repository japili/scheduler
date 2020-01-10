/*
 * Copyright 2020
 */

package com.exercise.scheduler.persistence;

import com.exercise.scheduler.persistence.dao.ProjectRepository;
import com.exercise.scheduler.persistence.dao.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;


/**
 * Created by japili on 06/01/2020.
 */
@Configuration
@PropertySource("test.db.properties")
@EnableJpaRepositories(basePackageClasses = {
        ProjectRepository.class,
        TaskRepository.class})
@EnableTransactionManagement
public class TestDatabaseConfiguration {

    @Autowired
    private Environment env;

    @Bean
    DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        if (env != null) {
            dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
            dataSource.setUrl(env.getProperty("jdbc.url"));
            dataSource.setUsername(env.getProperty("jdbc.user"));
            dataSource.setPassword(env.getProperty("jdbc.pass"));
        }
        return dataSource;
    }

    // configure entityManagerFactory
    /**
     * @return entityManager
     */
    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(new String[] { "com.exercise.scheduler.persistence.model" });
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(additionalProperties());
        return em;
    }

    // configure transactionManager
    @Bean
    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    // configure additional Hibernate Properties
    final Properties additionalProperties() {
        final Properties hibernateProperties = new Properties();
        if (env != null) {
            hibernateProperties.setProperty("hibernate.hbm2ddl.auto",
                env.getProperty("hibernate.hbm2ddl.auto"));
            hibernateProperties.setProperty("hibernate.dialect",
                env.getProperty("hibernate.dialect"));
            hibernateProperties.setProperty("hibernate.cache.use_second_level_cache",
                env.getProperty("hibernate.cache.use_second_level_cache"));
            hibernateProperties.setProperty("hibernate.cache.use_query_cache",
                env.getProperty("hibernate.cache.use_query_cache"));
        }
        return hibernateProperties;
    }

}
