package com.qykj;

import javax.servlet.DispatcherType;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.qykj.finance.core.xss.SecurityFilter;

/**
 * 主程序(Spring Jar包打包运行方式使用) 文件名: SpringbootApplication.java <br/>
 * 弃用
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
//因未启用 所以注释了
//@SpringBootApplication
//@Import(value = { SpringConfig.class })
//@EnableScheduling
//@EnableAsync
//@EnableCaching
//@ServletComponentScan // 扫描方式添加Servlet、Filter、ServletListener
//@EnableTransactionManagement
//@ImportResource("classpath:transaction.xml")
//@Slf4j
// @EnableRedisHttpSession//开启后使用redis来存储session
public class SpringbootApplication {

	/**
	 * 程序启动主方法
	 * 
	 * @param args
	 */
	/*public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}*/

	/**
	 * antisamy 安全过滤器
	 * 
	 * @return
	 */
	@Bean
	public FilterRegistrationBean securityFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean(new SecurityFilter());
		registration.getUrlPatterns().clear();
		registration.setDispatcherTypes(DispatcherType.REQUEST);
		registration.getUrlPatterns().add("/*");
//		log.info("SecurityFilter init Success");
		return registration;
	}

}
