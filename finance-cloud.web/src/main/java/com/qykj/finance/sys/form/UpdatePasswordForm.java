package com.qykj.finance.sys.form;

import lombok.Getter;
import lombok.Setter;

/**
 *  新增修改用户表单
 *  创 建 人: wenjing <br/>
 *  版 本 号: V1.0.0 <br/>
 */
@Getter
@Setter
public class UpdatePasswordForm {
	private String oldPassword;
	private String newPassword;
}
