package com.view;

import java.io.File;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.part.ViewPart;

import com.composite.TestCasePlanComposite;
import com.sky.bo.TestPlan;
import com.sky.dao.TestPlanDAO;
import com.sky.excel.CreatePlanExcel;
import com.utils.BackgroundUtils;
import com.utils.RcpUtils;
import com.utils.Utils;

public class TestCasePlanView extends ViewPart {
	public static final String ID = "testlink.testPlanView";
	public TestCasePlanComposite testCasePlanComposite;
	public TestPlanDAO dao = new TestPlanDAO();
	private Composite container;
	public TestCasePlanView() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		container = new Composite(parent, SWT.NONE);
		BackgroundUtils.setShallowGrayBackgroundColor(container);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));
		{
			ViewForm viewForm = new ViewForm(container, SWT.NONE);
			{
				testCasePlanComposite = new TestCasePlanComposite(viewForm, SWT.NONE);
				viewForm.setTopLeft(testCasePlanComposite);
			}
		}
		createActions();
	}
	/**
	 * Create the actions.
	 */
	private void createActions() {		
		// 选择工程名称的联动
		testCasePlanComposite.getCombo_projectName().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {			
				resetCombo();
				String projectId = Utils.getProjectId(testCasePlanComposite.getCombo_projectName().getText());
				setCombo(testCasePlanComposite.getCombo_testPlan(), dao.getTestPlan(projectId));
			}
		});
		// 选择测试计划的联动
		testCasePlanComposite.getCombo_testPlan().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {			
				resetPlanCombo();
				String planId = Utils.getBaseId(testCasePlanComposite.getCombo_testPlan().getText());
				setCombo(testCasePlanComposite.getCombo_platForm(), dao.getPlatForm(planId), 0);
				setCombo(testCasePlanComposite.getCombo_build(), dao.getBuildId(planId), 0);
			}
		});
		// 选择模版文件
		testCasePlanComposite.getButton_selectModelFile().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(container.getShell(), SWT.OPEN);
				fileDialog.setText("选择模版文件");
				//fileDialog.setFilterNames(new String[] {"Word2007", "Excel2007" });
				fileDialog.setFilterExtensions(new String[] {"*.xlsx" });
				if (null != fileDialog.open()) {
					String fileName = fileDialog.getFilterPath() + File.separator + fileDialog.getFileName();
					testCasePlanComposite.getText_modelPath().setText(fileName);
				}
			}
		});
		// 导出
		testCasePlanComposite.getButton_export().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String project = testCasePlanComposite.getCombo_projectName().getText();
				if(RcpUtils.checkIsNull(project)){
					String projectId = Utils.getProjectId(project);
					String projectName = Utils.getProjectName(project);
					String planId = Utils.getBaseId(testCasePlanComposite.getCombo_testPlan().getText());	
					String platId = testCasePlanComposite.getCombo_platForm().getText();
					if(RcpUtils.checkIsNull(platId)){
						platId = Utils.getBaseId(testCasePlanComposite.getCombo_platForm().getText());
					}
					String build = Utils.getBaseId(testCasePlanComposite.getCombo_build().getText());
					String modelPath = testCasePlanComposite.getText_modelPath().getText();
					if(RcpUtils.checkIsNull(planId)){
						FileDialog fileDialog = new FileDialog(container.getShell(), SWT.SAVE);
						fileDialog.setText("导出");
						fileDialog.setFileName(projectName);
						fileDialog.setFilterExtensions(new String[] {"*.xlsx"});
						if (null != fileDialog.open()) {
							MessageDialog.openInformation(null, "通知", "正在导出，请耐心等待！");
							String fileName = fileDialog.getFilterPath() + File.separator + fileDialog.getFileName();
							List<TestPlan> list = dao.getPlanById(Utils.getPrefix(project), planId, build, platId);
							CreatePlanExcel plan = new CreatePlanExcel(modelPath, projectName , fileName);					
							if(plan.createExcel(list)){
								MessageDialog.openInformation(null, "通知", "导出成功！");
							}else{
								MessageDialog.openInformation(null, "通知", "导出失败，请联系管理员！");
							}
						}
						
					}else{
						MessageDialog.openInformation(null, "通知", "测试计划不能为空！");
					}
				}else{
					MessageDialog.openInformation(null, "通知", "工程名称不能为空！");
				}
				
				
			}
		});
	}

	public void setCombo(Combo combo, List<String> list){
		for(String str : list){
			combo.add(str);
		}
	}
	
	public void setCombo(Combo combo, List<String> list, int selected){
		for(String str : list){
			combo.add(str);
		}
		combo.select(selected);
	}
	
	public void resetCombo(){
		testCasePlanComposite.getCombo_build().removeAll();
		testCasePlanComposite.getCombo_platForm().removeAll();
		testCasePlanComposite.getCombo_testPlan().removeAll();
	}
	public void resetPlanCombo(){
		testCasePlanComposite.getCombo_build().removeAll();
		testCasePlanComposite.getCombo_platForm().removeAll();
	}
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		//viewer.getControl().setFocus();
	}
}