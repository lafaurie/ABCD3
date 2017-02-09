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
import cu.uci.abcd.administration.library.ui.model.CoinAddArea;
import cu.uci.abcd.administration.library.ui.model.CoinUpdateArea;
import cu.uci.abcd.administration.library.ui.model.CoinViewArea;
import cu.uci.abcd.administration.library.util.Auxiliary;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Coin;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
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

public class ManageCoin extends ContributorPage implements Contributor {
	private Label manageCoinLabel;
	private Label searchCriteriaLabel;
	private Label coinNameLabel;
	private Text coinNameText;
	private Label identifierCoinLabel;
	private Combo identifierCoin;
	private Label separador;
	private Button consultButton;
	private Button newSearchButton;
	private Label coinListLabel;
	private SecurityCRUDTreeTable tableCoin;
	private String coinNameConsult = null;
	private Nomenclator identificationConsult = null;
	private String orderByString = "coinName";
	private int direction = 1024;
	private List<String> searchCriteriaList = new ArrayList<>();
	private Composite right;
	private ValidatorUtils validator;
	private Library library;

	@Override
	public String contributorName() {
		return AbosMessages.get().COINS;
	}

	@Override
	public String getID() {
		return "manageCoinID";
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
		
		
		manageCoinLabel = new Label(shell, SWT.NONE);
		addHeader(manageCoinLabel);
		
		Label separator = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);
		

		searchCriteriaLabel = new Label(shell, SWT.NONE);
		addHeader(searchCriteriaLabel);

		coinNameLabel = new Label(shell, SWT.NONE);
		add(coinNameLabel);

		coinNameText = new Text(shell, SWT.NONE);
		//validator.applyValidator(coinNameText, 29);
		add(coinNameText);

		identifierCoinLabel = new Label(shell, SWT.NONE);
		add(identifierCoinLabel);

		identifierCoin = new Combo(shell, SWT.READ_ONLY);
		add(identifierCoin);

		newSearchButton = new Button(shell, SWT.NONE);
		add(newSearchButton);

		newSearchButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -5179607102055540891L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				searchCriteriaList.clear();
				tableCoin.destroyEditableArea();
				coinNameText.setText("");
				coinNameText.setFocus();
				identifierCoin.select(0);
				tableCoin.destroyEditableArea();
				coinNameConsult = null;
				identificationConsult = null;
				orderByString = "coinName";
				direction = 1024;
				Auxiliary.showLabelAndTable(coinListLabel, tableCoin, true);
				tableCoin.getPaginator().goToFirstPage();

			}
		});
		consultButton = new Button(shell, SWT.NONE);
		add(consultButton);
		br();

		separador = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separador);

		right = new Composite(shell, SWT.NONE);
		addComposite(right);
		right.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(right).atTopTo(separador, 10).atRight(15).withWidth(300).withHeight(5);

		coinListLabel = new Label(shell, SWT.NORMAL);
		addHeader(coinListLabel);

		tableCoin = new SecurityCRUDTreeTable(shell, SWT.NONE);
		add(tableCoin);
		tableCoin.setEntityClass(Coin.class);
		tableCoin.setSearch(false);
		tableCoin.setSaveAll(false);

		tableCoin.setAdd(true, new CoinAddArea(controller, tableCoin));
		tableCoin.setWatch(true, new CoinViewArea());
		tableCoin.setUpdate(true, new CoinUpdateArea(controller, tableCoin));

		tableCoin.setDelete("deleteCoinID");

		tableCoin.setVisible(true);
		tableCoin.setPageSize(10);

		tableCoin.addListener(SWT.Resize, new Listener() {
            private static final long serialVersionUID = 8817895862824622805L;
            @Override
            public void handleEvent(Event event) {
                    refresh();
            }
           });
		
		CRUDTreeTableUtils.configUpdate(tableCoin);
		CRUDTreeTableUtils.configReports(tableCoin, contributorName(), searchCriteriaList);

		
		tableCoin.addDeleteListener(new TreeColumnListener() {
			public void handleEvent(final TreeColumnEvent event) {
				

				//event.performDelete = true;

				MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_QUESTION), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_WARN_DELET_DATA),
						new DialogCallback() {

							private static final long serialVersionUID = 8415736231132589115L;

							@Override
							public void dialogClosed(int returnCode) {
								if (returnCode == 0) {
									tableCoin.destroyEditableArea();
									Coin coin = (Coin) event.entity.getRow();
									switch (((CoinViewController) controller).deleteCoin(coin)) {
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
									//if(((ProviderViewController) controller)
									//		.deleteProviderByProvider(provider)){
										
									//}else{
										
									//}
								    //int b = a;
									//searchProviders(0, tableProvider.getPaginator().getPageSize());
									tableCoin.destroyEditableArea();
									tableCoin.getPaginator().goToPage(tableCoin.getPaginator().getCurrentPage());
									//searchCoins(0, tableCoin.getPaginator().getPageSize());
									
								}
							}
						});
			
				

			}
		});
		
		/*
		CRUDTreeTableUtils.configRemove(tableCoin, new IActionCommand() {
			@Override
			public void execute(final TreeColumnEvent event) {
				tableCoin.destroyEditableArea();
				Coin coin = (Coin) event.entity.getRow();
				Long idCoin = coin.getCoinID();
				((CoinViewController) controller).deleteCoinById(idCoin);
				searchCoins(0, tableCoin.getPaginator().getPageSize());

				List<Coin> listAllCoins = ((CoinViewController) controller).findCoinByLibrary(library.getLibraryID());

				String pickList = listAllCoins.toString();
				pickList = pickList.replace("[", "");
				pickList = pickList.replace("]", "");
				String databaseName = "Registro_De_Adquisicion";
				String isisDefHome = library.getIsisDefHome();
				String workSheetNameDonation = "Donación";
				String workSheetNameCompra = "Compra";
				String workSheetNameDefault = "Default worksheet";
				String workSheetNameCanje = "Canje";
				try {

					WorksheetDef workSheetDonation = ((CoinViewController) controller).getAllManagementLibraryViewController().getLibraryService()
							.getWorksheet(workSheetNameDonation, databaseName, isisDefHome);
					workSheetDonation.getFieldByTag(22).setPickList(pickList);
					((CoinViewController) controller).getAllManagementLibraryViewController().getLibraryService().updateWorksheet(workSheetDonation);

					WorksheetDef workSheetCompra = ((CoinViewController) controller).getAllManagementLibraryViewController().getLibraryService()
							.getWorksheet(workSheetNameCompra, databaseName, isisDefHome);
					workSheetCompra.getFieldByTag(22).setPickList(pickList);
					((CoinViewController) controller).getAllManagementLibraryViewController().getLibraryService().updateWorksheet(workSheetCompra);

					WorksheetDef workSheetDefault = ((CoinViewController) controller).getAllManagementLibraryViewController().getLibraryService()
							.getWorksheet(workSheetNameDefault, databaseName, isisDefHome);
					workSheetDefault.getFieldByTag(22).setPickList(pickList);
					((CoinViewController) controller).getAllManagementLibraryViewController().getLibraryService().updateWorksheet(workSheetDefault);

					WorksheetDef workSheetCanje = ((CoinViewController) controller).getAllManagementLibraryViewController().getLibraryService()
							.getWorksheet(workSheetNameCanje, databaseName, isisDefHome);
					workSheetCanje.getFieldByTag(22).setPickList(pickList);
					((CoinViewController) controller).getAllManagementLibraryViewController().getLibraryService().updateWorksheet(workSheetCanje);

				} catch (Exception e1) {
					RetroalimentationUtils.showErrorMessage("JISIS is down");
				}

			}
		});
*/
		TreeTableColumn columns[] = { new TreeTableColumn(35, 0, "getCoinName"), 
				new TreeTableColumn(15, 1, "getCoinType.getNomenclatorName"), 
				new TreeTableColumn(20, 2, "getExchangeRate"),
				new TreeTableColumn(30, 3, "getUpdatedDateToString") };
		tableCoin.createTable(columns);

		tableCoin.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {
				if (event.sortData != null) {
					direction = event.sortData.sortDirection;
					switch (event.sortData.columnIndex) {
					case 1:
						orderByString = "coinName";
						break;
					case 2:
						orderByString = "coinType.nomenclatorName";
						break;
					case 3:
						orderByString = "exchangeRate";
						break;
					case 4:
						orderByString = "updatedDate";
						break;
					}
				}
				searchCoins(event.currentPage - 1, event.pageSize);
			}
		});

		consultButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -7973704151914496343L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				tableCoin.destroyEditableArea();
				tableCoin.clearRows();
				searchCriteriaList.clear();
				coinNameConsult = coinNameText.getText();

				if (UiUtils.getSelected(identifierCoin) == null) {
					identificationConsult = null;
				} else {
					identificationConsult = (Nomenclator) UiUtils.getSelected(identifierCoin);
				}

				orderByString = "coinName";
				direction = 1024;
				tableCoin.getPaginator().goToFirstPage();
				
				UiUtils.get().addSearchCriteria(searchCriteriaList,MessageUtil.unescape(AbosMessages.get().LABEL_NAME_COIN),coinNameText.getText()).addSearchCriteria(searchCriteriaList,MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFIER_COIN),identifierCoin);
				
				if (tableCoin.getRows().isEmpty()) {
					RetroalimentationUtils.showInformationMessage(right, cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NO_COINCIDENCES_FOUND);
					//Auxiliary.showLabelAndTable(coinListLabel, tableCoin, false);
				} 
			}
		});
		tableCoin.getPaginator().goToFirstPage();
		l10n();
		//consultButton.getShell().setDefaultButton(consultButton);
		return shell;
	}

	private void searchCoins(int page, int size) {
		Page<Coin> list = ((CoinViewController) controller).findByCoinByParams(library, coinNameConsult, identificationConsult, page, size, direction, orderByString);
		tableCoin.clearRows();
		tableCoin.setTotalElements((int) list.getTotalElements());
		tableCoin.setRows(list.getContent());
		tableCoin.refresh();
		
		//else {
			//Auxiliary.showLabelAndTable(coinListLabel, tableCoin, true);
		//}
	}

	@Override
	public boolean canClose() {
		return false;
	}

	@Override
	public void l10n() {
		manageCoinLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_MANAGE_COINS));
		searchCriteriaLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SEARCH_CRITERIA));
		coinNameLabel.setText(AbosMessages.get().LABEL_NAME_COIN);
		identifierCoinLabel.setText(AbosMessages.get().LABEL_IDENTIFIER_COIN);
		consultButton.setText(AbosMessages.get().BUTTON_CONSULT);
		newSearchButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_NEW_SEARCH));
		coinListLabel.setText(AbosMessages.get().LABEL_LIST_OF_MATCHES);
		tableCoin.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_NAME_COIN), MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFIER_COIN),
				MessageUtil.unescape(AbosMessages.get().LABEL_EXCHANGE_RATE), MessageUtil.unescape(AbosMessages.get().LABEL_UPDATE_DATE)));
		tableCoin.setAddButtonText(MessageUtil.unescape(AbosMessages.get().BUTTON_ADD));
		tableCoin.setCancelButtonText(MessageUtil.unescape(AbosMessages.get().BUTTON_CANCEL));
		tableCoin.setActionButtonText("exportPDFButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_PDF));
		tableCoin.setActionButtonText("exportExcelButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));
		
		int position = identifierCoin.getSelectionIndex();
		initialize(identifierCoin, ((CoinViewController) controller).getAllManagementLibraryViewController().getLibraryService().findNomenclatorByCode(library.getLibraryID(), Nomenclator.COIN_TYPE));
		identifierCoin.select(position);
		
		tableCoin.getParent().layout(true, true);
		tableCoin.getParent().redraw();
		tableCoin.getParent().update();
		tableCoin.l10n();
	}

	@Override
	public void setViewController(ViewController controller) {
		super.controller = controller;
	}
}
