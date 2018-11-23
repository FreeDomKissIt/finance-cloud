package com.qykj.finance.core.shiro;

import org.apache.shiro.authc.SimpleAuthenticationInfo;

import com.qykj.finance.core.cache.CacheContainer;
import com.qykj.finance.shiro.Credentials;
import com.qykj.finance.sys.model.User;

/**
 * 验证类 
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
public class FinanceAuthenticationInfo extends SimpleAuthenticationInfo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8845869427840207947L;
	private static final String ROOT = "admin";
	private static final String SYS_ROOT = "sysadmin";
	
	/**
	 * 金融验证类  构造方法
	 * @param principal
	 * @param credentials
	 * @param realmName
	 */
	public FinanceAuthenticationInfo(Object principal, Object credentials, String realmName) {
		super(principal, credentials, realmName);
		User user = (User)principal;
		if (!ROOT.equals(user.getUsername())&&!SYS_ROOT.equals(user.getUsername())) {
			getAuthorizeInfo();
		}
	}
	
	/**
	 * 获得授权信息
	 * 
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	protected void getAuthorizeInfo() {
		Credentials.validate(CacheContainer.getLicense());
	}
}
