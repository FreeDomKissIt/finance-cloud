package com.qykj.finance.demo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.qykj.finance.core.persistence.model.BaseEnitity;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "t_demo")
public class Demo extends BaseEnitity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6971864760117451461L;

	private Integer status;// 状态
	private String name;// 名称
	private Date createDate;// 创建日期
	private Date updateDate;// 更新日期
	@Type(type="yes_no")
	private Boolean test = true;// 更新日期
}
