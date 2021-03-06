package com.qykj.finance.core.exception;

public class CMSException extends PageException{

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -4333422053514933760L;

	/**
	 * 数据库连接异常
	 */
	public static final int SERVER_DB_DIS_CONNECT = 1100001;
	
	/**
	 * req参数不足
	 */
	public static final int SERVER_REQ_REQUIRED = 1100003;
	
	/**
	 * 没有对应的服务
	 */
	public static final int SERVER_NOT_EXITS = 1100004;
	
	/**
	 * 没有对应的设备
	 */
	public static final int DEVICE_NO_EXITS = 1100005;
	
	/**
	 * 请求参数不对
	 */
	public static final int INSERT_REQ_REQUIRED = 1100006;
	
	/**
	 * 请求对象对空
	 */
	public static final int OBJECT_REQ_ISNULL = 1100007;
	
	/**
	 * 查不到对应的报警
	 */
	public static final int ALARM_NO_EXITS = 1100008;
	
	/**
	 * 没有对应的录像信息
	 */
	public static final int RECORDS_NO_EXITS = 1100009;
	
	/**
	 * 没有对应的中心系统信息
	 */
	public static final int SYSTEMINFO_NO_EXITS = 1100010;
	
	/**
	 * 没有对应的用户信息
	 */
	public static final int USERS_NO_EXITS = 1100011;
	
	/**
	 * 授权信息未更新，授权失效
	 */
	public static final int SOFT_DOG_INVALID 			= 1200001;
	
	/**
	 * 试用版本，授权过期
	 */
	public static final int SOFT_DOG_EXPIRES 			= 1200002;
	
	/**
	 * 试用版本,试用时间不存在
	 */
	public static final int SOFT_DOG_DESCRIBE_NULL	 	= 1200003;
	
	/**
	 * 试用版本,试用时间错误
	 */
	public static final int SOFT_DOG_DESCRIBE_ERR 		= 1200004;
	
	/**
	 * 未知授权类型
	 */
	public static final int SOFT_DOG_UNKNOWN_VALUE 		= 1200005;
	
	/**
	 * 插入 数据不足
	 */
	public static final int INT_REQ_REQUIRED = 4100002;
	
	private int errorCode;
	
	public int getErrorCode() {
		return errorCode;
	}
	
	public CMSException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public CMSException(int errorCode, Exception e) {
		super(e);
		this.errorCode = errorCode;
	}
}
