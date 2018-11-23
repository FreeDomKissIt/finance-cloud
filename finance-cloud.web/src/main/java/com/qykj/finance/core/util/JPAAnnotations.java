package com.qykj.finance.core.util;

import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Type;

import lombok.Data;

/**
 *  Hibernate ORM 相关注解
 *  创 建 人: wenjing <br/>
 *  版 本 号: V1.0.0 <br/>
 */
@Data
public class JPAAnnotations {
	Column column;
	OneToOne oneToOne;
	JoinColumn joinColumn;
	ManyToMany manyToMany;
	ManyToOne manyToOne;
	OneToMany oneToMany;
	Enumerated enumerated;
	Type type;
}
