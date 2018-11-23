package com.qykj.finance.core.persistence.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

/**
 *  模型层父类
 *  创 建 人: wenjing <br/>
 *  版 本 号: V1.0.0 <br/>
 */
@Getter
@Setter
@MappedSuperclass
public abstract class BaseEnitity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7141327503923152455L;
	@Id
	@GeneratedValue
	private Integer id;
}
