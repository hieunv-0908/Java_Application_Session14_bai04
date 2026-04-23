package re.java_application_session14_bai04.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.hibernate.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration

public class HibernateConfig {



   @Bean

   public DataSource dataSource() {

       DriverManagerDataSource dataSource = new DriverManagerDataSource();

       dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

       dataSource.setUrl("jdbc:mysql://localhost:3306/ptit_javawebapplication_session13?createDatabaseIfNotExist=true");

       dataSource.setUsername("root");

       dataSource.setPassword("12345678");

       return dataSource;

   }



   @Bean

   public Properties properties() {

       Properties properties = new Properties();



       properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

       properties.setProperty("hibernate.hbm2ddl.auto", "update");

       properties.setProperty("hibernate.show_sql", "true");



       return properties;

   }



   @Bean

   public LocalSessionFactoryBean dataLocalSessionFactoryBean(Properties properties, DataSource dataSource) {

       LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();

       localSessionFactoryBean.setDataSource(dataSource);

       localSessionFactoryBean.setPackagesToScan("org.example.session13_orm_hibernate.model");

       localSessionFactoryBean.setHibernateProperties(properties);

       return localSessionFactoryBean;

   }



}