package com.qykj.finance.demo.form;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DemoCreateForm {
	@Length(min = 1, max = 20, message = "demo名字长度异常")
	@Pattern(regexp = "[\\D]*", message = "名字不能包含数字")
	private String name;//名称
	private Integer status;//状态
}
