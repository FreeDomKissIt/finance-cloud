package com.qykj.finance.core.view;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 文件名: Combox.java <br/>
 * Copyright (c) 2017-2018 HangZhou qykj System Technology Co., Ltd. All
 * Right Reserved. <br/>
 * 文件编号: <br/>
 * 创 建 人: wenjing <br/>
 * 日 期: 2018年2月27日 <br/>
 * 修 改 人: wenjing <br/>
 * 日 期: 2018年2月27日 <br/>
 * 描 述: <br/>
 * 版 本 号: V1.0.0 <br/>
 */
@Setter
@Getter
public class Combox<T> {
	private List<T> options;
	public Combox(List<T> options) {
		this.options = options;
	}
}
