package org.eclipse.wb.swt;

import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.views.IViewDescriptor;

public class ActionManager {

//	/** 新建客户操作 */
//	public static IWorkbenchAction createNewCustomerAction(IWorkbenchWindow window) {
//		if (window == null) {
//			throw new IllegalArgumentException();
//		}
//		IWorkbenchAction action = new NewCustomerAction(window);
//		return action;
//	}                                                                                       
//
//	/** 新建联系人操作 */
//	public static IWorkbenchAction createNewContactAction(IWorkbenchWindow window) {
//		if (window == null) {
//			throw new IllegalArgumentException();
//		}
//		IWorkbenchAction action = new NewContactAction(window);
//		return action;
//	}
	/**打开视图操作 */
	public static IWorkbenchAction createShowViewAction(IWorkbenchWindow window, String viewId) {
		if (window == null) {
			throw new IllegalArgumentException();
		}
		/**获得plugin.xml文件中配置的视图信息*/
		IViewDescriptor desc = window.getWorkbench().getViewRegistry().find(viewId);
		IWorkbenchAction action = new ShowViewAction(window, desc);
		return action;
	}
}
