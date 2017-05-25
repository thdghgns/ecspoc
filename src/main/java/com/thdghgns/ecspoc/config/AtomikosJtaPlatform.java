package com.thdghgns.ecspoc.config;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.hibernate.engine.transaction.jta.platform.internal.AbstractJtaPlatform;

//http://fabiomaffioletti.me/blog/2014/04/15/distributed-transactions-multiple-databases-spring-boot-spring-data-jpa-atomikos/
public class AtomikosJtaPlatform extends AbstractJtaPlatform {
	private static final long serialVersionUID = 6843141706317028194L;

	static TransactionManager transactionManager;
	static UserTransaction transaction;
	
	@Override
	protected TransactionManager locateTransactionManager() {
		return transactionManager;
	}

	@Override
	protected UserTransaction locateUserTransaction() {
		return transaction;
	}

}
