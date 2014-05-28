package com.sky.bo;

import com.utils.Utils;

public class TestPlan {
	public String testsuite_id;
	public String tc_id;
	public String name;
	public String platform_name;
	public int execution_order;
	public String exec_id;
	public String exec_status;
	public String execution_notes;
	public String case_id;
	
	public String getCase_id() {
		return case_id;
	}
	public void setCase_id(String case_id) {
		this.case_id = case_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTestsuite_id() {
		return testsuite_id;
	}
	public void setTestsuite_id(String testsuite_id) {
		this.testsuite_id = testsuite_id;
	}
	public String getTc_id() {
		return tc_id;
	}
	public void setTc_id(String tc_id) {
		this.tc_id = tc_id;
	}
	public String getPlatform_name() {
		return platform_name;
	}
	public void setPlatform_name(String platform_name) {
		this.platform_name = platform_name;
	}
	public int getExecution_order() {
		return execution_order;
	}
	public void setExecution_order(int execution_order) {
		this.execution_order = execution_order;
	}
	public String getExec_id() {
		return exec_id;
	}
	public void setExec_id(String exec_id) {
		this.exec_id = exec_id;
	}
	public String getExec_status() {
		return exec_status;
	}
	public void setExec_status(String exec_status) {
		this.exec_status = Utils.getPlanResult(exec_status);
	}
	public String getExecution_notes() {
		return execution_notes;
	}
	public void setExecution_notes(String execution_notes) {
		this.execution_notes = Utils.replace(execution_notes);
	}
	
}
