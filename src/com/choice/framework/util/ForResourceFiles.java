package com.choice.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 获取资源文件和参数
 * @author csb
 *
 */
public class ForResourceFiles {
	
	/**
	 * 根据传入的资源文件名和该文件里的参数的名称，获取该参数对应的值
	 * @param fileName 1、要获取的资源文件名称；
	 * 				   2、可为空，默认文件名为“config.properties”；
	 * @param parameterName 1、要获取的资源文件里参数的名称；
	 * 						2、请传入资源文件里正确的对应的参数名，否则返回null；
	 * @return 1、String类型的值（有值）；
	 * 		   2、null(无值)；
	 */
	public static String getValByKey(String fileName,String parameterName){
		String resourceName="config.properties";
		if(null!=fileName && !"".equals(fileName)){
			resourceName=fileName;
		}
		InputStream inputStream=ForResourceFiles.class.getClassLoader().getResourceAsStream(resourceName);
		Properties properties = new Properties();
		String result=null;//返回结果默认为空；
		try {
			if(null!=parameterName && !"".equals(parameterName)){
				properties.load(inputStream);
				result=properties.getProperty(parameterName);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	/**
	 * 从数据库里读取参数
	 * @param parameterName 传入的StringConstant中的静态变量。例如：StringConstant.INOUT_IS_NOT
	 * @return
	 */
	public static String getValBySQL(String parameterName){
		Connection con = DbConn.getConn();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String enum_value = null;
		String sql = null;
		try {
			sql="select * from dict where id= ?";
			ps=con.prepareStatement(sql);
			ps.setString(1, parameterName);
			rs=ps.executeQuery();
			while(rs.next()){
//				String id=rs.getString(1);
//				String enum_meaning=rs.getString(2);
				enum_value=rs.getString(3);
//				String deleteflag=rs.getString(4);
//				String parent_id=rs.getString(5);
//				String is_initial=rs.getString(6);
//				String remarks=rs.getString(7);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				if(null!=rs){
					rs.close();
				}
				if(null!=ps){
					ps.close();
				}
				if(null!=con){
					con.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return enum_value;
	}
}