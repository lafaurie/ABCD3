package cu.uci.abcd.administration.library.ui.model;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abcd.administration.library.ui.controller.CalendarViewController;
import cu.uci.abcd.administration.library.util.Auxiliary;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Calendar;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.FormPagePainter;
import cu.uci.abos.core.ui.PagePainter;
import cu.uci.abos.core.ui.Percent;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class CalendarAddArea extends BaseEditableArea {
	private int valueDayType;
	private CRUDTreeTable table;
	private Button saveBtn;
	List<Date> listDate = new ArrayList<Date>();
	@SuppressWarnings("unused")
	private Library library;
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
	@SuppressWarnings("unused")
	private int option;
	private Button rangeButton;
	private Composite msg;
	private Label holidayData;

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

	public CalendarAddArea(CRUDTreeTable table, ViewController controller,
			int valueDayType) {
		this.controller = controller;
		this.valueDayType = valueDayType;
		this.table = table;
	}

	@Override
	public Composite createUI(Composite shell, IGridViewEntity entity,
			IVisualEntityManager manager) {
		library = (Library) SecurityUtils.getService().getPrincipal()
				.getByKey("library");
		dimension = shell.getParent().getParent().getBounds().width;
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
		//esto
//		titleText.setTextLimit(50);
		controls.put("titleText", titleText);
		painter.add(titleText);
		
		validator.applyValidator(titleText, "titleTextRequired", DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(titleText, 50);
//		validator.applyValidator(titleText, "titleTextAlphaNumericSpaces",
//				DecoratorType.ALPHA_NUMERICS_SPACES, true, 50);
		descriptionLabel = new Label(shell, SWT.NORMAL);
		painter.add(descriptionLabel);
		descriptionText = new Text(shell, SWT.NONE | SWT.WRAP);
		controls.put("descriptionText", descriptionText);
		painter.add(descriptionText);
		validator.applyValidator(descriptionText, 250);
		//validator.applyValidator(descriptionText, "descriptionText2",
			//	DecoratorType.ALL_BUT_NO_OTHER_REPEAT_CONSECUTIVE, true, 250);
		return shell;

	}

	@Override
	public Composite createButtons(Composite parent, IGridViewEntity entity,
			final IVisualEntityManager manager) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		saveBtn = new Button(parent, SWT.PUSH);
		saveBtn.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {

				if (getValidator().decorationFactory
						.isRequiredControlDecorationIsVisible()) {
					RetroalimentationUtils.showErrorShellMessage(MessageUtil
							.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
				} else {
					if (getValidator().decorationFactory
							.AllControlDecorationsHide()) {

						if (getRangeButton().getSelection()
								&& (Auxiliary.getDate(getDayDateTime()).after(
										Auxiliary.getDate(getDayToDateTime())) || Auxiliary
										.getDate(getDayDateTime())
										.equals(Auxiliary
												.getDate(getDayToDateTime())))) {
							RetroalimentationUtils.showErrorShellMessage(MessageUtil
									.unescape(AbosMessages.get().LABEL_END_DATE_GREATER));
							getDayDateTime().setBackground(
									new Color(getDayDateTime().getDisplay(),
											255, 204, 153));
							getDayToDateTime().setBackground(
									new Color(getDayToDateTime().getDisplay(),
											255, 204, 153));
						} else {
							getDayDateTime().setBackground(null);
							getDayToDateTime().setBackground(null);
							DateTime dayToDateTime1 = dayToDateTime;
							if (!getRangeButton().getSelection()) {
								dayToDateTime1 = null;
							}
							String title = titleText.getText()
									.replaceAll(" +", " ").trim();
							String description = descriptionText.getText()
									.replaceAll(" +", " ").trim();
							Nomenclator daytype1 = null;
							Nomenclator daytype2 = null;
							daytype1 = ((CalendarViewController) controller)
									.getAllManagementLibraryViewController()
									.getLibraryService()
									.findNomenclatorById(
											Nomenclator.DAY_TYPE_ALL_YEAR);

							daytype2 = ((CalendarViewController) controller)
									.getAllManagementLibraryViewController()
									.getLibraryService()
									.findNomenclatorById(
											Nomenclator.DAY_TYPE_THIS_YEAR);

							Library library = (Library) SecurityUtils
									.getService().getPrincipal()
									.getByKey("library");

							boolean flag = false;
							if (valueDayType == 2) {

								List<Calendar> thisYear = ((CalendarViewController) controller)
										.findCalendarByLibraryAndDayType(
												daytype2,
												library.getLibraryID());
								List<Calendar> allYear = ((CalendarViewController) controller)
										.findCalendarByLibraryAndDayType(
												daytype1,
												library.getLibraryID());
								flag = exist(
										dayDateTime,
										dayToDateTime1,
										thisYear,
										true,
										MessageUtil.unescape(AbosMessages.get().ELEMENT_EXIST_IN_THIS_YEAR),
										MessageUtil.unescape(AbosMessages.get().ELEMENT_RANGE_EXIST_IN_THIS_YEAR))
										|| exist(
												dayDateTime,
												dayToDateTime1,
												allYear,
												MessageUtil.unescape(AbosMessages
														.get().ELEMENT_EXIST_IN_ALL_YEAR),
												MessageUtil.unescape(AbosMessages
														.get().ELEMENT_RANGE_EXIST_IN_ALL_YEAR));

							} else {
								if (valueDayType == 1) {

									List<Calendar> allYear = ((CalendarViewController) controller)
											.findCalendarByLibraryAndDayType(
													daytype1,
													library.getLibraryID());
									List<Calendar> thisYear = ((CalendarViewController) controller)
											.findCalendarByLibraryAndDayType(
													daytype2,
													library.getLibraryID());
									flag = exist(
											dayDateTime,
											dayToDateTime1,
											allYear,
											MessageUtil.unescape(AbosMessages
													.get().ELEMENT_EXIST_IN_ALL_YEAR),
											MessageUtil.unescape(AbosMessages
													.get().ELEMENT_RANGE_EXIST_IN_ALL_YEAR))
											|| exist(
													dayDateTime,
													dayToDateTime1,
													thisYear,
													MessageUtil
															.unescape(AbosMessages
																	.get().ELEMENT_EXIST_IN_THIS_YEAR),
													MessageUtil
															.unescape(AbosMessages
																	.get().ELEMENT_RANGE_EXIST_IN_THIS_YEAR));
								}
							}
							if (!flag) {
								Nomenclator daytype = daytype2;
								if (valueDayType == 1) {
									daytype = daytype1;
								}

								for (int i = 0; i < listDate.size(); i++) {
									Calendar calendar = new Calendar();
									calendar.setCalendarDay(listDate.get(i));
									if (valueDayType == 2) {
										Integer year = Integer
												.parseInt(new SimpleDateFormat(
														"yyyy").format(listDate
														.get(i)));
										calendar.setYear(year);

									}

									calendar.setDaytype(daytype);
									calendar.setDescription(description);
									calendar.setLibrary(library);
									calendar.setCalendarName(title);
									calendar.setDescription(description);
									((CalendarViewController) controller)
											.saveCalendar(calendar);
									calendar = null;
								}

								table.getPaginator().goToFirstPage();
								table.destroyEditableArea();

								Composite viewSmg = table.getParent();
								RetroalimentationUtils.showInformationMessage(
										viewSmg,
										MessageUtil
												.unescape(cu.uci.abos.core.l10n.AbosMessages
														.get().MSG_INF_CREATE_NEW_ELEMENT));

								refresh();

							}
						}
					} else {
						RetroalimentationUtils.showErrorShellMessage(MessageUtil
								.unescape(cu.uci.abos.core.l10n.AbosMessages
										.get().MSG_ERROR_INCORRECT_DATA));
					}
				}
			}
		});
		return parent;

	}

	@Override
	public boolean isValid() {
		return false;
	}

	@Override
	public boolean closable() {
		return true;
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

		registerCalendarLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_REGISTER_HOLIDAY));
		saveBtn.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ACEPT));
		saveBtn.getParent().layout(true, true);
		saveBtn.getParent().redraw();
		saveBtn.getParent().update();
	}

	@Override
	public String getID() {
		return "addCalendarID";
	}

	public boolean exist(DateTime dayDateTime, DateTime dayToDateTime,
			List<Calendar> list, String singleSmg, String rangeMsg) {
		return exist(dayDateTime, dayToDateTime, list, false, singleSmg,
				rangeMsg);
	}

	@SuppressWarnings("deprecation")
	public boolean exist(DateTime dayDateTime, DateTime dayToDateTime,
			List<Calendar> list, boolean yearCheck, String singleSmg,
			String rangeMsg) {
		listDate.clear();
		Date temp = new Date(dayDateTime.getYear() - 1900,
				dayDateTime.getMonth(), dayDateTime.getDay());
		if (dayToDateTime == null) {
			listDate.add(temp);
		} else {
			Date dayToDate = new Date(dayToDateTime.getYear() - 1900,
					dayToDateTime.getMonth(), dayToDateTime.getDay());
			while (temp.compareTo(dayToDate) <= 0) {
				listDate.add(temp);
				temp = new Date(temp.getTime() + 1 * 24 * 60 * 60 * 1000);
			}
		}

		for (int j = 0; j < listDate.size(); j++) {
			for (int i = 0; i < list.size(); i++) {
				java.util.Calendar cal = java.util.Calendar.getInstance();
				java.util.Calendar cal1 = java.util.Calendar.getInstance();
				cal.setTime(list.get(i).getCalendarDay());
				cal1.setTime(listDate.get(j));
				if ((yearCheck) ? cal.get(java.util.Calendar.MONTH) + 1 == listDate
						.get(j).getMonth() + 1
						&& cal.get(java.util.Calendar.DAY_OF_MONTH) == cal1
								.get(java.util.Calendar.DAY_OF_MONTH)
						&& cal.get(java.util.Calendar.YEAR) == listDate.get(j)
								.getYear() + 1900
						: cal.get(java.util.Calendar.MONTH) + 1 == listDate
								.get(j).getMonth() + 1
								&& cal.get(java.util.Calendar.DAY_OF_MONTH) == cal1
										.get(java.util.Calendar.DAY_OF_MONTH)) {

					if (dayToDateTime == null) {
						RetroalimentationUtils.showErrorShellMessage(singleSmg);
					} else {
						RetroalimentationUtils.showErrorShellMessage(rangeMsg);
					}
					return true;
				}
			}
		}
		return false;
	}

	public Map<String, Control> getControls() {
		return controls;
	}

	public Composite getMsg() {
		return msg;
	}

	public Button getRangeButton() {
		return rangeButton;
	}

	public ValidatorUtils getValidator() {
		return validator;
	}
}
