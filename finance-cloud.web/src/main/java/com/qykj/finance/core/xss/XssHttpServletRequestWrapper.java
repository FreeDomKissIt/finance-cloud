package com.qykj.finance.core.xss;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringEscapeUtils;
import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;

import lombok.extern.slf4j.Slf4j;

/**
 * XSS安全过滤HttpServletReques封装对象
 * 文件名: XssHttpServletRequestWrapper.java <br/>
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
@Slf4j
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
	/**
	 * AntiSamy对象
	 */
	private AntiSamy antiSamy;

	/**
	 * 构造方法
	 * 
	 * @param request
	 *            请求体
	 * @param antiSamy
	 *            AntiSamy对象
	 */
	public XssHttpServletRequestWrapper(ServletRequest request, AntiSamy antiSamy) {
		super((HttpServletRequest) request);
		this.antiSamy = antiSamy;
	}

	/**
	 * 重写getParameterMap方法 实现安全过滤
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> request_map = super.getParameterMap();
		Iterator<?> iterator = request_map.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry me = (Map.Entry) iterator.next();
			String[] values = (String[]) me.getValue();
			for (int i = 0; i < values.length; i++) {
				values[i] = xssClean(values[i]);
			}
		}
		return request_map;
	}

	/**
	 * 重写getParameterValues方法 实现安全过滤
	 */
	@Override
	public String[] getParameterValues(String paramString) {
		String[] arrayOfString1 = super.getParameterValues(paramString);
		if (arrayOfString1 == null)
			return null;
		int i = arrayOfString1.length;
		String[] arrayOfString2 = new String[i];
		for (int j = 0; j < i; j++)
			arrayOfString2[j] = xssClean(arrayOfString1[j]);
		return arrayOfString2;
	}

	/**
	 * 重写getParameter方法 实现安全过滤
	 */
	@Override
	public String getParameter(String paramString) {
		String str = super.getParameter(paramString);
		if (str == null)
			return null;
		return xssClean(str);
	}

	/**
	 * 重写getHeader方法 实现安全过滤
	 */
	@Override
	public String getHeader(String paramString) {
		String str = super.getHeader(paramString);
		if (str == null)
			return null;
		return xssClean(str);
	}

	/**
	 * 信息xss信息过滤方法
	 * 
	 * @param value
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	private String xssClean(String value) {
		try {
			if (antiSamy != null) {
				final CleanResults cr = antiSamy.scan(value);
				// 安全的HTML输出
				// 修复 " 变为 &quot;
				String str = StringEscapeUtils.unescapeHtml(cr.getCleanHTML());
				// 修复 &nbsp; 变为乱码
				str = str.replaceAll(antiSamy.scan("&nbsp;").getCleanHTML(), "");
				return str;
			}
		} catch (ScanException e) {
			log.error("xssClean ScanException {}", e);
		} catch (PolicyException e) {
			log.error("xssClean PolicyException {}", e);
		}

		return value;
	}
}