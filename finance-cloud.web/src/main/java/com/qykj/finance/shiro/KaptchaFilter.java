package com.qykj.finance.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

/**
 *  验证码验证
 *  创 建 人: wenjing <br/>
 *  版 本 号: V1.0.0 <br/>
 */
public class KaptchaFilter extends FormAuthenticationFilter {

	public static final String KAPTCHA_SESSION_KEY = "kaptcha.code";

	/**
	 * 访问时过滤
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		// 在这里进行验证码的校验
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpSession session = httpServletRequest.getSession();
		// 取出验证码
		String validateCode = (String) session.getAttribute(KAPTCHA_SESSION_KEY);
		//  如启用则验证
		if(validateCode != null ) {
			// 取出页面的验证码
			// 输入的验证和session中的验证进行对比
			String kaptcha = httpServletRequest.getParameter("kaptcha");

			if (!validateCode.equals(kaptcha)) {
				// 如果校验失败，将验证码错误失败信息，通过shiroLoginFailure设置到request中
				httpServletRequest.setAttribute("shiroLoginFailure", "kaptchaValidateFailed");// 自定义登录异常
				// 拒绝访问，不再校验账号和密码
				return true;
			}
		}
		
		
		return super.onAccessDenied(request, response);
	}
}
