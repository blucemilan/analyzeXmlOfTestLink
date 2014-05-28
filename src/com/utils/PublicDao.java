package com.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.jdbc.core.JdbcTemplate;

public class PublicDao extends PublicClass{
	public ResultSet rs = null;
	public Statement stmt = null;
	public PreparedStatement pstmt = null;
	public JdbcTemplate jdbcTemplate;
	public Connection conn = null;
	
	public void colseConnection() {
		if (stmt != null) {
			try {
				stmt.close();
				if (rs != null) {
					rs.close();
					if (conn != null)
						conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void colseConnectionWithOutCon() {
		if (stmt != null) {
			try {
				stmt.close();
				if (rs != null) {
					rs.close();
					if (conn != null)
						conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
