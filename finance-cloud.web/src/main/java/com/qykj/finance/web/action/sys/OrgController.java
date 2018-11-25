package com.qykj.finance.web.action.sys;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
import com.qykj.finance.sys.form.OrgForm;
import com.qykj.finance.sys.model.Org;
import com.qykj.finance.sys.service.OrgService;
import com.qykj.finance.util.ListUtils;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 单位控制层 
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
@Controller
@RequestMapping("org")
@Slf4j
public class OrgController {
	public static final String PAGE_NUM = "1";// 默认页码
	public static final String PAGE_SIZE = "10";// 默认页面大小
	@Autowired
	QueryService queryService;
	@Autowired
	OrgService orgService;
	
	/**
	 * 跳转单位管理页
	 * @param model
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@RequestMapping("/toOrg")
	public String toOrg(Model model) {
		return "modules/sysConfig/org/org";
	}
	
	/**
	 * 跳转添加单位页
	 * @param model
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@PostMapping("/toAddOrg")
	public String toAddOrg(Model model) {
		return "/modules/sysConfig/org/dialog/addOrg";
	}

	/**
	 * 跳转编辑单位页
	 * @param model
	 * @param id
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@PostMapping("/toEditOrg")
	public String toEditOrg(Model model, Integer id) {
		Org org = new Org();
		if (id != null) {
			org = orgService.findOne(id);
		}
		model.addAttribute("org", org);
		
		return "/modules/sysConfig/org/dialog/editOrg";
	}
	
	/**
	 * 跳转行政区划树界面
	 * @param model
	 * @return
	 * @author    dingbaishun
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@PostMapping("/toDistrictTree")
	public String toDistrictTree(Model model) {
		return "/modules/sysConfig/org/dialog/districtTree";
	}
	
	/**
	 * 跳转单位详情页
	 * @param model
	 * @return
	 * @author    dingbaishun
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@PostMapping("/toViewDetail")
	public String toViewDetail(Model model, Integer id) {
		Org org = new Org();
		if (id != null) {
			org = orgService.findOne(id);
		}
		model.addAttribute("org", org);
		
		return "/modules/sysConfig/org/dialog/viewDetail";
	}

	/**
	 * 添加单位
	 * 
	 * @param org
	 * @param menuIdsStr
	 *            1,2,3,4
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	@PostMapping("addEdit")
	@ApiOperation(value = "添加更新单位", notes = "添加更新单位")
	@ResponseBody
	public ResponseEntity<ActionResponse<Org>> addEdit(/* @Valid */ OrgForm orgForm, Integer districtId) {
		Org org = new Org();
		if(StringUtils.isBlank(orgForm.getCode())) {
			return ActionResponse.fail("单位编码不能为空");
		}
		Org dbOrg = orgService.findByOrgCode(orgForm.getCode());
		
		BeanUtils.copyProperties(orgForm, org);
		if(null == org.getId()) {
			if (dbOrg != null) {
				return ActionResponse.fail("单位编码重复");
			}
			orgService.addOrg(org, districtId);
		}else {
			if (dbOrg != null) {
				if(!dbOrg.getId().equals(orgForm.getId())) {
					return ActionResponse.fail("单位编码重复");
				}
			}
			orgService.updateOrg(org, districtId);
		}
		
		log.debug("保存单位成功orgId:{}", org.getId());
		return ActionResponse.ok(org);
	}

	/**
	 * 删除单位
	 * 
	 * @param userForm
	 * @return
	 * @author wenjing
	 * @since V1.0.0
	 */
	@PostMapping("delete")
	@ApiOperation(value = "删除单位", notes = "删除单位")
	@ResponseBody
	public ResponseEntity<ActionResponse<String>> delete(Integer id) {
		
		Assert.notNull(id, "单位id不能为空");
		try {
		orgService.delete(id);
		log.debug("删除成功roleId:{}", id);
		return ActionResponse.ok("删除成功");
		}catch(Exception e) {
			return ActionResponse.fail(500, "选择的单位已有用户使用");
		}
	}
	
	/**
	 * 批量删除单位
	 * 
	 * @param ids
	 * @return
	 * @author wenjing
	 * @since V1.0.0
	 */
	@PostMapping("deleteBatch")
	@ApiOperation(value = "批量删除单位", notes = "批量删除单位")
	@ResponseBody
	public ResponseEntity<ActionResponse<String>> deleteBatch(String ids) {
		
		Assert.notNull(ids, "请选择单位");
		try {
		List<Integer> idList = ListUtils.strToList(ids);
		orgService.delete(idList);
		log.debug("批量删除成功");
		return ActionResponse.ok("成功");
	}catch(Exception e) {
		return ActionResponse.fail(500, "选择的单位已有用户使用");
	}
	}

	/**
	 * 单位列表分页
	 * 
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
			@RequestParam(defaultValue = PAGE_SIZE) Integer pageSize, Org org) {
		Page<Map<String, Object>> page = orgService.findPageList(pageNum, pageSize);
		
		return ActionPageResponse.create(page, null);
	}
	
	/**
	 * 单位列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param user
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	@ApiOperation(value = "单位列表", notes = "单位列表")
	@PostMapping("combox")
	@ResponseBody
	public ResponseEntity<ActionResponse<Combox<Org>>> combox() {
		List<Org> orgs = orgService.findAll();
		
		return ActionResponse.ok(new Combox<Org>(orgs));
	}
}
