package com.choice.framework.util;

import java.util.ArrayList;
import java.util.List;

public class OracleUtil {

	private static int ORA_IN_LIMIT=1000;
	/**
	 * 构造sql里面的in条件，由于oracle里面对于in条件里面的数据项有限制，不能超过1000个，所以对于超过1000个数据项的查询需特殊处理
	 * add by liuyzh 直接转换
	 * 
	 * @param 1:columnName
	 * @param 2:'a','b,','c'
	 * @return
	 */
	public static String getOraInSql(String columnName, String values){
		StringBuffer sql = new StringBuffer();
		String[] tiaojian = values.split(",");
		List<String> inList = new ArrayList<String>();
		String inSql = "";
		for (int i = 0; i < tiaojian.length; i++){
			inSql += tiaojian[i] + ",";

			// 每1000个处理一次
			if ((i + 1) % ORA_IN_LIMIT == 0 || (i == tiaojian.length - 1)){
				inList.add(inSql.substring(0, inSql.length() - 1));// 去掉最后一个逗号
				inSql = "";
			}
		}
		for (int i = 0; i < inList.size(); i++){
			if (i > 0){
				sql.append(" or ");
			}
//			sql.append(columnName).append(" in (").append(CodeHelper.replaceCode(inList.get(i))).append(") ");
			sql.append(columnName).append(" in (").append(inList.get(i)).append(") ");
		}
		return sql.insert(0, "(").append(")").toString();
	}
	
	/**
	 * 构造sql里面的in条件，由于oracle里面对于in条件里面的数据项有限制，不能超过1000个，所以对于超过1000个数据项的查询需特殊处理
	 * add by liuyzh 直接转换
	 * 
	 * @param 1:columnName
	 * @param 2:'a','b,','c'
	 * @return
	 */
	public static String getOraInSql(String columnName, List<String> idList){
		StringBuffer sql = new StringBuffer();
		List<String> inList = new ArrayList<String>();
		String inSql = "";
		for (int i = 0; i < idList.size(); i++){
			inSql += "'"+idList.get(i) + "',";

			// 每1000个处理一次
			if ((i + 1) % ORA_IN_LIMIT == 0 || (i == idList.size() - 1)){
				inList.add(inSql.substring(0, inSql.length() - 1));// 去掉最后一个逗号
				inSql = "";
			}
		}
		for (int i = 0; i < inList.size(); i++){
			if (i > 0){
				sql.append(" or ");
			}
//			sql.append(columnName).append(" in (").append(CodeHelper.replaceCode(inList.get(i))).append(") ");
			sql.append(columnName).append(" in (").append(inList.get(i)).append(") ");
		}
		return sql.insert(0, "(").append(")").toString();
	}
	
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("5");
		list.add("6");
		System.out.println(getOraInSql("id",list));
	}
}
