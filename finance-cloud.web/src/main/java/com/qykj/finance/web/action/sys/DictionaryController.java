
package com.qykj.finance.web.action.sys;

import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.qykj.finance.common.CommonConstant;
import com.qykj.finance.core.util.ActionPageResponse;
import com.qykj.finance.core.util.ActionResponse;
import com.qykj.finance.core.view.Combox;
import com.qykj.finance.sys.form.DictionaryForm;
import com.qykj.finance.sys.model.Dictionary;
import com.qykj.finance.sys.service.DictionaryService;

/**
 * 字典目录管理controller
 *  创 建 人: wenjing8 <br/>
 *  版 本 号: V1.0.0 <br/>
 */

@Controller
@RequestMapping("/dictionary/")
@Slf4j
public class DictionaryController {
	@Autowired
	DictionaryService dictionaryService;

	/**
	 * 跳转字典管理页
	 * @param model
	 * @return
	 * @author    dingbaishun
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@RequestMapping("/toDictionaryConfig")
	public String toDictionaryConfig(Model model) {
		return "/modules/sysConfig/dataDictionaryConfig/dataDictionaryConfig";
	}
	/**
	 * 跳转添加字典管理页
	 * @param model
	 * @return
	 * @author    dingbaishun
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@PostMapping("/toAddDictionaryConfig1")
	public String toAddDictionaryConfig(Model model) {
		return "/modules/sysConfig/dataDictionaryConfig/dialog/addDictionaryConfig";
	}
	/**
	 * 跳转编辑字典管理页
	 * @param model
	 * @return
	 * @author    dingbaishun
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@PostMapping("/toEditDictionaryConfig")
	public String toEditDictionaryConfig(Model model, Integer id) {
		return "/modules/sysConfig/dataDictionaryConfig/dialog/editDictionaryConfig";
	}
	/**
	 * 跳转字典管理详情页
	 * @param model
	 * @return
	 * @author    dingbaishun
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@PostMapping("/toViewDetail")
	public String toViewDetail(Model model, Integer id) {
		
		return "/modules/sysConfig/dataDictionaryConfig/dialog/viewDetail";
	}
	/**
	 * 跳转字典子条目页
	 * @param model
	 * @return
	 * @author    dingbaishun
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@PostMapping("/toSubItems")
	public String toSubItems(Model model, String tid) {
		model.addAttribute("tid",tid);
		return "/modules/sysConfig/dataDictionaryConfig/dataSubDictionary";
	}
	/**
	 * 字典管理首页  只查询 parentId = 0 即 根节点下一级字典
	 * @return 
	 * @throws Exception
	 * @author    wenjing8
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@PostMapping("dictionaryPageQuery")
	@ResponseBody
	public ResponseEntity<ActionPageResponse<Dictionary>> dictionaryPageQuery(Integer pageNum,Integer pageSize) throws Exception {
		if(pageNum==null) {
			pageNum = 1;
		}
		Page<Dictionary> page = dictionaryService.dictionaryPageQuery(pageNum,pageSize);
		return ActionPageResponse.create(page, null);
	}
	
	/**
	 * 字典目录 新增1、修改2、详情3  页面加载方法  
	 * @param model 
	 * @param form
	 * @return
	 * @author    wenjing8
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@PostMapping("toAddDictionary")
	public String toAddDictionary(Model model,DictionaryForm form) {
		String oprateType = form.getOprateType();
		Dictionary dictionary = new Dictionary();
		String ret = "";
		if(form.getId()!=null){
			dictionary = dictionaryService.selectDictionaryById(form.getId());
			model.addAttribute(dictionary);
		}
		if("1".equals(oprateType)){
			log.info("加载新增字典项详细页面");
			ret = "/modules/sysConfig/dataDictionaryConfig/dialog/addDictionaryConfig";
		}else if("2".equals(oprateType)){
			log.info("加载修改字典项详细页面name:{}", dictionary.getName());
			ret = "/modules/sysConfig/dataDictionaryConfig/dialog/editDictionaryConfig";
		}else if("3".equals(oprateType)){
			log.info("加载字典项详细页面name:{}", dictionary.getName());
			ret = "/modules/sysConfig/dataDictionaryConfig/dialog/viewDetail";
		}
		return ret;
	}
	
	/**
	 * 新增1、修改2  保存字典信息
	 * @param form
	 * @return
	 * @author    wenjing8
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@PostMapping("addOrUpdateDictionary")
	@ApiOperation(value = "添加/修改字典目录项", notes = "添加/修改字典目录项")
	@ResponseBody
	public ResponseEntity<ActionResponse<Dictionary>> addOrUpdateDictionary(DictionaryForm form) {
		if(StringUtils.isBlank(form.getName()))
			return ActionResponse.fail("字典名字不能为空");
		if(StringUtils.isBlank(form.getCode()))
			return ActionResponse.fail("字典名字不能为空");
		Dictionary dbDictionary = dictionaryService.findByCode(form.getCode());
		String oprateType = form.getOprateType();//操作类型
		Dictionary dictionary = new Dictionary();
		if("1".equals(oprateType)){//新增
			
			if (dbDictionary != null) {
				return ActionResponse.fail("字典编码重复");
			}
			BeanUtils.copyProperties(form, dictionary);
			dictionary.setParentId(0);
			dictionary.setIsDeleted(false);
			dictionary.setLeaf(false);
			try {
				dictionaryService.addDictionary(dictionary);
			} catch (Exception e) {
				return ActionResponse.fail(e.getMessage());
			}
			log.info("新增字典项name:{}", dictionary.getName());
		}else{
			if (dbDictionary != null) {
				if(!dbDictionary.getId().equals(form.getId())) {
					return ActionResponse.fail("字典编码重复");
				}
			}
			dictionary = dictionaryService.selectDictionaryById(form.getId());
			dictionary.setName(form.getName());
			dictionary.setCode(form.getCode());
			dictionaryService.updateDictionary(dictionary);
			log.info("修改字典项name:{}", dictionary.getName());
		}
		return ActionResponse.ok(dictionary);
	}
	
	/**
	 * 删除字典项   删除之前判断是否有下级节点，如果有，则不能删除
	 * @param ids  id,id,id 支持多个删除
	 * @return
	 * @author    wenjing8
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@PostMapping("deleteDictionary")
	@ApiOperation(value = "删除字典目录项", notes = "删除字典目录项")
	@ResponseBody
	public ResponseEntity<ActionResponse<Dictionary>> deleteDictionary(String ids) {
		try {
			String[] str = ids.split(",");//支持多个删除
			for (String string : str) {
				Dictionary dictionary = dictionaryService.selectDictionaryById(new Integer(string));
				String result = dictionaryService.deleteDictionary(dictionary);
				if (CommonConstant.SUCCESS.equals(result)) {// 删除成功
					log.info("删除字典项name:{}成功", dictionary.getName());
				} else if (CommonConstant.FAIL.equals(result)) {
					log.info("该字典项name:{}存在下级目录，不能删除", dictionary.getName());
					return ActionResponse.fail("存在下级目录，不能删除");
				}
			}
		} catch (Exception e) {
			return ActionResponse.fail(500, "数据库删除失败!", null);
		}
		return ActionResponse.ok(null);
	}
	
	//------------------下面是字典管理主页的【分配子条目】内的操作controller------------------------
	/**
	 * 分配子条目  用父id查下级  parentId
	 * @return 
	 * @throws Exception
	 * @author    wenjing8
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@PostMapping("dictionaryChildPageQuery")
	@ResponseBody
	public ResponseEntity<ActionPageResponse<Dictionary>> dictionaryChildPageQuery(Integer pageNum,Integer pageSize,Integer parentId) throws Exception {
		if(pageNum==null) {
			pageNum = 1;
		}
		Page<Dictionary> page = dictionaryService.dictionaryChildPageQuery(pageNum,pageSize,parentId);
		return ActionPageResponse.create(page, null);
	}
	
	/**
	 * 子条目  新增1、修改2、详情3  页面加载方法  
	 * @param model 
	 * @param form  这里需要传id(可以为空) 和  parentId(不能为空)
	 * @return
	 * @author    wenjing8
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@PostMapping("toAddDictionaryChild")
	public String toAddDictionaryChild(Model model,DictionaryForm form) {
		String oprateType = form.getOprateType();
		Integer parentId = form.getParentId();
		String ret = "";
		Dictionary dictionary = new Dictionary();
		if(form.getId()!=null){
			dictionary = dictionaryService.selectDictionaryById(form.getId());
			model.addAttribute(dictionary);
		}
		if("1".equals(oprateType)){//新增的时候必须给parentId
			dictionary.setParentId(parentId);
			model.addAttribute(dictionary);
			log.info("加载新增字典项详细页面");
			ret = "/modules/sysConfig/dataDictionaryConfig/dialog/addDictionaryConfig";
		}else if("2".equals(oprateType)){
			log.info("加载修改字典项详细页面name:{}", dictionary.getName());
			ret = "/modules/sysConfig/dataDictionaryConfig/dialog/editDictionaryConfig";
		}else if("3".equals(oprateType)){
			log.info("加载字典项详细页面name:{}", dictionary.getName());
			ret = "/modules/sysConfig/dataDictionaryConfig/dialog/subViewDetail";
		}
		return ret;
	}
	
	/**
	 * 新增1、修改2  保存字典子条目信息
	 * @param form
	 * @return
	 * @author    wenjing8
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@PostMapping("addOrUpdateDictionaryChild")
	@ApiOperation(value = "添加/修改字典目录项", notes = "添加/修改字典目录项")
	@ResponseBody
	public ResponseEntity<ActionResponse<Dictionary>> addOrUpdateDictionaryChild(DictionaryForm form) {
		if(StringUtils.isBlank(form.getName()))
			return ActionResponse.fail("字典名字不能为空");
		if(StringUtils.isBlank(form.getCode()))
			return ActionResponse.fail("字典名字不能为空");
		Dictionary dbDictionary = dictionaryService.findByCode(form.getCode());
		String oprateType = form.getOprateType();//操作类型
		Dictionary dictionary = new Dictionary();
		if("1".equals(oprateType)){//新增
			if (dbDictionary != null) {
				return ActionResponse.fail("字典编码重复");
			}
			BeanUtils.copyProperties(form, dictionary);
			dictionary.setParentId(form.getParentId());
			dictionary.setIsDeleted(false);
			dictionary.setLeaf(false);
			try {
				dictionaryService.addDictionary(dictionary);
			} catch (Exception e) {
				return ActionResponse.fail(500, e.getMessage(), null);
			}
			log.info("新增字典项name:{}", dictionary.getName());
		}else{
			if (dbDictionary != null) {
				if(!dbDictionary.getId().equals(form.getId())) {
					return ActionResponse.fail("字典编码重复");
				}
			}
			dictionary = dictionaryService.selectDictionaryById(form.getId());
			dictionary.setName(form.getName());
			dictionary.setCode(form.getCode());
			dictionaryService.updateDictionary(dictionary);
			log.info("修改字典项name:{}", dictionary.getName());
		}
		return ActionResponse.ok(dictionary);
	}
	
	/**
	 * 删除字典子条目  删除之前判断是否有下级节点，如果有，则不能删除
	 * @param ids  id,id,id 支持多个删除
	 * @return
	 * @author    wenjing8
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@PostMapping("deleteDictionaryChild")
	@ApiOperation(value = "删除字典目录项", notes = "删除字典目录项")
	@ResponseBody
	public ResponseEntity<ActionResponse<Dictionary>> deleteDictionaryChild(String ids) {
		try {
			String[] str = ids.split(",");//支持多个删除
			for (String string : str) {
				Dictionary dictionary = dictionaryService.selectDictionaryById(new Integer(string));
				String result = dictionaryService.deleteDictionary(dictionary);
				if (CommonConstant.SUCCESS.equals(result)) {// 删除成功
					log.info("删除字典项name:{}成功", dictionary.getName());
				} else if (CommonConstant.FAIL.equals(result)) {
					log.info("该字典项name:{}存在下级目录，不能删除", dictionary.getName());
					return ActionResponse.fail("存在下级目录，不能删除");
				}
			}
			return ActionResponse.ok(null);
		} catch (Exception e) {
			return ActionResponse.fail(500, "数据库删除失败!", null);
		}
	}
	
	/**
	 * 用父id 返回code,name格式数据
	 * @param item id
	 * @return
	 * @author    wenjing8
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@PostMapping("getDictionaryData")
	@ResponseBody
	public ResponseEntity<ActionResponse<Map<String,String>>> getDictionaryData(Integer item){
		List<Dictionary> dicts = dictionaryService.selectDictionaryByParentId(item);
		Map<String,String> codes = new LinkedHashMap<String, String>();
		for (Dictionary dictionary : dicts) {
			codes.put(dictionary.getCode(),dictionary.getName());
		}
		return ActionResponse.ok(codes);
	}
	
	/**
	 * 查询所有返回code,name格式数据
	 * @param item id
	 * @return
	 * @author    wenjing8
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@PostMapping("getAllDictionary")
	@ResponseBody
	public ResponseEntity<ActionResponse<Map<String,String>>> getAllDictionary(){
		List<Dictionary> dicts = dictionaryService.getAllDictionary();
		Map<String,String> codes = new HashMap<String, String>();
		for (Dictionary dictionary : dicts) {
			if(StringUtils.isNoneBlank(dictionary.getCode())) {
				codes.put(dictionary.getCode(),dictionary.getName());
			}
		}
		return ActionResponse.ok(codes);
	}
	
	/**
	 * 
	 * @param id 用id查下级数据
	 * @return 
	 * @author    wenjing8
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@ApiOperation(value = "字典下拉框", notes = "字典下拉框")
	@PostMapping("combox")
	@ResponseBody
	public ResponseEntity<ActionResponse<Combox<Dictionary>>> combox(Integer id) {
		List<Dictionary> dicts = dictionaryService.selectDictionaryByParentId(id);
		return ActionResponse.ok(new Combox<Dictionary>(dicts));
	}
	
	/**
	 * 从缓存加载单个下拉框数据
	 * @param id 用id查下级数据
	 * @return 
	 * @author    wenjing8
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@ApiOperation(value = "字典下拉框", notes = "字典下拉框")
	@PostMapping("redisCombox")
	@ResponseBody
	public ResponseEntity<ActionResponse<Combox<Dictionary>>> redisCombox(Integer id) {
		if(null == id){
			return ActionResponse.fail("字典id不能为空");
		}
		return ActionResponse.ok(new Combox<Dictionary>(dictionaryService.selectDictionaryByParentIdFromCache(id.toString())));
	}
	
	/**
	 * 
	 * @param id 用id查下级数据 支持返回多个下拉框模式 id,id,id...
	 * @return List<Combox<Dictionary>>
	 * @author    wenjing8
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@ApiOperation(value = "字典下拉框", notes = "字典下拉框")
	@PostMapping("comboxs")
	@ResponseBody
	public ResponseEntity<ActionResponse<List<Combox<Dictionary>>>> comboxs(String ids) {
		List<Combox<Dictionary>> ret = new ArrayList<Combox<Dictionary>>();
		String[] id = ids.split(",");
		for (String string : id) {
			List<Dictionary> dicts = dictionaryService.selectDictionaryByParentId(Integer.valueOf(string));
			ret.add(new Combox<Dictionary>(dicts));
		}
		return ActionResponse.ok(ret);
	}
	
	/**
	 * 从缓存里加载多个字典下拉框数据
	 * @param id 用id查下级数据 支持返回多个下拉框模式 id,id,id...
	 * @return List<Combox<Dictionary>>
	 * @author    wenjing8
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@ApiOperation(value = "字典下拉框", notes = "字典下拉框")
	@PostMapping("cacheComboxs")
	@ResponseBody
	public ResponseEntity<ActionResponse<List<Combox<Dictionary>>>> cacheComboxs(String ids) {
		List<Combox<Dictionary>> ret = new ArrayList<Combox<Dictionary>>();
		String[] id = ids.split(",");
		for (String string : id) {
			ret.add(new Combox<Dictionary>(dictionaryService.selectDictionaryByParentIdFromCache(string)));
		}
		return ActionResponse.ok(ret);
	}
	
}
