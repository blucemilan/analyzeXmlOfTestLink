package com.sky.bo;

import com.utils.Utils;

public class TestStep {
	private String parent_id;
	private String id;
	private String step_number;
	private String actions;
	private String expectedResults;
		
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStep_number() {
		return step_number;
	}
	public void setStep_number(String step_number) {
		this.step_number = step_number;
	}
	public String getActions() {
		return actions;
	}
	public void setActions(String actions) {
		this.actions = Utils.replace(actions);
	}
	public String getExpectedResults() {
		return expectedResults;
	}
	public void setExpectedResults(String expectedResults) {
		this.expectedResults = Utils.replace(expectedResults);
	}
	
}
