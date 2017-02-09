package cu.uci.abcd.acquisition.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.acquisition.IRegistrationManageAcquisitionService;
import cu.uci.abcd.acquisition.ui.controller.AllManagementController;
import cu.uci.abcd.acquisition.ui.updateArea.RecordRow;
import cu.uci.abcd.acquisition.ui.updateArea.ViewRegisterAcquisitionArea;
import cu.uci.abcd.acquisition.ui.updateArea.ViewRegisterAcquisitionAreaConsult;
import cu.uci.abcd.acquisition.ui.updateArea.ViewRegisterAcquisitionAreaUpdate;
import cu.uci.abcd.adquisition.l10n.AbosMessages;
import cu.uci.abcd.dataprovider.jisis.impl.search.option.Option;
import cu.uci.abcd.dataprovider.jisis.impl.search.option.OptionAND;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.ServiceProviderUtil;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeColumnListener;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

public class ConsultLogAcquisition extends ContributorPage implements Contributor {

	private Label lblTitle;
	private Label lblAcquisitionVia;
	private Label lblSearchCriteria;
	private Label lblConsultLog;
	private Label lblControlNumber;
	private Text txtControlNumber;
	private Composite group;
	private Text txtTitle;
	private Combo cbAcquisitionVia;
	private Button findButton;
	private Button btnNewSearch;
	private Composite compoButton1;
	private Label lbCoincidenceList;
	private CRUDTreeTable consultLogAcquisitionTable;
	private Boolean modificated = false;
	List<Record> list = new ArrayList<Record>();
	List<RecordRow> recordList = new ArrayList<RecordRow>();
	private IRegistrationManageAcquisitionService dataBaseManager = ServiceProviderUtil.getService(IRegistrationManageAcquisitionService.class);;

	private ViewRegisterAcquisitionArea classView;
	private Record record;
	public static final String DataBaseName = "Registro_De_Adquisicion";

	private Option option1;
	private Option option2;
	private Option option3;

	private Library library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
	private String defhome = library.getIsisDefHome();
	private String title;

	@Override
	public String getID() {
		return "consultLogAcquisitionID";
	}

	public String containerMenu() {
		return "Adquisición";
	}

	@Override
	public Control createUIControl(Composite parent) {

		parent.setLayout(new FormLayout());

		group = new Composite(parent, SWT.NONE);
		addComposite(group);

		group.setData(RWT.CUSTOM_VARIANT, "gray_background");
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");

		Composite resize = new Composite(parent, 0);
		resize.setVisible(true);
		FormDatas.attach(resize).atTop().atRight().withWidth(0).withHeight(Display.getCurrent().getBounds().height - 140);

		lblConsultLog = new Label(group, SWT.NONE);
		addHeader(lblConsultLog);

		lblSearchCriteria = new Label(group, SWT.NONE);
		addHeader(lblSearchCriteria);

		lblTitle = new Label(group, SWT.NONE);
		add(lblTitle);
		txtTitle = new Text(group, SWT.NONE);
		add(txtTitle);

		lblAcquisitionVia = new Label(group, SWT.NONE);
		add(lblAcquisitionVia);
		cbAcquisitionVia = new Combo(group, SWT.READ_ONLY);
		add(cbAcquisitionVia);
		cbAcquisitionVia.add("-seleccione-");
		cbAcquisitionVia.add(MessageUtil.unescape(AbosMessages.get().LABEL_EXCHANGE));
		cbAcquisitionVia.add(MessageUtil.unescape(AbosMessages.get().LABEL_REQUEST));
		cbAcquisitionVia.add(MessageUtil.unescape(AbosMessages.get().LABEL_DONATION));
		cbAcquisitionVia.select(0);

		br();

		lblControlNumber = new Label(group, SWT.NONE);
		add(lblControlNumber);
		txtControlNumber = new Text(group, SWT.NONE);
		add(txtControlNumber);

		br();

		btnNewSearch = new Button(group, SWT.PUSH);
		add(btnNewSearch);
		findButton = new Button(group, SWT.PUSH);
		add(findButton);

		Label separator = new Label(group, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);

		compoButton1 = new Composite(parent, SWT.NORMAL);
		addComposite(compoButton1);
		compoButton1.setData(RWT.CUSTOM_VARIANT, "gray_background");

		lbCoincidenceList = new Label(compoButton1, SWT.NONE);
		addHeader(lbCoincidenceList);
		lbCoincidenceList.setVisible(false);
		findButton.addSelectionListener(new SelectionAdapter() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				if ((txtTitle.getText().isEmpty()) && (txtControlNumber.getText().isEmpty()) && (cbAcquisitionVia.getSelectionIndex() == 0) && !(modificated))
					RetroalimentationUtils.showInformationMessage(compoButton1, AbosMessages.get().MESSAGES_YOU_MUST_SPECIFY_AT_LEAST_ONE_SEARCH_CRITERIA);
				else {

					title = "\"" + txtTitle.getText() + "\"";					
					lbCoincidenceList.setVisible(true);
					consultLogAcquisitionTable.setVisible(true);

					// table.setVisible(true);
					try {
						searchResults();
						createAcquiditionTable(0, consultLogAcquisitionTable.getPageSize());
						consultLogAcquisitionTable.getPaginator().goToFirstPage();
					} catch (Exception e1) {

					}

					if (consultLogAcquisitionTable.getRows().isEmpty()) {
						lbCoincidenceList.setVisible(false);
						consultLogAcquisitionTable.setVisible(false);
						// table.setVisible(false);
						RetroalimentationUtils.showInformationMessage(compoButton1, cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NO_COINCIDENCES_FOUND);
					}
					group.getParent().layout(true, true);
					group.getParent().redraw();
					group.getParent().update();
				}
			}
		});

		btnNewSearch.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				txtControlNumber.setText("");
				txtTitle.setText("");
				cbAcquisitionVia.select(0);
				modificated = false;
				list.clear();

				consultLogAcquisitionTable.clearRows();
				// table.clearAll();
				lbCoincidenceList.setVisible(false);

				consultLogAcquisitionTable.setVisible(false);
				// table.setVisible(false);
				consultLogAcquisitionTable.destroyEditableArea();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});

		consultLogAcquisitionTable = new CRUDTreeTable(compoButton1, SWT.NONE);
		add(consultLogAcquisitionTable);
		consultLogAcquisitionTable.setEntityClass(RecordRow.class);

		consultLogAcquisitionTable.setVisible(false);

		consultLogAcquisitionTable.setWatch(true, new ViewRegisterAcquisitionAreaConsult(controller));
		consultLogAcquisitionTable.setUpdate(true, new ViewRegisterAcquisitionAreaUpdate(controller));
		consultLogAcquisitionTable.setDelete(true);
		consultLogAcquisitionTable.setPageSize(10);

		consultLogAcquisitionTable.addListener(SWT.Resize, new Listener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();
			}
		});

		TreeTableColumn columns[] = { new TreeTableColumn(25, 0, "getField.getStringFieldValue", new Object[] { 2, null }), new TreeTableColumn(25, 1, "getField.getStringFieldValue", new Object[] { 3, null }), new TreeTableColumn(25, 2, "getField.getStringFieldValue", new Object[] { 1, null }),
				new TreeTableColumn(10, 3, "getField.getStringFieldValue", new Object[] { 23, null }) };

		consultLogAcquisitionTable.createTable(columns);

		consultLogAcquisitionTable.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(PageChangedEvent event) {

				try {
					createAcquiditionTable(event.currentPage - 1, event.pageSize);
				} catch (Exception e) {
					e.printStackTrace();
				}
				refresh();

			}
		});
		consultLogAcquisitionTable.addDeleteListener(new TreeColumnListener() {

			@Override
			public void handleEvent(TreeColumnEvent event) {

				try {
					RecordRow d = (RecordRow) event.entity.getRow();
					long mfn = d.getMfn();

					record = ((AllManagementController) controller).getAcquisition().getRecordByMfn(mfn, "DEF_HOME");
					// record = dataBaseManager.getRecordByMfn(mfn, "DEF_HOME");

					DialogCallback callback = new DialogCallback() {
						private static final long serialVersionUID = 1L;

						public void dialogClosed(int returnCode) {
							if (returnCode == 0) {
								((AllManagementController) controller).getAcquisition();
								dataBaseManager.deleteRecordAcquisition(record, DataBaseName, defhome);

								showInformationMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_DELETE_ONE_ITEM));
								consultLogAcquisitionTable.refresh();

								try {
									createAcquiditionTable(0, consultLogAcquisitionTable.getPageSize());

								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								// tabFolder.notifyListeners(SWT.Resize, new
								// Event());
								l10n();
							}
						}
					};

					MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_WARNING), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_WARN_DELET_DATA), callback);

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		l10n();
		return parent;
	}

	@Override
	public boolean canClose() {
		return false;
	}

	@Override
	public void l10n() {

		lblSearchCriteria.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SEARCH_CRITERIA));
		lblConsultLog.setText(MessageUtil.unescape(AbosMessages.get().CONSULT_LOG_ACQUISITION));

		findButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_CONSULT));
		btnNewSearch.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_NEW_SEARCH));
		lblTitle.setText(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE));
		lblAcquisitionVia.setText(MessageUtil.unescape(AbosMessages.get().LABEL_ACQUISITION_WAY));

		lblControlNumber.setText(MessageUtil.unescape(AbosMessages.get().LABEL_CONTROL_NUMBER));
		lbCoincidenceList.setText(MessageUtil.unescape(AbosMessages.get().LABEL_COINCIDENCE_LIST));

		consultLogAcquisitionTable.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE), MessageUtil.unescape(AbosMessages.get().LABEL_AUTHOR), MessageUtil.unescape(AbosMessages.get().LABEL_CONTROL_NUMBER),
				MessageUtil.unescape(AbosMessages.get().LABEL_ACQUISITION_WAY)));

		consultLogAcquisitionTable.l10n();
		consultLogAcquisitionTable.setCancelButtonText(AbosMessages.get().BUTTON_CANCEL);

	}

	@Override
	public void setViewController(ViewController controller) {
		super.controller = controller;
	}

	/*
	 * public void searchLogAcquisitionTable(int page, int size) throws
	 * JisisDatabaseException {
	 * 
	 * consultLogAcquisitionTable.clearRows();
	 * 
	 * try { list = ((AllManagementController)
	 * controller).getAcquisition().findByOptions(listOption(), DataBaseName,
	 * defhome);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * consultLogAcquisitionTable.setTotalElements(list.size()); List<RecordRow>
	 * listRR = new ArrayList<RecordRow>();
	 * 
	 * for (Record iterator : list) { RecordRow recordRow = new
	 * RecordRow(iterator); listRR.add(recordRow); }
	 * 
	 * consultLogAcquisitionTable.setRows(listRR);
	 * consultLogAcquisitionTable.refresh();
	 * 
	 * }
	 */

	public List<Option> listOption() {
		List<Option> list = new ArrayList<>();
		if ((!txtTitle.getText().isEmpty()) && (!txtControlNumber.getText().isEmpty()) && (cbAcquisitionVia.getSelectionIndex() > 0)) {
			if (list.isEmpty())
				option1 = new Option("2", title);
			list.add(option1);
			option2 = new OptionAND("1", txtControlNumber.getText());
			list.add(option2);
			option3 = new OptionAND("23", cbAcquisitionVia.getText());
			list.add(option3);

		}

		else if ((!txtTitle.getText().isEmpty()) && (!txtControlNumber.getText().isEmpty()) && (cbAcquisitionVia.getSelectionIndex() == 0)) {
			if (list.isEmpty())
				option1 = new Option("2", title);
			list.add(option1);
			option2 = new OptionAND("1", txtControlNumber.getText());
			list.add(option2);
		} else if ((!txtTitle.getText().isEmpty()) && (cbAcquisitionVia.getSelectionIndex() > 0) && (txtControlNumber.getText().isEmpty())) {
			if (list.isEmpty())
				option1 = new Option("2", title);
			list.add(option1);
			if (cbAcquisitionVia.getText().equals("Donación"))
				option3 = new OptionAND("23", "donacion");
			else
				option3 = new OptionAND("23", cbAcquisitionVia.getText());
			list.add(option3);
		} else if ((txtTitle.getText().isEmpty()) && (cbAcquisitionVia.getSelectionIndex() > 0) && (!txtControlNumber.getText().isEmpty())) {
			if (list.isEmpty())
				option2 = new Option("1", txtControlNumber.getText());
			list.add(option2);
			if (cbAcquisitionVia.getText().equals("Donación"))
				option3 = new OptionAND("23", "donacion");
			else
				option3 = new OptionAND("23", cbAcquisitionVia.getText());
			list.add(option3);

		} else if (!(txtTitle.getText().isEmpty()) && (cbAcquisitionVia.getSelectionIndex() == 0) && (txtControlNumber.getText().isEmpty())) {
			if (list.isEmpty())
				option1 = new Option("2", title);
			list.add(option1);
		} else if ((txtTitle.getText().isEmpty()) && (cbAcquisitionVia.getSelectionIndex() == 0) && (!txtControlNumber.getText().isEmpty())) {
			if (list.isEmpty())
				option2 = new Option("1", txtControlNumber.getText());
			list.add(option2);
		} else if ((txtTitle.getText().isEmpty()) && (cbAcquisitionVia.getSelectionIndex() > 0) && (txtControlNumber.getText().isEmpty())) {
			if (list.isEmpty())
				if (cbAcquisitionVia.getText().equals("Donación"))
					option3 = new Option("23", "donacion");
				else
					option3 = new Option("23", cbAcquisitionVia.getText());
			list.add(option3);

		}
		return list;

	}   

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().CONTRIBUTOR_CONSULT_ACQUISITION);
	}

	public int order() {
		return 0;
	}

	private void createAcquiditionTable(int page, int size) {

		consultLogAcquisitionTable.clearRows();

		consultLogAcquisitionTable.setTotalElements((int) recordList.size());
		if (list.size() <= page * size + size) {
			consultLogAcquisitionTable.setRows(recordList.subList(page * size, recordList.size()));
		} else {
			consultLogAcquisitionTable.setRows(recordList.subList(page * size, page * size + size));
		}
		consultLogAcquisitionTable.refresh();
	}

	private void searchResults() {
		recordList.clear();
		list.clear();

		try {
			list = ((AllManagementController) controller).getAcquisition().findByOptions(listOption(), DataBaseName, defhome);

		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Record iterator : list) {
			RecordRow recordRow = new RecordRow(iterator);
			recordList.add(recordRow);
		}
	}

}