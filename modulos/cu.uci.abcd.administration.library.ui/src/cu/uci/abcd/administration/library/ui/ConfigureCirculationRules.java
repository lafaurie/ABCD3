package cu.uci.abcd.administration.library.ui;

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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.springframework.data.domain.Page;

import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abcd.administration.library.ui.controller.CirculationRuleViewController;
import cu.uci.abcd.administration.library.ui.model.CirculationRuleAddArea;
import cu.uci.abcd.administration.library.ui.model.CirculationRuleUpdateArea;
import cu.uci.abcd.administration.library.ui.model.CirculationRuleViewArea;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.CirculationRule;
import cu.uci.abcd.domain.management.library.Library;
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

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class ConfigureCirculationRules extends ContributorPage implements
		Contributor {
	private Label configureCirculationRuleLabel;
	private Combo recordTypeCombo;
	private Combo loanUserTypeCombo;
	private SecurityCRUDTreeTable tableCirculationRule;
	private Library library;
	private Nomenclator loanUserTypeConsult = null;
	private Nomenclator recordTypeConsult = null;
	private String orderByString = "loanUserType.nomenclatorName";
	private int direction = 1024;
	private Label listado;
	private Button consultButton;
	private Button newSearchButton;
	private List<String> searchCriteriaList = new ArrayList<>();
	private Label lblCriiteriosDeBsqueda;
	private Label lblNombreDelRegistro;
	private Label lblCdigo;
	private Composite right;

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().CIRCULATIONS_RULES);
	}

	@Override
	public String getID() {
		return "configureCirculationRuleID";
	}

	@Override
	public Control createUIControl(Composite shell) {
		library = (Library) SecurityUtils.getService().getPrincipal()
				.getByKey("library");
		addComposite(shell);
		shell.setData(RWT.CUSTOM_VARIANT, "gray_background");
		Composite scroll = new Composite(shell, SWT.NONE);
		scroll.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(scroll).atTopTo(shell, 25).atRight(0).withWidth(0)
				.withHeight(Display.getCurrent().getBounds().height - 172);
		configureCirculationRuleLabel = new Label(shell, SWT.NONE);
		addHeader(configureCirculationRuleLabel);
		Label separator = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);
		lblCriiteriosDeBsqueda = new Label(shell, SWT.NONE);
		addHeader(lblCriiteriosDeBsqueda);
		lblNombreDelRegistro = new Label(shell, SWT.NONE);
		add(lblNombreDelRegistro);
		recordTypeCombo = new Combo(shell, SWT.READ_ONLY);
		add(recordTypeCombo);
		lblCdigo = new Label(shell, SWT.NONE);
		add(lblCdigo);
		loanUserTypeCombo = new Combo(shell, SWT.READ_ONLY);
		add(loanUserTypeCombo);
		newSearchButton = new Button(shell, SWT.NONE);
		add(newSearchButton);
		consultButton = new Button(shell, SWT.NONE);
		add(consultButton);
		br();
		Label separador = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separador);
		right = new Composite(shell, SWT.NONE);
		addComposite(right);
		right.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(right).atTopTo(separador, 10).atRight(15)
				.withWidth(300).withHeight(5);
		listado = new Label(shell, SWT.NORMAL);
		addHeader(listado);
		tableCirculationRule = new SecurityCRUDTreeTable(shell, SWT.NONE);
		add(tableCirculationRule);
		tableCirculationRule.setEntityClass(CirculationRule.class);
		tableCirculationRule.setSearch(false);
		tableCirculationRule.setSaveAll(false);
		tableCirculationRule.setAdd(true, new CirculationRuleAddArea(
				controller, tableCirculationRule));
		tableCirculationRule.setWatch(true, new CirculationRuleViewArea());
		tableCirculationRule.setUpdate(true, new CirculationRuleUpdateArea(
				controller, tableCirculationRule));
		tableCirculationRule.setDelete("deleteCirculationRuleID");
		tableCirculationRule.setVisible(true);
		tableCirculationRule.setPageSize(10);
		tableCirculationRule.addListener(SWT.Resize, new Listener() {
			private static final long serialVersionUID = 8817895862824622805L;

			@Override
			public void handleEvent(Event event) {
				refresh();
			}
		});

		CRUDTreeTableUtils.configUpdate(tableCirculationRule);
		CRUDTreeTableUtils.configReports(tableCirculationRule,
				contributorName(), searchCriteriaList);

		CRUDTreeTableUtils.configRemove(tableCirculationRule,
				new IActionCommand() {
					@Override
					public void execute(final TreeColumnEvent event) {
						tableCirculationRule.destroyEditableArea();
						CirculationRule circulationRule = (CirculationRule) event.entity
								.getRow();
						Long idCirculationRule = circulationRule
								.getCirculationRuleID();
						((CirculationRuleViewController) controller)
								.deleteCirculationRuleById(idCirculationRule);
						searchCirculationRules(0, tableCirculationRule
								.getPaginator().getPageSize());
					}
				});

		TreeTableColumn columns[] = {
				new TreeTableColumn(30, 0, "getLoanUserType.getNomenclatorName"),
				new TreeTableColumn(25, 1, "getRecordType.getNomenclatorName"),
				new TreeTableColumn(14, 2, "getQuantityOfLoanAllowed"),
				new TreeTableColumn(12, 3, "getQuantityDayOnLoan"),
				new TreeTableColumn(19, 4, "getQuantityOfRenewedAllowed") };
		tableCirculationRule.createTable(columns);

		tableCirculationRule.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {
				if (event.sortData != null) {
					direction = event.sortData.sortDirection;
					switch (event.sortData.columnIndex) {
					case 0:
						orderByString = "loanUserType.nomenclatorName";
						break;
					case 1:
						orderByString = "recordType.nomenclatorName";
						break;
					case 2:
						orderByString = "quantityOfLoanAllowed";
						break;
					case 3:
						orderByString = "quantityDayOnLoan";
						break;
					case 4:
						orderByString = "quantityOfRenewedAllowed";
						break;
					}
				}
				searchCirculationRules(event.currentPage - 1, event.pageSize);
			}
		});

		consultButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 2653858202521146499L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				tableCirculationRule.destroyEditableArea();
				tableCirculationRule.clearRows();
				if (UiUtils.getSelected(loanUserTypeCombo) == null) {
					loanUserTypeConsult = null;
				} else {
					loanUserTypeConsult = (Nomenclator) UiUtils
							.getSelected(loanUserTypeCombo);
				}
				if (UiUtils.getSelected(recordTypeCombo) == null) {
					recordTypeConsult = null;
				} else {
					recordTypeConsult = (Nomenclator) UiUtils
							.getSelected(recordTypeCombo);
				}

				orderByString = "quantityDayOnLoan";
				direction = 1024;
				tableCirculationRule.getPaginator().goToFirstPage();

				if (tableCirculationRule.getRows().isEmpty()) {
					RetroalimentationUtils
							.showInformationMessage(
									right,
									cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NO_COINCIDENCES_FOUND);
				}
			}
		});

		newSearchButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -5179607102055540891L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				searchCriteriaList.clear();
				tableCirculationRule.destroyEditableArea();
				recordTypeCombo.select(0);
				loanUserTypeCombo.select(0);
				loanUserTypeConsult = null;
				recordTypeConsult = null;
				tableCirculationRule.getPaginator().goToFirstPage();
			}
		});
		tableCirculationRule.getPaginator().goToFirstPage();
		l10n();
		return shell;
	}

	private void searchCirculationRules(int page, int size) {
		Page<CirculationRule> list = ((CirculationRuleViewController) controller)
				.findCirculationRuleByParams(library, recordTypeConsult,
						loanUserTypeConsult, page, size, direction,
						orderByString);
		tableCirculationRule.clearRows();
		tableCirculationRule.setTotalElements((int) list.getTotalElements());
		tableCirculationRule.setRows(list.getContent());
		tableCirculationRule.refresh();

	}

	@Override
	public void l10n() {
		configureCirculationRuleLabel.setText(MessageUtil.unescape(AbosMessages
				.get().LABEL_CONFIGURE_CIRCULATIONS_RULES));
		lblCriiteriosDeBsqueda
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SEARCH_CRITERIA));
		lblNombreDelRegistro
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_TYPE_OF_MATERIAL));
		lblCdigo.setText(MessageUtil.unescape(AbosMessages.get().LABEL_TYPE_OF_USER));
		listado.setText(MessageUtil.unescape(AbosMessages.get().LABEL_LIST_OF_MATCHES));
		tableCirculationRule.setAddButtonText(MessageUtil.unescape(AbosMessages
				.get().BUTTON_ADD));
		tableCirculationRule.setCancelButtonText(MessageUtil
				.unescape(AbosMessages.get().BUTTON_CANCEL));
		tableCirculationRule
				.setColumnHeaders(Arrays.asList(
						MessageUtil
								.unescape(AbosMessages.get().LABEL_TYPE_OF_USER),
						MessageUtil.unescape(AbosMessages.get().LABEL_TYPE_OF_MATERIAL),
						MessageUtil.unescape(AbosMessages.get().LOANS),
						MessageUtil.unescape(AbosMessages.get().TIME),
						MessageUtil.unescape(AbosMessages.get().RENEWALS)));

		tableCirculationRule.setActionButtonText("exportPDFButton",
				MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_PDF));
		tableCirculationRule
				.setActionButtonText("exportExcelButton", MessageUtil
						.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));
		consultButton.setText(AbosMessages.get().BUTTON_CONSULT);
		newSearchButton
				.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_NEW_SEARCH));

		int positionRecordType = recordTypeCombo.getSelectionIndex();
		initialize(
				recordTypeCombo,
				((CirculationRuleViewController) controller)
						.getAllManagementLibraryViewController()
						.getLibraryService()
						.findNomenclatorByCode(library.getLibraryID(),
								Nomenclator.LOANOBJECT_TYPE));
		recordTypeCombo.select(positionRecordType);

		int positionLoanUserType = loanUserTypeCombo.getSelectionIndex();
		initialize(
				loanUserTypeCombo,
				((CirculationRuleViewController) controller)
						.getAllManagementLibraryViewController()
						.getLibraryService()
						.findNomenclatorByCode(library.getLibraryID(),
								Nomenclator.LOANUSER_TYPE));
		loanUserTypeCombo.select(positionLoanUserType);

		tableCirculationRule.l10n();
	}

	@Override
	public boolean canClose() {
		return false;
	}

	@Override
	public void setViewController(ViewController controller) {
		super.controller = controller;
	}
}