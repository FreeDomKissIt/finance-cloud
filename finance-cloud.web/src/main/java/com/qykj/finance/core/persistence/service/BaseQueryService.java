package com.qykj.finance.core.persistence.service;

import java.util.Date;
import java.util.List;

import com.github.pagehelper.Page;
/**
 *  基本查询服务接口
 *  创 建 人: wenjing <br/>
 *  版 本 号: V1.0.0 <br/>
 */
public interface BaseQueryService<T> {

	/**
	 * 通用保存
	 * @param t
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	T save(T t);
	/**
	 * 
	 * sql查询列表
	 * eg. sql=select * from demo where id=? params=1
	 * @param sql sql语句
	 * @param params 参数
	 * @return
	 * @author    wenjing
	 * @see       com.qykj.facebigdata.persistence.service.impl.AbstractQueryServiceImpl<T>
	 * @since     V1.0.0
	 */
	List<T> select(String sql, Object... params);

	/**
	 * sql查询分页信息
	 * eg. sql=select * from demo where id=? params=1
	 * @param sql sql语句
	 * @param pageNum 页码
	 * @param pageSize 分页大小
	 * @param needCount 是否返回总数
	 * @param params 参数
	 * @return
	 * @author    wenjing
	 * @see       com.qykj.facebigdata.persistence.service.impl.AbstractQueryServiceImpl<T>
	 * @since     V1.0.0
	 */
	Page<T> selectPage(String sql, int pageNum, int pageSize, boolean needCount, Object... params);

	/**
	 * 
	 * sql查询分页列表 返回总页数
	 * eg. sql=select * from demo where id=? params=1
	 * @param sql sql语句
	 * @param pageNum 页码
	 * @param pageSize 分页大小
	 * @param params 参数
	 * @return
	 * @author    wenjing
	 * @see       com.qykj.facebigdata.persistence.service.impl.AbstractQueryServiceImpl<T>
	 * @since     V1.0.0
	 */
	Page<T> selectPage(String sql, int pageNum, int pageSize, Object... params);

	/**
	 * 
	 * sql查询分页列表 返回总页数,记录数
	 * @param pageNum  页码
	 * @param pageSize 分页大小
	 * @param t 查询条件
	 * @return
	 * @author    wenjing
	 * @see       com.qykj.facebigdata.persistence.service.impl.AbstractQueryServiceImpl<T>
	 * @since     V1.0.0
	 */
	Page<T> selectPage(int pageNum, int pageSize, T t);
	
	/**
	 * 获取数据库时间 mysql
	 * @return
	 * @author    wenjing
	 * @since     V1.0.0
	 */
	Date getSysDate();
	/**
	 * 通用删除
	 * @param id
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	void delete(Integer id);
	
	/**
	 * 通用批量删除
	 * @param id
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	void delete(List<Integer> idList);
	/**
	 * 通用删除
	 * @param t
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	void delete(T t);
	
	/**
	 * 根据id查找
	 * @param id
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	T findOne(Integer id);
}