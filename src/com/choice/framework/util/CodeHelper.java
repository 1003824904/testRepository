package com.choice.framework.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.choice.framework.exception.CRUDException;

public class CodeHelper {
	
	/**
	 * 部门编号的每一分段长度，如为4则code将类似1234-5678
	 */
	public static int CODE_LENGTH = 4;
	
	public static String createUUID(){
		return String.valueOf(UUID.randomUUID()).replaceAll("-", "");
	}
	/**
	 * in 中字段过滤
	 * @param ins
	 * @return
	 */
	public static String replaceCode(String ins){
		if(null==ins || "".equals(ins)){
			return ins;
		}
		return  "'"+ins.replace(",","','")+"'";
	}
	
	/**
	 * in 中字段过滤
	 * @param ins
	 * @return
	 */
	public static List<String> replaceCode(List<String> ins){
		if(null==ins || "".equals(ins)){
			return ins;
		}
		List<String> inss = new ArrayList<String>();
		for(int i=0; i<ins.size();i++) {
			inss.add(i, "'"+ins.get(i)+"'");
		}
		
		return  inss;
//		ins.replace(",","','");
	}
	
	/**
	 * 获得下一个code
	 * @param map map包含的key有2个：“code”和“parentCode”
	 * @return
	 * @throws CRUDException
	 */
	public static String getNextCode(String selfCode, String parentCode) throws CRUDException{
		String nextCode = "";
		DecimalFormat df = new DecimalFormat("0000");
		
		
		if(parentCode != null && !parentCode.equals("")){
			if(selfCode == null || selfCode.equals("")){//第一次添加部门的子部门
				nextCode = parentCode + "-0001";
			}else{
				int beginIndex = selfCode.lastIndexOf("-");
				
				int lastNum = Integer.parseInt(selfCode.substring(beginIndex+1))+1;
				
				nextCode = selfCode.substring(0,beginIndex+1)+df.format(lastNum);
			}
		}else{
			if(selfCode == null || selfCode.equals("")){
				nextCode = "0001";
			}else{
				
				int lastNum = Integer.parseInt(selfCode)+1;
				
				nextCode = df.format(lastNum);
				
			}
		}
			
		return nextCode;
	}
}
