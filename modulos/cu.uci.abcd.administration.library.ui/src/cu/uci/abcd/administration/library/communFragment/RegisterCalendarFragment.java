package cu.uci.abcd.administration.library.communFragment;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abos.api.ui.FragmentContributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.FormPagePainter;
import cu.uci.abos.core.ui.PagePainter;
import cu.uci.abos.core.ui.Percent;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class RegisterCalendarFragment implements FragmentContributor {
	private Label registerCalendarLabel;
	private DateTime dayDateTime;
	private DateTime dayToDateTime;
	private Label titleLabel;
	private Text titleText;
	private Label descriptionLabel;
	private Text descriptionText;
	private ViewController controller;
	private Map<String, Control> controls = new HashMap<String, Control>();
	private PagePainter painter;
	private ValidatorUtils validator;
	private int dimension;
	private Label dayLabel;
	private Label dayToLabel;
	private int option;
	private Button rangeButton;
	private Label holidayData;

	public RegisterCalendarFragment(ViewController controller, int dimension,
			int option) {
		this.setController(controller);
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
		registerCalendarLabel = new Label(shell, SWT.NORMAL);
		painter.addHeader(registerCalendarLabel);
		Label separator = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		painter.addSeparator(separator);
		holidayData = new Label(shell, SWT.NORMAL);
		painter.addHeader(holidayData);
		painter.add(new Label(shell, SWT.NONE), Percent.W25);
		rangeButton = new Button(shell, SWT.CHECK);
		rangeButton.setText("Rango");
		painter.add(rangeButton);
		controls.put("rangeButton", rangeButton);
		rangeButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (rangeButton.getSelection()) {
					dayLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_FROM));
					dayToLabel.setVisible(true);
					dayToDateTime.setVisible(true);
				} else {
					dayLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_DAY));
					dayToLabel.setVisible(false);
					dayToDateTime.setVisible(false);
				}
			}
		});
		painter.reset();
		dayLabel = new Label(shell, SWT.NORMAL);
		painter.add(dayLabel);
		dayDateTime = new DateTime(shell, SWT.BORDER | SWT.DROP_DOWN);
		controls.put("dayDateTime", dayDateTime);
		painter.add(dayDateTime);
		dayDateTime.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -5179607102055540891L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				dayDateTime.setBackground(null);
				dayToDateTime.setBackground(null);
			}

		});
		dayToLabel = new Label(shell, SWT.NORMAL);
		painter.add(dayToLabel);
		dayToLabel.setVisible(false);
		controls.put("dayToLabel", dayToLabel);
		dayToDateTime = new DateTime(shell, SWT.BORDER | SWT.DROP_DOWN);
		controls.put("dayToDateTime", dayToDateTime);
		painter.add(dayToDateTime);
		dayToDateTime.setVisible(false);
		dayToDateTime.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -5179607102055540891L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				dayDateTime.setBackground(null);
				dayToDateTime.setBackground(null);
			}
		});
		painter.reset();
		titleLabel = new Label(shell, SWT.NORMAL);
		painter.add(titleLabel);
		titleText = new Text(shell, SWT.NONE);
		controls.put("titleText", titleText);
		painter.add(titleText);
		validator.applyValidator(titleText, "titleTextRequired",
				DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(titleText, "titleTextAlphaNumericSpaces",
				DecoratorType.ALPHA_NUMERICS_SPACES, true, 50);
		descriptionLabel = new Label(shell, SWT.NONE);
		painter.add(descriptionLabel);

		descriptionText = new Text(shell, SWT.NONE | SWT.WRAP | SWT.V_SCROLL);
		controls.put("descriptionText", descriptionText);
		painter.add(descriptionText);
		validator.applyValidator(descriptionText, 250);
		//validator.applyValidator(descriptionText, "descriptionText1",
				//DecoratorType.ALL_BUT_NO_OTHER_REPEAT_CONSECUTIVE, true, 250);
		l10n();
		return shell;
	}

	@Override
	public String getID() {
		return null;
	}

	@Override
	public void l10n() {
		dayToLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_TO));
		if (rangeButton.getSelection()) {
			dayLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_FROM));
			dayToLabel.setVisible(true);
			dayToDateTime.setVisible(true);
		} else {
			dayLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_DAY));
			dayToLabel.setVisible(false);
			dayToDateTime.setVisible(false);
		}
		titleLabel.setText(MessageUtil.unescape(MessageUtil
				.unescape(AbosMessages.get().LABEL_TITLE)));
		descriptionLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_DESCRIPTION));
		holidayData
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_HOLIDAY_DATA));
		if (option == 1) {
			registerCalendarLabel.setText(MessageUtil.unescape(AbosMessages
					.get().LABEL_REGISTER_HOLIDAY));
		} else {
			registerCalendarLabel.setText(MessageUtil.unescape(AbosMessages
					.get().LABEL_UPDATE_CALENDAR));
		}
	}

	@Override
	public Control getControl(String arg0) {
		return null;
	}

	public ViewController getController() {
		return controller;
	}

	public void setController(ViewController controller) {
		this.controller = controller;
	}

	public Map<String, Control> getControls() {
		return controls;
	}

	public Button getRangeButton() {
		return rangeButton;
	}

	public ValidatorUtils getValidator() {
		return validator;
	}

	public DateTime getDayDateTime() {
		return dayDateTime;
	}

	public void setDayDateTime(DateTime dayDateTime) {
		this.dayDateTime = dayDateTime;
	}

	public DateTime getDayToDateTime() {
		return dayToDateTime;
	}

	public void setDayToDateTime(DateTime dayToDateTime) {
		this.dayToDateTime = dayToDateTime;
	}

	public void setViewController(ViewController controller) {
		this.setController(controller);
	}
}
