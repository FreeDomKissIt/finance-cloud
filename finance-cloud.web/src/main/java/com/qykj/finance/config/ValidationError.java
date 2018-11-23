package com.qykj.finance.config;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *  错误校验对象
 *  创 建 人: wenjing <br/>
 *  版 本 号: V1.0.0 <br/>
 */
@Data
@Accessors(chain = true)
public class ValidationError {
    String message;
    String name;
    Object value;
}
