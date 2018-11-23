package com.qykj.finance.core.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 *  授权异常类
 *  创 建 人: wenjing <br/>
 *  版 本 号: V1.0.0 <br/>
 */
public class LicenseExpireException extends AuthenticationException  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4755404161096442978L;
	public static final String LICENSE_EXPIRE = "证书已过期";
	public static final String LICENSE_UABLE = "证书无效";
	public static final String LICENSE_SERVER_STOP = "更新服务暂停";
	public static final String LICENSE_NOT_EXIST = "证书不存在";
	public static final String LICENSE_TRY_EXPIRE = "试用版本，授权过期";
	public static final String LICENSE_TRY_NOT_TIME = "试用版本，试用时间不存在";
	
	public LicenseExpireException() {
		super(LICENSE_EXPIRE);
	}

	public LicenseExpireException(String message) {
		super(message);
	}
}
