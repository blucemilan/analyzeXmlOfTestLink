package com.sky.excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sky.bo.TestPlan;
import com.utils.PublicClass;

public class CreatePlanExcel extends PublicClass{
	
	private String modelPath = "";
	private String outPutFilePath = "";
	private String projectName = "";
	private CellPlanStyle style;
	private XSSFWorkbook wBook;

	public CreatePlanExcel(String modelPath, String projectName, String filePath) {
		try{
			this.modelPath = modelPath;
			this.outPutFilePath = filePath;
			this.projectName = projectName;
			wBook = readModel();
			style = new CellPlanStyle(wBook, 0, 2, 0, 8 );
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	public boolean createExcel(List<TestPlan> planList) {
		FillExcelPlanData planData = new FillExcelPlanData();
		FileOutputStream outputStream = null;
		try{
			wBook.removeSheetAt(0);
			planData.sheet_setPlanData(wBook, style, planList, projectName);		
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
