package com.qykj.finance.web.action.sys;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.qykj.finance.core.persistence.service.QueryService;
import com.qykj.finance.core.util.ActionPageResponse;
import com.qykj.finance.core.util.ActionResponse;
import com.qykj.finance.core.view.Combox;
import com.qykj.finance.sys.form.UpdatePasswordForm;
import com.qykj.finance.sys.form.UserForm;
import com.qykj.finance.sys.model.User;
import com.qykj.finance.sys.service.RoleService;
import com.qykj.finance.sys.service.UserService;
import com.qykj.finance.util.ListUtils;
import com.qykj.finance.util.SessionUtil;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户控制层 文件名: UserController.java <br/>
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
@Controller
@RequestMapping("user")
@Slf4j
public class UserController {
	public static final String PAGE_NUM = "1";// 默认页码
	public static final String PAGE_SIZE = "10";// 默认页面大小
	@Autowired
	QueryService queryService;
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;
	
	/**
	 * 跳转用户管理页
	 * @param model
	 * @return
	 * @author    dingbaishun
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@RequestMapping("/toUser")
	public String toUserConfig(Model model) {
		return "modules/sysConfig/user/user";
	}
	
	
	/**
	 * 跳转添加用户页
	 * @param model
	 * @return
	 * @author    dingbaishun
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@PostMapping("/toAddUser")
	public String toAddUser(Model model) {
		return "/modules/sysConfig/user/dialog/addUser";
	}
	/**
	 * 跳转编辑用户页
	 * @param model
	 * @return
	 * @author    dingbaishun
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@PostMapping("/toEditUser")
	public String toEditUser(Model model, Integer id) {
		User user = new User();
		if (id != null) {
			user = userService.findOne(id);
		} else {
			user = new User();
		}
		model.addAttribute("user", user);
		
		return "/modules/sysConfig/user/dialog/editUser";
	}
	/**
	 * 跳转用户详情页
	 * @param model
	 * @return
	 * @author    dingbaishun
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@PostMapping("/toViewDetail")
	public String toViewDetail(Model model, Integer id) {
		User user = new User();
		if (id != null) {
			user = userService.findOne(id);
		} else {
			user = new User();
		}
		model.addAttribute("user", user);
		
		return "/modules/sysConfig/user/dialog/viewDetail";
	}
	/**
	 * 跳转部门组织树界面
	 * @param model
	 * @return
	 * @author    dingbaishun
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@PostMapping("/toOrgTree")
	public String toOrgTree(Model model) {
		return "/modules/sysConfig/user/dialog/orgTree";
	}
	
	

	/**
	 * 添加修改用户
	 * 
	 * @param userForm
	 * @return
	 * @author wenjing
	 * @since V1.0.0
	 */
	@PostMapping("addEdit")
	@ApiOperation(value = "添加修改用户", notes = "添加修改用户")
	@ResponseBody
	public ResponseEntity<ActionResponse<User>> update(/* @Valid */ UserForm userForm) {
		Assert.isTrue(userForm.getUsername() != null, "用户名不能为空");
		
		User user = new User();
		BeanUtils.copyProperties(userForm, user);
		if (null == userForm.getId()) {
			User dbUser = userService.obtain(userForm.getUsername());
			if(dbUser != null) {
				return ActionResponse.fail("用户名重复");
			}
			userService.addUser(user, userForm.getOrgId(), userForm.getRoleId());
		} else {
			
			userService.updateUser(user, userForm.getOrgId(), userForm.getRoleId());
		}

		log.debug("保存成功userId:{}", user.getId());
		return ActionResponse.ok(user);
	}

	@PostMapping("updatePassword")
	@ApiOperation(value = "修改用户密码", notes = "修改用户密码")
	@ResponseBody
	public ResponseEntity<ActionResponse<String>> updatePassword(/* @Valid */ UpdatePasswordForm updatePasswordForm) {

		boolean flag = userService.updatePassword(SessionUtil.getCurrentUser().getId(),
				updatePasswordForm.getOldPassword(), updatePasswordForm.getNewPassword());
		if (flag)
			return ActionResponse.ok("成功");
		else
			return ActionResponse.fail(401, "密码错误");

	}

	@PostMapping("resetPassword")
	@ApiOperation(value = "重置用户密码", notes = "重置用户密码")
	@ResponseBody
	public ResponseEntity<ActionResponse<String>> resetPassword(Integer id) {
		boolean flag = userService.resetPassword(id);
		if (flag)
			return ActionResponse.ok("成功");
		else
			return ActionResponse.fail(401, "用户不存在请刷新列表重试");
	}

	@PostMapping("resetPasswordBatch")
	@ApiOperation(value = "批量重置用户密码", notes = "批量重置用户密码")
	@ResponseBody
	public ResponseEntity<ActionResponse<String>> resetPassword(String ids) {
		List<Integer> idList = ListUtils.strToList(ids);
		
		for (Integer id : idList) {
			userService.resetPassword(id);
		}
		
		return ActionResponse.ok("成功");
	}
	
	@PostMapping("enable")
	@ApiOperation(value = "批量修改用户启用禁用状态", notes = "批量修改用户启用禁用状态")
	@ResponseBody
	public ResponseEntity<ActionResponse<String>> enable(String ids) {
		List<Integer> idList = ListUtils.strToList(ids);
		for (Integer id : idList) {
			userService.enable(id);
		}
		
		return ActionResponse.ok("成功");
	}
	
	
	@PostMapping("unenable")
	@ApiOperation(value = "批量修改用户禁用状态", notes = "批量修改用户禁用状态")
	@ResponseBody
	public ResponseEntity<ActionResponse<String>> unenable(String ids) {
		List<Integer> idList = ListUtils.strToList(ids);
		for (Integer id : idList) {
			userService.unenable(id);
		}
		
		return ActionResponse.ok("成功");
	}
	

	/**
	 * 删除用户
	 * 
	 * @param userForm
	 * @return
	 * @author wenjing
	 * @since V1.0.0
	 */
	@PostMapping("delete")
	@ApiOperation(value = "删除用户", notes = "删除用户")
	@ResponseBody
	public ResponseEntity<ActionResponse<String>> delete(Integer id) {
		Assert.notNull(id, "用户id不能为空");
		userService.delete(id);
		log.debug("删除成功userId:{}", id);
		return ActionResponse.ok("成功");
	}

	/**
	 * 批量删除用户
	 * 
	 * @param userForm
	 * @return
	 * @author wenjing
	 * @since V1.0.0
	 */
	@PostMapping("deleteBatch")
	@ApiOperation(value = "批量删除用户", notes = "批量删除用户")
	@ResponseBody
	public ResponseEntity<ActionResponse<String>> deleteBatch(String ids) {
		Assert.notNull(ids, "请选择用户");
		List<Integer> idList = ListUtils.strToList(ids);
		userService.delete(idList);
		log.debug("批量删除成功");
		return ActionResponse.ok("成功");
	}

	/**
	 * 用户列表分页
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param user
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	@ApiOperation(value = "用户列表分页", notes = "用户列表分页")
	@PostMapping("list")
	@ResponseBody
	public ResponseEntity<ActionPageResponse<User>> list(@RequestParam(defaultValue = PAGE_NUM) Integer pageNum,
	        @RequestParam(defaultValue = PAGE_SIZE) Integer pageSize, User user) {
		Page<User> page = null;
		if (SessionUtil.isRoot()) {
			page = userService.findROOTPageList(pageNum, pageSize);
		} else {
			page = userService.findPageList(pageNum, pageSize);
		}
		
		return ActionPageResponse.create(page, null);
	}
	
	/**
	 * 单位选择框树
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param user
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	/*@ApiOperation(value = "单位选择框树", notes = "单位选择框树")
	@PostMapping("org/combox")
	@ResponseBody
	public ResponseEntity<ActionResponse<DistrictOrgTree>> combox(Integer orgId) {
		DistrictOrgTree tree = orgService.getOrgComboxTree(orgId);
		return ActionResponse.ok(tree);
	}*/
	
	/**
	 * 用户角色下拉框值
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param user
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	@ApiOperation(value = "角色列表", notes = "角色列表")
	@PostMapping("role/combox")
	@ResponseBody
	public ResponseEntity<ActionResponse<Combox<Map<String, Object>>>> combox() {
		List<Map<String, Object>> roles = null;
		if (SessionUtil.isRoot()) {
			roles = roleService.findAllROOTEnable();
		} else {
			roles = roleService.findAllEnable();
		}
		return ActionResponse.ok(new Combox<Map<String, Object>>(roles));
	}

}
