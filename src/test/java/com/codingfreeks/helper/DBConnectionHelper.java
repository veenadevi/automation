package com.codingfreeks.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnectionHelper {
	
	public static  Connection connectDB(String DBName) throws ClassNotFoundException, SQLException   {
		
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		
		String url="jdbc:sqlserver://172.16.0.219";
		String userName="sa";
		String password="welcome001*";
	//	String dataBase="QA_Order";
		
		Properties pro=new Properties();
		pro.put("user", userName);
		pro.put("password", password);
		pro.put("database", DBName);
		
		Connection conn=DriverManager.getConnection(url, pro);		
		return conn;
		
		
	}

}
