package com.sky.testlinkconvert;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.sky.bo.TestCase;
import com.sky.bo.TestStep;
import com.sky.bo.TestSuite;
import com.utils.Utils;

public class XmlToElement {
	
	public static void main(String[] args){
		List<TestSuite> list  = transferXMLToElement("C:\\Users\\Administrator\\Downloads\\testsuites.xml", "a");
		for(TestSuite suite : list){
			System.out.println(suite.getName());
		}
	}
	
	public static List<TestSuite> transferXMLToElement(String filename, String projectPrefix) {
		Document doc = null;
		File f = new File(filename);
		SAXReader reader = new SAXReader();
		try {
			doc = reader.read(f);
			Element root = doc.getRootElement();
			boolean isMulSuiteLevel = checkMulSuiteLevel(doc);
			if(!isMulSuiteLevel){
				List<TestSuite> testSuiteList = new ArrayList<TestSuite>();
				TestSuite testSuite = new TestSuite();
				testSuite.setName(root.attributeValue(Utils.ATTRIBUTE_NAME));	
				testSuite = forEachCaseElement(root, testSuite, projectPrefix);
				testSuiteList.add(testSuite);
				return testSuiteList;
			}
			return forEachSuiteElement(root, projectPrefix);
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/*public static List<TestSuite> transferXMLToElementInPlan(String filename, String projectPrefix) {
		Document doc = null;
		File f = new File(filename);
		SAXReader reader = new SAXReader();
		try {
			doc = reader.read(f);
			Element root = doc.getRootElement();
			for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
				Element subelement = (Element) it.next();
				if (subelement.getName().equals(Utils.TESTSUILTS_TAG)){
					return forEachSuiteElement(subelement, projectPrefix);
				}
			}
			return null;
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}*/

	private static List<TestSuite> forEachSuiteElement(Element element, String projectPrefix){
		List<TestSuite> testSuiteList = new ArrayList<TestSuite>();
		TestSuite testSuite = null;
		try{
			for (Iterator<Element> it = element.elementIterator(); it.hasNext();) {
				Element subelement = (Element) it.next();
				if (subelement.getName().equals(Utils.TESTSUILT_TAG)&&!"".equals(subelement.attributeValue(Utils.ATTRIBUTE_NAME))) {
					testSuite = new TestSuite();
					testSuite.setName(subelement.attributeValue(Utils.ATTRIBUTE_NAME));
					//testSuite = forEachCaseElement(subelement, testSuite, projectPrefix);
					testSuiteList = forEachChildSuiteElement(subelement, testSuite, projectPrefix, testSuiteList);
				}else if(subelement.getName().equals(Utils.TESTCASE_CASE)){
					testSuite = new TestSuite();
					testSuite.setName(Utils.DEFAULT_SHEETNAME);	
					testSuite = forEachCaseElementOnlyCase(subelement, testSuite, projectPrefix);
					testSuiteList.add(testSuite);
				}			
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return testSuiteList;
	}
	
	//获得下级Suite迭代信息
	private static List<TestSuite> forEachChildSuiteElement(Element element, TestSuite testSuite, String projectPrefix, List<TestSuite> testSuiteList){
		TestSuite testSuiteTmp = null;
		boolean flag = true;
		try{
			for (Iterator<Element> it = element.elementIterator(); it.hasNext();) {
				Element subelement = (Element) it.next();
				if (subelement.getName().equals(Utils.TESTSUILT_TAG)&&!"".equals(subelement.attributeValue(Utils.ATTRIBUTE_NAME))) {
					testSuiteTmp = new TestSuite();
					testSuiteTmp.setName(testSuite.getName() + "->" + subelement.attributeValue(Utils.ATTRIBUTE_NAME));
					testSuiteTmp = forEachCaseElement(subelement, testSuiteTmp, projectPrefix);
					testSuiteList.add(testSuiteTmp);
					flag = false;
				}			
			}
			if(flag){
				testSuite = forEachCaseElement(element, testSuite, projectPrefix);
				testSuiteList.add(testSuite);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return testSuiteList;
	}
	
	private static TestSuite forEachCaseElement(Element caseElement, TestSuite testSuite, String projectPrefix){
		List<TestCase> testCaseList = new ArrayList<TestCase>();
		TestCase testCase = null;
		try{
			for (Iterator<Element> it = caseElement.elementIterator(); it.hasNext();) {
				Element subelement = (Element) it.next();
				if(Utils.TESTCASE_CASE.equals(subelement.getName())){
					testCase = new TestCase();
					testCase.setNumber(projectPrefix + "-" +subelement.elementText(Utils.TESTCASE_NUM));
					testCase.setName(subelement.attributeValue(Utils.ATTRIBUTE_NAME));
					testCase.setPreconditions(subelement.elementText(Utils.TESTCASE_TEXT_PRECONDITIONS));
					testCase.setGoal(subelement.elementText(Utils.TESTCASE_TEXT_SUMMARY));
					testCase.setTestStepList(forEachStepElement(subelement));
					testCaseList.add(testCase);
				}
			}
			testSuite.setTestCaseList(testCaseList);
		}catch(Exception e){
			e.printStackTrace();
		}
		return testSuite;
	}
	private static TestSuite forEachCaseElementOnlyCase(Element caseElement, TestSuite testSuite, String projectPrefix){
		List<TestCase> testCaseList = new ArrayList<TestCase>();
		TestCase testCase = new TestCase();
		try{
			testCase.setNumber(projectPrefix + "-" + caseElement.elementText(Utils.TESTCASE_NUM));
			testCase.setName(caseElement.attributeValue(Utils.ATTRIBUTE_NAME));
			testCase.setPreconditions(caseElement.elementText(Utils.TESTCASE_TEXT_PRECONDITIONS));
			testCase.setGoal(caseElement.elementText(Utils.TESTCASE_TEXT_SUMMARY));
			testCase.setTestStepList(forEachStepElement(caseElement));
			testCaseList.add(testCase);
			testSuite.setTestCaseList(testCaseList);
		}catch(Exception e){
			e.printStackTrace();
		}
		return testSuite;
	}
	private static List<TestStep> forEachStepElement(Element stepsElement){
		List<TestStep> testCaseList = new ArrayList<TestStep>();
		TestStep testStep = null;
		try{
			for (Iterator<Element> it = stepsElement.elementIterator(); it.hasNext();) {
				Element subelement = (Element) it.next();
				if (subelement != null&&Utils.TESTSTEP_STEPNAMES.equals(subelement.getName())) {
					List<Element> stepsList = subelement.elements(Utils.TESTSTEP_STEPNAME);
					for(Element step : stepsList){
						testStep = new TestStep();
						testStep.setStep_number(step.elementText(Utils.TESTSTEP_NUMBER));
						testStep.setActions(step.elementText(Utils.TESTSTEP_ACTIONS));
						testStep.setExpectedResults(step.elementText(Utils.TESTSTEP_EXPECTEDRESULTS));
						testCaseList.add(testStep);
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return testCaseList;
	}
	//判断suite层次，1层false,多层true
	public static boolean checkMulSuiteLevel(Document doc){
		if(doc.asXML().split("<testsuite").length==2){
			return false;
		}
		return true;
	}
	

}