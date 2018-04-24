package com.choice.orientationSys.constants;


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
	
	public static final String ROOT_ID = "00000000000000000000000000000000";//树形结构的根节点编号
	public static final String INOUT_IS_NOT = "08c8784493424a51b0bd930711ab24f3";//是否入库只能入到默认存放仓位
	public static final String MAXSIZE = "77ab6d86813c4f7fa14ea04603ddbef0";//分页每页显示的最大记录数
	public static final String WLZX = "5180e4744be143c7a2ea1eadcab0498d";//虚拟物流中心编码
	public static final String SAVEDAYS = "1a02405c892d4bc68a760dd650e87d01";//日志保留天数，到期日志将被删除
	public static final String AUTOCLEAN = "98dbfa0e295d4db29fc66f8d92ad3c1e";//是否对日志进行自动清除
	public static final String MAXDAY = "bc47f10268e8496c816cbf083bd44eeb";//日志最大保留天数
	
	//字符串表示布尔值
	public static final String TRUE = "T";
	public static final String FALSE = "F";
	
	//操作完成后（保存或者修改），统一跳转的页面
	public static final String QUIT_SCHOOL_ACTION_DONE = "share/QuitSchoolsaveDone";
	public static final String ACTION_DONE = "share/done";
	public static final String ACTION_AFFIRMDONE= "share/affirmDone";
	public static final String SAVE_DONE="share/saveDone";

	//使用状态
	public static final String STATE_DISABLE = "0";
	public static final String STATE_ENABLE = "1";
	//默认显示字段
	public static final String BASICINFO_TABLE_DEFAULT_COLUMNS = "01,02,12,10,13,14,09,25";
	
	public static final String TABLE_NAME = "student_info";
	public static final String READ_EXCEL_OK = "ok";
	public static final String INPUT_EXCEL_ERROR = "输入非法！";
	public static final String READ_EXCEL_ERROR = "解析文件出错,出错的原因由于列名不对应。<br/>请重新下载模板对比相应列顺序。";

	//列选择跳转的页面
	public static final String TO_COLUMNS_CHOOSE_VIEW="share/columnsChoose";
	
	public static final String EXCELTEMPLETE = "template.xls";
	public static final String HELPWORD = "餐饮系统操作手册.doc";
	public static final String KTR = "oneone.ktr";
}