package com.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.sky.dao.TestProjectDAO;

public class RcpUtils {
	public static Map<String, String> modelPathByTypeMap = new HashMap<String, String>();
	public static Map<String, String> fileTypeMap = new HashMap<String, String>();
	public static String CASEBYEXCEL = "导出用例信息(Excel)";
	public static String CASEBYWORD = "导出用例信息(Word)";
	public static List<String> projectList = null;
	static{
		modelPathByTypeMap.put(CASEBYEXCEL, getDefaultExcelModelFilePath());
		modelPathByTypeMap.put(CASEBYWORD, getDefaultWordModelFilePath());
		projectList = getProjectList();
		
		fileTypeMap.put(CASEBYEXCEL, ".xlsx");
		fileTypeMap.put(CASEBYWORD, ".docx");
	}
	// 如果字符串最后一个字符是";"，则去除
	public static String dealString(String str) {
		if (";".equals(str.substring(str.length() - 1))) {
			return str.substring(0, str.length() - 1);
		}
		return str;
	}
		
	public static boolean checkIsNull(String projectName, String xmlPath){
		if(null==projectName||"".equals(projectName)||null==xmlPath||"".equals(xmlPath)){
			return false;
		}else{
			return true;
		}
	}
	public static boolean checkIsNull(String str){
		if(null==str||"".equals(str)){
			return false;
		}else{
			return true;
		}
	}
	public static String getDefaultExcelModelFilePath() {	
		String url = System.getProperty("user.dir") + "\\model\\model.xlsx";
		return url;
	}
	
	public static String getDefaultWordModelFilePath() {	
		String url = System.getProperty("user.dir") + "\\model\\model.docx";
		return url;
	}
	
	public static String getDefaultPlanModelFilePath() {	
		String url = System.getProperty("user.dir") + "\\model\\planModel.xlsx";
		return url;
	}
	
	// 初始化label
	public static Label initLabelControl(Group parentGroup, 
			String name, int widthStart, int heightStart, int width, int height) {
		Label label = new Label(parentGroup, SWT.NONE);
		label.setAlignment(SWT.RIGHT);
		label.setText(name);
		label.setFont(SWTResourceManager.getFont(CommonData.FONTSTYLE,
				CommonData.FONTSIZE, SWT.NORMAL));
		label.setBounds(widthStart, heightStart, width, height);
		return label;
	}

	// 初始化radio
	public static Button initRadioControl(Group parentGroup, 
			String text, boolean selected, int widthStart, int heightStart,
			int width, int height) {
		Button btn = new Button(parentGroup, SWT.RADIO);
		btn.setText(text);
		btn.setFont(SWTResourceManager.getFont(CommonData.FONTSTYLE, CommonData.FONTSIZE, SWT.NORMAL));
		btn.setBounds(widthStart, heightStart, width, height);
		return btn;
	}

	// 初始化text
	public static Text initTextControl(Group parentGroup,
			String initText, int style, boolean enable, int widthStart,
			int heightStart, int width, int height) {
		Text text = new Text(parentGroup, style);
		text.setFont(SWTResourceManager.getFont(CommonData.FONTSTYLE,
				CommonData.FONTSIZE, SWT.NORMAL));
		text.setBounds(widthStart, heightStart, width, height);
		text.setEnabled(enable);
		text.setText(initText);
		return text;
	}

	// 初始化btn
	public static Button initBtnControl(Group parentGroup, String text, int style, int widthStart,
			int heightStart, int width, int height) {
		Button btn = new Button(parentGroup, style);
		btn.setBounds(widthStart, heightStart, width, height);
		btn.setText(text);
		return btn;
	}
	// 初始化group
	public static Group initGroup(Composite parent, String text, int style, int widthStart,
			int heightStart, int width, int height) {
		Group group = new Group(parent, style);
		group.setText(text);
		group.setFont(SWTResourceManager.getFont(CommonData.FONTSTYLE, CommonData.FONTSIZE, SWT.NORMAL));
		group.setBounds(widthStart, heightStart, width, height);
		return group;
	}
	// 初始化Combo
	public static Combo initComboControl(Group parentGroup, List<String> listData, String selected, int widthStart,
			int heightStart, int width, int height) {
		if(null==selected){
			selected = "0";
		}
		Combo combo = new Combo(parentGroup, SWT.READ_ONLY);
		combo.setBounds(widthStart, heightStart, width, height);
		if(null!=listData){
			for(String data : listData){
				combo.add(data);
			}
			combo.select(Integer.parseInt(selected));
		}	
		
		return combo;
	}
	// 初始化工程列表
	public static List getProjectList(){
		List<String> list = null;
		try {
			TestProjectDAO dao = new TestProjectDAO();
			list = dao.getAllProject();
			list.add(0, "");
			if(null==list){
				list = new ArrayList<String>();
				list.add(Config.getDefaultProjectName()+"("+Config.getDefaultProjectPrefix()+")");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
}
