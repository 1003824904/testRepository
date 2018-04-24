package com.choice.orientationSys.util;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

public class Util {
	public static final char TRUE ='T';//是
	public static final char FALSE = 'F';//否
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.replaceAll("-","");
	}
	 /**
	  * 获取客户端ip
	  * @param request
	  * @return
	  */
	public static String getIpAddr(HttpServletRequest request) { 
	     String ip = request.getHeader("x-forwarded-for"); 
	     if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	         ip = request.getHeader("Proxy-Client-IP"); 
	     } 
	     if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	         ip = request.getHeader("WL-Proxy-Client-IP"); 
	     } 
	     if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	         ip = request.getRemoteAddr(); 
	     } 
	     // 返回一串ip值的话，取第一个
	     if(ip.contains(",")){
	      ip = ip.substring(0, ip.indexOf(","));
	     }
	    if("0:0:0:0:0:0:0:1".equals(ip)){
	    	ip = "127.0.0.1";
	    }  
	     return ip; 
	 }
}
