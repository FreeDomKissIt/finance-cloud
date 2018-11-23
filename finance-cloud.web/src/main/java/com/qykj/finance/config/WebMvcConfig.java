package com.qykj.finance.config;

import javax.servlet.DispatcherType;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.google.code.kaptcha.servlet.KaptchaServlet;
import com.qykj.finance.core.xss.SecurityFilter;
import com.qykj.finance.shiro.KaptchaFilter;

import lombok.extern.slf4j.Slf4j;

/**
 * kaptcha验证码配置 
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
@Configuration
@Slf4j
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	/**
	 * 字体大小
	 */
	public static final String FONT_SIZE = "28";
	/**
	 * 图像宽度
	 */
	public static final String IMAGE_WIDTH = "70";
	/**
	 * 图像高度
	 */
	public static final String IMAGE_HEIGHT = "34";
	/**
	 * 验证码长度
	 */
	public static final String CHAR_LENGTH = "4";
	/**
	 * 验证码空隙
	 */
	public static final String CHAR_SPACE = "5";
	/**
	 * 背景颜色开始颜色
	 */
	public static final String BACKGROUND_FROM = "247,247,247";
	/**
	 * 背景颜色结束颜色
	 */
	public static final String BACKGROUND_TO = "247,247,247";
	
	/**
	 * 增加过滤
	 */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
    }
    
    /**
     * 默认调转/index
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.addViewControllers(registry);
    } 
    
	/**
	 * 验证码Servlet
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@Bean
	public ServletRegistrationBean kaptchaServlet() {
		ServletRegistrationBean servlet = new ServletRegistrationBean(new KaptchaServlet());
		servlet.getUrlMappings().add("/kaptcha");
		servlet.addInitParameter("kaptcha.border", "no");// 无边框
		servlet.addInitParameter("kaptcha.session.key", KaptchaFilter.KAPTCHA_SESSION_KEY);// session key
		servlet.addInitParameter("kaptcha.textproducer.font.color", "black");// 字体颜色
		servlet.addInitParameter("kaptcha.textproducer.font.size", FONT_SIZE);// 字体大小
		
		servlet.addInitParameter("kaptcha.textproducer.font.names", "Arial");// 字体大小
		
		servlet.addInitParameter("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.WaterRipple");// 图片样式
		//servlet.addInitParameter("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");// 验证码扰乱器
		servlet.addInitParameter("kaptcha.image.width", IMAGE_WIDTH);// 验证码图片宽度
		servlet.addInitParameter("kaptcha.image.height", IMAGE_HEIGHT);// 验证码图片高度
		servlet.addInitParameter("kaptcha.textproducer.char.length", CHAR_LENGTH);// 验证码长度
		servlet.addInitParameter("kaptcha.textproducer.char.space", CHAR_SPACE);// 空格
		servlet.addInitParameter("kaptcha.background.clear.from", BACKGROUND_FROM); // 背景颜色一致
		servlet.addInitParameter("kaptcha.background.clear.to", BACKGROUND_TO);// 背景颜色一致
		servlet.setOrder(1);
		log.info("KaptchaServlet init");
		return servlet;
	}

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
		log.info("SecurityFilter init Success");
		return registration;
	}
}
