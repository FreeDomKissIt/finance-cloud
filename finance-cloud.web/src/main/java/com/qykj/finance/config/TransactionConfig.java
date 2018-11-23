package com.qykj.finance.config;

import java.util.Properties;

import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;


/**
 *  事务配置
 *  创 建 人: wenjing <br/>
 *  版 本 号: V1.0.0 <br/>
 */
//xml与此类配置二选一
//@Configuration
public class TransactionConfig {

	public static final String transactionModuleExecution = "execution (* com.qykj.*.*.service.*.*(..))";

	@Autowired
	private PlatformTransactionManager transactionManager;

	/**
	 *  方法规则配置
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	public static Properties pointCutProperties() {
		Properties attributes = new Properties();
		attributes.setProperty("get*", "PROPAGATION_SUPPORTS,-Throwable,readOnly");
		attributes.setProperty("query*", "PROPAGATION_SUPPORTS,-Throwable,readOnly");
		attributes.setProperty("select*", "PROPAGATION_SUPPORTS,-Throwable,readOnly");
		attributes.setProperty("find*", "PROPAGATION_SUPPORTS,-Throwable,readOnly");
		attributes.setProperty("add*", "PROPAGATION_REQUIRED,-Throwable");
		attributes.setProperty("save*", "PROPAGATION_REQUIRED,-Throwable");
		attributes.setProperty("insert*", "PROPAGATION_REQUIRED,-Throwable");
		attributes.setProperty("update*", "PROPAGATION_REQUIRED,-Throwable");
		attributes.setProperty("delete*", "PROPAGATION_REQUIRED,-Throwable");
		attributes.setProperty("reset*", "PROPAGATION_REQUIRED,-Throwable");
		return attributes;
	}
	
	/**
	 * 事务拦截器
	 * @return
	 * @since 1.0.0
	 */
	@Bean
	public TransactionInterceptor transactionInterceptor() {
		Properties attributes = pointCutProperties();
		TransactionInterceptor txAdvice = new TransactionInterceptor(transactionManager, attributes);
		return txAdvice;
	}

	/**
	 * 切面
	 * @return
	 * @since 1.0.0
	 */
	@Bean
	public DefaultPointcutAdvisor modulePointcutAdvisor() {
		DefaultPointcutAdvisor advisor = createAdvisor(transactionModuleExecution);
		return advisor;
	}

	/**
	 * 创建默认切面规则
	 * @param transactionExecution aop切面方法匹配表达式
	 * @return
	 * @since 1.0.0
	 */
	private DefaultPointcutAdvisor createAdvisor(String transactionExecution) {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(transactionExecution);
		DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
		advisor.setPointcut(pointcut);
		Properties attributes = pointCutProperties();
		TransactionInterceptor txAdvice = new TransactionInterceptor(transactionManager, attributes);
		advisor.setAdvice(txAdvice);
		return advisor;
	}
}