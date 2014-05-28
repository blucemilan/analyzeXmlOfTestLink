package com.sky.bo;

import java.util.ArrayList;
import java.util.List;

public class TestSuite {
	private String name;
	private String id;
	private List<TestCase> testCaseList = new ArrayList<TestCase>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<TestCase> getTestCaseList() {
		return testCaseList;
	}
	public void setTestCaseList(List<TestCase> testCaseList) {
		this.testCaseList = testCaseList;
	}
	public TestSuite(String name, List<TestCase> testCaseList){
		this.name = name;
		this.testCaseList = testCaseList;
	}
	public TestSuite(){
		
	}
	
}
