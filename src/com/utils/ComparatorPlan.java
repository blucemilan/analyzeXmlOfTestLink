package com.utils;

import java.util.Comparator;

import com.sky.bo.TestPlan;

public class ComparatorPlan implements Comparator<Object>  {
	public int compare(Object arg0, Object arg1) {
		TestPlan case0 = (TestPlan) arg0;
		TestPlan case1 = (TestPlan) arg1;
		if(case1.getExecution_order() > case0.getExecution_order()){
			return 0;
		}else{
			return 1;
		}
	}
}
