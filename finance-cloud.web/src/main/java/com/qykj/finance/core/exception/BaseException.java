package com.qykj.finance.core.exception;

/**
 * 项目异常基类 文件名: BaseException.java <br/>
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
public class BaseException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4958784133837290696L;
	private int errorCode = 400;

	public int getErrorCode() {
		return errorCode;
	}

	public BaseException() {

	}

	public BaseException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public BaseException(int errorCode, Exception e) {
		super(e);
		this.errorCode = errorCode;
	}
	
	public BaseException(String message) {
		super(message);
	}
	
	public BaseException(Exception e) {
		super(e);
	}
}
