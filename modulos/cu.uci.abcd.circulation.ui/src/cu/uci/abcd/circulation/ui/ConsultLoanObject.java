package cu.uci.abcd.circulation.ui;

import java.sql.Date;
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
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.springframework.data.domain.Page;

import cu.uci.abcd.circulation.l10n.AbosMessages;
import cu.uci.abcd.circulation.ui.controller.AllManagementLoanUserViewController;
import cu.uci.abcd.circulation.ui.listener.EventConsultObjectLoan;
import cu.uci.abcd.circulation.ui.model.ViewAreaLoanObject;
import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Room;
import cu.uci.abcd.domain.util.Auxiliary;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.widget.grid.CRUDTreeTableUtils;
import cu.uci.abos.core.widget.grid.SecurityCRUDTreeTable;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;
public class ConsultLoanObject extends ContributorPage implements Contributor {

	private List<String> searchC = new ArrayList<>();

	private String orderByString = "title";

	private Label lbSearchCriteria;
	private Label lbTitle;
	private Label lbAuthor;
	private Label lbControlNumber;
	private Label lbObjectType;
	private Label lbInventaryNumber;
	private Label lbStateObject;
	private Label lbLocation;
	private Label lbFrom;
	private Label lbUp;
	private Label lbCoincidenceList;
	private Link link;
	private Text txtTitle;
	private Text txtAuthor;
	private Text txtControlNumber;
	private Text txtInventaryNumber;
	private Label lbRangeDate;
	private DateTime dateTime;
	private DateTime dateTime1;
	private Label lbConsultLoanObject;

	private Button btnConsult;
	private Button btnNewSearch;
	private Combo comboObjectType;
	private Combo comboObjectState;
	private Combo comboLocation;
	private Composite busquedaB;
	private SecurityCRUDTreeTable tabla;

	private String title = null;
	private String author = null;
	private String control_number = null;
	private Nomenclator record_type_id = null;
	private Nomenclator loan_object_state = null;
	private Room loan_object_rooms = null;
	private String inventory_number = null;
	private Date dateRegister = null;
	private Date endDateRegister = null;
	private Composite compoSearchA;
	private List<LoanObject> finalList;
	private int direction = 1024;
	private Composite compoButton;
	private Composite compoButton1;
	private Library library;

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().NAME_UI_CONSULT_OBJECT_LOAN);
	}

	// FIXME METODO COMPLEJO
	@Override
	public Control createUIControl(final Composite parent) {
		library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");

		addComposite(parent);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		
		Composite resize = new Composite(parent, 0);
		resize.setVisible(false);
		FormDatas.attach(resize).atTop().atRight().withWidth(0).withHeight(Display.getCurrent().getBounds().height-100);
		
		
		busquedaB = new Composite(parent, SWT.NORMAL);
		addComposite(busquedaB);
		busquedaB.setData(RWT.CUSTOM_VARIANT, "gray_background");

		lbConsultLoanObject = new Label(busquedaB, SWT.NONE);
		addHeader(lbConsultLoanObject);

		Label separatorHeader = new Label(busquedaB,  SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separatorHeader);
	
		lbSearchCriteria = new Label(busquedaB, SWT.NONE);
		addHeader(lbSearchCriteria);

		lbTitle = new Label(busquedaB, SWT.RIGHT);
		add(lbTitle);
		txtTitle = new Text(busquedaB, SWT.NORMAL);
		add(txtTitle);

		lbAuthor = new Label(busquedaB, SWT.RIGHT);
		add(lbAuthor);
		txtAuthor = new Text(busquedaB, SWT.NORMAL);
		add(txtAuthor);

		br();

		lbControlNumber = new Label(busquedaB, SWT.RIGHT);
		add(lbControlNumber);
		txtControlNumber = new Text(busquedaB, SWT.NORMAL);
		add(txtControlNumber);

		lbObjectType = new Label(busquedaB, SWT.RIGHT);
		add(lbObjectType);
		comboObjectType = new Combo(busquedaB, SWT.READ_ONLY);
		add(comboObjectType);

		br();

		lbStateObject = new Label(busquedaB, SWT.RIGHT);
		add(lbStateObject);
		comboObjectState = new Combo(busquedaB, SWT.READ_ONLY);
		add(comboObjectState);

		lbInventaryNumber = new Label(busquedaB, SWT.RIGHT);
		add(lbInventaryNumber);
		txtInventaryNumber = new Text(busquedaB, SWT.NORMAL);
		add(txtInventaryNumber);

		// -----------------Search Advanced---------------------------
		compoSearchA = new Composite(parent, SWT.NORMAL);
		compoSearchA.setVisible(false);
		addComposite(compoSearchA);
		compoSearchA.setData(RWT.CUSTOM_VARIANT, "gray_background");

		lbRangeDate = new Label(compoSearchA, SWT.NORMAL);
		add(lbRangeDate);
		br();

		lbFrom = new Label(compoSearchA, SWT.RIGHT);
		add(lbFrom);
		dateTime = new DateTime(compoSearchA, SWT.BORDER | SWT.DROP_DOWN);
		add(dateTime);

		cu.uci.abcd.circulation.ui.auxiliary.Auxiliary.goDateTimeToBeforeOneMonth(dateTime);

		lbUp = new Label(compoSearchA, SWT.RIGHT);
		add(lbUp);
		dateTime1 = new DateTime(compoSearchA, SWT.BORDER | SWT.DROP_DOWN);
		add(dateTime1);

		lbLocation = new Label(compoSearchA, SWT.RIGHT);
		add(lbLocation);
		comboLocation = new Combo(compoSearchA, SWT.READ_ONLY);
		add(comboLocation);

		// ********************Buttons***************************
		compoButton = new Composite(parent, SWT.NORMAL);
		addComposite(compoButton);
		compoButton.setData(RWT.CUSTOM_VARIANT, "gray_background");

		btnNewSearch = new Button(compoButton, SWT.PUSH);
		add(btnNewSearch);

		btnConsult = new Button(compoButton, SWT.PUSH);
		add(btnConsult);

		link = new Link(compoButton, SWT.NONE);
		add(link);

		br();

		link.addListener(SWT.Selection, new EventConsultObjectLoan(compoSearchA, link, busquedaB, this));

		btnNewSearch.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -8525541221879944571L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				txtTitle.setText("");
				txtAuthor.setText("");
				txtControlNumber.setText("");
				txtInventaryNumber.setText("");
				comboObjectType.select(0);
				comboObjectState.select(0);
				comboLocation.select(0);

				tabla.clearRows();
				tabla.destroyEditableArea();
				lbCoincidenceList.setVisible(false);
				tabla.setVisible(false);

				cu.uci.abcd.circulation.ui.auxiliary.Auxiliary.goDateTimeToBeforeOneMonth(dateTime);
				cu.uci.abcd.circulation.ui.auxiliary.Auxiliary.goDateTimeToToday(dateTime1);
				  
			}
		});

		btnConsult.addSelectionListener(new SelectionAdapter() {

			private static final long serialVersionUID = 7898030967335446631L;

			@SuppressWarnings({ "deprecation" })
			@Override
			public void widgetSelected(SelectionEvent e) {
				searchC.clear();
				tabla.destroyEditableArea();
				tabla.clearRows();
				lbCoincidenceList.setVisible(true);
				tabla.setVisible(true);

				if (compoSearchA.getVisible() == false) {
					dateRegister = null;
					endDateRegister = null;
				} else {
					int fromYear = dateTime.getYear() - 1900;
					int fromMonth = dateTime.getMonth();
					int fromDay = dateTime.getDay();

					dateRegister = new Date(fromYear, fromMonth, fromDay);
					searchC.add(lbFrom.getText());
					searchC.add(Auxiliary.FormatDate(dateRegister));

					int fromYear1 = dateTime1.getYear() - 1900;
					int fromMonth1 = dateTime1.getMonth();
					int fromDay1 = dateTime1.getDay();

					endDateRegister = new Date(fromYear1, fromMonth1, fromDay1);
					searchC.add(lbUp.getText());
					searchC.add(Auxiliary.FormatDate(endDateRegister));

					if (endDateRegister.before(dateRegister)) {
						lbCoincidenceList.setVisible(false);
						tabla.setVisible(false);
						RetroalimentationUtils.showErrorMessage(compoButton1, AbosMessages.get().MSG_ERROR_FINAL_DATE_LESS_THAN_INITIAL);

					}
				}

				title = txtTitle.getText().length() > 0 ? txtTitle.getText().replaceAll(" +", " ").trim() : null;
				author = txtAuthor.getText().length() > 0 ? txtAuthor.getText().replaceAll(" +", " ").trim() : null;
				control_number = txtControlNumber.getText().length() > 0 ? txtControlNumber.getText().replaceAll(" +", " ").trim() : null;
				inventory_number = txtInventaryNumber.getText().length() > 0 ? txtInventaryNumber.getText().replaceAll(" +", " ").trim() : null;

				UiUtils.get().addSearchCriteria(searchC, MessageUtil.unescape(AbosMessages.get().LABEL_TITLE), txtTitle.getText()).addSearchCriteria(searchC, MessageUtil.unescape(AbosMessages.get().LABEL_AUTHOR), txtAuthor.getText())
						.addSearchCriteria(searchC, MessageUtil.unescape(AbosMessages.get().LABEL_CONTROL_NUMBER), txtControlNumber.getText()).addSearchCriteria(searchC, MessageUtil.unescape(AbosMessages.get().LABEL_INVENTORY_NUMBER), txtInventaryNumber.getText());

				if (UiUtils.getSelected(comboObjectType) == null) {
					record_type_id = null;
				} else {
					record_type_id = (Nomenclator) UiUtils.getSelected(comboObjectType);
					searchC.add(lbObjectType.getText());
					searchC.add(comboObjectType.getText());
				}

				if (UiUtils.getSelected(comboObjectState) == null) {
					loan_object_state = null;
				} else {
					loan_object_state = (Nomenclator) UiUtils.getSelected(comboObjectState);
					searchC.add(lbStateObject.getText());
					searchC.add(comboObjectState.getText());
				}

				if (UiUtils.getSelected(comboLocation) == null) {
					loan_object_rooms = null;
				} else {
					loan_object_rooms = (Room) UiUtils.getSelected(comboLocation);
					searchC.add(lbLocation.getText());
					searchC.add(comboLocation.getText());
				}

				orderByString = "title";

				direction = 1024;
				
				searchLoanObject(0, tabla.getPaginator().getPageSize());
			
				if (tabla.getRows().isEmpty()) {
					lbCoincidenceList.setVisible(false);
					tabla.setVisible(false);
					RetroalimentationUtils.showInformationMessage(compoButton1, cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NO_COINCIDENCES_FOUND);
				}
				tabla.getPaginator().goToFirstPage();
				refresh();
			}
		});

		Label separator = new Label(compoButton, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);
   
		compoButton1 = new Composite(parent, SWT.NORMAL);
		addComposite(compoButton1);		
		compoButton1.setSize(100, 800);
		
		compoButton1.setData(RWT.CUSTOM_VARIANT, "gray_background");
		           
		resize = new Composite(compoButton1, 0);
		resize.setVisible(false);    
		FormDatas.attach(resize).withWidth(0).atRight(0).withHeight(Display.getCurrent().getBounds().height - 350);
	      
		lbCoincidenceList = new Label(compoButton1, SWT.NONE);
		addHeader(lbCoincidenceList);
		lbCoincidenceList.setVisible(false);

		// -----------------Table--------------------------------------

		tabla = new SecurityCRUDTreeTable(compoButton1, SWT.NONE);
		add(tabla);
		tabla.setVisible(false);
		tabla.setEntityClass(LoanObject.class);
		tabla.setWatch(true, new ViewAreaLoanObject(controller));
		
		tabla.addListener(SWT.Resize, new Listener() {			
			private static final long serialVersionUID = -2654809274899010553L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();
			}
		});
		
		tabla.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {				
				searchLoanObject(event.currentPage - 1, event.pageSize);
			}
		});

		CRUDTreeTableUtils.configReports(tabla, MessageUtil.unescape(AbosMessages.get().NAME_CONSULT_OBJECT_LOAN), searchC);

		TreeTableColumn columns[] = { new TreeTableColumn(20, 0, "getTitle"), new TreeTableColumn(20, 1, "getAuthor"), new TreeTableColumn(20, 2, "getInventorynumber"), new TreeTableColumn(20, 3, "getRecordType.getNomenclatorName"),
				new TreeTableColumn(20, 4, "getLoanObjectState.getNomenclatorName") };

		tabla.createTable(columns);
		tabla.setPageSize(10);
	
		l10n();
		return parent;

	}
    
	public void searchLoanObject(int page, int size) {
		tabla.clearRows();
		Page<LoanObject> listLoanObject = ((AllManagementLoanUserViewController) controller)
				.getManageObject().findAllLoanObject(title, author,
						record_type_id, loan_object_state, inventory_number,
						control_number, dateRegister, endDateRegister,
						loan_object_rooms,library, page, size, size, orderByString);
		tabla.setTotalElements((int) listLoanObject.getTotalElements());
		finalList = listLoanObject.getContent();
		if (finalList.size() > 0) {
			tabla.setRows(finalList);
			tabla.refresh();
		}     
		
		refresh();
	}

	@Override
	public String getID() {
		return "consultLoanObjectID";
	}

	@Override
	public boolean canClose() {
		return false;
	}

	@Override
	public void l10n() {
		lbConsultLoanObject.setText(MessageUtil.unescape(AbosMessages.get().NAME_CONSULT_OBJECT_LOAN));
		lbRangeDate.setText(AbosMessages.get().LABEL_DATE_OF_REGISTRATION_RANGE);
		lbSearchCriteria.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SEARCH_CRITERIA));
		lbTitle.setText(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE));
		lbAuthor.setText(AbosMessages.get().LABEL_AUTHOR);
		lbControlNumber.setText(AbosMessages.get().LABEL_CONTROL_NUMBER);
		lbObjectType.setText(AbosMessages.get().LABEL_OBJECT_TYPE);
		link.setText("<a>" + MessageUtil.unescape(AbosMessages.get().LABEL_ADVANCED_SEARCH) + "</a>");
		btnConsult.setText(AbosMessages.get().BUTTON_CONSULT);
		btnNewSearch.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_NEW_SEARCH));
		lbStateObject.setText(AbosMessages.get().LABEL_STATE_OF_OBJECT);
		lbLocation.setText(MessageUtil.unescape(AbosMessages.get().LABEL_LOCATION));
		lbFrom.setText(AbosMessages.get().LABEL_FROM);
		lbUp.setText(AbosMessages.get().LABEL_UP);
		lbCoincidenceList.setText(AbosMessages.get().LABEL_COINCIDENCE_LIST);
		lbInventaryNumber.setText(MessageUtil.unescape(AbosMessages.get().LABEL_INVENTORY_NUMBER));

		tabla.setActionButtonText("exportPDFButton", AbosMessages.get().BUTTON_EXPORT_TO_PDF);
		tabla.setActionButtonText("exportExcelButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));
		tabla.setCancelButtonText(AbosMessages.get().BUTTON_CLOSE);
		tabla.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE), AbosMessages.get().LABEL_AUTHOR, MessageUtil.unescape(AbosMessages.get().LABEL_INVENTORY_NUMBER), AbosMessages.get().LABEL_OBJECT_TYPE, AbosMessages.get().LABEL_STATE));

		tabla.l10n();

		initialize(comboObjectType, ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByNomenclator(library.getLibraryID(), Nomenclator.LOANOBJECT_TYPE));
		initialize(comboObjectState, ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByNomenclator(library.getLibraryID(), Nomenclator.LOANOBJECT_STATE));
		initialize(comboLocation, ((AllManagementLoanUserViewController) controller).getManageLoanUser().findRoomByLibrary(library.getLibraryID()));

		busquedaB.getParent().layout(true, true);
		busquedaB.getParent().redraw();
		busquedaB.getParent().update();

		refresh();
	}

	@Override
	public void setViewController(ViewController controller) {
		super.setViewController(controller);
	}

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

	public Composite getBusquedaB() {
		return busquedaB;
	}

	public void setBusquedaB(Composite busquedaB) {
		this.busquedaB = busquedaB;
	}

	public Composite getCompoSearchA() {
		return compoSearchA;
	}

	public void setCompoSearchA(Composite compoSearchA) {
		this.compoSearchA = compoSearchA;
	}

}
