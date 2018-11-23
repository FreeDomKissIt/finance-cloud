package com.qykj.finance.core.xss;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.io.IOUtils;
import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.Policy;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 文件名: SecurityFilter.java <br/>
 * 创 建 人: wenjing <br/>
 * 描 述: 安全过滤器<br/>
 * 版 本 号: V1.0.0 <br/>
 */
@Slf4j
public class SecurityFilter implements Filter {
	FilterConfig filterConfig = null;
	/**
	 * AntiSamy对象
	 */
	private AntiSamy antiSamy;

	/**
	 * 过滤器初始化方法
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		this.antiSamy = new AntiSamy();
		InputStream inputStream = null;
		try {
			inputStream = SecurityFilter.class.getClassLoader().getResource("config/antisamy.xml").openStream();
			this.antiSamy.setPolicy(Policy.getInstance(inputStream));
		} catch (Exception e) {
			log.error("SecurityFilter antiSamy init Error {}", e);
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
	}

	/**
	 * 销毁方法
	 */
	@Override
	public void destroy() {
		this.filterConfig = null;
		this.antiSamy = null;
	}

	/**
	 * 过滤方法
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		chain.doFilter(new XssHttpServletRequestWrapper(request, this.antiSamy), response);
	}
}