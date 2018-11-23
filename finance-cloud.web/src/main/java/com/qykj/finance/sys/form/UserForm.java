package com.qykj.finance.sys.form;

import lombok.Getter;
import lombok.Setter;

/**
 *  新增修改用户表单
 *  创 建 人: wenjing <br/>
 *  版 本 号: V1.0.0 <br/>
 */
@Getter
@Setter
public class UserForm {
	private Integer id;
	/** 帐号(用户名) **/
	private String username;
	/** 名称/真实姓名 **/
	private String name;
	/** 地址  **/
	private String address;
	/** 电话 **/
	private String phone;
	/** 邮箱地址  **/
	private String email;
	/**  单位id **/
	private Integer orgId;
	/** 角色id **/
	private Integer roleId;
	/** 授权情况  1:正常状态 2:锁定 **/
	private Integer state;
}
