package com.qykj.finance.core.persistence.service.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import com.github.pagehelper.Page;
import com.qykj.finance.core.persistence.handler.BeanRecordResultSetHandler;
import com.qykj.finance.core.persistence.handler.MapRecordResultSetHandler;
import com.qykj.finance.core.persistence.service.BaseQueryService;
import com.qykj.finance.core.persistence.service.CommonQueryService;
import com.qykj.finance.core.util.BeanUtil;
import com.qykj.finance.core.util.SqlParam;

import lombok.extern.slf4j.Slf4j;

/**
 * 抽象分页辅助查询类
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
@Slf4j
public abstract class AbstractQueryServiceImpl<T> extends CommonQueryService implements BaseQueryService<T> {
	/**
	 * 泛型对象
	 */
	private Class<T> entityClass;

	/**
	 * Bean相关工具类
	 */
	@Autowired
	private BeanUtil beanUtil;

	@Autowired
	protected JpaRepository<T, Integer> repository;

	/**
	 * 构造函数
	 */
	public AbstractQueryServiceImpl() {
		this.entityClass = getEntityClass(getClass().getGenericSuperclass(), 0);
	}

	/**
	 * 获取泛型类
	 * 
	 * @param clazz
	 * @param index
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Class<T> getEntityClass(final Type clazz, int index) {
		try {
			Type[] types = ((ParameterizedType) clazz).getActualTypeArguments();
			if (types.length > 0) {
				return (Class<T>) types[index];
			}
		} catch (Exception ex) {
			// ignore
		}
		return null;
	}

	/**
	 * 
	 * sql查询列表 eg. sql=select * from demo where id=? params=1
	 * 
	 * @param sql
	 *            sql语句
	 * @param params
	 *            参数
	 * @return
	 * @author wenjing
	 * @since V1.0.0
	 */
	@Override
	public List<T> select(String sql, Object... params) {
		List<T> list = new ArrayList<T>();
		try {
			QueryRunner qr = new QueryRunner(dataSource);
			list = qr.query(sql, new BeanRecordResultSetHandler<T>(entityClass, beanUtil), params);
		} catch (Exception e) {
			log.error(sql + ":查询出错", e);
		}
		return list;
	}

	/**
	 * sql查询分页信息 eg. sql=select * from demo where id=? params=1
	 * 
	 * @param sql
	 *            sql语句
	 * @param pageNum
	 *            页码
	 * @param pageSize
	 *            分页大小
	 * @param needCount
	 *            是否返回总数
	 * @param params
	 *            参数
	 * @return
	 * @author wenjing
	 * @since V1.0.0
	 */
	@Override
	public Page<T> selectPage(String sql, int pageNum, int pageSize, boolean needCount, Object... params) {
		try {
			int start = (pageNum - 1) * pageSize;
			Object[] values = null;
			if (params != null) {
				values = Arrays.copyOf(params, params.length + 2);
				values[values.length - 2] = start;
				values[values.length - 1] = pageSize;

			} else {
				values = new Object[] { start, pageSize };
			}

			QueryRunner qr = new QueryRunner(dataSource);
			long totol = -1l;
			if (needCount)
				totol = this.countBySql(sql, params);

			Page<T> page = new Page<T>();
			List<T> list = qr.query(sql + " limit ?,?", new BeanRecordResultSetHandler<T>(entityClass, beanUtil),
					values);
			page.setPageNum(pageNum);
			page.setPageSize(pageSize);
			page.setTotal(totol);
			page.addAll(list);

			return page;
		} catch (Exception e) {
			log.error(sql + ":查询出错", e);
		}
		return null;
	}

	/**
	 * 
	 * sql查询分页列表 返回总页数 eg. sql=select * from demo where id=? params=1
	 * 
	 * @param sql
	 *            sql语句
	 * @param pageNum
	 *            页码
	 * @param pageSize
	 *            分页大小
	 * @param params
	 *            参数
	 * @return
	 * @author wenjing
	 * @since V1.0.0
	 */
	@Override
	public Page<T> selectPage(String sql, int pageNum, int pageSize, Object... params) {
		return selectPage(sql, pageNum, pageSize, true, params);
	}
	/**
	 * 
	 * sql查询分页列表 返回总页数,记录数
	 * @param pageNum  页码
	 * @param pageSize 分页大小
	 * @param t 查询条件
	 * @return
	 * @author    wenjing
	 * @since     V1.0.0
	 */
	@Override
	public Page<T> selectPage(int pageNum, int pageSize, T t) {
		SqlParam sqlParam = BeanUtil.getSqlParam(t);
		return selectPage(sqlParam.getSql(), pageNum, pageSize, sqlParam.getParams());
	}

	/**
	 * 通用保存
	 * @param t
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@Override
	public T save(T t) {
		return repository.save(t);
	}
	
	/**
	 * 通用删除
	 * @param t
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@Override
	public void delete(T t) {
		 repository.delete(t);
	}
	
	
	/**
	 * 通用删除
	 * @param id
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@Override
	public void delete(Integer id) {
		 repository.delete(id);
	}
	
	/**
	 * 通用批量删除
	 * @param id
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@Override
	public void delete(List<Integer> idList) {
		for (Integer id : idList) {
			 repository.delete(id);
		}
	}
	
	/**
	 * 根据id查找
	 * @param id
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@Override
	public T findOne(Integer id) {
		 return repository.findOne(id);
	}
	
	/**
	 * 获取数据库时间 mysql
	 * @return
	 * @author    wenjing
	 * @since     V1.0.0
	 */
	@Override
	public Date getSysDate() {
		QueryRunner qr = new QueryRunner(dataSource);
		List<Map<String, Object>> list;
		try {
			list = qr.query("select current_timestamp", new MapRecordResultSetHandler());
			Map<String, Object> map = list.get(0);
			Date date = (Date) map.get("current_timestamp");
			return date;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new Date();
	}

}
