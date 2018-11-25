package com.qykj.finance.web.action.sys;

import java.util.List;

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
import com.qykj.finance.sys.model.Role;
import com.qykj.finance.sys.service.RoleService;
import com.qykj.finance.util.ListUtils;
import com.qykj.finance.util.SessionUtil;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 角色控制层 
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
@Controller
@RequestMapping("role")
@Slf4j
public class RoleController {
	public static final String PAGE_NUM = "1";// 默认页码
	public static final String PAGE_SIZE = "10";// 默认页面大小
	@Autowired
	QueryService queryService;
	@Autowired
	RoleService roleService;

	/**
	 * 跳转角色管理页
	 * @param model
	 * @return
	 * @author    dingbaishun
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@RequestMapping("/toRole")
	public String toUserConfig(Model model) {
		return "modules/sysConfig/role/role";
	}
	/**
	 * 跳转添加角色页
	 * @param model
	 * @return
	 * @author    dingbaishun
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@PostMapping("/toAddRole")
	public String toAddRole(Model model) {
		return "/modules/sysConfig/role/dialog/addRole";
	}
	/**
	 * 跳转编辑角色页
	 * @param model
	 * @return
	 * @author    dingbaishun
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@PostMapping("/toEditRole")
	public String toEditRole(Model model, Integer id) {
		Role role = new Role();
		if (id != null) {
			role = roleService.findOne(id);
		}
		model.addAttribute("role", role);
		
		return "/modules/sysConfig/role/dialog/editRole";
	}
	/**
	 * 跳转角色详情页
	 * @param model
	 * @return
	 * @author    dingbaishun
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@PostMapping("/toViewDetail")
	public String toViewDetail(Model model, Integer id) {
		Role role = new Role();
		if (id != null) {
			role = roleService.findOne(id);
		}
		model.addAttribute("role", role);
		
		return "/modules/sysConfig/role/dialog/viewDetail";
	}
	/**
	 * 添加更新角色
	 * 
	 * @param role
	 * @param menuIdsStr
	 *            1,2,3,4
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	@PostMapping("addEdit")
	@ApiOperation(value = "添加角色", notes = "添加角色")
	@ResponseBody
	public ResponseEntity<ActionResponse<Role>> addEdit(/* @Valid */ Role role, String menuIdsStr) {
		List<Integer> menuIds = ListUtils.strToList(menuIdsStr);
		if(null == role.getId()) {
			roleService.addRole(role, menuIds);
		}else {
			roleService.updateRole(role, menuIds);
		}
	
		log.debug("保存成功roleId:{}", role.getId());
		return ActionResponse.ok(role);
	}

	
	/**
	 * 删除角色
	 * 
	 * @param userForm
	 * @return
	 * @author wenjing
	 * @since V1.0.0
	 */
	@PostMapping("delete")
	@ApiOperation(value = "删除角色", notes = "删除角色")
	@ResponseBody
	public ResponseEntity<ActionResponse<String>> delete(Integer id) {
		try {
			Assert.notNull(id, "用户id不能为空");
			roleService.deleteRole(id);
			log.debug("删除成功roleId:{}", id);
			return ActionResponse.ok("成功");
		}catch(Exception e) {
			return ActionResponse.fail(500, "该角色已有用户使用");
		}
	}
	
	/**
	 * 批量删除角色
	 * 
	 * @param ids
	 * @return
	 * @author wenjing
	 * @since V1.0.0
	 */
	@PostMapping("deleteBatch")
	@ApiOperation(value = "批量删除角色", notes = "批量删除角色")
	@ResponseBody
	public ResponseEntity<ActionResponse<String>> deleteBatch(String ids) {
		try {
		Assert.notNull(ids, "请选择角色");
		List<Integer> idList = ListUtils.strToList(ids);
		roleService.delete(idList);
		log.debug("批量删除成功");
		return ActionResponse.ok("成功");
	}catch(Exception e) {
		return ActionResponse.fail(500, "选择的角色已有用户使用");
	}
		
	}
	
	/**
	 * 角色列表分页
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param user
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	@ApiOperation(value = "角色列表分页", notes = "角色列表分页")
	@PostMapping("list")
	@ResponseBody
	public ResponseEntity<ActionPageResponse<Role>> list(@RequestParam(defaultValue = PAGE_NUM) Integer pageNum,
			@RequestParam(defaultValue = PAGE_SIZE) Integer pageSize, Role user) {
		Page<Role> page = null;
		if(SessionUtil.isRoot()) {
			page = roleService.findRootPageList(pageNum, pageSize);
		}else {
			page = roleService.findPageList(pageNum, pageSize);
		}
		
		return ActionPageResponse.create(page, null);
	}

}
