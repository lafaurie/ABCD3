package cu.uci.abcd.opac.contribution;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import cu.uci.abos.ui.api.IPlatformContributor;

public class OpacBackgroundProvider implements IPlatformContributor {
	public static final String BACKGROUND_CONTROL = OpacBackgroundProvider.class
			.getName() + "#Background";

	@Override
	public Control createUIControl(Composite parent) {
		Label result = new Label(parent, SWT.NONE);
		result.setData(RWT.CUSTOM_VARIANT, "content-background");
		result.moveBelow(null);
		return result;
	}

	@Override
	public String getID() {
		return BACKGROUND_CONTROL;
	}

	@Override
	public void l10n() {

	}
}