package cu.uci.abcd.circulation.ui.auxiliary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
import cu.uci.abcd.domain.common.Person;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.util.Auxiliary;
import cu.uci.abcd.management.security.IPersonService;
import cu.uci.abos.core.ui.FragmentPage;
import cu.uci.abos.core.ui.Percent;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.ServiceProviderUtil;
import cu.uci.abos.core.widget.compoundgroup.CompoundGroup;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.Column;
import cu.uci.abos.core.widget.grid.TreeColumnListener;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

public class AssociatePersonFragment extends FragmentPage{

	private Composite parent;
	private Text searchText;
	private Button searchButton;
	private CRUDTreeTable tableLoanUser;
	private Group loanUserGroup;
	private Person person;
	private List<Control> grupControls = new ArrayList<Control>();
	private List<String> leftListLoanUser = new ArrayList<String>();

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	int direction = 1024;
	private String orderByString = "firstName";
	private Label representDataLabel1;
	private List<String> rigthListLoanUser;
	private Composite personData;

	private Button unassociate;

	private IPersonService personService;

	private Label nameRegister;
	private String textRegister;
	private Composite compoButtons;
	private TreeColumnListener treeColumnListener;
	private Composite composite;
	private String searchTextConsult;
	private Label lbRegisterLoanUser;
	private Label lbAssociatePerson;
	private String lastStringLoanUser;
	private int dimension;
	private Composite parentSMS;
	private Label separatorHeader;
	
	public String getTextRegister() {
		return textRegister;
	}

	public void setTextRegister(String textRegister) {
		this.textRegister = textRegister;
	}

	public AssociatePersonFragment(Person person) {
		this.person = person;
		this.personService = ServiceProviderUtil.getService(IPersonService.class);
	}

	public AssociatePersonFragment(Person person, Composite composite, Composite compoButtons, int dimension, Composite parentSMS) {
		this.person = person;
		this.personService = ServiceProviderUtil.getService(IPersonService.class);
		this.composite = composite;
		this.compoButtons = compoButtons;
		this.dimension = dimension;
		this.parentSMS = parentSMS;
	}

	// FIXME METODO COMPLEJO
	@Override
	public Control createUIControl(Composite shell) {
		addComposite(shell);

		setDimension(dimension);

		parent = new Composite(shell, SWT.NORMAL);
		addComposite(parent);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");

		lbRegisterLoanUser = new Label(parent, SWT.NONE);
		addHeader(lbRegisterLoanUser);		

		separatorHeader = new Label(parent,  SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separatorHeader);
	
		lbAssociatePerson = new Label(parent, SWT.NONE);
		add(lbAssociatePerson, Percent.W40);
		FormDatas.attach(lbAssociatePerson).atTopTo(separatorHeader,15).atLeft(20);
		
		reset();

		representDataLabel1 = new Label(parent, SWT.NONE);
		add(representDataLabel1);
		FormDatas.attach(representDataLabel1).atTopTo(lbAssociatePerson, 18).atLeft(20);
		representDataLabel1.setText(MessageUtil.unescape(AbosMessages.get().LABEL_MESSAGE_SANCION));
    
		searchText = new Text(parent, 0);
		add(searchText);
		FormDatas.attach(searchText).atTopTo(lbAssociatePerson, 15).atLeftTo(representDataLabel1, 10).withHeight(10).withWidth(280);

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
		
		searchText.setMessage(MessageUtil.unescape(AbosMessages.get().LABEL_NAME) + " | " + MessageUtil.unescape(AbosMessages.get().LABEL_SURNAME) + " | " + MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));
		lbRegisterLoanUser.setText(MessageUtil.unescape(cu.uci.abcd.circulation.l10n.AbosMessages.get().NAME_UI_REGISTER_LOAN_USER));
		lbAssociatePerson.setText(MessageUtil.unescape(AbosMessages.get().MSJE_ASSOCIATE_LOANUSER));

		searchButton = new Button(parent, SWT.NONE);
		FormDatas.attach(searchButton).atLeftTo(searchText, 10).atTopTo(lbAssociatePerson, 15).withHeight(22);
		searchButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_SEARCH));
		
		reset();

		tableLoanUser = new CRUDTreeTable(parent, SWT.NONE);
		add(tableLoanUser);
		tableLoanUser.setEntityClass(Person.class);
		tableLoanUser.setVisible(false);

		tableLoanUser.addListener(SWT.Resize, new Listener() {			
			private static final long serialVersionUID = -2654809274899010553L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();
			}
		});
		
		tableLoanUser.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().TABLE_NAME_AND_LAST_NAME), MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION), MessageUtil.unescape(AbosMessages.get().LABEL_SEX)));

		Column column = new Column("associate", parent.getDisplay(), treeColumnListener);
		column.setToolTipText(AbosMessages.get().BUTTON_ASSOCIATE);
		column.setAlignment(SWT.CENTER);
		
		tableLoanUser.addActionColumn(column);

		TreeTableColumn columns[] = { new TreeTableColumn(60, 0, "getFullName"), new TreeTableColumn(20, 1, "getDNI"), new TreeTableColumn(20, 2, "getSex.getNomenclatorDescription") };

		tableLoanUser.createTable(columns);
		tableLoanUser.setPageSize(10);
		
		tableLoanUser.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {
				searchPerson(event.currentPage - 1, event.pageSize);
			//	tableLoanUser.getPaginator().goToFirstPage();
			//	tableLoanUser.notifyListeners(SWT.Resize, new Event());
				refresh();
			}
		});

		searchButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -8115718469128343452L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				tableLoanUser.setVisible(true);
				tableLoanUser.clearRows();
				searchTextConsult = (searchText.getText() != "") ? searchText.getText().replaceAll(" +", " ").trim() : null;
				orderByString = "firstName";
				direction = 1024;
				searchPerson(0, tableLoanUser.getPageSize());
				if (tableLoanUser.getRows().isEmpty()) {
					tableLoanUser.setVisible(false);
					RetroalimentationUtils.showInformationMessage(parentSMS, cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NO_COINCIDENCES_FOUND);
				} else
					tableLoanUser.getPaginator().goToFirstPage();
				
				refresh();
			}
		});

		Label espacio = new Label(parent, SWT.NORMAL);
		addSeparator(espacio);

		createComponent();

		l10n(); 
		
		/**
		 * Se quito lo del foco.
		 */

		return shell;
	}

	@Override
	public void l10n() {
		searchText.setMessage(MessageUtil.unescape(AbosMessages.get().LABEL_NAME) + " | " + MessageUtil.unescape(AbosMessages.get().LABEL_SURNAME) + " | " + MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));
		lbRegisterLoanUser.setText(MessageUtil.unescape(cu.uci.abcd.circulation.l10n.AbosMessages.get().NAME_UI_REGISTER_LOAN_USER));
		lbAssociatePerson.setText(MessageUtil.unescape(AbosMessages.get().MSJE_ASSOCIATE_LOANUSER));

		searchButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_SEARCH));
		tableLoanUser.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().TABLE_NAME_AND_LAST_NAME), MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION), MessageUtil.unescape(AbosMessages.get().LABEL_SEX)));

		representDataLabel1.setText(MessageUtil.unescape(AbosMessages.get().LABEL_MESSAGE_SANCION));
		unassociate.setText(AbosMessages.get().BUTTON_DISSOCIATE);

		lastStringLoanUser = MessageUtil.unescape(AbosMessages.get().LABEL_DETAILS_OF_THE_PERSON);
		leftListLoanUser.clear();
		leftListLoanUser.add(AbosMessages.get().TABLE_NAME_AND_LAST_NAME);
		leftListLoanUser.add(AbosMessages.get().LABEL_USER);
		leftListLoanUser.add(AbosMessages.get().LABEL_BIRTHDATE);
		leftListLoanUser.add(AbosMessages.get().LABEL_SEX);
		leftListLoanUser.add(MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));

		loanUserGroup.setText(lastStringLoanUser);
		CompoundGroup.l10n(grupControls, leftListLoanUser);

		refresh();
	}

	@Override
	public Control getControl(String arg0) {
		// FIXME DEBE RETORNAR UN CONTROL
		return null;
	}

	public void showDataLoanUser(Person person) {
		if (person != null) {
			cleanComponent();
			createComponent();

			tableLoanUser.setVisible(false);
			unassociate.setVisible(true);
			loanUserGroup.setVisible(true);
			showOrHidePersonData(true);
			showOrHideSearchAndIndications(false);

			lastStringLoanUser = MessageUtil.unescape(AbosMessages.get().LABEL_DETAILS_OF_THE_PERSON);

			User user = person.getUser();

			leftListLoanUser = new LinkedList<>();
			leftListLoanUser.add(AbosMessages.get().TABLE_NAME_AND_LAST_NAME);
			leftListLoanUser.add(AbosMessages.get().LABEL_USER);
			leftListLoanUser.add(AbosMessages.get().LABEL_BIRTHDATE);
			leftListLoanUser.add(AbosMessages.get().LABEL_SEX);
			leftListLoanUser.add(MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));

			rigthListLoanUser = new LinkedList<>();
			rigthListLoanUser.add(person.getFullName());

			if (user != null) {
				rigthListLoanUser.add(user.getUsername());
			} else
				rigthListLoanUser.add(" - ");

			rigthListLoanUser.add(Auxiliary.FormatDate(person.getBirthDate()));
			rigthListLoanUser.add(person.getSex().getNomenclatorName());
			rigthListLoanUser.add(person.getDNI());

			grupControls = CompoundGroup.printGroup(person.getPhoto().getImage(), loanUserGroup, lastStringLoanUser, leftListLoanUser, rigthListLoanUser);

			ajustRezise(parent, 220);
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
		lbRegisterLoanUser.setVisible(visible);
		lbAssociatePerson.setVisible(visible);
		separatorHeader.setVisible(visible);
		searchText.setVisible(visible);
		searchButton.setVisible(visible);
		tableLoanUser.setVisible(visible);

	}

	// FIXME FALTA VISIBILIDAD DE ATRIBUTOS Y METODOS
	Page<Person> list = null;

	public void searchPerson(int page, int size) {
		tableLoanUser.clearRows();
		list = null;
		Library library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
		list = personService.findAll(library, searchTextConsult, page, size, size, orderByString);
		tableLoanUser.setTotalElements((int) list.getTotalElements());
		tableLoanUser.setRows(list.getContent());
		tableLoanUser.refresh();
	}

	// FIXME METODO COMPLEJO
	public void createComponent() {

		personData = new Composite(parent, SWT.NORMAL);
		addComposite(personData);
		personData.setData(RWT.CUSTOM_VARIANT, "gray_background");
		personData.setVisible(false);

		// Gruop de Usuario de Prestamo
		loanUserGroup = new Group(personData, SWT.NORMAL);
		//add(loanUserGroup);
		FormDatas.attach(loanUserGroup).atTop().atLeft(15).atBottom().withWidth(500);
		loanUserGroup.setVisible(false);

		unassociate = new Button(personData, SWT.NONE);
		FormDatas.attach(unassociate).atLeftTo(loanUserGroup, 10).atTopTo(personData, 180).withHeight(22);
		unassociate.setText(AbosMessages.get().BUTTON_DISSOCIATE);

		unassociate.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -8115718469128343452L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				person = null;
				// tableLoanUser.clearRows();
				tableLoanUser.setVisible(true);
				showOrHidePersonData(false);
				unassociate.setVisible(false);
				showOrHideSearchAndIndications(true);

				ajustRezise(parent, 1000);

				refresh();

				if (composite != null) {
					composite.setVisible(false);
					compoButtons.setVisible(false);
				}		
				
			}
		});

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

	public Composite getParentSMS() {
		return parentSMS;
	}

	public void setParentSMS(Composite parentSMS) {
		this.parentSMS = parentSMS;
	}

}
