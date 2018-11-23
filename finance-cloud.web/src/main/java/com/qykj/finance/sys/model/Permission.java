package com.qykj.finance.sys.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.qykj.finance.core.persistence.model.BaseEnitity;

import lombok.Getter;
import lombok.Setter;

/**
 *  权限
 *  创 建 人: wenjing <br/>
 *  版 本 号: V1.0.0 <br/>
 */
@Getter
@Setter
@Entity
@Table(name = "permission")
public class Permission extends BaseEnitity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7143792036837414873L;
	/** 权限名 **/
	@Column(length = 50)
	private String name;
	/** 权限编码 **/
	@Column(length = 50)
	private String code;
	/** 备注 **/
	@Column(length = 250)
	private String note;

}
