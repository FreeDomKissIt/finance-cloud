package com.qykj.finance.core.util;

import lombok.Data;

/**
 *  SQL参数工具
 *  创 建 人: wenjing <br/>
 *  版 本 号: V1.0.0 <br/>
 */
@Data
public class SqlParam {
	private String sql;
	private Object[] params;
}
