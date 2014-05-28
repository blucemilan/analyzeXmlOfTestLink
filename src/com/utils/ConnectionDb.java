package com.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionDb extends PublicClass{

	public static Connection getDBConnection() {
		Connection cn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String url = "jdbc:mysql://" + Config.getDataBaseUrl() + "/" + Config.getDataBaseName();
			String username = Config.getDataBaseUser();
			String password = Config.getDataBasePassWord();
			cn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
			cn = null;
		}
		return cn;
	}

}