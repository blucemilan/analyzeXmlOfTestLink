package testlink;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.view.TestCaseView;

public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(false);
		layout.addView(TestCaseView.ID, IPageLayout.RIGHT, 0.75F, IPageLayout.ID_EDITOR_AREA);
		layout.setFixed(true);
	}

}
