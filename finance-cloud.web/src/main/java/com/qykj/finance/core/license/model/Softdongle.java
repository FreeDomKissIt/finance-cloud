package com.qykj.finance.core.license.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.qykj.finance.core.persistence.model.BaseEnitity;

import lombok.Getter;
import lombok.Setter;

/**
 * 软件狗实体 
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
@Getter
@Setter
@Entity
@Table(name = "softdongle")
public class Softdongle extends BaseEnitity {

	private static final long serialVersionUID = 7499966287970927051L;
	public String license;//证书
	public Date expireDate;//有效期
}
