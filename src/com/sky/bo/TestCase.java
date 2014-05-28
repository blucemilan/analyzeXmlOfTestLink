package com.sky.bo;

import java.util.ArrayList;
import java.util.List;

import com.utils.Utils;

public class TestCase {
	private String id;
	private String parent_id;
	private String name;
	private String number;
	private String goal;
	private String preconditions;
	private String refRequirement;
	private String importance;
	private String version;
	private String node_order;
	private List<TestStep> testStepList = new ArrayList<TestStep>();
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getNode_order() {
		return node_order;
	}
	public void setNode_order(String node_order) {
		this.node_order = node_order;
	}
	public String getImportance() {
		return importance;
	}
	public void setImportance(String importance) {
		this.importance = importance;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = Utils.replace(name);
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getGoal() {
		return goal;
	}
	public void setGoal(String goal) {
		this.goal = Utils.replace(goal);
	}
	public String getPreconditions() {
		return preconditions;
	}
	public List<TestStep> getTestStepList() {
		return testStepList;
	}
	public void setTestStepList(List<TestStep> testStepList) {
		this.testStepList = testStepList;
	}
	public void setPreconditions(String preconditions) {
		this.preconditions = Utils.replace(preconditions);
	}
	public String getRefRequirement() {
		return refRequirement;
	}
	public void setRefRequirement(String refRequirement) {
		this.refRequirement = refRequirement;
	}

}
