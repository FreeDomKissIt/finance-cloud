package com.qykj.finance.sys.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.qykj.finance.core.persistence.model.BaseEnitity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *  创 建 人: wenjing <br/>
 *  版 本 号: V1.0.0 <br/>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "dictionary")
public class Dictionary extends BaseEnitity {

	private static final long serialVersionUID = 8791608133117690410L;
	/** 名称 **/
	@Column(length = 50)
	private String name;
	/** 编码/内码 **/
	@Column(length = 10)
	private String code;
	/** 类型名，比如案件类型：caseType**/
	private String mainType;
	/** 描述 **/
	@Column(length = 255)
	private String notes;
	/** 父id，一级菜单该值为0 **/
	@Column(length = 11)
	private Integer parentId;
	/** 排序号 **/
	@Column(length = 10)
	private Integer sort;
	/** 是否叶子节点  用于区分字典目录和详细数据**/
	@Type(type="yes_no")
	private Boolean leaf;
	/** 是否删除标识**/
	@Type(type="yes_no")
	private Boolean isDeleted;
}
