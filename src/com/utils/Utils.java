package com.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils extends PublicClass{
	public static final String DEFAULT_SHEETNAME = "测试用例";
	public static final String TESTSUILT_TAG = "testsuite";
	public static final String TESTSUILTS_TAG = "testsuites";
	public static final String TESTCASE_CASES = "testcases";
	public static final String TESTCASE_NUM = "externalid";
	public static final String TESTCASE_CASE = "testcase";
	public static final String TESTSTEP_STEPNAME = "step";
	public static final String TESTSTEP_STEPNAMES = "steps";
	public static final String TESTSTEP_NUMBER = "step_number";
	public static final String TESTSTEP_ACTIONS = "actions";
	public static final String TESTSTEP_EXPECTEDRESULTS = "expectedresults";
	public static final String ATTRIBUTE_NAME = "name";
	public static final String TESTCASE_TEXT_PRECONDITIONS = "PRECONDITIONS";
	public static final String TESTCASE_TEXT_SUMMARY = "summary";
	public static final int EXCEL_COLNUM = 4;
	public static final int EXCEL_PLANCOLNUM = 6;
	public static final int DEFAULTBRLENGTH = 30;
	public static String getPropertyFilePath() {	
		String url = System.getProperty("user.dir") + "\\configuration\\app.config";
		return url;
	}
	public static String getExcelName(String oldfilename, long time) {
		String newfilename = "";
		String[] temp = oldfilename.split("\\\\");
		String prename = temp[(temp.length - 1)].substring(0,
				temp[(temp.length - 1)].length() - 4);
		newfilename = oldfilename.substring(0, oldfilename.length()
				- temp[(temp.length - 1)].length())
				+ "Excel_" + prename + "_" + time + ".xls";

		return newfilename;
	}

	public static String replace(String oldStr) {
		if (oldStr != null) {
			String newStr = "";
			newStr = oldStr.replaceAll("<([^>]*)>", "");
			newStr = newStr.replaceAll("&\\w*;", "");
			if(newStr!=null&&newStr.length()>2){
				if(newStr.endsWith("\n")||newStr.endsWith("\r")){
					newStr = newStr.substring(0, newStr.length()-2);
				}
			}
			if(!"".equals(newStr)){
				return newStr.trim();
			}else{
				return "";
			}			
		}
		return "";
	}
	
	public static String replaceGoal(String oldStr) {
		String newStr = replace(oldStr);
		if (oldStr != null) {

			newStr = oldStr.replaceAll("<([^>]*)>", "");
			newStr = newStr.replaceAll("&\\w*;", "");
			return newStr;
		}
		return "";
	}
	
	public static String getSheetName(String sheetName) {
		if (sheetName != null&&sheetName.length()>30) {
			return sheetName.substring(0, 27) + "...";
		}
		return sheetName;
	}
	
	public static String getProjectName(String name) {
		if (name != null) {
			return name.substring(0, name.indexOf("("));
		}
		return "";
	}

	public static String getProjectId(String name) {
		if (name != null) {
			return name.substring(name.indexOf("[") + 1, name.length() - 1);
		}
		return "";
	}

	public static String getPrefix(String name) {
		if (name != null) {
			return name.substring(name.indexOf("(") + 1, name.indexOf(")"));
		}
		return "";
	}
	public static String getBaseId(String name) {
		if (name != null) {
			return name.substring(name.indexOf("(") + 1, name.indexOf(")"));
		}
		return "";
	}
	public static String setBaseStr(String name, String id) {
		return name + "(" + id + ")";
	}
	
	public static String returnImportance(String importance) {
		if ("1".equals(importance))
			return "低";
		else if ("2".equals(importance))
			return "中";
		else if ("3".equals(importance)) {
			return "高";
		}
		return "";
	}
	
	public static String getFileName(String projectName, String fileType) {
		Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return System.getProperty("user.dir") + "\\output\\" + projectName + "测试用例_" + sdf.format(d) + "." + fileType;
	}
	
	public static String getPlanResult(String result) {	
		if("f".equals(result)){
			return "Failed";
		}else if("p".equals(result)){
			return "Passed";
		}else if("b".equals(result)){
			return "Blocked";
		}else{
			return "Not Run";
		}
	}
}
