package com.view;

import java.io.File;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.part.ViewPart;

import com.composite.TestCaseComposite;
import com.sky.bo.TestSuite;
import com.sky.excel.CreateExcel;
import com.sky.testlinkconvert.XmlToElement;
import com.sky.word.CreateWord;
import com.utils.BackgroundUtils;
import com.utils.ProgressMonitorAction;
import com.utils.RcpUtils;
import com.utils.Utils;

public class TestCaseView extends ViewPart {
	public static final String ID = "testlink.testCaseView";
	public TestCaseComposite testCaseComposite;
	private Composite container;
	public TestCaseView() {
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
				testCaseComposite = new TestCaseComposite(viewForm, SWT.NONE);
				viewForm.setTopLeft(testCaseComposite);
			}
		}
		createActions();
	}
	/**
	 * Create the actions.
	 */
	private void createActions() {
		// 选择导出类型的联动
		testCaseComposite.getCombo_exportType().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String value = RcpUtils.modelPathByTypeMap.get(testCaseComposite.getCombo_exportType().getText());
				testCaseComposite.getText_modelPath().setText(value);
			}
		});
		// 选择xml文件
		testCaseComposite.getButton_selectXmlFile().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(container.getShell(), SWT.OPEN);
				fileDialog.setText("选择xml文件");
				fileDialog.setFilterNames(new String[] {"xml" });
				fileDialog.setFilterExtensions(new String[] { "*.xml"});
				if (null != fileDialog.open()) {
					String fileName = fileDialog.getFilterPath() + File.separator + fileDialog.getFileName();
					testCaseComposite.getText_xmlPath().setText(fileName);
				}

			}
		});
		// 选择模版文件
		testCaseComposite.getButton_selectModelFile().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(container.getShell(), SWT.OPEN);
				fileDialog.setText("选择模版文件");
				//fileDialog.setFilterNames(new String[] {"Word2007", "Excel2007" });
				fileDialog.setFilterExtensions(new String[] {"*.docx", "*.xlsx" });
				if (null != fileDialog.open()) {
					String fileName = fileDialog.getFilterPath() + File.separator + fileDialog.getFileName();
					testCaseComposite.getText_modelPath().setText(fileName);
				}
			}
		});
		// 导出
		testCaseComposite.getButton_export().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String projectName = testCaseComposite.getCombo_projectName().getText();
				String xmlPath = testCaseComposite.getText_xmlPath().getText();
				String modelPath = testCaseComposite.getText_modelPath().getText();
				String type = testCaseComposite.getCombo_exportType().getText();
				if(RcpUtils.checkIsNull(projectName, xmlPath)){
					if(RcpUtils.CASEBYEXCEL.equals(type)){
						exportCaseExcel(projectName, xmlPath, modelPath);
					}else if(RcpUtils.CASEBYWORD.equals(type)){
						exportCaseWord(projectName, xmlPath, modelPath);
					}				
				}else{
					MessageDialog.openWarning(null, "警告", "工程名称、xml路径不能为空");
				}
				
			}
		});
	}
	//导出excel格式的用例信息
	public void exportCaseExcel(String projectName, String xmlPath, String modelPath){
		FileDialog fileDialog = new FileDialog(container.getShell(), SWT.SAVE);
		fileDialog.setText("导出");
		fileDialog.setFileName(Utils.getProjectName(projectName));
		fileDialog.setFilterExtensions(new String[] {"*.xlsx"});
		if (null != fileDialog.open()) {
			String fileName = fileDialog.getFilterPath() + File.separator + fileDialog.getFileName();
			List<TestSuite> dataList = XmlToElement.transferXMLToElement(xmlPath, Utils.getPrefix(projectName));
			CreateExcel excel = new CreateExcel(modelPath, Utils.getProjectName(projectName), fileName);
			if (ProgressMonitorAction.waitForComplete(excel, dataList)) {
				MessageDialog.openInformation(null, "通知", "导出成功！");
			} else {
				MessageDialog.openInformation(null, "通知", "导出失败，请联系管理员！");
			}		
		}
	}
	//导出word格式的用例信息
	public void exportCaseWord(String projectName, String xmlPath, String modelPath){
		FileDialog fileDialog = new FileDialog(container.getShell(), SWT.SAVE);
		fileDialog.setText("导出");
		fileDialog.setFileName(Utils.getProjectName(projectName));
		fileDialog.setFilterExtensions(new String[] {"*.docx"});
		if (null != fileDialog.open()) {
			//MessageDialog.openInformation(null, "通知", "正在导出，请耐心等待！");
			String fileName = fileDialog.getFilterPath() + File.separator + fileDialog.getFileName();
			List<TestSuite> dataList = XmlToElement.transferXMLToElement(xmlPath, Utils.getPrefix(projectName));
			CreateWord word = new CreateWord(modelPath, Utils.getProjectName(projectName), fileName);
			if (ProgressMonitorAction.waitForComplete(word, dataList)) {
				MessageDialog.openInformation(null, "通知", "导出成功！");
			} else {
				MessageDialog.openInformation(null, "通知", "导出失败，请联系管理员！");
			}
		}
		
	}
	
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		//viewer.getControl().setFocus();
	}
}