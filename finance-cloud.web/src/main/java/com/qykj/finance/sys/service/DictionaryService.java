package com.qykj.finance.sys.service;

import java.util.List;

import com.github.pagehelper.Page;
import com.qykj.finance.core.exception.JSONDataException;
import com.qykj.finance.core.persistence.service.BaseQueryService;
import com.qykj.finance.sys.form.DictionaryTree;
import com.qykj.finance.sys.model.Dictionary;

/**
 * 数据字典接口 
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
public interface DictionaryService extends BaseQueryService<Dictionary> {
	/**
	 * 添加新数据字典 该方法已经校验了code唯一性
	 * 
	 * @param dictionary
	 * @return
	 * @throws Exception 
	 * @since 1.0.0
	 */
	public Dictionary addDictionary(Dictionary dictionary) throws JSONDataException;
	/**
	 * 修改数据字典
	 * 
	 * @param dictionary
	 * @return
	 * @since 1.0.0
	 */
	public Dictionary updateDictionary(Dictionary dictionary);
	/**
	 * 删除数据字典
	 * 
	 * @param dictionary
	 * @return
	 * @since 1.0.0
	 */
	public String deleteDictionary(Dictionary dictionary) throws Exception;
	/**
	 * 用id查询字典
	 * 
	 * @param id
	 * @return
	 * @since 1.0.0
	 */
	public Dictionary selectDictionaryById(Integer id);
	/**
	 * 用code查询字典
	 * 
	 * @param dictionary
	 * @return
	 * @since 1.0.0
	 */
	public Dictionary selectDictionaryByCode(String code);
	/**
	 * 用name查询字典
	 * 
	 * @param dictionary
	 * @return
	 * @since 1.0.0
	 */
	public Dictionary selectDictionaryByName(String name);
	/**
	 * 用parentId查询字典 默认用sort升序排列
	 * 
	 * @param parentId
	 * @return
	 * @since 1.0.0
	 */
	public List<Dictionary> selectDictionaryByParentId(Integer parentId);
	
	/**
	 * 加载二级树结构字典数据
	 * @return
	 * @author    wenjing8
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	public DictionaryTree dictionaryTree();
	/**
	 * 字典目录管理的分页查询
	 * 
	 * @param pageNum 分页码
	 * @param pageSize 分页大小
	 * @return
	 * @since 1.0.0
	 */
	public Page<Dictionary> dictionaryPageQuery(Integer pageNum,Integer pageSize);
	/**
	 * 详细字典数据的分页查询
	 * 
	 * @param pageNum 分页码
	 * @param pageSize 分页大小
	 * @param parentId 父节点id
	 * @return
	 * @since 1.0.0
	 */
	public Page<Dictionary> dictionaryDetailPageQuery(Integer pageNum,Integer pageSize,Integer parentId);
	/**
	 * 用code查子节点
	 * @param code
	 * @return
	 * @author    wenjing8
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	public List<Dictionary> selectDictionaryChildsByCode(String code);
	/**
	 * 分配子条目 查询页面
	 * @param pageNum
	 * @param pageSize
	 * @param parentId
	 * @return
	 * @author    wenjing8
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	public Page<Dictionary> dictionaryChildPageQuery(Integer pageNum,
			Integer pageSize,Integer parentId);
	
	/**
	 * 用code和parentId查询字典数据，保证在同一层级下的code不重复
	 * @param code
	 * @return false 表示存在重复code，true表示不存在重复code
	 * @author    wenjing8
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	public Boolean validateCode(Integer parentId,String code);
	
	/**
	 * 查询所有字典
	 * @return
	 * @author    wenjing8
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	public List<Dictionary> getAllDictionary();
	
	/**
	 * 加载字典缓存 字典缓存  {"id":DictionaryCache} 虚拟根节点 id=0 , code =0
	 * @author    wenjing8
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	public void loadDictionaryCache();
	
	/**
	 * 缓存里用id查询字典
	 * 
	 * @param id
	 * @return
	 * @since 1.0.0
	 */
	public Dictionary selectDictionaryByIdFromCache(String id);
	
	/**
	 * 缓存里用parentId查询字典 默认用sort升序排列
	 * 
	 * @param parentId
	 * @return
	 * @since 1.0.0
	 */
	public List<Dictionary> selectDictionaryByParentIdFromCache(String parentId);
	
	/**
	 * 根据编码查字典
	 * @param code
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	public Dictionary findByCode(String code);
	/**
	 * 删除列表
	 * @param idList
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	public void deleteDictionary(List<Integer> idList);
	
}
