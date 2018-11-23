package com.qykj.finance.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.qykj.finance.common.CommonConstant;
import com.qykj.finance.core.shiro.FinanceAuthenticationInfo;
import com.qykj.finance.core.util.EncryptUtil;
import com.qykj.finance.sys.model.User;
import com.qykj.finance.sys.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * shiro登录验证领域
 * 
 * @author wenjing
 * @version v1.0.0
 */
@Slf4j // Subject subject = SecurityUtils.getSubject();
public class HikShiroRealm extends AuthorizingRealm {

	@Autowired
	UserRepository userRepository;

	/**
	 * 获得用户名
	 */
	@Override
	public String getName() {
		User user = (User) SecurityUtils.getSubject().getPrincipal();

		return user.getName();
	}

	/**
	 * 是否支持验证
	 */
	@Override
	public boolean supports(AuthenticationToken token) {
		return true;
	}

	/**
	 * 获取用户权限角色信息
	 * 
	 * @since 1.0.0
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 因为非正常退出，即没有显式调用 SecurityUtils.getSubject().logout()
		// (可能是关闭浏览器，或超时)，但此时缓存依旧存在(principals)，所以会自己跑到授权方法里。
		if (!SecurityUtils.getSubject().isAuthenticated()) {
			doClearCache(principals);
			SecurityUtils.getSubject().logout();
			return null;
		}
		User userInfo = (User) principals.getPrimaryPrincipal();
		String userName = userInfo.getUsername();
		if (StringUtils.isNotBlank(userName)) {
			SimpleAuthorizationInfo sazi = new SimpleAuthorizationInfo();
			try {
				// 角色
				/*
				 * Set<String> roleIds= shiroUserService.getRoles(userName); for (String roleId:
				 * roleIds){ shiroUser.setRoleId(roleId); } sazi.addRoles(roleIds);
				 * sazi.addStringPermissions(shiroUserService.getPermissions(userName));
				 */
				return sazi;
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		return null;
	}

	/**
	 * 登录验证规则
	 * 
	 * @since 1.0.0
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//getAuthorizeInfo();
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
		String username = usernamePasswordToken.getUsername();
		if (username == null) {
			throw new UnknownAccountException();
		}
		// 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UnknownAccountException();
		}
		
		/*
		if(CommonConstant.USER_STATUS_LOCK == user.getState()) {
			throw new LockedAccountException();
		}
		
		char[] passwordChars = usernamePasswordToken.getPassword();
		if(passwordChars == null)
			throw new IncorrectCredentialsException();
		
		String password = new String(passwordChars);
		// 盐加密
		String salt = user.getSalt();
		String saltpassword = EncryptUtil.encrypt(password, salt);
		 	
		if (!user.getPassword().equals(saltpassword))
			throw new IncorrectCredentialsException();
*/
		FinanceAuthenticationInfo authenticationInfo = new FinanceAuthenticationInfo(user, // 用户名
				user.getPassword(), // 密码
				// ByteSource.Util.bytes(user.getSalt()), // 弃用shiro的盐加密
				user.getName() // realm name
		);

		return authenticationInfo;
	}
	

}
