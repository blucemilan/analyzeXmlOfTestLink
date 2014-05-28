package com.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;

import com.sky.bo.TestSuite;
import com.sky.excel.CreateExcel;
import com.sky.word.CreateWord;

public class ProgressMonitorAction {
	public static boolean waitForComplete(final CreateWord word, final List<TestSuite> dataList) {
		ProgressMonitorDialog progress = new ProgressMonitorDialog(null);
		progress.setCancelable(true);
		boolean flag = false;
		try {
			progress.run(true, true, new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor)
						throws InvocationTargetException {
					doSomething(monitor, word, dataList);
				}
			});
			flag = true;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			flag = false;
		} catch (InterruptedException e) {
			flag = false;
		}finally{
			return flag;
		}
	}
	public static void doSomething(IProgressMonitor monitor,
			final CreateWord word, final List<TestSuite> dataList) {
		monitor.beginTask("开始导出, 请耐心等待..........", IProgressMonitor.UNKNOWN);
		monitor.subTask("正在导出word文件！");		
		if(monitor.isCanceled()) return ;
		// TODO 相应的业务逻辑
		word.createWord(dataList);
		monitor.done();	
	}
	
	public static boolean waitForComplete(final CreateExcel excel, final List<TestSuite> dataList) {
		ProgressMonitorDialog progress = new ProgressMonitorDialog(null);
		progress.setCancelable(true);
		boolean flag = false;
		try {
			progress.run(true, true, new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor)
						throws InvocationTargetException {
					doSomething(monitor, excel, dataList);
				}
			});
			flag = true;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			flag = false;
		} catch (InterruptedException e) {
			flag = false;
		}finally{
			return flag;
		}
	}
	public static void doSomething(IProgressMonitor monitor,
			final CreateExcel excel, final List<TestSuite> dataList) {
		monitor.beginTask("开始导出, 请耐心等待...", IProgressMonitor.UNKNOWN);
		monitor.subTask("正在导出excel文件！");		
		if(monitor.isCanceled()) return ;
		// TODO 相应的业务逻辑
		excel.createExcel(dataList);
		monitor.done();	
	}
	
}
