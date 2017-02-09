package cu.uci.abcd.management.security.ui;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Room;
import cu.uci.abcd.domain.management.security.AccessRecord;
import cu.uci.abcd.management.security.l10n.AbosMessages;
import cu.uci.abcd.management.security.ui.controller.AccessRecordViewController;
import cu.uci.abcd.management.security.ui.model.AccessControlUpdateArea;
import cu.uci.abcd.management.security.ui.model.AccessControlViewArea;
import cu.uci.abcd.management.security.util.Auxiliary;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.widget.grid.CRUDTreeTableUtils;
import cu.uci.abos.core.widget.grid.IActionCommand;
import cu.uci.abos.core.widget.grid.SecurityCRUDTreeTable;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

public class ConsultAccessControl extends ContributorPage implements
		Contributor {

	private Label lblHeader;
	private Label firstNameLabel;
	private Label secondNameLabel;
	private Label firstSurnameLabel;
	private Label secondSurnameLabel;
	private Label identificationLabel;
	private Label roomLabel;
	private Label fromDateLabel;
	private Label registerDateLabel;
	private Label toDateLabel;
	private Label accessListLabel;
	private Text firstNameText;
	private Text secondNameText;
	private Text firstSurNameText;
	private Text secondSurnameText;
	private Text identificationText;
	private Combo roomCombo;
	private DateTime fromDateTime;
	private DateTime toDateTime;
	private Button consultButton;
	private Button btnNew;
	private Library library;
	private SecurityCRUDTreeTable tabla;
	private int direction = 1024;
	private String orderByString = "motivation";
	private String firstNameConsult = null;
	private String secondNameConsult = null;
	private String firstSurnameConsult = null;
	private String seconSurnameConsult = null;
	private String identificationConsult = null;
	private Room roomConsult = null;
	private Date fromDateConsult;
	private Date toDateConsult;
	private List<String> searchCriteriaList = new ArrayList<>();
	private Composite right;

	@Override
	public Control createUIControl(Composite shell) {
		library = (Library) SecurityUtils.getPrincipal().getByKey("library");

		addComposite(shell);
		shell.setData(RWT.CUSTOM_VARIANT, "gray_background");

		Composite scroll = new Composite(shell,SWT.NONE);
		scroll.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(scroll).atTopTo(shell, 25).atRight(0).withWidth(0).withHeight(Display.getCurrent().getBounds().height - 172);
		
		
		Label consultAccessLabel = new Label(shell, SWT.NONE);
		consultAccessLabel.setText("CONSULTAR ACCESOS");
		addHeader(consultAccessLabel);
		
		Label separator1 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator1);

		lblHeader = new Label(shell, SWT.NONE);
		addHeader(lblHeader);

		firstNameLabel = new Label(shell, SWT.NONE);
		add(firstNameLabel);

		firstNameText = new Text(shell, SWT.NONE);
		add(firstNameText);

		secondNameLabel = new Label(shell, SWT.NONE);
		add(secondNameLabel);

		secondNameText = new Text(shell, SWT.NONE);
		add(secondNameText);

		br();

		firstSurnameLabel = new Label(shell, SWT.NONE);
		add(firstSurnameLabel);

		firstSurNameText = new Text(shell, SWT.NONE);
		add(firstSurNameText);

		secondSurnameLabel = new Label(shell, SWT.NONE);
		add(secondSurnameLabel);

		secondSurnameText = new Text(shell, SWT.NONE);
		add(secondSurnameText);

		br();

		identificationLabel = new Label(shell, SWT.NONE);
		add(identificationLabel);

		identificationText = new Text(shell, SWT.NONE);
		add(identificationText);

		roomLabel = new Label(shell, SWT.NONE);
		add(roomLabel);

		roomCombo = new Combo(shell, SWT.NONE | SWT.READ_ONLY);
		add(roomCombo);

		br();

		registerDateLabel = new Label(shell, SWT.NONE);
		add(registerDateLabel);
		br();

		fromDateLabel = new Label(shell, SWT.NONE);
		add(fromDateLabel);

		fromDateTime = new DateTime(shell, SWT.BORDER | SWT.DROP_DOWN);
		add(fromDateTime);
		
		Auxiliary.goDateTimeToBeforeOneMonth(fromDateTime);

		toDateLabel = new Label(shell, SWT.NONE);
		add(toDateLabel);

		toDateTime = new DateTime(shell, SWT.BORDER | SWT.DROP_DOWN);
		add(toDateTime);

		btnNew = new Button(shell, SWT.PUSH);
		add(btnNew);

		consultButton = new Button(shell, SWT.PUSH);
		add(consultButton);

		Label separator = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);

		right = new Composite(shell, SWT.NONE);
		addComposite(right);
		right.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(right).atTopTo(separator, 10).atRight(15)
				.withWidth(300).withHeight(5);

		accessListLabel = new Label(shell, SWT.NONE);
		addHeader(accessListLabel);

		tabla = new SecurityCRUDTreeTable(shell, SWT.NONE);
		add(tabla);
		tabla.setEntityClass(AccessRecord.class);
		tabla.setSearch(false);
		tabla.setSaveAll(false);
		
		tabla.setWatch(true, new AccessControlViewArea());
		tabla.setUpdate(true, new AccessControlUpdateArea(controller, tabla));
		tabla.setDelete("deleteAccessControlID");
		tabla.setVisible(true);
		tabla.setPageSize(10);
		CRUDTreeTableUtils.configUpdate(tabla);
		
		CRUDTreeTableUtils.configReports(tabla, contributorName(),
				searchCriteriaList);

		tabla.addListener(SWT.Resize, new Listener() {
            private static final long serialVersionUID = 8817895862824622805L;
            @Override
            public void handleEvent(Event event) {
                    refresh();
            }
           });
		
		CRUDTreeTableUtils.configRemove(tabla, new IActionCommand() {
			@Override
			public void execute(final TreeColumnEvent event) {
				tabla.destroyEditableArea();
				AccessRecord accessRecord = (AccessRecord) event.entity
						.getRow();
				Long idAccessRecord = accessRecord.getAccessRecordnID();
				((AccessRecordViewController) controller)
						.deleteAccessRecord(idAccessRecord);
				//tabla.getPaginator().goToFirstPage();
			}
		});

		TreeTableColumn columns[] = {
				new TreeTableColumn(30, 0, "getPerson.getFullName"),
				new TreeTableColumn(20, 1, "getPerson.getDNI"),
				new TreeTableColumn(20, 2, "getRoom.getRoomName"),
				new TreeTableColumn(15, 3, "getAccessDateHourToString"),
				new TreeTableColumn(15, 4, "getAccessDateMinutesToString") };
		tabla.createTable(columns);

		tabla.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {
				if (event.sortData != null) {
					direction = event.sortData.sortDirection;
					switch (event.sortData.columnIndex) {
					case 1:
						orderByString = "motivation";
						break;
					case 2:
						orderByString = "motivation";
						break;
					case 3:
						orderByString = "motivation";
						break;
					case 4:
						orderByString = "motivation";
						break;
					}
				}
				searchAccess(event.currentPage - 1, event.pageSize);
			}
		});

		btnNew.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				tabla.destroyEditableArea();
				tabla.clearRows();
				firstNameText.setText("");
				secondNameText.setText("");
				firstSurNameText.setText("");
				secondSurnameText.setText("");
				identificationText.setText("");
				firstNameText.setFocus();
				
				roomCombo.select(0);
				Auxiliary.goDateTimeToBeforeOneMonth(fromDateTime);
				Auxiliary.goDateTimeToToday(toDateTime);
				Auxiliary.showLabelAndTable(accessListLabel, tabla, false);
			}
		});
		consultButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			
			@SuppressWarnings("deprecation")
			@Override
			public void widgetSelected(SelectionEvent e) {
				tabla.destroyEditableArea();
				tabla.clearRows();
				firstNameConsult = (firstNameText.getText().length() > 0) ? firstNameText
						.getText() : null;
						
						
						
				secondNameConsult = (secondNameText.getText().length() > 0) ? Auxiliary.getValue(secondNameText.getText()) : null;
				firstSurnameConsult = (firstSurNameText.getText().length() > 0) ? firstSurNameText
						.getText() : null;
				seconSurnameConsult = (secondSurnameText.getText().length() > 0) ? Auxiliary.getValue(secondSurnameText.getText()) : null;
				
				identificationConsult = (identificationText.getText().length() > 0) ? Auxiliary.getValue(identificationText.getText()) : null;
				if (UiUtils.getSelected(roomCombo) == null) {
					roomConsult = null;
				} else {
					roomConsult = (Room) UiUtils.getSelected(roomCombo);
				}
				int fromYear = fromDateTime.getYear() - 1900;
				int fromMonth = fromDateTime.getMonth();
				int fromDay = fromDateTime.getDay();
				fromDateConsult = new Date(fromYear, fromMonth, fromDay);

				int toYear = toDateTime.getYear() - 1900;
				int toMonth = toDateTime.getMonth();
				int toDay = toDateTime.getDay();
				toDateConsult = new Date(toYear, toMonth, toDay);

				orderByString = "motivation";
				direction = 1024;

				searchAccess(0, tabla.getPaginator().getPageSize());
				
				if (tabla.getRows().isEmpty()) {
					RetroalimentationUtils
							.showInformationMessage(
									cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NO_COINCIDENCES_FOUND);
					tabla.setVisible(false);
					accessListLabel.setVisible(false);
				} else {
					tabla.setVisible(true);
					accessListLabel.setVisible(true);
				}
				
				tabla.getPaginator().goToFirstPage();
				refresh();
				
				searchCriteriaList.clear();
				UiUtils.get()
				.addSearchCriteria(searchCriteriaList,firstNameLabel.getText(),firstNameText.getText())
				.addSearchCriteria(searchCriteriaList,secondNameLabel.getText(),secondNameText.getText())
				.addSearchCriteria(searchCriteriaList,firstSurnameLabel.getText(),firstSurNameText.getText())
				.addSearchCriteria(searchCriteriaList,secondSurnameLabel.getText(),secondSurnameText.getText())
				.addSearchCriteria(searchCriteriaList,identificationLabel.getText(),identificationText.getText())
				.addSearchCriteria(searchCriteriaList,roomLabel.getText(),roomCombo)
				.addSearchCriteria(searchCriteriaList,fromDateLabel.getText(),fromDateTime)
				.addSearchCriteria(searchCriteriaList,toDateLabel.getText(),toDateTime);
				
			}

		});
		//Auxiliary.showLabelAndTable(accessListLabel, tabla, false);
		
		tabla.getPaginator().goToFirstPage();
		Auxiliary.showLabelAndTable(accessListLabel, tabla, false);
		
		l10n();
		//consultButton.getShell().setDefaultButton(consultButton);
		return shell;
	}

	/*
	 * public void loadRooms() { List<Room> listRoom = library.getRooms();
	 * String[] comboStrings = new String[listRoom.size()]; List<Room> type =
	 * new LinkedList<Room>(); for (int i = 0; i < listRoom.size(); i++) { Room
	 * room = listRoom.get(i); type.add(room); comboStrings[i] =
	 * room.getRoomName(); } roomCombo.setData(type);
	 * roomCombo.setItems(comboStrings); }
	 */
	private void searchAccess(int page, int size) {
		Page<AccessRecord> list = ((AccessRecordViewController) controller)
				.findAccessByParams(library, firstNameConsult, secondNameConsult,
						firstSurnameConsult, seconSurnameConsult,
						identificationConsult, roomConsult, fromDateConsult,
						toDateConsult, page, size, direction, orderByString);
		tabla.clearRows();
		tabla.setTotalElements((int) list.getTotalElements());
		tabla.setRows(list.getContent());
		tabla.refresh();
		
	}

	@Override
	public String getID() {
		return "consultAccessControlID";
	}

	@Override
	public void l10n() {
		//initialize(roomCombo, library.getRooms());
		initialize(roomCombo, ((AccessRecordViewController) controller).getAllManagementSecurityViewController().getAccessRecordService().findRoomByLibrary(library.getLibraryID()));
		
		lblHeader
				.setText(MessageUtil.unescape(AbosMessages.get().SEARCH_CRITERIA));
		firstNameLabel
				.setText(MessageUtil.unescape(AbosMessages.get().FIRST_NAME));
		secondNameLabel
				.setText(MessageUtil.unescape(AbosMessages.get().SECOND_NAME));
		firstSurnameLabel
				.setText(MessageUtil.unescape(AbosMessages.get().FIRST_SURNAME));
		secondSurnameLabel
				.setText(MessageUtil.unescape(AbosMessages.get().SECOND_SURNAME));
		identificationLabel
				.setText(MessageUtil.unescape(AbosMessages.get().IDENTIFICATION));
		roomLabel.setText(MessageUtil.unescape(AbosMessages.get().DESTIN));
		fromDateLabel.setText(MessageUtil.unescape(AbosMessages.get().FROM));
		registerDateLabel
				.setText(MessageUtil.unescape(AbosMessages.get().REGISTER_DATE));
		toDateLabel.setText(MessageUtil.unescape(AbosMessages.get().TO));
		btnNew.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_NEW_SEARCH));
		consultButton
				.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_CONSULT));
		accessListLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LIST_OF_MATCHES));
		tabla.setCancelButtonText(MessageUtil.unescape(AbosMessages.get().BUTTON_CANCEL));
		tabla.setColumnHeaders(Arrays.asList(
				MessageUtil.unescape(AbosMessages.get().NAME_AND_SURNAME),
				MessageUtil.unescape(AbosMessages.get().IDENTIFICATION),
				MessageUtil.unescape(AbosMessages.get().DESTIN),
				MessageUtil.unescape(AbosMessages.get().REGISTER_DATE),
				MessageUtil.unescape(AbosMessages.get().HOUR)));
		
		//tabla.setAddButtonText(AbosMessages.get().BUTTON_ADD);
		tabla.setActionButtonText("exportPDFButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_PDF));
		tabla.setActionButtonText("exportExcelButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));
		
		
		accessListLabel.getParent().layout(true, true);
		accessListLabel.getParent().redraw();
		accessListLabel.getParent().update();
		tabla.l10n();
	}

	@Override
	public boolean canClose() {
		return false;
	}

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().ACCESS_CONSULT);
	}
}
