package com.choice.orientationSys.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.choice.orientationSys.constants.StringConstant;
import com.choice.orientationSys.constants.StudentInfoConstant;


public class ReadProperties {
	private Map<String, String> props = new HashMap<String, String>();
	private static String cfgFilePath = "/config.properties";
	private String ZYTZ;
	
	public String getZYTZ() {
		return ZYTZ;
	}
	public void setZYTZ(String zytz) {
		ZYTZ = zytz;
	}
	
	private void readConf(){
		Properties properties = new Properties();
		try {
			properties.load(this.getClass().getResourceAsStream(  
        			cfgFilePath));
		} catch (Exception e) {
			System.out.println("不能读取属性文件. " + "请确保" + cfgFilePath
					+ "在CLASSPATH指定的路径中");
			return;
		}
		Enumeration<Object> e = properties.keys();  
        //枚举所有 key-value对  
        while (e.hasMoreElements()) {  
            String keyO = (String) e.nextElement();  
            String value = properties.getProperty(keyO);
            props.put(keyO, value);
        }  
	}
	
	public void writeConf(String key,String value){
		 Properties properties = new Properties();  
	        try {  
	        	properties.load(this.getClass().getResourceAsStream(  
	        			cfgFilePath));  
	              
	           
	            OutputStream fos = new FileOutputStream(this.getClass().getResource(cfgFilePath).getPath()); 
	            //修改一个  
	            properties.setProperty(key, value);
	            properties.store(fos,"this is header");  
	            fos.close();  
	  
	        } catch (IOException e1) {  
	            // TODO Auto-generated catch block  
	            e1.printStackTrace();  
	        }      
	}
	public String getStrByParam(String param){
		if(props.isEmpty())
			readConf();
		String paramValue = props.get(param);
		return paramValue;
	}
	
	
}
