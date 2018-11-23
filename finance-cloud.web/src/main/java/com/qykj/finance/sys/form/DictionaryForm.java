
package com.qykj.finance.sys.form;

import lombok.Getter;
import lombok.Setter;

import com.qykj.finance.sys.model.Dictionary;

/**
 * 字典form
 *  创 建 人: wenjing8 <br/>
 *  版 本 号: V1.0.0 <br/>
 */
@Setter
@Getter
public class DictionaryForm extends Dictionary{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4080311831111659932L;
	private String oprateType;//操作类型 新增1、修改2、详情3
}
