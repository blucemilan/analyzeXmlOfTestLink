package com.sky.excel;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sky.bo.TestPlan;
import com.utils.PublicClass;
import com.utils.Utils;

public class FillExcelPlanData extends PublicClass{
	
	public FillExcelPlanData(){

	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void sheet_setPlanData(XSSFWorkbook wBook, CellPlanStyle style, List<TestPlan> planList, String projectName){
		XSSFSheet xSheet = null;	
        xSheet = wBook.createSheet();
		setColWidth(xSheet, style);
		wBook.setSheetName(0,  "测试结果");	
		setPlanTile(xSheet, projectName, style);
		setColTile(xSheet, style);
		//设置plan数据
		sheet_setPlanListData(xSheet, style, planList);		    
	}
	
	public void sheet_setPlanListData(XSSFSheet xSheet, CellPlanStyle style, List<TestPlan> list){
		int seqNum = 0;
		createCellLockCol(xSheet, 2, 2 + list.size(), style, 2);
		for(TestPlan plans : list){		
			XSSFCell xCell_1_0 = xSheet.getRow(seqNum + 2).getCell(0);
			xCell_1_0.setCellValue(plans.getCase_id());
			
			XSSFCell xCell_1_1 = xSheet.getRow(seqNum + 2).getCell(1);
			xCell_1_1.setCellValue(plans.getName());
			style.setRowHeight(xSheet.getRow(seqNum + 2), plans.getName(), 50, 
					Short.parseShort(String.valueOf(style.mapRowHeight_plan.get(1))));
			XSSFCell xCell_1_2 = xSheet.getRow(seqNum + 2).getCell(2);
			xCell_1_2.setCellValue(plans.getExec_status());
			
			XSSFCell xCell_1_3 = xSheet.getRow(seqNum + 2).getCell(3);
			xCell_1_3.setCellValue(plans.getExecution_notes());
			
			XSSFCell xCell_1_4 = xSheet.getRow(seqNum + 2).getCell(4);
			xCell_1_4.setCellValue("");
			
			XSSFCell xCell_1_5 = xSheet.getRow(seqNum + 2).getCell(5);
			xCell_1_5.setCellValue("");
			seqNum++;
		}		
	}
	
	public void setPlanTile(XSSFSheet xSheet, String name, CellPlanStyle style){
		//设置plan名称
		mergedRegion(xSheet, 0, 0, 0, 5);
		createCell(xSheet, 0, 0, style, 0);
		XSSFCell xCell = xSheet.getRow(0).getCell(0);
		xCell.setCellValue(style.cellplan_0_0.replaceAll("X", name));
	}
	
	public void setColTile(XSSFSheet xSheet, CellPlanStyle style){
		createCell(xSheet, 1, 1, style, 0);
		xSheet.getRow(1).getCell(0).setCellValue(style.cellplan_1_0);
		xSheet.getRow(1).getCell(1).setCellValue(style.cellplan_1_1);
		xSheet.getRow(1).getCell(2).setCellValue(style.cellplan_1_2);
		xSheet.getRow(1).getCell(3).setCellValue(style.cellplan_1_3);
		xSheet.getRow(1).getCell(4).setCellValue(style.cellplan_1_4);
		xSheet.getRow(1).getCell(5).setCellValue(style.cellplan_1_5);
	}
	
	public void createCell(XSSFSheet xSheet, int beginRow, int endRow, CellPlanStyle style, int seqNum){
		for (int i = beginRow; i <= endRow; i++) {
			XSSFRow currRow = xSheet.createRow(i);
			currRow.setHeight(Short.parseShort(style.mapRowHeight_plan.get(i + seqNum).toString()));
			for(int j = 0; j < Utils.EXCEL_PLANCOLNUM; j++){
				XSSFCell cell = currRow.createCell(j);
				cell.setCellStyle((XSSFCellStyle)style.mapStyle_plan.get((i + seqNum) + "_" + (j)));
			}		
		}
	}
	public void createCellLockCol(XSSFSheet xSheet, int beginRow, int endRow, CellPlanStyle style, int lockCol){
		for (int i = beginRow; i < endRow; i++) {
			XSSFRow currRow = xSheet.createRow(i);
			currRow.setHeight(Short.parseShort(style.mapRowHeight_plan.get(lockCol).toString()));
			for(int j = 0; j < Utils.EXCEL_PLANCOLNUM; j++){
				XSSFCell cell = currRow.createCell(j);
				cell.setCellStyle((XSSFCellStyle)style.mapStyle_plan.get((lockCol) + "_" + (j)));
			}		
		}
	}
	
	public void setColWidth(XSSFSheet xSheet, CellPlanStyle style){
		for(int j = 0; j < Utils.EXCEL_PLANCOLNUM; j++){
			xSheet.setColumnWidth(j, (Integer)style.mapColWidth_plan.get(j));
		}
	}
	public void mergedRegion(XSSFSheet xSheet, int bRow, int bCol, int eRow, int eCol){
		xSheet.addMergedRegion((new CellRangeAddress(bRow, eRow, bCol , eCol)));
	}
}
