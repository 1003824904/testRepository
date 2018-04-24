package com.choice.framework.constants;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 文件名：StringConstant<br>
 * 
 * 说明：字符串常量类 <br>
 * 
 * 作者：lib<br>
 *
 */
public final class StringConstant {
	private StringConstant(){}
	
	//登陆账号的缓存
	public static final String ACCOUNT_CACHE = "account_cache";
	
	//树形结构的根节点编号
	public static final String ROOT_ID = "00000000000000000000000000000000";
	
	//初始化密码
	public static final String INIT_PASSWORD = "123456";
	
	//字符串表示布尔值
	public static final String TRUE = "T";
	public static final String FALSE = "F";
	
	//操作完成后（保存或者修改），统一跳转的页面
	public static final String ACTION_DONE = "share/done";
	public static final String ERROR_DONE = "share/errorDone";
	public static final String CHKSTOD_LOCAL_DONE = "share/chkstodLocalDone";
	public static final String CHKPAYM_LOCAL_DONE = "share/chkpaymLocalDone";

	
	//性别
	public static final String MALE = "0";
	public static final String FEMALE = "1";

	//在职状态
	public static final String STATE_OUT_OFFICE = "0";
	public static final String STATE_IN_OFFICE = "1";
	public static final String STATE_RETIRE = "2";
	
	//使用状态
	public static final String STATE_DISABLE = "0";
	public static final String STATE_ENABLE = "1";
	
	//关联状态
	public static final String STATE_UNRELATED = "0";
	public static final String STATE_RELATED = "1";
	

	public static final HashMap<String, String> NULLABLE = new LinkedHashMap<String, String>();
	
	public static final HashMap<String, String> DELETE_FLAG = new LinkedHashMap<String, String>();

	public static final HashMap<String, String> SEX_RADIO = new LinkedHashMap<String, String>();
	
	public static final HashMap<String, String> USER_STATE = new LinkedHashMap<String, String>();
	
	public static final HashMap<String, String> ACCOUNT_STATE = new LinkedHashMap<String, String>();
	
	public static final HashMap<String, String> ACCOUNT_ROLE_STATE = new LinkedHashMap<String, String>();
	
	static {

		NULLABLE.put(TRUE, "是");
		NULLABLE.put(FALSE, "否");
		
		DELETE_FLAG.put(FALSE, "启用");
		DELETE_FLAG.put(TRUE, "禁用");

		SEX_RADIO.put(MALE, "男");
		SEX_RADIO.put(FEMALE, "女");
		
		USER_STATE.put(STATE_IN_OFFICE, "在职");
		USER_STATE.put(STATE_OUT_OFFICE, "离职");
		USER_STATE.put(STATE_RETIRE, "退休");
		
		ACCOUNT_STATE.put(STATE_ENABLE, "启用");
		ACCOUNT_STATE.put(STATE_DISABLE, "禁用");
		
		ACCOUNT_ROLE_STATE.put(STATE_RELATED, "已关联");
		ACCOUNT_ROLE_STATE.put(STATE_UNRELATED, "未关联");
		
	}
}