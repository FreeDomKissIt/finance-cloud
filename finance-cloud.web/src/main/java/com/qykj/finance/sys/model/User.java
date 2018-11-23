package com.qykj.finance.sys.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.qykj.finance.core.persistence.model.BaseEnitity;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户 文件名: User.java <br/>
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
@Getter
@Setter
@Entity
@Table(name = "user")
public class User extends BaseEnitity {
	
	private static final long serialVersionUID = 8847280512500101457L;
	
	/** 帐号(用户名) **/
	@Column(unique = true, length = 50)
	private String username;
	/** 密码 **/
	@Column(length = 255)
	private String password;
	/** 名称/真实姓名 **/
	@Column(length = 50)
	private String name;
	/** 加密密码的盐 **/
	@Column(length = 100)
	private String salt;
	/** 地址 **/
	@Column(length = 255)
	private String address;
	/** 电话 **/
	@Column(length = 50)
	private String phone;
	/** 邮箱地址 **/
	@Column(length = 100)
	private String email;
	/** 部门 **/
	@ManyToOne
	@JoinColumn(name = "orgId")
	private Org org;
	/** 角色 **/
	@ManyToOne
	@JoinColumn(name = "roleId")
	private Role role;
	/** 授权情况 1:正常状态 0:锁定 **/
	private Integer state;
	
	public boolean isSys() {
		return "sysadmin".equals(username);
	}
}
