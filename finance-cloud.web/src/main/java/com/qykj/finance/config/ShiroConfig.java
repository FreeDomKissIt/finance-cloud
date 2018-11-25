package com.qykj.finance.config;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.ServletContainerSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.qykj.finance.shiro.ShiroRealm;
import com.qykj.finance.shiro.KaptchaFilter;

import lombok.extern.slf4j.Slf4j;

/**
 * Shiro配置类 文件名: ShiroConfig.java <br/>
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
@Configuration
@Slf4j
public class ShiroConfig {

	/*
	 * @Bean public HikShiroRealm hikShiroRealm() { HikShiroRealm hikShiroRealm =
	 * new HikShiroRealm(); return hikShiroRealm; }
	 */
	// redis 缓存管理配置

	/*
	 * @Bean public RedisCacheManager redisCacheManager() { return new
	 * RedisCacheManager(); }
	 */

	/**
	 * EhCache缓存管理配置
	 * 
	 * @return
	 * @since 1.0.0
	 */
	@Bean
	public EhCacheManager ehCacheManager() {
		EhCacheManager ehcacheManager = new EhCacheManager();
		ehcacheManager.setCacheManagerConfigFile("classpath:config/ehcache-shiro.xml");
		return ehcacheManager;
	}

	/**
	 * 登录验证
	 * 
	 * @param ehCacheManager
	 *            ehCache管理器
	 * @return
	 * @since 1.0.0
	 */
	@Bean
	public ShiroRealm hikShiroRealm(EhCacheManager ehCacheManager) {
		ShiroRealm hikShiroRealm = new ShiroRealm();
		hikShiroRealm.setCacheManager(ehCacheManager);
		return hikShiroRealm;
	}

	/**
	 * 生命周期处理类
	 * 
	 * @return
	 * @since 1.0.0
	 */
	@Bean
	public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	// redis 缓存方式
	/*
	 * @Bean public SessionManager sessionManager(RedisSessionDAO sessionDAO) {
	 * DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
	 * sessionManager.setSessionDAO(sessionDAO);
	 * sessionManager.setGlobalSessionTimeout(1800);
	 * sessionManager.setCacheManager(redisCacheManager()); return sessionManager; }
	 */

	/**
	 * 默认代理创建
	 * 
	 * @return
	 * @since 1.0.0
	 */
	@Bean
	public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
		daap.setProxyTargetClass(true);
		return daap;
	}

	/**
	 * ehCache方式securityManager
	 * 
	 * @param realm
	 * @return
	 * @since 1.0.0
	 */
	@Bean
	public DefaultWebSecurityManager securityManager(ShiroRealm realm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(realm);
		securityManager.setCacheManager(ehCacheManager());

		return securityManager;
	}

	/**
	 * servletContainerSessionManager
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@Bean
	public ServletContainerSessionManager servletContainerSessionManager() {
		ServletContainerSessionManager sessionManager = new ServletContainerSessionManager();
		return sessionManager;
	}

	/**
	 * 验证参数配置
	 * 
	 * @param securityManager
	 *            安全管理器
	 * @return
	 * @since 1.0.0
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
		aasa.setSecurityManager(securityManager);
		return new AuthorizationAttributeSourceAdvisor();
	}

	/**
	 * 授权过滤配置
	 * 
	 * @param securityManager
	 *            安全管理器
	 * @return
	 * @since 1.0.0
	 */
	@Bean
	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();// 获取filters
		//验证码过滤器
		filters.put("authc", new KaptchaFilter());
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// 拦截器
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		// 配置不会被拦截的链接 顺序判断
		filterChainDefinitionMap.put("/favicon.ico", "anon");
		filterChainDefinitionMap.put("/static/**", "anon");
		filterChainDefinitionMap.put("/baseui/**", "anon");
		filterChainDefinitionMap.put("/common/**", "anon");
		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/financeui/**", "anon");
		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/images/**", "anon");
		filterChainDefinitionMap.put("/plugins/**", "anon");
		filterChainDefinitionMap.put("/resources/**", "anon");
		filterChainDefinitionMap.put("/druid/**", "anon");
		filterChainDefinitionMap.put("/kaptcha", "anon");
		// 过滤链定义，从上向下顺序执行，一般将/**放在最为下边
		// authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问
		filterChainDefinitionMap.put("/**", "authc");//默认都需要登录

		//filterChainDefinitionMap.put("/**", "anon");
		// 配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
		filterChainDefinitionMap.put("/logout", "logout");
		// 如果不设置默认会自动寻找Web工程根目录下的"login.jsp"页面
		shiroFilterFactoryBean.setLoginUrl("/login");
		// 登录成功后要跳转的链接
		shiroFilterFactoryBean.setSuccessUrl("/index");
		// 未授权跳转的链接
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		log.info("ShiroConfiguration init");
		return shiroFilterFactoryBean;
	}

}
