package com.composite;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.utils.RcpUtils;

@SuppressWarnings({"unused", "unchecked", "rawtypes"})
public class TestCaseComposite extends Composite {
	private int height = 20;
	// 工程名称
	private Combo combo_projectName;
	private Label label_projectName;
	// xml路径
	private Text text_xmlPath;
	private Label label_xmlPath;
	// 模版路径
	private Text text_modelPath;
	private Label label_modelPath;
	// 导出类型路径
	private Combo combo_exportType;
	private Label label_exportType;
	//按钮
	private Button button_export;
	private Button button_selectXmlFile;
	private Button button_selectModelFile;
	//外框
	private Group group_export;
	private List exportTypeList = new ArrayList();
	
	public void initData(){
		exportTypeList.add(RcpUtils.CASEBYEXCEL);
		exportTypeList.add(RcpUtils.CASEBYWORD);	
	}
	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public TestCaseComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(null);
		initData();
		group_export = RcpUtils.initGroup(this, "TestLink", SWT.NONE, 10, 10, 1000, 700);
//		工程名称
		label_projectName = RcpUtils.initLabelControl(group_export, "工程名称", 30, height, 140, 17);
		combo_projectName =RcpUtils.initComboControl(group_export, RcpUtils.projectList, null, 190, height, 600, 23);
		addHeight();
//		xml路径		
		label_xmlPath = RcpUtils.initLabelControl(group_export, "xml路径", 30, height, 140, 17);
		text_xmlPath = RcpUtils.initTextControl(group_export, "", SWT.BORDER, false, 190, height, 600, 23);
		button_selectXmlFile = RcpUtils.initBtnControl(group_export, "选择文件", SWT.NONE, 800, height, 61, 25);
		addHeight();
//		模版路径		
		label_modelPath = RcpUtils.initLabelControl(group_export, "模版路径", 30, height, 140, 17);
		text_modelPath = RcpUtils.initTextControl(group_export, RcpUtils.getDefaultExcelModelFilePath(), 
				SWT.BORDER, false, 190, height, 600, 23);
		button_selectModelFile = RcpUtils.initBtnControl(group_export, "选择文件", SWT.NONE, 800, height, 61, 25);
		addHeight();
//		导出类型
		label_exportType = RcpUtils.initLabelControl(group_export, "导出类型", 30, height, 140, 17);
		combo_exportType = RcpUtils.initComboControl(group_export, exportTypeList, null, 190, height, 600, 23);		
		addHeight();
//		按钮
		button_export = RcpUtils.initBtnControl(group_export, "导出", SWT.NONE, 500, height, 61, 25);
	}
	

	public Combo getCombo_projectName() {
		return combo_projectName;
	}
	public Text getText_xmlPath() {
		return text_xmlPath;
	}
	public Text getText_modelPath() {
		return text_modelPath;
	}
	private void addHeight(){
		height += 30;
	}
	
	private void addHeight(int increment){
		height += increment;
	}
	public Button getButton_export() {
		return button_export;
	}
	public Button getButton_selectXmlFile() {
		return button_selectXmlFile;
	}
	public Button getButton_selectModelFile() {
		return button_selectModelFile;
	}
	public Combo getCombo_exportType() {
		return combo_exportType;
	}	
	
}
