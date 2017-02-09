package cu.uci.abcd.administration.library.communFragment;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.api.ui.FragmentContributor;
import cu.uci.abos.core.ui.FormPagePainter;
import cu.uci.abos.core.ui.PagePainter;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class RegisterLibraryFragment implements FragmentContributor {

	private Label registerLibraryLabel;
	private Composite parent;
	private Label lblNewLab;
	private Label nameLibraryLabel;
	private Text nameLibraryText;
	private Label addressLibraryLabel;
	private Text addressLibraryText;
	private Label isisHomeLabel;
	private Text isisHomeText;
	private Library library;
	private PagePainter painter;
	private ValidatorUtils validator;
	private int dimension;
	private int option;

	public RegisterLibraryFragment(Library library, int dimension, int option) {
		this.library = library;
		this.dimension = dimension;
		this.option = option;
	}

	@Override
	public Control createUIControl(Composite shell) {
		painter = new FormPagePainter();
		validator = new ValidatorUtils(new CustomControlDecoration());
		painter.setDimension(dimension);
		painter.addComposite(shell);
		shell.setData(RWT.CUSTOM_VARIANT, "gray_background");

		parent = new Composite(shell, SWT.NORMAL);
		painter.addComposite(parent);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");

		registerLibraryLabel = new Label(parent, SWT.NONE);
		painter.addHeader(registerLibraryLabel);

		Label separator = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		painter.addSeparator(separator);

		lblNewLab = new Label(parent, SWT.NONE);
		painter.addHeader(lblNewLab);

		nameLibraryLabel = new Label(parent, SWT.NONE);
		painter.add(nameLibraryLabel);

		nameLibraryText = new Text(parent, SWT.NONE);
		painter.add(nameLibraryText);

		validator.applyValidator(nameLibraryText, "nameLibraryAlphaNumeric",
				DecoratorType.ALPHA_NUMERICS_SPACES, true, 50);

		validator.applyValidator(nameLibraryText, "nameLibraryRequired",
				DecoratorType.REQUIRED_FIELD, true);

		addressLibraryLabel = new Label(parent, SWT.NONE);
		painter.add(addressLibraryLabel);

		addressLibraryText = new Text(parent, SWT.NONE | SWT.WRAP
				| SWT.V_SCROLL);
		painter.add(addressLibraryText);

		validator.applyValidator(addressLibraryText, 250);

		validator.applyValidator(addressLibraryText,
				"addressLibraryTextRequired", DecoratorType.REQUIRED_FIELD,
				true);

		painter.reset();

		Integer dimension = ((FormPagePainter) painter).getDimension();
		if (dimension < 840) {
			painter.reset();
			painter.add(new Label(parent, SWT.NONE));
			painter.reset();
			painter.reset();
			isisHomeLabel = new Label(parent, SWT.NONE);
			painter.add(isisHomeLabel);

			isisHomeText = new Text(parent, SWT.NONE);
			painter.add(isisHomeText);
		} else {
			isisHomeLabel = new Label(parent, SWT.NONE);
			painter.add(isisHomeLabel);

			isisHomeText = new Text(parent, SWT.NONE);
			painter.add(isisHomeText);
		}

		validator.applyValidator(isisHomeText, "isisHomeTextIsisHome",
				DecoratorType.ALPHANUMERIC_AND_UNDERSCORE, true, 50);
		LoadLibraryData();
		l10n();
		return shell;
	}

	private void LoadLibraryData() {
		if (library != null) {
			nameLibraryText
					.setText((!(library.getLibraryName() == null)) ? library
							.getLibraryName() : "");
			addressLibraryText
					.setText((!(library.getAddress() == null)) ? library
							.getAddress() : "");
			isisHomeText
					.setText((!(library.getIsisDefHome() == null)) ? library
							.getIsisDefHome() : "");
		}
	}

	@Override
	public String getID() {
		return null;
	}

	@Override
	public void l10n() {
		if (option == 1) {
			registerLibraryLabel.setText(MessageUtil.unescape(AbosMessages
					.get().LABEL_REGISTER_LIBRARY));
		} else {
			registerLibraryLabel.setText(MessageUtil.unescape(AbosMessages
					.get().LABEL_UPDATE_LIBRARY));
		}
		lblNewLab
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_GENERAL_DATA));
		nameLibraryLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_NAME));
		addressLibraryLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_ADDREES));
		isisHomeLabel
				.setText(MessageUtil.unescape(AbosMessages.get().ISIS_HOME));
		parent.getParent().layout(true, true);
		parent.getParent().redraw();
		parent.getParent().update();
	}

	@Override
	public Control getControl(String arg0) {
		return null;
	}

	public Text getNameLibraryText() {
		return nameLibraryText;
	}

	public void setNameLibraryText(Text nameLibraryText) {
		this.nameLibraryText = nameLibraryText;
	}

	public Text getAddressLibraryText() {
		return addressLibraryText;
	}

	public void setAddressLibraryText(Text addressLibraryText) {
		this.addressLibraryText = addressLibraryText;
	}

	public Text getIsisHomeText() {
		return isisHomeText;
	}

	public void setIsisHomeText(Text isisHomeText) {
		this.isisHomeText = isisHomeText;
	}

	public Composite getParent() {
		return parent;
	}

	public ValidatorUtils getValidator() {
		return validator;
	}

	public void setValidator(ValidatorUtils validator) {
		this.validator = validator;
	}

}
