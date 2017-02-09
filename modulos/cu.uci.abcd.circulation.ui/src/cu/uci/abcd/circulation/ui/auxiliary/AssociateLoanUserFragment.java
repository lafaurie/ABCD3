package cu.uci.abcd.circulation.ui.auxiliary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.springframework.data.domain.Page;

import cu.uci.abcd.circulation.l10n.AbosMessages;
import cu.uci.abcd.circulation.ui.RegisterLoan;
import cu.uci.abcd.circulation.ui.RegisterRenew;
import cu.uci.abcd.circulation.ui.RegisterReturn;
import cu.uci.abcd.circulation.ui.RegisterSanction;
import cu.uci.abcd.circulation.ui.controller.AllManagementLoanUserViewController;
import cu.uci.abcd.domain.circulation.LoanUser;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.FragmentPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.widget.compoundgroup.CompoundGroup;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.Column;
import cu.uci.abos.core.widget.grid.TreeColumnListener;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;
   
public class AssociateLoanUserFragment extends FragmentPage {
	   
	private Composite parent;
	private Text searchText;
	private Button searchButton;
	private CRUDTreeTable tableLoanUser;
	private Group loanUserGroup;
	private LoanUser loanUser;
	private int direction = 1024;
	private String orderByString = "person.firstName";
	private Label representDataLabel1;
	private List<String> rigthListLoanUser;
	private Composite personData;
	private Button unassociate;
	private String loan_fistname = null;
	private Composite registerLoanUser;
	private Composite registerPenalty;
	public RegisterRenew registerRenew;
	public RegisterReturn registerReturn;
	private Label nameRegister;
	private String textRegister;
	private Composite compoButtons;
	private TreeColumnListener treeColumnListener;
	private String lastStringLoanUser;
	private List<String> leftListLoanUser= new ArrayList<String>();
	private int dimension;
	private boolean loanUserTypeInterLibrarian;
	private Page<LoanUser> listLoanUser;
	private Map<String, Control> controlsMaps;
	private RegisterLoan registerLoan;
	private boolean prueba = true;
	private Button rdbInterB;
	private List<Control> grupControls  = new ArrayList<Control>();
	private Label separatorHeader;
	private RegisterSanction registerSanction;
	private ViewController controller;
	private Library library;
	
	public String getTextRegister() {
		return textRegister;
	}

	public void setTextRegister(String textRegister) {
		this.textRegister = textRegister;
	}

	public AssociateLoanUserFragment(ViewController controller) {
		this.controller = controller;
	}
	
	int temp;
	/**
	 * 
	 * @param controller
	 * @param loanUser
	 * @param registerLoanUser
	 * @param temp
	 * @param dimension
	 * @param registerSantion
	 */
	public AssociateLoanUserFragment(ViewController controller, LoanUser loanUser,  Composite registerLoanUser,int temp,int dimension, RegisterSanction registerSanction) {
		this.controller = controller;
		this.loanUser = loanUser;
		this.textRegister = "";
		this.registerLoanUser = registerLoanUser;
		this.temp = temp;
		this.dimension = dimension;
		this.registerSanction = registerSanction;
	}
	
	public AssociateLoanUserFragment(ViewController controller, LoanUser loanUser,  Composite registerLoanUser,int temp,int dimension) {
		this.controller = controller;
		this.loanUser = loanUser;
		this.textRegister = "";
		this.registerLoanUser = registerLoanUser;
		this.temp = temp;
		this.dimension = dimension;
	}
	//int interB;
	public AssociateLoanUserFragment(ViewController controller, LoanUser loanUser,  Composite registerLoanUser,int temp,int dimension, Button rdbInterB) {
		this.controller = controller;
		this.loanUser = loanUser;
		this.textRegister = "";
		this.registerLoanUser = registerLoanUser;
		this.temp = temp;
		this.dimension = dimension;
		this.rdbInterB = rdbInterB;
		
	}
	
	public AssociateLoanUserFragment(ViewController controller, LoanUser loanUser,  Composite registerLoanUser,int temp,Composite compoButtons, int dimension) {
		this.controller = controller;
		this.loanUser = loanUser;
		this.textRegister = "";
		this.registerLoanUser = registerLoanUser;
		this.temp = temp;
		this.compoButtons = compoButtons;
		this.dimension = dimension;
	}
	
		
	public AssociateLoanUserFragment(ViewController controller, LoanUser loanUser, Composite registerLoanUser,Composite registerPenalty) {
		this.controller = controller;
		this.loanUser = loanUser;
		this.textRegister = "";
		this.registerLoanUser = registerLoanUser;
		this.registerPenalty = registerPenalty;
	}

	@Override
	public Control createUIControl(Composite shell) {
		library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
		
		addComposite(shell);
		setDimension(dimension);
		parent = new Composite(shell, SWT.NORMAL);
		addComposite(parent);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		
		prueba = loanUserTypeInterLibrarian;
		   
		if (temp == 0) {
			
			nameRegister = new Label(parent, SWT.NONE);
			addHeader(nameRegister);
			

			separatorHeader = new Label(parent,  SWT.SEPARATOR | SWT.HORIZONTAL);
			addSeparator(separatorHeader);
		
		}    
		    		
		representDataLabel1 = new Label(parent, SWT.NONE);		
		FormDatas.attach(representDataLabel1).atLeft(10).atTopTo(separatorHeader, 19);    
       
		searchText = new Text(parent, 0);
		FormDatas.attach(searchText).atLeftTo(representDataLabel1, 10).atTopTo(separatorHeader, 15).withHeight(10).withWidth(280);
		   
		searchButton = new Button(parent, SWT.NONE);
		FormDatas.attach(searchButton).atLeftTo(searchText, 10).atTopTo(separatorHeader, 15).withHeight(22);
		
		reset();
		
		searchText.addFocusListener(new FocusListener() {
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
		
		tableLoanUser = new CRUDTreeTable(parent, SWT.NONE);
		tableLoanUser.setEntityClass(LoanUser.class);
		tableLoanUser.setVisible(false);
		
		tableLoanUser.addListener(SWT.Resize, new Listener() {			
			private static final long serialVersionUID = -2654809274899010553L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();
			}
		});

		Column column = new Column("associate", parent.getDisplay(), treeColumnListener);
		column.setToolTipText(AbosMessages.get().BUTTON_ASSOCIATE);
		column.setAlignment(SWT.CENTER);
	
		tableLoanUser.addActionColumn(column);
		

		TreeTableColumn columns[] = {
				new TreeTableColumn(25, 0, "fullName"),
				new TreeTableColumn(25, 1, "getLoanUserCode"),
				new TreeTableColumn(25, 2, "getLoanUserType.getNomenclatorName"),
				new TreeTableColumn(25, 3,
						"getLoanUserState.getNomenclatorName") };
     
		tableLoanUser.createTable(columns);
		tableLoanUser.setPageSize(10);
		
		FormDatas.attach(tableLoanUser).atLeft(10).atTopTo(searchButton, 15).atRight(10);
		
		
		tableLoanUser.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {
				searchLoanUser(event.currentPage - 1, event.pageSize);
			}
		});

		searchButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -8115718469128343452L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				tableLoanUser.setVisible(true);
				tableLoanUser.clearRows();
				loan_fistname = searchText.getText().replaceAll(" +", " ").trim();
				orderByString = "loanUserCode";
				direction = 1024;     
				searchLoanUser(0, tableLoanUser.getPageSize());				
				if (tableLoanUser.getRows().isEmpty()) {
					tableLoanUser.setVisible(false);
					RetroalimentationUtils.showInformationMessage( cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NO_COINCIDENCES_FOUND);
				} else 
					tableLoanUser.getPaginator().goToFirstPage();

			}
		});

		Label espacio = new Label(parent, SWT.NORMAL);
		addSeparator(espacio);

		createComponent();
		
		//Display.getCurrent().getActiveShell().setDefaultButton(searchButton);
		
		l10n();	
		return shell;
	}

	@Override
	public String getID() {
		return null;
	}

	@Override
	public void l10n() {
		searchText.setMessage(MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION) + " | " + AbosMessages.get().LABEL_NAME + " | " + MessageUtil.unescape(AbosMessages.get().LABEL_CODE));

		searchButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_SEARCH));
		tableLoanUser.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().TABLE_NAME_AND_LAST_NAME), MessageUtil.unescape(AbosMessages.get().LABEL_USER_CODE), MessageUtil.unescape(AbosMessages.get().LABEL_TYPE_OF_USER), MessageUtil.unescape(AbosMessages.get().LABEL_STATE)));

		representDataLabel1.setText(MessageUtil.unescape(AbosMessages.get().LABEL_MESSAGE_USER_LOAN));
		unassociate.setText(AbosMessages.get().BUTTON_DISSOCIATE);

		if (temp == 0) {
			nameRegister.setText(textRegister);
		}		
		
		leftListLoanUser.clear();	
		leftListLoanUser.add(AbosMessages.get().TABLE_NAME_AND_LAST_NAME);
		leftListLoanUser.add(MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));
		leftListLoanUser.add(AbosMessages.get().LABEL_USER);
		leftListLoanUser.add(MessageUtil.unescape(AbosMessages.get().LABEL_USER_CODE));
		leftListLoanUser.add(AbosMessages.get().LABEL_TYPE_OF_USER);
		leftListLoanUser.add(AbosMessages.get().LABEL_STATE);
		leftListLoanUser.add(MessageUtil.unescape(AbosMessages.get().LABEL_VALID_UNTIL));

		lastStringLoanUser = MessageUtil.unescape(AbosMessages.get().LABEL_USER_LOAN_DATA);
		
		loanUserGroup.setText(lastStringLoanUser);
		CompoundGroup.l10n(grupControls, leftListLoanUser);			    
		
		refresh();
		
	}

	@Override
	public Control getControl(String arg0) {
		return null;
	}

	public void showDataLoanUser(LoanUser loanUser) {
		if (loanUser != null) {
			cleanComponent();
			createComponent();
			
			tableLoanUser.setVisible(false);
			unassociate.setVisible(true);
			loanUserGroup.setVisible(true);
			showOrHidePersonData(true);
			showOrHideSearchAndIndications(false);
		
			User user = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findUserByPersonID(loanUser.getPersonID());

			lastStringLoanUser = MessageUtil.unescape(AbosMessages.get().LABEL_USER_LOAN_DATA);
			
			leftListLoanUser = new LinkedList<>();
			leftListLoanUser.add(AbosMessages.get().TABLE_NAME_AND_LAST_NAME);
			leftListLoanUser.add(MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));
			leftListLoanUser.add(AbosMessages.get().LABEL_USER);
			leftListLoanUser.add(MessageUtil.unescape(AbosMessages.get().LABEL_USER_CODE));
			leftListLoanUser.add(AbosMessages.get().LABEL_TYPE_OF_USER);
			leftListLoanUser.add(AbosMessages.get().LABEL_STATE);
			leftListLoanUser.add(MessageUtil.unescape(AbosMessages.get().LABEL_VALID_UNTIL));

			rigthListLoanUser = new LinkedList<>();
			rigthListLoanUser.add(loanUser.fullName());
			rigthListLoanUser.add(loanUser.getDNI());
			if (user != null) {
				rigthListLoanUser.add(user.getUsername());
			} else
				rigthListLoanUser.add(" - ");

			rigthListLoanUser.add(loanUser.getLoanUserCode());
			rigthListLoanUser.add(loanUser.getLoanUserType().getNomenclatorName());
			rigthListLoanUser.add(loanUser.getLoanUserState().getNomenclatorName());
			rigthListLoanUser.add(Auxiliary.FormatDate(loanUser.getExpirationDate()));

			grupControls= CompoundGroup.printGroup(loanUser.getPerson().getPhoto().getImage(), loanUserGroup, lastStringLoanUser, leftListLoanUser, rigthListLoanUser);
			
			ajustRezise(parent, 280);			
			l10n();	
		}
	}

	public void ajustRezise(Composite parent, int height) {
		parent.setSize(parent.getSize().x, height);
		FormData tempo = (FormData) parent.getLayoutData();
		tempo.height = height;
		parent.setLayoutData(tempo);
		refresh();

	}

	/*public void refresh() {
		Composite parentRezize = parent.getParent().getParent().getParent().getParent().getParent().getParent().getParent();
		parentRezize.layout(true, true);
		parentRezize.redraw();
		parentRezize.update();
	}*/

	public void showOrHidePersonData(boolean visible) {
		personData.setVisible(visible);

	}

	public void showOrHideSearchAndIndications(boolean visible) {
		representDataLabel1.setVisible(visible);
		searchText.setVisible(visible);
		searchButton.setVisible(visible);
		tableLoanUser.setVisible(visible);
		if (temp == 0) {
			nameRegister.setVisible(visible);
			separatorHeader.setVisible(visible);
		}
		
	}

	public void searchLoanUser(int page, int size) {
		tableLoanUser.clearRows();
		if (temp == 1) {

			if (rdbInterB.getSelection())
				listLoanUser = ((AllManagementLoanUserViewController) controller)
						.getManageLoanUser()
						.findLoanUserFragmentInterLibrarian(loan_fistname,library,
								page, size, direction, orderByString);
			else
				listLoanUser = ((AllManagementLoanUserViewController) controller)
						.getManageLoanUser().findLoanUserFragmentOtherType(
								loan_fistname,library, page, size, direction,
								orderByString);
		} else if (temp == 0) {
			listLoanUser = ((AllManagementLoanUserViewController) controller)
					.getManageLoanUser().findLoanUserFragment(loan_fistname,library,
							page, size, direction, orderByString);
		}

		tableLoanUser.setTotalElements((int) listLoanUser.getTotalElements());
		tableLoanUser.setRows(listLoanUser.getContent());
		tableLoanUser.refresh();
		if (registerSanction != null)
			registerSanction.refresh();

		refresh();
	}

	public LoanUser getLoanUser() {
		return loanUser;
	}

	public void setLoanUser(LoanUser loanUser) {
		this.loanUser = loanUser;
	}

	public void createComponent() {

		personData = new Composite(parent, SWT.NORMAL);
		addComposite(personData);
		personData.setData(RWT.CUSTOM_VARIANT, "gray_background");
		personData.setVisible(false);
          
		// Gruop de Usuario de Prestamo
		loanUserGroup = new Group(personData, SWT.NORMAL);
		FormDatas.attach(loanUserGroup).atTop().atLeft(15).atBottom().withWidth(500);
		loanUserGroup.setVisible(false);

		unassociate = new Button(personData, SWT.NONE);
		FormDatas.attach(unassociate).atTopTo(personData, 233).withHeight(23).atLeftTo(loanUserGroup, 10);

		unassociate.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -8115718469128343452L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				loanUser = null;
				tableLoanUser.setVisible(true);
				showOrHidePersonData(false);
				unassociate.setVisible(false);
				showOrHideSearchAndIndications(true);
				if (temp == 0) {
					nameRegister.setVisible(true);
				}
				
				ajustRezise(parent, 500);

				refresh();
				registerLoanUser.setVisible(false);
				if (compoButtons!= null) {
					compoButtons.setVisible(false);
				}
				
				if (registerPenalty != null) {
					registerPenalty.setVisible(false);
				}
				rigthListLoanUser.clear();

			}
		});
		
		if(registerSanction != null)
			registerSanction.refresh();

	}

	public void cleanComponent() {
		try {
			Control[] temp = personData.getChildren();
			for (int i = 0; i < temp.length; i++)
				temp[i].dispose();

		} catch (Exception e) {
		
		}
	}   

	public Label getNameRegister() {
		return nameRegister;
	}

	public void setNameRegister(Label nameRegister) {
		this.nameRegister = nameRegister;
	}

	public TreeColumnListener getTreeColumnListener() {
		return treeColumnListener;
	}

	public void setTreeColumnListener(TreeColumnListener treeColumnListener) {
		this.treeColumnListener = treeColumnListener;
	}	
}