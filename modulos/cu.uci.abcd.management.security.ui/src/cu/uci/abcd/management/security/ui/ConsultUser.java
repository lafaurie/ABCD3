package cu.uci.abcd.management.security.ui;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.management.security.communFragment.ResetPassword;
import cu.uci.abcd.management.security.l10n.AbosMessages;
import cu.uci.abcd.management.security.ui.controller.UserViewController;
import cu.uci.abcd.management.security.ui.model.UserUpdateArea;
import cu.uci.abcd.management.security.ui.model.UserViewArea;
import cu.uci.abcd.management.security.util.Auxiliary;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.domain.Row;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.Digest;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.widget.grid.CRUDTreeTableUtils;
import cu.uci.abos.core.widget.grid.Column;
import cu.uci.abos.core.widget.grid.IActionCommand;
import cu.uci.abos.core.widget.grid.IActionDenied;
import cu.uci.abos.core.widget.grid.SecurityCRUDTreeTable;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeColumnListener;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

public class ConsultUser extends ContributorPage implements Contributor {

	private SecurityCRUDTreeTable tabla;
	ResetPassword resetPassword;

	int direction = 1024;
	String orderByString = "username";

	Text firstNameText;
	Text secondNameText;
	Text firstSurNameText;
	Text secondSurnameText;
	Text identificationText;
	Text userNameText;
	DateTime fromDateTime;
	DateTime toDateTime;
	// Combo userTypeCombo;

	Library library;
	Button gestionSystem;
	Button opac;

	Label lblHeader;
	Label firstNameLabel;
	Label secondNameLabel;
	Label firstSurnameLabel;
	Label secondSurnameLabel;
	Label identificationLabel;
	Label userNameLabel;
	Label systemLabel;
	Label opacLabel;
	Label fromDateLabel;
	Label toDateLabel;
	Label registerDateLabel;
	Button btnNew;
	Button consultButton;
	Label userListLabel;

	String firstNameConsult = null;
	String secondNameConsult = null;
	String firstSurnameConsult = null;
	String secondSurnameConsult = null;
	String identificationConsult = null;
	String userConsult = null;
	boolean opacConsult = false;
	boolean systemConsult = true;
	Date fromDateTimeConsult = null;
	Date toDateTimeConsult = null;

	int withPerson = 0;

	private List<String> searchCriteriaList = new ArrayList<>();

	@Override
	public Control createUIControl(final Composite shell) {
		library = (Library) SecurityUtils.getPrincipal().getByKey("library");

		addComposite(shell);
		shell.setData(RWT.CUSTOM_VARIANT, "gray_background");

		Composite scroll = new Composite(shell,SWT.NONE);
		scroll.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(scroll).atTopTo(shell, 25).atRight(0).withWidth(0).withHeight(Display.getCurrent().getBounds().height - 172);
		
		Label consultUserLabel = new Label(shell, SWT.NONE);
		consultUserLabel.setText("CONSULTAR USUARIOS");
		addHeader(consultUserLabel);
		
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

		userNameLabel = new Label(shell, SWT.NONE);
		add(userNameLabel);

		userNameText = new Text(shell, SWT.NONE);
		add(userNameText);

		br();

		systemLabel = new Label(shell, SWT.NONE);
		add(systemLabel);

		gestionSystem = new Button(shell, SWT.CHECK);
		add(gestionSystem);
		gestionSystem.setSelection(true);
		

		opacLabel = new Label(shell, SWT.NONE);
		add(opacLabel);

		opac = new Button(shell, SWT.CHECK);
		add(opac);

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

		userListLabel = new Label(shell, SWT.NONE);
		addHeader(userListLabel);

		tabla = new SecurityCRUDTreeTable(shell, SWT.NONE);
		add(tabla);
		tabla.setEntityClass(User.class);
		tabla.setSearch(false);
		tabla.setSaveAll(false);
		tabla.setWatch(true, new UserViewArea());
		tabla.setUpdate(true, new UserUpdateArea(controller, tabla));
		tabla.setDelete("deleteUserID");
		tabla.setVisible(true);
		tabla.setPageSize(10);
		
		tabla.addListener(SWT.Resize, new Listener() {
            private static final long serialVersionUID = 8817895862824622805L;
            @Override
            public void handleEvent(Event event) {
                    refresh();
            }
           });
		
		if(SecurityUtils.check("resetPassword")){
		Column columnResetPassword = new Column("resetPassword", shell.getDisplay(),
				new TreeColumnListener() {
					public void handleEvent(final TreeColumnEvent event) {
						resetPassword = new ResetPassword(
								shell.getShell(),
								SWT.APPLICATION_MODAL | SWT.CLOSE, MessageUtil.unescape(AbosMessages.get().RESET),
								shell, new SelectionListener() {
									private static final long serialVersionUID = -5702216441589389303L;

									@Override
									public void widgetSelected(SelectionEvent arg0) {
										

										if (((ResetPassword) resetPassword).getValidator().decorationFactory
												.isRequiredControlDecorationIsVisible()) {
											RetroalimentationUtils.showErrorMessage(MessageUtil
													.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
										} else {
											if (((ResetPassword) resetPassword).getValidator().decorationFactory
													.AllControlDecorationsHide()) {
												
												String password = ((ResetPassword) resetPassword).getPasswordText().getText();
										        String confirmPassword = ((ResetPassword) resetPassword).getPasswordConfirmText().getText();
										        
										        if(password.equals(confirmPassword)){
										        User userSelected = (User) event.entity.getRow();
										        userSelected.setUserPassword(Digest.digest(password, "SHA1"));
										        
										        ((UserViewController) controller).addUser(userSelected);
										        
										        RetroalimentationUtils
												.showInformationMessage(MessageUtil.unescape(AbosMessages.get().RESET_PASSWORD_OK));
										        
										        resetPassword.close();
										        
										        }else{
										        	RetroalimentationUtils
													.showErrorMessage(MessageUtil.unescape(AbosMessages.get().PASSWORD_NOT_MATCH));
										        }
										        
											} else {
												RetroalimentationUtils.showErrorMessage(MessageUtil
														.unescape(cu.uci.abos.core.l10n.AbosMessages
																.get().MSG_ERROR_INCORRECT_DATA));
											}
										}
									
										
										
									
										
									}

									@Override
									public void widgetDefaultSelected(
											SelectionEvent arg0) {
									}
								});
						// FIXME CAlcular medio de pantalla y dimensiones
						resetPassword.setBounds(500, 200, 320, 150);
						resetPassword.open();
						//((SearchUserCredentianls) searchUserCredentianls)
							//	.selectDomain(library);
						//((LoginSearchUser) searchUserCredentianls)
						//		.setUserName(userNameText.getText());

					
						
					}
				});
		columnResetPassword.setToolTipText(MessageUtil.unescape(AbosMessages.get().RESET_PASSWORD));
		tabla.addActionColumn(columnResetPassword);
		
	}
		
		CRUDTreeTableUtils.configUpdate(tabla);

		CRUDTreeTableUtils.configReports(tabla, contributorName(), searchCriteriaList);

		CRUDTreeTableUtils.configRemove(tabla, new IActionCommand() {
			@Override
			public void execute(final TreeColumnEvent event) {
				tabla.destroyEditableArea();
				User entityUser = (User) event.entity.getRow();
				((UserViewController) controller).deleteUser(entityUser);
				//tabla.getPaginator().goToFirstPage();
			}
		});
		TreeTableColumn columns[] = {
				new TreeTableColumn(35, 0, "nameIfHavePerson"),
				new TreeTableColumn(20, 1, "identificationIfHavePerson"),
				new TreeTableColumn(20, 2, "getUsernameToString"),
				new TreeTableColumn(15, 3, "getCreationDate"),
				new TreeTableColumn(10, 4, "getEnabledToString")};

		tabla.createTable(columns);

		tabla.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {

				if (event.sortData != null) {
					direction = event.sortData.sortDirection;
					switch (event.sortData.columnIndex) {
					case 1:
						orderByString = "username";
						break;
					case 2:
						orderByString = "username";
						break;
					}
				}
				searchUsers(event.currentPage - 1, event.pageSize);

			}
		});

		
		tabla.setActionDenied(new IActionDenied() {
			@Override
			public boolean isDenied(Column column, Row row) {
				User user = (User) row;
				if (column.getIndex() == 5) {
					if (user.getDomain()!=null){
						return true;
					}
				}
				return false;
			}
		});
		/*
		tabla.setActionDenied(new IActionDenied() {
			@Override
			public boolean isDenied(Column column, Row row) {
				User user1 = (User) row;
				if (column.getIndex() == 7) {
					if (user1.getPerson()==null){
						return true;
					}
				}
				return false;
			}
		});
		*/
		gestionSystem.addSelectionListener(new SelectionAdapter() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -8848167914724402821L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (gestionSystem.getSelection()) {
					systemConsult = true;
				} else {
					systemConsult = false;
				}
			}
		});

		opac.addSelectionListener(new SelectionAdapter() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 6751758036618113863L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (opac.getSelection()) {
					opacConsult = true;
				} else {
					opacConsult = false;
				}
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
				userNameText.setText("");
				opac.setSelection(false);
				gestionSystem.setSelection(true);
				opacConsult = false;
				systemConsult = false;
				
				Auxiliary.goDateTimeToBeforeOneMonth(fromDateTime);
				Auxiliary.goDateTimeToToday(toDateTime);
				
				firstNameText.setFocus();
				Auxiliary.showLabelAndTable(userListLabel, tabla, false);
			}

		});

		consultButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("deprecation")
			@Override
			public void widgetSelected(SelectionEvent e) {
				//tabla.clearRows();
				tabla.destroyEditableArea();
				tabla.clearRows();
				firstNameConsult = (firstNameText.getText().length() > 0) ? firstNameText.getText() : null;
				
				
				secondNameConsult = (secondNameText.getText().length() > 0) ? Auxiliary.getValue(secondNameText.getText()) : null;
				firstSurnameConsult = (firstSurNameText.getText().length() > 0) ? firstSurNameText.getText() : null;
				secondSurnameConsult = (secondSurnameText.getText().length() > 0) ? Auxiliary.getValue(secondSurnameText.getText()) : null;
				identificationConsult = (identificationText.getText().length() > 0) ? Auxiliary.getValue(identificationText.getText()) : null;

				if (firstNameConsult != null || secondNameConsult != null || firstSurnameConsult != null || secondSurnameConsult != null || identificationConsult != null) {
					withPerson = 1;
				} else {
					withPerson = 0;
				}

				userConsult = (userNameText.getText().length() > 0) ? userNameText.getText() : null;

				int fromYear = fromDateTime.getYear() - 1900;
				int fromMonth = fromDateTime.getMonth();
				int fromDay = fromDateTime.getDay();
				fromDateTimeConsult = new Date(fromYear, fromMonth, fromDay);

				int toYear = toDateTime.getYear() - 1900;
				int toMonth = toDateTime.getMonth();
				int toDay = toDateTime.getDay();
				toDateTimeConsult = new Date(toYear, toMonth, toDay);

				orderByString = "username";
				direction = 1024;

				tabla.getPaginator().goToFirstPage();
				//Auxiliary.showLabelAndTable(userListLabel, tabla, true);
				
				if (tabla.getRows().isEmpty()) {
					RetroalimentationUtils
							.showInformationMessage(
									cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NO_COINCIDENCES_FOUND);
					tabla.setVisible(false);
					userListLabel.setVisible(false);
				} else {
					tabla.setVisible(true);
					userListLabel.setVisible(true);
				}
				
				searchCriteriaList.clear();
				UiUtils.get()
				.addSearchCriteria(searchCriteriaList,firstNameLabel.getText(),firstNameText.getText())
				.addSearchCriteria(searchCriteriaList,secondNameLabel.getText(),secondNameText.getText())
				.addSearchCriteria(searchCriteriaList,firstSurnameLabel.getText(),firstSurNameText.getText())
				.addSearchCriteria(searchCriteriaList,secondSurnameLabel.getText(),secondSurnameText.getText())
				.addSearchCriteria(searchCriteriaList,identificationLabel.getText(),identificationText.getText())
				.addSearchCriteria(searchCriteriaList,userNameLabel.getText(),userNameText.getText())
				
				.addSearchCriteria(searchCriteriaList,systemLabel.getText(), gestionSystem.getSelection()?"Si":"No")
				.addSearchCriteria(searchCriteriaList,opacLabel.getText(), opac.getSelection()?"Si":"No")
				
				.addSearchCriteria(searchCriteriaList,fromDateLabel.getText(),fromDateTime)
				.addSearchCriteria(searchCriteriaList,toDateLabel.getText(),toDateTime)
				
				;
				
			}

		});
		//Auxiliary.showLabelAndTable(userListLabel, tabla, false);
		
		tabla.getPaginator().goToFirstPage();
		Auxiliary.showLabelAndTable(userListLabel, tabla, false);
		
		
		l10n();

		//systemConsult = true;
		//systemConsult = false;
		
		//consultButton.getShell().setDefaultButton(consultButton);
		return shell;
	}

	private void searchUsers(int page, int size) {
		tabla.clearRows();

		boolean autenticated = false;
		// boolean opacConsult = true;
		// boolean systemConsult = true;
		// Date fromDateTimeConsult = null;
		// Date toDateTimeConsult = null;

		Page<User> list = ((UserViewController) controller).findUsersByParams(library, withPerson, autenticated, firstNameConsult, secondNameConsult, firstSurnameConsult, secondSurnameConsult,
				identificationConsult, userConsult, opacConsult, systemConsult, fromDateTimeConsult, toDateTimeConsult, page, size, direction, orderByString);

		tabla.getPaginator().setTotalElements((int) list.getTotalElements());
		tabla.setRows(list.getContent());
		tabla.refresh();
		
		

	}

	/*
	 * private void updateGrid(CRUDTreeTable table){ String username =
	 * txtUser.getText();
	 * 
	 * Collection<Account> listPerson =
	 * ((UserViewController)controller).findAccountByQuery(username);
	 * 
	 * //Delete all items table.setRedraw( false ); table.setVisible(false);
	 * //table.remove(0, table.getEntities().size()); table.setVisible(true);
	 * table.refresh(); for (Account aux : listPerson) { EntityUser user = new
	 * EntityUser(aux); if(aux.getOwner() != null)
	 * //user.setOwner(aux.getOwner()); //user.setAccountID(aux.getAccountID());
	 * //user.getDateInformation().setCreationDate(aux.getDateInformation().
	 * getCreationDate()); table.addRow(user); } table.setRedraw( true ); }
	 * 
	 * private void initializeGrid(CRUDTreeTable table) {
	 * 
	 * Collection<Account> listPerson =
	 * ((UserViewController)controller).findAllAccount(); for (Account aux :
	 * listPerson) { EntityUser user = new EntityUser(aux); if(aux.getOwner() !=
	 * null) //user.setOwner(aux.getOwner());
	 * //user.setAccountID(aux.getAccountID()); table.addRow(user); } }
	 */

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return "consultUserID";
	}

	@Override
	public void l10n() {

		lblHeader.setText(MessageUtil.unescape(AbosMessages.get().SEARCH_CRITERIA));
		firstNameLabel.setText(MessageUtil.unescape(AbosMessages.get().FIRST_NAME));
		secondNameLabel.setText(MessageUtil.unescape(AbosMessages.get().SECOND_NAME));
		firstSurnameLabel.setText(MessageUtil.unescape(AbosMessages.get().FIRST_SURNAME));
		secondSurnameLabel.setText(MessageUtil.unescape(AbosMessages.get().SECOND_SURNAME));
		identificationLabel.setText(MessageUtil.unescape(AbosMessages.get().IDENTIFICATION));
		userNameLabel.setText(MessageUtil.unescape(AbosMessages.get().USER));
		systemLabel.setText(MessageUtil.unescape(AbosMessages.get().GESTION_SYSTEM));
		opacLabel.setText(MessageUtil.unescape(AbosMessages.get().OPAC));

		fromDateLabel.setText(MessageUtil.unescape(AbosMessages.get().FROM));
		toDateLabel.setText(MessageUtil.unescape(AbosMessages.get().TO));
		registerDateLabel.setText(MessageUtil.unescape(AbosMessages.get().REGISTER_DATE));

		btnNew.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_NEW_SEARCH));
		consultButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_CONSULT));
		userListLabel.setText(MessageUtil.unescape(AbosMessages.get().LIST_OF_MATCHES));

		tabla.setCancelButtonText(MessageUtil.unescape(AbosMessages.get().BUTTON_CANCEL));
		tabla.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().NAME_AND_SURNAME ), MessageUtil.unescape(AbosMessages.get().IDENTIFICATION), MessageUtil.unescape(AbosMessages.get().USER + " / " + "Domain" ),
				MessageUtil.unescape(AbosMessages.get().REGISTER_DATE), "Estado"));

		//new TreeTableColumn(15, 0, "getUsername"),
		//new TreeTableColumn(55, 1, "nameIfHavePerson"),
		//new TreeTableColumn(15, 2, "getCreationDate"),
		//new TreeTableColumn(15, 3, "getEnabled")};
		tabla.setActionButtonText("exportPDFButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_PDF));
		tabla.setActionButtonText("exportExcelButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));
		
		
		userListLabel.getParent().layout(true, true);
		userListLabel.getParent().redraw();
		userListLabel.getParent().update();
		tabla.l10n();

	}

	@Override
	public boolean canClose() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String contributorName() {
		// TODO Auto-generated method stub
		return MessageUtil.unescape(AbosMessages.get().CONSULT_USERS);
	}

	@Override
	public void setViewController(ViewController controller) {
		super.controller = controller;

	}

}
