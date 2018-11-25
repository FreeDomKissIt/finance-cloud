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
import com.qykj.finance.core.cache.sys.MenuNode;
import com.qykj.finance.core.persistence.service.QueryService;
import com.qykj.finance.core.util.ActionPageResponse;
import com.qykj.finance.core.util.ActionResponse;
import com.qykj.finance.sys.form.MenuForm;
import com.qykj.finance.sys.model.Menu;
import com.qykj.finance.sys.service.MenuService;
import com.qykj.finance.sys.service.RoleService;
import com.qykj.finance.sys.service.UserService;
import com.qykj.finance.util.ListUtils;
import com.qykj.finance.util.SessionUtil;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 菜单控制层 文件名: DistrictController.java <br/>
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
@Controller
@RequestMapping("menu")
@Slf4j
public class MenuController {
	
	public static final String PAGE_NUM = "1";// 默认页码
	public static final String PAGE_SIZE = "10";// 默认页面大小
	@Autowired
	QueryService queryService;
	@Autowired
	MenuService menuService;
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;
	
	/**
	 * 跳转菜单页
	 * @param model
	 * @return
	 * @author dingbaishun
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	@RequestMapping("toMenu")
	public String toMenu(Model model) {
		return "/modules/sysConfig/menu/menu";
	}
	/**
	 * 跳转修改密码界面
	 * 
	 * @param model
	 * @return
	 * @author dingbaishun
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	@RequestMapping("toChangePassword")
	public String toChangePassword(Model model) {
		return "/changePassword";
	}
	
	/**
	 * 菜单新增页
	 * @param model
	 * @return
	 * @author dingbaishun
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	@RequestMapping("toAddMenu")
	public String toAddMenu(Model model, Integer parentId) {
		long sort = menuService.getNextChildrenSort(parentId);
		model.addAttribute("sort", sort);
		model.addAttribute("parentId", parentId);
		
		return "/modules/sysConfig/menu/dialog/addMenu";
	}
	
	/**
	 * 菜单编辑页
	 * @param model
	 * @return
	 * @author dingbaishun
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	@RequestMapping("toEditMenu")
	public String toEditMenu(Model model, Integer id) {
		Menu menu = menuService.findOne(id);
		model.addAttribute("menu", menu);
		
		return "/modules/sysConfig/menu/dialog/editMenu";
	}
	
	/**
	 * 菜单查看页
	 * @param model
	 * @return
	 * @author dingbaishun
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	@RequestMapping("toViewDetail")
	public String toViewDetail(Model model, Integer id) {
		Menu menu = menuService.findOne(id);
		model.addAttribute("menu", menu);
		
		return "/modules/sysConfig/menu/dialog/viewDetail";
	}
	
	/**
	 * 添加修改菜单
	 * @param menuForm
	 * @return
	 * @author wenjing
	 * @since V1.0.0
	 */
	@PostMapping("addEdit")
	@ApiOperation(value = "添加修改菜单", notes = "添加修改菜单")
	@ResponseBody
	public ResponseEntity<ActionResponse<Menu>> update(/* @Valid */ MenuForm menuForm) {
		Assert.isTrue(menuForm.getName() != null, "用户名不能为空");
		Menu menu = new Menu();
		BeanUtils.copyProperties(menuForm, menu);
		if (null == menuForm.getId()) {
			menuService.addMenu(menu);
		} else {
			menuService.updateMenu(menu);
		}
		log.debug("保存成功userId:{}", menu.getId());
		return ActionResponse.ok(menu);
	}
	
	/**
	 * 添加修改菜单
	 * @param menuForm
	 * @return
	 * @author wenjing
	 * @since V1.0.0
	 */
	@PostMapping("updateName")
	@ApiOperation(value = "添加修改菜单", notes = "添加修改菜单")
	@ResponseBody
	public ResponseEntity<ActionResponse<String>> updateName(/* @Valid */ MenuForm menuForm) {
		Assert.isTrue(menuForm.getName() != null, "菜单名不能为空");
		menuService.updateMenuName(menuForm.getId(),menuForm.getName());
		log.debug("保存成功menuId:{}", menuForm.getId());
		return ActionResponse.ok("成功");
	}
	
	
	/**
	 * 删除菜单
	 * @param id
	 * @return
	 * @author wenjing
	 * @since V1.0.0
	 */
	@PostMapping("delete")
	@ApiOperation(value = "删除菜单", notes = "删除菜单")
	@ResponseBody
	public ResponseEntity<ActionResponse<String>> delete(Integer id) {
		Assert.notNull(id, "菜单id不能为空");
		menuService.delete(id);
		log.debug("删除成功menu:{}", id);
		return ActionResponse.ok("成功");
	}
	
	/**
	 * 批量删除菜单
	 * @param ids
	 * @return
	 * @author wenjing
	 * @since V1.0.0
	 */
	@PostMapping("deleteBatch")
	@ApiOperation(value = "批量删除菜单", notes = "批量删除菜单")
	@ResponseBody
	public ResponseEntity<ActionResponse<String>> deleteBatch(String ids) {
		Assert.notNull(ids, "请选择菜单");
		List<Integer> idList = ListUtils.strToList(ids);
		menuService.delete(idList);
		log.debug("批量删除成功");
		return ActionResponse.ok("成功");
	}
	
	/**
	 * 角色管理
	 * @param pageNum
	 * @param pageSize
	 * @param roleId
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	@PostMapping("data")
	@ApiOperation(value = "菜单数据", notes = "菜单数据")
	@ResponseBody
	public ResponseEntity<ActionResponse<MenuNode>> getMenuData(Integer pageNum, Integer pageSize, Integer roleId) {
		MenuNode data = menuService.getRoleManagerMenu(roleId);
		return ActionResponse.ok(data);
	}
	
	/**
	 * 页面显示
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	@RequestMapping("pageTree")
	@ApiOperation(value = "菜单数据", notes = "菜单数据")
	@ResponseBody
	public ResponseEntity<ActionResponse<List<MenuNode>>> pageTree() {
		List<MenuNode> data = SessionUtil.getCurrentMenus();
		return ActionResponse.ok(data);
	}
	
	/**
	 * 用于菜单管理
	 * @param pageNum
	 * @param pageSize
	 * @param roleId
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	@PostMapping("userDefinedTree")
	@ApiOperation(value = "用户自定义菜单数据", notes = "用户自定义菜单数据")
	@ResponseBody
	public ResponseEntity<ActionResponse<List<MenuNode>>> userDefinedTree(Integer parentId) {
		List<MenuNode> data = menuService.getUserDefinedTree(parentId);
		return ActionResponse.ok(data);
	}
	
	
	/**
	 * 用于菜单管理
	 * @param pageNum
	 * @param pageSize
	 * @param roleId
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	@PostMapping("tree")
	@ApiOperation(value = "菜单数据", notes = "菜单数据")
	@ResponseBody
	public ResponseEntity<ActionResponse<MenuNode>> tree() {
		MenuNode data = menuService.getMenuForManager();
		return ActionResponse.ok(data);
	}
	
	/**
	 * 菜单列表分页
	 * @param pageNum
	 * @param pageSize
	 * @param user
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	@ApiOperation(value = "单位列表分页", notes = "单位列表分页")
	@PostMapping("list")
	@ResponseBody
	public ResponseEntity<ActionPageResponse<Map<String, Object>>> list(@RequestParam(defaultValue = PAGE_NUM) Integer pageNum,
	        @RequestParam(defaultValue = PAGE_SIZE) Integer pageSize, Integer parentId) {
		Page<Map<String, Object>> page = menuService.findPageList(pageNum, pageSize, parentId);
		return ActionPageResponse.create(page, null);
	}
	
}
