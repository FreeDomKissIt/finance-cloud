package com.qykj.finance.sys.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.qykj.finance.core.persistence.model.BaseEnitity;

import lombok.Getter;
import lombok.Setter;

/**
 *  快捷查询参数对象
 *  创 建 人: wenjing <br/>
 *  版 本 号: V1.0.0 <br/>
 */
@Getter
@Setter
@Entity
@Table(name = "quick_query_params")
public class QuickQueryParams extends BaseEnitity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5068172754170670833L;
	/** 查询类型 **/
	@Column(length=10)
	private String queryType;
	/** 查询参数json字符串  **/
	@Column(length=4000)
	private String jsonString;
}
