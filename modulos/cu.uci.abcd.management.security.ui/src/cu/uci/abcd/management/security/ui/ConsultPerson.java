package cu.uci.abcd.management.security.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
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

import cu.uci.abcd.domain.common.Person;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.management.security.l10n.AbosMessages;
import cu.uci.abcd.management.security.ui.controller.PersonViewController;
import cu.uci.abcd.management.security.ui.model.PersonUpdateArea;
import cu.uci.abcd.management.security.ui.model.PersonViewArea;
import cu.uci.abcd.management.security.util.Auxiliary;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ViewController;
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

public class ConsultPerson extends ContributorPage implements Contributor {

	private Text firstNameText;
	private Text secondNameText;
	private Text firstSurNameText;
	private Text secondSurnameText;
	private Text identificationText;

	private Label lblHeader;
	private Label firstNameLabel;
	private Label secondNameLabel;
	private Label firstSurnameLabel;
	private Label secondSurnameLabel;
	private Label identificationLabel;
	private Label personList;

	private SecurityCRUDTreeTable tabla;
	int direction = 1024;
	private String orderByString = "firstname";

	private String firstNameConsult = null;
	private String secondNameConsult = null;
	private String firstSurnameConsult = null;
	private String secondSurnameConsult = null;
	private String identificationConsult = null;

	private Button consultButton;
	private Button btnNew;
	private Library library;
	private List<String> searchCriteriaList = new ArrayList<>();

	@Override
	public Control createUIControl(Composite shell) {
		library = (Library) SecurityUtils.getPrincipal().getByKey("library");

		addComposite(shell);
		shell.setData(RWT.CUSTOM_VARIANT, "gray_background");

		Composite scroll = new Composite(shell,SWT.NONE);
		scroll.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(scroll).atTopTo(shell, 25).atRight(0).withWidth(0).withHeight(Display.getCurrent().getBounds().height - 172);
		
		Label consultPersonLabel = new Label(shell, SWT.NONE);
		consultPersonLabel.setText("CONSULTAR PERSONAS");
		addHeader(consultPersonLabel);
		
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
		br();

		btnNew = new Button(shell, SWT.PUSH);
		add(btnNew);

		consultButton = new Button(shell, SWT.PUSH);
		add(consultButton);

		Label separator = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);

		personList = new Label(shell, SWT.NONE);
		addHeader(personList);

		tabla = new SecurityCRUDTreeTable(shell, SWT.NONE);
		add(tabla);
		tabla.setEntityClass(Person.class);
		tabla.setSearch(false);
		tabla.setSaveAll(false);
		tabla.setWatch(true, new PersonViewArea());
		tabla.setUpdate(true, new PersonUpdateArea(super.controller, tabla));
		tabla.setDelete("deletePersonID");
		tabla.setVisible(true);
		tabla.setPageSize(10);
		
		tabla.addListener(SWT.Resize, new Listener() {
            private static final long serialVersionUID = 8817895862824622805L;
            @Override
            public void handleEvent(Event event) {
                    refresh();
            }
           });
		
		CRUDTreeTableUtils.configUpdate(tabla);
		CRUDTreeTableUtils.configReports(tabla, contributorName(), searchCriteriaList);
		CRUDTreeTableUtils.configRemove(tabla, new IActionCommand() {
			@Override
			public void execute(final TreeColumnEvent event) {
				tabla.destroyEditableArea();
				Person person = (Person) event.entity.getRow();
				Long idPerson = person.getPersonID();
				((PersonViewController) controller).deletePerson(idPerson);
				//tabla.getPaginator().goToFirstPage();
			}
		});

		TreeTableColumn columns[] = {
				new TreeTableColumn(50, 0, "getFullName"),
				new TreeTableColumn(20, 1, "getDNI"),
				new TreeTableColumn(15, 1, "getSex.getNomenclatorName"),
				new TreeTableColumn(15, 2, "getAge") };
		tabla.createTable(columns);

		tabla.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {
				if (event.sortData != null) {
					direction = event.sortData.sortDirection;
					switch (event.sortData.columnIndex) {
					case 1:
						orderByString = "firstname";
						break;
					case 2:
						orderByString = "firstname";
						break;
					}
				}
				searchPersons(event.currentPage - 1, event.pageSize);
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
				Auxiliary.showLabelAndTable(personList, tabla, false);
			}

		});

		consultButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				tabla.destroyEditableArea();
				tabla.clearRows();
				firstNameConsult = (firstNameText.getText() != "") ? firstNameText.getText() : null;
				
				
				secondNameConsult = (secondNameText.getText() != "") ? Auxiliary.getValue(secondNameText.getText()) : null;
				
				firstSurnameConsult = (firstSurNameText.getText() != "") ? firstSurNameText.getText() : null;
				
				
				secondSurnameConsult = (secondSurnameText.getText() != "") ? Auxiliary.getValue(secondSurnameText.getText()) : null;
				
				identificationConsult = (identificationText.getText() != "") ? Auxiliary.getValue(identificationText.getText()) : null;
				orderByString = "firstName";
				direction = 1024;
				tabla.getPaginator().goToFirstPage();
				//Auxiliary.showLabelAndTable(personList, tabla, true);
				
				if (tabla.getRows().isEmpty()) {
					RetroalimentationUtils
							.showInformationMessage(
									cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NO_COINCIDENCES_FOUND);
					tabla.setVisible(false);
					personList.setVisible(false);
				} else {
					tabla.setVisible(true);
					personList.setVisible(true);
				}
				
				searchCriteriaList.clear();
				UiUtils.get()
				.addSearchCriteria(searchCriteriaList,firstNameLabel.getText(),firstNameText.getText())
				.addSearchCriteria(searchCriteriaList,secondNameLabel.getText(),secondNameText.getText())
				.addSearchCriteria(searchCriteriaList,firstSurnameLabel.getText(),firstSurNameText.getText())
				.addSearchCriteria(searchCriteriaList,secondSurnameLabel.getText(),secondSurnameText.getText())
				.addSearchCriteria(searchCriteriaList,identificationLabel.getText(),identificationText.getText());
				
			}

		});
		//Auxiliary.showLabelAndTable(personList, tabla, false);
		
		tabla.getPaginator().goToFirstPage();
		Auxiliary.showLabelAndTable(personList, tabla, false);
		
		
		l10n();
		//consultButton.getShell().setDefaultButton(consultButton);
		return shell;
	}

	private void searchPersons(int page, int size) {
		Page<Person> list = ((PersonViewController) controller).findPersonsByParams(library, firstNameConsult, secondNameConsult, firstSurnameConsult, secondSurnameConsult, identificationConsult,
				page, size, direction, orderByString);
		tabla.clearRows();
		tabla.setTotalElements((int) list.getTotalElements());
		tabla.setRows(list.getContent());
		tabla.refresh();
		
		
	}

	@Override
	public String getID() {
		return "consultPersonID";
	}

	@Override
	public void l10n() {

		lblHeader.setText(MessageUtil.unescape(AbosMessages.get().SEARCH_CRITERIA));
		firstNameLabel.setText(MessageUtil.unescape(AbosMessages.get().FIRST_NAME));
		secondNameLabel.setText(MessageUtil.unescape(AbosMessages.get().SECOND_NAME));
		secondNameLabel.setText(MessageUtil.unescape(AbosMessages.get().SECOND_NAME));
		firstSurnameLabel.setText(MessageUtil.unescape(AbosMessages.get().FIRST_SURNAME));
		secondSurnameLabel.setText(MessageUtil.unescape(AbosMessages.get().SECOND_SURNAME));
		identificationLabel.setText(MessageUtil.unescape(AbosMessages.get().IDENTIFICATION));
		btnNew.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_NEW_SEARCH));
		consultButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_CONSULT));

		personList.setText(MessageUtil.unescape(AbosMessages.get().LIST_OF_MATCHES));

		tabla.setAddButtonText(AbosMessages.get().BUTTON_ADD);
		
		tabla.setCancelButtonText(MessageUtil.unescape(AbosMessages.get().BUTTON_CANCEL));
		tabla.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().NAME_AND_SURNAME), MessageUtil.unescape(AbosMessages.get().IDENTIFICATION),
				MessageUtil.unescape(AbosMessages.get().SEX), MessageUtil.unescape(AbosMessages.get().AGE)));
		tabla.setActionButtonText("exportPDFButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_PDF));
		tabla.setActionButtonText("exportExcelButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));
		
		personList.getParent().layout(true, true);
		personList.getParent().redraw();
		personList.getParent().update();
		tabla.l10n();
	}

	@Override
	public boolean canClose() {
		return false;
	}

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().PERSON_CONSULT);
	}

	@Override
	public void setViewController(ViewController controller) {
		super.controller = controller;
	}
}
