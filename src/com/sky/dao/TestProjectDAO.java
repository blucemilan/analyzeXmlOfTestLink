package com.sky.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.utils.ConnectionDb;
import com.utils.PublicDao;

public class TestProjectDAO extends PublicDao {

	public List<String> getAllProject() throws IOException {
		List<String> allProject = null;
		try {
			allProject = new ArrayList<String>();
			conn = ConnectionDb.getDBConnection();
			pstmt = conn.prepareStatement(getAllProject_Sql(),
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				allProject.add(rs.getString("name")+"("+rs.getString("prefix")+")"+"["+rs.getString("id")+"]");
			}

		} catch (Exception e) {
			e.printStackTrace();
			allProject = null;
		} finally {
			colseConnection();
		}
		return allProject;
	}
	public String getAllProject_Sql(){
		StringBuffer str = new StringBuffer("SELECT testprojects.id as id, nodes_hierarchy.name as name, testprojects.prefix as prefix ");
		str.append("FROM testprojects, nodes_hierarchy WHERE testprojects.id = nodes_hierarchy.id ");
		str.append("and testprojects.active=1 and testprojects.is_public=1");
		return str.toString();
	}
}
