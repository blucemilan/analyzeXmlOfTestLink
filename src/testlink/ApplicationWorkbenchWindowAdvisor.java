package testlink;

import org.eclipse.swt.SWT;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

    public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        super(configurer);
    }

    public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
        return new ApplicationActionBarAdvisor(configurer);
    }
    
    public void preWindowOpen() {
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        //获取屏幕大小
        //Rectangle screenSize = Display.getDefault().getClientArea();
        //初始化窗口大小
        //configurer.setInitialSize(new Point(screenSize.width, screenSize.height));
        configurer.setShowCoolBar(false);
        configurer.setShowStatusLine(false);
        //设置窗口只能最小化或关闭
        configurer.setShellStyle(SWT.MIN | SWT.CLOSE);
        configurer.setTitle("TestLink导出工具");
        postWindowCreate();
    }
    public void postWindowCreate() {
        super.postWindowCreate();
        //设置打开时最大化窗口
        getWindowConfigurer().getWindow().getShell().setMaximized(true);
    }
}
