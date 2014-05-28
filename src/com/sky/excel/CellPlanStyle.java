package com.sky.excel;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.utils.PublicClass;
import com.utils.Utils;

public class CellPlanStyle extends PublicClass{
	public Map<String, XSSFCellStyle> mapStyle_plan;
	public Map<Integer, Short> mapRowHeight_plan;
	public Map<Integer, Integer> mapColWidth_plan;
	public String cellplan_0_0;
	public String cellplan_1_0;
	public String cellplan_1_1;
	public String cellplan_1_2;
	public String cellplan_1_3;
	public String cellplan_1_4;
	public String cellplan_1_5;
	
	public CellPlanStyle(XSSFWorkbook wBook, int bRowNum_plan, int eRowNum_plan, int bRowNum, int eRowNum){
		XSSFSheet sheet = wBook.getSheetAt(0);
		setCellName_plan(sheet);
		setColWidth_plan(sheet);
		setStyle_plan(wBook, sheet, bRowNum_plan, eRowNum_plan);
	}

	public void setStyle_plan(XSSFWorkbook wBook,XSSFSheet sheet, int bRowNum, int eRowNum){
		XSSFCellStyle style = null;
		mapStyle_plan = new HashMap<String, XSSFCellStyle>();
		mapRowHeight_plan = new HashMap<Integer, Short>();		
		for(int i = bRowNum; i <= eRowNum; i++){
			XSSFRow currRow = sheet.getRow(i);
			mapRowHeight_plan.put(i, currRow.getHeight());
			for(int j = 0; j < Utils.EXCEL_PLANCOLNUM; j++){
				style = wBook.createCellStyle();
				style.cloneStyleFrom(currRow.getCell(j).getCellStyle());
				mapStyle_plan.put(i + "_" + j, style);
			}			
		}	
	}
	public void setCellName_plan(XSSFSheet sheet){
		cellplan_0_0 = sheet.getRow(0).getCell(0).getStringCellValue();
		cellplan_1_0 = sheet.getRow(1).getCell(0).getStringCellValue();
		cellplan_1_1 = sheet.getRow(1).getCell(1).getStringCellValue();
		cellplan_1_2 = sheet.getRow(1).getCell(2).getStringCellValue();
		cellplan_1_3 = sheet.getRow(1).getCell(3).getStringCellValue();
		cellplan_1_4 = sheet.getRow(1).getCell(4).getStringCellValue();
		cellplan_1_5 = sheet.getRow(1).getCell(5).getStringCellValue();
	}

	public void setColWidth_plan(XSSFSheet sheet){
		mapColWidth_plan = new HashMap<Integer, Integer>();
		for(int j = 0; j < Utils.EXCEL_PLANCOLNUM; j++){
			mapColWidth_plan.put(j, sheet.getColumnWidth(j));
		}	
	}
	
	public void setRowHeight(XSSFRow currRow,String content, short height){
		int num = brNum(content);
		setRowHeightValue(currRow, num, height);
	}
	//按照内容长度换行
	public void setRowHeight(XSSFRow currRow,String content, int length, short height){
		int num = brNumLength(content, length);
		setRowHeightValue(currRow, num, height);
	}
	
	public void setRowHeight(XSSFRow currRow,String content1, String content2, short height){
		int num1 = brNum(content1);
		int num2 = brNum(content2);
		int maxNum = getMaxNum(num1, num2);
		setRowHeightValue(currRow, maxNum, height);
	}
	
	public void setRowHeightValue(XSSFRow currRow, int num, short height){
		if(num > 1){	
			currRow.setHeight(Short.parseShort(String.valueOf((short)(1*num*height))));
		}
	}
	//临时定义action长度30换行
	public int brNum(String content){
		int num1 = 0, num2 = 0;
		if(null!=content&&!"".equals(content)&&content.length()>Utils.DEFAULTBRLENGTH){
			num1 = (content.length()/Utils.DEFAULTBRLENGTH) + 1;
		}
		if(null!=content&&!"".equals(content)&&content.indexOf("\r")!=-1){
			num2 = content.split("\r").length;
		}else if(null!=content&&!"".equals(content)&&content.indexOf("<br />")!=-1){
			num2 = content.split("<br />").length;
		}else if(null!=content&&!"".equals(content)&&content.indexOf("\n")!=-1){
			num2 = content.split("\n").length;
		}
		return getMaxNum(num1, num2);
	}
	public int brNumLength(String content, int length){
		int num1 = 0, num2 = 0;
		if(null!=content&&!"".equals(content)&&content.length()>length){
			num1 = (content.length()/length) + 1;
		}
		if(null!=content&&!"".equals(content)&&content.indexOf("\r")!=-1){
			num2 = content.split("\r").length;
		}else if(null!=content&&!"".equals(content)&&content.indexOf("<br />")!=-1){
			num2 = content.split("<br />").length;
		}else if(null!=content&&!"".equals(content)&&content.indexOf("\n")!=-1){
			num2 = content.split("\n").length;
		}
		return getMaxNum(num1, num2);
	}
	
	public int getMaxNum(int num1, int num2){
		if(num1 > num2){
			return num1;
		}else{
			return num2;
		}
	}
	public int getMinNum(int num1, int num2){
		if(num1 > num2){
			return num2;
		}else{
			return num1;
		}
	}
}
