
package com.qykj.finance.web.action.sys;

import java.util.List;

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
import com.qykj.finance.core.exception.JSONDataException;
import com.qykj.finance.core.util.ActionPageResponse;
import com.qykj.finance.core.util.ActionResponse;
import com.qykj.finance.core.util.ListUtils;
import com.qykj.finance.sys.form.DictionaryForm;
import com.qykj.finance.sys.form.DictionaryTree;
import com.qykj.finance.sys.model.Dictionary;
import com.qykj.finance.sys.service.DictionaryService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 *  详细的数据字典页面功能
 *  创 建 人: wenjing8 <br/>
 *  版 本 号: V1.0.0 <br/>
 */
@Controller
@RequestMapping("dictionaryDetail")
@Slf4j
public class DictionaryDetailController {
	@Autowired
	DictionaryService dictionaryService;
	
	/**
	 * 详细的数据字典页面 左侧的树 数据
	 * @param model
	 * @param form
	 * @return
	 * @author    wenjing8
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@PostMapping("tree")
	@ApiOperation(value = "数据字典树", notes = "数据字典树")
	@ResponseBody
	public ActionResponse<DictionaryTree> dictionaryTree(){
		DictionaryTree root = new DictionaryTree();
		ActionResponse<DictionaryTree> ret = new ActionResponse<DictionaryTree>();
		try {
			root = dictionaryService.dictionaryTree();//查询目录字典
			log.info("加载字典树数据完成 :"+root);
		} catch (Exception e) {
			log.error("加载字典树数据失败 :"+e.getMessage());
		}
		ret.setData(root);
		return ret;
	}
	/**
	 * 跳转字典页
	 * @param model
	 * @return
	 * @author    dingbaishun
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@RequestMapping("/toDictionaryDetail")
	public String toDictionaryDetail(Model model) {
		return "/modules/sysConfig/dataDictionary/dictionaryDetail";
	}
	/**
	 * 详细的数据字典分页  leaf  is true
	 * @param model
	 * @param pageNum
	 * @param pageSize
	 * @param parentId
	 * @return
	 * @throws Exception
	 * @author    wenjing8
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@PostMapping("dictionaryDetailPageQuery")
	@ResponseBody
	public ResponseEntity<ActionPageResponse<Dictionary>> dictionaryDetailPageQuery(Integer pageNum,Integer pageSize,Integer parentId) throws Exception {
		Page<Dictionary> page = dictionaryService.dictionaryDetailPageQuery(pageNum,pageSize,parentId);
		return ActionPageResponse.create(page, null);
	}
	
	/**
	 * 加载页面  新增1、修改2、详情3 
	 * @param model
	 * @param form
	 * @return
	 * @author    wenjing8
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@PostMapping("toAddDetailDictionary")
	public String toAddDetailDictionary(Model model,DictionaryForm form) {
		String oprateType = form.getOprateType();
		Dictionary dictionary = new Dictionary();
		if(form.getId()!=null){
			dictionary = dictionaryService.selectDictionaryById(form.getId());
			model.addAttribute(dictionary);
		}
		
		if("1".equals(oprateType)){
			log.info("加载新增字典项详细页面");
			return "/modules/sysConfig/dataDictionary/dialog/addDictionary";
		}else if("2".equals(oprateType)){
			log.info("加载修改字典项详细页面name:{}", dictionary.getName());
			return "/modules/sysConfig/dataDictionary/dialog/editDictionary";
		}else if("3".equals(oprateType)){
			log.info("加载字典项详细页面name:{}", dictionary.getName());
			return "/modules/sysConfig/dataDictionary/dialog/viewDetail";
		}
		return null;
	}
	
	/**
	 * 新增1、修改2  保存字典信息
	 * @param form
	 * @return
	 * @author    wenjing8
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@PostMapping("addOrUpdateDictionaryDetail")
	@ApiOperation(value = "添加/修改字典数据", notes = "添加/修改字典数据")
	@ResponseBody
	public ResponseEntity<ActionResponse<Dictionary>> addOrUpdateDictionaryDetail(DictionaryForm form) {
		if(StringUtils.isBlank(form.getName()))
			return ActionResponse.fail("字典名字不能为空");
		if(StringUtils.isBlank(form.getCode()))
			return ActionResponse.fail("字典编码不能为空");
		Dictionary dbDictionary = dictionaryService.findByCode(form.getCode());
		String oprateType = form.getOprateType();//操作类型
		Dictionary dictionary = new Dictionary();
		if("1".equals(oprateType)){//新增
			if (dbDictionary != null) {
				return ActionResponse.fail("字典编码重复");
			}
			BeanUtils.copyProperties(form, dictionary);
			dictionary.setIsDeleted(false);
			dictionary.setLeaf(true);
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
			dictionary.setSort(form.getSort());
			dictionary.setNotes(form.getNotes());
			dictionaryService.updateDictionary(dictionary);
			log.info("修改字典项name:{}", dictionary.getName());
		}
		return ActionResponse.ok(dictionary);
	}
	
	@PostMapping("deleteDictionary")
	@ApiOperation(value = "删除字典目录项", notes = "删除字典目录项")
	@ResponseBody
	public ResponseEntity<ActionResponse<Dictionary>> deleteDictionary(String ids) {
		try {
			List<Integer> idList = ListUtils.strToList(ids);
			dictionaryService.deleteDictionary(idList);
			return ActionResponse.ok(null);
		} catch (JSONDataException e) {
			throw e;
		}
	}
	
}
