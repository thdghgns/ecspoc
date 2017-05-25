package com.thdghgns.ecspoc.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@EnableJpaRepositories(
		basePackages = "com.thdghgns.ecspoc.repository.primary",
		entityManagerFactoryRef = "primaryEntityManagerFactory",
		transactionManagerRef = "transactionManager")
@EntityScan(basePackages = "com.thdghgns.ecspoc.entity.primary")
@Configuration
@DependsOn("transactionManager")
public class PrimaryConfiguration {
	
	@Autowired
	private JpaVendorAdapter jpaVendorAdapter;

    @Bean(name = "primaryDS")
    @Primary
    public DataSource primaryDataSource() {
        JdbcDataSource jdbcxaDataSource = new JdbcDataSource();
        jdbcxaDataSource.setUrl("jdbc:h2:tcp://localhost/~/primaryDS");
        jdbcxaDataSource.setUser("sa");
        jdbcxaDataSource.setPassword("");
 
 
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(jdbcxaDataSource);
        xaDataSource.setUniqueResourceName("primary");
        xaDataSource.setMaxPoolSize(10);
        return xaDataSource;
    }
    
    @Bean(name = "primaryEntityManagerFactory")
    @DependsOn("transactionManager")
    @Primary
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory() {
    	
    	HashMap<String, Object> jpaProperties = new HashMap<String, Object>();
    	jpaProperties.put("hibernate.transaction.jta.platform",  AtomikosJtaPlatform.class.getName());
    	jpaProperties.put("javax.persistence.transactionType", "JTA");
    	
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setJtaDataSource(primaryDataSource());
        entityManagerFactoryBean.setPackagesToScan("com.thdghgns.ecspoc.entity.primary");
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactoryBean.setJpaPropertyMap(jpaProperties);
  
        return entityManagerFactoryBean;
    }
}
