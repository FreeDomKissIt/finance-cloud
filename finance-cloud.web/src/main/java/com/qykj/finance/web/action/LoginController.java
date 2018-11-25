package com.qykj.finance.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qykj.finance.core.cache.CacheContainer;
import com.qykj.finance.core.util.ActionResponse;
import com.qykj.finance.shiro.Credentials;
import com.qykj.finance.util.SessionUtil;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 文件名: LoginController.java <br/>
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
@Controller
@RequestMapping("/")
@Slf4j
public class LoginController {

	/**
	 * shiro登录返回
	 * 
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 * @since 1.0.0
	 */
	@PostMapping("/login")
	public String login(HttpServletRequest request, Map<String, Object> map) throws Exception {
		log.info("LoginController.login()");
		// 登录失败从request中获取shiro处理的异常信息。
		// shiroLoginFailure:就是shiro异常类的全类名.
		String exception = (String) request.getAttribute("shiroLoginFailure");
		log.info("exception=" + exception);
		String logMsg = "";
		String msg = "";
		/*if (exception != null) {
			if (UnknownAccountException.class.getName().equals(exception)) {
				logMsg = "UnknownAccountException -- > 用户名不存在：";
				msg = "用户名不存在";
			} else if (IncorrectCredentialsException.class.getName().equals(exception)) {
				logMsg = "IncorrectCredentialsException -- > 密码不正确：";
				msg = "密码不正确";
			} else if ("kaptchaValidateFailed".equals(exception)) {
				logMsg = "kaptchaValidateFailed -- > 验证码错误";
				msg = "验证码错误";
			} else if (LockedAccountException.class.getName().equals(exception)) {
				logMsg = "LockedAccountException -- > 用户被锁定";
				msg = "用户被锁定";
			} else if (LicenseExpireException.class.getName().equals(exception)) {
				logMsg = "LicenseExpireException -- > 授权失效,请检查证书";
				msg = "授权失效,请检查证书";
			} else {
				logMsg = "else >> " + exception;
				msg = "系统异常";
				log.info("else -- >" + exception);
			}
		} else {
			return "index";
		}*/
		return "index";
		//log.info(logMsg);
		//map.put("msg", msg);

		// 此方法不处理登录成功,由shiro进行处理
		//return "login";
	}

	/**
	 * 登录页
	 * 
	 * @return
	 * @throws Exception
	 * @since 1.0.0
	 */
	@GetMapping("/login")
	public String loginPage() throws Exception {

		return "login";
	}

	/**
	 * 登录页
	 * 
	 * @return
	 * @throws Exception
	 * @since 1.0.0
	 */
	@GetMapping("/ajaxLogout")
	@ResponseBody
	public ResponseEntity<ActionResponse<String>> ajaxLogout() throws Exception {
		SessionUtil.logout();
		return ActionResponse.ok("成功");
	}

	/**
	 * 首页
	 * 
	 * @return
	 * @throws Exception
	 * @since 1.0.0
	 */
	@GetMapping("/index")
	public String index(Model model) throws Exception {
		//Org org = SessionUtil.getCurrentOrg();
		//model.addAttribute("org", org);
		return "index";
	}

	@GetMapping("getMachineCode")
	@ApiOperation(value = "获得当前机器码", notes = "获得当前机器码")
	@ResponseBody
	public ResponseEntity<ActionResponse<String>> getMachineCode() {
		return ActionResponse.ok(CacheContainer.getMachineCode());
	}

	@Value("${qykj.finance.event.version}")
	private String version;
	@Value("${qykj.finance.event.date}")
	private String date;
	/**
	 * 跳转关于界面
	 * 
	 * @param model
	 * @return
	 * @author dingbaishun
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	@RequestMapping("toAbout")
	public String toAbout(Model model) {
		StringBuilder versionMessage = new StringBuilder();
		versionMessage.append("平台版本：").append(version).append(" build ").append(date);
		String machineCode = CacheContainer.getMachineCode();
		model.addAttribute("version", versionMessage);
		StringBuilder machineCodeMessage = new StringBuilder();
		machineCodeMessage.append("本机机器码: ").append(machineCode);
		model.addAttribute("machineCode", machineCodeMessage);
		String message = Credentials.validateInfo(CacheContainer.getLicense());
		model.addAttribute("message", message);
		
		return "about";
	}
}
