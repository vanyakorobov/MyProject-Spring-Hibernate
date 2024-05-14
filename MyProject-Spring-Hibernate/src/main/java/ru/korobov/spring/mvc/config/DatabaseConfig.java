package ru.korobov.spring.mvc.config;

import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
@ComponentScan(value = "ru")
public class DatabaseConfig {

    @Autowired
    private Environment env;

    //Настройки подкл. к БД
    @Bean
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(env.getRequiredProperty("db.url"));
        dataSource.setDriverClassName(env.getRequiredProperty("db.driver"));
        dataSource.setUsername(env.getRequiredProperty("db.username"));
        dataSource.setPassword(env.getRequiredProperty("db.password"));

        //dataSource.setInitialSize(Integer.valueOf(
        //        env.getRequiredProperty("db.initialSize")));
        //dataSource.setMaxIdle(Integer.valueOf(
        //        env.getRequiredProperty("db.minIdle")));
        //dataSource.setMinIdle(Integer.valueOf(
        //        env.getRequiredProperty("db.maxIdle")));
        //dataSource.setTimeBetweenEvictionRunsMillis(Long.valueOf(
        //        env.getRequiredProperty("db.timeBetweenEvictionRunMills")));
        //dataSource.setMinEvictableIdleTimeMillis(Long.valueOf(
        //        env.getRequiredProperty("db.minEvictableIdTimeMills")));
        //dataSource.setTestOnBorrow(Boolean.valueOf(
        //        env.getRequiredProperty("db.testOnBorrow")));
        return dataSource;
    }

    //Транзакции
    @Bean
    public PlatformTransactionManager platformTransactionManager() {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(getEntityManagerFactory().getObject());
        return manager;
    }


    @Bean
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        Properties props=new Properties();
        props.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        props.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));

        factoryBean.setDataSource(getDataSource());
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        factoryBean.setPackagesToScan("ru.korobov.spring.mvc.model");
        factoryBean.setJpaProperties(props);

        return factoryBean;
    }
}
