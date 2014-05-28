package com.sky.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sky.bo.TestPlan;
import com.utils.ComparatorPlan;
import com.utils.ConnectionDb;
import com.utils.PublicDao;
import com.utils.Utils;

public class TestPlanDAO extends PublicDao  {
	public static String platSql = "  AND T.platform_id = ?  ";
	public List<TestPlan> getPlanById(String projectPrfix, String plan_id, String builds_id, String plat_id) {
		List<TestPlan> plansList = new ArrayList<TestPlan>();
		TestPlan plans = null;
		try {
			conn = ConnectionDb.getDBConnection();
			String tmpSql = "";
			if(!"".equals(plat_id)){
				tmpSql = platSql;
			}
			pstmt = conn.prepareStatement(getTestPlan_Sql(tmpSql),
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			pstmt.setInt(1, Integer.parseInt(builds_id));
			pstmt.setInt(2, Integer.parseInt(plan_id));		
			if(!"".equals(plat_id)){
				pstmt.setInt(3, Integer.parseInt(plat_id));
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				plans = new TestPlan();
				plans.setTestsuite_id(rs.getString("testsuite_id"));
				plans.setTc_id(rs.getString("tc_id"));
				plans.setName(rs.getString("name"));
				plans.setCase_id(projectPrfix + "-" + rs.getString("tc_external_id"));
				plans.setPlatform_name(rs.getString("platform_name"));
				plans.setExecution_order(Integer.parseInt(rs.getString("execution_order")));
				plans.setExec_id(rs.getString("exec_id"));
				plans.setExecution_notes(rs.getString("execution_notes"));
				plans.setExec_status(rs.getString("exec_status"));
				plansList.add(plans);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			colseConnection();
		}
		return plansList;
	}

	public String getTestPlan_Sql(String platSql) {
		StringBuffer str = new StringBuffer(
			"SELECT NHB.parent_id AS testsuite_id, NHA.parent_id AS tc_id, NHB.name, PLAT.name as platform_name, ");
		str.append("T.node_order AS execution_order , TCV.tc_external_id, ");
		str.append("E.id AS exec_id, E.notes as execution_notes, ");
		str.append("COALESCE(E.status,'n') AS exec_status FROM nodes_hierarchy NHA  JOIN nodes_hierarchy NHB ");
		str.append("ON NHA.parent_id = NHB.id  JOIN testplan_tcversions T ON NHA.id = T.tcversion_id  JOIN ");
		str.append("tcversions TCV ON NHA.id = TCV.id LEFT OUTER  JOIN executions E ON  (NHA.id = E.tcversion_id ");
		str.append("AND E.platform_id=T.platform_id AND  E.testplan_id=T.testplan_id ) ");
		str.append("LEFT OUTER JOIN platforms PLAT ON PLAT.id = T.platform_id LEFT OUTER JOIN user_assignments UA ");
		str.append("ON UA.feature_id = T.id AND UA.build_id=?   WHERE T.testplan_id = ?  ");
		str.append(platSql);
		str.append("AND (UA.type=1 OR UA.type IS NULL) ");
		str.append("ORDER BY testsuite_id,NHB.node_order,tc_id,T.platform_id,E.id ASC");
		return str.toString();
	}
	
	public List<String> getTestPlan(String projectId) {
		List<String> list = new ArrayList();
		String sql = "SELECT t.id,nodes.name FROM testplans t,nodes_hierarchy nodes " +
				" WHERE testproject_id = ? and nodes.id=t.id ";
		try {
			conn = ConnectionDb.getDBConnection();
			pstmt = conn.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			pstmt.setString(1, projectId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(Utils.setBaseStr(rs.getString("name"), rs.getString("id")));
			}
			list.add(0, "");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			colseConnection();
		}
		return list;
	}
	public List<String> getPlatForm(String planId) {
		String sql = "SELECT P.id, P.name FROM platforms P JOIN testplan_platforms TP ON P.id = TP.platform_id" +
				"  WHERE  TP.testplan_id = ?  ORDER BY name";
		List<String> list = new ArrayList();
		try {
			pstmt = conn.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			pstmt.setString(1, planId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(Utils.setBaseStr(rs.getString("name"), rs.getString("id")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			colseConnection();
		}
		return list;
	}
	public List<String> getBuildId(String planId) {
		String sql = "SELECT id, name FROM builds P WHERE testplan_id = ? ORDER BY id";
		List<String> list = new ArrayList();
		try {
			conn = ConnectionDb.getDBConnection();
			pstmt = conn.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			pstmt.setString(1, planId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(Utils.setBaseStr(rs.getString("name"), rs.getString("id")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			colseConnection();
		}
		return list;
	}
	
	public Map<String, String> getLastData(List<TestPlan> allList) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			for (TestPlan plan : allList) {
				map.put(plan.getCase_id(), plan.getExec_id());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	public List<TestPlan> returnActualList(List<TestPlan> allList, Map<String, String> maxMap) {
		List<TestPlan> actualList = new ArrayList<TestPlan>();
		try {
			for (TestPlan plans : allList) {
				if (plans.getExec_id().equals(maxMap.get(plans.getCase_id()))) {
					actualList.add(plans);
				}
			}
			ComparatorPlan comparator = new ComparatorPlan();
			Collections.sort(actualList, comparator);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return actualList;
	}
	public static void main(String[] arg){
		TestPlanDAO d = new TestPlanDAO();
		System.out.println(d.getTestPlan_Sql(""));
	}
}
