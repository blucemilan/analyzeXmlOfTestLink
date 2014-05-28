package com.sky.word;

import java.io.File;
import java.util.List;

import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.JcEnumeration;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.Tbl;

import com.sky.bo.TestCase;
import com.sky.bo.TestSuite;
import com.utils.PublicClass;
import com.utils.Utils;

public class CreateWord extends PublicClass{
	private String modelPath = "";
	private String outPutFilePath = "";
	private String projectName = "";

	public CreateWord(String modelPath, String projectName, String outPutFilePath) {
		this.modelPath = modelPath;
		this.outPutFilePath = outPutFilePath;
		this.projectName = projectName;
	}

	public boolean createWord(List<TestSuite> dataList) {
		ObjectFactory factory = new ObjectFactory();
		try{
			WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new File(modelPath));
			MainDocumentPart part = wordMLPackage.getMainDocumentPart();
			//标题
			WordUitls.rePlaceTxt("XXXX", projectName, part);
			// 页眉
			WordUitls.rePlaceTxtInHead("测试用例样例", projectName, wordMLPackage);
			for(TestSuite suite : dataList){
				part.addObject(WordUitls.createNumberedParagraph(8, 1, "2", suite.getName(), factory));
				List<TestCase> casesList = suite.getTestCaseList();
				for(TestCase cases : casesList){
					part.addObject(WordUitls.createNumberedParagraph(8, 2, "3", cases.getName(), factory));
					CreateTable table = new CreateTable();	
					Tbl tbl = table.createBorderTable(5 + cases.getTestStepList().size(), 3, WordUitls.getTableWidth());
					wordMLPackage.getMainDocumentPart().addObject(tbl);
					FillWordTableData.fillTableData(tbl, cases, true, "宋体", "21", true, JcEnumeration.CENTER, "宋体", "21", false, JcEnumeration.LEFT);
				}							
			}
			wordMLPackage.save(new File(outPutFilePath));
	        return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{

		}
		
	}
}
