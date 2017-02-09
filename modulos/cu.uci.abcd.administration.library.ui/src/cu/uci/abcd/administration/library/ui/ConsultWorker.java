//FIXME CLASE EXTENSA
package cu.uci.abcd.administration.library.ui;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
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
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.springframework.data.domain.Page;

import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abcd.administration.library.ui.controller.LibraryViewController;
import cu.uci.abcd.administration.library.ui.controller.ProviderViewController;
import cu.uci.abcd.administration.library.ui.model.WorkerUpdateArea;
import cu.uci.abcd.administration.library.ui.model.WorkerViewArea;
import cu.uci.abcd.administration.library.util.Auxiliary;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Worker;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.widget.grid.CRUDTreeTableUtils;
import cu.uci.abos.core.widget.grid.IActionCommand;
import cu.uci.abos.core.widget.grid.SecurityCRUDTreeTable;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class ConsultWorker extends ContributorPage implements Contributor {
    private Label consultWorkerLabel;
	private ViewController controller;
	private Label searchCriteriaLabel;
	private Label firstNameLabel;
	private Text firstNameText;
	private Label secondNameLabel;
	private Text secondNameText;
	private Label firstSurnameLabel;
	private Text firstSurnameText;
	private Label secondSurnameLabel;
	private Text secondSurnameText;
	private Label identificationLabel;
	private Text identificationText;
	private Label workerTypeLabel;
	private Combo workerTypeCombo;
	private Label rangeDateRegisterLabel;
	private Label fromDateLabel;
	private DateTime fromDateTime;
	private Label toDateLabel;
	private DateTime toDateTime;
	private Label sexLabel;
	private Combo sexCombo;
	private Button newSearchButton;
	private Button consultButton;
	private SecurityCRUDTreeTable workerTable;
	private Library library;
	private String orderByString = "person.firstName";
	private int direction = 1024;
	
	private String firstNameConsult = null;
	private String secondNameConsult = null;
	private String firstSurnameConsult = null;
	private String secondSurnameConsult = null;
	
	private String dniConsult = null;
	private Nomenclator workerTypeConsult = null;
	private Nomenclator genderConsult = null;
	private Date fromDateConsult;
	private Date toDateConsult;
	private Link link;
	private Label workerList;
	private List<String> searchCriteriaList = new ArrayList<>();
	//FIXME BORRAR CODIGO COMENTARIADO
	//private String myLink = ;
	

	private ValidatorUtils validator;
	Composite up;
	Composite middle;
	Composite down;
	Composite tableComposite;
	
	Composite right;
	
	@Override
	public String contributorName() {
		return AbosMessages.get().CONSULT_WORKER;
	}

	@Override
	public String getID() {
		return "consultWorkerID";
	}
	//FIXME BORRAR CODIGO COMENTARIADO
	@Override
	public Control createUIControl(Composite parent) {
		
		library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
		validator = new ValidatorUtils(new CustomControlDecoration());

		addComposite(parent);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		
		Composite scroll = new Composite(parent,SWT.NONE);
		scroll.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(scroll).atTopTo(parent, 25).atRight(0).withWidth(0).withHeight(Display.getCurrent().getBounds().height - 172);
		
		
		
		up = new Composite(parent, SWT.NONE);
		addComposite(up);
		up.setData(RWT.CUSTOM_VARIANT, "gray_background");
		
		consultWorkerLabel = new Label(up, SWT.NONE);
		addHeader(consultWorkerLabel);
		
		Label separator = new Label(up, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);
		
		searchCriteriaLabel = new Label(up, SWT.NONE);
		addHeader(searchCriteriaLabel);
		
        firstNameLabel = new Label(up, SWT.NONE);
		add(firstNameLabel);
		
		firstNameText = new Text(up, SWT.NONE);
		add(firstNameText);
		//validator.applyValidator(firstNameText, 29);
		//firstNameText.setData("grupo1", true);
		//firstNameText.setData("grupo2", true);

		secondNameLabel = new Label(up, SWT.NONE);
		add(secondNameLabel);
		secondNameText = new Text(up, SWT.NONE);
		//validator.applyValidator(secondNameText, 29);
		add(secondNameText);
		//secondNameText.setData("grupo1", true);
		br();
		
		firstSurnameLabel = new Label(up, SWT.NONE);
		add(firstSurnameLabel);
		firstSurnameText = new Text(up, SWT.NONE);
		//validator.applyValidator(firstSurnameText, 29);
		add(firstSurnameText);
		//firstSurnameText.setData("grupo2", true);
		
		
        secondSurnameLabel = new Label(up, SWT.NONE);
    	add(secondSurnameLabel);
    	
		secondSurnameText = new Text(up, SWT.NONE);
		//validator.applyValidator(secondSurnameText, 29);
		add(secondSurnameText);

		br();
		
		identificationLabel = new Label(up, SWT.NONE);
		add(identificationLabel);

		identificationText = new Text(up, SWT.NONE);
		add(identificationText);
		//validator.applyValidator(identificationText, 19);
		

		workerTypeLabel = new Label(up, SWT.NONE);
		add(workerTypeLabel);

		workerTypeCombo = new Combo(up, SWT.READ_ONLY);
		add(workerTypeCombo);
		
		br();
		
		middle = new Composite(parent, SWT.NONE);
		middle.setVisible(false);
		addComposite(middle);
		middle.setData(RWT.CUSTOM_VARIANT, "gray_background");
		
		
		
		rangeDateRegisterLabel = new Label(middle, SWT.NONE);
		add(rangeDateRegisterLabel);
		br();
			
		fromDateLabel = new Label(middle, SWT.NONE);
		add(fromDateLabel);
			
		
		fromDateTime = new DateTime(middle, SWT.BORDER | SWT.DROP_DOWN);
		add(fromDateTime);
		//FIXME BORRAR CODIGO COMENTARIADO
		//arreglar, invento
				java.util.Date d = new java.util.Date();
				Date a = new Date(d.getTime() - 899999999 );
				Date b = new Date(a.getTime() - 899999999 );
				Date c = new Date(b.getTime() - 899999999 );
				
				int fromYear = Integer.parseInt(new SimpleDateFormat("yyyy")
						.format(c));
				int fromMonth = Integer.parseInt(new SimpleDateFormat("MM")
						.format(c));
				int fromDay = Integer.parseInt(new SimpleDateFormat("dd")
						.format(c));
				
				fromDateTime.setDate(fromYear, fromMonth - 1, fromDay);
				
		
		toDateLabel = new Label(middle, SWT.NONE);
		add(toDateLabel);
			
		toDateTime = new DateTime(middle, SWT.BORDER | SWT.DROP_DOWN);
		add(toDateTime);
		br();

		sexLabel = new Label(middle, SWT.NONE);
		add(sexLabel);

		sexCombo = new Combo(middle, SWT.READ_ONLY);
		add(sexCombo);

			
		down = new Composite(parent, SWT.NONE);
		addComposite(down);
		down.setData(RWT.CUSTOM_VARIANT, "gray_background");
		
		newSearchButton = new Button(down, SWT.NONE);
		add(newSearchButton);

		newSearchButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -5179607102055540891L;
			@Override
			public void widgetSelected(SelectionEvent e) {
				searchCriteriaList.clear();
				workerTable.destroyEditableArea();
				if (tableComposite.isVisible()) {
					tableComposite.setVisible(false);
					insertComposite(tableComposite, down);
					
					tableComposite.getShell().layout(true, true);
					tableComposite.getShell().redraw();
					tableComposite.getShell().update();
					
				}
				Auxiliary.refreshPage(down.getParent());
				workerTable.destroyEditableArea();
				
				firstNameText.setText("");
				firstNameText.setFocus();
				secondNameText.setText("");
				firstSurnameText.setText("");
				secondSurnameText.setText("");
				identificationText.setText("");
				
				workerTypeCombo.select(0);
				//FIXME BORRAR CODIGO COMENTARIADO
				//arreglar, invento
				java.util.Date d = new java.util.Date();
				Date a = new Date(d.getTime() - 899999999 );
				Date b = new Date(a.getTime() - 899999999 );
				Date c = new Date(b.getTime() - 899999999 );
				
				int fromYear = Integer.parseInt(new SimpleDateFormat("yyyy")
						.format(c));
				int fromMonth = Integer.parseInt(new SimpleDateFormat("MM")
						.format(c));
				int fromDay = Integer.parseInt(new SimpleDateFormat("dd")
						.format(c));
				//FIXME BORRAR CODIGO COMENTARIADO
				fromDateTime.setDate(fromYear, fromMonth - 1, fromDay);
				
				java.util.Date utilDate = new java.util.Date();
				int year = Integer.parseInt(new SimpleDateFormat("yyyy")
						.format(utilDate));
				int month = Integer.parseInt(new SimpleDateFormat("MM")
						.format(utilDate));
				int day = Integer.parseInt(new SimpleDateFormat("dd")
						.format(utilDate));
				//fromDateTime.setDate(year, month - 1, day);
				toDateTime.setDate(year, month - 1, day);
				
				sexCombo.select(0);
			}

		});

		consultButton = new Button(down, SWT.NONE);
		add(consultButton);

		link = new Link(down, SWT.NONE);
		
		add(link);
		br();
		
		link.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = 8413313041460293463L;
			@Override
			public void handleEvent(Event arg0) {
				if (middle.isVisible()) {
					link.setText("<a>"+MessageUtil.unescape(AbosMessages.get().LABEL_ADVANCED_SEARCH)+"</a>");
					//link.setText("<a>Búsqueda Avanzada</a>");
					middle.setVisible(false);
					insertComposite(middle, up);
					middle.getShell().layout(true, true);
					middle.getShell().redraw();
					middle.getShell().update();
				}else{
					//FIXME BORRAR CODIGO COMENTARIADO
					link.setText("<a>"+MessageUtil.unescape(AbosMessages.get().LABEL_BASIC_SEARCH)+"</a>");
					//link.setText("<a>Búsqueda Basica</a>");
					middle.setVisible(true);
					insertComposite(middle, up);
					middle.getShell().layout(true, true);
					middle.getShell().redraw();
					middle.getShell().update();
				}
				Auxiliary.refreshPage(up.getParent());
			}
		});
		//FIXME BORRAR CODIGO COMENTARIADO

		Label separador = new Label(down, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separador);
		/*
		right =  new Composite(down, SWT.NONE);
		addComposite(right);
		right.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(right).atTopTo(separador, 10).atRight(15).withWidth(300).withHeight(5);
*/
		
		tableComposite = new Composite(parent, SWT.NONE);
		//tableComposite.setVisible(false);
		addComposite(tableComposite);
		tableComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		
		//right =  new Composite(down, SWT.NONE);
		//addComposite(right);
		//right.setData(RWT.CUSTOM_VARIANT, "gray_background");
		//FormDatas.attach(right).atTopTo(tableComposite, 10).atRight(15).withWidth(300).withHeight(5);
		
		workerList = new Label(tableComposite, SWT.NORMAL);
		addHeader(workerList);

		workerTable = new SecurityCRUDTreeTable(tableComposite, SWT.NONE);
		add(workerTable);
		FormDatas.attach(workerTable).atTopTo(workerList, 15).atLeft(15).atRight(15);
		workerTable.setEntityClass(Worker.class);
		workerTable.setSearch(false);
		workerTable.setSaveAll(false);
		
		workerTable.setWatch(true, new WorkerViewArea(controller));
		workerTable.setUpdate(true, new WorkerUpdateArea(controller, workerTable));
		
		//workerTable.setUpdate(true, new WorkerUpdateArea());

		workerTable.setDelete("deleteWorkerID");
		workerTable.setVisible(true);
		workerTable.setPageSize(10);
		
		workerTable.addListener(SWT.Resize, new Listener() {
            private static final long serialVersionUID = 8817895862824622805L;
            @Override
            public void handleEvent(Event event) {
                    refresh();
            }
           });
		
		CRUDTreeTableUtils.configUpdate(workerTable);
		CRUDTreeTableUtils.configReports(workerTable, contributorName(),
				searchCriteriaList);

		CRUDTreeTableUtils.configRemove(workerTable, new IActionCommand() {
			@Override
			public void execute(final TreeColumnEvent event) {
				workerTable.destroyEditableArea();
				Worker worker = (Worker) event.entity.getRow();
				Long workerId = worker.getWorkerID();
				((LibraryViewController) controller).deleteWorkerById(workerId);
				//searchWorkers(0, workerTable.getPaginator().getPageSize());
			}
		});
		
		TreeTableColumn columns[] = {
				new TreeTableColumn(32, 0, "getPersonToString"),
				new TreeTableColumn(30, 1, "getWorkerType.getNomenclatorName"),
				new TreeTableColumn(16, 2, "getWorkerActivity.getNomenclatorName"),
				new TreeTableColumn(22, 3, "getRegisterDateToString") };
		workerTable.createTable(columns);
	
		workerTable.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {
				if (event.sortData != null) {
					direction = event.sortData.sortDirection;
					switch (event.sortData.columnIndex) {
					case 0:
						orderByString = "person.firstName";
						break;
					case 1:
						orderByString = "workerType.nomenclatorName";
						break;
					case 2:
						orderByString = "registerDate";
						break;
					}
				}
				searchWorkers(event.currentPage - 1, event.pageSize);
				Auxiliary.refreshPage(up.getParent());
				
				refresh();
			}
		});

		consultButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;
			
			
			//FIXME BORRAR CODIGO COMENTARIADO
			@SuppressWarnings("deprecation")
			@Override
			public void widgetSelected(SelectionEvent e) {
				workerTable.destroyEditableArea();
				if(Auxiliary.finishDate_MoreThanInitDate_And_LessOrEqualThanToday(fromDateTime, toDateTime)){
					
				workerTable.destroyEditableArea();
				workerTable.clearRows();
				firstNameConsult = (firstNameText.getText().length()>0)?firstNameText.getText():null;
				
				
				secondNameConsult = (secondNameText.getText().length()>0)?Auxiliary.getValue(secondNameText.getText()):null;
				
				firstSurnameConsult = (firstSurnameText.getText().length()>0)?firstSurnameText.getText():null;
				
				
				secondSurnameConsult = (secondSurnameText.getText().length()>0)?Auxiliary.getValue(secondSurnameText.getText()):null;
				
				
				
				dniConsult = (identificationText.getText()!="")?Auxiliary.getValue(identificationText.getText()):null;
				
				if (UiUtils.getSelected(workerTypeCombo) == null) {
					workerTypeConsult = null;
				} else {
					workerTypeConsult = (Nomenclator) UiUtils.getSelected(workerTypeCombo);
				}
				
				
				if (UiUtils.getSelected(sexCombo) == null) {
					genderConsult = null;
				} else {
					genderConsult = (Nomenclator) UiUtils.getSelected(sexCombo);
				}
				
				if (!middle.isVisible()) {
					fromDateConsult = null;
					toDateConsult = null;
					genderConsult = null;
				} else {
					
					int fromYear = fromDateTime.getYear() - 1900;
					int fromMonth = fromDateTime.getMonth();
					int fromDay = fromDateTime.getDay();
					fromDateConsult = new Date(fromYear, fromMonth, fromDay);
					int toYear = toDateTime.getYear() - 1900;
					int toMonth = toDateTime.getMonth();
					int toDay = toDateTime.getDay();
					toDateConsult = new Date(toYear, toMonth, toDay);
				}
				if (!tableComposite.isVisible()) {
					tableComposite.setVisible(true);
					insertComposite(tableComposite, down);
					
					tableComposite.getParent().layout(true, true);
					tableComposite.getParent().redraw();
					tableComposite.getParent().update();
					
				}
				
				workerTable.getPaginator().goToFirstPage();
				
				if (workerTable.getRows().isEmpty()) {
					RetroalimentationUtils
							.showInformationMessage(
									tableComposite,
									cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NO_COINCIDENCES_FOUND);
					workerList.setVisible(false);
					workerTable.setVisible(false);
				}else{
					workerList.setVisible(true);
					workerTable.setVisible(true);
				}
				
			}else{
				workerList.setVisible(false);
				workerTable.clearRows();
				workerTable.setVisible(false);
			}
				
				//DateTime afromDateTime = fromDateTime;
				//DateTime atoDateTime = toDateTime;
				//Combo asexCombo = sexCombo;
				
				//if (!middle.isVisible()) {
				//	afromDateTime = null;
				//	atoDateTime = null;
				//	asexCombo = null;
				//}
				searchCriteriaList.clear();
				UiUtils sss = UiUtils.get().addSearchCriteria(searchCriteriaList, firstNameLabel.getText(), firstNameText.getText()).
				addSearchCriteria(searchCriteriaList, secondNameLabel.getText(), secondNameText.getText()).
				addSearchCriteria(searchCriteriaList, firstSurnameLabel.getText(), firstSurnameText.getText()).
				addSearchCriteria(searchCriteriaList, secondSurnameLabel.getText(), secondSurnameText.getText()).
				addSearchCriteria(searchCriteriaList, identificationLabel.getText(), identificationText.getText()).
				addSearchCriteria(searchCriteriaList, workerTypeLabel.getText(), workerTypeCombo);
				
				if (middle.isVisible()) {
					//afromDateTime = null;
					//atoDateTime = null;
					//asexCombo = null;
					sss.addSearchCriteria(searchCriteriaList, fromDateLabel.getText(), fromDateTime).
					addSearchCriteria(searchCriteriaList, toDateLabel.getText(), toDateTime).
					addSearchCriteria(searchCriteriaList, sexLabel.getText(), sexCombo);;
				}
				//addSearchCriteria(searchCriteriaList, fromDateLabel.getText(), afromDateTime).
				//addSearchCriteria(searchCriteriaList, toDateLabel.getText(), atoDateTime).
				//addSearchCriteria(searchCriteriaList, sexLabel.getText(), asexCombo);
				
				refresh();
				
				
			}
		});
		//loadWorkerType();
		//loadSex();
		
		workerTable.getPaginator().goToFirstPage();
		Auxiliary.showLabelAndTable(workerList, workerTable, false);
		//workerTable.setVisible(false);
		l10n();
		//consultButton.getShell().setDefaultButton(consultButton);
		return parent;
	}

	private void searchWorkers(int page, int size) {
		Page<Worker> list = ((LibraryViewController) controller)
				.findWorkerByParams(library, firstNameConsult,
						secondNameConsult, firstSurnameConsult,
						secondSurnameConsult, dniConsult, workerTypeConsult,
						genderConsult, fromDateConsult, toDateConsult, page,
						size, direction, orderByString);
		workerTable.clearRows();
		workerTable.setTotalElements((int) list.getTotalElements());
		workerTable.setRows(list.getContent());
		workerTable.refresh();
		/*
		if (workerTable.getRows().isEmpty()) {
			RetroalimentationUtils
					.showInformationMessage(
							tableComposite,
							cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NO_COINCIDENCES_FOUND);
			workerList.setVisible(false);
			workerTable.setVisible(false);
		}else{
			workerList.setVisible(true);
			workerTable.setVisible(true);
		}
		*/
		//refresh();
		
	}

	public void loadWorkerType() {
		List<Nomenclator> listWorkerType = ((LibraryViewController) controller)
				.findNomenclatorByCode(library.getLibraryID(), Nomenclator.WORKER_TYPE);
		String[] comboStrings = new String[listWorkerType.size()];
		List<Nomenclator> type = new LinkedList<Nomenclator>();
		for (int i = 0; i < listWorkerType.size(); i++) {
			Nomenclator workerType = listWorkerType.get(i);
			type.add(workerType);
			comboStrings[i] = workerType.getNomenclatorName();
		}
		workerTypeCombo.setData(type);
		workerTypeCombo.setItems(comboStrings);
	}

	public void loadSex() {
		List<Nomenclator> listSex = ((LibraryViewController) controller)
				.findNomenclatorByCode(library.getLibraryID(), Nomenclator.SEX);
		String[] comboStrings = new String[listSex.size()];
		List<Nomenclator> type = new LinkedList<Nomenclator>();
		for (int i = 0; i < listSex.size(); i++) {
			Nomenclator sex = listSex.get(i);
			type.add(sex);
			comboStrings[i] = sex.getNomenclatorName();
		}
		sexCombo.setData(type);
		sexCombo.setItems(comboStrings);
	}

	@Override
	public boolean canClose() {
		return false;
	}

	@Override
	public void l10n() {
		if (!middle.isVisible()) {
		link.setText("<a>"+MessageUtil.unescape(AbosMessages.get().LABEL_ADVANCED_SEARCH)+"</a>");
		}else{
			link.setText("<a>"+MessageUtil.unescape(AbosMessages.get().LABEL_BASIC_SEARCH)+"</a>");
		}
		consultWorkerLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_CONSULT_WORKER));
		searchCriteriaLabel
		.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SEARCH_CRITERIA));
		
		firstNameLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_FIRST_NAME));
		secondNameLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SECOND_NAME));
		firstSurnameLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_FIRST_SURNAME));
		secondSurnameLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SECOND_SURNAME));
		
		identificationLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));
		workerTypeLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_WORKER_TYPE));
		rangeDateRegisterLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_DATE_REGISTER_RANGE));
		fromDateLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_FROM));
		toDateLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_TO));
		sexLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SEX));
		newSearchButton
				.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_NEW_SEARCH));
		consultButton
				.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_CONSULT));
		workerList
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_LIST_OF_MATCHES));
		workerTable
				.setCancelButtonText(MessageUtil.unescape(AbosMessages.get().BUTTON_CANCEL));
		workerTable.setColumnHeaders(Arrays.asList(
				MessageUtil.unescape(AbosMessages.get().LABEL_NAME),
				MessageUtil.unescape(AbosMessages.get().LABEL_WORKER_TYPE),
				MessageUtil.unescape(AbosMessages.get().ACTIVITY),
				MessageUtil.unescape(AbosMessages.get().LABEL_DATE_REGISTER)));
		
		workerTable.setActionButtonText("exportPDFButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_PDF));
		workerTable.setActionButtonText("exportExcelButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));
		
		int position = workerTypeCombo.getSelectionIndex();
		initialize(workerTypeCombo, ((LibraryViewController) controller)
				.findNomenclatorByCode(library.getLibraryID(), Nomenclator.WORKER_TYPE));
		workerTypeCombo.select(position);
		
		
		int positionSex = sexCombo.getSelectionIndex();
		initialize(sexCombo, ((LibraryViewController) controller)
				.findNomenclatorByCode(library.getLibraryID(), Nomenclator.SEX));
		sexCombo.select(positionSex);
		
		workerTable.l10n();
		Auxiliary.refreshPage(up.getParent());
	}
	//FIXME BORRAR CODIGO COMENTARIADO
	@Override
	public void setViewController(ViewController controller) {
		this.controller = controller;
	}
	/*
	public void rezise(Composite parent) {
		int height = 0;
		for (int i = 0; i < parent.getChildren().length; i++) {
			if(  parent.getChildren()[i] instanceof Composite  ){
				height = height + parent.getChildren()[i].getSize().x;
			}
		}
		parent.setSize(parent.getSize().x, height);
		FormData tempo = (FormData) parent.getLayoutData();
		tempo.height = parent.getSize().y;
		parent.setLayoutData(tempo);
		parent.layout(true, true);
		parent.redraw();
		parent.update();
	}
*/
}
