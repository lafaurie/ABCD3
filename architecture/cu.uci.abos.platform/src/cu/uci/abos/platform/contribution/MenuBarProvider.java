package cu.uci.abos.platform.contribution;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.PlatformContributor;
import cu.uci.abos.core.util.FormDatas;

public class MenuBarProvider implements PlatformContributor {
	public static final String MENU_BAR_CONTROL = MenuBarProvider.class.getName() + "#MENUBAR";
	// NOTE: this value reflects the height of the menubar_background image set
	// via css
	public static final int MENU_BAR_HEIGHT = 40;
	private static final String MENUBAR_BACKGROUND = "menubar_background";
//FIXME OIGRES RAFA POR AQUI MENU
	Button createMenuButton(Composite parent, final ContributorService pageService, final String pageId) {

		parent.setLayout(new FormLayout());

		FormData data1 = new FormData(parent.getBounds().width, 25);
		data1.left = new FormAttachment(parent, 7);
		data1.top = new FormAttachment(parent, 7);

		Button result = new Button(parent, SWT.PUSH);

		result.setText("");
		result.setLayoutData(data1);
		result.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent evt) {
				pageService.selectContributor(pageId);
			}
		});

		FormData separator1 = new FormData();
		separator1.height = 37;
		separator1.left = new FormAttachment(result, 5);
		separator1.top = new FormAttachment(parent, 2);
		Label label1 = new Label(parent, SWT.SEPARATOR | SWT.SHADOW_OUT);
		label1.setLayoutData(separator1);
		pageService.selectContributor(pageId);
		return result;
	}

	@Override
	public Control createUIControl(Composite parent) {
		final Composite result = new Composite(parent, SWT.None);
		result.layout(true, true);
		result.setData(RWT.CUSTOM_VARIANT, MENUBAR_BACKGROUND);
		result.setLayout(new FormLayout());

		Text text = new Text(result, SWT.SEARCH | SWT.ICON_SEARCH | SWT.ICON_CANCEL);

       FormDatas.attach(text).atRight(25).atTopTo(result,5).withWidth(270);

		return result;
	}

	@Override
	public String getID() {
		return MENU_BAR_CONTROL;
	}

	@Override
	public void l10n() {}

}