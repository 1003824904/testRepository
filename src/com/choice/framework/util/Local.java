package com.choice.framework.util;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Globals;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.choice.framework.domain.system.MyLocaleResolver;


public class Local extends SessionLocaleResolver {
	private final transient static Log log = LogFactory.getLog(Local.class);
	/**
	 * 自定义Local标签调用方法 
	 * 国际化
	 * @throws Exception
	 */
    public static String show(String ins){
    	  try {
    		  
 //   		  RequestAttributes ra = RequestContextHolder.getRequestAttributes();  
 //   		  HttpServletRequest request = ((ServletRequestAttributes)ra).getRequest();  
//    		  String lang= request.getParameter("locale");
//    		  HttpSession session = request.getSession(); 

//    		  String lang=SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME;
//    		  lang=(String)session.getAttribute(Globals.SERVLET_NAME_ATTR);
    		  
//    		  MyLocaleResolver my=new MyLocaleResolver();
//    		  String lang = my.getMyLocal();
//    		  System.out.println("2222"+lang);
//    		  Locale defaultLocale = Locale.getDefault();
//    		  System.out.println("111:::"+defaultLocale);
    		  
    		  RequestAttributes ra = RequestContextHolder.getRequestAttributes();  
    		  HttpServletRequest request = ((ServletRequestAttributes)ra).getRequest();  
    		  HttpSession session = request.getSession(); 
  			  String locale=session.getAttribute("locale").toString();
  			  
    		  List<String> inList= Arrays.asList(ins.split(","));
    		  if(inList.size()<=1){
    			  return ins;
    		  }
    		  if("zh_TW".equals(locale)){
    			  locale=inList.get(1);
    		  }else if("en".equals(locale)){
    			  locale=inList.get(2);
    		  }else{
    			  locale=inList.get(0);
    		  }
    		  if(null==locale || "".equals(locale)){
    			  locale=inList.get(0);
    		  }
    			return locale;
    	   } catch (Exception e) {
    		  e.printStackTrace();
    	   }
		return ins;
	
    }
    public static String sift(String ins,Integer num){
    	try {
    		List<String> inList= Arrays.asList(ins.split(","));
    		if(num>2){
    			return ins;
    		}else if(inList.size()<=1){
    			return inList.get(0);
    		}else{
    			return inList.get(num);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return ins;
    	
    }
}    
