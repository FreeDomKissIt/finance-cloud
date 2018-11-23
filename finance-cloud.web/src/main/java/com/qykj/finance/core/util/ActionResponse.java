package com.qykj.finance.core.util;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.http.ResponseEntity;

import com.qykj.finance.common.CommonConstant;

/**
 *  响应对象
 *  创 建 人: wenjing <br/>
 *  版 本 号: V1.0.0 <br/>
 */
@Accessors(chain = true)
@Getter
@Setter
public class ActionResponse<T> {
	/**
	 * 响应代码
	 */
	private int code = 200;
	/**
	 * 响应成功与否
	 */
	private boolean success = true;
	/**
	 * 响应信息
	 */
	private String message = "";
	/**
	 * 响应数据
	 */
	private T data;

	/**
	 * 构造响应
	 * @param data
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	public static <T> ResponseEntity<ActionResponse<T>> ok(T data) {
		return ResponseEntity.ok(new ActionResponse<T>().setData(data));
	}

	/**
	 * 构造响应
	 * @param code
	 * @param message
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	public static <T> ResponseEntity<ActionResponse<T>> fail(int code, String message) {
		return ResponseEntity.ok(new ActionResponse<T>().setCode(code).setMessage(message).setSuccess(false));
	}
	
	/**
	 * 构造响应
	 * @param code
	 * @param message
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	public static <T> ResponseEntity<ActionResponse<T>> fail(String message) {
		return ResponseEntity.ok(new ActionResponse<T>().setCode(CommonConstant.FAILURE_CODE).setMessage(message).setSuccess(false));
	}
	

	/**
	 * 构造响应
	 * @param code
	 * @param message
	 * @param data
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	public static <T> ResponseEntity<ActionResponse<T>> fail(int code, String message, T data) {
		return ResponseEntity.ok(new ActionResponse<T>().setCode(code).setMessage(message).setData(data).setSuccess(false));
	}

	/**
	 * 构造响应
	 * @param data
	 * @return
	 * @since 1.0
	 */
	public static <T> ActionResponse<T> create(T data) {
		ActionResponse<T> ar = new ActionResponse<T>();
		ar.setData(data);
		return ar;
	}
	
	public static <T> ActionResponse<T> createFail(T data) {
		ActionResponse<T> ar = new ActionResponse<T>();
		ar.setData(data);
		ar.setSuccess(false);
		return ar;
	}
}