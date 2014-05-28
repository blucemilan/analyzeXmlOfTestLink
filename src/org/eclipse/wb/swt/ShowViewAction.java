package org.eclipse.wb.swt;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.views.IViewDescriptor;

public class ShowViewAction extends Action implements IWorkbenchAction {

	private IWorkbenchWindow workbenchWindow;

	private IViewDescriptor desc;

	public ShowViewAction(IWorkbenchWindow window, IViewDescriptor desc) {
		super("");
		/** 获得视图的名称 */
		String label = desc.getLabel();
		/** 设置操作的名称 */
		setText(label);
		/** 设置操作的图标为视图的图标 */
		setImageDescriptor(desc.getImageDescriptor());
		/** 设置操作的提示文本 */
		setToolTipText(label);
		setId("ShowView" + desc.getId());
		this.workbenchWindow = window;
		this.desc = desc;
	}

	public void run() {
		/** 获得当前工作区及获得工作页面 */
		IWorkbenchPage page = workbenchWindow.getActivePage();
		/** 如果页面不为null */
		if (page != null) {
			try {
				/** 显示视图 */
				page.showView(desc.getId());
			} catch (PartInitException e) {
				ErrorDialog.openError(workbenchWindow.getShell(), "打开视图错误！", e.getMessage(), e.getStatus());
			}
		}
	}

	public void dispose() {
		workbenchWindow = null;

	}

}
