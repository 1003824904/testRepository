package com.choice.orientationSys.util;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.choice.framework.exception.CRUDException;

public class ELFuncUtil {
	private final transient static Log log = LogFactory.getLog(ELFuncUtil.class);
	/**
	 * 自定义EL标签调用方法
	 * @param objParam
	 * @param columnParam
	 * @return
	 * @throws Exception
	 */
    public static String append(Object objParam, String columnParam,String pattern) throws Exception {
    	//获得要调用的字段
       String[] columnArrays = columnParam.trim().split("[.]");
       String result = "";
       try {
      	 for(String columnStr : columnArrays){
      		   if(objParam==null)
      			   break;
        	   objParam = getResult(objParam,columnStr);
           }
		} catch (Exception e) {
			log.error(e);
			throw new CRUDException(e);
		}
       if(objParam!=null){
    	   if(objParam.getClass().getName() == "java.util.Date"){
    		   SimpleDateFormat fmt = new SimpleDateFormat(pattern == null || pattern == "" ? "yyyy-MM-dd":pattern);
    		   result = fmt.format(objParam);
    	   }else{
    		   result = String.valueOf(objParam);
    	   }
       }
       return result;
    }
    /**
     * 辅助方法
     * @param objParam
     * @param columnMethod
     * @return
     * @throws Exception
     */
    protected static Object getResult(Object objParam,String columnMethod)throws Exception{
    	//拼接要获得的方法
		columnMethod = "get"+columnMethod.substring(0, 1).toUpperCase() + columnMethod.substring(1);
		//获得方法
		Method method = objParam.getClass().getMethod(columnMethod, new Class[]{});
		//执行方法，获取返回值
		Object retObj = method.invoke(objParam, new Object[] {});
        return retObj;
    }
}
