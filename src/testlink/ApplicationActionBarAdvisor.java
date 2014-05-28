package testlink;

import java.util.Arrays;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.wb.swt.ActionManager;

import com.view.TestCasePlanView;
import com.view.TestCaseView;

/**
 * An action bar advisor is responsible for creating, adding, and disposing of
 * the actions added to a workbench window. Each window will be populated with
 * new actions.
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {
	private IWorkbenchAction iExitAction;
	private IAction caseAction;
	private IAction planAction;
	private MenuManager mainMenu;
	
	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	protected void makeActions(IWorkbenchWindow window) {
		caseAction = new Action() {
			public void run() {
				ActionManager.createShowViewAction(Activator.getDefault().getWorkbench().getActiveWorkbenchWindow(), TestCaseView.ID).run();
			}
		};
		caseAction.setText("测试用例导出");
		iExitAction = ActionFactory.QUIT.create(window);
		register(iExitAction);
	}

	protected void fillMenuBar(IMenuManager menuBar) {
		
		mainMenu = createMenuManager(mainMenu, "测试用例导出", "mainMenu");
		//mainMenu = createMenuManager(mainMenu, "用例执行结果导出", "mainMenu");
		addContributionItem(menuBar, mainMenu);
		mainMenu.add(caseAction);
		//mainMenu.add(planAction);
		mainMenu.add(iExitAction);
	}
	
	private MenuManager createMenuManager(MenuManager menuManager, String text, String id) {
		if (menuManager == null) {
			menuManager = new MenuManager(text, id);
		}
		return menuManager;
	}

	private void addContributionItem(IMenuManager menuManager, IContributionItem item) {
		if (!Arrays.asList(menuManager.getItems()).contains(item)) {
			menuManager.add(item);
		}
	}

}
