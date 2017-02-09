package cu.uci.abcd.circulation.ui.auxiliary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import cu.uci.abcd.circulation.l10n.AbosMessages;
import cu.uci.abcd.circulation.ui.RegisterLoan;
import cu.uci.abcd.circulation.ui.controller.AllManagementLoanUserViewController;
import cu.uci.abcd.circulation.ui.model.ViewAreaTransactions;
import cu.uci.abcd.domain.circulation.Transaction;
import cu.uci.abcd.domain.common.User;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.FormPagePainter;
import cu.uci.abos.core.ui.FragmentPage;
import cu.uci.abos.core.ui.PagePainter;
import cu.uci.abos.core.ui.Percent;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.widget.compoundgroup.CompoundGroup;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

public class ViewTransactionsFragment extends FragmentPage {
	private ViewController controller;
	private Transaction transaction;
	private PagePainter painter;
	
	private Button btnNew;
	private Button btnClose;
	
	private List<Transaction> listTransactions;
	private Group loanUserGroup;
	private Group transactionGroup;

	private Composite registerLoan;
	private Composite compoParent;
	private Composite registerRenew;
	private Label lbViewTransaction;
	private int dimension;
	private RegisterLoan registerLoanClass;
	private ContributorService contributorService;
	private CRUDTreeTable tableLoanObject;
	private Label lbLoanObject;
	
	private List<Control> grupControlsLoanUser = new ArrayList<>();
	private List<Control> grupControlsTransaction = new ArrayList<>();
	
	private List<String> leftListLoanUser = new LinkedList<>();
	private List<String> leftListTransaction = new LinkedList<>();
	
	private String lastStringTransaction;
	private String lastStringLoanUser;
	
	public ViewTransactionsFragment(ViewController controller,
			List<Transaction> listTransactions,
			Composite registerLoan,int dimension, RegisterLoan registerLoanClass,ContributorService contributorService) {
		this.setController(controller);
		this.setListTransactions(listTransactions);
		this.setRegisterLoan(registerLoan);
		this.dimension = dimension;
		this.registerLoanClass = registerLoanClass;
		this.contributorService = contributorService;
	}

	public ViewTransactionsFragment(ViewController controller,
			List<Transaction> listTransactions,	int dimension) {
		this.setController(controller);
		this.setListTransactions(listTransactions);
		this.dimension = dimension;		
	}

	
	public ViewTransactionsFragment(ViewController controller) {
		this.setController(controller);
	}

	public ViewTransactionsFragment(ViewController controller,
			Transaction transaction) {
		this.setController(controller);
		this.setTransaction(transaction);
	}

	
//FIXME METODO COMPLEJO
	@Override
	public Control createUIControl(Composite parent) {
		
		painter = new FormPagePainter();
		painter.setDimension(dimension);
		painter.addComposite(parent);

		compoParent = new Composite(parent, SWT.NORMAL);
		painter.addComposite(compoParent);
		compoParent.setData(RWT.CUSTOM_VARIANT, "gray_background");

		lbViewTransaction = new Label(compoParent, SWT.NONE);
		painter.addHeader(lbViewTransaction);
		
		Label separator = new Label(compoParent,  SWT.SEPARATOR | SWT.HORIZONTAL);
		painter.addSeparator(separator);
		
		lastStringLoanUser = MessageUtil
				.unescape(AbosMessages.get().LABEL_USER_LOAN_DATA);
		loanUserGroup = new Group(compoParent, SWT.NORMAL);
		painter.add(loanUserGroup);

		leftListLoanUser = new LinkedList<>();
		leftListLoanUser.add(AbosMessages.get().TABLE_NAME_AND_LAST_NAME);
		leftListLoanUser.add(MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));
		leftListLoanUser.add(AbosMessages.get().LABEL_USER);
		leftListLoanUser.add(MessageUtil.unescape(AbosMessages.get().LABEL_USER_CODE));
		leftListLoanUser.add(AbosMessages.get().LABEL_TYPE_OF_USER);
		leftListLoanUser.add(AbosMessages.get().LABEL_STATE);
		leftListLoanUser.add(MessageUtil.unescape(AbosMessages.get().LABEL_VALID_UNTIL));
	
		lastStringTransaction = MessageUtil
				.unescape(AbosMessages.get().LABEL_DATE_OF_TRANSACTION);
		transactionGroup = new Group(compoParent, SWT.NORMAL);
		painter.add(transactionGroup);
	
		leftListTransaction = new LinkedList<>();
		leftListTransaction.add(MessageUtil.unescape(AbosMessages.get().LABEL_TYPE_OF_LOAN));
		leftListTransaction.add(AbosMessages.get().LABEL_STATE);
		leftListTransaction.add(AbosMessages.get().LABEL_OPERATOR);
		
		painter.reset();
		
		User user = ((AllManagementLoanUserViewController) controller)
				.getManageLoanUser().findUserByPersonID(
						listTransactions.get(0).getLoanUser().getPersonID());

		List<String> rigthListLoanUser = new LinkedList<>();
		rigthListLoanUser.add(listTransactions.get(0).getLoanUser().fullName());
		rigthListLoanUser.add(listTransactions.get(0).getLoanUser().getDNI());
		if (user != null) {
			rigthListLoanUser.add(user.getUsername());
		} else
			rigthListLoanUser.add(" - ");
		rigthListLoanUser.add(listTransactions.get(0).getLoanUser().getLoanUserCode());
		rigthListLoanUser.add(listTransactions.get(0).getLoanUser().getLoanUserType()
				.getNomenclatorName());
		rigthListLoanUser.add(listTransactions.get(0).getLoanUser().getLoanUserState()
				.getNomenclatorName());
		rigthListLoanUser.add(cu.uci.abcd.domain.util.Auxiliary.FormatDate(listTransactions.get(0).getLoanUser().getExpirationDate()));
	
		grupControlsLoanUser=CompoundGroup.printGroup(listTransactions.get(0).getLoanUser().getPhoto().getImage(), loanUserGroup, lastStringLoanUser,
				leftListLoanUser, rigthListLoanUser);

		lbLoanObject = new Label(compoParent, SWT.NONE);
		painter.addHeader(lbLoanObject);
		
		tableLoanObject = new CRUDTreeTable(compoParent, SWT.NONE);
		painter.add(tableLoanObject);
		tableLoanObject.setEntityClass(Transaction.class);
		tableLoanObject.setWatch(true, new ViewAreaTransactions(controller));
	
		TreeTableColumn columns[] = {
				new TreeTableColumn(16, 0, "getLoanObject.getTitle"),
				new TreeTableColumn(16, 1, "getLoanObject.getInventorynumber"),
				new TreeTableColumn(16, 2, "getLoanObject.getRecordType.getNomenclatorName"),
				new TreeTableColumn(16, 3, "getLoanObject.getRoom.getHourConvert"),
				new TreeTableColumn(16, 4, "getTransactionDateTime"),
				new TreeTableColumn(16, 5, "getEndTransactionDate"),				
				};

		tableLoanObject.createTable(columns);
		tableLoanObject.setPageSize(10);
	
		tableLoanObject.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {
	
				searchLoanObject(event.currentPage - 1, event.pageSize);

			}
		});
		
		searchLoanObject(0, tableLoanObject.getPageSize());
			
		tableLoanObject.getPaginator().goToFirstPage();
		
		List<String> rigthListTransaction = new LinkedList<>();
		rigthListTransaction.add(listTransactions.get(0).getLoanType()
				.getNomenclatorName());
		rigthListTransaction.add(listTransactions.get(0).getState()
				.getNomenclatorName());
		rigthListTransaction.add(listTransactions.get(0).getLibrarian().getPerson().getFullName());

		grupControlsTransaction=CompoundGroup.printGroup(transactionGroup, lastStringTransaction,
				leftListTransaction, rigthListTransaction);

		painter.reset();
		btnClose = new Button(compoParent, SWT.PUSH);
		painter.add(btnClose);
		
		btnNew = new Button(compoParent, SWT.PUSH);
		painter.add(btnNew);
		
		painter.add(new Label(compoParent, 0),Percent.W100);
		
		if (registerLoan != null) {
			btnClose.setVisible(true);
			btnNew.setVisible(true);
		} else
		{
			btnNew.setVisible(false);
			btnClose.setVisible(false);
		}
		
		l10n();
	

		btnNew.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				registerLoanClass.notifyListeners(SWT.Dispose, new Event()); 
				contributorService.selectContributor("addLoanID");
			}
		});
		
		btnClose.addSelectionListener(new SelectionAdapter() {
		    private static final long serialVersionUID = 2615553092700551346L;

		    @Override
		    public void widgetSelected(SelectionEvent e) {
		     registerLoanClass.notifyListeners(SWT.Dispose, new Event()); 
		     }
		});

		refresh();
		return parent;
	}

	public void searchLoanObject(int page, int size) {
		tableLoanObject.clearRows();
		
		tableLoanObject.setTotalElements(listTransactions.size());
		if (listTransactions.size() <= page * size + size) {
			tableLoanObject.setRows(listTransactions.subList(page * size, listTransactions.size()));
		} else {
			tableLoanObject.setRows(listTransactions.subList(page * size, page * size + size));
		}
	
		tableLoanObject.refresh();
		
		}

	@Override
	public String getID() {
		return null;
	}

	@Override
	public void l10n() {
		lbViewTransaction.setText(MessageUtil.unescape(AbosMessages.get().VIEW_TRANSACTION));
		btnClose.setText(AbosMessages.get().BUTTON_CLOSE);
		btnNew.setText(AbosMessages.get().BUTTON_NEW);
		
		lbLoanObject.setText(MessageUtil.unescape(AbosMessages.get().LABEL_LIST_OF_OBJECT_LOAN));
		tableLoanObject.setColumnHeaders(Arrays.asList(
				MessageUtil.unescape(AbosMessages.get().LABEL_TITLE),
				MessageUtil.unescape(AbosMessages.get().LABEL_INVENTORY_NUMBER),
				AbosMessages.get().LABEL_OBJECT_TYPE,
				MessageUtil.unescape(AbosMessages.get().LABEL_HOUR_RETURN),
				MessageUtil.unescape(AbosMessages.get().LABEL_DATE_LOAN),
				MessageUtil.unescape(AbosMessages.get().LABEL_DATE_RETURN)));
		
		leftListLoanUser.clear();
		leftListLoanUser.add(AbosMessages.get().TABLE_NAME_AND_LAST_NAME);
		leftListLoanUser.add(MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));
		leftListLoanUser.add(AbosMessages.get().LABEL_USER);
		leftListLoanUser.add(MessageUtil.unescape(AbosMessages.get().LABEL_USER_CODE));
		leftListLoanUser.add(AbosMessages.get().LABEL_TYPE_OF_USER);
		leftListLoanUser.add(AbosMessages.get().LABEL_STATE);
		leftListLoanUser.add(MessageUtil.unescape(AbosMessages.get().LABEL_VALID_UNTIL));
		CompoundGroup.l10n(grupControlsLoanUser, leftListLoanUser);
		
		leftListTransaction.clear();
		leftListTransaction.add(MessageUtil.unescape(AbosMessages.get().LABEL_TYPE_OF_LOAN));
		leftListTransaction.add(AbosMessages.get().LABEL_STATE);
		leftListTransaction.add(AbosMessages.get().LABEL_OPERATOR);
		CompoundGroup.l10n(grupControlsTransaction, leftListTransaction);
		
		lastStringLoanUser = MessageUtil
				.unescape(AbosMessages.get().LABEL_USER_LOAN_DATA);
		lastStringTransaction = MessageUtil
				.unescape(AbosMessages.get().LABEL_DATE_OF_TRANSACTION);
		
		loanUserGroup.setText(lastStringLoanUser);
		transactionGroup.setText(lastStringTransaction);
	}

	@Override
	public Control getControl(String arg0) {
		return null;
	}

	public Composite getRegisterLoan() {
		return registerLoan;
	}

	public void setRegisterLoan(Composite registerLoan) {
		this.registerLoan = registerLoan;
	}

	public void refresh() {
		Composite parentRezize = compoParent.getParent().getParent();
		parentRezize.layout(true, true);
		parentRezize.redraw();
		parentRezize.update();
	}

	public Composite getRegisterRenew() {
		return registerRenew;
	}

	public void setRegisterRenew(Composite registerRenew) {
		this.registerRenew = registerRenew;
	}	
	
	public ViewController getController() {
		return controller;
	}

	public void setController(ViewController controller) {
		this.controller = controller;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	public List<Transaction> getListTransactions() {
		return listTransactions;
	}

	public void setListTransactions(List<Transaction> listTransactions) {
		this.listTransactions = listTransactions;
	}	
}