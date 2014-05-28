package com.sky.excel;

import java.util.List;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sky.bo.TestCase;
import com.sky.bo.TestStep;
import com.sky.bo.TestSuite;
import com.utils.PublicClass;
import com.utils.Utils;

public class FillExcelCaseData  extends PublicClass{

	
	public FillExcelCaseData(){

	}
	
	public void sheet_setProjectName(XSSFWorkbook wBook, String projectName, int sheetNum){
		XSSFSheet xSheet = wBook.getSheetAt(sheetNum);
		XSSFCell xCell = xSheet.getRow(0).getCell(0);
		xCell.setCellValue(xCell.getStringCellValue().replaceAll("X", projectName));
	}
	
	public void sheet_setSuiteData(XSSFWorkbook wBook, CellStyle style, List<TestSuite> dataList, int sheetNumPre){
		int sheetNum = sheetNumPre;
		
		XSSFSheet xSheet = null;
		for(TestSuite suite : dataList){			
			xSheet = wBook.createSheet();
			setColWidth(xSheet, style);
			System.out.println("sheetNum:" + sheetNum);
			System.out.println("name:" + Utils.getSheetName(suite.getName()));
			wBook.setSheetName(sheetNum, Utils.getSheetName(suite.getName()));	
			setSuiteTile(xSheet, suite.getName(), style);
			//设置case数据
			sheet_setCaseData(xSheet, style, suite);
			sheetNum++;
		}
	}
	
	public void sheet_setCaseData(XSSFSheet xSheet, CellStyle style, TestSuite suite){
		int seqNum = 0;
		for(TestCase cases : suite.getTestCaseList()){
			//System.out.println("	===case:" + cases.getName());
			createCell(xSheet, 1 + seqNum, 5 + seqNum, style, seqNum);
			XSSFCell xCell_1_0 = xSheet.getRow(seqNum + 1).getCell(0);
			xCell_1_0.setCellValue(style.cell_1_0);
			XSSFCell xCell_1_1 = xSheet.getRow(seqNum + 1).getCell(1);
			xCell_1_1.setCellValue(cases.getNumber());
			XSSFCell xCell_1_2 = xSheet.getRow(seqNum + 1).getCell(2);
			xCell_1_2.setCellValue(style.cell_1_2);
			XSSFCell xCell_1_3 = xSheet.getRow(seqNum + 1).getCell(3);
			xCell_1_3.setCellValue(cases.getName());
			style.setRowHeight(xSheet.getRow(seqNum + 1), cases.getName(), 40, 
					Short.parseShort(String.valueOf(style.mapRowHeight.get(1))));
			XSSFCell xCell_2_0 = xSheet.getRow(seqNum + 2).getCell(0);
			xCell_2_0.setCellValue(style.cell_2_0);
			XSSFCell xCell_2_1 = xSheet.getRow(seqNum + 2).getCell(1);
			xCell_2_1.setCellValue(cases.getGoal());
			style.setRowHeight(xSheet.getRow(seqNum + 2), cases.getGoal(), 100, 
					Short.parseShort(String.valueOf(style.mapRowHeight.get(2))));		
			mergedRegion(xSheet, seqNum + 2, 1, seqNum + 2, 3);
			XSSFCell xCell_3_0 = xSheet.getRow(seqNum + 3).getCell(0);
			xCell_3_0.setCellValue(style.cell_3_0);
			XSSFCell xCell_3_1 = xSheet.getRow(seqNum + 3).getCell(1);
			xCell_3_1.setCellValue(cases.getPreconditions());
			style.setRowHeight(xSheet.getRow(seqNum + 3), cases.getPreconditions(), 
					Short.parseShort(String.valueOf(style.mapRowHeight.get(3))));
			mergedRegion(xSheet, seqNum + 3, 1, seqNum + 3, 3);
			XSSFCell xCell_4_0 = xSheet.getRow(seqNum + 4).getCell(0);
			xCell_4_0.setCellValue(style.cell_4_0);
			XSSFCell xCell_4_1 = xSheet.getRow(seqNum + 4).getCell(1);
			xCell_4_1.setCellValue(cases.getRefRequirement());
			style.setRowHeight(xSheet.getRow(seqNum + 4), cases.getRefRequirement(), 
					Short.parseShort(String.valueOf(style.mapRowHeight.get(4))));			
			mergedRegion(xSheet, seqNum + 4, 1, seqNum + 4, 3);
			XSSFCell xCell_5_0 = xSheet.getRow(seqNum + 5).getCell(0);
			xCell_5_0.setCellValue(style.cell_5_0);
			XSSFCell xCell_5_1 = xSheet.getRow(seqNum + 5).getCell(1);
			xCell_5_1.setCellValue(style.cell_5_1);			
			XSSFCell xCell_5_2 = xSheet.getRow(seqNum + 5).getCell(2);
			xCell_5_2.setCellValue(style.cell_5_2);
			mergedRegion(xSheet, seqNum + 5, 2, seqNum + 5, 3);
			seqNum += 6;
			sheet_setStepData(xSheet, style, cases.getTestStepList(), seqNum);
			seqNum += cases.getTestStepList().size();
			seqLine(xSheet, seqNum, style);
		}		
	}
	
	public void sheet_setStepData(XSSFSheet xSheet, CellStyle style, List<TestStep> stepList, int seqNum){
		int stepNum = stepList.size() + seqNum - 1;	
		createCellWithOutStyle(xSheet, seqNum, stepNum + seqNum, style);	
		for(TestStep steps : stepList){
			XSSFCell xCell_step_0 = xSheet.getRow(seqNum).getCell(0);
			xCell_step_0.setCellValue(steps.getStep_number());				
			XSSFCell xCell_step_1 = xSheet.getRow(seqNum).getCell(1);
			xCell_step_1.setCellValue(steps.getActions());	
			XSSFCell xCell_step_2 = xSheet.getRow(seqNum).getCell(2);
			xCell_step_2.setCellValue(steps.getExpectedResults());
			XSSFCell xCell_step_3 = xSheet.getRow(seqNum).getCell(3);
			
			style.setRowHeight(xSheet.getRow(seqNum), steps.getActions(), steps.getExpectedResults(),
					Short.parseShort(String.valueOf(style.mapRowHeight.get(6))));
			mergedRegion(xSheet, seqNum, 2, seqNum, 3);
			if(stepNum == seqNum){
				xCell_step_0.setCellStyle((XSSFCellStyle)style.mapStyle.get("7_0"));
				xCell_step_1.setCellStyle((XSSFCellStyle)style.mapStyle.get("7_1"));
				xCell_step_2.setCellStyle((XSSFCellStyle)style.mapStyle.get("7_2"));
				xCell_step_3.setCellStyle((XSSFCellStyle)style.mapStyle.get("7_3"));
			}else{
				xCell_step_0.setCellStyle((XSSFCellStyle)style.mapStyle.get("6_0"));
				xCell_step_1.setCellStyle((XSSFCellStyle)style.mapStyle.get("6_1"));
				xCell_step_2.setCellStyle((XSSFCellStyle)style.mapStyle.get("6_2"));
				xCell_step_3.setCellStyle((XSSFCellStyle)style.mapStyle.get("6_3"));
			}
			seqNum++;
		}
		
	}
	
	public void createCell(XSSFSheet xSheet, int beginRow, int endRow, CellStyle style, int seqNum){
		for (int i = beginRow; i <= endRow; i++) {
			XSSFRow currRow = xSheet.createRow(i);
			currRow.setHeight(Short.parseShort(style.mapRowHeight.get(i - seqNum).toString()));
			for(int j = 0; j < Utils.EXCEL_COLNUM; j++){
				XSSFCell cell = currRow.createCell(j);
				cell.setCellStyle((XSSFCellStyle)style.mapStyle.get((i - seqNum) + "_" + (j)));
			}		
		}
	}

	public void createCellWithOutStyle(XSSFSheet xSheet, int beginRow, int endRow, CellStyle style){
		for (int i = beginRow; i <= endRow; i++) {
			XSSFRow currRow = xSheet.createRow(i);
			currRow.setHeight(Short.parseShort(style.mapRowHeight.get(6).toString()));
			for(int j = 0; j < Utils.EXCEL_COLNUM; j++){
				currRow.createCell(j);
			}		
		}
	}
	
	public void createCellSeq(XSSFSheet xSheet, int beginRow, int endRow, CellStyle style){
		for (int i = beginRow; i <= endRow; i++) {
			XSSFRow currRow = xSheet.createRow(i);
			currRow.setHeight(Short.parseShort(style.mapRowHeight.get(8).toString()));
			for(int j = 0; j < Utils.EXCEL_COLNUM; j++){
				currRow.createCell(j);
			}		
		}
	}
	
	public void seqLine(XSSFSheet xSheet, int lineNum, CellStyle style){
		createCellSeq(xSheet, lineNum, lineNum, style);
		mergedRegion(xSheet, lineNum, 0, lineNum, 3);
	}
	
	public void mergedRegion(XSSFSheet xSheet, int bRow, int bCol, int eRow, int eCol){
		xSheet.addMergedRegion((new CellRangeAddress(bRow, eRow, bCol , eCol)));
	}
	
	public void setSuiteTile(XSSFSheet xSheet, String name, CellStyle style){
		//设置suite名称
		createCell(xSheet, 0, 0, style, 0);
		mergedRegion(xSheet, 0, 0, 0, 3);
		XSSFCell xCell = xSheet.getRow(0).getCell(0);
		xCell.setCellValue(style.cell_0_0.replaceAll("X", name));
	}
	
	public void setColWidth(XSSFSheet xSheet, CellStyle style){
		for(int j = 0; j < Utils.EXCEL_COLNUM; j++){
			xSheet.setColumnWidth(j, (Integer)style.mapColWidth.get(j));
		}
	}
}
