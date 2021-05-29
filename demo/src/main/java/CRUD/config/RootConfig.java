package CRUD.config;

import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:db.properties")
@ComponentScan(value = "CRUD",excludeFilters = {@ ComponentScan.Filter (Controller.class)})
public class RootConfig extends WebMvcConfigurationSupport {
    private final Environment env;

    public RootConfig(Environment env) {
        this.env = env;
    }


//    @Bean
//    @Scope("prototype")
//    List<User> workUsers(UserService userService) {
//        return userService.getAllUsers();
//    }

    @Bean
    DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("db.driver")));
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setUsername(env.getProperty("db.username"));
        dataSource.setPassword(env.getProperty("db.password"));
        return dataSource;
    }

    @Bean
    HibernateJpaVendorAdapter getJpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setGenerateDdl(true);
        adapter.setShowSql(false);
        return adapter;
    }

    @Bean(name = "emf")
    LocalContainerEntityManagerFactoryBean getEntityManager() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setPackagesToScan("CRUD.model");
        emf.setDataSource(getDataSource());
        emf.setJpaVendorAdapter(getJpaVendorAdapter());
        return emf;
    }

//    @Bean
//    LocalSessionFactoryBean getSessionFactory() {
//        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
//        factoryBean.setDataSource(getDataSource());
//
//        Properties properties = new Properties();
//        properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
//        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
//
//        factoryBean.setHibernateProperties(properties);
//        factoryBean.setAnnotatedClasses(User.class);
//        return factoryBean;
//    }

    @Bean
    JpaTransactionManager getTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(getEntityManager().getObject());
        return transactionManager;
    }

}
