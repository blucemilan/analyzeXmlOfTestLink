package com.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;

public class BackgroundUtils {
	
	/**
	 * 设置白色背景色
	 * @param control
	 */
	public static void setWhiteBackgroundColor(Composite composite){
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite.setBackgroundMode(SWT.INHERIT_FORCE);
	}
	
	/**
	 * 设置灰色背景色
	 * @param control
	 */
	public static void setGrayBackgroundColor(Composite composite){
		composite.setBackground(SWTResourceManager.getColor(new RGB(240, 240, 240)));
		composite.setBackgroundMode(SWT.INHERIT_FORCE);
	}
	
	/**
	 * 设置浅灰色背景色
	 * @param control
	 */
	public static void setShallowGrayBackgroundColor(Composite composite){
		composite.setBackground(SWTResourceManager.getColor(new RGB(247, 247, 247)));
		composite.setBackgroundMode(SWT.INHERIT_FORCE);
	}
}
