package cu.uci.abcd.management.db.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import cu.uci.abos.core.widget.wizard.BaseStep;
import cu.uci.abos.core.widget.wizard.IStep;
import cu.uci.abos.core.widget.wizard.Wizard;


public class PFTStep extends BaseStep implements IStep {

	public PFTStep(Wizard wizard) {
		super(wizard);
	}

	@Override
	public Control createUI(Composite parent) {
		
		return parent;
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public void l10n() {
	}

}
