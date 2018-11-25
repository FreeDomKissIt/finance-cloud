package com.qykj.finance.sys.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.qykj.finance.core.persistence.model.BaseEnitity;

import lombok.Getter;
import lombok.Setter;

/**
 * 单位 
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
@Getter
@Setter
@Entity
@Table(name = "org")
public class Org extends BaseEnitity {

	private static final long serialVersionUID = 8791608133117690410L;
	/** 单位名 **/
	@Column(length = 200)
	private String name;
	/** 单位编码 **/
	@Column(length = 10)
	private String code;
}
