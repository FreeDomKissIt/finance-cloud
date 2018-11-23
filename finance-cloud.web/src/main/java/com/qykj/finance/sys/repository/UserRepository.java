package com.qykj.finance.sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.qykj.finance.sys.model.User;

/**
 *  用户仓库
 *  创 建 人: wenjing <br/>
 *  版 本 号: V1.0.0 <br/>
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	// 查询用户列表语句
	public static final String QUERY_LIST = "select id,name,username,phone,orgId,roleId,state from user where username!='sysadmin'";
	// 查询用户列表语句
	public static final String QUERY_ROOT_LIST = "select id,name,username,phone,orgId,roleId,state from user";
	// 根据orgId查询用户列表语句
	public static final String QUERY_LIST_BY_ORG_ID = "select id,name,username,orgId from user where orgId=?";
	
	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	User findByUsername(String username);
	/**
	 * 根据用户名和密码查找用户
	 * @param username
	 * @param username
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	User findByUsernameAndPassword(String username, String password);

	/**
	 * 更新用户的密码
	 * @param password
	 * @param id
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@Modifying
	@Query("update User t set t.password= ?1  where t.id= ?2")
	int updatePasswordById(String password, Integer id);
}
