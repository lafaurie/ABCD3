package cu.uci.abcd.circulation.ui;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.springframework.data.domain.Page;

import cu.uci.abcd.circulation.l10n.AbosMessages;
import cu.uci.abcd.circulation.ui.controller.AllManagementLoanUserViewController;
import cu.uci.abcd.circulation.ui.model.ViewAreaTransactions;
import cu.uci.abcd.domain.circulation.Transaction;
import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Room;
import cu.uci.abcd.domain.management.library.Worker;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.Column;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeColumnListener;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

public class RegisterReturn extends ContributorPage implements Contributor {	
	
	private int direction = 1024;
	private String orderByStringTransactions = "transactionID";
	private Label msjeListReturn;
	private CRUDTreeTable tableLoanObjectCRUD;
	private Button btnCancel;
	private Composite registerReturn;
	private List<Room> listWorkerRooms;
	private Library library;
	private int dimension;
	private User user;

	private Label lbRegisterReturn;
	private Label lbsearchTransaction;
	private Text txtSearchTransaction;
	private Button searchButton;
	private String params = null;

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().NAME_UI_RETURN);
	}

	@Override
	public String getID() {
		return "addReturnID";
	}    

	@Override
	// FIXME METODO COMPLEJO
	public Control createUIControl(Composite parent) {
		addComposite(parent);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");

		dimension = parent.getParent().getParent().getParent().getBounds().width;
		          
		Composite resize = new Composite(parent, 0);
		resize.setVisible(false);
		FormDatas.attach(resize).atTop().atRight(0).withWidth(0).withHeight(Display.getCurrent().getBounds().height - 120);

		library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
		user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");
		if (user.getPerson() == null) {			
			RetroalimentationUtils.showInformationMessage(AbosMessages.get().MSG_USER_NOT_WORKER);			
		}
		else{		
		long idPerson = user.getPerson().getPersonID();

		Worker workerLoggin = ((AllManagementLoanUserViewController) controller).getManagePerson().findWorkerbyPersonID(idPerson);
		if (workerLoggin == null) {
			RetroalimentationUtils.showInformationMessage(AbosMessages.get().MSG_USER_NOT_WORKER);
		}
		else{
		listWorkerRooms = workerLoggin.getRooms();
	    
		registerReturn = new Composite(parent, SWT.NONE);
		registerReturn.setData(RWT.CUSTOM_VARIANT, "gray_background");
		addComposite(registerReturn);
    
		lbRegisterReturn = new Label(registerReturn, 0);
		addHeader(lbRegisterReturn);
        
		Label separatorHeader = new Label(registerReturn,  SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separatorHeader);
		
		lbsearchTransaction = new Label(registerReturn, 0);
		FormDatas.attach(lbsearchTransaction).atLeft(10).atTopTo(separatorHeader, 19);    
        
		txtSearchTransaction = new Text(registerReturn, 0);
		FormDatas.attach(txtSearchTransaction).atLeftTo(lbsearchTransaction, 10).atTopTo(separatorHeader, 17).withHeight(10).withWidth(410);

		searchButton = new Button(registerReturn, SWT.PUSH);
		FormDatas.attach(searchButton).atLeftTo(txtSearchTransaction, 10).atTopTo(separatorHeader, 17).withHeight(22);

		txtSearchTransaction.addFocusListener(new FocusListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void focusLost(FocusEvent arg0) {
				searchButton.getShell().setDefaultButton(null);
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				searchButton.getShell().setDefaultButton(searchButton);
			}
		});
		
		br();			
		
		msjeListReturn = new Label(registerReturn, SWT.None);
		addHeader(msjeListReturn);
		FormDatas.attach(msjeListReturn).atLeft(10).atTopTo(searchButton, 17);
		msjeListReturn.setVisible(false);
       
		tableLoanObjectCRUD = new CRUDTreeTable(registerReturn, SWT.NONE);
		add(tableLoanObjectCRUD);
		tableLoanObjectCRUD.setEntityClass(Transaction.class);
		tableLoanObjectCRUD.setPageSize(10);
		tableLoanObjectCRUD.setWatch(true, new ViewAreaTransactions(controller, this));
		tableLoanObjectCRUD.setVisible(false);
		    
		tableLoanObjectCRUD.addListener(SWT.Resize, new Listener() {			
			private static final long serialVersionUID = -2654809274899010553L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();
			}
		});

		Column columnReturn = new Column("return", registerReturn.getDisplay(), new TreeColumnListener() {
			
			public void handleEvent(final TreeColumnEvent event) {
				
				Transaction entity = (Transaction) event.entity.getRow();

								MessageDialogUtil.openConfirm(
										Display.getCurrent().getActiveShell(),
										MessageUtil
												.unescape(cu.uci.abos.core.l10n.AbosMessages
														.get().MESSAGE_QUESTION),
										MessageUtil
												.unescape(AbosMessages.get().MSG_USER_SUCURE_RETURN) + " " +entity.getLoanObject().getTitle() + " " +AbosMessages.get().MSG_USER_SUCURE_ASSOCIATE + " " + entity.getLoanUser().fullName()+ "?",
										new DialogCallback() {
											
				private static final long serialVersionUID = 1L;

					@Override
					public void dialogClosed(int returnCode) {
						if (returnCode == 0) {
							Transaction entity = (Transaction) event.entity.getRow();

							java.util.Date fecha = new java.util.Date();
							Date startDate = new Date(fecha.getTime());

							Nomenclator loanState = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID(Nomenclator.LOAN_STATE_RETURN);
							Nomenclator loanObjectState = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID(Nomenclator.LOANOBJECT_STATE_AVAILABLE);

							Transaction transactionParent = entity;
							transactionParent.setIsparent(true);
							((AllManagementLoanUserViewController) controller).getManageTransaction().addTransaction(transactionParent);

							Transaction transaction = new Transaction();
							transaction.setLoanUser(entity.getLoanUser());
							transaction.setLoanObject(entity.getLoanObject());
							transaction.setLoanType(entity.getLoanType());
							transaction.setState(loanState);
							transaction.setTransactionDateTime(startDate);
							transaction.setParenttransaction(entity);
							transaction.setTransactionDateTime(startDate);
							transaction.setEndTransactionDate(startDate);
							transaction.setIsparent(false);
							transaction.setLibrarian(user);
							if (entity.getReservation() != null) {
								transaction.setReservation(entity.getReservation());
							}

							((AllManagementLoanUserViewController) controller).getManageTransaction().addTransaction(transaction);

							LoanObject loanObject = entity.getLoanObject();
							loanObject.setLoanObjectState(loanObjectState);

							((AllManagementLoanUserViewController) controller).getManageObject().addLoanObject(loanObject);

							event.performDelete = true;

							searchTransaction(0, tableLoanObjectCRUD.getPageSize());
							tableLoanObjectCRUD.getPaginator().goToFirstPage();

							// ---------------Mensaje
							RetroalimentationUtils.showInformationMessage(registerReturn, MessageUtil.unescape(AbosMessages.get().MSJE_RETURN_SUCCESS));
						}
					}
					
				});
						
			}});
		
		
		columnReturn.setToolTipText(AbosMessages.get().BUTTON_RETURN);
		columnReturn.setAlignment(SWT.CENTER);
	
		tableLoanObjectCRUD.addActionColumn(columnReturn);


		TreeTableColumn columns[] = { new TreeTableColumn(16, 0, "getLoanObject.getTitle"), new TreeTableColumn(16, 1, "getLoanObject.getInventorynumber"), new TreeTableColumn(16, 2, "getLoanObject.getRecordType.getNomenclatorName"), new TreeTableColumn(16, 3, "getState.getNomenclatorName"),
				new TreeTableColumn(16, 4, "getLoanUser.fullName"), new TreeTableColumn(16, 5, "getLoanUser.getLoanUserCode"), };

		tableLoanObjectCRUD.createTable(columns);
		
		tableLoanObjectCRUD.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {
	
				searchTransaction(event.currentPage - 1, event.pageSize);
			}
		});

		br();

		searchButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -8115718469128343452L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				msjeListReturn.setVisible(true);
				tableLoanObjectCRUD.setVisible(true);
				btnCancel.setVisible(true);
				tableLoanObjectCRUD.clearRows();

				params = (txtSearchTransaction.getText().length() > 0 ? txtSearchTransaction.getText().replaceAll(" +", " ").trim() : null);

				orderByStringTransactions = "transactionID";
				direction = 1024;
				searchTransaction(0, tableLoanObjectCRUD.getPageSize());
				if (tableLoanObjectCRUD.getRows().isEmpty()) {
					tableLoanObjectCRUD.setVisible(false);
					msjeListReturn.setVisible(false);
					btnCancel.setVisible(false);
					RetroalimentationUtils.showInformationMessage(registerReturn, cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NO_COINCIDENCES_FOUND);
				}  else 
					tableLoanObjectCRUD.getPaginator().goToFirstPage();
				
				refresh();

			}
		});
		btnCancel = new Button(registerReturn, SWT.PUSH);
		add(btnCancel);
		btnCancel.setVisible(false);

		btnCancel.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_QUESTION), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_WARN_CANCEL_OPERATION), new DialogCallback() {
				private static final long serialVersionUID = 1L;
					@Override
					public void dialogClosed(int returnCode) {
						if (returnCode == 0) {						
							RegisterReturn.this.notifyListeners(SWT.Dispose, new Event());				
						}
					}
				});
			}
		});
		
		l10n();
		refresh();
		}}
		return parent;

	}

	@Override
	public boolean canClose() {
		return false;
	}

	@Override
	public void l10n() {

		lbRegisterReturn.setText(MessageUtil.unescape(AbosMessages.get().NAME_UI_REGISTER_RETURN));
		lbsearchTransaction.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SEARCH_TRANSACTION));
		txtSearchTransaction.setMessage(MessageUtil.unescape(AbosMessages.get().LABEL_INVENTORY_NUMBER) + " | " + MessageUtil.unescape(AbosMessages.get().LABEL_USER_CODE) + " | " + MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));

		searchButton.setText(AbosMessages.get().BUTTON_SEARCH);
		msjeListReturn.setText(MessageUtil.unescape(AbosMessages.get().LABEL_LIST_OF_OBJECTS_TO_RETURN));
		tableLoanObjectCRUD.setCancelButtonText(AbosMessages.get().BUTTON_CLOSE);
		btnCancel.setText(AbosMessages.get().BUTTON_CANCEL);

		tableLoanObjectCRUD.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE), MessageUtil.unescape(AbosMessages.get().LABEL_INVENTORY_NUMBER), AbosMessages.get().LABEL_OBJECT_TYPE, AbosMessages.get().LABEL_STATE, AbosMessages.get().TABLE_NAME_AND_LAST_NAME,
				MessageUtil.unescape(AbosMessages.get().LABEL_USER_CODE)));
		
		refresh();
	}

	@Override
	public void setViewController(ViewController controller) {
		super.setViewController(controller);
	}

	public void searchTransaction(int page, int size) {
		tableLoanObjectCRUD.clearRows();

		Page<Transaction> list = ((AllManagementLoanUserViewController) controller)
				.getManageTransaction().findAllTransactionByRegisterReturn(
						params,library,listWorkerRooms, page, size, size, orderByStringTransactions);

		tableLoanObjectCRUD.setRows(list.getContent());
		tableLoanObjectCRUD.setTotalElements((int) list.getTotalElements());
		tableLoanObjectCRUD.refresh();
		
		refresh();
	}	   
	
}