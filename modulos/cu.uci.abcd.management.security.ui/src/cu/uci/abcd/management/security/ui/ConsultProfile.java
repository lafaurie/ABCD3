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
import cu.uci.abcd.domain.management.security.Profile;
import cu.uci.abcd.management.security.l10n.AbosMessages;
import cu.uci.abcd.management.security.ui.controller.ProfileViewController;
import cu.uci.abcd.management.security.ui.model.ProfileUpdateArea;
import cu.uci.abcd.management.security.ui.model.ProfileViewArea;
import cu.uci.abcd.management.security.util.Auxiliary;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.CRUDTreeTableUtils;
import cu.uci.abos.core.widget.grid.IActionCommand;
import cu.uci.abos.core.widget.grid.SecurityCRUDTreeTable;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

public class ConsultProfile extends ContributorPage implements Contributor {

	private Library library;
	private SecurityCRUDTreeTable tabla;
	int direction = 1024;
	String orderByString = "profileName";
	Button consultButton;

	Label header;
	Label perfilName;
	Label fromDateTimeLabel;
	Label toDateTimeLabel;
	Label lblRegisterDate;
	Button btnNew;
	Label profilesList;
	Label consultProfileLabel;

	Text namePerfil;
	DateTime fromDateTime;
	DateTime toDateTime;

	String profileNameConsult = null;
	Date fromDateTimeConsult;
	Date toDateTimeConsult;

	private List<String> searchCriteriaList = new ArrayList<>();

	@Override
	public Control createUIControl(Composite shell) {
		library = (Library) SecurityUtils.getPrincipal().getByKey("library");
		addComposite(shell);
		shell.setData(RWT.CUSTOM_VARIANT, "gray_background");

		//Composite scroll = new Composite(shell,SWT.NONE);
		//scroll.setData(RWT.CUSTOM_VARIANT, "gray_background");
		//FormDatas.attach(scroll).atTopTo(shell, 25).atRight(0).withWidth(0).withHeight(Display.getCurrent().getBounds().height - 172);
		
		consultProfileLabel = new Label(shell, SWT.NONE);
		addHeader(consultProfileLabel);
		
		Label separator1 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator1);
		
		header = new Label(shell, SWT.BOLD | SWT.ALL);
		addHeader(header);

		perfilName = new Label(shell, SWT.NONE);
		add(perfilName);

		namePerfil = new Text(shell, SWT.NONE);
		add(namePerfil);
		br();

		lblRegisterDate = new Label(shell, SWT.NONE);
		add(lblRegisterDate);
		br();

		fromDateTimeLabel = new Label(shell, SWT.NONE);
		add(fromDateTimeLabel);

		fromDateTime = new DateTime(shell, SWT.BORDER | SWT.DROP_DOWN);
		add(fromDateTime);
		
		Auxiliary.goDateTimeToBeforeOneMonth(fromDateTime);

		
		toDateTimeLabel = new Label(shell, SWT.NONE);
		add(toDateTimeLabel);

		toDateTime = new DateTime(shell, SWT.BORDER | SWT.DROP_DOWN);
		add(toDateTime);

		btnNew = new Button(shell, SWT.PUSH);
		add(btnNew);

		consultButton = new Button(shell, SWT.PUSH);
		add(consultButton);

		Label separator = new Label(shell, SWT.HORIZONTAL | SWT.SEPARATOR);
		addSeparator(separator);

		profilesList = new Label(shell, SWT.NONE);
		addHeader(profilesList);

		tabla = new SecurityCRUDTreeTable(shell, SWT.NONE);
		add(tabla);
		tabla.setEntityClass(Profile.class);
		tabla.setSearch(false);
		tabla.setSaveAll(false);
		tabla.setWatch(true, new ProfileViewArea());
		tabla.setUpdate(true, new ProfileUpdateArea(controller, tabla));
		tabla.setDelete("deleteProfileID");
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
				Profile entityProfile = (Profile) event.entity.getRow();
				Profile profile = ((ProfileViewController) controller).findOneProfile(entityProfile.getId());
				((ProfileViewController) controller).deleteProfile(profile);
				//tabla.getPaginator().goToFirstPage();
			}
		});

		TreeTableColumn columns[] = { new TreeTableColumn(80, 0, "getProfileName"), new TreeTableColumn(20, 1, "getCreationDate") };
		tabla.createTable(columns);

		tabla.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {

				if (event.sortData != null) {
					direction = event.sortData.sortDirection;
					switch (event.sortData.columnIndex) {
					case 1:
						orderByString = "profileName";
						break;
					case 2:
						orderByString = "profileName";
						break;
					}
				}
				searchProfiles(event.currentPage - 1, event.pageSize);
			}
		});

		btnNew.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				tabla.destroyEditableArea();
				tabla.clearRows();
				namePerfil.setText("");
				namePerfil.setFocus();
				Auxiliary.goDateTimeToBeforeOneMonth(fromDateTime);
				Auxiliary.goDateTimeToToday(toDateTime);
				Auxiliary.showLabelAndTable(profilesList, tabla, false);
			}
		});

		consultButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("deprecation")
			public void widgetSelected(SelectionEvent event) {
				tabla.destroyEditableArea();
				tabla.clearRows();
					
					
				profileNameConsult = namePerfil.getText();

				int fromYear = fromDateTime.getYear() - 1900;
				int fromMonth = fromDateTime.getMonth();
				int fromDay = fromDateTime.getDay();
				fromDateTimeConsult = new Date(fromYear, fromMonth, fromDay);

				int toYear = toDateTime.getYear() - 1900;
				int toMonth = toDateTime.getMonth();
				int toDay = toDateTime.getDay();
				toDateTimeConsult = new Date(toYear, toMonth, toDay);

				orderByString = "profileName";
				direction = 1024;
				tabla.getPaginator().goToFirstPage();
				
				 refresh();
				 
				 if (tabla.getRows().isEmpty()) {
						RetroalimentationUtils
								.showInformationMessage(
										cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NO_COINCIDENCES_FOUND);
						tabla.setVisible(false);
						profilesList.setVisible(false);
					} else {
						tabla.setVisible(true);
						profilesList.setVisible(true);
					}
				 
				 searchCriteriaList.clear();
					UiUtils.get()
					.addSearchCriteria(searchCriteriaList,perfilName.getText(),namePerfil.getText())
					.addSearchCriteria(searchCriteriaList,fromDateTimeLabel.getText(),fromDateTime)
					.addSearchCriteria(searchCriteriaList,toDateTimeLabel.getText(),toDateTime);
			}
		});
		
		tabla.getPaginator().goToFirstPage();
		Auxiliary.showLabelAndTable(profilesList, tabla, false);
		
		l10n();
		return shell;
	}

	@Override
	public String getID() {
		return "consultProfileID";
	}

	private void searchProfiles(int page, int size) {
		tabla.clearRows();
		Page<Profile> list = ((ProfileViewController) controller).findProfilesByParams(library, profileNameConsult, fromDateTimeConsult, toDateTimeConsult, page, size, direction, orderByString);
		tabla.getPaginator().setTotalElements((int) list.getTotalElements());
		tabla.setRows(list.getContent());
		tabla.refresh();
		
		

	}

	@Override
	public void l10n() {

		header.setText(MessageUtil.unescape(AbosMessages.get().SEARCH_CRITERIA));
		perfilName.setText(MessageUtil.unescape(AbosMessages.get().PROFILE_NAME));
		lblRegisterDate.setText(MessageUtil.unescape(AbosMessages.get().REGISTER_DATE));
		fromDateTimeLabel.setText(MessageUtil.unescape(AbosMessages.get().FROM));
		toDateTimeLabel.setText(MessageUtil.unescape(AbosMessages.get().TO));
		btnNew.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_NEW_SEARCH));
		consultButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_CONSULT));
		profilesList.setText(MessageUtil.unescape(AbosMessages.get().LIST_OF_MATCHES));
		consultProfileLabel.setText(AbosMessages.get().CONSULT_PROFILE);

		tabla.setAddButtonText(AbosMessages.get().BUTTON_ADD);
		tabla.setCancelButtonText(AbosMessages.get().BUTTON_CANCEL);
		
		tabla.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().PROFILE_NAME), MessageUtil.unescape(AbosMessages.get().REGISTER_DATE)));

		tabla.setActionButtonText("exportPDFButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_PDF));
		tabla.setActionButtonText("exportExcelButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));
		
		
		profilesList.getParent().layout(true, true);
		profilesList.getParent().redraw();
		profilesList.getParent().update();
		tabla.l10n();

	}

	@Override
	public boolean canClose() {
		return false;
	}

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().CONSULT_PROFILES);
	}

	@Override
	public void setViewController(ViewController controller) {
		super.controller = controller;

	}

	public CRUDTreeTable getTabla() {
		return tabla;
	}
	

}
