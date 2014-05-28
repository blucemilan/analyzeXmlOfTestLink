package com.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class AppConfig {
	protected Properties p;

	public AppConfig() {
		p = new Properties();
	}

	public AppConfig(File file) {
		p = new Properties();
		this.open(file);
	}

	public void open(File file) {
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			p.load(in);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(String.format(
					"Failed to load configuration file %1$s",
					file.getAbsolutePath()));
		} catch (IOException e) {
			try {
				in.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public String getProperty(String propertyName) {
		
		try {
			return new String(p.getProperty(propertyName).getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
