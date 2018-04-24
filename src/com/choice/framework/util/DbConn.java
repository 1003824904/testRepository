package com.choice.framework.util;

import java.sql.Connection;
import java.sql.DriverManager;


public class DbConn {
	
	static String driver=ForResourceFiles.getValByKey("jdbc.properties","jdbc.driver");
	static String url=ForResourceFiles.getValByKey("jdbc.properties","jdbc.url");
	static String name=ForResourceFiles.getValByKey("jdbc.properties","jdbc.username");
	static String pass=ForResourceFiles.getValByKey("jdbc.properties","jdbc.password");
	//private final static String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	static {
		try {
			Class.forName(driver);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static Connection getConn(){
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url,name,pass);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return conn;
	}
//	public static void main(String[] args) {
//		Connection conn = DbConn.getConn();
//		System.out.println(conn);
//	}
}
