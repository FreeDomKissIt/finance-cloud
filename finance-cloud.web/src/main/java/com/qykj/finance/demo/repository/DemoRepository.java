package com.qykj.finance.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.qykj.finance.demo.model.Demo;


@Repository
public interface DemoRepository extends JpaRepository<Demo, Integer> {

	public List<Demo> findByStatus(Integer status);

	/**
	 * 根据状态查询
	 * @param status
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@Query("select t from Demo t where t.status = ?1")
	public List<Demo> findBy(Integer status);

	/**
	 * 根据状态查询
	 * @param status
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@Query("select t from Demo t where t.status= :status")
	public List<Demo> findByHQL(@Param("status") Integer status);

	/**
	 * 原生sql查询
	 * @param status
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@Query(value = "select t.* from t_demo t where t.status= :status", nativeQuery = true)
	public List<Demo> findBySQL(@Param("status") Integer status);

	/**
	 * 更新
	 * @param status
	 * @param id
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@Modifying
	@Query("update Demo t set t.status= ?1  where t.id= ?2")
	public void updateSatatusById(Integer status, Integer id);

}
