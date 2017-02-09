package cu.uci.abos.core.widget.wizard;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public interface IStep {

	Control createUI(Composite parent);

	boolean isValid();

	void l10n();
}
