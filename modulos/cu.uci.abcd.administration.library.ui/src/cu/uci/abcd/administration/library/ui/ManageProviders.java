package cu.uci.abcd.administration.library.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.widgets.DialogCallback;
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
import org.eclipse.swt.widgets.Text;
import org.springframework.data.domain.Page;

import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abcd.administration.library.ui.controller.CoinViewController;
import cu.uci.abcd.administration.library.ui.controller.ProviderViewController;
import cu.uci.abcd.administration.library.ui.model.ProviderAddArea;
import cu.uci.abcd.administration.library.ui.model.ProviderUpdateArea;
import cu.uci.abcd.administration.library.ui.model.ProviderViewArea;
import cu.uci.abcd.administration.library.util.Auxiliary;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Provider;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.ui.FormPagePainter;
import cu.uci.abos.core.ui.GridPagePainter;
import cu.uci.abos.core.ui.Percent;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.CRUDTreeTableUtils;
import cu.uci.abos.core.widget.grid.SecurityCRUDTreeTable;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeColumnListener;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class ManageProviders extends ContributorPage implements Contributor {
	private Label manageProvidersLabel;
	private Label searchCriteriaLabel;
	private Label providerNameLabel;
	private Text providerNameText;
	private Label providerRifLabel;
	private Text providerRifText;
	private Label providerNitLabel;
	private Text providerNitText;
	private Label providerStateLabel;
	private Combo providerStateCombo;
	private Label providerTypeLabel;
	private Button commercialCheckButton;
	private Button canjeCheckButton;
	private Button donateCheckButton;
	private Button consultButton;
	private Label providerList;
	private Button newSearchButton;
	private String providerNameConsult = null;
	private String providerNitConsult = null;
	private String providerRifConsult = null;
	private Nomenclator cangeNomenclator;
	private Nomenclator commercialNomenclator;
	private Nomenclator donationNomenclator;
	private Nomenclator cangeNomenclatorConsult;
	private Nomenclator commercialNomenclatorConsult;
	private Nomenclator donationNomenclatorConsult;
	private Nomenclator providerStateConsult = null;
	private String orderByString = "providerName";
	private int direction = 1024;
	private SecurityCRUDTreeTable tableProvider;
	private List<String> searchCriteriaList = new ArrayList<>();
	private Composite right;
	private ValidatorUtils validator;
	private Library library;

	@Override
	public String contributorName() {
		return AbosMessages.get().PROVIDERS;
	}

	@Override
	public String getID() {
		return "manageProviderID";
	}

	@Override
	public Control createUIControl(Composite shell) {
		library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
		validator = new ValidatorUtils(new CustomControlDecoration());
		addComposite(shell);
		shell.setData(RWT.CUSTOM_VARIANT, "gray_background");
		
		Composite scroll = new Composite(shell,SWT.NONE);
		scroll.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(scroll).atTopTo(shell, 25).atRight(0).withWidth(0).withHeight(Display.getCurrent().getBounds().height - 172);
		
		cangeNomenclator = ((ProviderViewController) controller)
				.getAllManagementLibraryViewController().getLibraryService()
				.findNomenclatorById(Nomenclator.PROVIDER_TYPE_CANGE);
		commercialNomenclator = ((ProviderViewController) controller)
				.getAllManagementLibraryViewController().getLibraryService()
				.findNomenclatorById(Nomenclator.PROVIDER_TYPE_COMMERCIAL);
		donationNomenclator = ((ProviderViewController) controller)
				.getAllManagementLibraryViewController().getLibraryService()
				.findNomenclatorById(Nomenclator.PROVIDER_TYPE_DONATION);

		manageProvidersLabel = new Label(shell, SWT.NONE);
		addHeader(manageProvidersLabel);
		
		Label separator = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);

		searchCriteriaLabel = new Label(shell, SWT.NONE);
		addHeader(searchCriteriaLabel);

		providerNameLabel = new Label(shell, SWT.NONE);
		add(providerNameLabel);

		providerNameText = new Text(shell, SWT.NONE);
		add(providerNameText);
		//validator.applyValidator(providerNameText, 49);

		providerRifLabel = new Label(shell, SWT.NONE);
		add(providerRifLabel);

		providerRifText = new Text(shell, SWT.NONE);
		add(providerRifText);
		//validator.applyValidator(providerRifText, 19);
		br();

		providerStateLabel = new Label(shell, SWT.NONE);
		add(providerStateLabel);

		providerStateCombo = new Combo(shell, SWT.READ_ONLY);
		add(providerStateCombo);

		providerNitLabel = new Label(shell, SWT.NONE);
		add(providerNitLabel);

		providerNitText = new Text(shell, SWT.NONE);
		add(providerNitText);
		//validator.applyValidator(providerNitText, 19);
		br();

		providerTypeLabel = new Label(shell, SWT.NONE);
		add(providerTypeLabel);
		br();
		
		

		canjeCheckButton = new Button(shell, SWT.CHECK);
		donateCheckButton = new Button(shell, SWT.CHECK);
		commercialCheckButton = new Button(shell, SWT.CHECK);
		
		
		if( getDimension()<840 ){
			add(canjeCheckButton);
			br();
			add(donateCheckButton);
			br();
			add(commercialCheckButton);
			br();
		}else{
		   add(new Label(shell, SWT.NONE));
		   add(canjeCheckButton);
		   add(donateCheckButton);
		   add(commercialCheckButton);
		}
		
		canjeCheckButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 5633809979492479824L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (canjeCheckButton.getSelection()) {
					cangeNomenclatorConsult = cangeNomenclator;
				} else {
					cangeNomenclatorConsult = null;
				}
			}
		});

		
		
		donateCheckButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (donateCheckButton.getSelection()) {
					donationNomenclatorConsult = donationNomenclator;
				} else {
					donationNomenclatorConsult = null;
				}
			}
		});
		
		
		commercialCheckButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (commercialCheckButton.getSelection()) {
					commercialNomenclatorConsult = commercialNomenclator;
				} else {
					commercialNomenclatorConsult = null;
				}
			}
		});
		br();

		newSearchButton = new Button(shell, SWT.NONE);
		add(newSearchButton);
		newSearchButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -3341103990632230082L;
			@Override
			public void widgetSelected(SelectionEvent e) {
				searchCriteriaList.clear();
				tableProvider.destroyEditableArea();
				providerNameText.setText("");
				providerNameText.setFocus();
				providerRifText.setText("");
				providerNitText.setText("");
				providerStateCombo.select(0);
				canjeCheckButton.setSelection(false);
				donateCheckButton.setSelection(false);
				commercialCheckButton.setSelection(false);
				cangeNomenclatorConsult = null;
				commercialNomenclatorConsult = null;
				donationNomenclatorConsult = null;
				consultButton.setSelection(false);
				tableProvider.destroyEditableArea();
				providerNameConsult = null;
				providerNitConsult = null;
				providerRifConsult = null;
				providerStateConsult = null;
				cangeNomenclatorConsult = null;
				commercialNomenclatorConsult = null;
				donationNomenclatorConsult = null;
				orderByString = "providerName";
				direction = 1024;
				Auxiliary.showLabelAndTable(providerList, tableProvider, true);
				tableProvider.getPaginator().goToFirstPage();
			}
		});

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

		providerList = new Label(shell, SWT.NORMAL);
		addHeader(providerList);

		tableProvider = new SecurityCRUDTreeTable(shell, SWT.NONE);
		add(tableProvider);
		tableProvider.setEntityClass(Provider.class);
		tableProvider.setSearch(false);
		tableProvider.setSaveAll(false);

		tableProvider.setAdd(true, new ProviderAddArea(controller,
				tableProvider));
		tableProvider.setWatch(true, new ProviderViewArea(controller));
		tableProvider.setUpdate(true, new ProviderUpdateArea(controller,
				tableProvider, tableProvider));

		tableProvider.setDelete("deleteProviderID");
		tableProvider.setVisible(true);
		tableProvider.setPageSize(10);

		tableProvider.addListener(SWT.Resize, new Listener() {
            private static final long serialVersionUID = 8817895862824622805L;
            @Override
            public void handleEvent(Event event) {
                    refresh();
            }
           });
		
		CRUDTreeTableUtils.configUpdate(tableProvider);
		CRUDTreeTableUtils.configReports(tableProvider, contributorName(),
				searchCriteriaList);
		
		tableProvider.addDeleteListener(new TreeColumnListener() {
		public void handleEvent(final TreeColumnEvent event) {
			MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_QUESTION), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_WARN_DELET_DATA),
					new DialogCallback() {
						private static final long serialVersionUID = 8415736231132589115L;
						@Override
						public void dialogClosed(int returnCode) {
							if (returnCode == 0) {
								Provider provider = (Provider) event.entity.getRow();
								switch (((ProviderViewController) controller).deleteProviderByProvider(provider)) {
								case 1:
									RetroalimentationUtils.showInformationMessage((cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_ERROR_USED_DATA));
									break;

								case 2:
									RetroalimentationUtils.showInformationMessage((cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NOT_DELETE_ONE_ITEM));
									break;
									
								case 3:
									RetroalimentationUtils.showInformationMessage((cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_DELETE));
									
									break;
								}
								//searchProviders(0, tableProvider.getPaginator().getPageSize());
								tableProvider.getPaginator().goToPage(tableProvider.getPaginator().getCurrentPage());
							}
						}
					});
		}
	});
		TreeTableColumn columns[] = {
				new TreeTableColumn(35, 0, "getName"),
				new TreeTableColumn(30, 1, "getService"),
				new TreeTableColumn(15, 2, "getProviderState.getNomenclatorName"),
				new TreeTableColumn(20, 3,
						"getCountry.getNomenclatorName") };
		tableProvider.createTable(columns);

		tableProvider.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {
				if (event.sortData != null) {
					direction = event.sortData.sortDirection;
					switch (event.sortData.columnIndex) {
					case 0:
						orderByString = "providerName";
						break;
					case 1:
						orderByString = "rif";
						break;
					case 2:
						orderByString = "nit";
						break;
					case 3:
						orderByString = "providerState.nomenclatorName";
						break;
					}
				}
				searchProviders(event.currentPage - 1, event.pageSize);
			}
		});

		consultButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -1030305633320960902L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				tableProvider.destroyEditableArea();
				tableProvider.clearRows();
				providerNameConsult = providerNameText.getText();//.equals("")?null:providerNameText.getText();
				providerNitConsult = providerNitText.getText();//.equals("")?null:Auxiliary.getValue(providerNitText.getText());
				providerRifConsult = providerRifText.getText();//.equals("")?null:Auxiliary.getValue(providerRifText.getText());
				if (UiUtils.getSelected(providerStateCombo) == null) {
					providerStateConsult = null;
				} else {
					providerStateConsult = (Nomenclator) UiUtils
							.getSelected(providerStateCombo);
				}

				orderByString = "providerName";
				direction = 1024;
				Auxiliary.showLabelAndTable(providerList, tableProvider, true);
				tableProvider.getPaginator().goToFirstPage();

				String canje = canjeCheckButton.getSelection()?cangeNomenclatorConsult.getNomenclatorName():"";
				String comercial = commercialCheckButton.getSelection()?commercialNomenclatorConsult.getNomenclatorName():"";
				String donacion = donateCheckButton.getSelection()?donationNomenclatorConsult.getNomenclatorName():"";
				
				String out;
				if(  canje=="" && comercial=="" && donacion==""  )  {
					out = "";
				}else{
					out = canje +"  "+ comercial +"  "+ donacion;
				}
				searchCriteriaList.clear();
				UiUtils.get()
						.addSearchCriteria(
								searchCriteriaList,
								MessageUtil.unescape(AbosMessages.get().LABEL_NAME_PROVIDER),
								providerNameText.getText())
						.addSearchCriteria(
								searchCriteriaList,
								(MessageUtil.unescape(AbosMessages.get().LABEL_NIT)),
								providerNitText.getText())
						.addSearchCriteria(
								searchCriteriaList,
								(MessageUtil.unescape(AbosMessages.get().LABEL_RIF)),
								providerRifText.getText())
				.addSearchCriteria(searchCriteriaList, MessageUtil.unescape(AbosMessages
						.get().LABEL_STATE), providerStateCombo)
				.addSearchCriteria(searchCriteriaList, MessageUtil.unescape(AbosMessages.get().LABEL_SERVICE), out);
					if (tableProvider.getRows().isEmpty()) {
						RetroalimentationUtils
								.showInformationMessage(
										right,
										cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NO_COINCIDENCES_FOUND);
					}
				
			}
		});
		tableProvider.getPaginator().goToFirstPage();
		l10n();
		return shell;

	}

	public void searchProviders(int page, int size) {
		Page<Provider> list = ((ProviderViewController) controller)
				.findByProviderByParams(library, providerNameConsult,
						providerNitConsult, providerRifConsult,
						providerStateConsult, cangeNomenclatorConsult, commercialNomenclatorConsult,
						donationNomenclatorConsult, page, size, direction,
						orderByString);
		tableProvider.clearRows();
		tableProvider.setTotalElements((int) list.getTotalElements());
		tableProvider.setRows(list.getContent());
		tableProvider.refresh();
	}

	@Override
	public boolean canClose() {
		return false;
	}

	@Override
	public void l10n() {
		manageProvidersLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_MANAGE_PROVIDERS));
		searchCriteriaLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SEARCH_CRITERIA));
		providerNameLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_NAME_PROVIDER));
		providerNitLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_NIT));
		providerRifLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_RIF));
		providerStateLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_STATE));
		providerTypeLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SERVICE));
		canjeCheckButton
				.setText(MessageUtil.unescape(AbosMessages.get().CHECKBUTTON_CHANGE));
		donateCheckButton
				.setText(MessageUtil.unescape(AbosMessages.get().CHECKBUTTON_DONATIONS));
		commercialCheckButton
				.setText(MessageUtil.unescape(AbosMessages.get().CHECKBUTTON_COMMERCIAL));
		newSearchButton
				.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_NEW_SEARCH));
		consultButton
				.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_CONSULT));
		providerList
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_LIST_OF_MATCHES));
		tableProvider
				.setAddButtonText(MessageUtil.unescape(AbosMessages.get().BUTTON_ADD));
		tableProvider.setCancelButtonText(MessageUtil.unescape(AbosMessages
				.get().BUTTON_CANCEL));
		tableProvider.setColumnHeaders(Arrays.asList(
				MessageUtil.unescape(AbosMessages.get().LABEL_NAME),
				MessageUtil.unescape(AbosMessages.get().LABEL_SERVICE), MessageUtil.unescape(AbosMessages.get().LABEL_STATE),
				MessageUtil.unescape(AbosMessages.get().LABEL_COUNTRY)));
	
		tableProvider.setActionButtonText("exportPDFButton",
				MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_PDF));
		tableProvider
				.setActionButtonText("exportExcelButton", MessageUtil
						.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));

		int position = providerStateCombo.getSelectionIndex();
		initialize(providerStateCombo, ((ProviderViewController) controller)
				.getAllManagementLibraryViewController().getLibraryService()
				.findNomenclatorByCode(library.getLibraryID(), Nomenclator.PROVIDER_STATE));
		providerStateCombo.select(position);
		
		providerList.getParent().layout(true, true);
		providerList.getParent().redraw();
		providerList.getParent().update();
		
		tableProvider.l10n();
	}

	@Override
	public void setViewController(ViewController controller) {
		super.controller = controller;
	}

	public CRUDTreeTable getTableProvider() {
		return tableProvider;
	}

}
