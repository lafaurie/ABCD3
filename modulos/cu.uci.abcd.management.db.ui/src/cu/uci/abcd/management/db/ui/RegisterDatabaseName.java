package cu.uci.abcd.management.db.ui;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.management.db.l10n.AbosMessages;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.FragmentContributor;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.ui.FormPagePainter;
import cu.uci.abos.core.ui.PagePainter;
import cu.uci.abos.core.util.MessageUtil;

public class RegisterDatabaseName implements FragmentContributor{

	Label databaseLabel;
	Text databaseNameText;
	
	public Text getDatabaseNameText() {
		return databaseNameText;
	}

	Label buttonSeparator;
	Label databaseNameLabel;
	PagePainter painter;
	Composite up;
	
	@Override
	public Control createUIControl(Composite parent) {
		
		painter = new FormPagePainter();
		up = new Composite(parent, SWT.NONE);
		painter.addComposite(up);
		up.setData(RWT.CUSTOM_VARIANT, "gray_background");
		
		databaseLabel = new Label(up, SWT.NONE);
		painter.addHeader(databaseLabel);
		
		buttonSeparator = new Label(up, SWT.HORIZONTAL | SWT.SEPARATOR);
		painter.addSeparator(buttonSeparator);
		
		databaseNameLabel = new Label(up, SWT.NONE);
		painter.add(databaseNameLabel);
		
		
		databaseNameText = new Text(up, SWT.NONE);
		painter.add(databaseNameText);
		l10n();
		return up;
	}

	@Override
	public void l10n() {
		databaseLabel.setText(MessageUtil.escape((AbosMessages.get().ENTER_DATABASE_NAME_LABEL)));
		databaseNameLabel.setText(MessageUtil.escape((AbosMessages.get().NEW_DATABASE_NAME_LABEL)));
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Control getControl(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}


}
