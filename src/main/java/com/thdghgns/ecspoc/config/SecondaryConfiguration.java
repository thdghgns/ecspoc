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
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@EnableJpaRepositories(
		basePackages = "com.thdghgns.ecspoc.repository.secondary",
		entityManagerFactoryRef = "secondaryEntityManagerFactory",
		transactionManagerRef = "transactionManager")
@EntityScan(basePackages = "com.thdghgns.ecspoc.entity.secondary")
@Configuration
@DependsOn("transactionManager")
public class SecondaryConfiguration {

	@Autowired
	private JpaVendorAdapter jpaVendorAdapter;
    
    @Bean(name = "secondaryDS")
    public DataSource secondaryDataSource() {
    	JdbcDataSource jdbcxaDataSource = new JdbcDataSource();
        jdbcxaDataSource.setUrl("jdbc:h2:tcp://localhost/~/secondaryDS");
        jdbcxaDataSource.setUser("sa");
        jdbcxaDataSource.setPassword("");
 
 
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(jdbcxaDataSource);
        xaDataSource.setUniqueResourceName("secondary");
        xaDataSource.setMaxPoolSize(10);
        return xaDataSource;
    }
    
    @Bean(name = "secondaryEntityManagerFactory")
    @DependsOn("transactionManager")
    public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory() {
    	HashMap<String, Object> jpaProperties = new HashMap<String, Object>();
    	jpaProperties.put("hibernate.transaction.jta.platform",  AtomikosJtaPlatform.class.getName());
    	jpaProperties.put("javax.persistence.transactionType", "JTA");
    	
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setJtaDataSource(secondaryDataSource());
        entityManagerFactoryBean.setPackagesToScan("com.thdghgns.ecspoc.entity.secondary");
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactoryBean.setJpaPropertyMap(jpaProperties);
  
        return entityManagerFactoryBean;
    }
    
//    @Bean(name = "secondaryPlatformTransactionManager")
//    public PlatformTransactionManager platformTransactionManager() {
//    	JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
//    	jpaTransactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
//		return jpaTransactionManager;
//    }
}
