package cu.uci.abos.core.ui;

import java.util.Collection;

import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import cu.uci.abos.core.ui.internal.FormPagePainter;
import cu.uci.abos.l10n.platform.AbosMessages;
import cu.uci.abos.l10n.util.MessageUtil;
import cu.uci.abos.ui.api.IContributor;
import cu.uci.abos.ui.api.IViewController;
import cu.uci.abos.util.api.MessageDialogUtil;
import cu.uci.abos.validation.ui.CustomControlDecoration;
import cu.uci.abos.validation.ui.DecoratedControl;
import cu.uci.abos.validation.ui.DecoratorType;

/**
 * 
 * @author sergio
 * 
 */
public abstract class ContributorPage implements IContributor {

	private PagePainter painter =new FormPagePainter();
	private CustomControlDecoration decorationFactory = new CustomControlDecoration();

	@Override
	public String getID() {
		return this.getClass().getName() + "ID";
	}

	@Override
	public boolean canClose() {
		return true;
	}

	@Override
	public void setViewController(IViewController controller) {
	}

	public void addComposite(Composite composite, Percent percent) {
		painter.addComposite(composite, percent);
	}

	public void addComposite(Composite composite) {
		painter.addComposite(composite);
	}

	public void addHeader(Label header) {
		painter.addHeader(header);
	}

	public void addSeparator(Label separator) {
		painter.addSeparator(separator);
	}

	public void add(Control control) {
		painter.add(control);
	}

	public void add(Control control, Percent percent) {
		painter.add(control, percent);
	}

	public void showErrorMessage(String message) {
		MessageDialogUtil.openError(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.l10n.platform.AbosMessages.get().MESSAGE_ERROR), MessageUtil.unescape(message), null);
	}

	public void showInformationMessage(String message) {
		MessageDialogUtil.openInformation(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.l10n.platform.AbosMessages.get().MESSAGE_INFORMATION), MessageUtil.unescape(message), null);
	}

	public void showWarningMessage(String message) {
		// FIXME OIGRES falta el tipo de mensaje
		MessageDialogUtil.openWarning(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.l10n.platform.AbosMessages.get().MESSAGE_ERROR), MessageUtil.unescape(message), null);
	}

	public void showQuestionMessage(String message, DialogCallback callback) {
		// FIXME OIGRES falta el tipo de mensaje
		MessageDialogUtil.openWarning(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.l10n.platform.AbosMessages.get().MESSAGE_ERROR), MessageUtil.unescape(message), null);
	}

	public void showConfirmationMessage(String message, DialogCallback callback) {
		// FIXME OIGRES falta el tipo de mensaje
		MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.l10n.platform.AbosMessages.get().MESSAGE_ERROR), MessageUtil.unescape(message), null);
	}

	public void applyValidator(Control control, String key, DecoratorType type, boolean visible) {
		String message = "Error";
		DecoratedControl[] decorated;
		switch (type) {
		case ALPHA_NUMERIC:
			message = MessageUtil.unescape(AbosMessages.get().MESSAGE_VALIDATE_FIELD_ONLY_LETTERS_OR_NUMBERS);
			break;

		case REQUIRED_FIELD:
			message = MessageUtil.unescape(AbosMessages.get().MESSAGE_VALIDATE_FIELD_REQUIRED);
			break;
		case CONTENT_PROPOSAL:
			// FIXME OIGRES FAlTA ESTE MENSAJE
			message = MessageUtil.unescape(AbosMessages.get().MESSAGE_VALIDATE_FIELD_REQUIRED);
			break;
		case LETTERS_ONLY:
			message = MessageUtil.unescape(AbosMessages.get().MESSAGE_VALIDATE_FIELD_ONLY_LETTERS);
			break;

		case DATE_RANGE:
			// FIXME OIGRES FAlTA ESTE MENSAJE
			message = MessageUtil.unescape(AbosMessages.get().MESSAGE_VALIDATE_FIELD_REQUIRED);
			break;
		case EMAIL:
			message = MessageUtil.unescape(AbosMessages.get().MESSAGE_VALIDATE_FIELD_EMAIL);
			break;
		case NUMBER_ONLY:
			message = MessageUtil.unescape(AbosMessages.get().MESSAGE_VALIDATE_FIELD_ONLY_NUMBERS);
			break;

		default:
			break;
		}
		decorated = new DecoratedControl[] { new DecoratedControl(control, key, message) };
		decorationFactory.createDecorator(decorated, type, 0, SWT.RIGHT);
	}

	public void initialize(Control control, Collection<?> items) {
		if (control.getClass().isAssignableFrom(Combo.class)) {
			String[] values=new String[items.size()];
			int i=0;
			for (Object object : items) {
				values[i++]=object.toString();
			}
			((Combo) control).setItems((String[]) items.toArray());
			control.setData(items);
		}
	}

	public void refresh() {
		Display.getCurrent().getActiveShell().layout(true, true);
	}

	public void refresh(Control control) {
		control.getParent().layout(true, true);
	}

	public void resetPainter() {
		painter.reset();
	}

}
