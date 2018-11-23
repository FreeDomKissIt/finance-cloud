package com.qykj.finance.core.exception;

/**
 *  Controller层 响应Json数据的异常
 *  文件名: JSONDataException.java <br/>
 *  创 建 人: wenjing <br/>
 *  版 本 号: V1.0.0 <br/>
 */
public class JSONDataException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8509277045857640074L;

	public JSONDataException() {
		super();
	}

	public JSONDataException(int errorCode, Exception e) {
		super(errorCode, e);
	}

	public JSONDataException(int errorCode, String message) {
		super(errorCode, message);
	}
	
	public JSONDataException(String message) {
		super(message);
	}

}
