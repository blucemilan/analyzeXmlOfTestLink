package com.sky.excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sky.bo.TestSuite;
import com.utils.PublicClass;

public class CreateExcel extends PublicClass{
	
	private String modelPath = "";
	private String outPutFilePath = "";
	private String projectName = "";
	private CellStyle style;
	private XSSFWorkbook wBook;

	public CreateExcel(String modelPath, String projectName, String filePath) {
		try{
			this.modelPath = modelPath;
			this.outPutFilePath = filePath;
			this.projectName = projectName;
			wBook = readModel();
			style = new CellStyle(wBook, 0, 2, 0, 8 );
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	public boolean createExcel(List<TestSuite> dataList) {
		FillExcelCaseData casesData = new FillExcelCaseData();
		FileOutputStream outputStream = null;
		try{
			wBook.removeSheetAt(1);
			casesData.sheet_setProjectName(wBook, projectName, 0);
			casesData.sheet_setSuiteData(wBook, style, dataList, 1);		
			outputStream = new FileOutputStream(this.outPutFilePath);
	        wBook.write(outputStream);   
	        return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			if(null!=outputStream){
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public XSSFWorkbook readModel() {
		try {
			return new XSSFWorkbook(new FileInputStream(modelPath));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
