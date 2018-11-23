
package com.qykj.finance.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.qykj.finance.common.CommonConstant;
import com.qykj.finance.core.cache.CacheContainer;
import com.qykj.finance.core.cache.sys.DictionaryCache;
import com.qykj.finance.core.exception.JSONDataException;
import com.qykj.finance.core.persistence.service.impl.AbstractQueryServiceImpl;
import com.qykj.finance.sys.form.DictionaryTree;
import com.qykj.finance.sys.model.Dictionary;
import com.qykj.finance.sys.repository.DictionaryRepository;
import com.qykj.finance.sys.service.DictionaryService;

import lombok.extern.slf4j.Slf4j;
/**
 * 字典管理service
 *  创 建 人: wenjing8 <br/>
 *  版 本 号: V1.0.0 <br/>
 */
@Service
@Slf4j
public class DictionaryServiceImpl extends AbstractQueryServiceImpl<Dictionary> implements DictionaryService{
	@Autowired
	DictionaryRepository dictionaryRepository;
	/**
	 * 添加新数据字典
	 * 
	 * @param dictionary
	 * @return
	 * @since 1.0.0
	 */
	@Override
	public Dictionary addDictionary(Dictionary dictionary) throws JSONDataException{
		//校验重复code
		Boolean result = validateCode(dictionary.getParentId(), dictionary.getCode());
		if(result){//不存在重复
			return dictionaryRepository.save(dictionary);
		}else{
			throw new JSONDataException("存在重复code");
		}
		
	}

	/**
	 * 修改数据字典
	 * 
	 * @param dictionary
	 * @return
	 * @since 1.0.0
	 */
	@Override
	public Dictionary updateDictionary(Dictionary dictionary) {
		return dictionaryRepository.save(dictionary);
	}

	/**
	 * 用code查询字典
	 * 
	 * @param dictionary
	 * @return
	 * @since 1.0.0
	 */
	@Override
	public Dictionary selectDictionaryByCode(String code) {
		return dictionaryRepository.findByCode(code);
	}

	/**
	 * 用name查询字典
	 * 
	 * @param dictionary
	 * @return
	 * @since 1.0.0
	 */
	@Override
	public Dictionary selectDictionaryByName(String name) {
		return dictionaryRepository.findByName(name);
	}

	/**
	 * 用id查询字典
	 * 
	 * @param id
	 * @return
	 * @since 1.0.0
	 */
	@Override
	public Dictionary selectDictionaryById(Integer id) {
		return dictionaryRepository.findOne(id);
	}

	/**
	 * 删除数据字典
	 * 
	 * @param dictionary
	 * @return
	 * @since 1.0.0
	 */
	@Override
	public String deleteDictionary(Dictionary dictionary) throws Exception {
		String hasLeaf = hasLeaf(dictionary);
		if(CommonConstant.SUCCESS.equals(hasLeaf)){
			Dictionary local = selectDictionaryById(dictionary.getId());//查询数据库
			local.setIsDeleted(true);//设置删除标识为已删除
			dictionaryRepository.save(local);
		}else{
			return CommonConstant.FAIL;
		}
		return "";
	}
	
	/**
	 * 判断是否有下级节点
	 * @param dictionary
	 * @return
	 * @author    wenjing8
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	private String hasLeaf(Dictionary dictionary){
		List<Dictionary> data = selectDictionaryByParentId(dictionary.getId());
		if(data!=null && data.size()>0){
			return CommonConstant.FAIL;//存在下级节点，不能删除
		}else{
			return CommonConstant.SUCCESS;//没有下级节点，可以删除
		}
	}

	/**
	 * 用父id查子节点 默认用sort升序排列
	 * @param parentId
	 * @return
	 * @author    wenjing8
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	public List<Dictionary> selectDictionaryByParentId(Integer parentId){
		//return dictionaryRepository.findByParentIdOrderBySortAsc(parentId);
		
		return dictionaryRepository.findByParentIdAndIsDeletedOrderBySortAsc(parentId,false);
	}
	
	/**
	 * 加载二级树结构字典数据  ，字典里只有详细字典数据是leaf=true,其他都是false
	 * @param 
	 * @return
	 * @author    wenjing8
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@Override
	public DictionaryTree dictionaryTree() {
		DictionaryTree root = new DictionaryTree();// root 根节点 虚拟的跟节点
		List<DictionaryTree> tree = new ArrayList<DictionaryTree>(); // 一级字典目录存放
		Boolean leaf = false;// 查询非叶子节点
		List<Dictionary> data = dictionaryRepository.findByLeaf(leaf);
		// 封装返回数据格式
		for (Dictionary dictionary : data) {// 遍历parentId=0时为一级目录 其他的是二级目录
			DictionaryTree node = new DictionaryTree();
			if (dictionary.getParentId() == 0 && !dictionary.getIsDeleted()) {// 一级目录
				node.setLabel(dictionary.getName());
				node.setParent(true);
				node.setOpen(true);
				node.setChecked(true);
				node.setControl(true);
				node.setModule(true);
				node.setId(dictionary.getId());
				node.setParentId(dictionary.getParentId());
				node.setLevel(1);
				node.setSort(dictionary.getSort());
				List<DictionaryTree> childs = new ArrayList<DictionaryTree>();
				for (Dictionary dictChild : data) {// 遍历设置下级节点 二级目录
					DictionaryTree child = new DictionaryTree();
					if (dictionary.getId().equals(dictChild.getParentId()) && !dictChild.getIsDeleted()) {
						child.setLabel(dictChild.getName());
						child.setParent(false);
						child.setChecked(true);
						child.setControl(true);
						child.setModule(true);
						child.setId(dictChild.getId());
						child.setParentId(dictChild.getParentId());
						child.setLevel(2);
						child.setSort(dictChild.getSort());
						child.setChildren(new ArrayList<DictionaryTree>());
						childs.add(child);
					}
				}
				node.setChildren(childs);
			} else {
				continue;
			}
			tree.add(node);
		}
		root.setLabel("字典目录");
		root.setParent(true);
		root.setChildren(tree);
		root.setOpen(true);
		root.setChecked(true);
		root.setId(0);
		return root;
	}

	/**
	 * 字典目录管理的分页查询
	 * @param pageNum 页码
	 * @return
	 * @author    wenjing8
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@Override
	public Page<Dictionary> dictionaryPageQuery(Integer pageNum,Integer pageSize) {
		return this.selectPage("select * from dictionary where parentId = 0 and isDeleted ='N' ", pageNum, pageSize);
	}
	
	/**
	 * 详细字典数据的分页查询
	 * @param pageNum 页码
	 * @param pageSize 分页大小
	 * @return
	 * @author    wenjing8
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@Override
	public Page<Dictionary> dictionaryDetailPageQuery(Integer pageNum,Integer pageSize,Integer parentId) {
		String sql = "select * from dictionary where leaf ='Y' and isDeleted ='N' and parentId = "+parentId;
		return this.selectPage(sql, pageNum, pageSize);
	}
	
	/**
	 * 用code查子节点
	 * @param code
	 * @return
	 * @author    wenjing8
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	public List<Dictionary> selectDictionaryChildsByCode(String code){
		List<Dictionary> dicts = new ArrayList<Dictionary>();
		Dictionary dictionary = selectDictionaryByCode(code);//先查自己，再用自己的id查下级
		if(null == dictionary){//当字典里不存在该code时，返回空列表
			return dicts;
		}
		dicts = selectDictionaryByParentId(dictionary.getId());
		return dicts;
	}
	/**
	 * 分配子条目查询
	 * @param pageNum 页码
	 * @param pageSize 分页大小
	 * @param parentId  跳转的时候带入父id
	 * @return
	 * @author    wenjing8
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@Override
	public Page<Dictionary> dictionaryChildPageQuery(Integer pageNum,Integer pageSize,Integer parentId) {
		String sql = "select * from dictionary where isDeleted = 'N' and parentId = ?";
		return this.selectPage(sql, pageNum, pageSize, parentId);
	}
	/**
	 * 校验code唯一性 , 保证同一个parentId下code不能重复
	 * @param parentId 父id
	 * @param code 字典code
	 * @return false 表示存在重复code，true表示不存在重复code
	 * @author    wenjing8
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@Override
	public Boolean validateCode(Integer parentId,String code) {
		Boolean ret = null;
		List<Dictionary> dicts = dictionaryRepository.findByParentIdAndCode(parentId, code);
		if(null != dicts && dicts.size()>0){
			ret = false;
		}else{
			ret = true;
		}
		return ret;
	}

	/**
	 * 查询所有字典
	 */
	@Override
	public List<Dictionary> getAllDictionary() {
		return dictionaryRepository.findAll();
	}

	
	public static String ROOT_CODE = "0";// 根字典编码
	public static int ROOT_ID = 0;// 根字典id
	/**
	 * 加载字典缓存
	 * @return 字典缓存  {"id":DictionaryCache}
	 * @author    wenjing8
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@Override
	public void loadDictionaryCache() {
		List<Dictionary> dicts = getAllDictionary();//查询所有字典
		for (Dictionary dictionary : dicts) {
			DictionaryCache cache = new DictionaryCache();
			List<Dictionary> childs = new ArrayList<Dictionary>();
			cache.setDict(dictionary);//cache里的字典本身
			childs = selectDictionaryByParentId(dictionary.getId());
			cache.setDicts(childs);//cache里的字典下级
			CacheContainer.put(dictionary.getId().toString(), cache);//放到缓存
		}
		DictionaryCache root = new DictionaryCache();//虚拟根节点 id=0 , code =0
		Dictionary rootDict = new Dictionary();
		rootDict.setId(ROOT_ID);
		rootDict.setCode(ROOT_CODE);
		root.setDict(rootDict);//虚拟根节点
		root.setDicts(selectDictionaryByParentId(rootDict.getId()));//一级菜单
		log.info("加载字典到缓存：{}", root.getDict().getId());
		CacheContainer.put(rootDict.getId().toString(), root);//放到缓存
	}

	/**
	 * 缓存里用id查询字典
	 * 
	 * @param id
	 * @return
	 * @since 1.0.0
	 */
	@Override
	public Dictionary selectDictionaryByIdFromCache(String id) {
		DictionaryCache cache = (DictionaryCache)CacheContainer.get(id.toString());
		return cache.getDict();
	}

	/**
	 * 缓存里用parentId查询字典 默认用sort升序排列
	 * 
	 * @param parentId
	 * @return
	 * @since 1.0.0
	 */
	@Override
	public List<Dictionary> selectDictionaryByParentIdFromCache(String parentId) {
		DictionaryCache cache = (DictionaryCache)CacheContainer.get(parentId.toString());
		return cache.getDicts();
	}
	
	/**
	 * 根据编码查字典
	 * @param code
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@Override
	public Dictionary findByCode(String code) {
		return dictionaryRepository.findByCode(code);
	}

	@Override
	public void deleteDictionary(List<Integer> idList) {
		for (Integer id : idList) {
			Dictionary dictionary = dictionaryRepository.findOne(id);
			String hasLeaf = hasLeaf(dictionary);
			if(CommonConstant.SUCCESS.equals(hasLeaf)){
				dictionary.setIsDeleted(true);//设置删除标识为已删除
				dictionaryRepository.save(dictionary);
			}else{
				throw new JSONDataException("存在子节点,请先删除子节点");
			}
		}
	}
}
