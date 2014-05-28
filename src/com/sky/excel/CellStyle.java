package com.sky.excel;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.utils.PublicClass;
import com.utils.Utils;

public class CellStyle extends PublicClass{
	public Map<String, XSSFCellStyle> mapStyle;
	public Map<Integer, Short> mapRowHeight;
	public Map<Integer, Integer> mapColWidth;
	public String cell_0_0;
	public String cell_1_0;
	public String cell_1_2;
	public String cell_2_0;
	public String cell_3_0;
	public String cell_4_0;
	public String cell_5_0;
	public String cell_5_1;
	public String cell_5_2;
	
	public CellStyle(XSSFWorkbook wBook, int bRowNum_plan, int eRowNum_plan, int bRowNum, int eRowNum){
		XSSFSheet sheet = wBook.getSheetAt(1);
		setCellName(sheet);
		setColWidth(sheet);
		setStyle(wBook, sheet, bRowNum, eRowNum);
	}
		
	public void setCellName(XSSFSheet sheet){
		cell_0_0 = sheet.getRow(0).getCell(0).getStringCellValue();
		cell_1_0 = sheet.getRow(1).getCell(0).getStringCellValue();
		cell_1_2 = sheet.getRow(1).getCell(2).getStringCellValue();
		cell_2_0 = sheet.getRow(2).getCell(0).getStringCellValue();
		cell_3_0 = sheet.getRow(3).getCell(0).getStringCellValue();
		cell_4_0 = sheet.getRow(4).getCell(0).getStringCellValue();
		cell_5_0 = sheet.getRow(5).getCell(0).getStringCellValue();
		cell_5_1 = sheet.getRow(5).getCell(1).getStringCellValue();
		cell_5_2 = sheet.getRow(5).getCell(2).getStringCellValue();
	}

	public void setColWidth(XSSFSheet sheet){
		mapColWidth = new HashMap<Integer, Integer>();
		for(int j = 0; j < Utils.EXCEL_COLNUM; j++){
			mapColWidth.put(j, sheet.getColumnWidth(j));
		}	
	}
	public void setStyle(XSSFWorkbook wBook,XSSFSheet sheet, int bRowNum, int eRowNum){
		XSSFCellStyle style = null;
		mapStyle = new HashMap<String, XSSFCellStyle>();
		mapRowHeight = new HashMap<Integer, Short>();		
		for(int i = bRowNum; i <= eRowNum; i++){
			XSSFRow currRow = sheet.getRow(i);
			mapRowHeight.put(i, currRow.getHeight());
			for(int j = 0; j < Utils.EXCEL_COLNUM; j++){
				style = wBook.createCellStyle();
				style.cloneStyleFrom(currRow.getCell(j).getCellStyle());
				mapStyle.put(i + "_" + j, style);
			}			
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
			currRow.setHeight(Short.parseShort(String.valueOf((short)(0.8*num*height))));
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
