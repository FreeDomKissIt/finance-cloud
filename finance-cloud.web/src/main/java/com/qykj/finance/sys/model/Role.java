package com.qykj.finance.sys.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.qykj.finance.core.persistence.model.BaseEnitity;

import lombok.Getter;
import lombok.Setter;

/**
 * 角色 文件名: Role.java <br/>
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
@Getter
@Setter
@Entity
@Table(name = "role")
public class Role extends BaseEnitity {

	private static final long serialVersionUID = 7159616148173196145L;
	/** 角色名 **/
	@Column(length = 50)
	private String name;
	/** 角色编码 **/
	@Column(length = 50)
	private String code;
	/** 备注 **/
	@Column(length = 250)
	private String note;
	@OneToMany(fetch = FetchType.EAGER)
	private Set<RoleMenu> roleMenus;
	/** 授权情况  1:正常状态 0:锁定 **/
	private Integer state;
	/** 超级系统角色  **/
	private Integer isRoot;
}
