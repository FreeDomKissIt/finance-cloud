package com.qykj.finance.core.util;

public final class Constants {
	public static final int ROOT_ORG_PARENT = 0;
	public static final int ROOT_ORG_ID = 1;
	public static final String ROOT_ORG_CODE = "000000000000000000";
	/** 系统版本 */
	public static final int SYSYEMBS = 1;// B/S系统
	public static final String VERSION_INFO = "1.0.0";
	public static final long SOFT_TIME = 600; // 登录时间与加密狗中版本时间间隔 单位秒

	public static final class SessionAttrbutes {
		public static final String SESSIONPRX = "fms-";
		public static final String SESSION = "session";
		public static final String USER_SESSION = "userSession";
		public static final String MENUS = "menus";
		public static final String CMENUS = "cmenus";
		public static final String MENUS_CS = "menu_cs";
	}

	/** 组织前缀 */
	public static final class TreeConstants {
		public static final String PREFIX_ORG = "reg_";
		public static final String PREFIX_DEV = "dev_";
		public static final String PREFIX_CHANNEL = "channel_";
		public static final String PREFIX_PRODUCTTYPE = "productType_";
		public static final String PREFIX_PRODUCT = "product_";
		public static final String PREFIX_COFFER = "cof_";
		public static final String PREFIX_ROUTE = "rou_";
	}

	/** 组织上限 */
	public static final class RegNum { // 组织上限
		public static final int REGION_NUM = 10; // 组织
	}

	/** 角色类型 */
	public static final class RoleType {
		public static final int SYSTEM_MANAGER = 1;// 系统管理员
		public static final int CENTER_MANAGER = 2; // 中心管理员
		public static final int COFFER_MANAGER = 3; // 库管员
	}

	/** 性别类型 */
	public static final class SexType {
		public static final int SEX_MAN = 1;// 男
		public static final int SEX_WOMEN = 0;// 女
	}

	/** 错误类型 */
	public static final class FailureType {
		public static final String NOT_FETCH_IDS = "NOT_FETCH_IDS"; // 未获取ids
		public static final String EXIST_RELATION_DATA = "EXIST_RELATION_DATA"; // 存在关联数据
		public static final String PASSWORD_UNFRESH = "PASSWORD_UNFRESH"; // 密码不新鲜
		public static final String PASSWORD_DEFAULT = "PASSWORD_DEFAULT"; // 密码默认
	}

	/** 用户状态 */
	public static final class UserStatus {
		public static final int NORMAL = 0; // 正常
		public static final int DISABLE = 1; // 禁用
		public static final int EXPIRED = 2; // 过期
		public static final int ONLINE = 3; // 在线
		public static final int NOTONLINE = 4; // 离线
	}

	/** 服务状态 */
	public static final class ServerStatus {
		public static final int ONLINE = 1; // 在线
		public static final int NOTONLINE = 0; // 离线
		public static final String ON_LINE = "在线"; // 在线
		public static final String NOT_ONLINE = "离线"; // 离线
	}

	/*** RspServerData 消息应答 */
	public static final class RSP_RESULT {
		public static final Integer SUCCESS = 0; // 成功
		public static final Integer FAIL = -1; // 失败
	}

	/** 字典参数 */
	public static final class SysDictionary {
		/** 字典启用-1表示 */
		public static final int enable = 1;
		/** 字典不启用-0表示 */
		public static final int disable = 0;

		/** 服务器定义 */
		public final static class ServerType { // 服务器类型

			/** 字典大类型 */
			public static final class DictBigType {
				public static final int DICT_B_PRODUCT = 0; // 产品大类型
				public static final int DICT_B_EVENT = 1; // 报警事件大类型
				public static final int DICT_B_SERVER = 2; // 服务器大类型
			}

			/** 字典小类型 */
			public static final class DictType {
				public static final int DICT_S_SERVER = 20; // 服务器类型
			}
		}

		/** 组织类型 */
		public final static class OrganizationType {
			public static final int ORGA_TYPE = 21; // 组织类型
		}
	}

	/**
	 * 文件上传保存根路径</br>
	 * <strong>严禁使用其他目录</strong>
	 */
	public static final String FILEUPLOAD = "fileupload/";

	/** 系统参数配置 */
	public static final class SysConfigType {

		/** 平台参数 */
		public static final int PLATFORM_DISCRIBE = 6000; // 平台描述文字
		public static final int PLATFORM_COPYRIGHT = 6001; // 版权描述文字
		public static final int IMG_LOGO = 6002; // 登录页LOGO图标
		public static final int IMG_BAR = 6003; // 导航栏
		public static final int IMG_LOGIN = 6004; // 登录页图片

		/** 模式参数 */
		public static final int VERIFICATION_CODE = 6200; // 启用禁用验证码

		/** 安全策略 */
		public static final int LOGIN_COUNT = 6300; // 登录次数
		public static final int LOGIN_LOCK_TIME = 6301; // 登录锁定时间
		public static final int DEFAULT_PASSWORD = 6302; // 平台初始密码
		public static final int PASSWORD_SECRUITY_LEVEL = 6303; // 密码安全等级

		public final static int FAIL_COUNT = 6304; // 登陆错误最大次数
		public final static int DISTANCE_MINUTES = 6305; // 账户锁定的分钟数

		/** 系统参数 */
		public static final int SYSTEM_LOG_SAVEDAY = 6400; // 系统日志保存天数
		public static final int TASK_LOG_SAVEDAY = 6401; // 任务日志保存天数
		public static final int AUTH_DUE_DAY = 6402; // 授权过期天数
		public static final int PASSWORD_FRESH_MONTH = 6403; // 密码保鲜期

		public static final String Default_NAVBAR_URL = "/images/logo_main.png"; // 导航栏logo地址
		public static final String Default_LOGO_URL = "/images/logo-03.png"; // 登录页logo地址
		public static final String Default_LOGIN_URL = "/images/login_bg.png"; // 登录页背景图片

	}

	/** 登陆0 登出1 */
	public static final class LoginStatus {
		public static final int LOGIN_IN = 0; // 登陆
		public static final int LOGIN_OUT = 1; // 登出
	}

	/** 操作状态 */
	public static final class OperStatus {
		public static final int ADD = 0; // 添加
		public static final int DELETE = 1; // 删除
		public static final int UPDATE = 2; // 修改
		public static final int IMPORT = 3; // 导入
		public static final int EXPORT = 4; // 导出
		public static final int LOGIN = 5; // 登录
		public static final int LOGOUT = 6; // 注销
		public static final int ENABLED = 7;// 启用
		public static final int DISABLED = 8;// 停用
	}

	public static final class UserType {
		public static final int SYS_MANAGER = 1; // 系统管理员
		public static final int DEP_MANAGER = 6;// 配置管理员
	}

	/** 树节点读取策略 */
	public static final class TreeReadStrategy {
		/** 只读取节点本身 */
		public static final int ONLY_SELF = 0;
		/** 包含子节点 */
		public static final int CONTAIN_CHILD = 1;
	}

	/** DC接口常量--BEG */

	/** 接口执行后的状态 */
	public static final class ResultStatus {
		/** 失败 */
		public static final int RESULT_FAILE = -1;
		/** 成功 */
		public static final int RESULT_SUCCESS = 0;
		/** 自定义 */
		public static final int RESULT_ERROR = -2;
		/** 自定义 */
		public static final int RESULT_ERR = -3;

	}

	/** 是否为初始化密码 */
	public static final class PasswordType {
		public static final int PERSONAL_PASSWORD = 0;// 不是初始化密码
		public static final int INITAL_PASSWORD = 1; // 是初始化密码
	}

	/** 密码等级 */
	public static final class PasswordLevel {
		public static final int RISK = 0;
		public static final int WEAK = 1; // 弱密码
		public static final int MIDDLE = 2; // 中
		public static final int STRONG = 3; // 强
	}
	/**按照天，周，月上报**/
	public static final class DateStsType {
		public static final int DAY = 1; // 当天
		public static final int WEEK = 2; // 当周
		public static final int MONTH = 3; // 当月
		public static final int MEL = 4; // 自己上报
		public static final int TIMETOTIME = 5; // 时间段查询
	}
	
	/** 百分比保留的位数 */
	public static final class PerFormat {
		public static final int PER_ZERO = 1;// 保留0位小数
		public static final int PER_ONE = 2; // 保留1位小数
		public static final int PER_TWO = 3; // 保留2位小数
	}
	
	/** 时间类型*/
	public static final class StaTypeFormat {
		public static final int STA_YEAR = 1;// 年
		public static final int STA_QUARTER = 2; // 季度
		public static final int STA_MONTH = 3; // 月
	}
	
	/**数据字典code值**/
	public static final class DicCode {
		public static final String LAW_TYPE = "A0000101";//案件类型
		public static final String LAW_OCCURREDPOSITION = "A0000102";//发案部位
		public static final String LAW_CRIMEMETHOD = "A0000115";//作案手段
		public static final String LAW_OCCURREDENVIRONMENT = "A0000107";//案发地环境因素
		public static final String LAW_INFRACTEDTARGET = "A0000104";//侵害目标
		public static final String LAW_SUSPECTTYPE = "A0000105";//嫌疑人类型
		public static final String LAW_OCCURREDORG = "A0000110";//发案单位
		public static final String LAW_CRIMETOOL = "A0000106";//作案工具
		public static final String LAW_ISCRIME = "A0000109";//既遂/未遂
		public static final String LAW_HAPPENCRIME_ITEM = "A0000113";//隐患和漏洞
		public static final String LAW_FAILCRIME_ITEM = "A0000114";//未遂原因成功经验
		
		public static final String LAW_HAPPEN = "JWS01";//既遂
		public static final String LAW_FAIL = "JWS02";//未遂
		
		public static final String LAW_MODE_AJLX01 = "AJLX01";//抢劫
		public static final String LAW_MODE_AJLX03= "AJLX03";//盗窃
		public static final String LAW_MODE_AJLX02 = "AJLX02";//抢夺
		public static final String LAW_MODE_AJLX04 = "AJLX04";//抢夺
		public static final String LAW_POSITION_FABW01 = "FABW01";//营业大厅
		public static final String LAW_POSITION_FABW02 = "FABW02";//现金业务区
		public static final String LAW_POSITION_FABW03 = "FABW03";//自助银行
		public static final String LAW_POSITION_FABW04 = "FABW04";//自助设备加钞间
		public static final String LAW_POSITION_FABW05 = "FABW05";//业务库
		public static final String LAW_POSITION_FABW06 = "FABW06";//运钞车
		
		public static final String LAW_TARGET_QHMB02 = "QHMB02";//储户
		
		public static final String LAW_CITY_AFDHJYS01= "AFDHJYS01";//城市闹市区发案
		public static final String LAW_CITY_AFDHJYS02= "AFDHJYS02";//城市一般街道
		public static final String LAW_CITY_AFDHJYS03= "AFDHJYS03";//城乡偏僻地带
		public static final String LAW_CITY_AFDHJYS04= "AFDHJYS04";//城乡结合部
		public static final String LAW_CITY_AFDHJYS05= "AFDHJYS05";//县城
		public static final String LAW_CITY_AFDHJYS06= "AFDHJYS06";//乡镇
		public static final String LAW_CITY_AFDHJYS07= "AFDHJYS07";//村
		
		public static final String LAW_HAPPEN_YHLD01= "YHLD01";//技防设备不健全
		public static final String LAW_HAPPEN_YHLD02= "YHLD02";//技防设施故障
		public static final String LAW_HAPPEN_YHLD03= "YHLD03";//物防设施不健全
		public static final String LAW_HAPPEN_YHLD04= "YHLD04";//物防设施故障
		public static final String LAW_HAPPEN_YHLD05= "YHLD05";//规章制度不健全
		public static final String LAW_HAPPEN_YHLD06= "YHLD06";//违规操作
		public static final String LAW_HAPPEN_YHLD07= "YHLD07";//其它
		
		
		
		
		
		public static final String EVENT_TYPE = "SJLX";//事件类型
		public static final String EVENT_YY = "YY";//事件诱因
		public static final String EVENT_SSZT01 = "SSZT01";//银行协解人员
		public static final String EVENT_SSZT02 = "SSZT02";//在职人员
		public static final String EVENT_SSZT03 = "SSZT03";//银行内退人员
		public static final String EVENT_SSZT04 = "SSZT04";//股民
		public static final String EVENT_SSZT05 = "SSZT05";//客户
		public static final String EVENT_SSZT06 = "SSZT06";//其它
		
		
		public static final String EVENT_SJBXXS01 = "SJBXXS01";//上访
		public static final String EVENT_SJBXXS02 = "SJBXXS02";//进京上访
		public static final String EVENT_SJBXXS03 = "SJBXXS03";//请愿
		public static final String EVENT_SJBXXS04 = "SJBXXS04";//静坐
		public static final String EVENT_SJBXXS05 = "SJBXXS05";//冲击党政机关
		public static final String EVENT_SJBXXS06 = "SJBXXS06";//围堵要害部门
		public static final String EVENT_SJBXXS07 = "SJBXXS07";//聚众阻塞交通
		public static final String EVENT_SJBXXS08 = "SJBXXS08";//聚众滋事
		public static final String EVENT_SJBXXS09 = "SJBXXS09";//串联聚会
		public static final String EVENT_SJBXXS10 = "SJBXXS10";//非法游行
		public static final String EVENT_SJBXXS11 = "SJBXXS11";//非法示威
		public static final String EVENT_SJBXXS12 = "SJBXXS12";//聚众械斗
		public static final String EVENT_SJBXXS13 = "SJBXXS13";//聚众哄抢
		public static final String EVENT_SJBXXS14 = "SJBXXS14";//打砸抢烧
		public static final String EVENT_SJBXXS15 = "SJBXXS15";//涉枪涉爆
		public static final String EVENT_SJBXXS16 = "SJBXXS16";//骚乱
		public static final String EVENT_SJBXXS17 = "SJBXXS17";//其它
		public static String[] SJBXXS_ARRY = new String[17];
		static{
			String code = "SJBXXS";
			for (int i = 0; i < 17; i++) {
				int index = i+1;
				String full = null;
				if(index<10){
					full = "0";
				}else{
					full = "";
				}
				String value= new StringBuilder(code).append(full).append(index).toString();
				SJBXXS_ARRY[i] = value;
			}
		}
		
		public static final String EVENT_YY01 = "YY01";//劳动合同引起事件
		public static final String EVENT_YY02 = "YY02";//福利待遇引起事件
		public static final String EVENT_YY03 = "YY03";//股市波动引起事件
		public static final String EVENT_YY04 = "YY04";//客户与金融机构发生纠纷引起事件
		public static final String EVENT_YY05 = "YY05";//其它事件

	}
	
	/** 时间类型*/
	public static final class TimeType {
		public static final int YEAR = 1;// 年
		public static final int QUARTER = 2; // 季度
		public static final int MONTH = 3; // 月
	}
	
	/** 数据字典Id*/
	public static final class DicId {
		public static final int CASEUNITID = 13;// 发案单位Id
		public static final int EVENTEXPRESSIONID = 19;// 发案单位Id
		
	}
	/**保留小数的位数**/
	public static final class SmallNum {
		public static final String SUB_ZREO = "0%";// 小数点0位
		public static final String SUB_ONE = "0.0%";// 小数点1位
		
	}

}
