package com.qykj.finance.core.exception;

/**
 *  Controller层页面抛出异常
 *  创 建 人: wenjing <br/>
 *  版 本 号: V1.0.0 <br/>
 */
public class PageException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3357652990235533686L;

	public PageException() {
		super();
	}

	public PageException(int errorCode, Exception e) {
		super(errorCode, e);
	}

	public PageException(int errorCode, String message) {
		super(errorCode, message);
	}
	
	public PageException(String message) {
		super(message);
	}
	public PageException( Exception e) {
		super(e);
	}
}
