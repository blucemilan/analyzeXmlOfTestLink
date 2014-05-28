package com.utils;

import java.io.File;


public class Config {
	
	static AppConfig config;  
	
	/* Read app.config file */
	static {
		config = new AppConfig(new File(Utils.getPropertyFilePath()));
	}
	
	/* Get DataBase Url */
	public static String getDataBaseUrl() {
		return config.getProperty("DataBaseUrl");
	}
	
	/* Get DataBase Name */
	public static String getDataBaseName() {
		return config.getProperty("DataBaseName");
	}
	
	/* Get DataBase User */
	public static String getDataBaseUser() {
		return config.getProperty("DataBaseUser");
	}
	
	/* Get DataBase PassWord */
	public static String getDataBasePassWord() {
		return config.getProperty("DataBasePassWord");
	}

	/* Get DefaultProjectName */
	public static String getDefaultProjectName() {
		return config.getProperty("DefaultProjectName");
	}

	/* Get DefaultProjectPrefix */
	public static String getDefaultProjectPrefix() {
		return config.getProperty("DefaultProjectPrefix");
	}
}
