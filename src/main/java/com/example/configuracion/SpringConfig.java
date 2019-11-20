package com.example.configuracion;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;


@Configuration
@ComponentScan(basePackages = {"com.example"})
@EnableTransactionManagement
@PropertySource(value={"classpath:application.properties"} , ignoreResourceNotFound = false)

public class SpringConfig implements EnvironmentAware {

	@Autowired
    Environment env;

	@Override
	public void setEnvironment(Environment environment) {
		this.env = environment;
	}

	@Bean
	public DataSource getDataSource() {
		try {
			DriverManagerDataSource dataSource = new DriverManagerDataSource();
			dataSource.setDriverClassName(env.getProperty("spring.datasource.driverClassName"));
			dataSource.setUrl(env.getProperty("spring.datasource.url"));
			dataSource.setUsername(env.getProperty("spring.datasource.username"));
			dataSource.setPassword(env.getProperty("spring.datasource.password"));
			return dataSource;

		}catch (NullPointerException e){
			return null;
		}
	}

    @Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
		LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
		emfb.setDataSource(dataSource);
		emfb.setJpaVendorAdapter(jpaVendorAdapter);
		emfb.setPackagesToScan("com.example");
		return emfb;
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.MYSQL);
		adapter.setShowSql(Boolean.getBoolean(env.getProperty("hibernate.show_sql")));
		adapter.setGenerateDdl(Boolean.getBoolean(env.getProperty("hibernate.hbm2ddl.auto")));
		adapter.setDatabasePlatform(env.getProperty("hibernate.dialect"));
		return adapter;
	}

	@Bean
	public PersistenceAnnotationBeanPostProcessor paPostProcessor() {
		return new PersistenceAnnotationBeanPostProcessor();
	}

	@Bean
	public BeanPostProcessor persistenceTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

    @Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource, @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") SessionFactory sessionFactory){
		HibernateTransactionManager tm = new HibernateTransactionManager();
		tm.setSessionFactory(sessionFactory);
		tm.setDataSource(dataSource);
		return tm;
	}

	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource, EntityManagerFactory emf) {
		JpaTransactionManager tm = new JpaTransactionManager();
		tm.setEntityManagerFactory(emf);
		tm.setDataSource(dataSource);
		return tm;
	}

}
