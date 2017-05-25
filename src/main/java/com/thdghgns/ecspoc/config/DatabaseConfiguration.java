package com.thdghgns.ecspoc.config;

import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;

@Configuration
public class DatabaseConfiguration {
	
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(true);
		hibernateJpaVendorAdapter.setGenerateDdl(true);
		hibernateJpaVendorAdapter.setDatabase(Database.H2);
		return hibernateJpaVendorAdapter;
	}
	
    @Bean(name = "userTransaction")
    public UserTransaction userTransactionImp() throws SystemException {
    	UserTransactionImp userTransactionImp = new UserTransactionImp();
    	userTransactionImp.setTransactionTimeout(300);
    	
    	return userTransactionImp;
    }
    
    @Bean(name = "atomikosTransactionManager", initMethod = "init", destroyMethod = "close")
    public TransactionManager atomikosTransactionManager() {
    	UserTransactionManager userTransactionManager = new UserTransactionManager();
    	userTransactionManager.setForceShutdown(true);
    	
    	AtomikosJtaPlatform.transactionManager = userTransactionManager;
    	
    	return userTransactionManager;
    }
    
    @Bean(name = "transactionManager")
    @DependsOn({ "userTransaction", "atomikosTransactionManager" })
    public PlatformTransactionManager jtaTransactionManager() throws SystemException {
    	UserTransaction userTransaction = userTransactionImp();
    	
    	AtomikosJtaPlatform.transaction = userTransaction;
    	
    	TransactionManager atomikosTransactionManager = atomikosTransactionManager();
    	
    	return new JtaTransactionManager(userTransaction, atomikosTransactionManager);
    }
}
