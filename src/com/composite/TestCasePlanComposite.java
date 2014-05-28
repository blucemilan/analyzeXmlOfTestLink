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
public class TestCasePlanComposite extends Composite {
	private int height = 20;
	// 工程名称
	private Combo combo_projectName;
	private Label label_projectName;
	// 测试计划
	private Combo combo_testPlan;
	private Label label_testPlan;
	// platForm
	private Combo combo_platForm;
	private Label label_platForm;
	// build
	private Combo combo_build;
	private Label label_build;
	// 模版路径
	private Text text_modelPath;
	private Label label_modelPath;
	//按钮
	private Button button_export;
	private Button button_selectModelFile;
	//外框
	private Group group_export;
	
	public void initData(){	
	}
	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public TestCasePlanComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(null);
		initData();
		group_export = RcpUtils.initGroup(this, "TestLink", SWT.NONE, 10, 10, 1000, 700);
//		工程名称
		label_projectName = RcpUtils.initLabelControl(group_export, "工程名称", 30, height, 140, 17);
		combo_projectName = RcpUtils.initComboControl(group_export, RcpUtils.projectList, null, 190, height, 600, 23);
		addHeight();
//		测试计划
		label_testPlan = RcpUtils.initLabelControl(group_export, "测试计划", 30, height, 140, 17);
		combo_testPlan =RcpUtils.initComboControl(group_export, null , null, 190, height, 600, 23);
		addHeight();
//		build
		label_build = RcpUtils.initLabelControl(group_export, "build", 30, height, 140, 17);
		combo_build =RcpUtils.initComboControl(group_export, null, null, 190, height, 600, 23);
		addHeight();
//		platForm
		label_platForm = RcpUtils.initLabelControl(group_export, "platForm", 30, height, 140, 17);
		combo_platForm =RcpUtils.initComboControl(group_export, null, null, 190, height, 600, 23);
		addHeight();
//		模版路径		
		label_modelPath = RcpUtils.initLabelControl(group_export, "模版路径", 30, height, 140, 17);
		text_modelPath = RcpUtils.initTextControl(group_export, RcpUtils.getDefaultPlanModelFilePath(), 
				SWT.BORDER, false, 190, height, 600, 23);
		button_selectModelFile = RcpUtils.initBtnControl(group_export, "选择文件", SWT.NONE, 800, height, 61, 25);	
		addHeight();
//		按钮
		button_export = RcpUtils.initBtnControl(group_export, "导出", SWT.NONE, 500, height, 61, 25);
	}
	

	public Combo getCombo_projectName() {
		return combo_projectName;
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

	public Combo getCombo_testPlan() {
		return combo_testPlan;
	}
	public Combo getCombo_platForm() {
		return combo_platForm;
	}
	public Combo getCombo_build() {
		return combo_build;
	}
	public Button getButton_selectModelFile() {
		return button_selectModelFile;
	}
	
}
