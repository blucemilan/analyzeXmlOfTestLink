package com.utils;

import java.util.Comparator;

import com.sky.bo.TestCase;

public class ComparatorCase implements Comparator<TestCase> {
	public int compare(TestCase case0, TestCase case1) {
		return case0.getNode_order().hashCode() - case1.getNode_order().hashCode();
	}
	
}
