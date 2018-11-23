package com.qykj.finance.sys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qykj.finance.sys.model.Dictionary;

/**
 *  数据字典仓库
 *  创 建 人: wenjing <br/>
 *  版 本 号: V1.0.0 <br/>
 */
@Repository
public interface DictionaryRepository extends JpaRepository<Dictionary, Integer> {
	
	/**
	 * 查询全部 dictionary
	 * @return
	 * @author    wenjing8
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	List<Dictionary> findAll();

	/**
	 * 用父id查子节点
	 * @param id
	 * @return
	 * @author    wenjing8
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	List<Dictionary> findByParentIdOrderBySortAsc(Integer id);

	/**
	 * 用code查字典 
	 * @param code
	 * @return
	 * @author    wenjing8
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	Dictionary findByCode(String code);

	/**
	 * 用name查字典
	 * @param name
	 * @return
	 * @author    wenjing8
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	Dictionary findByName(String name);

	/**
	 * 用是否叶子节点 区分字典目录和详细字典
	 * @return
	 * @author    wenjing8
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	List<Dictionary> findByLeaf(Boolean leaf);
	
	/**
	 * 用code和parentId查询字典数据，保证在同一层级下的code不重复
	 * @param parentId
	 * @param code
	 * @return
	 * @author    wenjing8
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	List<Dictionary> findByParentIdAndCode(Integer parentId,String code);

	/**
	 *  用code和parentId查询字典数据
	 * @param parentId
	 * @param b
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	List<Dictionary> findByParentIdAndIsDeletedOrderBySortAsc(Integer parentId, boolean b);
	
}
